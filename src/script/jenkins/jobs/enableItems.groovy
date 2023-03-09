/* groovylint-disable CompileStatic, Instanceof, UnnecessaryGetter */
/*
 * Tested on CloudBees CI Managed Controller 2.375.2.3-rolling
 */
package script.jenkins.jobs

import jenkins.model.*
import com.cloudbees.hudson.plugins.folder.AbstractFolder

ArrayList folders = ['folder-test']
dryRun = true //true (mock) or false (real)
disable = false //true (disable) or false (enable)

jenkins = Jenkins.instanceOrNull
validPathsHelp = false

for (String folder : folders) {
    targetFolder = jenkins.getItemByFullName(folder, AbstractFolder)
    if (targetFolder) {
        targetFolder.getAllItems().findAll{ it instanceof ParameterizedJobMixIn.ParameterizedJob || it instanceof AbstractFolder }.each {
            if (!dryRun) {
                it.makeDisabled(disable)
                  println("Item [$it.fullName] has been set Disable to $disable")
            } else {
                  println("Item [$it.fullName] would be a candidate to set Disable to $disable in case dryRun=false")
            }
        }
  } else {
        println "Folder $folder is not a valid path."
        validPathsHelp = true
    }
}
if (validPathsHelp) {
    println 'Valid folders paths are:'
    jenkins.getAllItems(AbstractFolder).each { it ->
        println " - $it.fullName"
    }
}
null
