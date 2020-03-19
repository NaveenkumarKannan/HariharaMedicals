package com.example.harihara_medicals.Doctor;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    ImageView back_icon;
    TextView spc_title;
    String spc;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_doctor);

        recyclerView=findViewById(R.id.doctor_list_view);
        back_icon = findViewById(R.id.back_icon);
        spc_title = findViewById(R.id.signup_title);

        Intent intent = getIntent();
        String spcl = intent.getExtras().getString("spe");

        if (spcl.equals("gd")) {
            spc_title.setText("General Doctors");
        }

        if (spcl.equals("child")) {
            spc_title.setText("Child Doctors");
        }

        if (spcl.equals("skin")) {
            spc_title.setText("Skin Doctors");
        }

        if (spcl.equals("women")) {
            spc_title.setText("Women Doctors");
        }

        if (spcl.equals("home")) {
            spc_title.setText("Homeopathy Doctors");
        }

        if (spcl.equals("ay")) {
            spc_title.setText("Ayurveda Doctors");
        }

        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.BLUE));

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });



        getResponse(spcl);

    }

    private void getResponse(String spcl) {
        /*Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(ProductApi.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ProductApi api =retrofit.create(ProductApi.class);*/
        //ProductApi api =ApiUtils.getUrl();
        ProductApi api =ApiUtils.getScalarUrl();

        Call<String> call= api.getDoctorbySpe(spcl);
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
                doctor_list.setDid(dataobj.getString("did"));
                doctor_list.setDoctor_name(dataobj.getString("name"));
                doctor_list.setDoctor_spc(dataobj.getString("specialist"));
                doctor_list.setDoctor_address(dataobj.getString("address"));
                doctor_list.setDoctor_fees(dataobj.getString("fees"));
                doctor_list.setDoctor_exprience(dataobj.getString("experience"));
                doctor_list.setDoctor_num1(dataobj.getString("phone_number1"));
                doctor_list.setDoctor_num2(dataobj.getString("phone_number2"));
                doctor_list.setDid(dataobj.getString("did"));
                doctor_list.setDoctor_qu(dataobj.getString("qualification"));
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
