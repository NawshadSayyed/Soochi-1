package Cash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.prathamesh.Authentication.R;

import java.util.ArrayList;

import Extra.AllParties;
import Extra.RealmConfig;
import RealmClasses.PurchaseInput;
import RealmClasses.Purchase_return;
import RealmClasses.Return_items_sale;
import RealmClasses.Returns_items_purchase;
import RealmClasses.SaleInput;
import RealmClasses.Sales_return;
import RecyclerClass.Cash_Recycler;
import RecyclerClass.Myadapter;
import io.realm.Realm;
import io.realm.SyncConfiguration;

public class Cash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_all_parties);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cash Details");

        //Setting up the realm
        Realm.init(Cash.this);

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
                                .equalTo("payType", "Cash")
                                .findAll());
                        Log.d("CashSale", String.valueOf(list_1.size()));

                        ArrayList<PurchaseInput> list_2 = new ArrayList<>(realm.where(PurchaseInput.class)
                                .equalTo("payType", "Cash")
                                .findAll());
                        ArrayList<Return_items_sale> list_3 = new ArrayList<>(realm.where(Return_items_sale.class)
                                .equalTo("payType", "Cash")
                                .findAll());
                        ArrayList<Returns_items_purchase> list_4 = new ArrayList<>(realm.where(Returns_items_purchase.class)
                                .equalTo("payType", "Cash")
                                .findAll());

                        // List of 1,2,3!
                        ArrayList list = new ArrayList();
                        list.addAll(list_1);

                        ArrayList list1 = new ArrayList();
                        list1.addAll(list_1);
                        list1.addAll(list_2);

                        ArrayList list2 = new ArrayList();
                        list2.addAll(list_1);
                        list2.addAll(list_2);
                        list2.addAll(list_3);


                        // List of respective types
                        ArrayList<PurchaseInput> list_5 = new ArrayList<>();
                        list_5.addAll(list);
                        list_5.addAll(list_2);

                        ArrayList<Return_items_sale> list_6 = new ArrayList<>();
                        list_6.addAll(list1);
                        list_6.addAll(list_3);

                        ArrayList<Returns_items_purchase> list_7 = new ArrayList<>();
                        list_7.addAll(list2);
                        list_7.addAll(list_4);




                        RecyclerView username = (RecyclerView) findViewById(R.id.textView10);
                        username.setHasFixedSize(true);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(Cash.this);
                        username.setLayoutManager(layoutManager);

                        Cash_Recycler mAdapter = new Cash_Recycler(list_1, list_5, list_6, list_7);

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



