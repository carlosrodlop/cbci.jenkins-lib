# CloudBees CI Pipeline Ninja

<p align="center">
  <img alt="pipeline-ninja-icon" src="https://www.jenkins.io/images/logos/ninja/ninja.png" height="160" />
  <p align="center">This track is orientated to the CI <strong>Developer</strong> rol</p>
</p>

---

Inspired by [CloudBees CI feature comparison](https://docs.cloudbees.com/docs/cloudbees-ci/latest/feature-definition).

## Jenkins CI: Starting with a solid Open Source core

* Know the difference between [Scripted vs Declarative](https://dsstream.com/declarative-vs-scripted-pipeline-key-differences/) syntax.
  * Declarative syntax uses a simpler and more strict way of creating a Jenkins Pipeline. The declarative character of this solution puts limitations on what developers can do and forces them to work within predefined structures.
  * Groovy code can still be used but inside the [script](https://www.jenkins.io/doc/book/pipeline/syntax/#script) block of declarative.
* [ssbostan/jenkins-tutorial: The completest Jenkins tutorial, reference, awesome, examples](https://github.com/ssbostan/jenkins-tutorial)
* Pipelines vs Multibranch Pipelines
  * Markerfile
* Review [Pipeline Best Practices](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/pipeline-best-practices)
* Pipeline Development
    * [Pipeline Steps Reference](https://www.jenkins.io/doc/pipeline/steps/)
    * [Built-in Documentation](https://www.jenkins.io/doc/book/pipeline/getting-started/#built-in-documentation): Snippet Generator, Global Variable Reference, Declarative Directive Generator
    * IDEs plugins
      * VisualStudio Code
        * [Jenkins Jack - Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=tabeyti.jenkins-jack)
        * [Jenkins Extension Pack - Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=DontShaveTheYak.jenkins-extension-pack)
        * [Jenkins Pipeline Linter Connector - Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=janjoerke.jenkins-pipeline-linter-connector)
      * Eclipse: [Jenkins Editor | Eclipse Plugins, Bundles and Products - Eclipse Marketplace](https://marketplace.eclipse.org/content/jenkins-editor)
      * IntellJ
        * [Jenkins Pipeline Linter - IntelliJ IDEs Plugin | Marketplace](https://plugins.jetbrains.com/plugin/15699-jenkins-pipeline-linter)
        * [Working with Jenkinsfile in Intellij IDEA](http://vgaidarji.me/blog/2018/07/30/working-with-jenkinsfile-in-intellij-idea/)
* Use Kaniko for building docker images
* Add cache for your dependencies 
* [Shared Library](https://www.jenkins.io/doc/book/pipeline/shared-libraries/)
  * [Prototype steps and pipelines using Global vars](https://github.com/aimtheory/jenkins-pipeline-best-practices)
  * [Resource folder](https://www.jenkins.io/doc/book/pipeline/shared-libraries/#loading-resources) to load agent configuration and script
    * Do not use groovy
    * sh and bat are executed on the agent
      * Script https://www.youtube.com/watch?v=mbeQWBNaNKQ

[Declarative Pipeline Migration Assistant | Jenkins plugin](https://plugins.jenkins.io/declarative-pipeline-migration-assistant/)

## CloudBees CI: Make Jenkins Pipelines more scalable and reliable

* Checkpoints
* Detects restore scenario and attempts to bring your instance back up quickly after loss of environment
* Pipelines Policies
* Pipeline Template Catalogs