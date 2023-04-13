# CloudBees CI Admin Hero

<p align="center">
  <img alt="admin-hero-icon" src="https://www.jenkins.io/images/logos/jenkins-is-the-way/jenkins-is-the-way.png" height="160" />
  <p align="center">This track is orientated to the CI <strong>Admin</strong> rol</p>
</p>

---

Inspired by [CloudBees CI feature comparison](https://docs.cloudbees.com/docs/cloudbees-ci/latest/feature-definition) and completed with my experience in the field and awesome content (from awesome people) public on the Internet.
Technology integration choices here cover the most common use cases based on the [Jenkins Stats](https://stats.jenkins.io/) but more integrations are available via plugins.

## Jenkins CI: Starting with a solid Open Source core

* What is Jenkins CI? [Jenkins (software) - Wikipedia](https://en.wikipedia.org/wiki/Jenkins_(software))
* Spot Jenkins inside the [CD Landscape Map](https://landscape.cd.foundation/)
* Get a first look at the Jenkins UI accessing the instance [jenkins.io](https://ci.jenkins.io/) as a Guest. Jenkins uses Jenkins for the CI of their plugins and core ("Dogfooding")

### Jenkins CI: Installation

* [Install Jenkins](https://www.jenkins.io/doc/book/installing/) in your desired platform following the recommendations explained in [Prepare Jenkins for Support](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/best-practices/prepare-jenkins-for-support)
* Always install the latest available version. It is a good practice to review [LTS Changelog](https://www.jenkins.io/changelog-stable/) to understand the new features and bug fixes (specifically for upgrades)
* Additional [Jenkins Features are controlled with System Properties](https://www.jenkins.io/doc/book/managing/system-properties/).

### Jenkins CI: Configuration

* Jenkins comes with a series of bundle plugins (required) but its capabilities can be extended via [Manage Plugins](https://www.jenkins.io/doc/book/managing/plugins/). There are more than 1800+ community-contributed plugins (see [Jenkins Plugins Index](https://plugins.jenkins.io/)).
  * ⚠️ Note that [Advanced installation](https://www.jenkins.io/doc/book/managing/plugins/#advanced-installation) does manage transitive dependencies requirements.
* Explore configure as code using [JCasc](https://github.com/jenkinsci/configuration-as-code-plugin), and pay special attention to the [Handling secrets](https://github.com/jenkinsci/configuration-as-code-plugin/blob/master/docs/features/secrets.adoc) and the [Exporting configuration tool](https://github.com/jenkinsci/configuration-as-code-plugin/blob/master/docs/features/configExport.md) sections.
* Prepare the instance to onboard developers:
  * 🔑 Add [Credentials](https://www.jenkins.io/doc/book/using/using-credentials/) to the Jenkins Internal Store to connect to your third-party systems (Security Realm, SCM, Artifactory Registry). Check out that you [understand different scopes](https://github.com/jenkinsci/credentials-plugin/blob/master/docs/user.adoc#credentials-scopes).
  * 🔒 Define Jenkins [Access Control](https://www.jenkins.io/doc/book/security/managing-security/#access-control). Among the different options, the most common setup would be:  
    * Configuring [LDAP](https://plugins.jenkins.io/ldap/) plugin for `Security Realm` (🍬 ensure [tuning](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/troubleshooting-guides/the-log-in-with-ldap-plugin-is-very-slow) is enabled)
    * Configuring [Matrix Authorization Strategy](https://plugins.jenkins.io/matrix-auth/) plugin as `Authorization Strategy` for your projects.
  * :octocat: Integrate with [GitHub](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/client-and-managed-masters/github-webhook-configuration) as SCM via WebHook (🍬 review additional [Best Practices for SCM](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/best-practices/scm-best-practices))
  * [Configure Agents](https://www.jenkins.io/doc/book/managing/nodes/#managing-nodes) to perform your builds (🍬 check [best practices](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/best-practices/remoting-best-practices)
    * Jenkins supports different types of OS (Windows, Linux and MacOS) and deployments [Static Agents](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/agents#static-agents) vs Cloud [Kubernetes Plugin](https://plugins.jenkins.io/kubernetes/)), including support for [Windows containers](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/agents#_setting_up_a_kubernetes_cluster_with_linux_and_windows_node_pools).
  * Integrate with an Artifact Registry like [Artifactory](https://plugins.jenkins.io/artifactory/) to store artifacts (build outcome) for Continuos Delivery or Release Orchestration
    * ⚠️ For intermediate artifacts to be used by others Jenkins builds (e.g. [archiveArtifacts](https://www.jenkins.io/doc/pipeline/steps/core/#archiveartifacts-archive-the-artifacts)), do not use `$JENKINS_HOME` but S3 compatible storage like [Artifact Manager on S3](https://plugins.jenkins.io/artifact-manager-s3/)
* Housekeeping: [Configure Global Build Discarders](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/best-practices/deleting-old-builds-best-strategy-for-cleanup-and-disk-space-management#_resolution) helps to reduce the **disk space** consumption on build data for jobs ([🎥 How to Clean up Old Jenkins Builds](https://www.youtube.com/watch?v=_Z7BlaTTGlo))
  * 🍬 Build data consume more Disk space and has higher IO rates that the rest of the elements outside the `$JENKINS_HOME`. Why do not place it a more appropiate disk according to its requirements outside the `$JENKINS_HOME` thanks to [jenkins.model.Jenkins.buildsDir](https://www.jenkins.io/doc/book/managing/system-properties/#jenkins-model-jenkins-buildsdir)?
  * 🍬 Jenkins outcomes that can be storage outside of Filesystem see [Pluggable Storage](https://www.jenkins.io/sigs/cloud-native/pluggable-storage/)

### Jenkins CI: Administration

* 📈 Integrate Jenkins with an external Monitoring solution like [🎥 Prometheus and Graphana](https://www.youtube.com/watch?v=3H9eNIf9KZs). (⚠️ Using [Monitoring plugin](https://plugins.jenkins.io/monitoring/) for production environment is not a good approach because Jenkins is being monitored inside Jenkins).
  * By default, the [Metrics](https://plugins.jenkins.io/metrics/) plugin exposes a set of metrics including  System and Java Virtual Machine metrics, Web UI metrics and Jenkins-specific metrics. Other plugins might add additional metrics like the [CloudBees Disk Usage Simple](https://plugins.jenkins.io/cloudbees-disk-usage-simple/)
  * Recommended resources to watch for performance: memory usage percentage, CPU usage percentage, JENKINS_HOME disk usage percentage, JENKINS_HOME IOPS, operations center and managed controller response time, Remaining build nodes capacity, Remaining master nodes capacity and Build/master nodes instances usage
  * 🍬 Grafana offers series of ready-built [dashboards for Jenkins](https://grafana.com/grafana/dashboards/?search=jenkins)
* 🔬 Audit Jenkins
  * [Audit Trail Plugin](https://plugins.jenkins.io/audit-trail/) adds an “Audit Trail” section in your Jenkins main configuration page, where it is possible to define where to save logs on who performed particular operations on Jenkins. (more info at [How does Audit Trail plugin work](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/client-and-managed-masters/how-does-audit-trail-plugin-work).
  * [Job Config History Plugin](https://plugins.jenkins.io/jobConfigHistory/) stores all the changes made to jobs (history), saving the config.xml of each job. For each change, it is possible to see the record of the change, compare the difference between the new and the old version and restore a previous version. It is also possible to keep track of the changes made to the system configuration. (⚠️ This plugin can become a performance killer if you do not follow the recommendations provided in [JobConfigHistory Plugin Best Practices](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/best-practices/jobconfighistory-best-practices))
* 💾 [Backup/Restore](https://www.jenkins.io/doc/book/system-administration/backing-up/)
  * [Thinbackup plugin](https://plugins.jenkins.io/thinBackup/) for process automation.
    * Backup storage: Directory/Mount point accessible from `$JENKINS_HOME`
    * Currently, it is no integrated in JCasc [JENKINS-53442](https://issues.jenkins.io/browse/JENKINS-53442)
* Operate remotely with Jenkins
  * [Jenkins CLI](https://www.jenkins.io/doc/book/managing/cli/) (🍬 If you use the Jenkins CLI tool regularly, [configure an alias](https://docs.cloudbees.com/docs/admin-resources/latest/cli-guide/config-alias) to avoid having to type the entire command each time).
    * `$JENKINS_URL/cli` contains up-to-date docs about remoting cli commands.
  * [REST API](https://www.jenkins.io/doc/book/using/remote-access-api/) (⚠️ Jenkins REST API should [never be used without the tree parameter](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/best-practices/best-practice-for-using-jenkins-rest-api)).
    * `$JENKINS_URL/api` contains up-to-date docs about remoting API REST.
* Automate management via Groovy code running into the [Script Console](https://www.jenkins.io/doc/book/managing/script-console/) (🍬 [Write Groovy scripts for Jenkins with code completion](https://www.mdoninger.de/2011/11/07/write-groovy-scripts-for-jenkins-with-code-completion.html))
  * Check API on [Jenkins Core and Plugins Javadoc](https://javadoc.jenkins.io/)
  * [Learn by examples](https://www.jenkins.io/doc/book/managing/script-console/#example-groovy-scripts)

### Jenkins CI: Support 🏥

* Find your answers within the [community](https://community.jenkins.io/) in different channels like [Stack Overflow](https://stackoverflow.com/questions/tagged/jenkins)
* If you are stuck, report your request or bug in the [Jenkins Jira](https://issues.jenkins.io/secure/Dashboard.jspa)

## CloudBees CI: Make Jenkins administration more scalable and reliable 🚀

### CloudBees CI: Installation and Platforms

* [Traditional platform](https://docs.cloudbees.com/docs/cloudbees-ci/latest/architecture/ci-trad) (see [diagram](https://docs.cloudbees.com/docs/cloudbees-ci/latest/architecture/_images/cloudbees-ci-traditional-arch.574b6fc.svg))
  * Make your CI builds **more resilient** by adding [High Availability](https://docs.cloudbees.com/docs/cloudbees-ci/latest/traditional-install-guide/high-availability) (see [diagram](https://docs.cloudbees.com/docs/cloudbees-ci/latest/traditional-install-guide/_images/ha-network-diagram.e8469d2.png)).
    * ⚠️ Ensure to meet the requirements from [NFS Guide](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/client-and-managed-masters/nfs-guide)
* [Modern platform](https://docs.cloudbees.com/docs/cloudbees-ci/latest/architecture/ci-cloud) (see [diagram](https://docs.cloudbees.com/docs/cloudbees-ci/latest/architecture/_images/k8s-ci-architecture.31527cd.svg))
  * CloudBees CI on Kubernetes additionally benefits from the robust container management of the Kubernetes control plane. Aside from the operations center and managed controllers running as `StatefulSets`, controllers use the Jenkins Kubernetes plugin to schedule builds on disposable agent pods, eliminating the need to explicitly manage worker infrastructure.
  * Make your CI build **more elastic** thanks to:
    * Configure autoscaling (for [example in EKS](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/eks-auto-scaling-nodes))
    * Configure [Hibernation on Controllers](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/managing-controllers#_hibernation_in_managed_masters) to save computing cost
    * Enable Hybrid Cloud workload (among different public clouds and on-premises)
      * [Controllers](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/provisioning-in-multiple-clusters)
      * [Agents](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/deploying-agents-separate-cluster)
* 🔒 Installations more secure with:
  * [Signed Docker images](https://docs.cloudbees.com/docs/cloudbees-ci/latest/kubernetes-install-guide/verifying-cloud-docker-images)
  * [Signed Helm Charts](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-secure-guide/helm-verification)
* Always install the latest version and review [CloudBees CI Release Notes](https://docs.cloudbees.com/docs/release-notes/latest/cloudbees-ci/) to understand the new features and bug fixes.

### CloudBees CI: Configuration

* If you come from Jenkins Open source, [Migrate](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/client-and-managed-masters/migrating-jenkins-instances) your configuration and transit data (builds) to CloudBees CI.
  * For this types of scenarios or any type of service maintenance, [CloudBees Quiet Start](https://docs.cloudbees.com/docs/admin-resources/latest/plugins/quiet-start) can help to not run all your builds immediately after restart.
* Extend your Declarative configuration from JCasc to Casc.
  * [Operation Center](https://docs.cloudbees.com/docs/cloudbees-ci/latest/casc-oc/)
  * [Controllers](https://docs.cloudbees.com/docs/cloudbees-ci/latest/casc-oc/)
* Operation Center: Central governance for your CloudBees CI Controllers with a Shared Context. It eases the scaling of your CI platform.
  * [Client Controllers](https://docs.cloudbees.com/docs/cloudbees-ci/latest/traditional-setup-guide/connecting-cms) / [Managed Controllers](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/managing-controllers).
    * [Slipt Monolithic Controllers](https://docs.cloudbees.com/docs/cloudbees-ci-migration/latest/splitting-controllers/) to a group of connected Controllers per Development Teams ( 🍬 check this video [From Big and Slow to Small and Agile: Splitting Monolithic Jenkins Controllers for Increased Performance](https://www.cloudbees.com/videos/splitting-monolithic-jenkins-controllers-for-increased-performance))
  * Use [Move/Copy/Promote](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/move-copy-promote) to disribute items across your Plataform.
  * [Cluster Operations](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/cluster-operations) perform maintenance operations on various items in operations center, such as Client/Managed Controllers.
  * Shared Agent Configuration
    * [Shared Agent](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/shared-agents) for static agents.
    * [Shared Cloud](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/pushed-clouds), including Kubernetes ([Globally editing pod templates in operations center](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/agents#_globally_editing_pod_templates_in_operations_center))
  * [Centrally managing security for controllers SSO](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-secure-guide/using-sso).
* Enterprise Grade Plugin Management, enable [Beekeeper Upgrade Assistant](https://docs.cloudbees.com/docs/admin-resources/latest/plugin-management/beekeeper-upgrade-assistant) to guarantee plugin compatibility with the Jenkins core version thanks to the [CloudBees Assurance Program (CAP)](https://docs.cloudbees.com/docs/admin-resources/latest/assurance-program/)
  * [Plugins in the CAP are categorized into three tiers](https://docs.cloudbees.com/search?type=ci-plugins&search=show), adding to Jenkins comunity plugin a set of Propietary plugins, when you are trying to determine if you should install a particular plugin, [choosing plugins that are part of CAP](https://docs.cloudbees.com/docs/admin-resources/latest/plugin-management/find-support-tier) (Tier 1 and Tier 2) provides the assurance of greater stability and security.
  * Have flexibility to override CAP on a plugin-by-plugin basis with [Beekeeper plugin exceptions](https://docs.cloudbees.com/docs/admin-resources/latest/plugin-management/beekeeper-exceptions)
  * Extend Beekeeper with plugins outside CAP (e.g. custom plugins) with the [Plugin Catalog](https://docs.cloudbees.com/docs/admin-resources/latest/plugin-management/configuring-plugin-catalogs)
* 🔒 Increase your Security
  * Adding roles to your authorization strategy using [RBAC](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-secure-guide/rbac)
  * Support for [Self-signed certificates in Kubernetes](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/kubernetes-self-signed-certificates)
  * 🔑 Credentials:
    * Restrictions with:
      * [Folders and RBAC](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/client-and-managed-masters/ssh-credentials-management-with-jenkins#_architecture_with_credential_management_in_folders)
      * [Restricted Credentials](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-secure-guide/restricted-credentials) lets you define restricted credentials with built-in access control using the full item names define in allowlists and denylists.
    * Adding [CyberArk plugin](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-secure-guide/cyberark) provider.
  * [Trigger restrictions](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/trigger-restrictions) restrict which upstream jobs are allowed to trigger builds of other jobs.
* [Folder plus](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-secure-guide/folders-plus) adds the following features to the Standard folder plugin: Define environment variables that are passed to the builds of all jobs within a folder, Display selected jobs from subfolders in a higher-level view, and Restrict which agents each team has access to.
* Developer productivity:
  * [Cross Team Collaboration](https://docs.cloudbees.com/docs/admin-resources/latest/pipelines/cross-team-collaboration) improves collaboration by connecting Pipelines on the same controller or different one. It allows a Pipeline to create a notification event that will be consumed by other Pipelines waiting on it to trigger a job.
  * :octocat: [GitHub Apps](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/github-app-auth): Receive and act upon granular, actionable build data directly in GitHub. [Unthrottling GitHub API usage](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/github-app-auth#_unthrottling_github_api_usage)
  * [CloudBees SCM Reporting](https://docs.cloudbees.com/docs/cloudbees-ci/latest/scm-integration/enabling-scm-reporting): Provides rich information beyond the standard GitHub or Bitbucket pass/fail status, Displays check for code coverage and test results directly in GitHub or Bitbucket, and Delivers detailed error and warning summaries.
  * 🔔 [Slack](https://docs.cloudbees.com/docs/cloudbees-ci/latest/slack-integration/slack-integration-intro): Receive granular, actionable build data directly in Slack
  * [Service Now](https://docs.cloudbees.com/docs/cloudbees-ci/latest/cloud-admin-guide/servicenow) Create and manage ServiceNow change requests and incident tickets from your Pipeline
  * New UI replacement for [Blue Ocean](https://www.jenkins.io/doc/book/blueocean/getting-started/): [CloudBees Pipeline Explorer Plugin](https://docs.cloudbees.com/docs/release-notes/latest/plugins/cloudbees-pipeline-explorer-plugin/).
* Housekeeping
  * [CloudBees Inactive Items plugin](https://docs.cloudbees.com/docs/admin-resources/latest/plugins/inactive-items): Identify **unused items** which are good candidate to be removed from the instance.
  * [CloudBees Usage plugin](https://docs.cloudbees.com/docs/admin-resources/latest/plugins/plugin-usage): Curated list of **plugins usage** at instance level. It is recommeded to only install plugin the instance required.

### CloudBees CI: Administration

* 📈 Monitoring, adding the [CloudBees Prometheus Metrics plugin](https://docs.cloudbees.com/docs/cloudbees-ci/latest/monitoring/prometheus-plugin) which exposes metrics securely for Operation Center. (Additionally, it can be used also with controllers)
* 🔬 Auditing, adding the [User Activity Monitoring plugin](https://docs.cloudbees.com/docs/admin-resources/latest/plugins/user-activity-monitoring) which provides you with a summary of user activity.
* 💾 [Backup/Restore](https://docs.cloudbees.com/docs/admin-resources/latest/backup-restore/)
  * [CloudBees Backup plugin](https://docs.cloudbees.com/docs/admin-resources/latest/backup-restore/cloudbees-backup-plugin) to automate the backup process.
    * It offers a more solid backup solution which is integrated in CasC and compatible with [multiple Storage types](https://docs.cloudbees.com/docs/admin-resources/latest/backup-restore/cloudbees-backup-plugin#where-to-back-up) (including [AWS S3](https://docs.cloudbees.com/docs/admin-resources/latest/backup-restore/cloudbees-backup-plugin#_amazon_s3) or other [Cloud Buckets S3-compatible](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/operations-center/overriding-aws-s3-endpoint-for-cluster-operation-backups))
    * It can be integrated with [Cluster Operation to take a backup](https://docs.cloudbees.com/docs/admin-resources/latest/backup-restore/backup-cluster) for every controller connected to the controller.
* Operate remotely with Jenkins
  * API REST
    * 🔒 [CloudBees Request Filter Plugin](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/client-and-managed-masters/how-do-i-use-the-cloudbees-request-filter-plugin) can block any specifict API endpoint request that has been identified like potential damage for the performance or security instance.
    * New API Endpoints are adding for [Casc](https://docs.cloudbees.com/docs/cloudbees-ci-api/latest/bundle-management-api) and [RBAC](https://docs.cloudbees.com/docs/cloudbees-ci-api/latest/rbac-api)

### CloudBees CI: Support 🏥

* From day zero you are not alone in this journey, [CloudBees Support](https://support.cloudbees.com/hc/en-us) counts on a Global Team of [Certified CloudBees CI/Jenkins experts](https://www.cloudbees.com/cloudbees-university/training-certifications) willing to answer your questions and help your processes including [Assisted updates](https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/required-data/required-data-upgrade-a-jenkins-instance)
  * [CloudBees plugin support policies](https://docs.cloudbees.com/docs/cloudbees-common/latest/plugin-support-policies) will cover Tier 1 and Tier 2 plugins.
  * Additionally, we include:
    * [Jenkins Health Advisor](https://plugins.jenkins.io/cloudbees-jenkins-advisor/)  to make sure your instance is not impacted by Known issues and meets with Best Practices
    * [Support CLI](https://docs.cloudbees.com/docs/cbsupport/latest/) to help you with data collection per topic.
