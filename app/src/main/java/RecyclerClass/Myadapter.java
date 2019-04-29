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

import Extra.Cardname;
import RealmClasses.PurchaseInput;
import RealmClasses.SaleInput;
import RealmClasses.User;

   public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder> {
    private ArrayList<SaleInput> list;
    private ArrayList<PurchaseInput> list1;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView_1, textView_2;

        public MyViewHolder(View v) {
            super(v);
            textView_1 = (TextView) v.findViewById(R.id.textView14);
            textView_2 = (TextView) v.findViewById(R.id.textView15);

        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public Myadapter(ArrayList<SaleInput> list, ArrayList<PurchaseInput> list1) {
       this.list = list;
       this.list1 = list1;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Myadapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list, parent, false);


        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    int p = 0;
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

              try {
                  holder.textView_1.setText(list.get(position).getName());
                  holder.textView_2.setText(list.get(position).getId());
              }catch(Exception e){}

              try {
                  holder.textView_1.setText(list1.get(position).getName());
                  holder.textView_2.setText(list1.get(position).getId());
              }catch(Exception e){}



            holder.textView_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.textView_1.getContext(), Cardname.class);
                    try{
                    intent.putExtra("Value", list.get(position).getName());}catch(Exception e){}
                    try{
                        intent.putExtra("Value", list1.get(position).getName());}catch(Exception e){}
                    holder.textView_1.getContext().startActivity(intent);
                }
            });

        }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        ArrayList final_list = new ArrayList();
        final_list.addAll(list);
        final_list.addAll(list1);
        Log.d("listlength", String.valueOf(final_list.size()));
        return final_list.size();
    }
}