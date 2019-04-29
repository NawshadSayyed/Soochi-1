package RecyclerClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import Extra.Bill;
import com.example.prathamesh.Authentication.R;

import java.util.ArrayList;

import RealmClasses.SaleInput;

public class MyadapterBill extends RecyclerView.Adapter<MyadapterBill.MyViewHolder> {
    private ArrayList<SaleInput> list_bill;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            // each data item is just a string in this case
                        TextView view, view_1, view_2, view_3, view_4;
           MyClickListener listener;
           Button edit;

        public MyViewHolder(View v,MyClickListener listener) {
            super(v);
            view = (TextView) v.findViewById(R.id.text_View1);
            view_1 = (TextView) v.findViewById(R.id.text_View2);
            view_2 = (TextView) v.findViewById(R.id.text_View3);
            view_3 = (TextView) v.findViewById(R.id.text_View4);
            view_4 = (TextView) v.findViewById(R.id.text_View5);

            this.listener = listener;
             edit = (Button) v.findViewById(R.id.button_pdf);
            edit.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {


        }




        }

    public interface MyClickListener {
        void onEdit(int p);
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyadapterBill(ArrayList<SaleInput> list) {
        this.list_bill = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyadapterBill.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_bill, parent, false);


        MyViewHolder vh = new MyViewHolder(view, new MyClickListener() {
            @Override
            public void onEdit(int p) {

            }
        });

        return vh;
        }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.view.setText(Integer.toString(list_bill.get(position).getInvoice()));
        holder.view_1.setText(list_bill.get(position).getDate());
        holder.view_2.setText(Float.toString(list_bill.get(position).getTotal()));
        holder.view_3.setText(Float.toString(list_bill.get(position).getReceived()));
        holder.view_4.setText(Float.toString(list_bill.get(position).getBalance()));

       holder.edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(view.getContext(), Bill.class);
               intent.putExtra("1", Integer.toString(list_bill.get(position).getInvoice()));
               intent.putExtra("2", list_bill.get(position).getDate());
               intent.putExtra("3", Float.toString(list_bill.get(position).getTotal()));
               intent.putExtra("4", Float.toString(list_bill.get(position).getReceived()));
               intent.putExtra("5", Float.toString(list_bill.get(position).getBalance()));
               view.getContext().startActivity(intent);
           }
       });






        /*holder.textView_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.textView_1.getContext(), Cardname.class);
                intent.putExtra("Value", list.get(position).getUsername());
                holder.textView_1.getContext().startActivity(intent);
            }
        }); */


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list_bill.size();
    }

 private  Context context;
    public MyadapterBill(Context context)
    {
        this.context = context;
    }


}