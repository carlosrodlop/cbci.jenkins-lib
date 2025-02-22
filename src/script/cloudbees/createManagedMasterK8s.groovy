﻿/* groovylint-disable CompileStatic, UnnecessarySetter */
package script.cloudbees

import com.cloudbees.masterprovisioning.kubernetes.KubernetesImagePullSecret
import com.cloudbees.masterprovisioning.kubernetes.KubernetesMasterProvisioning
import com.cloudbees.opscenter.server.model.ManagedMaster
import com.cloudbees.opscenter.server.properties.ConnectedMasterLicenseServerProperty
import com.cloudbees.opscenter.server.properties.ConnectedMasterOwnerProperty

/*****************
 * INPUTS        *
 *****************/

/**
 * The master name is mandatory
 */
String  masterName = "mm-from-groovy"

/**
 * Following attributes may be specified. The values proposed are the default from version 2.2.9 of Master Provisioning
 *
 * Note: If not setting properties explicitly, the defaults will be used.
 */
/* Master */
String  masterDisplayName = ""
String  masterDescription = ""
String  masterPropertyOwners = ""
Integer masterPropertyOwnersDelay = 5

/* Master Provisioning */
Integer k8sDisk = 50
Integer k8sMemory = 3072
/**
 * Since version 2.235.4.1, we recommend not using the Heap Ratio. Instead add `-XX:MinRAMPercentage` and
 * `-XX:MaxRAMPercentage` to the Java options. For example, a ratio of 0.5d translate to a percentage of 50:
 * `-XX:MinRAMPercentage=50.0 -XX:MaxRAMPercentage=50.0`
 *
 * See https://support.cloudbees.com/hc/en-us/articles/204859670-Java-Heap-settings-best-practice and
 * https://docs.cloudbees.com/docs/release-notes/latest/cloudbees-ci/modern-cloud-platforms/2.235.4.1.
 */
Double  k8sMemoryRatio = null
Double  k8sCpus = 1
String  k8sFsGroup = "1000"
Boolean k8sAllowExternalAgents = false
String  k8sClusterEndpointId = "default"
String  k8sEnvVars = ""
String  k8sJavaOptions = "-XX:MinRAMPercentage=50.0 -XX:MaxRAMPercentage=50.0"
String  k8sJenkinsOptions = ""
String  k8sImage = 'CloudBees CI - Managed Master - 2.249.2.4'
List<KubernetesImagePullSecret> k8sImagePullSecrets = Collections.emptyList()
// Example:
//   def k8sImagePullSecret1 = new KubernetesImagePullSecret(); k8sImagePullSecret1.setValue("useast-reg")
//   List<KubernetesImagePullSecret> k8sImagePullSecrets = Arrays.asList(k8sImagePullSecret1)
Integer k8sLivenessInitialDelaySeconds = 300
Integer k8sLivenessPeriodSeconds = 10
Integer k8sLivenessTimeoutSeconds = 10
Integer k8sReadinessInitialDelaySeconds = 30
Integer k8sReadinessFailureThreshold = 100
Integer k8sReadinessTimeoutSeconds = 5
String  k8sStorageClassName = ""
String  k8sSystemProperties = ""
String  k8sNamespace = ""
String  k8sNodeSelectors = ""
Long    k8sTerminationGracePeriodSeconds = 1200L
String  k8sYaml = ""

/**
 * cascBundle (optional). Configuration as Code Configuration Bundle
 */
String cascBundle = ""

/*****************
 * CREATE MASTER *
 *****************/

/**
 * Create a Managed Masters with just a name (this will automatically fill required values for id, idName, grantId, etc...)
 * Similar to creating an iten in the UI
 */
ManagedMaster newInstance = jenkins.model.Jenkins.instanceOrNull.createProject(ManagedMaster.class, masterName)
newInstance.setDescription(masterDescription)
newInstance.setDisplayName(masterDisplayName)

/************************
 * CONFIGURATION BUNDLE *
 ************************/
