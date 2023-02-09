package script.jenkins.jobs

import jenkins.model.*

def jenkins = Jenkins.instanceOrNull
// Example https://jenkins.example.com/job/folder-example/job/multibranch-example
def jobFullName="folder-example/multibranch-example"
def scan= jenkins.getItemByFullName("$jobFullName").scheduleBuild()
scan
