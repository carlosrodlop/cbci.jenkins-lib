# CI Discovery

## Analysis

* Are you using containers? Docker Agents, Docker Controllers
* Architecture: CI Traditional vs Modern
  * For Modern - Cloud Provider (ex: EKS, GKE, AKS)?
  * For Traditional - Proxy, LoadBalancer
* DevOps Technological Stack? Which other 3rd party Systems will be integrated CI/CD with:
  * Standard CI Pipeline Definitions
    * SCM (ex: Github or Bitbucket)
    * Registries for Artifacts (ex: Artifactory, Nexus, S3) or Images (ex: DockerHub, ECR)
  * Security Configuration
    * Authorization (ex: LDAP)
    * External Credential Provider (ex: Vault) or Jenkins build-in credentials store
  * Monitoring Solutions (ex: Prometheus)
* Transport Layer Security (TLS) certificates, Are you using Self-signed certificates?
* How do you package your product (ex: Maven artifacts, Docker images)?
* How do you distribute your product (ex: Website or Google Play Store)?
* Agents Pools Requirements:
  * OS (ex: Windows vs Linux)
  * Computer Requirements (CPU, Memory)
  * Tooling (ex: Maven, Gradel)
* Do you apply GitOps principles in the company? Do you use any sort of Configuration as Code Technology?

## CI Quickstart

### Traditional

* Complete a new installation and configuration of CloudBees CI in the production environment with one (1) Operations Center and NN Client Controllers
* Demonstrate how to create and attach a Client Controller to the Operations Center
* Demonstrate how to configure HA
* Demonstrate usage of Configuration as Code for Traditional
* Configure Static Agents

### Modern

* Complete a new installation and configuration of CloudBees CI in the production environment with one (1) Operations Center and NN Managed Controllers
* Demonstrate how to create Managed Controller for the Operations Center
* Demonstrate usage of Configuration as Code for Modern
* Configure Pod templates in the Operation Center and Shared Libraries
  
### Common

* Configuration
  * CloudBees Jenkins Health Advisor
  * Demonstrate and guide on basic Role-Based Access Control
  * Demonstrate integration with support authentication services (such as Active Directory, LDAP, SAML)
  * Credentials Isolation
  * Housekeeping
    * CloudBees Inactive Items plugin
    * CloudBees Usage plugin
    * Configure Global Build Discarders
  * Integration (Optional - Depending on Customer tech stack)
    * GitHub App
    * Slack
* Administration
  * Backup/Restore
    * Demonstrate backup of Operations Center
    * Demonstrate backup of Client Master using a Cluster Operation
  * Auditing
    * Audit Trail Plugin
    * User Activity Monitoring plugin
* Pipelines
  * Demonstrate Pipeline, Multibranch, and Organization Folder project types
  * Demonstrate how to use Shared Libraries
  * Demonstrate configuration and usage of Pipeline Policies
  * Demonstrate configuration and usage of Cross Team Collaboration
  * Demonstrate configuration and usage of Pipeline Template Catalog

## CI Performance

* CI Performance via [Dora Metrics](https://cloud.google.com/blog/products/devops-sre/using-the-four-keys-to-measure-your-devops-performance) [summary](https://storage.googleapis.com/gweb-cloudblog-publish/original_images/Calculating_the_metrics_frOhcbp.jpg)
  * What is your Job Build Frequency (ex: 100 builds per day)?

## CI Backup/Disaster Recovery

* Where is your Backup Storage (e.g: s3, FTP server)
* Are you using Configuration as Code?
* Disaster Recovery
  * Fault Tolerant Design: Primary and Secondary Zones/Regions ==> Guarantees the CI service resistance from full Zones/Regions outage but also a particular failure with a Network, Load Balancer, Machines/Nodes
  * Number of [Stateful vs Stateless applications](https://www.unixarena.com/2021/08/kubernetes-stateful-vs-stateless-applications.html/) ==> Stateful apps requires to be restore from Backup in the Disaster Recovery Regions vs
  * How is addressed the Active/Passive Failover
    * Mapping DNA A record to the Primary/Secondary Load Balancer
    * Restore Process of the States
  * Expected Metrics values for
    * Recovery Time Objective (RTO) is the duration of time and a service level within which a business process must be restored after a disaster to avoid unacceptable consequences associated with a break in continuity.
    * Recovery Point Objective (RPO) describes the interval of time that might pass during a disruption before the quantity of data lost during that period exceeds the Business Continuity Plan’s maximum allowable threshold or “tolerance.”
