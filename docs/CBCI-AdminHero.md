# CloudBees CI Admin Hero

<p align="center">
  <img alt="terraform-icon" src="https://www.jenkins.io/images/logos/jenkins-is-the-way/jenkins-is-the-way.png" height="160" />
  <p align="center">This track is orientated to the CI <strong>Admin</strong> rol</p>
</p>

---

Inspired by [CloudBees CI feature comparison](https://docs.cloudbees.com/docs/cloudbees-ci/latest/feature-definition).

## Jenkins: Starting with the Open Source offering

* Get the latest Jenkins LTS Distribution
  * [Jenkins download and deployment](https://www.jenkins.io/download/)
  * Review [LTS Changelog](https://www.jenkins.io/changelog-stable/) to understand the new features and bug fixes.
* [Install Jenkins](https://www.jenkins.io/doc/book/installing/) in your desired platform following Best practices as explained in [Prepare Jenkins for Support](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/best-practices/prepare-jenkins-for-support)
  * Additional [Jenkins Features are controlled with System Properties](https://www.jenkins.io/doc/book/managing/system-properties/).
* Jenkins comes with a series of required plugins but its capabilities can be extended via [Manage Plugins](https://www.jenkins.io/doc/book/managing/plugins/).
* Explore how to configure Jenkins in a Declarative way using [JCasc](https://github.com/jenkinsci/configuration-as-code-plugin), and pay special attention to the [Handling secrets](https://github.com/jenkinsci/configuration-as-code-plugin/blob/master/docs/features/secrets.adoc) and the [Exporting configuration tool](https://github.com/jenkinsci/configuration-as-code-plugin/blob/master/docs/features/configExport.md) sections.
* Configure the instance to onboard developers
  * Add [Global Credentials](https://www.jenkins.io/doc/book/using/using-credentials/#adding-new-global-credentials) to the Jenkins Internal Store and [understand different scopes](https://github.com/jenkinsci/credentials-plugin/blob/master/docs/user.adoc#credentials-scopes).
  * Define the [Access Control](https://www.jenkins.io/doc/book/security/managing-security/#access-control) mechanisms and know the most common authentication systems:
    * [Active Directory](https://plugins.jenkins.io/active-directory/) ([troubleshooting](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/troubleshooting-guides/cannot-make-my-ad-configuration-to-work) and [tunning](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/troubleshooting-guides/the-log-in-with-ad-plugin-is-very-slow))
    * [LDAP](https://plugins.jenkins.io/ldap/) ([troubleshooting](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/troubleshooting-guides/cannot-make-my-ldap-configuration-to-work) and [tunning](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/troubleshooting-guides/the-log-in-with-ldap-plugin-is-very-slow))
    * [SAML](https://plugins.jenkins.io/saml/) ([troubleshooting](https://github.com/jenkinsci/saml-plugin/blob/main/doc/TROUBLESHOOTING.md))
  * Integrations with SCM via WebHook [GitHub](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/client-and-managed-masters/github-webhook-configuration) and [Bitbucket](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/client-and-managed-masters/bitbucket-integration)(Check additional [SCM Best Practices](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/best-practices/scm-best-practices)))
  * [Configure Agents](https://www.jenkins.io/doc/book/managing/nodes/#managing-nodes) to perform your builds (Avoid using Jenkins built-in node)
    * [Static Agents](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/agents#static-agents). Use [winsw](https://github.com/winsw/winsw) for Window inbound-agents.
    * Cloud [Kubernetes Plugin](https://plugins.jenkins.io/kubernetes/) ([troubleshooting](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/required-data/required-data-kubernetes-cloud))
      * Windows container support on AKS, EKS, and GKE
* Operate remotely with Jenkins
  * [Jenkins CLI](https://www.jenkins.io/doc/book/managing/cli/)
  * [REST API](https://www.jenkins.io/doc/book/using/remote-access-api/)
* Automate Management tasks via [Script Console](https://www.jenkins.io/doc/book/managing/script-console/)
  * [Groovy scripts examples repositories](https://github.com/stars/carlosrodlop/lists/jenkins-groovy-scripts)
  * [Write Groovy scripts for Jenkins with code completion](https://www.mdoninger.de/2011/11/07/write-groovy-scripts-for-jenkins-with-code-completion.html)

## CloudBees CI: Make Jenkins Administration more scalable and reliable

* Install CloudBees CI on [Traditional](https://docs.cloudbees.com/docs/cloudbees-ci/latest/traditional-install-guide/) or Modern for EKS, GKE or AKS.

* Ensure Beekeeper is enabled to guarantee plugin compatibility with the Jenkins core thanks to the [CloudBees Assurance Program](https://docs.cloudbees.com/docs/admin-resources/latest/assurance-program/).

* Declarative configuration for CloudBees
  * Traditional Platform: Operation Center and Controllers
  * Modern Platform: Operation Center and Controllers

* Operation Center: Shared Context for CI Controllers
  * Client/Managed Controllers
  * Controllers Casc Bundle Repository
  * Cluster Operations
  * Shared Agent Configuration
    * Shared Agent
    * Kubernetes Shared Cloud
  * Centrally managing security for controllers SSO

* Cloud platform
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

* Security
  * RBAC
  * Credentials Management
  * Restricted Credentials
  * Signed Docker images
  * Signed Helm Charts
  * CyberArk plugin
  * Trigger restrictions (restrict which upstream jobs are allowed to trigger builds of other jobs)

* Developer productivity
  * Cross Team Collaboration
  * GitHub Apps: Receive and act upon granular, actionable build data directly in GitHub
  * Slack: Receive granular, actionable build data directly in Slack
  * Service Now: Create and manage ServiceNow change requests and incident tickets from your Pipeline

* Backup
* Monitoring

* Get Support from the Jenkins experts
  * [Jenkins Health Advisor](https://plugins.jenkins.io/cloudbees-jenkins-advisor/)
  * [Support CLI](https://docs.cloudbees.com/docs/cbsupport/latest/) to collect data
  * [Assisted updates](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/required-data/required-data-upgrade-a-jenkins-instance)