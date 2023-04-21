# CloudBees CI Pipeline Ninja

<p align="center">
  <img alt="pipeline-ninja-icon" src="https://www.jenkins.io/images/logos/ninja/ninja.png" height="160" />
  <p align="center">This track is orientated to the CI <strong>Developer</strong> rol</p>
</p>

---

[![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](#awesome)

Inspired by [CloudBees CI feature comparison](https://docs.cloudbees.com/docs/cloudbees-ci/latest/feature-definition) and completed with my experience in the field and awesome content (from awesome people) public on the Internet.

Checkout my GitHub start repositories for [CI Pipeline Developer](https://github.com/stars/carlosrodlop/lists/jenkins-cbci-developer).

## Jenkins CI: Starting with a solid Open Source core

- [Jenkinsfile](https://www.jenkins.io/doc/book/pipeline/jenkinsfile/) stores the definition of a pipeline as code. It can be [created via UI](https://www.jenkins.io/doc/book/pipeline/getting-started/#through-the-classic-ui) or [imported from SCM](https://www.jenkins.io/doc/book/pipeline/getting-started/#defining-a-pipeline-in-scm) ( 🍬 The second option is [the recommendation](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/pipeline-best-practices#_store_pipeline_definitions_in_a_source_code_management_scm_tool), although the first one might be interesting to deploy "Hello World" examples via the assistant.
  - 🍬 [Declarative Pipeline Migration Assistant](https://plugins.jenkins.io/declarative-pipeline-migration-assistant/) can automate the migration process of existing Freestyles projects to Pipelines.
  - The Jenkinsfile supports two types of syntax [Scripted syntax](https://www.jenkins.io/doc/book/pipeline/syntax/#scripted-pipeline) vs [Declarative syntax](https://www.jenkins.io/doc/book/pipeline/syntax/#declarative-pipeline) ( 🍬 use [Declarative syntax as default](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/pipeline-best-practices#_when_writing_a_pipeline_definition_use_declarative_syntax) )
    - The best source of truth: [Built-in Documentation](https://www.jenkins.io/doc/book/pipeline/getting-started/#built-in-documentation): Snippet Generator (Pipeline steps), Global Variable Reference, and Declarative Directive Generator.
    - [Declarative steps](https://www.jenkins.io/doc/book/pipeline/syntax/#declarative-steps) may use all the available steps documented in the [Pipeline Steps reference](https://www.jenkins.io/doc/pipeline/steps/)
      - ⚠️ [Any processing within a Pipeline should occur within an agent](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/pipeline-best-practices#_do_all_the_work_within_an_agent) inside the [sh](https://www.jenkins.io/doc/pipeline/steps/workflow-durable-task-step/#sh-shell-script) (Linux Build) or [bat](https://www.jenkins.io/doc/pipeline/steps/workflow-durable-task-step/#bat-windows-batch-script) (Windows Build) (🍬 See this [🎥 How to Run a Shell Script in Jenkins Pipeline](https://www.youtube.com/watch?v=mbeQWBNaNKQ))
      - [script block](https://www.jenkins.io/doc/book/pipeline/syntax/#script) takes a block of Scripted Pipeline and executes that in the Declarative Pipeline.
    - ⚠️ It is important to understand [String Interpolation](https://www.jenkins.io/doc/book/pipeline/jenkinsfile/#string-interpolation), especially for security implication with [credentials](https://www.jenkins.io/doc/book/pipeline/jenkinsfile/#interpolation-of-sensitive-environment-variables) or [injection](https://www.jenkins.io/doc/book/pipeline/jenkinsfile/#injection-via-interpolation).
    - Learn by examples with:
      - [cloudbees/intro-to-declarative-pipeline](https://github.com/cloudbees/intro-to-declarative-pipeline)
      - [darinpope/jenkinsfile-library](https://github.com/darinpope/jenkinsfile-library)
      - [darinpope/jenkins-example-\*](https://github.com/darinpope?language=&page=2&q=jenkins-example&sort=&tab=repositories)
    - Check [Pipeline Development Tools](https://www.jenkins.io/doc/book/pipeline/development/), additionally, check these other [IDE integrations](https://www.jenkins.io/doc/book/pipeline/development/#ide-integrations):
      - VisualStudio Code: [Jenkins Jack](https://marketplace.visualstudio.com/items?itemName=tabeyti.jenkins-jack), [Jenkins Extension Pack](https://marketplace.visualstudio.com/items?itemName=DontShaveTheYak.jenkins-extension-pack)
      - IntelliJ: [Jenkins Pipeline Linter - IntelliJ IDEs Plugin](https://plugins.jetbrains.com/plugin/15699-jenkins-pipeline-linter)
        - [Working with Jenkinsfile in Intellij IDEA](http://vgaidarji.me/blog/2018/07/30/working-with-jenkinsfile-in-intellij-idea/)
- Branch Source plugins like The [GitHub Branch Source plugin](https://plugins.jenkins.io/github-branch-source/) allow you to create a new project based on the repository structure of one or more GitHub users or organizations. You can either:
  - Use [Multibranch Pipelines](https://www.jenkins.io/doc/book/pipeline/multibranch/) to import a single repository’s branches, pull requests, and tags as Pipeline projects.
  - :octocat: Use a GitHub Organization project to import all or a subset of repositories belonging to a GitHub user or organization as "Multibranch Pipeline" projects.
- 🏃 Make Jenkins Builds Faster by:
  - Use the [Performance optimized](https://www.jenkins.io/doc/book/pipeline/scaling-pipeline/#suggested-best-practices-and-tips-for-durability-settings) mode for most pipelines and especially basic build-test Pipelines or anything that can simply be run again if needed.
  - Identifying where are the bottlenecks by [🎥 Tracing Your Jenkins Pipelines With OpenTelemetry and Jaeger](https://www.youtube.com/watch?v=3XzVOxvNpGM) (Additionally [Troubleshooting Slow Builds](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/troubleshooting-guides/how-to-identify-cause-for-building-times-increase)).
  - Adding cache for your project dependencies (examples for [gradle](https://www.cloudbees.com/videos/speeding-up-jenkins-and-maven-build-cache) or [maven](https://sneha-wadhwa.medium.com/speeding-up-ci-pipelines-on-jenkins-63efff817d1d)) to speed up the build times.
    - ⚠️ Kubernetes particularities: [Improve Build Times on Kubernetes-Based Jenkins with Stateful Agents](https://blog.hiya.com/kubernetes-base-jenkins-stateful-agents/).
  - Using [Parallel](https://www.jenkins.io/doc/book/pipeline/syntax/#parallel) stages when it is possible.
- Install [Pipeline Utility Steps](https://github.com/jenkinsci/pipeline-utility-steps-plugin/blob/master/docs/STEPS.md) for adding useful Goodies For Parsing Configs, Maven POMs, etc
- 🐳 Docker Agents
  - Traditional Platform
    - [Using Docker with Pipeline](https://www.jenkins.io/doc/book/pipeline/docker/)
  - Modern Platform (Kubernetes)
    - Orchestrate docker images via [Kubernetes | Jenkins plugin](https://plugins.jenkins.io/kubernetes/)
    - Use tools like [Kaniko for building docker images](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/using-kaniko)
    - [Windows containers](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/agents#_setting_up_a_kubernetes_cluster_with_linux_and_windows_node_pools) are supported.
- 📚 [Shared Library](https://www.jenkins.io/doc/book/pipeline/shared-libraries/)
  - It allows to:
    - [Prototype steps and pipelines using Global vars](https://github.com/aimtheory/jenkins-pipeline-best-practices)
    - Perform [Unit Testing](https://github.com/jenkinsci/JenkinsPipelineUnit)
  - [Resource folder](https://www.jenkins.io/doc/book/pipeline/shared-libraries/#loading-resources) to load agent configuration and scripts ([🎥 Using Resource Files From a Jenkins Shared Library](https://www.youtube.com/watch?v=eV7roTXrEqg))
  - Learn by examples with:
    - [jenkins-infra/pipeline-library](https://github.com/jenkins-infra/pipeline-library)
    - [vfarcic/jenkins-shared-libraries](https://github.com/vfarcic/jenkins-shared-libraries)
- 🍬 Review [Pipeline Best Practices](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/pipeline-best-practices) docs, and also this post [Best Practices for Scalable Pipeline Code](https://www.jenkins.io/blog/2017/02/01/pipeline-scalability-best-practice/).

## CloudBees CI: Make Jenkins Pipelines more scalable and reliable

- Make pipelines more resilient with:
  - ↪️ [Checkpoints](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/inserting-checkpoints)
  - [Aborted builds](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/controlling-builds#aborted-builds) detect restore scenario and attempts to bring your instance back up quickly after the loss of environment.
- Align [pipeline Best Practices](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/pipeline-best-practices) within your organization:
  - 🚓 [Pipelines Policies](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/pipeline-policies) ([🎥 Demo video](https://www.youtube.com/watch?v=Js4d35kv19I))
  - [Pipeline Template Catalogs](https://docs.cloudbees.com/docs/admin-resources/latest/pipeline-templates-user-guide/setting-up-a-pipeline-template-catalog)([🎥 Demo video](https://www.youtube.com/watch?v=pPwI_kTSCmA))
  - 📌 [Markerfile](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/pipeline-as-code#custom-pac-scripts) restricts/selects which pipeline branches are candidates to be built within a Project.
