package com.example.harihara_medicals.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harihara_medicals.Doctor.Doctor_appoinment;
import com.example.harihara_medicals.Model.Doctor_list;
import com.example.harihara_medicals.R;

import java.util.ArrayList;

public class Doctor_list_adapter extends RecyclerView.Adapter<Doctor_list_adapter.MyViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<Doctor_list> doctor_listArrayList;

    public Doctor_list_adapter(Context ctx,ArrayList<Doctor_list> doctor_listArrayList){
        inflater=LayoutInflater.from(ctx);
        this.doctor_listArrayList=doctor_listArrayList;
    }

    @Override
    public Doctor_list_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.doctor_list,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Doctor_list_adapter.MyViewHolder holder,final int position) {
        String docid = doctor_listArrayList.get(position).getDid();
        holder.Dr__name.setText(doctor_listArrayList.get(position).getDoctor_name());
        holder.Dr_qu.setText(doctor_listArrayList.get(position).getDoctor_qu());
        holder.Dr_fees.setText("Rs-"+doctor_listArrayList.get(position).getDoctor_fees());
        holder.Dr_exp.setText(doctor_listArrayList.get(position).getDoctor_exprience()+" yrs of exp");

        String specl = doctor_listArrayList.get(position).getDoctor_spc();

        if(specl.equals("gd")){
            holder.Dr_spe.setText("General");
        }

        if(specl.equals("skin")){
            holder.Dr_spe.setText("Skin");
        }

        if(specl.equals("child")){
            holder.Dr_spe.setText("Child");
        }

        if(specl.equals("women")){
            holder.Dr_spe.setText("Women");
        }

        if(specl.equals("home")){
            holder.Dr_spe.setText("Homeopathy");
        }

        if(specl.equals("ay")){
            holder.Dr_spe.setText("Ayurveda");
        }

        holder.book_dr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String drname=holder.Dr__name.getText().toString();
                String drspc=holder.Dr_spe.getText().toString();
                String drfee=doctor_listArrayList.get(position).getDoctor_fees();
                String drexp=doctor_listArrayList.get(position).getDoctor_exprience();
                String draddress=doctor_listArrayList.get(position).getDoctor_address();
                String drnum1=doctor_listArrayList.get(position).getDoctor_num1();
                String drnum2=doctor_listArrayList.get(position).getDoctor_num2();
                String qual=doctor_listArrayList.get(position).getDoctor_qu();
                /*String drfee=doctor_listArrayList.get(position).getDoctor_fees();
                String drexp=doctor_listArrayList.get(position).getDoctor_exprience();*/
                Intent intent=new Intent(v.getContext(), Doctor_appoinment.class);
                intent.putExtra("Dr_name",drname);
                intent.putExtra("Dr_spc",drspc);
                intent.putExtra("Dr_address",draddress);
                intent.putExtra("Dr_qu",qual);
                //intent.putExtra("Dr_num",drnum);
                intent.putExtra("Dr_fees",drfee);
                intent.putExtra("Dr_exp",drexp);
                intent.putExtra("Dr_num1",drnum1);
                intent.putExtra("Dr_num2",drnum2);
                intent.putExtra("did", docid);

                //Log.d("Drnum1", ""+drnum);

                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return doctor_listArrayList.size();
    }

    public void filterlist(ArrayList<Doctor_list> listArrayList) {
        doctor_listArrayList = listArrayList;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Dr__name,Dr_qu,Dr_num,Dr_fees,Dr_exp,Dr_spe;
        Button call_dr,book_dr;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Dr__name=(TextView) itemView.findViewById(R.id.general_dr_name);
            Dr_qu=itemView.findViewById(R.id.general_dr_edu);
            book_dr=itemView.findViewById(R.id.general_book_dr);
            Dr_fees=itemView.findViewById(R.id.general_dr_fees);
            Dr_exp=itemView.findViewById(R.id.general_dr_exp);
            Dr_spe=itemView.findViewById(R.id.general_dr_edu1);
        }
    }
}
