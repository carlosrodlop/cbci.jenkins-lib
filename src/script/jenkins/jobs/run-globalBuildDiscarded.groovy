package script.jenkins.jobs

buildDiscarderWork = PeriodicWork.all().get(jenkins.model.BackgroundGlobalBuildDiscarder.class)
buildDiscarderWork.doRun()

null
