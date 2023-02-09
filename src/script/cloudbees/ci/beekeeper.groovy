package script.cloudbees.ci

import com.cloudbees.jenkins.plugins.assurance.CloudBeesAssurance
import com.cloudbees.jenkins.plugins.assurance.model.Beekeeper
import com.cloudbees.jenkins.cjp.installmanager.install.Config
import com.google.common.base.Function;

final CloudBeesAssurance cap = CloudBeesAssurance.get();
final Beekeeper beekeeper = cap.getBeekeeper();

//enable

if (beekeeper.isCap()) {

    cap.configure(new Function<Config, Config>() {
        @Override
        public Config apply(Config config) {
            return config.setCap(false);
        }
    });
}

//disable

if (!beekeeper.isCap()) {

    cap.configure(new Function<Config, Config>() {
        @Override
        public Config apply(Config config) {
            return config.setCap(true);
        }
    });
}
