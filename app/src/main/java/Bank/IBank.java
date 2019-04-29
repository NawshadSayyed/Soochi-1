package Bank;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.prathamesh.Authentication.R;

import java.util.ArrayList;

import Extra.RealmConfig;
import RealmClasses.Bank_realm;
import RecyclerClass.Bank_recycler;
import RecyclerClass.IBank_recycler;
import io.realm.Realm;
import io.realm.SyncConfiguration;

public class IBank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ibank);

      // Getting the value from the Bank Page
        String name = getIntent().getExtras().getString("key");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);

        Realm.init(IBank.this);

        RealmConfig realmConfig = new RealmConfig();
        SyncConfiguration config = realmConfig.getConfig();

        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                // Realm is ready


                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        ArrayList<Bank_realm> list = new ArrayList<>(realm.where(Bank_realm.class)
                                .contains("name", name)
                                .findAll());

                        RecyclerView username = (RecyclerView) findViewById(R.id.textView10);
                        username.setHasFixedSize(true);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(IBank.this);
                        username.setLayoutManager(layoutManager);

                        IBank_recycler mAdapter = new IBank_recycler(list);

                        username.setAdapter(mAdapter);
                    }
                });
            }

            @Override
            public void onError(Throwable exception) {
                // Handle error
            }

        });

    }
}