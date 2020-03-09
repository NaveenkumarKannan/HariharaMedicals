package com.example.harihara_medicals.ui.address;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.harihara_medicals.R;
import com.example.harihara_medicals.Medicine.orderconformation;

public class AddressFragment extends Fragment {

    Button address_btn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.address_fragment, null);
        address_btn=view.findViewById(R.id.address_btn);
        address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), orderconformation.class));
            }
        });



        return view;
    }
}