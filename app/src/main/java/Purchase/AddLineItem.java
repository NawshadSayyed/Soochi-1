package Purchase;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.prathamesh.Authentication.R;

import java.util.ArrayList;
import java.util.Arrays;

import Extra.RealmConfig;
import RealmClasses.DetailsItemPurchase;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import ProductMaster.Item;
import RealmClasses.ProductList;
import io.realm.SyncConfiguration;

public class AddLineItem extends AppCompatActivity {


    int position;
    int key;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addlineitem);

        RealmConfig realmConfig = new RealmConfig();
        SyncConfiguration config = realmConfig.getConfig();

        RealmAsyncTask realm = Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                // Realm is ready



        // Key and position to edit the item list

        try {
            position = getIntent().getExtras().getInt("position");
            key = getIntent().getExtras().getInt("key");
        }catch(Exception e)
        {
            position = 0;
            key = 0;
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_item_line);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Line Item");

        AutoCompleteTextView input_1 = (AutoCompleteTextView) findViewById(R.id.product_1);
        EditText input_2 = (EditText) findViewById(R.id.quantity);
        EditText input_3 = (EditText) findViewById(R.id.rate);
        EditText input_4 = (EditText) findViewById(R.id.subtotal);
        EditText input_5 = (EditText) findViewById(R.id.taxpercent);
        EditText input_6 = (EditText) findViewById(R.id.totalamount);
        EditText input_7 = (EditText) findViewById(R.id.unitt);
        AutoCompleteTextView input8 = (AutoCompleteTextView) findViewById(R.id.taxx);
        Button button_1 = (Button) findViewById(R.id.save_1);
        Button button_2 = (Button) findViewById(R.id.cancel_1);

        Realm.init(AddLineItem.this);

        // Retrieving data. When it's called again to edit the related item
        if(key == 1) {

            ArrayList<DetailsItemPurchase> list_1 = new ArrayList<DetailsItemPurchase>(realm.where(DetailsItemPurchase.class).findAll());
            Log.d("yup", String.valueOf(list_1.size()));
            input_1.setText(list_1.get(position).getName());
            input_4.setText(list_1.get(position).getsubtotal());
            input_5.setText(list_1.get(position).getTaxvalue());
            input_2.setText(list_1.get(position).getQuantity());
            input_7.setText(list_1.get(position).getUnit());
            input8.setText(list_1.get(position).getTaxx());
            input_3.setText(list_1.get(position).getRate());
            input_6.setText(String.valueOf(Float.parseFloat(list_1.get(position).getsubtotal()) + Float.parseFloat(list_1.get(position).getTaxvalue())));

        }
        realm.beginTransaction();

        ArrayList<ProductList> list = new ArrayList<ProductList>(realm.where(ProductList.class).findAll());
        ArrayList<String> array = new ArrayList<>(list.size() + 1);
        array.add(0, "Add a new Product");
        for (int i = 1; i <= list.size(); i++) {
            array.add(i, list.get(i - 1).getName());
        }
        final String[] Customers = array.toArray(new String[array.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, Customers);
        realm.commitTransaction();

        input_1.setAdapter(adapter);

        input_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                input_1.showDropDown();
                return false;
            }
        });

        input_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String buffer_position = (String) parent.getItemAtPosition(position);
                int real_position = Arrays.asList(Customers).indexOf(buffer_position);

                if(real_position == 0) {
                    startActivity(new Intent(AddLineItem.this, Item.class));
                    AddLineItem.this.finish();
                }
                else if(real_position>=1) {
                    realm.beginTransaction();
                    ArrayList<ProductList> list = new ArrayList<>(realm.where(ProductList.class).findAll());
                    input_3.setText(String.valueOf(list.get(real_position-1).getSale()));
                    input_7.setText(list.get(real_position-1).getUnit());
                    input8.setText(list.get(real_position-1).getRate());

                    realm.commitTransaction();
                }


            }

        });



        input_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() != 0)
                {
                    //for sub total
                    String string  = input8.getText().toString();
                    String string_split [] = string.split("%");
                    String string_value = string_split[0];



                    //for tax percent
                    try {
                        input_4.setText(String.valueOf(Integer.parseInt(input_3.getText().toString()) * Integer.parseInt(input_2.getText().toString())));
                    }catch(Exception e){}
                    //continue for tax percent
                    try {
                        String answer = String.valueOf(Float.parseFloat(input_4.getText().toString()) * Float.parseFloat(string_value) / 100);
                        input_5.setText(answer);
                    }catch(Exception e){}

                    // total amount
                    try {
                        input_6.setText(String.valueOf(Float.parseFloat(input_4.getText().toString()) + Float.parseFloat(input_5.getText().toString())));
                    }catch (Exception e){}
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });


        int counter = 0;

        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(AddLineItem.this, purchase.class);
                intent.putExtra("one", input_4.getText().toString());
                intent.putExtra("two", input_5.getText().toString());
                intent.putExtra("three", input_1.getText().toString());
                intent.putExtra("four", input_2.getText().toString());
                intent.putExtra("five", input_7.getText().toString());
                intent.putExtra("six", input8.getText().toString());
                intent.putExtra("seven", input_3.getText().toString());

                intent.putExtra("key", key);

                intent.putExtra("edit_key", position);



                startActivity(intent);
                finish();
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

