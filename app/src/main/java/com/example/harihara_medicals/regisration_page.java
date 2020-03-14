package com.example.harihara_medicals;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.harihara_medicals.Model.LoginData;
import com.example.harihara_medicals.Model.User;
import com.example.harihara_medicals.Retrofit.ApiUtils;
import com.example.harihara_medicals.utils.BitmapUtils;
import com.example.harihara_medicals.utils.CropImageRequest;
import com.example.harihara_medicals.utils.DirManager;
import com.example.harihara_medicals.utils.FireManager;
import com.example.harihara_medicals.utils.SharedPreferencesManager;
import com.example.harihara_medicals.utils.Util;
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

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.harihara_medicals.utils.Util.log;

public class regisration_page extends AppCompatActivity {
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
    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regisration_page);
        init();
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
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


        /*firstname.setText("Test firstname");
        lastname.setText("Test lastname");
        dob.setText("01/12/2020");
        email.setText("testmaill@gmail.com");
        address.setText("test addr");
        drname.setText("Test Dr");
        height.setText("172");
        weight.setText("75");
        blood_pressure.setText("165");
        sugar_level.setText("65");*/
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
            public void onClick(View v) {
                new DatePickerDialog(regisration_page.this, date, mycalender.get(Calendar.MONTH),
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
                    Toast.makeText(regisration_page.this, "Storage permission is needed to select the image", Toast.LENGTH_SHORT).show();
                    easyPermissions.request(permissions);
                }
            }
        });
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.regs_male_radio) {
                    g_ender = "Male";
                } else if (checkedId == R.id.regs_female_radio) {
                    g_ender = "Female";
                } else {
                    g_ender = "Others";
                }
                Log.e("Gender", g_ender);
            }
        });
        register = (Button) findViewById(R.id.regs_btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first_name = firstname.getText().toString().trim();
                String last_name = lastname.getText().toString().trim();
                String d_o_b = dob.getText().toString().trim();
                if (first_name.isEmpty()) {
                    firstname.setError("Enter your first name");
                    firstname.requestFocus();
                }/* else if (last_name.isEmpty()) {
                    lastname.setError("Enter your last name");
                    lastname.requestFocus();
                } */else if (d_o_b.isEmpty()) {
                    dob.setError("Enter your date of birth");
                    dob.requestFocus();
                } else if (g_ender.isEmpty()) {
                    Toast.makeText(regisration_page.this, "Choose Gender", Toast.LENGTH_SHORT).show();
                } else if (fileUri == null) {
                    Toast.makeText(regisration_page.this, "Choose Image", Toast.LENGTH_SHORT).show();
                } else {

                    //calling the upload file method after choosing the file
                    sendPost();
                    /*sendPost(firstname.getText().toString(),lastname.getText().toString(),dob.getText().toString()
                            ,email.getText().toString(),address.getText().toString(),height.getText().toString()
                            ,weight.getText().toString(),sugar_level.getText().toString(),blood_pressure.getText().toString(),dname.getText().toString()
                   );*/
                }
            }
        });


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

    //Github Permission Library Url
    //https://github.com/mukeshsolanki/easypermissions-android
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            //the image URI
            Uri selectedImage = data.getData();
            upload(selectedImage);



        }*/
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            //fileUri = uri;
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
                        // Log.d(TAG, String.valueOf(bitmap));
                        res_pro_pic.setImageBitmap(bitmap);
                        filePath = BitmapUtils.getImageFilePath(uri, regisration_page.this);
                        log("Path = " + filePath);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    /*  private void upload(Uri fileUri) {
            File file = new File(getRealPathFromURI(fileUri));
          RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
          Call<Myresponse> call=ApiUtils.getProductapi().getImage(requestFile);
          call.enqueue(new Callback<Myresponse>() {
              @Override
              public void onResponse(Call<Myresponse> call, Response<Myresponse> response) {

              }

              @Override
              public void onFailure(Call<Myresponse> call, Throwable t) {

              }
          });
      }*/
    public void updateLabel() {
        String myfromat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myfromat, Locale.UK);
        dob.setText(sdf.format(mycalender.getTime()));
    }

    private void sendPost() {//String fname, String lname, String d_ob, String gender, String m_ail, String a_ddress, String c_urrent_height, String c_urrent_weight, String s_ugar, String b_p,  String dname,Uri fileUri) {
        String path = filePath;
        if (path == null) {
            log("Path null");
            return;
        } else {
            log("Path not null");
        }
        File file = new File(path);
        RequestBody f_name = RequestBody.create(MediaType.parse("text/plain"), firstname.getText().toString());
        RequestBody l_name = RequestBody.create(MediaType.parse("text/plain"), lastname.getText().toString());
        RequestBody d_o_b = RequestBody.create(MediaType.parse("text/plain"), dob.getText().toString());
        RequestBody gen = RequestBody.create(MediaType.parse("text/plain"), g_ender);
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), Objects.requireNonNull(FireManager.getPhoneNumber()));
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), Objects.requireNonNull(FireManager.getUid()));
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

        //Call<LoginData> call= ApiUtils.getScalarProductApi().register(body,f_name,l_name,d_o_b,e_mail,user_address,gen,h_eight,w_eight,sugarlevel,d_name,bp/*,bmi*/,userId,phone);
        Call<LoginData> call = ApiUtils.getProductApi().register(body, f_name, l_name, d_o_b, e_mail, user_address, gen, h_eight, w_eight, sugarlevel, d_name, bp/*,bmi*/, userId, phone);
        //Call<Login> call=ApiUtils.getProductapi().getResgister(f_name,l_name,d_o_b,gen,e_mail,user_address,h_eight,w_eight,sugarlevel,d_name,bp,bp,requestFile);
        //Call<Myresponse> call=ApiUtils.getProductapi().getResgister(f_name,l_name,d_o_b,gen,e_mail,user_address,h_eight,w_eight,sugarlevel,d_name,bp,bp);
        //Call<Login> call=ApiUtils.getScalarProductapi().getResgister(f_name,l_name,d_o_b,gen,e_mail,user_address,h_eight,w_eight,sugarlevel,d_name,bp,bp);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                progressDialog.dismiss();

                LoginData loginData = response.body();
                if (loginData.getSuccess()) {
                    User user = loginData.getUser();
                    log("onResponse " + response);
                    Toast.makeText(regisration_page.this, loginData.getMessage(), Toast.LENGTH_SHORT).show();
                    try {
                        final File file = DirManager.generateUserProfileImage();

                        //it is not recommended to change IMAGE_QUALITY_COMPRESS as it may become
                        //too big and this may cause the app to crash due to large thumbImg
                        //therefore the thumb img may became un-parcelable through activities
                        int IMAGE_QUALITY_COMPRESS = 30;
                        BitmapUtils.compressImage(fileUri.getPath(), file, IMAGE_QUALITY_COMPRESS);

                        /*//generate circle bitmap
                        Bitmap circleBitmap = BitmapUtils.getCircleBitmap(BitmapUtils.convertFileImageToBitmap(file.getPath()));
                        //decode the image as base64 string
                        String decodedImage = BitmapUtils.decodeImageAsPng(circleBitmap);

                        SharedPreferencesManager.saveMyThumbImg(decodedImage);*/
                        SharedPreferencesManager.saveMyPhoto(file.getPath());
                    }catch (Exception e){
                        e.printStackTrace();
                        log(e.getMessage());
                    }
                    SharedPreferencesManager.saveFirstTimeLogin();
                    SharedPreferencesManager.setCurrentUser(user);

                    Intent intent = new Intent(regisration_page.this, HomePageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                progressDialog.dismiss();
                log("onFailure " + t.getMessage());
                Toast.makeText(regisration_page.this, "Error Occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void log(String message) {
        Log.e(getClass().getSimpleName(), message);
    }

    @Override
    public void onBackPressed() {
        Util.onBack(this);
    }
}
