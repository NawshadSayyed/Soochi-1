package RecyclerClass;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prathamesh.Authentication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import Bank.IBank;
import Extra.Cardname;
import RealmClasses.Bank_realm;
import RealmClasses.PurchaseInput;
import RealmClasses.SaleInput;
import RealmClasses.User;

public class Bank_recycler extends RecyclerView.Adapter<Bank_recycler.MyViewHolder> {
    private ArrayList<Bank_realm> list;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name, balance;

        public MyViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            balance = (TextView) v.findViewById(R.id.balance);

        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public Bank_recycler(ArrayList<Bank_realm> list) {
        this.list = list;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public Bank_recycler.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bank_recycler, parent, false);


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
            holder.balance.setText(list.get(position).getBalance());
        }catch(Exception e){}




        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), IBank.class);
                intent.putExtra("key", list.get(position).getName());
                view.getContext().startActivity(intent);
            }
        });

    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}