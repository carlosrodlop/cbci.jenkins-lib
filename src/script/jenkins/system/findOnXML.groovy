/* groovylint-disable CompileStatic */
package script.jenkins.system

xml = new XmlSlurper().parse('/tmp/configToTest.xml')
xml.depthFirst().findAll { v ->
    // Comparing to tag name to search
    if (v.name() == 'hudson.model.ListView') {
        // Create view at this level
        println "List name: ${v.name}"
        v.jobNames.string.each { j ->
            // Assign job to create view at this level
            println "Job name: ${j}"
        }
    }
}
null
