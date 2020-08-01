package com.example.harihara_medicals.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.harihara_medicals.Adapters.Doctor_list_adapter;
import com.example.harihara_medicals.Model.Doctor_list;
import com.example.harihara_medicals.Model.Medicien_list;
import com.example.harihara_medicals.R;
import com.example.harihara_medicals.Retrofit.ApiUtils;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchDoctors extends AppCompatActivity {
    private Doctor_list_adapter doctor_list_adapter;
    RecyclerView recyclerView;
    private static final String TAG = "SearchDoctors";
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<Doctor_list> doctor_listArrayList;
    ProgressBar progressBar;
    EditText edt_sd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctors);

        //String token = FirebaseInstanceId.getInstance().getToken();

        //Log.d("token", ""+token);

        //Toast.makeText(this, ""+token, Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recycler_view_sd);
        progressBar = findViewById(R.id.progressbar_sd);
        swipeRefreshLayout = findViewById(R.id.swipe_ref_sd);
        edt_sd = findViewById(R.id.edt_sd);

        edt_sd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getResponse();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        getResponse();
    }

    private void getResponse() {

        Call<String> call = ApiUtils.getScalarUrl().listDoctors();
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Toast.makeText(SearchDoctors.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, ""+response);
                Log.d(TAG, ""+response.body());
                Log.d(TAG, ""+response.message());
                Log.d(TAG, ""+response.isSuccessful());
                Log.d(TAG, ""+response.toString());
                writeData(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(SearchDoctors.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, ""+t.getLocalizedMessage());
                Log.d(TAG, ""+t);
                Log.d(TAG, ""+t.getMessage());
            }
        });

    }

    private void writeData(String response) {

        try {
            JSONObject obj = new JSONObject(response);
            doctor_listArrayList = new ArrayList<>();
            JSONArray data_array = obj.getJSONArray("doctor");
            for (int i = 0; i < data_array.length(); i++) {
                Doctor_list doctor_list = new Doctor_list();
                JSONObject data_obj = data_array.getJSONObject(i);
                doctor_list.setDid(data_obj.getString("did"));
                doctor_list.setDoctor_name(data_obj.getString("name"));
                doctor_list.setDoctor_spc(data_obj.getString("specialist"));
                doctor_list.setDoctor_address(data_obj.getString("address"));
                doctor_list.setDoctor_fees(data_obj.getString("fees"));
                doctor_list.setDoctor_exprience(data_obj.getString("experience"));
                doctor_list.setDoctor_num1(data_obj.getString("phone_number1"));
                doctor_list.setDoctor_num2(data_obj.getString("phone_number2"));
                doctor_list.setDid(data_obj.getString("did"));
                doctor_list.setDoctor_qu(data_obj.getString("qualification"));
                doctor_listArrayList.add(doctor_list);
            }
            progressBar.setVisibility(View.GONE);
            doctor_list_adapter = new Doctor_list_adapter(this, doctor_listArrayList);
            recyclerView.setAdapter(doctor_list_adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void filter(String text){
        ArrayList<Doctor_list> listArrayList = new ArrayList<>();

        for(Doctor_list dt_list : doctor_listArrayList){
            if(dt_list.getDoctor_name().toLowerCase().contains(text.toLowerCase()) || dt_list.getDoctor_spc().toLowerCase().contains(text.toLowerCase())){
                listArrayList.add(dt_list);
            }
        }

        doctor_list_adapter.filterlist(listArrayList);
    }

}
