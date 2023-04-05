# CloudBees CI Admin Hero

<p align="center">
  <img alt="admin-hero-icon" src="https://www.jenkins.io/images/logos/jenkins-is-the-way/jenkins-is-the-way.png" height="160" />
  <p align="center">This track is orientated to the CI <strong>Admin</strong> rol</p>
</p>

---

Inspired by [CloudBees CI feature comparison](https://docs.cloudbees.com/docs/cloudbees-ci/latest/feature-definition).

## Jenkins CI: Starting with the Open Source offering

* What is Jenkins CI?
  * [Jenkins (software) - Wikipedia](https://en.wikipedia.org/wiki/Jenkins_(software))
  * Spot Jenkins inside the [CD Landscape Map](https://landscape.cd.foundation/)
  * Get a first look at the UI accessing the instance [jenkins.io](https://ci.jenkins.io/) as a guest.
* Installation Steps
  * Get the latest Jenkins LTS Distribution
    * [Jenkins download and deployment](https://www.jenkins.io/download/)
    * Review [LTS Changelog](https://www.jenkins.io/changelog-stable/) to understand the new features and bug fixes.
  * [Install Jenkins](https://www.jenkins.io/doc/book/installing/) in your desired platform following Best practices as explained in [Prepare Jenkins for Support](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/best-practices/prepare-jenkins-for-support)
  * Additional [Jenkins Features are controlled with System Properties](https://www.jenkins.io/doc/book/managing/system-properties/).
  * Note that Jenkins comes with a series of required plugins but its capabilities can be extended via [Manage Plugins](https://www.jenkins.io/doc/book/managing/plugins/).
    * Use [Jenkins Plugins Index](https://plugins.jenkins.io/) to discover the 1800+ community contributed Jenkins plugins
* Configure Jenkins
  * Explore how to configure Jenkins in a Declarative way using [JCasc](https://github.com/jenkinsci/configuration-as-code-plugin), and pay special attention to the [Handling secrets](https://github.com/jenkinsci/configuration-as-code-plugin/blob/master/docs/features/secrets.adoc) and the [Exporting configuration tool](https://github.com/jenkinsci/configuration-as-code-plugin/blob/master/docs/features/configExport.md) sections.
  * Prepare the instance to onboard developers
    * 🔑 Add [Global Credentials](https://www.jenkins.io/doc/book/using/using-credentials/#adding-new-global-credentials) to the Jenkins Internal Store and [understand different scopes](https://github.com/jenkinsci/credentials-plugin/blob/master/docs/user.adoc#credentials-scopes).
    * 🔒 Define the [Access Control](https://www.jenkins.io/doc/book/security/managing-security/#access-control) mechanisms and know the most common authentication systems:
      * [Active Directory](https://plugins.jenkins.io/active-directory/) ([troubleshooting](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/troubleshooting-guides/cannot-make-my-ad-configuration-to-work) and [tunning](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/troubleshooting-guides/the-log-in-with-ad-plugin-is-very-slow))
      * [LDAP](https://plugins.jenkins.io/ldap/) ([troubleshooting](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/troubleshooting-guides/cannot-make-my-ldap-configuration-to-work) and [tunning](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/troubleshooting-guides/the-log-in-with-ldap-plugin-is-very-slow))
      * [SAML](https://plugins.jenkins.io/saml/) ([troubleshooting](https://github.com/jenkinsci/saml-plugin/blob/main/doc/TROUBLESHOOTING.md))
    * :octocat: Integrate with SCM [GitHub](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/client-and-managed-masters/github-webhook-configuration) via WebHook and check additional [SCM Best Practices](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/best-practices/scm-best-practices)
    * 💻 [Configure Agents](https://www.jenkins.io/doc/book/managing/nodes/#managing-nodes) to perform your builds (Avoid using Jenkins built-in node)
      * [Static Agents](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/agents#static-agents). Use [winsw](https://github.com/winsw/winsw) for Window inbound-agents.
      * Cloud [Kubernetes Plugin](https://plugins.jenkins.io/kubernetes/) ([troubleshooting](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/required-data/required-data-kubernetes-cloud))
        * Windows container support on AKS, EKS, and GKE
* Operate remotely with Jenkins
  * [Jenkins CLI](https://www.jenkins.io/doc/book/managing/cli/)
  * [REST API](https://www.jenkins.io/doc/book/using/remote-access-api/)
* Automate management via Groovy code running into the [Script Console](https://www.jenkins.io/doc/book/managing/script-console/)
  * [Jenkins Core and Plugins Javadoc](https://javadoc.jenkins.io/)
  * [Write Groovy scripts for Jenkins with code completion](https://www.mdoninger.de/2011/11/07/write-groovy-scripts-for-jenkins-with-code-completion.html)
  * [Learn by examples](https://www.jenkins.io/doc/book/managing/script-console/#example-groovy-scripts)
* Did you find any problem or issue until this point?
  * Find your answers within the [community](https://community.jenkins.io/) in different channels like [Stack Overflow](https://stackoverflow.com/questions/tagged/jenkins)
  * If you are stuck, report your request or bug in the [Jenkins Jira](https://issues.jenkins.io/secure/Dashboard.jspa)

## CloudBees CI: Make Jenkins Administration more scalable and reliable 🚀

* From day zero you are not alone in this journey, CloudBees Support counts on certified Jenkins experts that are willing to help
  * [Jenkins Health Advisor](https://plugins.jenkins.io/cloudbees-jenkins-advisor/)
  * [Support CLI](https://docs.cloudbees.com/docs/cbsupport/latest/) to collect data
  * [Assisted updates](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/required-data/required-data-upgrade-a-jenkins-instance)
* Install CloudBees CI on:
  * [Traditional platform](https://docs.cloudbees.com/docs/cloudbees-ci/latest/architecture/ci-trad) (see [diagram](https://docs.cloudbees.com/docs/cloudbees-ci/latest/architecture/_images/cloudbees-ci-traditional-arch.574b6fc.svg))
  * [Modern platform](https://docs.cloudbees.com/docs/cloudbees-ci/latest/architecture/ci-cloud) (see [diagram](https://docs.cloudbees.com/docs/cloudbees-ci/latest/architecture/_images/k8s-ci-architecture.31527cd.svg))

* Migrate your configuration and transit data (builds) from Jenkins to CloudBees CI

* Configuration

  * Extend your Declarative configuration from JCasc to Casc.
    * Traditional Platform: Operation Center and Controllers
    * Modern Platform: Operation Center and Controllers
  * `Enable Beekeeper` to guarantee plugin compatibility with the Jenkins core thanks to the [CloudBees Assurance Program](https://docs.cloudbees.com/docs/admin-resources/latest/assurance-program/).
  * Configure the Operation Center to share context within the CloudBees CI platform
    * Client/Managed Controllers
    * Controllers Casc Bundle Repository
    * Cluster Operations
    * Shared Agent Configuration
      * Shared Agent
      * Kubernetes Shared Cloud
    * Centrally managing security for controllers SSO
  * No Monolithic Jenkins
    * Security
    * RBAC
    * Credentials Management
    * Restricted Credentials
    * Signed Docker images
    * Signed Helm Charts
    * CyberArk plugin
    * Trigger restrictions (restrict which upstream jobs are allowed to trigger builds of other jobs)
  * Make the most of the Cloud
    * Configure autoscaling for EKS and GKE for elastic demands
    * Configure Hibernation to save computing cost
    * Hybrid Cloud workload (among different public clouds and on-premises)
      * Controllers
      * Agents
* Folder plus:
  * Define environment variables that are passed to the builds of all jobs within a folder
  * Display selected jobs from subfolders in a higher-level view
  * Restrict which agents each team has access to

* Move Copy Promote



* Developer productivity
  * Cross Team Collaboration
  * GitHub Apps: Receive and act upon granular, actionable build data directly in GitHub
  * Slack: Receive granular, actionable build data directly in Slack
  * Service Now: Create and manage ServiceNow change requests and incident tickets from your Pipeline

* Backup
* Monitoring
* Auditing

