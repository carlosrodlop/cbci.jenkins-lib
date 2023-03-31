def call(configYaml) {

    Map config = readYaml text: "${configYaml}"

    pipeline {
        environment {
            SLEEP_SEC = "${config.sleepTime}"
        }
        agent {
            kubernetes {
                yaml '''
                    apiVersion: v1
                    kind: Pod
                    spec:
                        containers:
                        - name: stress
                          image: carlosrodlop/stress.ubuntu.ub:main
                          command:
                          - cat
                          tty: true
                        '''
                    retries 2
                    defaultContainer 'stress'
                    }
                }
        triggers {
            eventTrigger jmespathQuery("eventName=='${config.eventName}'")
        }
        stages {
            stage('Stress CPU') {
                steps {
                    sh """
                        uptime
                        sudo stress --cpu  8 --timeout 20
                        uptime
                    """
                }
            }
        }
    }
}
