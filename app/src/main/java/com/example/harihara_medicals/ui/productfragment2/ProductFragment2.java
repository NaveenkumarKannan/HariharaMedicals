package com.example.harihara_medicals.ui.productfragment2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.harihara_medicals.Ar_product;
import com.example.harihara_medicals.Medicine.Medicine;
import com.example.harihara_medicals.R;
import com.example.harihara_medicals.SearchProducts;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class ProductFragment2 extends Fragment {

private Button btn_health,btn_cosmetics,btn_medicine,btn_surgical,btn_veternary,btn_ayrvedic;
private EditText product_search;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {
        final  View view=inflater.inflate(R.layout.product_fragment2_fragment, null);

        product_search = view.findViewById(R.id.product_search);

        PushDownAnim.setPushDownAnimTo(product_search)
                .setOnClickListener( new View.OnClickListener(){
                    @Override
                    public void onClick( View view ){
                        startActivity( new Intent(getActivity(),Medicine.class));
                    }
                } );

        btn_medicine=view.findViewById(R.id.product_btn_Medicine);
        btn_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity( new Intent(getActivity(),Medicine.class));
            }
        });
        btn_cosmetics=view.findViewById(R.id.product_btn_cosmetics);
        btn_cosmetics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getActivity(),Medicine.class));

            }
        });
        btn_surgical=view.findViewById(R.id.product_btn_Surgical_item);
        btn_surgical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getActivity(),Medicine.class));

            }
        });
        btn_veternary=view.findViewById(R.id.product_btn_veterinary);
        btn_veternary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getActivity(),Medicine.class));

            }
        });
        btn_ayrvedic=view.findViewById(R.id.product_btn_Ayrvedic);
        btn_ayrvedic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getActivity(),Medicine.class));

            }
        });
//       product_search=view.findViewById(R.id.product_search);
//        product_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(),Medicine.class));
//           }
//        });
        btn_health=view.findViewById(R.id.product_btn_health_wealth);
        btn_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getActivity(),Medicine.class));
            }
        });
        return view;


    }

}
