package com.example.harihara_medicals.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harihara_medicals.Model.My_appoinment_list;
import com.example.harihara_medicals.R;
import com.example.harihara_medicals.Retrofit.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class My_appoinment_list_adaptor extends RecyclerView.Adapter<My_appoinment_list_adaptor.MyViewHolder> {

    private ArrayList<My_appoinment_list> my_appoinment_listArrayList;
    private Context context;

    public My_appoinment_list_adaptor(ArrayList<My_appoinment_list> my_appoinment_listArrayList, Context context) {
        this.my_appoinment_listArrayList = my_appoinment_listArrayList;
        this.context = context;
    }

    @Override
    public My_appoinment_list_adaptor.MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.my_appointment_list,parent,false);
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
        holder.cn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog canclap = new AlertDialog.Builder(context)
                        .setTitle("Cancel")
                        .setMessage("Are you sure want to cancel this appointment")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String apid = my_appoinment_listArrayList.get(position).getApid();
                                //Toast.makeText(context, "Cancel btn clicked "+apid, Toast.LENGTH_SHORT).show();
                                Call<String> call = ApiUtils.getProductApi().cancelAppointment(apid);
                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        Toast.makeText(context, "Appointment Cancelled Successfully", Toast.LENGTH_SHORT).show();
                                        my_appoinment_listArrayList.remove(position);
                                        notifyItemRemoved(position);
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Toast.makeText(context, "Appointment Cancel failed, try again", Toast.LENGTH_SHORT).show();
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

                canclap.show();
            }
        });
    }

    public void updateList(List<My_appoinment_list> appoinmentLists){
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return my_appoinment_listArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dr_apm_name,dr_apm_spl,dr_apm_fees,dr_apm_date,dr_apm_time,dr_apm_ex,dr_apm_edu;
        Button cn_btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dr_apm_name=itemView.findViewById(R.id.dr_name);
            dr_apm_spl=itemView.findViewById(R.id.dr_edu1);
            dr_apm_fees=itemView.findViewById(R.id.dr_fees);
            dr_apm_date=itemView.findViewById(R.id.dr_date);
            dr_apm_time=itemView.findViewById(R.id.dr_time);
            dr_apm_ex=itemView.findViewById(R.id.dr_exp);
            dr_apm_edu=itemView.findViewById(R.id.dr_edu);
            cn_btn = itemView.findViewById(R.id.ap_cancl_btn);
        }
    }
}
