// vars/gitUrlValidator.groovy
def call(paramName, paramValue) {
  if (paramValue.contains(" ")){
      error("${paramName} is not expecting any spaces within the string")
  }
}
