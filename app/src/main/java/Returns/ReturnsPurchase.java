package Returns;

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
import android.widget.Toast;


import com.example.prathamesh.Authentication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import Extra.RealmConfig;
import MainActivity.MainActivity;
import RealmClasses.DetailsItemPurchase1;
import RealmClasses.PaymentType;
import RealmClasses.PurchaseInput;
import RealmClasses.Purchase_return;
import RealmClasses.Return_items_sale;
import RealmClasses.Returns_items_purchase;
import RealmClasses.SaleInput;
import RealmClasses.User_p;
import RecyclerClass.PurchaseReturnAdapter;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.SyncConfiguration;

public class ReturnsPurchase extends AppCompatActivity {


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_return);

        RealmConfig realmConfig = new RealmConfig();
        SyncConfiguration config = realmConfig.getConfig();

        RealmAsyncTask realm = Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                // Realm is ready




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sales_return);
        AutoCompleteTextView auto = (AutoCompleteTextView) findViewById(R.id.name);
        AutoCompleteTextView auto1 = (AutoCompleteTextView) findViewById(R.id.invoice);
        EditText return_invoice = (EditText) findViewById(R.id.return_invoice);
        AutoCompleteTextView payment = (AutoCompleteTextView) findViewById(R.id.payment);
        EditText referenceNumber = (EditText) findViewById(R.id.referenceNumber);
        Button save = (Button) findViewById(R.id.save);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Purchase Return");

        // Return Date
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
                new DatePickerDialog(ReturnsPurchase.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        // Fetching the Sales User List
        realm.beginTransaction();
        ArrayList<User_p> list = new ArrayList<User_p>(realm.where(User_p.class).findAll());
        ArrayList<String> array = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            array.add(i, list.get(i).getUsername());
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
                realm.beginTransaction();
                ArrayList<PurchaseInput> list_for_invoice = new ArrayList<PurchaseInput>(realm.where(PurchaseInput.class)
                        .equalTo("name", buffer_position)
                        .findAll());

                Log.d("InvoiceList", list_for_invoice.toString() + buffer_position);
                ArrayList<String> array1 = new ArrayList<>(list_for_invoice.size());
                for (int i = 0; i < list_for_invoice.size(); i++) {
                    array1.add(i, String.valueOf(list_for_invoice.get(i).getInvoice()));
                }
                final String[] Invoice_List = array1.toArray(new String[array1.size()]);
                ArrayAdapter<String> adapter_1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, Invoice_List);
                realm.commitTransaction();

                auto1.setAdapter(adapter_1);

                auto1.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        auto1.showDropDown();
                        return false;
                    }
                });

                auto1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus)
                            auto1.getId(); // Instead of your Toast
                    }
                });

            }
        });

        auto1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String buffer = (String) parent.getItemAtPosition(position); // Item Position
                String random = UUID.randomUUID().toString();
                String random_sub = random.substring(1,4);
                String random_final = buffer + random_sub;
                return_invoice.setText(random_final);

                // Passing the value to Recycler View of the corresponding Invoice and Party Cuatomer.
                ArrayList<DetailsItemPurchase1> list_1 = new ArrayList<DetailsItemPurchase1>(realm.where(DetailsItemPurchase1.class)
                        .equalTo("invoice", buffer)
                        .findAll());

                realm.beginTransaction();
                for(int i = 0; i < list_1.size(); i++)
                {
                    Returns_items_purchase items = realm.createObject(Returns_items_purchase.class);
                    items.setInvoice(list_1.get(i).getInvoice());
                    items.setName(list_1.get(i).getName());
                    items.setPosition(list_1.get(i).getPosition());
                    items.setQuantity(list_1.get(i).getQuantity());
                    items.setRate(list_1.get(i).getRate());
                    items.setSubtotal(list_1.get(i).getSubtotal());
                    items.setTaxvalue(list_1.get(i).getTaxvalue());
                    items.setTaxx(list_1.get(i).getTaxx());
                    items.setUnit(list_1.get(i).getUnit());
                    items.setUsername(list_1.get(i).getUsername());
                    items.setId("returns_purchase");
                }
                realm.commitTransaction();


                RecyclerView username = (RecyclerView) findViewById(R.id.recyclerview_item);
                username.setHasFixedSize(true);

                LinearLayoutManager layoutManager = new LinearLayoutManager(ReturnsPurchase.this);
                username.setLayoutManager(layoutManager);
                PurchaseReturnAdapter mAdapter = new PurchaseReturnAdapter(list_1);
                username.setAdapter(mAdapter);


            }
        });


        // Payment type without finance and credit for sale's return
        final String[] type = new String[]{"Cash", "Cheque", "Card", "RTGS", "NEFT", "Digital"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, type);
        payment.setAdapter(adapter1);

        payment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                payment.showDropDown();
                return false;
            }
        });

        payment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    payment.getId(); // Instead of your Toast
            }
        });



        payment.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String buffer_position = (String) parent.getItemAtPosition(position);
                int real_position = Arrays.asList(type).indexOf(buffer_position);


                if(real_position >= 1 && real_position <= 5){

                    final AlertDialog.Builder builder = new AlertDialog.Builder(ReturnsPurchase.this);
                    final LayoutInflater inflater = ReturnsPurchase.this.getLayoutInflater();

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
                    AutoCompleteTextView fname  = (AutoCompleteTextView) custom_dialog.findViewById(R.id.fname);
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

                            if(!x.isShowing()){
                                x.show();
                            }


                        }
                    });

                    date1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new DatePickerDialog(ReturnsPurchase.this, date_2, myCalendar_1
                                    .get(Calendar.YEAR), myCalendar_1.get(Calendar.MONTH),
                                    myCalendar_1.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });

                    start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new DatePickerDialog(ReturnsPurchase.this, date_3, myCalendar_1
                                    .get(Calendar.YEAR), myCalendar_1.get(Calendar.MONTH),
                                    myCalendar_1.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });

                    end.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new DatePickerDialog(ReturnsPurchase.this, date_4, myCalendar_1
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
                    if(real_position == 2 || real_position == 3 || real_position == 4) {

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

                    }
                    else if(real_position == 1 || real_position == 5)
                    {
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
                    }

                    // Finance Company Name (Realm + DialogBox)

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

                    doneBuild.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(real_position == 2 || real_position == 3 || real_position == 4) {
                                if (TextUtils.isEmpty(camount.getText().toString())) {
                                    Toast.makeText(ReturnsPurchase.this, "cash amount cannot be empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(amount.getText().toString())) {
                                    Toast.makeText(ReturnsPurchase.this, "Amount cannot be empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(date.getText().toString())) {
                                    Toast.makeText(ReturnsPurchase.this, "Date cannot be empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(number.getText().toString())) {
                                    Toast.makeText(ReturnsPurchase.this, "Number cannot be empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(bank.getText().toString())) {
                                    Toast.makeText(ReturnsPurchase.this, "Bank.Bank name cannot be empty", Toast.LENGTH_SHORT).show();
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

                            }
                            else if(real_position == 1 || real_position == 5) {
                                if (TextUtils.isEmpty(camount.getText().toString())) {
                                    Toast.makeText(ReturnsPurchase.this, "cash amount cannot be empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(amount.getText().toString())) {
                                    Toast.makeText(ReturnsPurchase.this, "Amount cannot be empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(date.getText().toString())) {
                                    Toast.makeText(ReturnsPurchase.this, "Date cannot be empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(number.getText().toString())) {
                                    Toast.makeText(ReturnsPurchase.this, "Number cannot be empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(bank.getText().toString())) {
                                    Toast.makeText(ReturnsPurchase.this, "Bank.Bank name cannot be empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                realm.beginTransaction();
                                PaymentType paymentType = realm.createObject(PaymentType.class);
                                paymentType.setCamount(camount.getText().toString());
                                paymentType.setAmount(amount.getText().toString());
                                paymentType.setDate(date.getText().toString());
                                paymentType.setNumber(number.getText().toString());
                                paymentType.setBank(bank.getText().toString());
                                try{
                                    paymentType.setAmount1(amount1.getText().toString());
                                    paymentType.setDate1(date1.getText().toString());
                                    paymentType.setNumber1(number1.getText().toString());
                                    paymentType.setBank1(bank1.getText().toString());
                                }catch(Exception e){}
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

        //Save
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                realm.beginTransaction();
                Purchase_return purchase_return = realm.createObject(Purchase_return.class);
                purchase_return.setInvoice(auto1.getText().toString());
                purchase_return.setName(auto.getText().toString());
                purchase_return.setReturn_invoice(return_invoice.getText().toString());
                purchase_return.setPtype(payment.getText().toString());
                purchase_return.setRef(referenceNumber.getText().toString());
                purchase_return.setDate(edittext.getText().toString());
                purchase_return.setId("purchase_return");

                // Adding Return invoice
                RealmResults<Returns_items_purchase> results = realm.where(Returns_items_purchase.class).findAll();
                for(int j = 0; j < results.size(); j++){
                    results.get(j).setReturn_invoice(return_invoice.getText().toString());
                    results.get(j).setDate(edittext.getText().toString());
                    results.get(j).setpayType(payment.getText().toString());
                    results.get(j).setRef(referenceNumber.getText().toString());
                }
                realm.commitTransaction();
                Toast.makeText(ReturnsPurchase.this, "Saved", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(ReturnsPurchase.this, MainActivity.class));
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
