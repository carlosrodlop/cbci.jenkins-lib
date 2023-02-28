/**
 * Tested on CloudBees CI Managed Controller 2.375.2.3-rolling
 */

package script.jenkins.jobs

import jenkins.model.*
import com.cloudbees.hudson.plugins.folder.AbstractFolder

ArrayList folders = ["folderA", "folderB"]
dryRun=false //true (mock) or false (real)
disable=false //true (disable) or false (enable)

jenkins=Jenkins.instanceOrNull
validPathsHelp=false

for (String folder : folders) {
  if (jenkins.getItemByFullName(folder, AbstractFolder)) {
    jenkins.getItemByFullName(folder, AbstractFolder).getAllItems()
            .findAll { it instanceof ParameterizedJobMixIn.ParameterizedJob || it instanceof AbstractFolder }
            .each {
                if (!dryRun){
                    it.makeDisabled(disable)
                }
                println("Job [$it.fullName] set Disable to $disable")
            }
  } else {
    println "Folder $folder is not a valid path."
    validPathsHelp=true
  }
}
if (validPathsHelp){
    println "Valid folders paths are:"
    jenkins.getAllItems(AbstractFolder.class).each {it ->
    	println " - $it.fullName";
	}
 }
null
