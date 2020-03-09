package com.example.harihara_medicals.ui.user;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.harihara_medicals.Edit_details;
import com.example.harihara_medicals.Loginpage;
import com.example.harihara_medicals.Medicine.MedicalRecords;
import com.example.harihara_medicals.R;
import com.example.harihara_medicals.Startpage;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class UserFragment extends Fragment {
    private TextView user_record,edit_details,feedback,language;
    private  TextView logout;
    FirebaseAuth  auth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

                 final View view=  inflater.inflate(R.layout.user_fragment, null);
         user_record= view.findViewById(R.id.user_record);
        user_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MedicalRecords.class));
            }
        });
        logout=view.findViewById(R.id.user_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth=FirebaseAuth.getInstance();
                auth.signOut();
                startActivity(new Intent(getActivity(), Loginpage.class));

            }
        });
        edit_details=view.findViewById(R.id.user_edit);
        edit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Edit_details.class));
            }
        });
        feedback=view.findViewById(R.id.user_feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?ei=3vs8Xse6Nt2U4-EP0eWzeA&q=harihara+medicals&oq=harihar+medicals&gs_l=psy-ab.1.0.0i13l2j0i22i30.0.0..8066...0.0..0.129.236.0j2......0......gws-wiz.EM9em3_fpkg#lrd=0x3bae18095d5d1703:0x289ad4c0bc2b2acf,1,,,")));
                }catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com/search?ei=3vs8Xse6Nt2U4-EP0eWzeA&q=harihara+medicals&oq=harihar+medicals&gs_l=psy-ab.1.0.0i13l2j0i22i30.0.0..8066...0.0..0.129.236.0j2......0......gws-wiz.EM9em3_fpkg#lrd=0x3bae18095d5d1703:0x289ad4c0bc2b2acf,1,,,")));
                }
            }
        });
        language=view.findViewById(R.id.user_language);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle(R.string.title_language);
                builder.setPositiveButton(R.string.title_English, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String lantoload="en";
                        Locale locale = new Locale(lantoload);
                        Locale.setDefault(locale);
                        Configuration configuration=new Configuration();
                        configuration.locale=locale;
                        getContext().getResources().updateConfiguration(configuration,getContext().getResources().getDisplayMetrics());
                        dialog.dismiss();
                        Intent refresh=new Intent(getActivity(), Startpage.class);
                        startActivity(refresh);
                    }
                });
                builder.setNegativeButton(R.string.title_Kannada, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String lantoload="kn";
                        Locale locale = new Locale(lantoload);
                        Locale.setDefault(locale);
                        Configuration configuration=new Configuration();
                        configuration.locale=locale;
                        getContext().getResources().updateConfiguration(configuration,getContext().getResources().getDisplayMetrics());
                        dialog.dismiss();
                        Intent refresh=new Intent(getActivity(), Startpage.class);
                        startActivity(refresh);
                    }
                });
                builder.create().show();
            }
        });
        return view;

    }



}
