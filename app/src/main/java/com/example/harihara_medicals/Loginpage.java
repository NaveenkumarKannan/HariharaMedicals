package com.example.harihara_medicals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    public  static final  String MyPreferences="MyPrefs";
    public  static final String phone="phonekey";
    SharedPreferences sharedPreferences;

    @Override
    protected void onStart() {
        super.onStart();
         user=FirebaseAuth.getInstance().getCurrentUser();
        if(user !=null){
            startActivity(new Intent(Loginpage.this, HomePageActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        edit_phone=findViewById(R.id.edt);
        Button getotp=findViewById(R.id.getotpbtn);
        sharedPreferences=getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moblie=edit_phone.getText().toString().trim();

                SharedPreferences.Editor editor=sharedPreferences.edit();
                if(moblie.isEmpty() || moblie.length()<10){
                    edit_phone.setError("enter vaild number");
                    edit_phone.requestFocus();
                    return;
                }
                editor.putString(phone,moblie);
                editor.commit();
                Intent intent=new Intent(Loginpage.this,otp_page.class);
                intent.putExtra("mobile",moblie);
                startActivity(intent);
            }
        });

    }

}
