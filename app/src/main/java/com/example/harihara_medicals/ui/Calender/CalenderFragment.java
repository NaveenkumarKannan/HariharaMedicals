package com.example.harihara_medicals.ui.Calender;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.harihara_medicals.Retrofit.ApiUtils;
import com.example.harihara_medicals.DbHander;
import com.example.harihara_medicals.Retrofit.ProductApi;
import com.example.harihara_medicals.R;
import com.example.harihara_medicals.Model.Reminder_list;
import com.example.harihara_medicals.Adapters.Reminder_list_Adaptor;
import com.example.harihara_medicals.utils.AlarmReceiver;
import com.example.harihara_medicals.utils.SharedPreferencesManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalenderFragment extends Fragment {

    private static final long SIXTY_SECONDS =6000 ;
    private static final long DELAY_TIME = 1000;
    final String TAG = getClass().getSimpleName();
    CalendarView calendarView;
    FloatingActionButton floatingActionButton;
    DbHander dbHander;
    EditText reminder_title,reminder_date,reminder_time,reminder_location,reminder_description;
    final Calendar mycalender=Calendar.getInstance();
    private Reminder_list_Adaptor reminder_list_adaptor;
    private RecyclerView recyclerView;
    private ProgressBar calprogrs;
    private SwipeRefreshLayout refreshReminders;
/*
    private final static Handler handler = new Handler();
*/
    private String usrid = SharedPreferencesManager.getCurrentUser().getUid();

    private int notificationId = 1;
    private RelativeLayout remly, emremly;

    public  View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calender,null);

        calprogrs = root.findViewById(R.id.calprogress);
        refreshReminders = root.findViewById(R.id.calremswip);
        remly = root.findViewById(R.id.remlayout);
        emremly = root.findViewById(R.id.emremly);

        calendarView=root.findViewById(R.id.calender_calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String Date
                        = dayOfMonth + "-"
                        + (month + 1) + "-" + year;
            }
        });

        floatingActionButton=root.findViewById(R.id.calendar_remainder);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.calendar_remainder);
                final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mycalender.set(Calendar.YEAR,year);
                        mycalender.set(Calendar.MONTH,month);
                        mycalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        updateLabel();
                    }
                };

                reminder_title=dialog.findViewById(R.id.remainder_title);
                reminder_time=dialog.findViewById(R.id.remainder_time);
                reminder_date=dialog.findViewById(R.id.remainder_remainder);
                reminder_description=dialog.findViewById(R.id.remainder_description);
                reminder_location=dialog.findViewById(R.id.remainder_location);

                reminder_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar mcurrentTime=Calendar.getInstance();
                        final int hour =mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minutes=mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimepicker;

                        mTimepicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
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
                                reminder_time.setText(formattedTime);
                            }
                        },hour,minutes,false);
                        mTimepicker.setTitle("Select time");
                        mTimepicker.show();

                    }
                });

                reminder_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(getActivity(),date,mycalender.get(Calendar.YEAR),
                                mycalender.get(Calendar.MONTH),mycalender.get(Calendar.DAY_OF_MONTH)).show();

                    }
                });

                TextView done = dialog.findViewById(R.id.remainder_done);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(TextUtils.isEmpty(reminder_title.getText().toString())){
                            reminder_title.setError("Please enter title");
                            reminder_title.requestFocus();
                            return;
                        }

                        if(TextUtils.isEmpty(reminder_time.getText().toString())){
                            reminder_time.setError("Please choose time");
                            reminder_time.requestFocus();
                            return;
                        }

                        if(TextUtils.isEmpty(reminder_date.getText().toString())){
                            reminder_date.setError("Please choose date");
                            reminder_date.requestFocus();
                            return;
                        }

                        if(TextUtils.isEmpty(reminder_description.getText().toString())){
                            reminder_description.setError("Please enter description");
                            reminder_description.requestFocus();
                            return;
                        }

                        if(TextUtils.isEmpty(reminder_location.getText().toString())){
                            reminder_location.setError("Please enter location");
                            reminder_location.requestFocus();
                            return;
                        }

                        try {
                            sendpost(reminder_title.getText().toString(),reminder_time.getText().toString(),reminder_date.getText().toString(),reminder_location.getText().toString(),reminder_description.getText().toString());
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        //Toast.makeText(getContext(),"Remainder Done",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            }
        });
        recyclerView=root.findViewById(R.id.reminder_recy);
        recyclerView.setNestedScrollingEnabled(false);
        /*Runnable runnable = new Runnable() {
             @Override
             public  void run() {
                 // polling code
                 getResponce();

                 handler.postDelayed(this, SIXTY_SECONDS);
             }
         };
         Handler.postDelayed(runnable, DELAY_TIME);*/

        refreshReminders.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getResponce();
                refreshReminders.setRefreshing(false);
            }
        });

        getResponce();

        return root;

    }

    private void getResponce() {
        /*Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(ProductApi.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ProductApi api =retrofit.create(ProductApi.class);*/
        //ProductApi api =ApiUtils.getUrl();
        ProductApi api =ApiUtils.getScalarUrl();
        Call<String> call=api.getReminders(usrid);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                log("res 0");
                if (response.isSuccessful()){
                    log("res success");
                    if (response.body()!=null){
                       log("keep doing");
                        String jsonresponse =response.body().toString();
                        writedata(jsonresponse);
                    }
                    else {
                        log("empty response");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void writedata(String response) {
        calprogrs.setVisibility(View.VISIBLE);
        try {
            log("Step 0");
            JSONObject obj=new JSONObject(response);
            ArrayList<Reminder_list> reminder_listArrayList=new ArrayList<>();
            JSONArray dataarray =obj.getJSONArray("reminder");
            log("Step 1");
            for (int i=0;i<dataarray.length();i++){
                log("Step 2");
                Reminder_list reminder_list=new Reminder_list();
                JSONObject dataobj= dataarray.getJSONObject(i);
                log("Step 3");
                reminder_list.setVisit_medical(dataobj.getString("title"));
                reminder_list.setVisit_time(dataobj.getString("time"));
                reminder_list.setRemdate(dataobj.getString("date"));
                reminder_list.setRemdesc(dataobj.getString("description"));
                reminder_list.setReminderid(dataobj.getString("id"));

                reminder_listArrayList.add(reminder_list);
            }
            log("Step 4");
           /* for (int j=0;j<doctor_listArrayList.size();j++){
                Dr_name.setText(Dr_name.getText()+doctor_listArrayList.get(j).getDoctor_name()+"\n");

            }*/

           if(reminder_listArrayList.isEmpty()){
               emremly.setVisibility(View.VISIBLE);
           } else {
               emremly.setVisibility(View.GONE);
           }

            reminder_list_adaptor= new Reminder_list_Adaptor(recyclerView.getContext(),reminder_listArrayList);
            recyclerView.setAdapter(reminder_list_adaptor);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            calprogrs.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void log(String message) {
        Log.e(TAG,message);
    }

    private void updateLabel() {
            String myfromat="yyyy-MM-dd";
            SimpleDateFormat sdf=new SimpleDateFormat(myfromat, Locale.UK);
            reminder_date.setText(sdf.format(mycalender.getTime()));
    }

    private void sendpost(String title, String time, String date, String loc, String desc) throws  IOException {
        Call<Void> mkrem = ApiUtils.getScalarUrl().makeReminder(desc, date, title, loc, time, usrid);
        mkrem.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getContext(),"Remainder Done",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(),"Try again",Toast.LENGTH_SHORT).show();
            }
        });
    }

}