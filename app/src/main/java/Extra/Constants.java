package Extra;

import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncUser;

public final class Constants {
        /**
         * Realm Cloud Users:
         * Replace INSTANCE_ADDRESS with the hostname of your cloud instance
         * e.g., "mycoolapp.us1.cloud.realm.io"
         *
         * ROS On-Premises Users
         * Replace the INSTANCE_ADDRESS with the fully qualified version of
         * address of your ROS server, e.g.: INSTANCE_ADDRESS = "192.168.1.65:9080" and "http://" + INSTANCE_ADDRESS + "/auth"
         * (remember to use 'http/realm' instead of 'https/realms' if you didn't setup SSL on ROS yet)
         */
        private static final String INSTANCE__ADDRESS = "bapat97.us1a.cloud.realm.io";
        public static final String AUTH_URL = "https://" + INSTANCE__ADDRESS + "/auth";
        public static final String REALM_URL = "realms://" + INSTANCE__ADDRESS;


    }

