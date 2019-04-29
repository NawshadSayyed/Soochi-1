package ProductMaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.prathamesh.Authentication.R;

import java.util.ArrayList;

import Extra.RealmConfig;
import RecyclerClass.Myadapteritem;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import RealmClasses.ProductList;
import io.realm.SyncConfiguration;

public class ItemList extends AppCompatActivity {
    private static final String TAG = "ItemList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);
        Realm.init(ItemList.this);

        RealmConfig realmConfig = new RealmConfig();
        SyncConfiguration config = realmConfig.getConfig();

        RealmAsyncTask realm = Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                // Realm is ready


                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Item List");

                FloatingActionButton fab = findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(ItemList.this, Item.class);
                        startActivity(intent);
                    }
                });

                realm.beginTransaction();
                ArrayList<ProductList> list = new ArrayList<>(realm.where(ProductList.class).findAll());
                RecyclerView username = (RecyclerView) findViewById(R.id.recyclerview);
                username.setHasFixedSize(true);

                LinearLayoutManager layoutManager = new LinearLayoutManager(ItemList.this);
                username.setLayoutManager(layoutManager);

                Myadapteritem mAdapter = new Myadapteritem(list);

                username.setAdapter(mAdapter);
                Log.d(TAG, "Size -> " + list.size());
                realm.commitTransaction();
            }


            @Override
            public void onError(Throwable exception) {
                // Handle error
            }
        });


    }
}





