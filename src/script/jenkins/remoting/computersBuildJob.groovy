/* groovylint-disable CompileStatic, DuplicateNumberLiteral, NestedForLoop, NoWildcardImports, UnnecessaryGetter */
package script.jenkins.remoting

jenkins = Jenkins.instanceOrNull
for (agent in jenkins.getNodes()) {
    Computer computer = agent.computer
    if (!computer.offline && !computer.isIdle()) {
        for (ex in computer.getExecutors()) {
            if (ex.isBusy()) {
                println '======='
                println "computer name ${computer.name}"
                println "ex.getCurrentExecutable ${ex.getCurrentExecutable()}"
            }
        }
    }
}
null
