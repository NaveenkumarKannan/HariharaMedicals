package com.example.harihara_medicals.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.harihara_medicals.R;

public class Doctor_appoinment  extends AppCompatActivity {
    TextView app_dr_name,app_dr_spc,app_dr_address,app_dr_num,app_dr_num2,app_dr_qu,app_dr_sp;
    Button btn_appointment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_appoinment);

        app_dr_name=findViewById(R.id.dr_app_name);
        app_dr_spc=findViewById(R.id.dr_app_edu);
        app_dr_address=findViewById(R.id.dr_app_address);
        app_dr_num=findViewById(R.id.dr_app_num1);
        app_dr_num2=findViewById(R.id.dr_app_num2);
        app_dr_qu=findViewById(R.id.dr_app_edu);
        app_dr_sp=findViewById(R.id.dr_app_physician);
        Intent intent=getIntent();
        String drname=intent.getStringExtra("Dr_name");
        String drspc=intent.getStringExtra("Dr_spc");
        String draddress=intent.getStringExtra("Dr_address");
        String drexp=intent.getStringExtra("Dr_exp");
        String drnum1=intent.getStringExtra("Dr_num1");
        String drnum2=intent.getStringExtra("Dr_num2");
        String drqu=intent.getStringExtra("Dr_qu");

        /* String drnum=intent.getStringExtra("Dr_num");*/
        app_dr_name.setText(drname);
        app_dr_spc.setText(drspc);
        app_dr_address.setText(draddress);
        app_dr_num.setText(drnum1);
        app_dr_num2.setText(drnum2);
        app_dr_qu.setText(drqu);
        app_dr_sp.setText(drspc);

        btn_appointment=findViewById(R.id.dr_app_appointment);
        btn_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Doctor_appoinment.this,BookAppointment.class));
                Intent intent=new Intent(v.getContext(),BookAppointment.class);
                intent.putExtra("Dr_name",drname);

                Intent intent1=getIntent();
                String drname=intent1.getStringExtra("Dr_name");
                String drspc=intent1.getStringExtra("Dr_spc");
                String draddress=intent1.getStringExtra("Dr_address");
                String drfees=intent1.getStringExtra("Dr_fees");
                String drexp=intent1.getStringExtra("Dr_exp");
                String doid=intent1.getStringExtra("did");

                //Intent intent=new Intent(v.getContext(),BookAppointment.class);
                intent.putExtra("Dr_name",drname);
                intent.putExtra("Dr_spc",drspc);
                intent.putExtra("Dr_fees",drfees);
                intent.putExtra("Dr_epx",drexp);
                intent.putExtra("docid",doid);
                v.getContext().startActivity(intent);
            }
        });



    }
}