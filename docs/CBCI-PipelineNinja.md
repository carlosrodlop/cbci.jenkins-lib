# CloudBees CI Pipeline Ninja

<p align="center">
  <img alt="pipeline-ninja-icon" src="https://www.jenkins.io/images/logos/ninja/ninja.png" height="160" />
  <p align="center">This track is orientated to the CI <strong>Developer</strong> rol</p>
</p>

---

[![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](#awesome) [![md2pdf](https://github.com/carlosrodlop/cbci.jenkins-lib/actions/workflows/md2pdf.yml/badge.svg)](https://github.com/carlosrodlop/cbci.jenkins-lib/actions/workflows/md2pdf.yml) [![mdLinkChecker](https://github.com/carlosrodlop/cbci.jenkins-lib/actions/workflows/mdLinkChecker.yaml/badge.svg)](https://github.com/carlosrodlop/cbci.jenkins-lib/actions/workflows/mdLinkChecker.yaml)

Inspired by [CloudBees CI feature comparison](https://docs.cloudbees.com/docs/cloudbees-ci/latest/feature-definition) and completed with my experience in the field and awesome content (from awesome people) public on the Internet.

## Jenkins CI: Starting with a solid Open Source core

* [Jenkinsfile](https://www.jenkins.io/doc/book/pipeline/jenkinsfile/) stores the pipeline as code definition and it can be [created via UI](https://www.jenkins.io/doc/book/pipeline/getting-started/#through-the-classic-ui) or [imported from SCM](https://www.jenkins.io/doc/book/pipeline/getting-started/#defining-a-pipeline-in-scm) ( 🍬 The second option is [the recommendation](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/pipeline-best-practices#_store_pipeline_definitions_in_a_source_code_management_scm_tool), although the first one might be interesting to deploy "Hello World" examples via the assistant ).
* The Jenkinsfile accepts two types of syntax [Scripted syntax](https://www.jenkins.io/doc/book/pipeline/syntax/#scripted-pipeline) vs [Declarative syntax](https://www.jenkins.io/doc/book/pipeline/syntax/#declarative-pipeline) ( 🍬 use [Declarative syntax as default](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/pipeline-best-practices#_when_writing_a_pipeline_definition_use_declarative_syntax) )
  * [Declarative steps](https://www.jenkins.io/doc/book/pipeline/syntax/#declarative-steps) may use all the available steps documented in the [Pipeline Steps reference](https://www.jenkins.io/doc/pipeline/steps/)
    * ⚠️ [Any processing within a Pipeline should occur within an agent](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/pipeline-best-practices#_do_all_the_work_within_an_agent) inside the [sh](https://www.jenkins.io/doc/pipeline/steps/workflow-durable-task-step/#sh-shell-script) (linux) or [bat](https://www.jenkins.io/doc/pipeline/steps/workflow-durable-task-step/#bat-windows-batch-script) (windows) (🍬 See this [🎥 How to Run a Shell Script in Jenkins Pipeline](https://www.youtube.com/watch?v=mbeQWBNaNKQ))
    * [script block](https://www.jenkins.io/doc/book/pipeline/syntax/#script) takes a block of Scripted Pipeline and executes that in the Declarative Pipeline.
  * Learn by examples in [ssbostan/jenkins-tutorial](https://github.com/ssbostan/jenkins-tutorial)
  * 🍬 For the migration of existing Freestyles projects to Declarative Pipelines the [Declarative Pipeline Migration Assistant](https://plugins.jenkins.io/declarative-pipeline-migration-assistant/) can be useful as an initial step.
* Branch Source plugins like The GitHub Branch Source plugin allow you to create a new project based on the repository structure from one or more GitHub users or organizations. You can either:
  * Use a [Multibranch Pipelines](https://www.jenkins.io/doc/book/pipeline/multibranch/) to import a single repository’s branches, pull requests, and tags as Pipeline projects.
  * Use a GitHub Organization project to import all or a subset of repositories belonging to a GitHub user or organization as "Multibranch Pipeline" projects.
* Reduce building times by
  * Adding cache for your project dependencies (examples for [gradle](https://www.cloudbees.com/videos/speeding-up-jenkins-and-maven-build-cache) or [maven](https://sneha-wadhwa.medium.com/speeding-up-ci-pipelines-on-jenkins-63efff817d1d)) to speed up the build times.
  * Using [Parallel](https://www.jenkins.io/doc/book/pipeline/syntax/#parallel) stages when it is possible
* Docker Agents
  * Traditional Platform
    * [Using Docker with Pipeline](https://www.jenkins.io/doc/book/pipeline/docker/)
  * Modern Platform (Kubernetes)
    * Orchestrate docker images via [Kubernetes | Jenkins plugin](https://plugins.jenkins.io/kubernetes/)
    * Use tools like [Kaniko for building docker images](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/using-kaniko)
* Pipeline Development
  * The best source of truth [Built-in Documentation](https://www.jenkins.io/doc/book/pipeline/getting-started/#built-in-documentation): Snippet Generator, Global Variable Reference, Declarative Directive Generator
  * Linters and Validations
    * API REST
    * IDEs plugins
      * VisualStudio Code
        * [Jenkins Jack - Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=tabeyti.jenkins-jack)
        * [Jenkins Extension Pack - Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=DontShaveTheYak.jenkins-extension-pack)
        * [Jenkins Pipeline Linter Connector - Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=janjoerke.jenkins-pipeline-linter-connector)
      * Eclipse: [Jenkins Editor | - Eclipse Marketplace](https://marketplace.eclipse.org/content/jenkins-editor)
      * IntelliJ: [Jenkins Pipeline Linter - IntelliJ IDEs Plugin](https://plugins.jetbrains.com/plugin/15699-jenkins-pipeline-linter)
        * [Working with Jenkinsfile in Intellij IDEA](http://vgaidarji.me/blog/2018/07/30/working-with-jenkinsfile-in-intellij-idea/)
* [Shared Library](https://www.jenkins.io/doc/book/pipeline/shared-libraries/)
  * [Prototype steps and pipelines using Global vars](https://github.com/aimtheory/jenkins-pipeline-best-practices)
  * [Resource folder](https://www.jenkins.io/doc/book/pipeline/shared-libraries/#loading-resources) to load agent configuration and script ([🎥 Using Resource Files From a Jenkins Shared Library](https://www.youtube.com/watch?v=eV7roTXrEqg))
* Review [Pipeline Best Practices](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/pipeline-best-practices)

## CloudBees CI: Make Jenkins Pipelines more scalable and reliable

* Make pipelines more resilient with:
  * [Checkpoints](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/inserting-checkpoints)
  * [Aborted builds](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/controlling-builds#aborted-builds) detect restore scenario and attempts to bring your instance back up quickly after loss of environment
* Align [pipeline Best Practices](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/pipeline-best-practices) within your organization
  * [Pipelines Policies](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/pipeline-policies) ([🎥 Demo video](https://www.youtube.com/watch?v=Js4d35kv19I))
  * [Pipeline Template Catalogs](https://docs.cloudbees.com/docs/admin-resources/latest/pipeline-templates-user-guide/setting-up-a-pipeline-template-catalog)([🎥 Demo video](https://www.youtube.com/watch?v=pPwI_kTSCmA))
* [Markerfile](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/pipeline-as-code#custom-pac-scripts): To support administrators wanting to restrict how Pipelines are defined, CloudBees CI includes an additional option for [Multibranch Pipelines](https://www.jenkins.io/doc/book/pipeline/multibranch/) and organization folders. With this option, the configuration in Jenkins consists of both the name of a markerfile, and the Pipeline script to run when it is encountered. (Validate only CI)