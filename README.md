# CBCI Admins Labs - `main`

[![gitleaks badge](https://img.shields.io/badge/protected%20by-gitleaks-blue)](https://github.com/zricethezav/gitleaks#pre-commit) [![gitsecrets](https://img.shields.io/badge/protected%20by-gitsecrets-blue)](https://github.com/awslabs/git-secrets)

[![Baywatch](img/baywatch/Jenkins_Support_Baywatch_flags.png)](img/baywatch/)

CloudBees CI admin library is separated into 2 branches:

- [main](https://github.com/carlosrodlop/cbci.jenkins-libs/tree/main): Admin resources beyond the Pipeline scope.
  - [Operate Remotely with CloudBeesCI](remote)
    - [Jenkins CLI](remote/cli)
    - [Jenkins API-REST](remote/rest-api)
  - [Groovy scripts automation](src/script)
- [pipelines](https://github.com/carlosrodlop/cbci.jenkins-libs/tree/pipelines): Compatible with `Jenkins Shared Libraries` and `CloudBees Pipeline Template Catalog` structures.

## References

- [Jenkins Javadoc](https://javadoc.jenkins.io/)
- [Script Console](https://www.jenkins.io/doc/book/managing/script-console/)
- [Script Console - Example of groovy scripts](https://www.jenkins.io/doc/book/managing/script-console/#example-groovy-scripts)
- [Write Groovy scripts for Jenkins with code completion](https://www.mdoninger.de/2011/11/07/write-groovy-scripts-for-jenkins-with-code-completion.html)
- [cloudbees: jenkins-scripts](https://github.com/cloudbees/jenkins-scripts)
- [jenkinsci: jenkins-scripts](https://github.com/jenkinsci/jenkins-scripts/tree/master/scriptler)
