package com.example.harihara_medicals.Medicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harihara_medicals.Adapters.MedicienAdapter;
import com.example.harihara_medicals.Model.Medicien_list;
import com.example.harihara_medicals.R;
import com.example.harihara_medicals.Retrofit.ApiUtils;
import com.example.harihara_medicals.Retrofit.ProductApi;
import com.example.harihara_medicals.Retrofit.RetrofitClient;
import com.example.harihara_medicals.utils.SharedPreferencesManager;

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
    LinearLayoutManager layoutManager;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    EditText prodctserch;
    ArrayList<Medicien_list> medicienlistArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        textOne = findViewById(R.id.textOne);
        recyclerView = findViewById(R.id.recy);
        cart=findViewById(R.id.cart_item);
        progressBar = findViewById(R.id.prbar);
        swipeRefreshLayout = findViewById(R.id.swiperef);
        prodctserch = findViewById(R.id.product_searchh);

        prodctserch.addTextChangedListener(new TextWatcher() {
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



        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Medicine.this,Cartpage.class));
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchJSON();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        fetchJSON();

        Log.d("res","Medicien");

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    //fetchJSON();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            Toast.makeText(Medicine.this, "pg", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }

    private void filter(String text){
        ArrayList<Medicien_list> listArrayList = new ArrayList<>();

        for(Medicien_list medlist : medicienlistArrayList){
            if(medlist.getMedicien_name().toLowerCase().contains(text.toLowerCase())){
                listArrayList.add(medlist);
            }
        }

        medicienAdapter.filterlist(listArrayList);
    }

    private void fetchJSON() {

        /*Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(ProductApi.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ProductApi api =retrofit.create(ProductApi.class);*/
        //ProductApi api =ApiUtils.getUrl();
        ProductApi api = ApiUtils.getScalarUrl();
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
                        log("step 2");
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void writeRecycler(String response) {
        progressBar.setVisibility(View.VISIBLE);
        try {
            log("step 3");
            JSONObject obj=new JSONObject(response);
            medicienlistArrayList=new ArrayList<>();
            JSONArray dataarray=obj.getJSONArray("product");
            log("step 4");
                for(int i=0;i<dataarray.length();i++){
                    Medicien_list medicien=new Medicien_list();
                    JSONObject dataojb=dataarray.getJSONObject(i);
                    medicien.setMedicien_name(dataojb.getString("productname"));
                    medicien.setMedicien_price(dataojb.getInt("price"));
                    medicien.setMed_id(dataojb.getString("id"));
                    medicienlistArrayList.add(medicien);
                    Log.d("done",""+response.toString());
                }
                medicienAdapter=new MedicienAdapter(this,medicienlistArrayList);
                recyclerView.setAdapter(medicienAdapter);
                layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
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
                            //Toast.makeText(Medicine.this, ""+medicienlistArrayList.get(position).getMedicien_name()+" "+medicienlistArrayList.get(position).getMedi_count()+" "+medicienlistArrayList.get(position).getMedicien_price(), Toast.LENGTH_SHORT).show();
                            String count = String.valueOf(medicienlistArrayList.get(position).getMedi_count());
                            String userid = SharedPreferencesManager.getCurrentUser().getUid();
                            String pric = String.valueOf(medicienlistArrayList.get(position).getMedicien_price()*medicienlistArrayList.get(position).getMedi_count());
                            String prid = medicienlistArrayList.get(position).getMed_id();

                            //userid, medicienlistArrayList.get(position).getMedicien_name(), count, medicienlistArrayList.get(position).getMedicien_price()

                            sendpost(userid, prid, medicienlistArrayList.get(position).getMedicien_name(), count, pric);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }

                });


        } catch (JSONException e) {
            e.printStackTrace();
        }
        progressBar.setVisibility(View.GONE);
    }

    private void sendpost(String userid, String prid, String pname, String pcount, String price) {

        Call<String> call = ApiUtils.getScalarUrl().addtcart(userid, prid, pname, pcount, price);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(Medicine.this, pname+" added to cart", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Medicine.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Log.e("data_inserted","cart added");

        //Call<String> call= ApiUtils.getProductApi().getCart(pname,pcount,price);
        // Log.d("data_failed","nope");
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Log.e("data_inserted","cart added");
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.e("failed",t.getMessage());
//            }
//        });
    }

    private void log(String message) {
        Log.e(getClass().getSimpleName(),message);
    }



}

