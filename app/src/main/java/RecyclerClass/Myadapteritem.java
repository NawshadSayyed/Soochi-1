package RecyclerClass;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prathamesh.Authentication.R;

import java.util.ArrayList;

import RealmClasses.ProductList;

public  class Myadapteritem extends RecyclerView.Adapter<Myadapteritem.MyViewHolder> {
    private ArrayList<ProductList> list;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView_1, textView_2, textView_3, textView_4;

        public MyViewHolder(View v) {
            super(v);
            textView_1 = (TextView) v.findViewById(R.id.textView20);
            textView_2 = (TextView) v.findViewById(R.id.textView21);
            textView_3 = (TextView) v.findViewById(R.id.textView22);
            textView_4 = (TextView) v.findViewById(R.id.textView23);


        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public Myadapteritem(ArrayList<ProductList> list) {
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Myadapteritem.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);


        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.textView_1.setText(list.get(position).getName());
        holder.textView_2.setText(Integer.toString(list.get(position).getBarcode()));
        holder.textView_4.setText(Integer.toString(list.get(position).getPurchase()));
        holder.textView_3.setText(Integer.toString(list.get(position).getSale()));
        Log.d("TAG", list.get(position).getName());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return list.size();
    }
}
