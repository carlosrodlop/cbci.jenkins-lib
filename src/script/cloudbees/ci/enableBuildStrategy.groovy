import org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject
import com.cloudbees.jenkins.plugins.buildstrategies.SkipInitialBuildOnFirstIndexingResetRevision

def dryRun = true

// Function to enable build strategy
def enableBuildStrategy(dryRun) {
    def modifiedCount = 0
    def emptyCount = 0
    def nonEmptyCount = 0

    Jenkins.instance.getAllItems(WorkflowMultiBranchProject).each { multibranchProject ->
		
        multibranchProject.getSourcesList().each { branchSource ->
            if (branchSource !=null) { 
                def buildStrategies = branchSource.getBuildStrategies()
                if (buildStrategies.isEmpty()) {
                    if (dryRun){
                        println "Dryrun: ${multibranchProject.fullName} is a candidate to add Build Strategy."
                    } else {
                        branchSource.setBuildStrategies(Arrays.asList(new SkipInitialBuildOnFirstIndexingResetRevision()))
                        println "Adding Build Strategy SkipInitialBuildOnFirstIndexingResetRevision for ${multibranchProject.fullName}" 
                        modifiedCount++
                    }
                    emptyCount++
                } else {
                    println "${multibranchProject.fullName} has enabled Build Strategy ${buildStrategies}"
                    nonEmptyCount++
                }
            }
        }
    }
    println "===================================================================================="
    println "Non-empty build strategies count: ${nonEmptyCount}"
    println "Empty build strategies count: ${emptyCount}"
    println "Added build strategies SkipInitialBuildOnFirstIndexingResetRevision: ${modifiedCount}"
    println "===================================================================================="
}

enableBuildStrategy(dryRun)
null