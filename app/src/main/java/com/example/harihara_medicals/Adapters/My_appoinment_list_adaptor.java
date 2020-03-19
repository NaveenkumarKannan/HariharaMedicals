package com.example.harihara_medicals.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harihara_medicals.Model.My_appoinment_list;
import com.example.harihara_medicals.R;

import java.util.ArrayList;

public class My_appoinment_list_adaptor extends RecyclerView.Adapter<My_appoinment_list_adaptor.MyViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<My_appoinment_list> my_appoinment_listArrayList;
    public My_appoinment_list_adaptor(Context ctm,ArrayList<My_appoinment_list> my_appoinment_listArrayList){
        inflater=LayoutInflater.from(ctm);
        this.my_appoinment_listArrayList=my_appoinment_listArrayList;
     }

    @Override
    public My_appoinment_list_adaptor.MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.my_appointment_list,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder( My_appoinment_list_adaptor.MyViewHolder holder, int position) {
        holder.dr_apm_name.setText(my_appoinment_listArrayList.get(position).getDr_name());
        holder.dr_apm_spl.setText(my_appoinment_listArrayList.get(position).getDr_spl());
        holder.dr_apm_fees.setText("Rs: "+my_appoinment_listArrayList.get(position).getDr_fees());
        holder.dr_apm_ex.setText(my_appoinment_listArrayList.get(position).getDr_ex()+" yrs of exp");
        holder.dr_apm_time.setText(my_appoinment_listArrayList.get(position).getDr_time());
        holder.dr_apm_date.setText(my_appoinment_listArrayList.get(position).getDr_date());
        holder.dr_apm_edu.setText(my_appoinment_listArrayList.get(position).getDr_edu());
    }

    @Override
    public int getItemCount() {
        return my_appoinment_listArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dr_apm_name,dr_apm_spl,dr_apm_fees,dr_apm_date,dr_apm_time,dr_apm_ex,dr_apm_edu;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dr_apm_name=itemView.findViewById(R.id.dr_name);
            dr_apm_spl=itemView.findViewById(R.id.dr_edu1);
            dr_apm_fees=itemView.findViewById(R.id.dr_fees);
            dr_apm_date=itemView.findViewById(R.id.dr_date);
            dr_apm_time=itemView.findViewById(R.id.dr_time);
            dr_apm_ex=itemView.findViewById(R.id.dr_exp);
            dr_apm_edu=itemView.findViewById(R.id.dr_edu);
        }
    }
}
