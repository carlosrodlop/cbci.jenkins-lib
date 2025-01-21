package script.cloudbees

import nectar.plugins.rbac.strategy.RoleMatrixAuthorizationPlugin;

RoleMatrixAuthorizationPlugin rbacPlugin = RoleMatrixAuthorizationPlugin.getInstance()
if (rbacPlugin != null) {
    rbacPlugin.reset()
} else {
    println 'RBAC is not configured as Security Realm'
}
