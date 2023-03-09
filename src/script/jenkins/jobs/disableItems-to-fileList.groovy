/* groovylint-disable CompileStatic, ImplicitClosureParameter, Instanceof, UnnecessaryGetter */
/*
 * It saves existing disabled items in a txt file passed as parameter (pathToExistingDisabledItems)
 * Tested on CloudBees CI Managed Controller 2.375.2.3
 */
package script.jenkins.jobs

import com.cloudbees.hudson.plugins.folder.AbstractFolder

pathToExistingDisabledItems = '/tmp/disabledItems.txt'
jenkins = Jenkins.instanceOrNull
file = new File(pathToExistingDisabledItems)
disabledItems = new StringBuilder()

jenkins.getAllItems().findAll { it instanceof ParameterizedJobMixIn.ParameterizedJob || it instanceof AbstractFolder }
    .each {
        if (it.disabled) {
            disabledItems.append("- $it.fullName\n")
        }
    }
file.withWriter('UTF-8') { writer ->
   try {
        writer.writeLine "$disabledItems"
    } finally {
        writer.close()
    }
}
println file.text
null
