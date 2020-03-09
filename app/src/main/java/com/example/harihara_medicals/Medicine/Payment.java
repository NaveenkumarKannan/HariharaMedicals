package com.example.harihara_medicals.Medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.harihara_medicals.R;

public class Payment extends AppCompatActivity {
    Button payment_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        payment_btn=findViewById(R.id.payment_pay_securely);
        payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Payment.this,Order.class));
            }
        });

    }
}
