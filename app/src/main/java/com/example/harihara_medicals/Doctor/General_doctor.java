package com.example.harihara_medicals.Doctor;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harihara_medicals.Adapters.Doctor_list_adapter;
import com.example.harihara_medicals.Model.Doctor_list;
import com.example.harihara_medicals.R;
import com.example.harihara_medicals.Retrofit.ApiUtils;
import com.example.harihara_medicals.Retrofit.ProductApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class General_doctor extends AppCompatActivity {
    private Doctor_list_adapter doctor_list_adapter;
    private RecyclerView recyclerView;
    ProgressBar progressBar;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_doctor);

        recyclerView=findViewById(R.id.doctor_list_view);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.BLUE));


        getResponse();

    }

    private void getResponse() {
        /*Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(ProductApi.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ProductApi api =retrofit.create(ProductApi.class);*/
        //ProductApi api =ApiUtils.getUrl();
        ProductApi api =ApiUtils.getScalarUrl();

        Call<String> call= api.getDoctors();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        String jsonresponse =response.body().toString();
                        writedata(jsonresponse);




                    }
                    else {
                        Log.i("onemptyresponce","empty response");
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
            ArrayList<Doctor_list> doctor_listArrayList=new ArrayList<>();
            JSONArray dataarray =obj.getJSONArray("doctor");
            for (int i=0;i<dataarray.length();i++){
                Doctor_list doctor_list=new Doctor_list( );
                JSONObject dataobj= dataarray.getJSONObject(i);
                doctor_list.setDoctor_name(dataobj.getString("name"));
                doctor_list.setDoctor_spc(dataobj.getString("specialist"));
                doctor_list.setDoctor_address(dataobj.getString("address"));
                doctor_list.setDoctor_fees(dataobj.getString("fees"));
                doctor_list.setDoctor_exprience(dataobj.getString("experience"));
                doctor_listArrayList.add(doctor_list);
            }
           /* for (int j=0;j<doctor_listArrayList.size();j++){
                Dr_name.setText(Dr_name.getText()+doctor_listArrayList.get(j).getDoctor_name()+"\n");

            }*/

            progressBar.setVisibility(View.GONE);
            doctor_list_adapter = new Doctor_list_adapter(this,doctor_listArrayList);
            recyclerView.setAdapter(doctor_list_adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
