package com.example.harihara_medicals.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harihara_medicals.Doctor.Doctor_appoinment;
import com.example.harihara_medicals.Medicine.Order_status;
import com.example.harihara_medicals.Model.MyOrders;
import com.example.harihara_medicals.R;

import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<MyOrders> myOrders;

    public MyOrderAdapter(Context context, ArrayList<MyOrders> myOrders) {
        this.context = context;
        this.myOrders = myOrders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.list_item_myorder,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ordername.setText("#"+myOrders.get(position).getOrder_name());
        holder.trcorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), Order_status.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ordername", myOrders.get(position).getOrder_name());
                intent.putExtra("orderstatus", myOrders.get(position).getStatus());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myOrders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView ordername;
        private Button trcorder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ordername = itemView.findViewById(R.id.orname);
            trcorder = itemView.findViewById(R.id.trckbtn);

        }

    }
}
