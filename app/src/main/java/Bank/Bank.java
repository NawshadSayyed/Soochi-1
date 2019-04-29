package Bank;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prathamesh.Authentication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import Extra.RealmConfig;
import ProductMaster.Item;
import RealmClasses.Bank_realm;
import RealmClasses.Unit;
import RecyclerClass.Bank_recycler;
import RecyclerClass.Myadapter;
import Sale.Sale;
import io.realm.Realm;
import io.realm.SyncConfiguration;

public class Bank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bank Account List");

        Realm.init(Bank.this);

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
                                .findAll());

                        RecyclerView username = (RecyclerView) findViewById(R.id.textView10);
                        username.setHasFixedSize(true);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(Bank.this);
                        username.setLayoutManager(layoutManager);

                        Bank_recycler mAdapter = new Bank_recycler(list);

                        username.setAdapter(mAdapter);

                        //Calling Fab button
                         fab.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {

                                 final AlertDialog.Builder builder = new AlertDialog.Builder(Bank.this);
                                 final LayoutInflater inflater = Bank.this.getLayoutInflater();
                                 ViewGroup nullParent = null;

                                 View custom_dialog = inflater.inflate(R.layout.bank_details_dialog, nullParent);

                                 EditText name = (EditText) custom_dialog.findViewById(R.id.name);
                                 EditText bname = (EditText) custom_dialog.findViewById(R.id.bname);
                                 EditText no = (EditText) custom_dialog.findViewById(R.id.no);
                                 EditText balance = (EditText) custom_dialog.findViewById(R.id.balance);
                                 EditText date = (EditText) custom_dialog.findViewById(R.id.date);

                                 //Adding Date
                                 final Calendar myCalendar = Calendar.getInstance();
                                 DatePickerDialog.OnDateSetListener date_1 = new DatePickerDialog.OnDateSetListener() {

                                     @Override
                                     public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                           int dayOfMonth) {
                                         // TODO Auto-generated method stub
                                         myCalendar.set(Calendar.YEAR, year);
                                         myCalendar.set(Calendar.MONTH, monthOfYear);
                                         myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                         updateLabel();
                                     }

                                     private void updateLabel() {
                                         String myFormat = "MM/dd/yy"; //In which you need put here
                                         SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                                         date.setText(sdf.format(myCalendar.getTime()));

                                     }

                                 };


                                 date.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         new DatePickerDialog(custom_dialog.getContext(), date_1, myCalendar
                                                 .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                                 myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                                     }
                                 });


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
                                         if (TextUtils.isEmpty(name.getText().toString())) {
                                             Toast.makeText(Bank.this, "Name should not be empty", Toast.LENGTH_SHORT).show();
                                             return;
                                         }
                                         if (TextUtils.isEmpty(bname.getText().toString())) {
                                             Toast.makeText(Bank.this, "Name should not be empty", Toast.LENGTH_SHORT).show();
                                             return;
                                         }
                                         if (TextUtils.isEmpty(no.getText().toString())) {
                                             Toast.makeText(Bank.this, "Name should not be empty", Toast.LENGTH_SHORT).show();
                                             return;
                                         }
                                         if (TextUtils.isEmpty(balance.getText().toString())) {
                                             Toast.makeText(Bank.this, "Name should not be empty", Toast.LENGTH_SHORT).show();
                                             return;
                                         }
                                         if (TextUtils.isEmpty(date.getText().toString())) {
                                             Toast.makeText(Bank.this, "Name should not be empty", Toast.LENGTH_SHORT).show();
                                             return;
                                         }



                                         realm.beginTransaction();
                                         Bank_realm bank_realm = realm.createObject(Bank_realm.class);

                                         bank_realm.setName(name.getText().toString());
                                         bank_realm.setBname(bname.getText().toString());
                                         bank_realm.setBalance(balance.getText().toString());
                                         bank_realm.setNo(no.getText().toString());
                                         bank_realm.setDate(date.getText().toString());

                                         realm.commitTransaction();
                                         Toast.makeText(Bank.this, "Saved", Toast.LENGTH_SHORT).show();
                                         doneBuild.dismiss();
                                         startActivity(new Intent(Bank.this, Bank.class));



                                     }
                                 });

                             }
                         });



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

