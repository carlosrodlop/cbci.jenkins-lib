/* groovylint-disable CompileStatic, DuplicateNumberLiteral */
//https://stackoverflow.com/questions/159148/groovy-executing-shell-commands
package script.jenkins.system

//input
shCommand = 'ls var/jenkins_home'

sout = new StringBuilder()
serr = new StringBuilder()
soutf = new StringBuilder()
serrf = new StringBuilder()

proc = shCommand.execute()
proc.consumeProcessOutput(sout, serr)
proc.waitForOrKill(1000)
soutf = sout.length() > 0 ? sout : 'none'
serrf = serr.length() > 0 ? serr : 'none'
println 'OUTPUT:\n' +
        "$soutf\n" +
        'ERROR:\n' +
        "$serrf\n"
null
