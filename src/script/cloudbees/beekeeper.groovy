/* groovylint-disable CompileStatic, UnnecessaryGetter, UnnecessaryPublicModifier */
package script.cloudbees

import com.cloudbees.jenkins.plugins.assurance.CloudBeesAssurance
import com.cloudbees.jenkins.plugins.assurance.model.Beekeeper
import com.cloudbees.jenkins.cjp.installmanager.install.Config
import com.google.common.base.Function

final CloudBeesAssurance cap = CloudBeesAssurance.get()
final Beekeeper beekeeper = cap.getBeekeeper()

if (beekeeper.isCap()) {
    def configure = cap.configure(new Function<Config, Config>() {
        @Override
        public Config apply(Config config) {
            return config.setCap(false)
        }
    })
    configure
}
