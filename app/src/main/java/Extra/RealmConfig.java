package Extra;

import android.view.KeyEvent;

import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.SyncConfiguration;
import io.realm.SyncUser;

import static Extra.Constants.REALM_URL;

public class RealmConfig {

    // Realm Configuration


        SyncUser user = SyncUser.current();
        String url = REALM_URL + "/~/user";

        SyncConfiguration config = user.createConfiguration(url)
                .fullSynchronization()
                .waitForInitialRemoteData(30, TimeUnit.SECONDS)
                .build();


        public SyncConfiguration getConfig () {
        return config;
    }

}
