package com.example.harihara_medicals.Medicine;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harihara_medicals.Adapters.Cart_list_Adapter;
import com.example.harihara_medicals.Model.Cart_list;
import com.example.harihara_medicals.Model.ModelRes;
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

public class  Cartpage extends AppCompatActivity {

    TextView cart_tab_name,cart_tab_price,cart_tab_count,cart_detailed_price,cart_total_price;
    public  RecyclerView recyclerView;
    public Cart_list_Adapter cartListAdapter;
    int total=0;
    private Button placorder;
    private ImageView back_icon;
    private RelativeLayout relativeLayout, emptycartly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);

        recyclerView=findViewById(R.id.cart_recyclerview);
        recyclerView.setNestedScrollingEnabled(false);
        cart_detailed_price=findViewById(R.id.cart_detail_price);
        cart_total_price=findViewById(R.id.cart_total_price);
        placorder = findViewById(R.id.cart_btn);
        back_icon = findViewById(R.id.back_icon);
        relativeLayout = findViewById(R.id.cartlayout);
        emptycartly = findViewById(R.id.emtycartlayout);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getrespose();

        placorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plceorderr();
            }
        });

    }

    private void getrespose() {
        /*Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(ProductApi.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ProductApi api =retrofit.create(ProductApi.class);*/
        //ProductApi api =ApiUtils.getUrl();
        ProductApi api = ApiUtils.getScalarUrl();
        Call<String> call=api.getItem(SharedPreferencesManager.getCurrentUser().getUid());
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
            ArrayList<Cart_list> cart_listArrayList=new ArrayList<>();
            JSONArray dataarray =obj.getJSONArray("cart");

            for (int i=0;i<dataarray.length();i++){
                Cart_list cart_list=new Cart_list( );
                JSONObject dataobj= dataarray.getJSONObject(i);
                cart_list.setCart_tab_name(dataobj.getString("product_name"));
                cart_list.setCart_tab_count(dataobj.getString("product_count"));
                cart_list.setCart_tab_price(dataobj.getString("price"));
                cart_list.setCart_id(dataobj.getString("cart_id"));
                try {
                    total = total + Integer.parseInt(dataobj.getString("price"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                cart_listArrayList.add(cart_list);
            }
            cart_total_price.setText(String.valueOf(total));
            cart_detailed_price.setText(String.valueOf(total));
            cartListAdapter = new Cart_list_Adapter(this,cart_listArrayList);
            recyclerView.setAdapter(cartListAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
            if(cart_listArrayList.isEmpty()){
                relativeLayout.setVisibility(View.GONE);
                emptycartly.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void plceorderr() {
        Call<ModelRes> call = ApiUtils.getScalarUrl().plcord(SharedPreferencesManager.getCurrentUser().getUid(), cart_total_price.getText().toString());

        call.enqueue(new Callback<ModelRes>() {
            @Override
            public void onResponse(Call<ModelRes> call, Response<ModelRes> response) {

                ModelRes res = response.body();

                Toast.makeText(Cartpage.this, ""+res.getMessage(), Toast.LENGTH_SHORT).show();
                placorder.setClickable(false);
                placorder.setText("Order placed");
                Log.d("abc", ""+response);
                Log.d("abc", ""+response.body());
                Log.d("abc", ""+response.errorBody());
                Log.d("abc", ""+response.message());
                Log.d("abc", ""+response.code());
            }

            @Override
            public void onFailure(Call<ModelRes> call, Throwable t) {
                Toast.makeText(Cartpage.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("abc", ""+t.getMessage());
            }
        });
    }

}
