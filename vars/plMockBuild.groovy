def call(configYaml) {

    Map config = readYaml text: "${configYaml}"

    properties([pipelineTriggers([eventTrigger(jmespathQuery("eventName=='${config.eventName}'"))])])

    parallel([0, 1].collectEntries {b -> ["branch-$b", {

        podTemplate {
            node("maven") {
            stage('Preparation') {
                sh 'curl https://ipinfo.io/'
            }

            stage('Build') {
                mockLoad config.buildTime
            }
        }

        checkpoint 'middle'

        podTemplate {
            node("maven") {
            stage('Deploy') {
                archiveArtifacts allowEmptyArchive: true, artifacts: 'mock-artifact-*.txt'
                fingerprint 'mock-artifact-*.txt'
                junit 'mock-junit.xml'
            }
            }
        }

    }}]})
}
