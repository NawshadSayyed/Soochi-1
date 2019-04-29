package Purchase;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prathamesh.Authentication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Extra.RealmConfig;
import MainActivity.MainActivity;
import RealmClasses.DetailsItemPurchase;
import RealmClasses.DetailsItemPurchase1;
import RealmClasses.DetailsItemSale;
import RealmClasses.PaymentType;
import RealmClasses.PurchaseInput;
import RealmClasses.SaleInput;
import RealmClasses.User_p;
import RealmClasses.finance_name;
import RecyclerClass.MyadapterItemPurchase;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.SyncConfiguration;
import io.realm.SyncUser;

import static Extra.Constants.REALM_URL;

public class purchase extends AppCompatActivity {

        @SuppressLint("ClickableViewAccessibility")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.purchase);



            final int[] position = {0};
            int position1;
            int key;

            // Edit_key for item

            RealmConfig realmConfig = new RealmConfig();
            SyncConfiguration config = realmConfig.getConfig();

            Realm.getInstanceAsync(config, new Realm.Callback() {
                @Override
                public void onSuccess(Realm realm) {
                    // Realm is ready

                    AutoCompleteTextView auto = (AutoCompleteTextView) findViewById(R.id.spinne);

                    HashMap<Integer, String> hashMap = new HashMap<>();

                    try {
                        Realm.init(purchase.this);
                    } catch (Exception e) {
                    }

                    String subtotal = "", tax_value = "", name = "", quantity = "", unit = "", taxx = "", rate = "";
                    int value = 0;

                    //To get the subtotal and tax value data from AddLineItem
                    try {


                        subtotal = getIntent().getExtras().getString("one");
                        tax_value = getIntent().getExtras().getString("two");
                        name = getIntent().getExtras().getString("three");
                        quantity = getIntent().getExtras().getString("four");
                        unit = getIntent().getExtras().getString("five");
                        taxx = getIntent().getExtras().getString("six");
                        rate = getIntent().getExtras().getString("seven");
                        value = getIntent().getExtras().getInt("key");

                        position[0] = getIntent().getExtras().getInt("edit_key");


                        //Adding the items in Realm database

                        realm.beginTransaction();
                        DetailsItemPurchase detailsItemPurchase = realm.createObject(DetailsItemPurchase.class);
                        detailsItemPurchase.setName(name);
                        detailsItemPurchase.setSubtotal(subtotal);
                        detailsItemPurchase.setTaxvalue(tax_value);
                        detailsItemPurchase.setQuantity(quantity);
                        detailsItemPurchase.setUnit(unit);
                        detailsItemPurchase.setTaxx(taxx);
                        detailsItemPurchase.setRate(rate);
                        //adding it's position
                        detailsItemPurchase.setPosition(position[0]);
                        realm.commitTransaction();

                        realm.beginTransaction();
                        DetailsItemPurchase1 detailsItemPurchase1 = realm.createObject(DetailsItemPurchase1.class);
                        detailsItemPurchase1.setName(name);
                        detailsItemPurchase1.setSubtotal(subtotal);
                        detailsItemPurchase1.setTaxvalue(tax_value);
                        detailsItemPurchase1.setQuantity(quantity);
                        detailsItemPurchase1.setUnit(unit);
                        detailsItemPurchase1.setTaxx(taxx);
                        detailsItemPurchase1.setRate(rate);
                        realm.commitTransaction();


                        //Editing at the specific  realm position


                    } catch (Exception e) {
                        value = 0;
                        Log.d("Error11", e.toString());
                    }


                    int finalValue = value;
                    EditText editText4 = (EditText) findViewById(R.id.editText);

                    if (finalValue == 0) {
                        // Adapter for showing the item is called with the help of recycler view!
                        ArrayList<DetailsItemPurchase> list_1 = new ArrayList<>(realm.where(DetailsItemPurchase.class).findAll());
                        RecyclerView username = (RecyclerView) findViewById(R.id.recyclerview_item);
                        username.setHasFixedSize(true);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(purchase.this);
                        username.setLayoutManager(layoutManager);

                        // Showing Qty Total ans Sub total


                        TextView Qtotal = (TextView) findViewById(R.id.totalquantity);
                        TextView Stotal = (TextView) findViewById(R.id.subtotal);

                        Stotal.setText(String.valueOf(list_1.size()));

                        float stotal = 0, sub = 0, tax = 0;
                        try {
                            for (int i = 0; i < list_1.size(); i++) {
                                sub = sub + Float.parseFloat(list_1.get(i).getsubtotal());
                                tax = tax + Float.parseFloat(list_1.get(i).getTaxvalue());
                                stotal = sub + tax;
                            }
                        } catch (Exception e) {
                        }
                        try {
                            editText4.setText(String.valueOf(stotal));
                        } catch (Exception e) {

                        }
                        MyadapterItemPurchase mAdapter = new MyadapterItemPurchase(list_1);
                        username.setAdapter(mAdapter);
                    }


                    AutoCompleteTextView auto_1 = (AutoCompleteTextView) findViewById(R.id.payment);
                    EditText reference = (EditText) findViewById(R.id.referenceNumber);

        /*auto_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() != 0) {

                    if (s.toString().equals("Cheque")) {

                        reference.setFocusableInTouchMode(true);
                    }
                    if (s.toString().equals("Cash")) {
                        reference.setFocusable(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });*/


                    // Ended


                    final Calendar myCalendar = Calendar.getInstance();

                    EditText edittext = (EditText) findViewById(R.id.date);
                    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

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

                            edittext.setText(sdf.format(myCalendar.getTime()));

                        }

                    };


                    edittext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new DatePickerDialog(purchase.this, date, myCalendar
                                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });


                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sale);
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setTitle("Purchase");

                    // Calling Add Item Activity
                    Button button_add_item = (Button) findViewById(R.id.addItem);
                    button_add_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(purchase.this, AddLineItem.class));
                            finish();
                        }
                    });
       /* SyncUser user = SyncUser.current();
        String url = "http://127.0.0.1:9080/";
        SyncConfiguration config = user.createConfiguration(url).build();*/

                    //autocompleteTextView

                    realm.beginTransaction();
                    ArrayList<User_p> list = new ArrayList<User_p>(realm.where(User_p.class).findAllAsync());
                    ArrayList<String> array = new ArrayList<>(list.size() + 1);
                    array.add(0, "Add a new Party");
                    for (int i = 1; i <= list.size(); i++) {
                        array.add(i, list.get(i - 1).getUsername());
                    }
                    final String[] Customers = array.toArray(new String[array.size()]);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, Customers);
                    realm.commitTransaction();

                    auto.setAdapter(adapter);

                    auto.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            auto.showDropDown();
                            return false;
                        }
                    });

                    auto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus)
                                auto.getId(); // Instead of your Toast
                        }
                    });


                    auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            String buffer_position = (String) parent.getItemAtPosition(position);
                            int real_position = Arrays.asList(Customers).indexOf(buffer_position);
                            switch (real_position) {

                                case 0: {
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(purchase.this);
                                    final LayoutInflater inflater = purchase.this.getLayoutInflater();

                                    ViewGroup nullParent = null;


                                    // Add action buttons


                                    View custom_dialog = inflater.inflate(R.layout.dialog_party, nullParent);

                                    EditText username = (EditText) custom_dialog.findViewById(R.id.username);
                                    EditText phone = (EditText) custom_dialog.findViewById(R.id.phone);

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
                                            if (TextUtils.isEmpty(username.getText().toString())) {
                                                Toast.makeText(purchase.this, "Name should not be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(phone.getText().toString())) {
                                                Toast.makeText(purchase.this, "Phone number should not be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }

                                            String username_string = username.getText().toString();
                                            String phone_string = phone.getText().toString();
                                            int phone_integer = Integer.parseInt(phone_string);

                                            realm.beginTransaction();
                                            User_p user = realm.createObject(User_p.class);

                                            user.setUsername(username_string);
                                            user.setPhonenumber(phone_integer);
                                            realm.commitTransaction();

                                            // Upload will continue in the background even if we
                                            // close the Realm immediately.


                                            // if every thing is Ok then dismiss dialog
                                            doneBuild.dismiss();
                                            // Setting the newly added user to Party Customer
                                            auto.setText(username_string);

                                        }
                                    });
                                }


                            }

                        }
                    });

                    // Payment type Auto-Complete-Listener
                    final String[] type = new String[]{"Cash", "Cheque", "Card", "RTGS", "NEFT", "Digital", "Credit", "Finance"};
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, type);
                    auto_1.setAdapter(adapter1);

                    auto_1.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            auto_1.showDropDown();
                            return false;
                        }
                    });

                    auto_1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus)
                                auto_1.getId(); // Instead of your Toast
                        }
                    });


                    auto_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            String buffer_position = (String) parent.getItemAtPosition(position);
                            int real_position = Arrays.asList(type).indexOf(buffer_position);


                            if (real_position >= 1 && real_position <= 7) {

                                final AlertDialog.Builder builder = new AlertDialog.Builder(purchase.this);
                                final LayoutInflater inflater = purchase.this.getLayoutInflater();

                                ViewGroup nullParent = null;


                                // Add action buttons


                                View custom_dialog = inflater.inflate(R.layout.dialog_payment_type, nullParent);

                                // Defining all the attributes

                                EditText camount = (EditText) custom_dialog.findViewById(R.id.camount);
                                EditText bamount = (EditText) custom_dialog.findViewById(R.id.bamount);
                                EditText amount = (EditText) custom_dialog.findViewById(R.id.amount);
                                EditText date = (EditText) custom_dialog.findViewById(R.id.date);
                                EditText number = (EditText) custom_dialog.findViewById(R.id.number);
                                EditText amount1 = (EditText) custom_dialog.findViewById(R.id.amount1);
                                EditText number1 = (EditText) custom_dialog.findViewById(R.id.number1);
                                EditText date1 = (EditText) custom_dialog.findViewById(R.id.date1);
                                EditText bank = (EditText) custom_dialog.findViewById(R.id.bank);
                                EditText bank1 = (EditText) custom_dialog.findViewById(R.id.bank1);
                                EditText days = (EditText) custom_dialog.findViewById(R.id.days);
                                EditText start = (EditText) custom_dialog.findViewById(R.id.start);
                                EditText end = (EditText) custom_dialog.findViewById(R.id.end);
                                AutoCompleteTextView fname = (AutoCompleteTextView) custom_dialog.findViewById(R.id.fname);
                                EditText fexc = (EditText) custom_dialog.findViewById(R.id.fexc);
                                EditText fagree = (EditText) custom_dialog.findViewById(R.id.fagree);
                                EditText fdpay = (EditText) custom_dialog.findViewById(R.id.fdpay);
                                EditText famount = (EditText) custom_dialog.findViewById(R.id.famount);
                                EditText fproch = (EditText) custom_dialog.findViewById(R.id.fproch);
                                EditText femi = (EditText) custom_dialog.findViewById(R.id.femi);
                                EditText femiamount = (EditText) custom_dialog.findViewById(R.id.femiamount);


                                //Adding Date

                                final Calendar myCalendar_1 = Calendar.getInstance();

                                DatePickerDialog.OnDateSetListener date_1 = new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                          int dayOfMonth) {
                                        // TODO Auto-generated method stub
                                        myCalendar_1.set(Calendar.YEAR, year);
                                        myCalendar_1.set(Calendar.MONTH, monthOfYear);
                                        myCalendar_1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                        Log.d("datepicker", " " + view.toString());
                                        updateLabel();
                                    }

                                    private void updateLabel() {
                                        String myFormat = "MM/dd/yy"; //In which you need put here
                                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                                        date.setText(sdf.format(myCalendar_1.getTime()));

                                    }
                                };

                                DatePickerDialog.OnDateSetListener date_2 = new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                          int dayOfMonth) {
                                        // TODO Auto-generated method stub
                                        myCalendar_1.set(Calendar.YEAR, year);
                                        myCalendar_1.set(Calendar.MONTH, monthOfYear);
                                        myCalendar_1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                        Log.d("datepicker", " " + view.toString());
                                        updateLabel();
                                    }

                                    private void updateLabel() {
                                        String myFormat = "MM/dd/yy"; //In which you need put here
                                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


                                        date1.setText(sdf.format(myCalendar_1.getTime()));


                                    }
                                };


                                DatePickerDialog.OnDateSetListener date_3 = new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                          int dayOfMonth) {
                                        // TODO Auto-generated method stub
                                        myCalendar_1.set(Calendar.YEAR, year);
                                        myCalendar_1.set(Calendar.MONTH, monthOfYear);
                                        myCalendar_1.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                        updateLabel();
                                    }

                                    private void updateLabel() {
                                        String myFormat = "MM/dd/yy"; //In which you need put here
                                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                                        start.setText(sdf.format(myCalendar_1.getTime()));


                                    }
                                };


                                DatePickerDialog.OnDateSetListener date_4 = new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                          int dayOfMonth) {
                                        // TODO Auto-generated method stub
                                        myCalendar_1.set(Calendar.YEAR, year);
                                        myCalendar_1.set(Calendar.MONTH, monthOfYear);
                                        myCalendar_1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                        Log.d("datepicker", " " + view.toString());
                                        updateLabel();
                                    }

                                    private void updateLabel() {
                                        String myFormat = "MM/dd/yy"; //In which you need put here
                                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


                                        end.setText(sdf.format(myCalendar_1.getTime()));

                                    }
                                };


                                date.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        DatePickerDialog x = new DatePickerDialog(custom_dialog.getContext(), date_1, myCalendar_1
                                                .get(Calendar.YEAR), myCalendar_1.get(Calendar.MONTH),
                                                myCalendar_1.get(Calendar.DAY_OF_MONTH));

                                        if (!x.isShowing()) {
                                            x.show();
                                        }


                                    }
                                });

                                date1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        new DatePickerDialog(purchase.this, date_2, myCalendar_1
                                                .get(Calendar.YEAR), myCalendar_1.get(Calendar.MONTH),
                                                myCalendar_1.get(Calendar.DAY_OF_MONTH)).show();
                                    }
                                });

                                start.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        new DatePickerDialog(purchase.this, date_3, myCalendar_1
                                                .get(Calendar.YEAR), myCalendar_1.get(Calendar.MONTH),
                                                myCalendar_1.get(Calendar.DAY_OF_MONTH)).show();
                                    }
                                });

                                end.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        new DatePickerDialog(purchase.this, date_4, myCalendar_1
                                                .get(Calendar.YEAR), myCalendar_1.get(Calendar.MONTH),
                                                myCalendar_1.get(Calendar.DAY_OF_MONTH)).show();
                                    }
                                });

                                // Difference in dates in Cerdit Payment Option


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
                                builder.setTitle(buffer_position);
                                AlertDialog doneBuild = builder.create();
                                doneBuild.show();

                                // Hiding the unnecesarry edit texts
                                if (real_position == 2 || real_position == 3 || real_position == 4) {

                                    bamount.setVisibility(View.GONE);
                                    days.setVisibility(View.GONE);
                                    start.setVisibility(View.GONE);
                                    end.setVisibility(View.GONE);
                                    amount1.setVisibility(View.GONE);
                                    number1.setVisibility(View.GONE);
                                    date1.setVisibility(View.GONE);
                                    bank1.setVisibility(View.GONE);
                                    fname.setVisibility(View.GONE);
                                    fexc.setVisibility(View.GONE);
                                    fagree.setVisibility(View.GONE);
                                    fdpay.setVisibility(View.GONE);
                                    famount.setVisibility(View.GONE);
                                    fproch.setVisibility(View.GONE);
                                    femi.setVisibility(View.GONE);
                                    femiamount.setVisibility(View.GONE);

                                } else if (real_position == 1 || real_position == 5) {
                                    bamount.setVisibility(View.GONE);
                                    days.setVisibility(View.GONE);
                                    start.setVisibility(View.GONE);
                                    end.setVisibility(View.GONE);
                                    fname.setVisibility(View.GONE);
                                    fexc.setVisibility(View.GONE);
                                    fagree.setVisibility(View.GONE);
                                    fdpay.setVisibility(View.GONE);
                                    famount.setVisibility(View.GONE);
                                    fproch.setVisibility(View.GONE);
                                    femi.setVisibility(View.GONE);
                                    femiamount.setVisibility(View.GONE);
                                } else if (real_position == 6) {
                                    bamount.setVisibility(View.GONE);
                                    fname.setVisibility(View.GONE);
                                    fexc.setVisibility(View.GONE);
                                    fagree.setVisibility(View.GONE);
                                    fdpay.setVisibility(View.GONE);
                                    famount.setVisibility(View.GONE);
                                    fproch.setVisibility(View.GONE);
                                    femi.setVisibility(View.GONE);
                                    femiamount.setVisibility(View.GONE);
                                } else if (real_position == 7) {
                                    bamount.setVisibility(View.GONE);
                                    days.setVisibility(View.GONE);
                                    start.setVisibility(View.GONE);
                                    end.setVisibility(View.GONE);
                                    amount1.setVisibility(View.GONE);
                                    number1.setVisibility(View.GONE);
                                    date1.setVisibility(View.GONE);
                                    bank1.setVisibility(View.GONE);
                                    amount.setVisibility(View.GONE);
                                    number.setVisibility(View.GONE);
                                    date.setVisibility(View.GONE);
                                    bank.setVisibility(View.GONE);
                                    camount.setVisibility(View.GONE);

                                    // Finance Company Name (Realm + DialogBox)

                                    realm.beginTransaction();
                                    ArrayList<finance_name> list_1 = new ArrayList<finance_name>(realm.where(finance_name.class).findAll());
                                    ArrayList<String> array = new ArrayList<>(list_1.size() + 1);
                                    array.add(0, "Add a new Finance Company ");
                                    for (int i = 1; i <= list_1.size(); i++) {
                                        array.add(i, list_1.get(i - 1).getName());
                                    }

                                    final String[] finance_list = array.toArray(new String[array.size()]);
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(custom_dialog.getContext(), android.R.layout.simple_spinner_dropdown_item, finance_list);
                                    realm.commitTransaction();

                                    fname.setAdapter(adapter);


                                    fname.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            fname.showDropDown();
                                            return false;
                                        }
                                    });

                                    fname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                        @Override
                                        public void onFocusChange(View v, boolean hasFocus) {
                                            if (hasFocus)
                                                fname.getId(); // Instead of your Toast
                                        }
                                    });

                                    fname.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            String buffer_position = (String) parent.getItemAtPosition(position);
                                            int real_position = Arrays.asList(finance_list).indexOf(buffer_position);
                                            switch (real_position) {

                                                case 0: {
                                                    final AlertDialog.Builder builder = new AlertDialog.Builder(purchase.this);
                                                    final LayoutInflater inflater = purchase.this.getLayoutInflater();

                                                    ViewGroup nullParent = null;


                                                    // Add action buttons


                                                    View custom_dialog = inflater.inflate(R.layout.financecompany_dialog, nullParent);

                                                    EditText name = (EditText) custom_dialog.findViewById(R.id.finance_name);

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
                                                                Toast.makeText(purchase.this, "Name should not be empty", Toast.LENGTH_SHORT).show();
                                                                return;
                                                            }

                                                            String name_string = name.getText().toString();

                                                            realm.beginTransaction();
                                                            finance_name fin_name = realm.createObject(finance_name.class);
                                                            fin_name.setName(name_string);
                                                            realm.commitTransaction();

                                                            // Upload will continue in the background even if we
                                                            // close the Realm immediately.

                                                            // if every thing is Ok then dismiss dialog
                                                            doneBuild.dismiss();
                                                            // Setting the newly added user to Party Customer
                                                            fname.setText(name_string);


                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });

                                    // Tenure days

                                    String s1 = start.getText().toString();
                                    end.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                            if (s.length() != 0) {
                                                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy", Locale.US);
                                                Date start2 = null;
                                                Date end2 = null;

                                                try {
                                                    start2 = formatter.parse(s1.toString());
                                                    end2 = formatter.parse(end.getText().toString());
                                                    long diff = start2.getTime() - end2.getTime();
                                                    long day = diff / 86400000;
                                                    days.setText(String.valueOf(day));

                                                    Log.d("errorrr", " " + days.getText().toString());
                                                } catch (Exception e) {

                                                }

                                            }
                                        }


                                        @Override
                                        public void afterTextChanged(Editable s) {

                                        }
                                    });

                                    start.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                            if (s.length() != 0) {
                                                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy", Locale.US);
                                                Date start2 = null;
                                                Date end2 = null;

                                                try {
                                                    start2 = formatter.parse(s1.toString());
                                                    end2 = formatter.parse(end.getText().toString());
                                                    long diff = start2.getTime() - end2.getTime();
                                                    long day = diff / 86400000;
                                                    days.setText(String.valueOf(day));

                                                } catch (Exception e) {
                                                }

                                            }
                                        }


                                        @Override
                                        public void afterTextChanged(Editable s) {

                                        }
                                    });


                                    //Adjusting the amount
                                    femi.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                            if (s.length() != 0) {
                                                try {
                                                    float amount2 = Float.valueOf(famount.getText().toString()) + Float.valueOf(fproch.getText().toString()) - Float.valueOf(fdpay.getText().toString());
                                                    float amount3 = amount2 / Float.valueOf(femi.getText().toString());
                                                    femiamount.setText(String.valueOf(amount3));
                                                } catch (Exception e) {
                                                    femiamount.setText(String.valueOf(0));
                                                }
                                            }
                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                        }


                                    });
                                    famount.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                            if (s.length() != 0) {
                                                try {
                                                    float amount2 = Float.valueOf(famount.getText().toString()) + Float.valueOf(fproch.getText().toString()) - Float.valueOf(fdpay.getText().toString());
                                                    float amount3 = amount2 / Float.valueOf(femi.getText().toString());
                                                    femiamount.setText(String.valueOf(amount3));
                                                } catch (Exception e) {
                                                    femiamount.setText(String.valueOf(0));
                                                }
                                            }
                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                        }


                                    });
                                    fproch.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                            if (s.length() != 0) {
                                                try {
                                                    float amount2 = Float.valueOf(famount.getText().toString()) + Float.valueOf(fproch.getText().toString()) - Float.valueOf(fdpay.getText().toString());
                                                    float amount3 = amount2 / Float.valueOf(femi.getText().toString());
                                                    femiamount.setText(String.valueOf(amount3));
                                                } catch (Exception e) {
                                                    femiamount.setText(String.valueOf(0));
                                                }
                                            }
                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                        }


                                    });
                                }

                                doneBuild.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (real_position == 2 || real_position == 3 || real_position == 4) {
                                            if (TextUtils.isEmpty(camount.getText().toString())) {
                                                Toast.makeText(purchase.this, "cash amount cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(amount.getText().toString())) {
                                                Toast.makeText(purchase.this, "Amount cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(date.getText().toString())) {
                                                Toast.makeText(purchase.this, "Date cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(number.getText().toString())) {
                                                Toast.makeText(purchase.this, "Number cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(bank.getText().toString())) {
                                                Toast.makeText(purchase.this, "Bank.Bank name cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }

                                            realm.beginTransaction();
                                            PaymentType paymentType = realm.createObject(PaymentType.class);
                                            paymentType.setCamount(camount.getText().toString());
                                            paymentType.setAmount(amount.getText().toString());
                                            paymentType.setDate(date.getText().toString());
                                            paymentType.setNumber(number.getText().toString());
                                            paymentType.setBank(bank.getText().toString());
                                            realm.commitTransaction();

                                        } else if (real_position == 1 || real_position == 5) {
                                            if (TextUtils.isEmpty(camount.getText().toString())) {
                                                Toast.makeText(purchase.this, "cash amount cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(amount.getText().toString())) {
                                                Toast.makeText(purchase.this, "Amount cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(date.getText().toString())) {
                                                Toast.makeText(purchase.this, "Date cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(number.getText().toString())) {
                                                Toast.makeText(purchase.this, "Number cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(bank.getText().toString())) {
                                                Toast.makeText(purchase.this, "Bank.Bank name cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            realm.beginTransaction();
                                            PaymentType paymentType = realm.createObject(PaymentType.class);
                                            paymentType.setCamount(camount.getText().toString());
                                            paymentType.setAmount(amount.getText().toString());
                                            paymentType.setDate(date.getText().toString());
                                            paymentType.setNumber(number.getText().toString());
                                            paymentType.setBank(bank.getText().toString());
                                            try {
                                                paymentType.setAmount1(amount1.getText().toString());
                                                paymentType.setDate1(date1.getText().toString());
                                                paymentType.setNumber1(number1.getText().toString());
                                                paymentType.setBank1(bank1.getText().toString());
                                            } catch (Exception e) {
                                            }
                                            realm.commitTransaction();

                                        } else if (real_position == 6) {
                                            if (TextUtils.isEmpty(camount.getText().toString())) {
                                                Toast.makeText(purchase.this, "cash amount cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(amount.getText().toString())) {
                                                Toast.makeText(purchase.this, "Amount cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(date.getText().toString())) {
                                                Toast.makeText(purchase.this, "Date cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(number.getText().toString())) {
                                                Toast.makeText(purchase.this, "Number cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(bank.getText().toString())) {
                                                Toast.makeText(purchase.this, "Bank.Bank name cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(days.getText().toString())) {
                                                Toast.makeText(purchase.this, "Days cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(start.getText().toString())) {
                                                Toast.makeText(purchase.this, "Start cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(end.getText().toString())) {
                                                Toast.makeText(purchase.this, "End cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }

                                            realm.beginTransaction();
                                            PaymentType paymentType = realm.createObject(PaymentType.class);
                                            paymentType.setCamount(camount.getText().toString());
                                            paymentType.setAmount(amount.getText().toString());
                                            paymentType.setDate(date.getText().toString());
                                            paymentType.setNumber(number.getText().toString());
                                            paymentType.setBank(bank.getText().toString());
                                            paymentType.setDays(days.getText().toString());
                                            paymentType.setStart(start.getText().toString());
                                            paymentType.setEnd(end.getText().toString());
                                            try {
                                                paymentType.setAmount1(amount1.getText().toString());
                                                paymentType.setDate1(date1.getText().toString());
                                                paymentType.setNumber1(number1.getText().toString());
                                                paymentType.setBank1(bank1.getText().toString());
                                            } catch (Exception e) {
                                            }
                                            realm.commitTransaction();
                                        } else if (real_position == 7) {
                                            if (TextUtils.isEmpty(fname.getText().toString())) {
                                                Toast.makeText(purchase.this, "Company amount cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(fexc.getText().toString())) {
                                                Toast.makeText(purchase.this, "Executive cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(fagree.getText().toString())) {
                                                Toast.makeText(purchase.this, "Agreement cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(fdpay.getText().toString())) {
                                                Toast.makeText(purchase.this, "Down Payment cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(famount.getText().toString())) {
                                                Toast.makeText(purchase.this, "Finance Amount name cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(fproch.getText().toString())) {
                                                Toast.makeText(purchase.this, "Processing cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(femi.getText().toString())) {
                                                Toast.makeText(purchase.this, "EMI cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (TextUtils.isEmpty(femiamount.getText().toString())) {
                                                Toast.makeText(purchase.this, "EMI Amount cannot be empty", Toast.LENGTH_SHORT).show();
                                                return;
                                            }

                                            realm.beginTransaction();
                                            PaymentType paymentType = realm.createObject(PaymentType.class);
                                            paymentType.setCamount(fname.getText().toString());
                                            paymentType.setAmount(fexc.getText().toString());
                                            paymentType.setDate(fagree.getText().toString());
                                            paymentType.setNumber(fdpay.getText().toString());
                                            paymentType.setBank(famount.getText().toString());
                                            paymentType.setDays(fproch.getText().toString());
                                            paymentType.setStart(femi.getText().toString());
                                            paymentType.setEnd(femiamount.getText().toString());
                                            realm.commitTransaction();
                                        }


                                        //Entering the data into Database

                                        doneBuild.dismiss();
                                        // Setting the newly added user to Party Customer


                                    }
                                });
                            }
                        }

                    });


                    //(Save and save and new)Button code
                    Button save = (Button) findViewById(R.id.save);
                    Button savenew = (Button) findViewById(R.id.savenew);


                    EditText editText1 = (EditText) findViewById(R.id.invoice);
                    EditText editText2 = (EditText) findViewById(R.id.date);
                    EditText editText3 = (EditText) findViewById(R.id.supplyplace);
                    EditText editText5 = (EditText) findViewById(R.id.editText2);
                    EditText editText6 = (EditText) findViewById(R.id.editText3);
                    EditText editText7 = (EditText) findViewById(R.id.description);

                    editText5.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            if (s.length() != 0) {
                                float first = Float.parseFloat(editText4.getText().toString());
                                float second = Float.parseFloat(editText5.getText().toString());

                                if (first - second >= 0)
                                    editText6.setText(Float.toString(first - second));


                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }


                    });


                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(editText1.getText().toString())) {
                                Toast.makeText(purchase.this, "Invoice should not be empty", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (TextUtils.isEmpty(editText2.getText().toString())) {
                                Toast.makeText(purchase.this, "Date should not be empty", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (TextUtils.isEmpty(editText3.getText().toString())) {
                                Toast.makeText(purchase.this, "Place of supply should not be empty", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (TextUtils.isEmpty(editText4.getText().toString())) {
                                Toast.makeText(purchase.this, "Total amount should not be empty", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (TextUtils.isEmpty(editText5.getText().toString())) {
                                Toast.makeText(purchase.this, "Amount received should not be empty", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (TextUtils.isEmpty(auto.getText().toString())) {
                                Toast.makeText(purchase.this, "Customer name should not be empty", Toast.LENGTH_SHORT).show();
                                return;
                            } else {

                                Toast.makeText(purchase.this, "Submitted", Toast.LENGTH_SHORT).show();
                                realm.beginTransaction();
                                PurchaseInput purchaseInput = realm.createObject(PurchaseInput.class);
                                purchaseInput.setName(auto.getText().toString());
                                purchaseInput.setPlace(editText3.getText().toString());
                                purchaseInput.setInvoice(Integer.parseInt(editText1.getText().toString()));
                                purchaseInput.setDate(editText2.getText().toString());
                                purchaseInput.setTotal(Float.parseFloat(editText4.getText().toString()));
                                purchaseInput.setReceived(Float.parseFloat(editText5.getText().toString()));
                                purchaseInput.setBalance(Float.parseFloat(editText6.getText().toString()));
                                purchaseInput.setDescription(editText7.getText().toString());
                                purchaseInput.setPayType(auto_1.getText().toString());
                                purchaseInput.setId("purchase");
                                try {
                                    purchaseInput.setRefNumber(Integer.parseInt(reference.getText().toString()));
                                } catch (Exception e) {
                                }

                                realm.commitTransaction();

                                // Deleting the item Selected list

                                // Adding the unique username and invoice number to list.


                                realm.beginTransaction();
                                RealmResults<DetailsItemPurchase1> obj = realm.where(DetailsItemPurchase1.class).findAll();
                                Log.d("returnpurchase", String.valueOf(obj.size()));
                                int b = 110000000;
                                for (int j = 0; j < obj.size(); j++) {
                                    String a = obj.get(j).getUsername();
                                    try {
                                        if (a == null) {
                                            if (b > j) {
                                                b = j;
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                                for (int i = b; i < obj.size(); i++) {
                                    obj.get(i).setUsername(auto.getText().toString());
                                    obj.get(i).setInvoice(editText1.getText().toString());
                                }

                                realm.commitTransaction();


                                realm.beginTransaction();
                                realm.delete(DetailsItemPurchase.class);
                                realm.commitTransaction();
                                Intent intent1 = new Intent(purchase.this, MainActivity.class);
                                startActivity(intent1);
                                finish();


                            }
                        }
                    });

                    savenew.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(editText1.getText().toString())) {
                                Toast.makeText(purchase.this, "Invoice should not be empty", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (TextUtils.isEmpty(editText2.getText().toString())) {
                                Toast.makeText(purchase.this, "Date should not be empty", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (TextUtils.isEmpty(editText3.getText().toString())) {
                                Toast.makeText(purchase.this, "Place of supply should not be empty", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (TextUtils.isEmpty(editText4.getText().toString())) {
                                Toast.makeText(purchase.this, "Total amount should not be empty", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (TextUtils.isEmpty(editText5.getText().toString())) {
                                Toast.makeText(purchase.this, "Amount received should not be empty", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (TextUtils.isEmpty(auto.getText().toString())) {
                                Toast.makeText(purchase.this, "Customer name should not be empty", Toast.LENGTH_SHORT).show();
                                return;
                            } else {

                                Toast.makeText(purchase.this, "Submitted", Toast.LENGTH_SHORT).show();

                                //Realm code for Purchase details
                                realm.beginTransaction();
                                 PurchaseInput purchaseInput = realm.createObject(PurchaseInput.class);
                               purchaseInput.setName(auto.getText().toString());
                               purchaseInput.setPlace(editText3.getText().toString());
                               purchaseInput.setInvoice(Integer.parseInt(editText1.getText().toString()));
                               purchaseInput.setDate(editText2.getText().toString());
                               purchaseInput.setTotal(Integer.parseInt(editText4.getText().toString()));
                               purchaseInput.setReceived(Integer.parseInt(editText5.getText().toString()));
                               purchaseInput.setBalance(Integer.parseInt(editText6.getText().toString()));
                               purchaseInput.setId("purchase");
                                realm.commitTransaction();

                                Intent intent2 = new Intent(purchase.this, purchase.class);
                                startActivity(intent2);
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

        //To delete the database on back button
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event)
        {
            if ((keyCode == KeyEvent.KEYCODE_BACK))
            {
                SyncUser user = SyncUser.current();
                String url =  REALM_URL + "/~/user";
                SyncConfiguration config = user.createConfiguration(url)
                        .fullSynchronization()
                        .waitForInitialRemoteData(30, TimeUnit.SECONDS)
                        .build();

                RealmAsyncTask realm = Realm.getInstanceAsync(config, new Realm.Callback() {
                    @Override
                    public void onSuccess(Realm realm) {
                        // Realm is ready
                        realm.beginTransaction();
                        RealmResults results = realm.where(DetailsItemPurchase.class).findAll();
                        results.deleteAllFromRealm();
                        realm.commitTransaction();
                    }

                    @Override
                    public void onError(Throwable exception) {
                        // Handle error
                    }
                });


            }
            return super.onKeyDown(keyCode, event);
        }


    }









