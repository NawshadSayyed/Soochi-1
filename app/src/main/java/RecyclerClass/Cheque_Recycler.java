package RecyclerClass;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Comparator;
import java.util.Locale;

import Extra.Cardname;
import RealmClasses.PurchaseInput;
import RealmClasses.Purchase_return;
import RealmClasses.Return_items_sale;
import RealmClasses.Returns_items_purchase;
import RealmClasses.SaleInput;
import RealmClasses.Sales_return;
import RealmClasses.User;
import Returns.ReturnsPurchase;
import Returns.ReturnsSale;

public class Cheque_Recycler extends RecyclerView.Adapter<Cheque_Recycler.MyViewHolder> {
    private ArrayList<SaleInput> list;
    private ArrayList<PurchaseInput> list1;
    private ArrayList<Return_items_sale> list2;
    private ArrayList<Returns_items_purchase> list3;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name, id, date, status, ref, amount;
        public Button d_w;

        public MyViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            id = (TextView) v.findViewById(R.id.id);
            date = (TextView) v.findViewById(R.id.date);
            status = (TextView) v.findViewById(R.id.open_close);
            ref = (TextView) v.findViewById(R.id.ref);
            amount = (TextView) v.findViewById(R.id.amount1);
            d_w = (Button) v.findViewById(R.id.button);

        }

    }

        // Provide a suitable constructor (depends on the kind of dataset)
        public Cheque_Recycler(ArrayList<SaleInput> list, ArrayList<PurchaseInput> list1, ArrayList<Return_items_sale> list2, ArrayList<Returns_items_purchase> list3)
        {
            this.list = list;
            this.list1 = list1;
            this.list2 = list2;
            this.list3 = list3;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public Cheque_Recycler.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cheque_recycler, parent, false);


            MyViewHolder vh = new MyViewHolder(view);
            return vh;
        }

        int p = 0;

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            if (position < list.size()) {
                holder.name.setText(list.get(position).getName());
                holder.date.setText(list.get(position).getDate());
                holder.amount.setText(String.valueOf(list.get(position).getTotal()));
                holder.id.setText(list.get(position).getId());
                holder.ref.setText(String.valueOf(list.get(position).getRefNumber()));
                holder.status.setText("open");

            }
            else if (position >= list.size() && position < list1.size()) {
                holder.name.setText(list1.get(position).getName());
                holder.date.setText(list1.get(position).getDate());
                holder.amount.setText(String.valueOf(list1.get(position).getTotal()));
                holder.id.setText(list1.get(position).getId());
                holder.ref.setText(String.valueOf(list1.get(position).getRefNumber()));
                holder.status.setText("open");
            }
            else if (position >= list1.size() && position < list2.size())
            {
                holder.name.setText(list2.get(position).getName());
                holder.date.setText(list2.get(position).getDate());
                holder.id.setText(list2.get(position).getId());
                holder.ref.setText(list2.get(position).getRef());
                holder.status.setText("open");
                holder.amount.setText(String.valueOf(Float.parseFloat(list2.get(position).getsubtotal()) + Float.parseFloat(list2.get(position).getTaxvalue())));

            }

            else if (position >= list2.size() && position < list3.size()) {


                    holder.name.setText(list3.get(position).getName());
                    holder.date.setText(list3.get(position).getDate());
                    holder.id.setText(list3.get(position).getId());
                    holder.ref.setText(list3.get(position).getRef());
                    holder.status.setText("open");
                    holder.amount.setText(String.valueOf(Float.parseFloat(list3.get(position).getsubtotal()) + Float.parseFloat(list3.get(position).getTaxvalue())));
            }

            // Button to pop-up deposit/ withdraw.
            holder.d_w.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    final LayoutInflater inflater = LayoutInflater.from(v.getContext());
                    View custom_dialog = inflater.inflate(R.layout.submit_cheque, null);
                    EditText date = (EditText) custom_dialog.findViewById(R.id.date);

                    //Defining date
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

                      // Inflating the date
                    date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new DatePickerDialog(v.getContext(), date_1, myCalendar
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

                            if (TextUtils.isEmpty(date.getText().toString())) {
                                Toast.makeText(v.getContext(), "Date cannot be empty", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            holder.status.setText("closed");

                            doneBuild.dismiss();

                        }

                        });

                  }
            });
        }


        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {

            return list3.size();
        }
    }
