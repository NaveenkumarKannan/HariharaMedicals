package com.example.harihara_medicals.ui.Book_Dr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.harihara_medicals.Doctor.General_doctor;
import com.example.harihara_medicals.R;
import com.example.harihara_medicals.Retrofit.ApiUtils;
import com.example.harihara_medicals.Retrofit.ProductApi;

public class BookdrFragment extends Fragment {

    Button btn_general,btn_skin,btn_child,btn_women_health,btn_homeopathy,btn_ayrveda;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bookdr, container, false);

        btn_general = root.findViewById(R.id.product_btn_general);
        btn_skin = root.findViewById(R.id.product_btn_skin);
        btn_child = root.findViewById(R.id.product_btn_child);
        btn_women_health = root.findViewById(R.id.product_btn_women_health);
        btn_homeopathy = root.findViewById(R.id.product_btn_homeopathy);
        btn_ayrveda = root.findViewById(R.id.product_btn_Ayrvedic);

        btn_general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), General_doctor.class);
                intent.putExtra("spe", "gd");
                startActivity(intent);
            }
        });

        btn_skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), General_doctor.class);
                intent.putExtra("spe", "skin");
                startActivity(intent);
            }
        });

        btn_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), General_doctor.class);
                intent.putExtra("spe", "child");
                startActivity(intent);
            }
        });

        btn_women_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), General_doctor.class);
                intent.putExtra("spe", "women");
                startActivity(intent);
            }
        });

        btn_homeopathy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), General_doctor.class);
                intent.putExtra("spe", "home");
                startActivity(intent);
            }
        });

        btn_ayrveda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), General_doctor.class);
                intent.putExtra("spe", "ay");
                startActivity(intent);
            }
        });

        return root;
    }

}