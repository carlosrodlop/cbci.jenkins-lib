// resources/pocs/pipelines/aws/Jenkinsfile-withAWS-rol-1.groovy
pipeline {
    agent {
        docker { image 'carlosrodlop/jenkins-slave-aws:e7c3999' }
    }
    stages {
        stage('hello AWS') {
            steps {
                withAWS(profile:'example-profile') { // Pre-requisite: it must be defined in '~/.aws/config' in the master
                    echo "Trying 'aws sts get-caller-identity'"
                    sh "aws sts get-caller-identity"
                    echo "Trying 'awsIdentity()'"
                    script {
                        def identity = awsIdentity()
                    }
                }
            }
        }
    }
}
