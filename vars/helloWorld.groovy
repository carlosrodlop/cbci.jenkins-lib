// vars/helloWorld.groovy
def call(configYaml) {

    Map config = readYaml text: "${configYaml}"

    pipeline {
        agent any
        environment {
            PERSON_NAME = "${config.name.trim()}"
        }
        stages {
            stage ("Hello") {
                steps {
                    echo "Hello ${PERSON_NAME}!"
                }
            }
        }
    }
}
