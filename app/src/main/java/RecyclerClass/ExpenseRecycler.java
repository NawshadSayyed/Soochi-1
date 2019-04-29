package RecyclerClass;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prathamesh.Authentication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import Expenses.Expense;
import Extra.Cardname;
import Extra.RealmConfig;
import RealmClasses.DetailsItemSale;
import RealmClasses.ExpenseDetails;
import RealmClasses.PaymentType;
import RealmClasses.SaleInput;
import RealmClasses.Tax_List;
import RealmClasses.User;
import RealmClasses.User_expense;
import Sale.Sale;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.SyncConfiguration;

public class ExpenseRecycler extends RecyclerView.Adapter<ExpenseRecycler.MyViewHolder> {
    private ArrayList<User_expense> list;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name, type;


        public MyViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            type = (TextView) v.findViewById(R.id.type);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ExpenseRecycler(ArrayList<User_expense> list) {
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ExpenseRecycler.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_list_recycler, parent, false);


        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        try {
            holder.name.setText(list.get(position).getName());
            holder.type.setText(list.get(position).getType());
        } catch (Exception e) {
            Log.d("Error_Expense_R", e.toString());
        }

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RealmConfig realmConfig = new RealmConfig();
                SyncConfiguration config = realmConfig.getConfig();

                RealmAsyncTask realm = Realm.getInstanceAsync(config, new Realm.Callback() {
                    @Override
                    public void onSuccess(Realm realm) {
                        // Realm is ready




                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                final LayoutInflater inflater = LayoutInflater.from(v.getContext());

                ViewGroup nullParent = null;
                View custom_dialog = inflater.inflate(R.layout.dialog_expense, nullParent);

                EditText date = (EditText) custom_dialog.findViewById(R.id.date);
                EditText payment = (EditText) custom_dialog.findViewById(R.id.payment);
                EditText purpose = (EditText) custom_dialog.findViewById(R.id.purpose);
                EditText amount = (EditText) custom_dialog.findViewById(R.id.amount);
                AutoCompleteTextView ptype = (AutoCompleteTextView) custom_dialog.findViewById(R.id.ptype);
                EditText etype = (EditText) custom_dialog.findViewById(R.id.atype);

                // Adding date
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
                        new DatePickerDialog(v.getContext(), date_1, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                payment.setText(list.get(position).getName());
                etype.setText(list.get(position).getType());

                // Adding payment type details
                final String[] type = new String[]{"Cash", "Cheque"};
                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, type);
                ptype.setAdapter(adapter1);

                ptype.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        ptype.showDropDown();
                        return false;
                    }
                });

                ptype.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus)
                            ptype.getId(); // Instead of your Toast
                    }
                });

                ptype.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String buffer_position = (String) parent.getItemAtPosition(position);
                        int real_position = Arrays.asList(type).indexOf(buffer_position);
                        if (real_position == 1) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            final LayoutInflater inflater = LayoutInflater.from(v.getContext());

                            ViewGroup nullParent = null;

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


                            date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    new DatePickerDialog(custom_dialog.getContext(), date_1, myCalendar_1
                                            .get(Calendar.YEAR), myCalendar_1.get(Calendar.MONTH),
                                            myCalendar_1.get(Calendar.DAY_OF_MONTH)).show();
                                }

                            });

                            date1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new DatePickerDialog(custom_dialog.getContext(), date_2, myCalendar_1
                                            .get(Calendar.YEAR), myCalendar_1.get(Calendar.MONTH),
                                            myCalendar_1.get(Calendar.DAY_OF_MONTH)).show();
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
                            builder.setTitle(buffer_position);
                            AlertDialog doneBuild = builder.create();
                            doneBuild.show();

                            // Hiding Stuff
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


                            doneBuild.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (TextUtils.isEmpty(camount.getText().toString())) {
                                        Toast.makeText(v.getContext(), "cash amount cannot be empty", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (TextUtils.isEmpty(amount.getText().toString())) {
                                        Toast.makeText(v.getContext(), "Amount cannot be empty", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (TextUtils.isEmpty(date.getText().toString())) {
                                        Toast.makeText(v.getContext(), "Date cannot be empty", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (TextUtils.isEmpty(number.getText().toString())) {
                                        Toast.makeText(v.getContext(), "Number cannot be empty", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (TextUtils.isEmpty(bank.getText().toString())) {
                                        Toast.makeText(v.getContext(), "Bank.Bank name cannot be empty", Toast.LENGTH_SHORT).show();
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

                                }

                            });
                        }


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
                        if (TextUtils.isEmpty(date.getText().toString())) {
                            Toast.makeText(v.getContext(), "Date name should not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(payment.getText().toString())) {
                            Toast.makeText(v.getContext(), "Payment should not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(purpose.getText().toString())) {
                            Toast.makeText(v.getContext(), "Purpose should not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(amount.getText().toString())) {
                            Toast.makeText(v.getContext(), "Amount should not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(ptype.getText().toString())) {
                            Toast.makeText(v.getContext(), "Payment type should not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(etype.getText().toString())) {
                            Toast.makeText(v.getContext(), "Payment type should not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        String random = UUID.randomUUID().toString();
                        String random_sub = random.substring(1,6);
                        String username_date = date.getText().toString();
                        String username_payment = payment.getText().toString();
                        String username_purpose = purpose.getText().toString();
                        String username_amount = amount.getText().toString();
                        String username_ptype = ptype.getText().toString();
                        String username_etype = etype.getText().toString();

                        // Realm class entry

                        Toast.makeText(v.getContext(), "Saved", Toast.LENGTH_SHORT).show();
                        realm.beginTransaction();
                        ExpenseDetails user = realm.createObject(ExpenseDetails.class);
                        user.setDate(username_date);
                        user.setPayment(username_payment);
                        user.setAmount(username_amount);
                        user.setPurpose(username_purpose);
                        user.setPayment_type(username_ptype);
                        user.setAccount_type(username_etype);
                        user.setId(random_sub);
                        realm.commitTransaction();
                        // if every thing is Ok then dismiss dialog
                        doneBuild.dismiss();


                    }
                });
                    }

                    @Override
                    public void onError(Throwable exception) {
                        // Handle error
                    }
                });
            }
        });

    }

    public void updateList(ArrayList<User_expense> list_1){
        list= list_1;
        notifyDataSetChanged();
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return list.size();

    }
}
