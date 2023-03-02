# CBCI Admins Labs - `pipelines`

[![gitleaks badge](https://img.shields.io/badge/protected%20by-gitleaks-blue)](https://github.com/zricethezav/gitleaks#pre-commit) [![gitsecrets](https://img.shields.io/badge/protected%20by-gitsecrets-blue)](https://github.com/awslabs/git-secrets)

CloudBees CI admin library is separated into 2 branches:

- [pipeline](https://github.com/carlosrodlop/cbci.jenkins-libs/tree/pipelines/resources/docker/agents): Compatible with `Jenkins Shared Libraries` and `CloudBees Pipeline Template Catalog` structures.
  - Jenkins Shared Libraries
    - [Global variables](vars)
    - [Source files](src)
    - [resources](resources)
      - [K8](resources/k8s)
      - [Docker](resources/docker)
      - [Jenkinsfiles](resources/jenkinsfiles)
      - [Bash scripts](resources/bash)
  - [CloudBees Pipeline Templates Catalog](templates)
- [main](https://github.com/carlosrodlop/cbci.jenkins-libs/tree/main): Admin resources beyond the pipeline scope.

## References

- [CloudBees CI Workshops](https://cloudbees-ci.labs.cb-sa.io/)
- [CloudBees CI Pipeline Workshop](https://cloudbees-ci-pipeline.labs.cb-sa.io/getting-started/)
- [CloudBees Solutions Architecture Pipeline Examples](https://github.com/beedemo)
- [Jenkins Demonstrations](https://github.com/jenkins-demo)
- [darinpope - jenkinsfile-library](https://github.com/darinpope/jenkinsfile-library)
- [darinpope - jenkinsfile-example-*](https://github.com/darinpope?language=&page=2&q=jenkins-example&sort=&tab=repositories)
- [vfarcic - jenkins-shared-libraries](https://github.com/vfarcic/jenkins-shared-libraries)
- [cloudbees/intro-to-declarative-pipeline: A gentle introduction to developing Declarative Pipelines for Jenkins.](https://github.com/cloudbees/intro-to-declarative-pipeline)

## Blog post

- [Template Catalog: Welcome to the Pipeline as Code Family](https://www.cloudbees.com/blog/pipeline-as-code)
- [Troubleshooting Jenkins Performance: Kubernetes Edition - Part 2](https://www.cloudbees.com/blog/application-performance-monitoring-tools)
