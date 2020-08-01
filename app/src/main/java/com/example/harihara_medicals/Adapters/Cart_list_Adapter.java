package com.example.harihara_medicals.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harihara_medicals.Model.Cart_list;
import com.example.harihara_medicals.R;
import com.example.harihara_medicals.Retrofit.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        holder.crt_remv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog removfromcart = new AlertDialog.Builder(inflater.getContext())
                        .setTitle("Remove from cart")
                        .setMessage("Are you sure want to remove this item")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String cid = cartlistArrayList.get(position).getCart_id();

                                Call<String> call = ApiUtils.getProductApi().remvcart(cid);

                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        Toast.makeText(inflater.getContext(), cartlistArrayList.get(position).getCart_tab_name()+" removed from cart", Toast.LENGTH_SHORT).show();
                                        cartlistArrayList.remove(position);
                                        notifyItemRemoved(position);
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Toast.makeText(inflater.getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();

                removfromcart.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartlistArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tab_name,tab_count,tab_price, crt_remv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tab_name=itemView.findViewById(R.id.cart_name);
            tab_count=itemView.findViewById(R.id.cart_count);
            tab_price=itemView.findViewById(R.id.cart_price);
            crt_remv=itemView.findViewById(R.id.cart_remove);
        }
    }
}
