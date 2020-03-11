package com.example.harihara_medicals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.harihara_medicals.utils.SharedPreferencesManager;
import com.example.harihara_medicals.utils.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Loginpage extends AppCompatActivity {
    Button get_otp;
    EditText edit_phone;
    /*FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private  String verificationcode;
    String phonenumber;*/
    FirebaseUser user;

    private void log(String message) {
        Log.e(getClass().getSimpleName(), message);
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (SharedPreferencesManager.isLoggedIn()) {
                startActivity(new Intent(Loginpage.this, HomePageActivity.class));
                finish();
            } else {
                startActivity(new Intent(Loginpage.this, regisration_page.class));
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        edit_phone = findViewById(R.id.edt);
        Button getotp = findViewById(R.id.getotpbtn);
        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moblie = edit_phone.getText().toString().trim();

                if (moblie.isEmpty() || moblie.length() < 10) {
                    edit_phone.setError("enter vaild number");
                    edit_phone.requestFocus();
                    return;
                }
                Intent intent = new Intent(Loginpage.this, otp_page.class);
                intent.putExtra("mobile", moblie);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Util.onBack(this);
    }
}
