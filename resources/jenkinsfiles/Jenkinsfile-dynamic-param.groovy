def list = []
pipeline {
    agent none
    parameters {
        choice choices: ['dev', 'stage', 'prod'], description: '', name: 'environment'
    }
    stages {
        stage('fetch Data'){
            // An agent with jq or yq installed can be used for JSON processing
            agent {
                label "main"
            }
            steps{
                script{
                    if ( params.environment == 'dev') {
                        sh "curl -XGET https://gist.githubusercontent.com/carlosrodlop/5657561200ff1b8fda7b4af76767b908/raw/750ea536721568db0d922219506a2937486b21a0/dummy.json > example.json"
                        // See https://www.jenkins.io/doc/pipeline/steps/pipeline-utility-steps/#readjson-read-json-from-files-in-the-workspace
                        def props = readJSON file: 'example.json'
                        list.add(props['key1'])
                        list.add(props['key3'])
                    } else {
                        list.add("value8")
                        list.add("value9")
                    }
                    
                }
            }
        }
        stage('Select Data') {
            steps {
                input message: 'which element to query?', parameters: [choice(choices: list, description: '', name: 'list')]
            }
        }
    }
}