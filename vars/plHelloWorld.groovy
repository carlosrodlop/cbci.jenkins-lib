def call(configYaml) {

    Map config = readYaml text: "${configYaml}"

    pipeline {
        agent any
        environment {
            JOB_NAME = "${config.name.trim()}"
        }
        stages {
            stage ("Hello") {
                steps {
                    echo "Hello from Job ${JOB_NAME}!"
                }
            }
        }
    }
}
