# CI Discovery Sessions

1. Are you deploying your CI infrastructure on Modern (Kubernetes) or Traditional?
2. (Optional) In the case your platform is Traditional, do you use containers for Agents?
3. What is the technological stack of your Platform and Product(s)?
4. How do you package your product (ex: Maven artifacts, Docker images)?
5. How do you distribute your product (ex: Website or Google Play Store)?
6. Which SCM solution is implemented in your company (ex: Github or Bitbucket)?
7. Do you use GitOps principles in the company? Do you use any sort of Configuration as Code Technology?
8. CI Performance via [Dora Metrics](https://cloud.google.com/blog/products/devops-sre/using-the-four-keys-to-measure-your-devops-performance) [summary](https://storage.googleapis.com/gweb-cloudblog-publish/original_images/Calculating_the_metrics_frOhcbp.jpg)
   * What is your Job Build Frequency (ex: 100 builds per day)?
9. Backup/Disaster Recovery
   * Where is your Backup Storage (e.g: s3, FTP server)
   * Are you using Configuration as Code?
   * Disaster Recovery
     * Fault Tolerant Design: Primary and Secondary Zones/Regions ==> Guarantees the CI service resistance from full Zones/Regions outage but also a particular failure with a Network, Load Balancer, Machines/Nodes
     * Number of [Stateful vs Stateless applications](https://www.unixarena.com/2021/08/kubernetes-stateful-vs-stateless-applications.html/) ==> Stateful apps requires to be restore from Backup in the Disaster Recovery Regions vs 
     * How is addressed the Active/Passive Failover
       * Mapping DNA A record to the Primary/Secondary Load Balancer
       * Restore Process of the States
     * Metrics
       * Recovery Time Objective (RTO) is the duration of time and a service level within which a business process must be restored after a disaster to avoid unacceptable consequences associated with a break in continuity.
       * Recovery Point Objective (RPO) describes the interval of time that might pass during a disruption before the quantity of data lost during that period exceeds the Business Continuity Plan’s maximum allowable threshold or “tolerance.”