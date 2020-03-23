package com.example.harihara_medicals.ui.medicien;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.harihara_medicals.Adapters.MedicienAdapter;
import com.example.harihara_medicals.Model.Medicien_list;
import com.example.harihara_medicals.R;

public class MedicienFragment extends Fragment {

    private Medicien_list medicienlist;
    private RecyclerView recyclerView;
    private MedicienAdapter medicienAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final  View view=inflater.inflate(R.layout.medicien_fragment, null);
        recyclerView=view.findViewById(R.id.medicine_view);
        /*getResponce();*/

        return view;
    }

    /*private void getResponce() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ProductApi.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ProductApi api=retrofit.create(ProductApi.class);
        Call<String> call=api.getMedicen();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        String jsonresponce=response.body().toString();
                        writedata(jsonresponce);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void writedata(String responce) {
        try {
            JSONObject obj=new JSONObject(responce);
            ArrayList<Medicien_list> medicienlistArrayList =new ArrayList<>();
            JSONArray dataarray=obj.getJSONArray("product");
            for (int i=0;i<dataarray.length();i++){
                Medicien_list medicienlist=new Medicien_list();
                JSONObject dataobj=dataarray.getJSONObject(i);
                medicienlist.setMedicien_name(dataobj.getString("productname"));
                medicienlist.setMedicien_count(dataobj.getString("stock"));
                medicienlist.setMedicien_price(dataobj.getString("price"));
            }
            medicienAdapter=new MedicienAdapter(getActivity(),medicienlistArrayList);
            recyclerView.setAdapter(medicienAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/


}
