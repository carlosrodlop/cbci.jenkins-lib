# CI Admin Hero

<p align="center">
  <img alt="terraform-icon" src="https://www.jenkins.io/images/logos/jenkins-is-the-way/jenkins-is-the-way.png" height="160" />
  <p align="center">Get CI Superadmin powers</p>
</p>

Inspired by [CloudBees CI feature comparison](https://docs.cloudbees.com/docs/cloudbees-ci/latest/feature-definition).

## Jenkins offering

* LTS Jenkins Distribution
* Installation
* Declarative configuration: JCasc
* Credentials
* Authorization
  * Active Directory
  * LDAP
  * SAML
* SCM Integrations: WebHooks
  * GitHub
  * Bitbucket
* Operate with Jenkins remotely
  * CLI
  * Rest API
* Automate Management tasks: [Script Console](https://www.jenkins.io/doc/book/managing/script-console/)
  * Groovy scripts repositories
  * [Write Groovy scripts for Jenkins with code completion](https://www.mdoninger.de/2011/11/07/write-groovy-scripts-for-jenkins-with-code-completion.html)
* Folder
* Cloud
  * Auto-configured Kubernetes agent
  * Windows container support on AKS, EKS, and GKE

## CloudBees CI: Make Jenkins Administration more scalable and reliable

* CloudBees Release Notes

* Support
  * Jenkins Health Advisor
  * Support CLI
  * Assisted updates

* Declarative configuration
  * Traditional Platform
  * Modern Platform

* Plugin Management
  * CloudBees Assurance Program - Beekeeper
    * Tested/validated the build of Jenkins to ensure plugin compatibility
    * A curated list of 200+ plugins to ensure compatibility with each other and the Jenkins build instance

* Operation Center: Shared Context for CI Controllers
  * Client/Managed Controllers
  * Controllers Casc Bundle Repository
  * Cluster Operations
  * Shared Agent Configuration
    * Shared Agent
    * Kubernetes Shared Cloud
  * Centrally managing security for controllers SSO

* Cloud plataform
  * Autoscaling for EKS and GKE
  * Hybrid Configuration
    * Controllers
    * Agents

* Folder plus:
  * Define environment variables that are passed to the builds of all jobs within a folder
  * Display selected jobs from subfolders in a higher-level view
  * Restrict which agents each team has access to

* Move Copy Promote

* Security
  * RBAC
  * Credentials Management
  * Restricted Credentials
  * Signed Docker images
  * Signed Helm Charts
  * CyberArk plugin
  * Trigger restrictions (restrict which upstream jobs are allowed to trigger builds of other jobs)

* Developer productivity
  * GitHub Apps: Receive and act upon granular, actionable build data directly in GitHub
  * Slack: Receive granular, actionable build data directly in Slack
  * Service Now: Create and manage ServiceNow change requests and incident tickets from your Pipeline
