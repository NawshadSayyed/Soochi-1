package RecyclerClass;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.prathamesh.Authentication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import Extra.Cardname;
import Extra.RealmConfig;
import RealmClasses.DetailsItemSale;
import RealmClasses.ExpenseDetails;
import RealmClasses.SaleInput;
import RealmClasses.User;
import Sale.Sale;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import io.realm.SyncConfiguration;

public class Expense_List_Recycler extends RecyclerView.Adapter<Expense_List_Recycler.MyViewHolder> {
    private ArrayList<ExpenseDetails> list;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView date, name, purpose, amount, ptype, etype;
        public Button delete;

        public MyViewHolder(View v) {
            super(v);
            date = (TextView) v.findViewById(R.id.date);
            name = (TextView) v.findViewById(R.id.name);
            purpose = (TextView) v.findViewById(R.id.purpose);
            amount = (TextView) v.findViewById(R.id.amount);
            ptype = (TextView) v.findViewById(R.id.ptype);
            etype = (TextView) v.findViewById(R.id.etype);
            delete = (Button) v.findViewById(R.id.delete);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public Expense_List_Recycler(ArrayList<ExpenseDetails> list) {
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Expense_List_Recycler.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_details_list, parent, false);


        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    int p = 0;
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.date.setText(list.get(position).getDate());
        holder.name.setText(list.get(position).getPayment());
        holder.purpose.setText(list.get(position).getPurpose());
        holder.amount.setText("Amount- " + list.get(position).getAmount());
        holder.ptype.setText(list.get(position).getPayment_type());
        holder.etype.setText(list.get(position).getAccount_type());

        // Delete Button
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RealmConfig realmConfig = new RealmConfig();
                SyncConfiguration config = realmConfig.getConfig();

                Realm.getInstanceAsync(config, new Realm.Callback() {
                    @Override
                    public void onSuccess(Realm realm) {
                        // Realm is ready
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, list.size());

                        Realm.init(v.getContext());
                        realm.beginTransaction();
                        RealmResults results_1 = realm.where(ExpenseDetails.class).findAll();

                        try {
                            if (list.size() >= 1) {
                                results_1.deleteFromRealm(holder.getAdapterPosition());
                            } else {
                                results_1.deleteAllFromRealm();
                            }
                        }catch(Exception e){

                        }

                        realm.commitTransaction();


                    }

                    @Override
                    public void onError(Throwable exception) {
                        // Handle error
                    }
                });
            }
        });

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}