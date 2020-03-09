package com.example.harihara_medicals.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harihara_medicals.Model.Cart_list;
import com.example.harihara_medicals.R;

import java.util.ArrayList;

public class Cart_list_Adapter extends RecyclerView.Adapter<Cart_list_Adapter.ViewHolder> {
    private LayoutInflater inflater;
    public ArrayList<Cart_list> cartlistArrayList;
    public Cart_list_Adapter(Context mctx, ArrayList<Cart_list> cartlistArrayList){
        inflater=LayoutInflater.from(mctx);
        this.cartlistArrayList=cartlistArrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.add_to_cart_layout,parent,false);
        ViewHolder holder =new ViewHolder(view);
         return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tab_name.setText(cartlistArrayList.get(position).getCart_tab_name());
        holder.tab_count.setText(cartlistArrayList.get(position).getCart_tab_count());
        holder.tab_price.setText(cartlistArrayList.get(position).getCart_tab_price());

    }

    @Override
    public int getItemCount() {
        return cartlistArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tab_name,tab_count,tab_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tab_name=itemView.findViewById(R.id.cart_name);
            tab_count=itemView.findViewById(R.id.cart_count);
            tab_price=itemView.findViewById(R.id.cart_price);
        }
    }
}
