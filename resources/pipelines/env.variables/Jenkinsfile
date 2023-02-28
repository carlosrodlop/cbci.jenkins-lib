/* groovylint-disable CompileStatic */
// Required Approvals
// method org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper getRawBuild
// method hudson.model.Run getCause java.lang.Class
// hudson.model.Cause$UserIdCause getUserId

pipeline {
    agent any
    stages {
        stage('Mock checkout') {
            steps {
                git credentialsId: 'xxxxxxx-xxxxxx-xxxxxx-xxxxx',
                url: 'https://github.com/jenkins-demo/simple-maven-project-with-tests'
            }
        }
        stage('Set variables') {
            steps {
                    //$JenkinsURL/pipeline-syntax/globals
                    GO_PIPELINE_NAME = env.JOB_BASE_NAME
                    // https://stackoverflow.com/a/43609466/2684377
                    GO_TRIGGER_USER = currentBuild.rawBuild.getCause(Cause.UserIdCause).getUserId()
                    // https://github.com/jenkinsci/git-plugin#commit-variables
                    GO_REVISION = sh(script: "git rev-parse --short=5 ${GIT_COMMIT}", returnStdout: true).trim()
                }
            }
        }
        stage('Get variables') {
            steps {
                echo "GO_PIPELINE_NAME: ${GO_PIPELINE_NAME}"
                echo "GO_TRIGGER_USER: ${GO_TRIGGER_USER}"
                echo "GO_REVISION: ${GO_REVISION}"
            }
        }
    }
}
