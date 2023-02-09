# CLI

## Alias (incluying auth)

Ref: https://docs.cloudbees.com/docs/admin-resources/latest/cli-guide/config-alias

```sh
alias jenkins-cli='/path/to/jenkins-cli.sh'
```

## Adding Plugin catalog

Ref: https://docs.cloudbees.com/docs/admin-resources/latest/plugin-management/configuring-plugin-catalogs#adding-a-plugin-catalog-to-operation-center

```sh
$> jenkins-cli -webSocket plugin-catalog --put < plugin-catalog-file.json
{"id":"additional-funny-plugins","message":"Catalog updated correctly","status":"SUCCESS"}
```

## Creating new folder

```shell
jenkins-cli create-job folder-a/folder-b < newFolder.xml
```

Note: `folder-a` needs to be existing to create a subfolder from it

## RBAC

### List groups

```sh
$>  jenkins-cli list-groups root
administrators
users
...
viewer
```

## Migrate credentials to a different domain

Ref: https://docs.cloudbees.com/docs/cloudbees-ci/latest/cje1-to-ci/#migrate-credentials

Export from the Root

```sh
$>  jenkins-cli list-credentials-as-xml system::system::jenkins > credentials/credentials.xml
```

Import from the targetFolder

```sh
targetFolder="dse-team-na/carlosr"
jenkins-cli import-credentials-as-xml folder::item::${targetFolder} < credentials/credentials.xml
```

## CasC

```
jenkins-cli casc-bundle-set-controller -b test-casc-master -c carlosrodlop_mm
```

```
jenkins-cli -webSocket casc-bundle-set-controller -b "crodriguezlopez-carlosrodlop-1" -c "Teams/dse-team-na"
```

## Ref

- [Jenkins CLI](https://www.jenkins.io/doc/book/managing/cli/)
