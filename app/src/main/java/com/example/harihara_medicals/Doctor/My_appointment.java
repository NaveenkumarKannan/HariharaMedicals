package com.example.harihara_medicals.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.harihara_medicals.Adapters.My_appoinment_list_adaptor;
import com.example.harihara_medicals.Model.My_appoinment_list;
import com.example.harihara_medicals.Model.User;
import com.example.harihara_medicals.R;
import com.example.harihara_medicals.Retrofit.ApiUtils;
import com.example.harihara_medicals.Retrofit.ProductApi;
import com.example.harihara_medicals.utils.SharedPreferencesManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class My_appointment extends AppCompatActivity {

    private My_appoinment_list_adaptor my_appoinment_list_adaptor;
    private RecyclerView recyclerView;
    private ImageView bk_btn;
    private TextView title;
    private SwipeRefreshLayout sRlmyapt;
    private ProgressBar progressBarmyapp;
    private RelativeLayout aplayout, emtyaply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_appointment);

        recyclerView = findViewById(R.id.my_appointment_recy);
        bk_btn = findViewById(R.id.back_icon);
        title = findViewById(R.id.title);
        sRlmyapt = findViewById(R.id.swiperefmyapts);
        progressBarmyapp = findViewById(R.id.progressBarmyapp);
        aplayout = findViewById(R.id.rellayout);
        emtyaply = findViewById(R.id.emptyaply);

        bk_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        title.setText("My Appointment");

        sRlmyapt.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getResponse();
                sRlmyapt.setRefreshing(false);
            }
        });

        getResponse();
    }

    private void getResponse() {
        /*Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(ProductApi.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ProductApi api =retrofit.create(ProductApi.class);*/
        //ProductApi api =ApiUtils.getUrl();
        ProductApi api = ApiUtils.getScalarUrl();

        User user = SharedPreferencesManager.getCurrentUser();
        String user_id = user.getUid();

        progressBarmyapp.setVisibility(View.VISIBLE);
        Call<String> call = api.getAppoinments(user_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String jsonresponse = response.body().toString();
                        writedata(jsonresponse);
                    } else {
                        Log.i("onemptyresponce", "empty response");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void writedata(String response) {
        try {
            JSONObject obj=new JSONObject(response);
            ArrayList<My_appoinment_list> my_appoinment_listArrayList=new ArrayList<>();
            JSONArray dataarray =obj.getJSONArray("appointment");
            for (int i=0;i<dataarray.length();i++){
                My_appoinment_list my_appoinment_list=new My_appoinment_list();
                JSONObject dataobj= dataarray.getJSONObject(i);
                my_appoinment_list.setDr_name(dataobj.getString("doctor_name"));
                my_appoinment_list.setDr_spl(dataobj.getString("specialist"));
                my_appoinment_list.setDr_fees(dataobj.getString("fees"));
                my_appoinment_list.setDr_ex(dataobj.getString("experience"));
                my_appoinment_list.setDr_time(dataobj.getString("time"));
                my_appoinment_list.setDr_date(dataobj.getString("date"));
                my_appoinment_list.setDr_edu(dataobj.getString("qualification"));
                my_appoinment_list.setApid(dataobj.getString("ap_id"));
                my_appoinment_listArrayList.add(my_appoinment_list);
            }

            if(my_appoinment_listArrayList.isEmpty()){
                aplayout.setVisibility(View.GONE);
                emtyaply.setVisibility(View.VISIBLE);
            }

            my_appoinment_list_adaptor = new My_appoinment_list_adaptor(my_appoinment_listArrayList, this);
            recyclerView.setAdapter(my_appoinment_list_adaptor);
            my_appoinment_list_adaptor.updateList(my_appoinment_listArrayList);
            progressBarmyapp.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
