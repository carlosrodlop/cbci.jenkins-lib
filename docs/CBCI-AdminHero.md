# CloudBees CI Admin Hero

<p align="center">
  <img alt="admin-hero-icon" src="https://www.jenkins.io/images/logos/jenkins-is-the-way/jenkins-is-the-way.png" height="160" />
  <p align="center">This track is orientated to the CI <strong>Admin</strong> rol</p>
</p>

---

This is a story inspired by [CloudBees CI feature comparison](https://docs.cloudbees.com/docs/cloudbees-ci/latest/feature-definition). Technology integration choices cover the most common use cases based on the [Jenkins Stats](https://stats.jenkins.io/) and my experience working with customers but you can extend to your technology stack thanks to the 1800+ community-contributed Jenkins plugins (see [Jenkins Plugins Index](https://plugins.jenkins.io/)).

## Jenkins CI: Starting with the Open Source offering

* What is Jenkins CI?
* [Jenkins (software) - Wikipedia](https://en.wikipedia.org/wiki/Jenkins_(software))
* Spot Jenkins inside the [CD Landscape Map](https://landscape.cd.foundation/)
* Get a first look at the UI accessing the instance [jenkins.io](https://ci.jenkins.io/) as a guest.

### Installation

* Always install the latest version and review [LTS Changelog](https://www.jenkins.io/changelog-stable/) to understand the new features and bug fixes.
* [Install Jenkins](https://www.jenkins.io/doc/book/installing/) in your desired platform following the recommendations explained in [Prepare Jenkins for Support](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/best-practices/prepare-jenkins-for-support)
* Additional [Jenkins Features are controlled with System Properties](https://www.jenkins.io/doc/book/managing/system-properties/).
* ℹ️ Note: Jenkins comes with a series of bundle plugins but its capabilities can be extended via [Manage Plugins](https://www.jenkins.io/doc/book/managing/plugins/).

### Configuration

* Explore configure as code using [JCasc](https://github.com/jenkinsci/configuration-as-code-plugin), and pay special attention to the [Handling secrets](https://github.com/jenkinsci/configuration-as-code-plugin/blob/master/docs/features/secrets.adoc) and the [Exporting configuration tool](https://github.com/jenkinsci/configuration-as-code-plugin/blob/master/docs/features/configExport.md) sections.
* Prepare the instance to onboard developers
  * 🔑 Add [Credentials](https://www.jenkins.io/doc/book/using/using-credentials/#adding-new-global-credentials) to the Jenkins Internal Store and [understand different scopes](https://github.com/jenkinsci/credentials-plugin/blob/master/docs/user.adoc#credentials-scopes).
  * 🔒 Define the [Access Control](https://www.jenkins.io/doc/book/security/managing-security/#access-control), which consists of:
    * Security Realm: Install and configure [LDAP](https://plugins.jenkins.io/ldap/) plugin ([troubleshooting](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/troubleshooting-guides/cannot-make-my-ldap-configuration-to-work) and [tuning](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/troubleshooting-guides/the-log-in-with-ldap-plugin-is-very-slow)).
    * Authorization: [Matrix Authorization Strategy](https://plugins.jenkins.io/matrix-auth/)
  * :octocat: Integrate with an SCM: [GitHub](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/client-and-managed-masters/github-webhook-configuration)](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/client-and-managed-masters/github-webhook-configuration) via WebHook and check additional [SCM Best Practices](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/best-practices/scm-best-practices)
  * 💻 [Configure Agents](https://www.jenkins.io/doc/book/managing/nodes/#managing-nodes) to perform your builds (Avoid using Jenkins built-in node)
    * [Static Agents](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/agents#static-agents). Use [winsw](https://github.com/winsw/winsw) for Windows inbound agents.
    * Cloud [Kubernetes Plugin](https://plugins.jenkins.io/kubernetes/) ([troubleshooting](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/required-data/required-data-kubernetes-cloud))
      * ℹ️ Support to [Windows containers](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/agents#_setting_up_a_kubernetes_cluster_with_linux_and_windows_node_pools)

### Management

* Operate remotely with Jenkins
  * [Jenkins CLI](https://www.jenkins.io/doc/book/managing/cli/)
  * [REST API](https://www.jenkins.io/doc/book/using/remote-access-api/)
* Automate management via Groovy code running into the [Script Console](https://www.jenkins.io/doc/book/managing/script-console/)
  * [Jenkins Core and Plugins Javadoc](https://javadoc.jenkins.io/)
  * [Write Groovy scripts for Jenkins with code completion](https://www.mdoninger.de/2011/11/07/write-groovy-scripts-for-jenkins-with-code-completion.html)
  * [Learn by examples](https://www.jenkins.io/doc/book/managing/script-console/#example-groovy-scripts)

### Support

* Find your answers within the [community](https://community.jenkins.io/) in different channels like [Stack Overflow](https://stackoverflow.com/questions/tagged/jenkins)
* If you are stuck, report your request or bug in the [Jenkins Jira](https://issues.jenkins.io/secure/Dashboard.jspa)

## CloudBees CI: Make Jenkins administration more scalable and reliable 🚀

### Installation. Platforms

* Always install the latest version and review [CloudBees CI Release Notes](https://docs.cloudbees.com/docs/release-notes/latest/cloudbees-ci/) to understand the new features and bug fixes.
* [Traditional platform](https://docs.cloudbees.com/docs/cloudbees-ci/latest/architecture/ci-trad) (see [diagram](https://docs.cloudbees.com/docs/cloudbees-ci/latest/architecture/_images/cloudbees-ci-traditional-arch.574b6fc.svg))
  * Make your CI builds **more resilient** by adding [High Availability](https://docs.cloudbees.com/docs/cloudbees-ci/latest/traditional-install-guide/high-availability) (see [diagram](https://docs.cloudbees.com/docs/cloudbees-ci/latest/traditional-install-guide/_images/ha-network-diagram.e8469d2.png))
* [Modern platform](https://docs.cloudbees.com/docs/cloudbees-ci/latest/architecture/ci-cloud) (see [diagram](https://docs.cloudbees.com/docs/cloudbees-ci/latest/architecture/_images/k8s-ci-architecture.31527cd.svg))
  * CloudBees CI on Kubernetes additionally benefits from the robust container management of the Kubernetes control plane. Aside from the operations center and managed controllers running as `StatefulSets`, controllers use the Jenkins Kubernetes plugin to schedule builds on disposable agent pods, eliminating the need to explicitly manage worker infrastructure.
  * Make your CI build **more elastic** thanks to:
    * Configure autoscaling (for [example in EKS](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/eks-auto-scaling-nodes))
    * Configure [Hibernation on Controllers](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/managing-controllers#_hibernation_in_managed_masters) to save computing cost
    * Enable Hybrid Cloud workload (among different public clouds and on-premises)
      * [Controllers](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/provisioning-in-multiple-clusters)
      * [Agents](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/deploying-agents-separate-cluster)
  
### Configuration

* If you come from Jenkins Open source, [Migrate](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/client-and-managed-masters/migrating-jenkins-instances) your configuration and transit data (builds) to CloudBees CI.
* Extend your Declarative configuration from JCasc to Casc.
  * Traditional Platform: Operation Center and Controllers
  * Modern Platform: Operation Center and Controllers
* `Enable Beekeeper` to guarantee plugin compatibility with the Jenkins core thanks to the [CloudBees Assurance Program](https://docs.cloudbees.com/docs/admin-resources/latest/assurance-program/).
* Configure the Operation Center to share context within the CloudBees CI platform
  * Client/Managed Controllers.
    * Slipt Monolithic Controllers to a group of connected Controllers per Development Team
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

### Support

* From day zero you are not alone in this journey, [CloudBees Support](https://support.cloudbees.com/hc/en-us) counts on a Global Team of [Certified CloudBees CI/Jenkins experts](https://www.cloudbees.com/cloudbees-university/training-certifications) willing to answer your questions and help your processes including [Assisted updates](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/required-data/required-data-upgrade-a-jenkins-instance)
  * [CloudBees plugin support policies](https://docs.cloudbees.com/docs/cloudbees-common/latest/plugin-support-policies) will cover Tier 1 and Tier 2 plugins.
  * Additionally, we include:
    * [Jenkins Health Advisor](https://plugins.jenkins.io/cloudbees-jenkins-advisor/) 🏥 to make sure your instance is not impacted by Known issues and meets with Best Practices
    * [Support CLI](https://docs.cloudbees.com/docs/cbsupport/latest/) to help you with data collection per topic.