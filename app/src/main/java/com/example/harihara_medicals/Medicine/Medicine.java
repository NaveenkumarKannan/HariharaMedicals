package com.example.harihara_medicals.Medicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.harihara_medicals.Adapters.MedicienAdapter;
import com.example.harihara_medicals.Model.Medicien_list;
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

public class Medicine extends AppCompatActivity {
    public RecyclerView recyclerView;
    public MedicienAdapter medicienAdapter;
    TextView textOne;
    ImageView cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        textOne = findViewById(R.id.textOne);
        recyclerView = findViewById(R.id.recy);
        cart=findViewById(R.id.cart_item);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Medicine.this,Cartpage.class));
            }
        });
        fetchJSON();
        Log.d("res","Medicien");



    }

    private void fetchJSON() {

        /*Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(ProductApi.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ProductApi api =retrofit.create(ProductApi.class);*/
        //ProductApi api =ApiUtils.getUrl();
        ProductApi api =ApiUtils.getScalarUrl();
        Call<String> call=api.getMedicen();
        log("step o");
        call.enqueue(new Callback<String>() {
            @Override

            public void onResponse(Call<String> call, Response<String> response) {

                log("step 1");
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        String jsonresponse=response.body().toString();
                        writeRecycler(jsonresponse);
                    }
                    else {
                        log("step 2");                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void writeRecycler(String response) {
        try {
            log("step 3");
            JSONObject obj=new JSONObject(response);
            ArrayList<Medicien_list> medicienlistArrayList=new ArrayList<>();
            JSONArray dataarray=obj.getJSONArray("product");
            log("step 4");
                for(int i=0;i<dataarray.length();i++){
                    Medicien_list medicien=new Medicien_list();
                    JSONObject dataojb=dataarray.getJSONObject(i);
                    medicien.setMedicien_name(dataojb.getString("productname"));
                    medicien.setMedicien_price(dataojb.getString("price"));
                    medicienlistArrayList.add(medicien);
                    Log.d("done",""+response.toString());
                }
                medicienAdapter=new MedicienAdapter(this,medicienlistArrayList);
                recyclerView.setAdapter(medicienAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                medicienAdapter.setOnItemClickListener(new MedicienAdapter.OnClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        try {
                            int count_item;
                            String presentValStr=textOne.getText().toString();
                            count_item=Integer.parseInt(presentValStr);
                            count_item++;
                            textOne.setText(String.valueOf(count_item));
                            textOne.setVisibility(View.VISIBLE);
                            log("cart_count = "+count_item);
                            log("Count = "+medicienlistArrayList.get(position).getMedi_count());
                            sendpost(medicienlistArrayList.get(position).getMedicien_name(), String.valueOf(medicienlistArrayList.get(position).getMedi_count()),medicienlistArrayList.get(position).getMedicien_price());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }

                });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void sendpost(String pname, String pcount, String price) {
        //Log.e("data_inserted","cart added");

        Call<String> call= ApiUtils.getProductApi().getCart(pname,pcount,price);
        // Log.d("data_failed","nope");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("data_inserted","cart added");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("failed",t.getMessage());
            }
        });
    }

    private void log(String message) {
        Log.e(getClass().getSimpleName(),message);
    }



}

