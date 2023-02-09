// vars/gitUrlValidator.groovy
def call(paramName, paramValue, org, apiUri) {
  if (!org || !apiUri){
    if (! paramValue.startsWith("https") || ! paramValue.endsWith (".git")){
      error("${paramName} is expecting a git url endpoint. Example: https://scm.example.com/example-org/example-repo.git")
    }
  } else {
    if ( paramValue.startsWith("https") || paramValue.endsWith (".git")){
      error("${paramName} is expecting repo name from the ${org} in the ${apiUri}")
    }
  }
}
