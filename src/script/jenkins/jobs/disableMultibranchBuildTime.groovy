package script.jenkins.jobs

def dryRun = false
def numDays = 365

// Function to disable old multibranch projects
def disableOldMultibranchProjects(dryRun, numDays) {
    def currentDate = Calendar.getInstance()
    def modifiedCount = 0

    // Iterate through all multibranch pipeline jobs in Jenkins
    Jenkins.instance.getAllItems(org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject).each { multibranchProject ->
        def allBranchesOld = true
        def hasBranches = false
        
        // Iterate through all branch projects
        multibranchProject.getItems().each { branchProject ->
            hasBranches = true
            def lastBuild = branchProject.getLastBuild()
            if (lastBuild != null) {
                def lastBuildDate = lastBuild.getTime()
                def timeCut = currentDate.clone()
                timeCut.add(Calendar.DAY_OF_YEAR, -numDays)
                
                // Check if the last build is older than the specified number of days
                if (lastBuildDate.after(timeCut.getTime())) {
                    allBranchesOld = false
                }
            } else {
                allBranchesOld = false
            }
        }

        // If the multibranch project has branches and all branches are older than the specified number of days, disable the multibranch project
        if (hasBranches && allBranchesOld) {
            if (dryRun) {
                println "Dry run: Would disable multibranch project: ${multibranchProject.fullName}"
            } else {
                println "Disabling multibranch project: ${multibranchProject.fullName}"
                multibranchProject.doDisable()
            }
            modifiedCount++
        }
    }

    // Print the number of modified multibranch projects
    println '================================================================================================='
    println "Total number of multibranch projects with all branches older than ${numDays} is ${modifiedCount}"
    println '================================================================================================='
}

// Call the function with the specified parameters
disableOldMultibranchProjects(dryRun, numDays)