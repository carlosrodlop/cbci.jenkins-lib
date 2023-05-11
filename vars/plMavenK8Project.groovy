// vars/mavenK8Project.groovy
def call(configYaml) {

    Map config = readYaml text: "${configYaml}"

    K8_AGENT_YAML = "${config.k8_agent_yaml}"
    git_commit = ""
    git_currentBranch = ""

    pipeline {
        agent none
        options {
            buildDiscarder(logRotator(numToKeepStr: "5", artifactNumToKeepStr: "5"))
        }
        stages {
            stage ("Skip Run?") {
                when {
                    anyOf { changeset "**/template.yaml"; changeset "**/Jenkinsfile"}
                }
                steps {
                    echo "Aborting Pipeline due to changes are coming from Template definition"
                    script{
                        currentBuild.result = "ABORTED"
                    }
                }
            }
            stage ("Run") {
                when {
                    beforeAgent true
                    expression { currentBuild.result != "ABORTED" }
                }
                agent {
                    kubernetes {
                        namespace "${config.k8_agentNs.trim()}"
                        defaultContainer "maven"
                        yaml libraryResource("k8s/agents/java/${K8_AGENT_YAML}.yaml")
                    }
                }
                environment {
                    DOCKERFILE_PATH = "${config.d_path.trim()}"
                }
                stages {
                    stage("Print configuration") {
                        steps {
                            writeYaml file: "config.yaml", data: config
                            sh "cat config.yaml"
                        }
                    }
                    stage("Checkout") {
                        environment {
                            DOCKER_IMAGE_LATEST = "${config.d_latest}"
                        }
                        steps {
                            // Checkout step is done implicitly being a multibranch pipeline template
                            script {
                                git_currentBranch = "${BRANCH_NAME}"
                                if (DOCKER_IMAGE_LATEST == "false") {
                                    echo "Tagging image with commit"
                                    git_commit = sh(script: "git rev-parse --short=5 ${GIT_COMMIT}", returnStdout: true).trim()
                                } else {
                                    echo "Tagging image as latest"
                                    git_commit = "latest"
                                }
                            }
                        }
                    }
                    stage("Build") {
                        steps {
                            sh "mvn clean package -Dmaven.test.skip=true"
                            archiveArtifacts artifacts: "config.yaml, target/*.jar", fingerprint: true
                            stash name: "docker", includes: "config.yaml, target/*.jar, ${DOCKERFILE_PATH}"
                        }
                    }
                    stage("Test") {
                        steps {
                            sh "mvn clean test"
                            junit allowEmptyResults: true, testResults: "target/surefire-reports/*.xml"
                        }
                    }
                    stage("Publish in Registry") {
                        environment {
                            GIT_PARAM_REPO = "${config.bbs_repo}"
                            DOCKER_DESTINATION = "${config.d_registry.trim()}/${GIT_PARAM_REPO}_${git_currentBranch}:${git_commit}"
                        }
                        steps {
                            container(name: "kaniko", shell: "/busybox/sh") {
                                dir ("unstash"){ // To avoid java.nio.file.AccessDeniedException ... example-app.jar
                                    unstash "docker"
                                    sh "/kaniko/executor --dockerfile `pwd`/${DOCKERFILE_PATH} --context `pwd` --destination ${DOCKER_DESTINATION}"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
