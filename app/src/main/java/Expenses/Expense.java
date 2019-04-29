package Expenses;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import android.widget.Toast;

import com.example.prathamesh.Authentication.R;

import java.util.ArrayList;
import java.util.List;

import Extra.RealmConfig;
import ProductMaster.ItemList;
import RealmClasses.User;
import RealmClasses.User_expense;
import RecyclerClass.ExpenseRecycler;
import RecyclerClass.MyadapterItemSale;
import Sale.Sale;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.SyncConfiguration;

public class Expense extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_list);

        RealmConfig realmConfig = new RealmConfig();
        SyncConfiguration config = realmConfig.getConfig();

        RealmAsyncTask realm = Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                // Realm is ready





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Expenses");

        // Adding a new expense party using Floating action button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Expense.this);
                final LayoutInflater inflater = Expense.this.getLayoutInflater();

                ViewGroup nullParent = null;
                View custom_dialog = inflater.inflate(R.layout.dialog_expense_list, nullParent);

                EditText name = (EditText) custom_dialog.findViewById(R.id.name);
                AutoCompleteTextView type = (AutoCompleteTextView) custom_dialog.findViewById(R.id.type);

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

                //Autocomplete Listener for type
                final String[] type_1 = new String[]{"Debit", "Credit"};
                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(custom_dialog.getContext(), android.R.layout.simple_spinner_dropdown_item, type_1);
                type.setAdapter(adapter1);

                type.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        type.showDropDown();
                        return false;
                    }
                });

                type.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus)
                            type.getId(); // Instead of your Toast
                    }
                });

                doneBuild.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // write check code
                        if (TextUtils.isEmpty(name.getText().toString())) {
                            Toast.makeText(Expense.this, "Expense name should not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(type.getText().toString())) {
                            Toast.makeText(Expense.this, "Expense type should not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(Expense.this, "Saved", Toast.LENGTH_SHORT).show();
                        String username_string = name.getText().toString();
                        String username_type = type.getText().toString();


                        realm.beginTransaction();
                        User_expense user = realm.createObject(User_expense.class);

                        user.setName(username_string);
                        user.setType(username_type);

                        realm.commitTransaction();
                        // if every thing is Ok then dismiss dialog
                        doneBuild.dismiss();
                      startActivity(new Intent(Expense.this, Expense.class));
                      finish();

                    }
               });
           }
     });
        // Pass the value to Recycler view everytime the activity is called upon.
        ArrayList<User_expense> list = new ArrayList<>(realm.where(User_expense.class).findAll());

        LinearLayoutManager layoutManager = new LinearLayoutManager(Expense.this);
        recyclerView.setLayoutManager(layoutManager);
        ExpenseRecycler mAdapter = new ExpenseRecycler(list);
        recyclerView.setAdapter(mAdapter);


     // To search from the list
     EditText searchView = (EditText) findViewById(R.id.searchView);
     searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                ArrayList<User_expense> temp = new ArrayList();
                for(User_expense d: list){
                    //or use .equal(text) with you want equal match
                    //use .toLowerCase() for better matches
                    if(d.getName().contains(s.toString())){
                        temp.add(d);
                    }
                }
                //update recyclerview
                mAdapter.updateList(temp);
                //you can use runnable postDelayed like 500 ms to delay search text
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

