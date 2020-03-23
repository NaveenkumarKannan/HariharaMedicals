package com.example.harihara_medicals.Medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harihara_medicals.HomePageActivity;
import com.example.harihara_medicals.Model.LoginData;
import com.example.harihara_medicals.Model.User;
import com.example.harihara_medicals.R;
import com.example.harihara_medicals.Retrofit.ApiUtils;
import com.example.harihara_medicals.regisration_page;
import com.example.harihara_medicals.utils.BitmapUtils;
import com.example.harihara_medicals.utils.CropImageRequest;
import com.example.harihara_medicals.utils.DirManager;
import com.example.harihara_medicals.utils.FireManager;
import com.example.harihara_medicals.utils.SharedPreferencesManager;
import com.mukesh.permissions.EasyPermissions;
import com.mukesh.permissions.OnPermissionListener;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.harihara_medicals.utils.Util.log;

public class MedicalRecords extends AppCompatActivity {

    Button choose, upload;
    ImageView pre, back;
    TextView medicalRecTitle, pretext;

    private int PICK_IMAGE_REQUEST = 1;
    Uri fileUri;
    String filePath = null;

    User user;

    EasyPermissions easyPermissions;
    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_records);

        init();

        choose = findViewById(R.id.chooose);
        upload = findViewById(R.id.record_upload_btn);
        pre = findViewById(R.id.preview);
        medicalRecTitle = findViewById(R.id.title);
        back = findViewById(R.id.back_icon);
        pretext = findViewById(R.id.pretxt);

        medicalRecTitle.setText("Medical Records");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (easyPermissions.hasPermission(permissions)) {
                    //openGallery();
                    pickImages();
                } else {
                    Toast.makeText(MedicalRecords.this, "Storage permission is needed to select the image", Toast.LENGTH_SHORT).show();
                    easyPermissions.request(permissions);
                }
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPost();
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
        CropImageRequest.getrCropImageRequest().start(this);
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
            CropImageRequest.getrCropImageRequest(uri).start(this);
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
                        pre.setImageBitmap(bitmap);
                        pretext.setVisibility(View.VISIBLE);
                        filePath = BitmapUtils.getImageFilePath(uri, MedicalRecords.this);
                        log("Path = " + filePath);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
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

        user = SharedPreferencesManager.getCurrentUser();
        String user_id = user.getUid();

        RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), user_id.toString());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Call<com.example.harihara_medicals.Model.Response> call = ApiUtils.getProductApi().mrecords(body, userid);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        call.enqueue(new Callback<com.example.harihara_medicals.Model.Response>() {
            @Override
            public void onResponse(Call<com.example.harihara_medicals.Model.Response> call, Response<com.example.harihara_medicals.Model.Response> response) {
                //Toast.makeText(MedicalRecords.this, "Success "+response.body(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MedicalRecords.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                pre.setImageResource(0);
                pretext.setVisibility(View.INVISIBLE);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<com.example.harihara_medicals.Model.Response> call, Throwable t) {
                //Toast.makeText(MedicalRecords.this, "Er "+t.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MedicalRecords.this, "Upload failed, try again", Toast.LENGTH_SHORT).show();
                pre.setImageResource(0);
                pretext.setVisibility(View.INVISIBLE);
                progressDialog.dismiss();
            }
        });

    }



}
