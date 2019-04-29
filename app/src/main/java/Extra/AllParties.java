package Extra;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.prathamesh.Authentication.R;

import java.util.ArrayList;

import RealmClasses.DetailsItemPurchase1;
import RealmClasses.DetailsItemSale1;
import RealmClasses.PurchaseInput;
import RealmClasses.SaleInput;
import RecyclerClass.Myadapter;
import Sale.Sale;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.SyncConfiguration;


public class AllParties extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_parties);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_all_parties);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Parties Details");


   final String TAG = "AllParties";

        //TextView Phonenumber = (TextView) findViewById(R.id.textView11);

        Realm.init(AllParties.this);

        RealmConfig realmConfig = new RealmConfig();
        SyncConfiguration config = realmConfig.getConfig();

        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                // Realm is ready


       realm.executeTransaction(new Realm.Transaction() {
           @Override
           public void execute(Realm realm) {


               ArrayList<SaleInput> list_1 = new ArrayList<>(realm.where(SaleInput.class)
                       .findAll());
               ArrayList<PurchaseInput> list_2 = new ArrayList<>(realm.where(PurchaseInput.class)
                       .findAll());

               ArrayList list = new ArrayList();
               list.addAll(list_1);

               ArrayList<PurchaseInput> list_3 = new ArrayList<>();
               list_3.addAll(list);
               list_3.addAll(list_2);

               RecyclerView username = (RecyclerView) findViewById(R.id.textView10);
               username.setHasFixedSize(true);

               LinearLayoutManager layoutManager = new LinearLayoutManager(AllParties.this);
               username.setLayoutManager(layoutManager);

               Myadapter mAdapter = new Myadapter(list_1, list_3);

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
