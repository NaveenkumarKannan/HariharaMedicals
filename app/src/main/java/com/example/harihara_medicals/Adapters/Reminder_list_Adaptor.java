package com.example.harihara_medicals.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harihara_medicals.R;
import com.example.harihara_medicals.Model.Reminder_list;
import com.example.harihara_medicals.Retrofit.ApiUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        holder.reminder_date.setText(reminder_listArrayList.get(position).getRemdate());
        holder.reminder_desc.setText(reminder_listArrayList.get(position).getRemdesc());
        holder.dltrem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog deleterem = new AlertDialog.Builder(inflater.getContext())
                        .setTitle("Delete Reminder")
                        .setMessage("Are you sure want to delete this reminder")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String remid = reminder_listArrayList.get(position).getReminderid();
                                Call<String> call = ApiUtils.getProductApi().deleteReminder(remid);
                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        Toast.makeText(inflater.getContext(), "Reminder deleted Successfully", Toast.LENGTH_SHORT).show();
                                        reminder_listArrayList.remove(position);
                                        notifyItemRemoved(position);
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Toast.makeText(inflater.getContext(), "Reminder deleted failed, try again", Toast.LENGTH_SHORT).show();
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

                deleterem.show();
            }
        });

        String date = reminder_listArrayList.get(position).getRemdate();
        String year = date.substring(0, 4);
        String mn = date.substring(5, 7);
        String dt = date.substring(8, 10);

        String time = reminder_listArrayList.get(position).getVisit_time();
        String hr = time.substring(0, 2);
        String mi = time.substring(3, 5);
        String a = time.substring(6, 8);
        String hrr;

        if(a.equals("pm")){
            int ba = Integer.parseInt(hr);
            hrr = String.valueOf(ba+12);
        } else{
            hrr = hr;
        }

        holder.addtocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int dy = Integer.parseInt(year);
                int dtt = Integer.parseInt(dt);
                int th = Integer.parseInt(hrr);
                int mnt = Integer.parseInt(mn)-1;

//                Toast.makeText(inflater.getContext(), " "+mn+" "+mnt, Toast.LENGTH_SHORT).show();

                try {
                    Intent calIntent = new Intent(Intent.ACTION_INSERT);
                    calIntent.setData(CalendarContract.Events.CONTENT_URI);
                    calIntent.putExtra(CalendarContract.Events.TITLE, reminder_listArrayList.get(position).getVisit_medical());
                    calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "place");
                    calIntent.putExtra(CalendarContract.Events.DESCRIPTION, reminder_listArrayList.get(position).getRemdesc());
                    Calendar startTime = Calendar.getInstance();
                    startTime.set(dy, mnt, dtt, th, Integer.parseInt(mi));
                    Calendar endTime = Calendar.getInstance();
                    startTime.set(dy, mnt, dtt, th, Integer.parseInt(mi));
                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                            startTime.getTimeInMillis());
                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                            endTime.getTimeInMillis());
                    inflater.getContext().startActivity(calIntent);
                }
                catch(Exception e)
                {
                    Toast.makeText(inflater.getContext(), ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return reminder_listArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView reminder_dr_appointment, reminder_time, reminder_date, reminder_desc;
        Button dltrem, addtocal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reminder_dr_appointment=itemView.findViewById(R.id.reminder_list_name);
            reminder_time=itemView.findViewById(R.id.reminder_list_time);
            reminder_date = itemView.findViewById(R.id.reminder_list_date);
            reminder_desc = itemView.findViewById(R.id.reminder_list_desc);
            addtocal = itemView.findViewById(R.id.addtocalbtn);
            dltrem=itemView.findViewById(R.id.dltbtn);
        }
    }
}
