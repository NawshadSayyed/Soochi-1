package RecyclerClass;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prathamesh.Authentication.R;

import java.util.ArrayList;

import RealmClasses.PurchaseInput;
import RealmClasses.Return_items_sale;
import RealmClasses.Returns_items_purchase;
import RealmClasses.SaleInput;

public class Cash_Recycler extends RecyclerView.Adapter<Cash_Recycler.MyViewHolder> {
    private ArrayList<SaleInput> list;
    private ArrayList<PurchaseInput> list1;
    private ArrayList<Return_items_sale> list2;
    private ArrayList<Returns_items_purchase> list3;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView date, invoice, remark, debit, credit, balance;

        public MyViewHolder(View v) {
            super(v);
            invoice = (TextView) v.findViewById(R.id.t1);
            date = (TextView) v.findViewById(R.id.t2);
            remark = (TextView) v.findViewById(R.id.textView26);
            balance = (TextView) v.findViewById(R.id.textView29);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Cash_Recycler(ArrayList<SaleInput> list, ArrayList<PurchaseInput> list1, ArrayList<Return_items_sale> list2, ArrayList<Returns_items_purchase> list3) {
        this.list = list;
        this.list1 = list1;
        this.list2 = list2;
        this.list3 = list3;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Cash_Recycler.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cash_recycler, parent, false);


        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    int p = 0;
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
         if(position < list.size()) {
                 holder.invoice.setText(String.valueOf(list.get(position).getInvoice()));
                 holder.date.setText(list.get(position).getDate());
                 holder.remark.setText(list.get(position).getId());
                 holder.balance.setText("-" + String.valueOf(list.get(position).getTotal()));

         }
         if(position >= list.size() && position < list1.size()) {
             try {
                 holder.invoice.setText(String.valueOf(list1.get(position).getInvoice()));
                 holder.date.setText(list1.get(position).getDate());
                 holder.remark.setText(list1.get(position).getId());
                 holder.balance.setText(String.valueOf(list1.get(position).getTotal()));
             } catch (Exception e) {
             }
         }
        if(position >= list1.size() && position < list2.size()){

            holder.invoice.setText(list2.get(position).getReturn_invoice());
            holder.date.setText(list2.get(position).getDate());
            holder.remark.setText(list2.get(position).getId());
            holder.balance.setText(String.valueOf(Float.parseFloat(list2.get(position).getsubtotal()) + Float.parseFloat(list2.get(position).getTaxvalue())));


        }

        if(position >= list2.size() && position < list3.size()) {
            try {
                holder.invoice.setText(list3.get(position).getReturn_invoice());
                holder.date.setText(list3.get(position).getDate());
                holder.remark.setText(list3.get(position).getId());
                holder.balance.setText(String.valueOf(Float.parseFloat(list3.get(position).getsubtotal()) + Float.parseFloat(list3.get(position).getTaxvalue())));

            } catch (Exception e) {
            }
        }

    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        ArrayList final_list = new ArrayList();
        final_list.addAll(list);
        final_list.addAll(list1);
        final_list.addAll(list2);
        final_list.addAll(list3);
        Log.d("listlength", String.valueOf(final_list.size()));
        return final_list.size();
    }
}