package com.example.harihara_medicals.Medicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harihara_medicals.Adapters.Doctor_list_adapter;
import com.example.harihara_medicals.Adapters.MyOrderAdapter;
import com.example.harihara_medicals.Model.Doctor_list;
import com.example.harihara_medicals.Model.MyOrders;
import com.example.harihara_medicals.R;
import com.example.harihara_medicals.Retrofit.ApiUtils;
import com.example.harihara_medicals.utils.SharedPreferencesManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class My_order_activity extends AppCompatActivity {

    ImageView back_icon;
    private RecyclerView recyclerView;
    private MyOrderAdapter myOrderAdapter;
    private ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView emptymyordtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order_activity);

        back_icon = findViewById(R.id.back_icon);
        recyclerView = findViewById(R.id.my_order_recy);
        progressBar = findViewById(R.id.progressbarmyorder);
        swipeRefreshLayout = findViewById(R.id.swiprefmyord);
        emptymyordtxt = findViewById(R.id.emptymyordtxt);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

        progressBar.setVisibility(View.VISIBLE);

        Call<String> call = ApiUtils.getScalarUrl().myorders(SharedPreferencesManager.getCurrentUser().getUid());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                //Toast.makeText(My_order_activity.this, ""+response.body(), Toast.LENGTH_SHORT).show();

                Log.d("myorder", ""+response.message());
                Log.d("myorder", ""+response.body());
                Log.d("myorder", ""+response.code());
                Log.d("myorder", ""+response.errorBody());
                Log.d("myorder", ""+response);

                try {
                    JSONObject obj=new JSONObject(response.body());
                    ArrayList<MyOrders> myOrdersArrayList=new ArrayList<>();
                    JSONArray dataarray1 =obj.getJSONArray("orders");

                    for (int i=0;i<dataarray1.length();i++){
                        MyOrders myOrders = new MyOrders();
                        JSONObject dataobj= dataarray1.getJSONObject(i);
                        myOrders.setOrder_name(dataobj.getString("order_name"));
                        myOrders.setStatus(dataobj.getString("status"));

                        myOrdersArrayList.add(myOrders);
                    }

                    if(myOrdersArrayList.isEmpty()){
                        emptymyordtxt.setVisibility(View.VISIBLE);
                    } else{
                        emptymyordtxt.setVisibility(View.GONE);
                    }

                    progressBar.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
                    myOrderAdapter = new MyOrderAdapter(getApplicationContext(), myOrdersArrayList);
                    recyclerView.setAdapter(myOrderAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(My_order_activity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
