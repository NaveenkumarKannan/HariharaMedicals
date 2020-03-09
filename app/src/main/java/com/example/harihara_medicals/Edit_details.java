package com.example.harihara_medicals;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Edit_details extends AppCompatActivity {
    FirebaseUser user;
    EditText number_bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_details);
        number_bg=findViewById(R.id.regs_mob);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            String num=user.getPhoneNumber();
            number_bg.setText(num);
        }

    }
}
