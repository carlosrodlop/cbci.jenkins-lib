import org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject
import com.cloudbees.jenkins.plugins.buildstrategies.SkipInitialBuildOnFirstIndexingResetRevision
import jenkins.branch.OrganizationFolder

def dryRun = false

// Function to enable build strategy

def enableBuildStrategy(dryRun) {
    def mPModifiedCount = 0
    def mPEmptyCount = 0
    def mPNonEmptyCount = 0
    def oFModifiedCount = 0
    def oFEmptyCount = 0
    def oFNonEmptyCount = 0

    def jenkins = Jenkins.instance

    jenkins.allItems(WorkflowMultiBranchProject).each { multibranchProject ->
        
        multibranchProject.getSourcesList().each { branchSource ->
            if (branchSource !=null) { 
                def buildStrategies = branchSource.getBuildStrategies()
                if (buildStrategies.isEmpty()) {
                    if (dryRun){
                        println "Dryrun: Multibranch: ${multibranchProject.fullName} is a candidate to add Build Strategy."
                    } else {
                        branchSource.setBuildStrategies(Arrays.asList(new SkipInitialBuildOnFirstIndexingResetRevision()))
                        println "Multibranch: Adding Build Strategy SkipInitialBuildOnFirstIndexingResetRevision for ${multibranchProject.fullName}" 
                        mPModifiedCount++
                        multibranchProject.save()
                    }
                    mPEmptyCount++
                } else {
                    println "Multibranch: ${multibranchProject.fullName} has already enabled Build Strategy ${buildStrategies}."
                    mPNonEmptyCount++
                }
            }
        }
    }

    jenkins.allItems(OrganizationFolder).each { orgFolder ->
            def buildStrategies = orgFolder.getBuildStrategies()
            if (buildStrategies.isEmpty()) {
                if (dryRun) {
                    println "Dryrun: Org Folder: ${orgFolder.fullName} is a candidate to add Build Strategy."
                } else {
                    orgFolder.getBuildStrategies().add(new SkipInitialBuildOnFirstIndexingResetRevision())
                    println "Org Folder: Adding Build Strategy SkipInitialBuildOnFirstIndexingResetRevision for ${orgFolder.fullName}"
                    oFModifiedCount++
                    orgFolder.save()
                }
                oFEmptyCount++
            } else {
                println "Org Folder: ${orgFolder.fullName} has already enabled Build Strategy ${buildStrategies}."
                oFNonEmptyCount++
            }
    }

    println """
====================================================================================
TOTALS
    Multibranch:
        Non-empty build strategies count: ${mPNonEmptyCount}
        Empty build strategies count: ${mPEmptyCount}
        Added build strategies SkipInitialBuildOnFirstIndexingResetRevision: ${mPModifiedCount}
    Organization Folder:
        Non-empty build strategies count: ${oFNonEmptyCount}
        Empty build strategies count: ${oFEmptyCount}
        Added build strategies SkipInitialBuildOnFirstIndexingResetRevision: ${oFModifiedCount}
===================================================================================="""
}

enableBuildStrategy(dryRun)

null