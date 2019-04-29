package ProductMaster;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prathamesh.Authentication.R;

import java.util.ArrayList;
import java.util.Arrays;

import Extra.RealmConfig;
import RealmClasses.ProductList;
import RealmClasses.Tax_List;
import RealmClasses.Unit;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.SyncConfiguration;

public class Item extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);

        EditText input1 = (EditText) findViewById(R.id.itemname);
        EditText input2 = (EditText) findViewById(R.id.barcode);
        EditText input3 = (EditText) findViewById(R.id.hsn);
        EditText input4 = (EditText) findViewById(R.id.sale);
        EditText input5 = (EditText) findViewById(R.id.purchase);
        AutoCompleteTextView input6 = (AutoCompleteTextView) findViewById(R.id.tax);
        AutoCompleteTextView input7 = (AutoCompleteTextView) findViewById(R.id.rate);
        AutoCompleteTextView input8 = (AutoCompleteTextView) findViewById(R.id.unit);
        Button button = (Button) findViewById(R.id.save);
        Button button1 = (Button) findViewById(R.id.cancel);
        final String[] data = new String[1];
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sale_1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Product");


        RealmConfig realmConfig = new RealmConfig();
        SyncConfiguration config = realmConfig.getConfig();

        RealmAsyncTask realm = Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                // Realm is ready


                Realm.init(Item.this);
                final String[] Customers = new String[]{"Inclusive tax", "Exclusive tax"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, Customers);
                input6.setAdapter(adapter);

                input6.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        input6.showDropDown();
                        return false;
                    }
                });

                // Tax List
                ArrayList<Tax_List> list = new ArrayList<Tax_List>(realm.where(Tax_List.class).findAllAsync());
                ArrayList<String> array = new ArrayList<>(list.size() + 1);
                for (int i = 0; i < list.size(); i++) {
                    array.add(i, list.get(i).getTax());
                }

                final String[] taxlist = array.toArray(new String[array.size()]);
                ArrayAdapter<String> adapter_1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, taxlist);

                input7.setAdapter(adapter_1);

                input7.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        input7.showDropDown();
                        return false;
                    }
                });

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        ArrayList<Unit> list = new ArrayList<Unit>(realm.where(Unit.class).findAllAsync());
                        ArrayList<String> array = new ArrayList<>(list.size() + 1);
                        array.add(0, "Add a new Unit");
                        for (int i = 1; i <= list.size(); i++) {
                            array.add(i, list.get(i - 1).getUnit());
                        }


                        final String[] Customers_2 = array.toArray(new String[array.size()]);
                        ArrayAdapter<String> adapter_2 = new ArrayAdapter<String>(Item.this, android.R.layout.simple_spinner_dropdown_item, Customers_2);
                        input8.setAdapter(adapter_2);

                        input8.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                input8.showDropDown();
                                return false;
                            }
                        });

                        // Dialog to add a new unit

                        input8.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                String buffer_position = (String) parent.getItemAtPosition(position);
                                int real_position = Arrays.asList(Customers_2).indexOf(buffer_position);
                                switch (real_position) {

                                    case 0: {

                                        final AlertDialog.Builder builder = new AlertDialog.Builder(Item.this);
                                        final LayoutInflater inflater = Item.this.getLayoutInflater();

                                        ViewGroup nullParent = null;

                                        builder.setTitle("Add a new unit");
                                        View custom_dialog = inflater.inflate(R.layout.newunit, nullParent);

                                        EditText name = (EditText) custom_dialog.findViewById(R.id.name);

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
                                                    Toast.makeText(Item.this, "Name should not be empty", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }

                                                realm.beginTransaction();
                                                Unit unit = realm.createObject(Unit.class);
                                                data[0] = name.getText().toString();
                                                unit.setUnit(data[0]);
                                                realm.commitTransaction();
                                                doneBuild.dismiss();
                                                input8.setText(data[0]);

                                            }
                                        });


                                    }
                                }
                            }
                        });
                    }
                });

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(input1.getText().toString())) {
                            Toast.makeText(Item.this, "Name should not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(input2.getText().toString())) {
                            Toast.makeText(Item.this, "Barcode should not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(input3.getText().toString())) {
                            Toast.makeText(Item.this, "HSN should not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(input4.getText().toString())) {
                            Toast.makeText(Item.this, "Sale price should not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(input5.getText().toString())) {
                            Toast.makeText(Item.this, "Sale price should not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(input6.getText().toString())) {
                            Toast.makeText(Item.this, "Tax type should not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(input7.getText().toString())) {
                            Toast.makeText(Item.this, "Tax rate should not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(input8.getText().toString())) {
                            Toast.makeText(Item.this, "Item unit should not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        } else {

                            realm.beginTransaction();
                            ProductList itemList = realm.createObject(ProductList.class);

                            String name = input1.getText().toString();
                            itemList.setName(name);
                            itemList.setRate(input7.getText().toString());
                            itemList.setType(input6.getText().toString());
                            itemList.setUnit(input8.getText().toString());
                            itemList.setBarcode(Integer.parseInt(input2.getText().toString()));
                            itemList.setHsn(Integer.parseInt(input3.getText().toString()));
                            itemList.setSale(Integer.parseInt(input4.getText().toString()));
                            itemList.setPurchase(Integer.parseInt(input5.getText().toString()));

                            Toast.makeText(Item.this, "Submitted", Toast.LENGTH_SHORT).show();
                            realm.commitTransaction();
                            Intent intent = new Intent(Item.this, ItemList.class);
                            startActivity(intent);

                        }
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