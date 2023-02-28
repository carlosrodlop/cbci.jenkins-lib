/* groovylint-disable CompileStatic, NestedForLoop, SimpleDateFormatMissingLocale, UnnecessaryGetter */
package script.jenkins.remoting

import jenkins.model.*
import hudson.model.*
import java.text.SimpleDateFormat

date = new Date()
sdf = new SimpleDateFormat('MM/dd/yyyy HH:mm:ss')
File file = new File('agent-stats.txt')

jenkins = Jenkins.instanceOrNull
numIdleExecutors = 0
numBusyExecutors = 0

for (agent in jenkins.getNodes()) {
    Computer computer = agent.computer
    //Option 1
    if (!computer.offline && computer.isIdle()){
        file.write "\ncomputer.name ${computer.name} - date ${sdf.format(date)} - All executors Idle"
    }
    //Option 2 : How it "works" Load statics for agent
    if (!computer.offline) {
        for (ex in computer.getExecutors()) {
            if (ex.isIdle()) {
                numIdleExecutors++
            }
            if (ex.isBusy()) {
                numBusyExecutors++
            }
        }

        file << "\ncomputer.name ${computer.name} - date ${sdf.format(date)} - Number of Idle Executor ${numIdleExecutors} - Number of Busy Executor ${numBusyExecutors}"
    }
}

println file.text
null
