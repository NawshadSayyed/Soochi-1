package Tax;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prathamesh.Authentication.R;

import java.util.ArrayList;

import Extra.RealmConfig;
import RealmClasses.Tax_List;
import RecyclerClass.taxRecycler;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.SyncConfiguration;

public class Taxlist extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taxlist);


        RealmConfig realmConfig = new RealmConfig();
        SyncConfiguration config = realmConfig.getConfig();

        RealmAsyncTask realm = Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                // Realm is ready



        // Stuff defined from Acitivity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fab);

        // Toolbar name given
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tax List");

        //Defining Floating Action Bar
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Taxlist.this);
                final LayoutInflater inflater = Taxlist.this.getLayoutInflater();
                ViewGroup nullParent = null;
                View custom_dialog = inflater.inflate(R.layout.dialog_new_tax, nullParent);

                EditText tax = (EditText) custom_dialog.findViewById(R.id.tax);


                builder.setPositiveButton(R.string.username, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        //Nothing for yet since this part is not working.
                    }

                })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Send the negative button event back to the host activity
                                dialog.dismiss();
                            }
                        });

                builder.setView(custom_dialog);
                AlertDialog doneBuild = builder.create();
                doneBuild.show();
                doneBuild.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // write check code
                        if (TextUtils.isEmpty(tax.getText().toString())) {
                            Toast.makeText(Taxlist.this, "Tax value should not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String tax_string = tax.getText().toString();

                        realm.beginTransaction();
                        Tax_List value = realm.createObject(Tax_List.class);
                        value.setTax(tax_string);
                        realm.commitTransaction();

                        // Upload will continue in the background even if we
                        // close the Realm immediately.


                        // if every thing is Ok then dismiss dialog
                        doneBuild.dismiss();
                        // Setting the newly added user to Party Customer


                    }
                });
            }
        });

        //Array values and pushing data into Database
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Taxlist.this);
        if (!prefs.getBoolean("firstTime", false)) {
            // Run it just once

            realm.beginTransaction();


            ArrayList<String> array = new ArrayList<>(21);
            array.add(0, "0% Exempted");
            array.add(1, "0% GST");
            array.add(2, "0% IGST");
            array.add(3, "0.125% GST");
            array.add(4, "0.125% IGST");
            array.add(5, "0.25% IGST");
            array.add(6, "1.5% GST");
            array.add(7, "1.5% IGST");
            array.add(8, "2.5% IGST");
            array.add(9, "2.5% GST");
            array.add(10, "3% IGST");
            array.add(11, "5% IGST");
            array.add(12, "6% GST");
            array.add(13, "6% IGST");
            array.add(14, "9% GST");
            array.add(15, "9% IGST");
            array.add(16, "12% IGST");
            array.add(17, "14% GST");
            array.add(18, "14% IGST");
            array.add(19, "18% IGST");
            array.add(20, "28% IGST");

            for (int i = 0; i < 21; i++) {
                Tax_List list = realm.createObject(Tax_List.class);
                list.setTax(array.get(i));
            }
            realm.commitTransaction();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }


       realm.beginTransaction();
        ArrayList<Tax_List> list_1 = new ArrayList<Tax_List>(realm.where(Tax_List.class).findAll());
        Log.d("taxlistdetails", " " + list_1.toString());
        RecyclerView username = (RecyclerView) findViewById(R.id.recyclerview_item);
        username.setHasFixedSize(true);
         realm.commitTransaction();
        LinearLayoutManager layoutManager = new LinearLayoutManager(Taxlist.this);
        username.setLayoutManager(layoutManager);

       taxRecycler mAdapter = new taxRecycler(list_1);
        username.setAdapter(mAdapter);
            }

            @Override
            public void onError(Throwable exception) {
                // Handle error
            }
        });
    }
}