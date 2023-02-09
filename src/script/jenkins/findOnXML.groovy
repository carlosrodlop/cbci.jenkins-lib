package script.jenkins

def xml = new XmlSlurper().parse('/Users/carlosrodlop/Support/cases/44422/configToTest.xml')
xml.depthFirst().findAll() {v ->
  // Comparing to tag name to search
  if (v.name() == "hudson.model.ListView"){
     // Create view at this level
     println "List name: ${v.name}"
     v.jobNames.string.each() { j ->
       // Assign job to create view at this level
       println "Job name: ${j}"
     }
  }
}
