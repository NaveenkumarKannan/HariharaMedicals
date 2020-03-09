package com.example.harihara_medicals.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harihara_medicals.R;
import com.example.harihara_medicals.Model.Reminder_list;

import java.util.ArrayList;

public class Reminder_list_Adaptor extends RecyclerView.Adapter<Reminder_list_Adaptor.MyViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<Reminder_list> reminder_listArrayList;
    public Reminder_list_Adaptor (Context ctm, ArrayList<Reminder_list> reminder_listArrayList){
        inflater=LayoutInflater.from(ctm);
        this.reminder_listArrayList=reminder_listArrayList;
    }

    @Override
    public Reminder_list_Adaptor.MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.reminder_list,parent,false);
            MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        holder.reminder_dr_appointment.setText(reminder_listArrayList.get(position).getVisit_medical());
        holder.reminder_time.setText(reminder_listArrayList.get(position).getVisit_time());


    }

    @Override
    public int getItemCount() {
        return reminder_listArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView reminder_dr_appointment,reminder_time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reminder_dr_appointment=itemView.findViewById(R.id.reminder_list_name);
            reminder_time=itemView.findViewById(R.id.reminder_list_time);
        }
    }
}
