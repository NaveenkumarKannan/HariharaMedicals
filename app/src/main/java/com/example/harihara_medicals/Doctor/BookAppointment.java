package com.example.harihara_medicals.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.harihara_medicals.HomePageActivity;
import com.example.harihara_medicals.R;
import com.example.harihara_medicals.Retrofit.ApiUtils;
import com.example.harihara_medicals.Retrofit.ProductApi;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAppointment extends AppCompatActivity {
    Button book_appointment;
    TextView dr_name,dr_spc,dr_fee,dr_ex,cal_date;
    CalendarView dr_date;
    TextView settime;
    ImageView back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_appointment);
        book_appointment=findViewById(R.id.book_appointment);
        book_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendpost(dr_name.getText().toString(),dr_spc.getText().toString(),dr_fee.getText().toString(),dr_ex.getText().toString(),settime.getText().toString(),cal_date.getText().toString());
                    final Dialog dialog=new Dialog(BookAppointment.this);
                    dialog.setContentView(R.layout.book_appointment_dialog);

                    TextView done=dialog.findViewById(R.id.appointment_done);
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(),"appointment done....",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            startActivity(new Intent(BookAppointment.this, HomePageActivity.class));
                        }
                    });
                    dialog.show();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        });

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });



        dr_name=findViewById(R.id.book_appointment_dr_name);
        dr_spc=findViewById(R.id.book_appointment_dr_edu);
        dr_ex=findViewById(R.id.book_appointment_dr_exp);
        dr_fee=findViewById(R.id.book_appointment_dr_fees);
        cal_date=findViewById(R.id.calender_date);
        Intent intent=getIntent();
        String Dr_name=intent.getStringExtra("Dr_name");
        String Dr_spc=intent.getStringExtra("Dr_spc");
        String Dr_fees=intent.getStringExtra("Dr_fees");
        String Dr_epx=intent.getStringExtra("Dr_epx");
        dr_fee.setText(Dr_fees+"Rs");
        dr_ex.setText(Dr_epx+"yrs of epx");
        dr_name.setText(Dr_name);
        dr_spc.setText(Dr_spc);
        settime=findViewById(R.id.book_appointment_time1);
        settime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime=Calendar.getInstance();
                final int hour =mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minutes=mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimepicker;

                mTimepicker = new TimePickerDialog(BookAppointment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String time=hourOfDay + ":" + minute;

                        SimpleDateFormat fmt=new SimpleDateFormat("hh:mm");
                        Date date=null;
                        try {
                            date=fmt.parse(time);
                        }catch (ParseException e){
                            e.printStackTrace();
                        }
                        SimpleDateFormat fmtout=new SimpleDateFormat("hh:mm aa");
                                String formattedTime=fmtout.format(date);
                        settime.setText(formattedTime);
                    }
                },hour,minutes,false);
                mTimepicker.setTitle("Select time");
                mTimepicker.show();
            }
        });
        dr_date=findViewById(R.id.book_appointment_calender);
        dr_date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                cal_date.setText(year+"-"+month+"-"+dayOfMonth);

            }
        });

    }

    private void sendpost(String dname, String spcl, String fee, String experience, String time, String date) throws IOException {
        //ProductApi productApi= ApiUtils.getProductApi();
        Call<Void> call=ApiUtils.getProductApi().getAppointment(dname,fee,experience,spcl,time,date);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(BookAppointment.this, "data Inserted successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(BookAppointment.this, "Error", Toast.LENGTH_SHORT).show();
                Log.e("OnFailure", t.getMessage());
            }
        });
    }


}