if(cascBundle?.trim()) {
    newInstance.getProperties().replace(new com.cloudbees.opscenter.server.casc.config.ConnectedMasterCascProperty(cascBundle))
}

/********************
 * CONFIGURE MASTER *
 ********************/

/**
 * Configure the Master provisioning details. Refer to the `config.xml` for more details.
 * Similar to configuring a Managed Master from the UI
 */
KubernetesMasterProvisioning masterProvisioning = new KubernetesMasterProvisioning()
masterProvisioning.setDomain(masterName.toLowerCase())

/**
 * Apply Managed Master provisioning configuration (similar to what is configured through the Managed Master UI)
 * Note: If not setting properties explicitly, the defaults will be used.
 */
masterProvisioning.setDisk(k8sDisk)
masterProvisioning.setMemory(k8sMemory)
if(k8sMemoryRatio) {
    masterProvisioning.setHeapRatio(new com.cloudbees.jce.masterprovisioning.Ratio(k8sMemoryRatio))
    /**
     * For versions earlier than 2.235.4.1 (Master Provisioning plugin 2.5.6), use setRatio
     * masterProvisioning.setRatio(k8sMemoryRatio)
     */
}
masterProvisioning.setCpus(k8sCpus)
masterProvisioning.setFsGroup(k8sFsGroup)
masterProvisioning.setAllowExternalAgents(k8sAllowExternalAgents)
masterProvisioning.setClusterEndpointId(k8sClusterEndpointId)
masterProvisioning.setEnvVars(k8sEnvVars)
masterProvisioning.setJavaOptions(k8sJavaOptions)
masterProvisioning.setJenkinsOptions(k8sJenkinsOptions)
masterProvisioning.setImage(k8sImage)
masterProvisioning.setImagePullSecrets(k8sImagePullSecrets)
masterProvisioning.setLivenessInitialDelaySeconds(k8sLivenessInitialDelaySeconds)
masterProvisioning.setLivenessPeriodSeconds(k8sLivenessPeriodSeconds)
masterProvisioning.setLivenessTimeoutSeconds(k8sLivenessTimeoutSeconds)
masterProvisioning.setReadinessInitialDelaySeconds(k8sReadinessInitialDelaySeconds)
masterProvisioning.setReadinessFailureThreshold(k8sReadinessFailureThreshold)
masterProvisioning.setReadinessTimeoutSeconds(k8sReadinessTimeoutSeconds)
masterProvisioning.setStorageClassName(k8sStorageClassName)
masterProvisioning.setSystemProperties(k8sSystemProperties)
masterProvisioning.setNamespace(k8sNamespace)
masterProvisioning.setNodeSelectors(k8sNodeSelectors)
masterProvisioning.setTerminationGracePeriodSeconds(k8sTerminationGracePeriodSeconds)
masterProvisioning.setYaml(k8sYaml)

/**
 * Provide Master item general configuration (similar to what is configured through the Master UI)
 * Note: If not setting properties explicitly, the defaults will be used.
 */
if (masterPropertyOwners != null && !masterPropertyOwners.isEmpty()) {
    newInstance.getProperties().replace(new ConnectedMasterOwnerProperty(masterPropertyOwners, masterPropertyOwnersDelay))
}
newInstance.getProperties().replace(new ConnectedMasterLicenseServerProperty(new ConnectedMasterLicenseServerProperty.DescriptorImpl().defaultStrategy()))

/**
 * Save the configuration
 */
newInstance.setConfiguration(masterProvisioning)
newInstance.save()

/**
 * Retrieve the master from the API and print the details of the created Managed Master
 */
def instance = jenkins.model.Jenkins.instanceOrNull.getItemByFullName(newInstance.fullName, ManagedMaster.class)
println "${instance.name}"
println " id: ${instance.id}"
println " idName: ${instance.idName}"

/******************************
 * PROVISION AND START MASTER *
 ******************************/

instance.provisionAndStartAction()
println "Started the master..."
return
