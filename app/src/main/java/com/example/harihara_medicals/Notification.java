package com.example.harihara_medicals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harihara_medicals.Adapters.MyMedicialRecords;
import com.example.harihara_medicals.Adapters.NotifAdapter;
import com.example.harihara_medicals.Model.Notif;
import com.example.harihara_medicals.Retrofit.ApiUtils;
import com.example.harihara_medicals.Retrofit.ProductApi;
import com.example.harihara_medicals.ui.home.HomeFragment;
import com.example.harihara_medicals.utils.SharedPreferencesManager;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification extends Fragment {

    TextView title, nonotify;
    ImageView bkbtn;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        title = view.findViewById(R.id.title);
        bkbtn = view.findViewById(R.id.back_icon);
        recyclerView = view.findViewById(R.id.noReview);
        progressBar = view.findViewById(R.id.progressbar);
        nonotify = view.findViewById(R.id.txtNonotification);
        swipeRefreshLayout = view.findViewById(R.id.swdrefnot);

        title.setText("Notifications");
        bkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.commit();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getResponse();
            }
        });

        getResponse();

        return view;
    }

    private void getResponse() {

        ProductApi api = ApiUtils.getScalarUrl();

        Call<String> call = api.getnotification(SharedPreferencesManager.getCurrentUser().getUid());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressBar.setVisibility(View.VISIBLE);
                //Toast.makeText(getActivity(), "Ok "+res, Toast.LENGTH_SHORT).show();
                getRec(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity(), "Er "+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getRec(String res) {

        try{
            JSONObject jsonObject = new JSONObject(res);
            ArrayList<Notif> arrayList = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("notifications");
            for(int i=0;i<jsonArray.length();i++){
                Notif notif = new Notif();
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                notif.setNotificationtxt(jsonObject1.getString("notificationtxt"));
                arrayList.add(notif);
            }

            if(arrayList.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                nonotify.setVisibility(View.VISIBLE);
            }

            NotifAdapter notifAdapter = new NotifAdapter(getContext(), arrayList);
            recyclerView.setAdapter(notifAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            progressBar.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

}