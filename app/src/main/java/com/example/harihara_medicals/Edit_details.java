package com.example.harihara_medicals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.harihara_medicals.Medicine.MedicalRecords;
import com.example.harihara_medicals.Model.LoginData;
import com.example.harihara_medicals.Model.User;
import com.example.harihara_medicals.Retrofit.ApiUtils;
import com.example.harihara_medicals.utils.BitmapUtils;
import com.example.harihara_medicals.utils.CropImageRequest;
import com.example.harihara_medicals.utils.DirManager;
import com.example.harihara_medicals.utils.FireManager;
import com.example.harihara_medicals.utils.SharedPreferencesManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mukesh.permissions.EasyPermissions;
import com.mukesh.permissions.OnPermissionListener;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.example.harihara_medicals.utils.Util.log;

public class Edit_details extends AppCompatActivity {

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

        init();

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

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mycalender.set(Calendar.YEAR, year);
                mycalender.set(Calendar.MONTH, month);
                mycalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }
        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Edit_details.this, date, mycalender.get(Calendar.MONTH),
                        mycalender.get(Calendar.DAY_OF_MONTH), mycalender.get(Calendar.YEAR)).show();
            }
        });

        res_pro_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (easyPermissions.hasPermission(permissions)) {
                    //openGallery();
                    pickImages();
                } else {
                    Toast.makeText(Edit_details.this, "Storage permission is needed to select the image", Toast.LENGTH_SHORT).show();
                    easyPermissions.request(permissions);
                }
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPost();
            }
        });

    }

    private void updateLabel() {
        String myfromat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myfromat, Locale.UK);
        dob.setText(sdf.format(mycalender.getTime()));
    }

    private void init() {
        easyPermissions = new EasyPermissions.Builder()
                .with(this)
                .listener(new OnPermissionListener() {
                    @Override
                    public void onAllPermissionsGranted(@NonNull List<String> list) {

                    }

                    @Override
                    public void onPermissionsGranted(@NonNull List<String> list) {

                    }

                    @Override
                    public void onPermissionsDenied(@NonNull List<String> list) {

                    }
                })
                .build();
        easyPermissions.request(permissions);
    }

    private void pickImages() {
        CropImageRequest.getCropImageRequest().start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            CropImageRequest.getCropImageRequest(uri).start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri uri = result.getUri();
                fileUri = uri;
                if (fileUri != null)
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        res_pro_pic.setImageBitmap(bitmap);

                        filePath = BitmapUtils.getImageFilePath(uri, Edit_details.this);
                        log("Path = " + filePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    private void sendPost() {

        if(rb_ml.isChecked()){
            g_ender = "Male";
        }

        else if(rb_fl.isChecked()){
            g_ender = "Female";
        }

        else if(rb_ot.isChecked()){
            g_ender = "Others";
        }

        String path = filePath;
        String loc = SharedPreferencesManager.getUserLocalPhoto();

        File file;

        if (path == null) {
            file = new File(loc);
            //Toast.makeText(this, ""+loc, Toast.LENGTH_SHORT).show();
            log("Path null");
        } else {
            file = new File(path);
            //Toast.makeText(this, ""+path, Toast.LENGTH_SHORT).show();
            log("Path not null");
        }

        user = SharedPreferencesManager.getCurrentUser();
        String user_id = user.getUid();

        RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody f_name = RequestBody.create(MediaType.parse("text/plain"), firstname.getText().toString());
        RequestBody l_name = RequestBody.create(MediaType.parse("text/plain"), lastname.getText().toString());
        RequestBody d_o_b = RequestBody.create(MediaType.parse("text/plain"), dob.getText().toString());
        RequestBody gen = RequestBody.create(MediaType.parse("text/plain"), g_ender);
        RequestBody e_mail = RequestBody.create(MediaType.parse("text/plain"), email.getText().toString());
        RequestBody user_address = RequestBody.create(MediaType.parse("text/plain"), address.getText().toString());
        RequestBody h_eight = RequestBody.create(MediaType.parse("text/plain"), height.getText().toString());
        RequestBody w_eight = RequestBody.create(MediaType.parse("text/plain"), weight.getText().toString());
        RequestBody sugarlevel = RequestBody.create(MediaType.parse("text/plain"), sugar_level.getText().toString());
        RequestBody bp = RequestBody.create(MediaType.parse("text/plain"), blood_pressure.getText().toString());
        RequestBody bmi = RequestBody.create(MediaType.parse("text/plain"), blood_pressure.getText().toString());
        RequestBody d_name = RequestBody.create(MediaType.parse("text/plain"), drname.getText().toString());
        //RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Toast.makeText(Edit_details.this, " "+f_name+" "+l_name+" "+d_o_b+" "+g_ender, Toast.LENGTH_SHORT).show();

        Call<LoginData> call = ApiUtils.getScalarUrl().edtUserinfo(body, f_name, l_name, d_o_b, e_mail, user_address, gen, h_eight, w_eight, sugarlevel, d_name, bp, userid);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                LoginData res = response.body();
                User user = res.getUser();
                Toast.makeText(Edit_details.this, "Ok "+res.getMessage()+" "+response, Toast.LENGTH_SHORT).show();

                try {
                    final File file = DirManager.generateUserProfileImage();

                    int IMAGE_QUALITY_COMPRESS = 30;
                    BitmapUtils.compressImage(fileUri.getPath(), file, IMAGE_QUALITY_COMPRESS);

                    SharedPreferencesManager.saveMyPhoto(file.getPath());
                }catch (Exception e){
                    e.printStackTrace();
                    log(e.getMessage());
                }

                SharedPreferencesManager.setCurrentUser(user);

//                try {
//                    SharedPreferencesManager.setFirstName(firstname.getText().toString());
//                    SharedPreferencesManager.setLastName(lastname.getText().toString());
//                    SharedPreferencesManager.setDob(dob.getText().toString());
//                    SharedPreferencesManager.setGender(g_ender);
//                    SharedPreferencesManager.setEmail(email.getText().toString());
//                    SharedPreferencesManager.setAddress(address.getText().toString());
//                    SharedPreferencesManager.setHeight(height.getText().toString());
//                    SharedPreferencesManager.setWeight(weight.getText().toString());
//                    SharedPreferencesManager.setBpLevel(blood_pressure.getText().toString());
//                    SharedPreferencesManager.setSugarLevel(sugar_level.getText().toString());
//                    SharedPreferencesManager.setPreferredDoctorName(drname.getText().toString());
//                }catch (Exception e){
//                    e.printStackTrace();
//                    log(e.getMessage());
//                }

                finish();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                Toast.makeText(Edit_details.this, "Er "+t.toString(), Toast.LENGTH_SHORT).show();
                finish();
                progressDialog.dismiss();
            }
        });
    }
}