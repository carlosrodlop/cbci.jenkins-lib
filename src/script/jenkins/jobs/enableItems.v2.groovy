/* groovylint-disable CompileStatic, Instanceof, UnnecessaryGetter */
/*
 * Tested on CloudBees CI Managed Controller 2.375.2.3-rolling
 */
package script.jenkins.jobs

import jenkins.model.*
import com.cloudbees.hudson.plugins.folder.AbstractFolder

pathToExistingDisabledItems = '/tmp/disabledItemsv3.txt'
dryRun = true //true (mock) or false (real)
disable = false //true (disable) or false (enable)
jenkins = Jenkins.instanceOrNull
file = new File(pathToExistingDisabledItems)

jenkins.getAllItems().findAll { it instanceof ParameterizedJobMixIn.ParameterizedJob || it instanceof AbstractFolder }
    .each {
        if (!dryRun) {
            if (file.text.contains("- $it.fullName\n")){
                println "$it.fullName is included inside $pathToExistingDisabledItems no action is made"
            } else {
                it.makeDisabled(disable)
                println("Item [$it.fullName] has been set Disable to $disable")
            }
         } else {
              if (file.text.contains("- $it.fullName\n")){
                println "$it.fullName is included inside $pathToExistingDisabledItems no action is made"
            } else {
                println("Item [$it.fullName] would be a candidate to set Disable to $disable in case dryRun=false")
            }
         }
      }
null
