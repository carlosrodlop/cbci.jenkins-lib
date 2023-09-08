# REST

## Alias (including auth)

Ref: https://docs.cloudbees.com/docs/admin-resources/latest/cli-guide/config-alias

```sh
alias jenkins-rest='/path/to/jenkins-rest.sh'
```

## Depth control

Control de mount of data with `tree` and `depth`

Ref: https://www.jenkins.io/doc/book/using/remote-access-api/#RemoteaccessAPI-Depthcontrol

## Crumb

Call to REST API including the .crumb in the same call

```sh
curl --user $USER:$APITOKEN -H $(curl --user $USER:$APITOKEN [-H "CRUB"] $SERVER/crumbIssuer/api/xml?xpath=concat\(//crumbRequestField,%22:%22,//crumb\))  $SERVER/job/hello-world-flow/build?token=codebase&cause=push
```

## Jobs

```sh
jenkins-rest --type -XGET --operation "api/json?pretty=true&depth=1"
```

## RBAC

### List groups

```sh
$ jenkins-rest --type -XGET --operation "groups/api/json?depth=1&tree=groups[name]"
{
_class: "nectar.plugins.rbac.groups.RootProxyGroupContainer",
groups: [
{
name: "administrators"
},
{
name: "users"
},
{
name: "viewers"
},
...
{
name: "fbelzunc-viewer"
}
]
}
```

### Create groups

```sh
jenkins-rest --type -XPOST --operation "groups/createGroup/api/json?name=developers"
```

## CasC

### List bundles

```sh
jenkins-rest --type -XGET --operation "casc-bundle/list"
```

### Regenerate token

```sh
jenkins-rest --type -XPOST --operation "casc-bundle/regenerate-token?bundleId=zd190212"
```

### Availability pattern

```sh
jenkins-rest --type -XPOST --operation "casc-bundle/set-availability-pattern?bundleId=Apps-1p&regex=seco-1252"
```
