def call(configYaml) {

    Map config = readYaml text: "${configYaml}"

    pipeline {
        agent none
        environment {
            SLEEP_SEC = "${config.sleepTime}"
        }
        triggers {
            eventTrigger jmespathQuery("eventName=='${config.eventName}'")
        }
        stages {
            stage('Build') {
            environment {
                MOCK_DATA_SIZE = "${config.fileSize.trim()}"
            }
            agent {
                kubernetes {
                    yaml '''
                        apiVersion: v1
                        kind: Pod
                        spec:
                            containers:
                            - name: maven
                              image: maven:alpine
                              command:
                               - cat
                              tty: true
                        '''
                    retries 2
                    }
                }
                steps {
                    container('maven') {
                        sh """
                            mvn -version
                            sleep ${config.sleepTime}
                            dd if=/dev/zero of=mock.${BUILD_NUMBER}.dat  bs=${MOCK_DATA_SIZE}  count=1
                        """
                    stash name: 'stuff', includes: 'mock.*.dat'
                    }
                }
            }
            stage("Checkpoint") {
                agent none
                steps {
                    checkpoint 'middle'
                }
            }
            stage('Post Build') {
                agent {
                    kubernetes {
                    yaml '''
                        apiVersion: v1
                        kind: Pod
                        spec:
                            containers:
                            - name: busybox
                              image: busybox
                              command:
                              - cat
                              tty: true
                        '''
                    retries 2
                    defaultContainer 'busybox'
                    }
                }
                stages {
                    stage ('Test'){
                        steps {
                            sh '/bin/busybox'
                            script {
                                Random random = new Random()
                                def random_int = (int) random.next(50)
                                echo "random_int : ${random_int}"
                                if (random_int % 2 == 0) {
                                    currentBuild.result = 'SUCCESS'
                                } else {
                                    currentBuild.result = 'FAILURE'
                                }
                            }
                        }
                    }
                    stage ('Deploy'){
                        when {
                            expression { currentBuild.result == 'SUCCESS' }
                        }
                        steps {
                            dir ("unstash"){ // To avoid java.nio.file.AccessDeniedException
                                unstash 'stuff'
                                sh "sleep ${config.sleepTime}"
                                archiveArtifacts allowEmptyArchive: true, artifacts: 'mock.*.dat'
                            }
                        }
                    }
                }
            }
        }
    }
}
