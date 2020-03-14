package com.example.harihara_medicals;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.harihara_medicals.Model.User;
import com.example.harihara_medicals.utils.SharedPreferencesManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mukesh.permissions.EasyPermissions;

import java.io.File;
import java.util.Calendar;

public class Edit_details extends AppCompatActivity {
    //FirebaseUser user;
    //EditText number_bg;
    EditText firstname, lastname, dob, email, address, drname, height, weight, blood_pressure, sugar_level;
    RadioGroup radio_group;
    ImageView res_pro_pic;
    Button register;
    private int PICK_IMAGE_REQUEST = 1;
    Uri fileUri;
    String filePath = null;
    String g_ender = "";
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
        radio_group = findViewById(R.id.radio_group);

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

    }
}
