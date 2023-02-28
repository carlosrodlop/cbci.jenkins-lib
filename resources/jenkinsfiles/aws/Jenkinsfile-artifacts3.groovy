/* groovylint-disable CompileStatic */
// resources/pocs/pipelines/aws/Jenkinsfile-artifacts3.groovy
pipeline {
    agent {
        kubernetes {
            containerTemplate {
                name 'slave'
                image 'jenkins/slave:alpine'
                ttyEnabled true
            }
        }
    }
    stages {
        stage('archiving artifacts into AWS s3') {
            steps {
                script {
                    sh 'dd if=/dev/urandom of=artifact.txt bs=5MB count=1'
                }
                archiveArtifacts artifacts: '*.*', fingerprint: true
            }
        }
    }
}
