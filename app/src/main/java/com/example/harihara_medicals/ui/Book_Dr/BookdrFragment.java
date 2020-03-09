package com.example.harihara_medicals.ui.Book_Dr;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.harihara_medicals.Doctor.General_doctor;
import com.example.harihara_medicals.R;

public class BookdrFragment extends Fragment {

    Button btn_general,btn_shin,btn_child,btn_women_health,btn_homeopathy,btn_ayrveda;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bookdr, container, false);
        btn_general=root.findViewById(R.id.product_btn_general);
        btn_general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), General_doctor.class));
            }
        });

        return root;
    }
}