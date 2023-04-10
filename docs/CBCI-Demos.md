# CI Demos

<p align="center">
  <img alt="demos-icon" src="https://www.jenkins.io/images/logos/magician/magician.png" height="160" />
</p>

---

## Disaster Recovery

* Problem Statement

* Solution

## Capacity Planning. Load Stress Analysis

* Problem Statement

* Solution

The idea of the following demo is to create mock load to emulate 

* Internal Stress
  * Pipelines
    * Mock Build Plugin
    * Stash/Unstash
    * StressTool
  * Scaling the Load Controllers=> Using Helm charts to replicate controllers as we required https://github.com/carlosrodlop/K8s-lib/blob/v0.3.0/helm/charts/cb-ci-local/templates/oc-casc-bundle.yaml#L46-L63
  * Generating Build Storm ⇒ CloudBees Cross Team collaboration ⇒ 1 Job publish event, the rest of the controller consumes https://github.com/carlosrodlop/cb-casc-controllers/blob/main/modern.good-boy/items-root.yaml
* External Stress ⇒ Any application that share namespace with CloudBees CI or agents
Add overhead using Chaos Engineering with https://github.com/carlosrodlop/K8s-lib/tree/v0.3.0/helm/charts/chaos-local
Others Tools
https://litmuschaos.io/ ()
https://chaos-mesh.org/docs/production-installation-using-helm/

## Troubleshoot Jenkins Performance. Monitoring

* Problem Statement

* Solution
  * Internal
  * External



