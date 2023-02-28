/* groovylint-disable CompileStatic */
package script.jenkins.jobs

import jenkins.model.*

jenkins = Jenkins.instanceOrNull
// Example https://jenkins.example.com/job/folder-example/job/multibranch-example
jobFullName = 'folder-example/multibranch-example'
scan = jenkins.getItemByFullName(jobFullName).scheduleBuild()
scan
null
