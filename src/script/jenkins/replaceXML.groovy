/* groovylint-disable CompileStatic, NoWildcardImports */
package script.jenkins

import javax.xml.transform.stream.*
import java.nio.charset.StandardCharsets

paramName = '<oldTag>oldValue</oldTag>'
newParamName = '<newTag>newValue</newTag>'
paramJobName = 'job2update'

Jenkins.instance.getAllItems(Job).each { j ->
    if (j.name == paramJobName) {
        println 'Job:  $j.name'
        baos = new ByteArrayOutputStream()
        j.writeConfigDotXml(baos)
        newXML = baos.toString("UTF-8").replaceAll(paramName,newParamName)
        InputStream stream = new ByteArrayInputStream(newXML.getBytes(StandardCharsets.UTF_8));
        j.updateByXml(new StreamSource(stream))
    }
}
null
