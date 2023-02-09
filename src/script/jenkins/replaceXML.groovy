package script.jenkins

import javax.xml.transform.stream.*
import java.nio.charset.StandardCharsets

def paramName = '<oldTag>oldValue</oldTag>'
def newParamName = '<newTag>newValue</newTag>'
def paramJobName = 'job2update'
//<oldTag> could be keep and just update oldValue to newValue

Jenkins.instance.getAllItems(Job.class).each{j ->
  if (j.name==paramJobName){
         println 'Job: '+j.name
         def baos = new ByteArrayOutputStream()
         j.writeConfigDotXml(baos)
         def newXML = baos.toString("UTF-8").replaceAll(paramName,newParamName)
         InputStream stream = new ByteArrayInputStream(newXML.getBytes(StandardCharsets.UTF_8));
       j.updateByXml(new StreamSource(stream))
  }
}
