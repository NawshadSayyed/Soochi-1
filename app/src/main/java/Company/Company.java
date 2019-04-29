package Company;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.prathamesh.Authentication.R;

import Extra.RealmConfig;
import RealmClasses.Company_details;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.SyncConfiguration;

public class Company extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company);

        EditText name = (EditText) findViewById(R.id.name);
        EditText email = (EditText) findViewById(R.id.email);
        EditText address = (EditText) findViewById(R.id.address);
        EditText gstin = (EditText) findViewById(R.id.gstin);
        EditText state = (EditText) findViewById(R.id.state);

        RealmConfig realmConfig = new RealmConfig();
        SyncConfiguration config = realmConfig.getConfig();

        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                // Realm is ready

                realm.beginTransaction();
                RealmResults<Company_details> results = realm.where(Company_details.class).findAll();
                results.get(0).setAddress(address.getText().toString());
                results.get(0).setEmail(email.getText().toString());
                results.get(0).setGstin(gstin.getText().toString());
                results.get(0).setState(state.getText().toString());
                
            }

            @Override
            public void onError(Throwable exception) {
                // Handle error
            }
        });


    }
}
