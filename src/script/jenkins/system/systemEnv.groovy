package script.jenkins.system

import hudson.slaves.EnvironmentVariablesNodeProperty
import hudson.slaves.EnvironmentVariablesNodeProperty.Entry

def updateGlobalEnvVars(Map<String, String> envVars) {
    def jenkins = Jenkins.instance

    // Get the global node properties
    def globalNodeProperties = jenkins.getGlobalNodeProperties()
    def envVarsNodeProperty = globalNodeProperties.get(EnvironmentVariablesNodeProperty)

    // If no EnvironmentVariablesNodeProperty exists, create one
    if (envVarsNodeProperty == null) {
        envVarsNodeProperty = new EnvironmentVariablesNodeProperty()
        globalNodeProperties.add(envVarsNodeProperty)
    }

    // Get the environment variables map
    def envVarsMap = envVarsNodeProperty.getEnvVars()

    // Update the environment variables
    envVars.each { key, value ->
        envVarsMap.put(key, value)
        println "Added/Updated environment variable ${key} with value ${value}"
    }

    // Save the Jenkins configuration
    jenkins.save()
    println "Global environment variables updated successfully."
}

// Example usage
def newEnvVars = [
    'GLOBAL_ENV_VAR1': 'global_value1',
    'GLOBAL_ENV_VAR2': 'global_value2'
]

updateGlobalEnvVars(newEnvVars)