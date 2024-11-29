/*
 * It updates environment variables for an existing folder passed as parameter (folderName)
 * Tested on CloudBees CI Managed Controller 2.476.1.2
 */
package script.jenkins.jobs

import com.cloudbees.hudson.plugins.folder.properties.EnvVarsFolderProperty

def updateEnvVarsForFolder(String folderName, String envValues) {
    def targetFolder = Jenkins.instance.getItemByFullName(folderName)
    if (targetFolder) {
        println "Processing folder: ${targetFolder.fullName}"
        println 'Existing Env properties: \n' + targetFolder.properties.get(EnvVarsFolderProperty)?.properties
        def envars = new EnvVarsFolderProperty(envValues)
        targetFolder.properties.replace(envars)
        targetFolder.save()
        println 'New Env properties: \n' + targetFolder.properties.get(EnvVarsFolderProperty)?.properties
    } else {
        println "ERROR: Folder ${folderName} not found."
    }
}

// Example usage
def parFolderName = 'test-folder'
def parEnvValues = 'FOO=foo\nBAR=bar'
updateEnvVarsForFolder(parFolderName, parEnvValues)