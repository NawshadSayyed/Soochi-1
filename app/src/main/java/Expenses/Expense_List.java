package Expenses;

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
import ProductMaster.Item;
import ProductMaster.ItemList;
import RealmClasses.ExpenseDetails;
import RealmClasses.ProductList;
import RecyclerClass.Expense_List_Recycler;
import RecyclerClass.Myadapteritem;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.SyncConfiguration;

public class Expense_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_paid_list);

        RealmConfig realmConfig = new RealmConfig();
        SyncConfiguration config = realmConfig.getConfig();
        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                // Realm is ready

                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Expense List");


                realm.beginTransaction();
                ArrayList<ExpenseDetails> list = new ArrayList<>(realm.where(ExpenseDetails.class).findAll());
                RecyclerView username = (RecyclerView) findViewById(R.id.recyclerview);
                username.setHasFixedSize(true);

                LinearLayoutManager layoutManager = new LinearLayoutManager(Expense_List.this);
                username.setLayoutManager(layoutManager);

                Expense_List_Recycler mAdapter = new Expense_List_Recycler(list);

                username.setAdapter(mAdapter);
                realm.commitTransaction();



            }

            @Override
            public void onError(Throwable exception) {
                // Handle error
            }
        });

    }
}

