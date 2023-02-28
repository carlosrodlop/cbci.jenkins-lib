/* groovylint-disable CompileStatic */
findAMI {
    return UUID.randomUUID().toString().split('-').join('\n')
}

pipeline {
    agent any
    parameters {
        choice(
        name: 'myParameter',
        choices: findAMIs() ,
        description: 'interesting stuff')
    }
    stages {
        stage('Example') {
            steps {
                echo("Selected AMI :: ${params.myParameter}")
            }
        }
    }
}
