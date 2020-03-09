package com.example.harihara_medicals.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.harihara_medicals.R;

public class Doctor_appoinment  extends AppCompatActivity {
    TextView app_dr_name,app_dr_spc,app_dr_address,app_dr_num,app_dr_num2;
    Button btn_appointment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_appoinment);
        btn_appointment=findViewById(R.id.dr_app_appointment);
        btn_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(new Intent(Doctor_appoinment.this,BookAppointment.class));*/
                /*Intent intent=new Intent(v.getContext(),BookAppointment.class);
                intent.putExtra("Dr_name",app_dr_name);
                */

                Intent intent1=getIntent();
                String drname=intent1.getStringExtra("Dr_name");
                String drspc=intent1.getStringExtra("Dr_spc");
                String draddress=intent1.getStringExtra("Dr_address");
                String drfees=intent1.getStringExtra("Dr_fees");
                String drexp=intent1.getStringExtra("Dr_epx");
                Intent intent=new Intent(v.getContext(),BookAppointment.class);
                intent.putExtra("Dr_name",drname);
                intent.putExtra("Dr_spc",drspc);
                intent.putExtra("Dr_fees",drfees);
                intent.putExtra("Dr_epx",drexp);
                v.getContext().startActivity(intent);
            }
        });
        app_dr_name=findViewById(R.id.dr_app_name);
        app_dr_spc=findViewById(R.id.dr_app_edu);
        app_dr_address=findViewById(R.id.dr_app_address);
       /* app_dr_num=findViewById(R.id.dr_app_num1);
        app_dr_num2=findViewById(R.id.dr_app_num2);*/
        Intent intent=getIntent();
        String drname=intent.getStringExtra("Dr_name");
        String drspc=intent.getStringExtra("Dr_spc");
        String draddress=intent.getStringExtra("Dr_address");


       /* String drnum=intent.getStringExtra("Dr_num");*/
        app_dr_name.setText(drname);
        app_dr_spc.setText(drspc);
        app_dr_address.setText(draddress);
        /*app_dr_num.setText(drnum);
        app_dr_num2.setText(drnum);*/


    }
}
