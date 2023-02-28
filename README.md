# CBCI Admins Labs

[![gitleaks badge](https://img.shields.io/badge/protected%20by-gitleaks-blue)](https://github.com/zricethezav/gitleaks#pre-commit) [![gitsecrets](https://img.shields.io/badge/protected%20by-gitsecrets-blue)](https://github.com/awslabs/git-secrets)

CloudBees CI admin library separated in 2 branches:

- [pipeline](https://github.com/carlosrodlop/cbci.jenkins-libs/tree/pipelines/resources/docker/agents): Compatible with `Jenkins Shared Libraries` and `CloudBees Pipeline Template Catalog` structures.
  - [Jenkinsfile](pipelines)
  - [Jenkins Shared Libraries](vars)
    - [K8](resources/k8s)
    - [Docker](resources/docker)
  - [Groovy automation](src)
  - [CloudBees Pipeline Templates Catalog](templates)
- [main](https://github.com/carlosrodlop/cbci.jenkins-libs/tree/main): Admin resources beyond the pipeline scope.
