/* groovylint-disable CompileStatic, UnnecessarySetter */
//https://stackoverflow.com/questions/159148/groovy-executing-shell-commands
package script.jenkins

import jenkins.model.Jenkins

message = 'foo'
Jenkins jenkins = Jenkins.instanceOrNull
jenkins.setSystemMessage(message)
jenkins.save()
null
