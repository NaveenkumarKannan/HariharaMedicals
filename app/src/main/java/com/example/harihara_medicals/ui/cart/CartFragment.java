package com.example.harihara_medicals.ui.cart;

import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.harihara_medicals.R;
import com.example.harihara_medicals.ui.address.AddressFragment;

public class CartFragment extends Fragment {
    Button cart_btn;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final  View view=inflater.inflate(R.layout.cart_fragment, null);
        cart_btn= view.findViewById(R.id.cart_btn);

        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new AddressFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.nav_host_fragment, newFragment);

                transaction.commit();
            }
        });
        return view;
    }



}
