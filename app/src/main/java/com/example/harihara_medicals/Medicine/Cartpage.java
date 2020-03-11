package com.example.harihara_medicals.Medicine;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harihara_medicals.Adapters.Cart_list_Adapter;
import com.example.harihara_medicals.Model.Cart_list;
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

public class  Cartpage extends AppCompatActivity {
    TextView cart_tab_name,cart_tab_price,cart_tab_count,cart_detailed_price,cart_total_price;
    public  RecyclerView recyclerView;
    public Cart_list_Adapter cartListAdapter;
    int total=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);
        recyclerView=findViewById(R.id.cart_recyclerview);
        recyclerView.setNestedScrollingEnabled(false);
        cart_detailed_price=findViewById(R.id.cart_detail_price);
        cart_total_price=findViewById(R.id.cart_total_price);

        getrespose();
    }

    private void getrespose() {
        /*Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(ProductApi.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ProductApi api =retrofit.create(ProductApi.class);*/
        //ProductApi api =ApiUtils.getUrl();
        ProductApi api = ApiUtils.getScalarUrl();
        Call<String> call=api.getItem();
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
