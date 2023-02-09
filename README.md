# CBCI Admins Labs

[![gitleaks badge](https://img.shields.io/badge/protected%20by-gitleaks-blue)](https://github.com/zricethezav/gitleaks#pre-commit) [![gitsecrets](https://img.shields.io/badge/protected%20by-gitsecrets-blue)](https://github.com/awslabs/git-secrets)

[![Baywatch](resources/img/baywatch/Jenkins_Support_Baywatch_flags.png)](resources/img/baywatch/)

- [Jenkins Shared Libraries](vars)
  - [K8](resources/k8s)
  - [Docker](resources/docker)
- [Groovy src](src)
- [CloudBees Pipeline Templates](templates)
- [Jenkinsfile](pipelines)
- Operate Remotely with CloudBeesCI/Jenkins
  - [Jenkins CLI](resources/cli)
  - [Jenkins API-REST](resources/rest-api)

## Structure

It creates a unique repository compatible to be imported to CloudBees CI as `Jenkins Shared Libraries` and/or `CloudBees Pipeline Template Catalog`.

The rest of the elements are saved within the `resource` folder and it could be [loaded](https://www.jenkins.io/doc/book/pipeline/shared-libraries/#loading-resources) into a `Jenkinsfile` if it was required.

## References

- [CloudBees CI Workshops](https://cloudbees-ci.labs.cb-sa.io/)
- [CloudBees CI Pipeline Workshop](https://cloudbees-ci-pipeline.labs.cb-sa.io/getting-started/)
- [CloudBees Solutions Architecture Pipeline Examples](https://github.com/beedemo)
- [Jenkins Demonstrations](https://github.com/jenkins-demo)
- [Write Groovy scripts for Jenkins with code completion](https://www.mdoninger.de/2011/11/07/write-groovy-scripts-for-jenkins-with-code-completion.html)
