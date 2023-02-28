# CBCI Admins Labs - `pipelines`

[![gitleaks badge](https://img.shields.io/badge/protected%20by-gitleaks-blue)](https://github.com/zricethezav/gitleaks#pre-commit) [![gitsecrets](https://img.shields.io/badge/protected%20by-gitsecrets-blue)](https://github.com/awslabs/git-secrets)

CloudBees CI admin library is separated into 2 branches:

- [pipeline](https://github.com/carlosrodlop/cbci.jenkins-libs/tree/pipelines/resources/docker/agents): Compatible with `Jenkins Shared Libraries` and `CloudBees Pipeline Template Catalog` structures.
- [main](https://github.com/carlosrodlop/cbci.jenkins-libs/tree/main): Admin resources beyond the pipeline scope.

##  pipelines

- Jenkins Shared Libraries
  - [Global variables](vars)
  - [Source files](src)
  - [resources](resources)
    - [K8](resources/k8s)
    - [Docker](resources/docker)
    - [Jenkinsfiles](resources/jenkinsfiles)
    - [Bash scripts](resources/bash)
- [CloudBees Pipeline Templates Catalog](templates)
