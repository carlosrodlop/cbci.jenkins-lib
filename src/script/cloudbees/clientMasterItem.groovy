/* groovylint-disable CompileStatic, UnnecessarySetter */
package script.cloudbees

// Imports
import com.cloudbees.opscenter.server.model.ClientMaster
import com.cloudbees.opscenter.server.model.ConnectedMaster
import com.cloudbees.opscenter.server.model.OperationsCenter
import com.cloudbees.opscenter.server.properties.ConnectedMasterLicenseServerProperty
import com.cloudbees.opscenter.server.config.ConnectedMasterWebSocketProperty

// Input parameters
clientMasterName = 'my-client-master_demo'
clientMasterId = 5
clientMasterGrantId = 'fjs2ktwfgd'

// Create Client Master Declaration
ClientMaster cm = OperationsCenter.instance.createClientMaster(clientMasterName)

// Another way to create a ClientMaster is as follow
//ClientMaster cm = Jenkins.instance.createProject(ClientMaster.class, clientMasterName)

//Set Client Master properties
cm.setId(clientMasterId)
cm.setIdName(ConnectedMaster.createIdName(clientMasterId, clientMasterName))
cm.setGrantId(clientMasterGrantId)
// Set the licensing strategy to its default by passing 'null'
cm.properties.replace(new ConnectedMasterLicenseServerProperty(null))
cm.properties.push(new  ConnectedMasterWebSocketProperty(true))
cm.save()

if (OperationsCenter.instance.getConnectedMasterByName(cm.idName) != null) {
    println "Created ClientMaster '${cm.name}' known as '${cm.idName}'"
    println "-DMASTER_INDEX=${cm.id}'"
    println "-DMASTER_NAME=${cm.name}'"
    println "-DMASTER_GRANT_ID=${cm.grantId}'"
} else {
    println '[ERROR:] $clientMasterName has not been created in CJOC'
}
return
