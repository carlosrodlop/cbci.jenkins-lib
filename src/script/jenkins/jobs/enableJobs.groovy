/**
 * Tested on CloudBees CI Managed Controller 2.375.2.3-rolling
 */

package script.jenkins.jobs

import jenkins.model.*
import com.cloudbees.hudson.plugins.folder.AbstractFolder

ArrayList folders = ["folder-a","folder-b"]
dryRun=false //true (mock) or false (real)
disable=false //true (disable) or false (enable)
jenkins=Jenkins.instanceOrNull

for (String folder : folders) {
    jenkins.getItemByFullName(folder, AbstractFolder).getAllItems()
            .findAll { it instanceof ParameterizedJobMixIn.ParameterizedJob || it instanceof AbstractFolder }
            .each {
                if (!dryRun){
                    it.makeDisabled(disable)
                }
                println("Job [$it.fullName] set Disable to $disable")
            }
    null
}