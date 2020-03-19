package com.example.harihara_medicals;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.harihara_medicals.Model.User;
import com.example.harihara_medicals.Retrofit.ApiUtils;
import com.example.harihara_medicals.utils.SharedPreferencesManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mukesh.permissions.EasyPermissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Edit_details extends AppCompatActivity {
    //FirebaseUser user;
    //EditText number_bg;
    EditText firstname, lastname, dob, email, address, drname, height, weight, blood_pressure, sugar_level;
    RadioButton rb_ml, rb_fl, rb_ot;
    ImageView res_pro_pic;
    Button save_btn;
    private int PICK_IMAGE_REQUEST = 1;
    Uri fileUri;
    String filePath = null;
    String g_ender;
    final Calendar mycalender = Calendar.getInstance();
    EasyPermissions easyPermissions;
    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_details);
        /*number_bg=findViewById(R.id.regs_mob);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            String num=user.getPhoneNumber();
            number_bg.setText(num);
        }*/

        firstname = findViewById(R.id.regs_firstname);
        lastname = findViewById(R.id.regs_lastname);
        dob = findViewById(R.id.regs_dob);
        email = findViewById(R.id.regs_email);
        address = findViewById(R.id.regs_address);
        drname = findViewById(R.id.regs_doctor_name);
        height = findViewById(R.id.regs_height);
        weight = findViewById(R.id.regs_weight);
        rb_ml = findViewById(R.id.regs_male_radio);
        rb_fl = findViewById(R.id.regs_female_radio);
        rb_ot = findViewById(R.id.regs_others_radio);
        save_btn = findViewById(R.id.regs_btn_register);
        blood_pressure = findViewById(R.id.regs_bp);
        sugar_level = findViewById(R.id.regs_sugar);
        res_pro_pic = findViewById(R.id.regs_upload);
        user = SharedPreferencesManager.getCurrentUser();
        firstname.setText(user.getFirstName());
        lastname.setText(user.getLastName());

        dob.setText(user.getDob());
        email.setText(user.getEmail());
        address.setText(user.getAddress());
        drname.setText(user.getPreferredDoctorName());
        height.setText(user.getHeight());
        weight.setText(user.getWeight());
        blood_pressure.setText(user.getBpLevel());
        sugar_level.setText(user.getSugarLevel());
        final String myPhoto = user.getUserLocalPhoto();

        Glide.with(this).load(Uri.fromFile(new File(myPhoto)))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(res_pro_pic);

        if(user.getGender().equals("Male")){
            rb_ml.setChecked(true);
        }

        if(user.getGender().equals("Female")){
            rb_fl.setChecked(true);
        }

        if(user.getGender().equals("Others")){
            rb_ot.setChecked(true);
        }

        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Edit_details.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(rb_ml.isChecked()){
                    g_ender = "Male";
                }

                else if(rb_fl.isChecked()){
                    g_ender = "Female";
                }

                else if(rb_ot.isChecked()){
                    g_ender = "Others";
                }

                //Toast.makeText(Edit_details.this, ""+g_ender, Toast.LENGTH_SHORT).show();
                updateUsrinfo(firstname.getText().toString(), lastname.getText().toString(), dob.getText().toString(),
                                email.getText().toString(), address.getText().toString(), g_ender,
                                height.getText().toString(), weight.getText().toString(), drname.getText().toString(),
                                blood_pressure.getText().toString(), sugar_level.getText().toString(), user.getUid());

                //SharedPreferencesManager.setEmail(email.getText().toString());

                finish();

            }
        });

    }

    private void updateUsrinfo(String fname, String lname, String dob, String mail, String address, String gender, String current_height, String current_weight, String dname, String bp, String sugar, String user_id) {

        Call<String> call = ApiUtils.getScalarProductApi().edtUserinfo(fname, lname, dob, mail, address, gender, current_height, current_weight, dname, bp, sugar, user_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(Edit_details.this, "Updated successfully"+response.message().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Edit_details.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dob.setText(sdf.format(mycalender.getTime()));
    }

}