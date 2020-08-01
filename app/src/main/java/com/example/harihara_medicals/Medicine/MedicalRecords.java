package com.example.harihara_medicals.Medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harihara_medicals.Adapters.MyMedicialRecords;
import com.example.harihara_medicals.Adapters.NotifAdapter;
import com.example.harihara_medicals.Model.MRecords;
import com.example.harihara_medicals.Model.Notif;
import com.example.harihara_medicals.Model.User;
import com.example.harihara_medicals.R;
import com.example.harihara_medicals.Retrofit.ApiUtils;
import com.example.harihara_medicals.Retrofit.ProductApi;
import com.example.harihara_medicals.utils.BitmapUtils;
import com.example.harihara_medicals.utils.CropImageRequest;
import com.example.harihara_medicals.utils.SharedPreferencesManager;
import com.google.gson.JsonObject;
import com.mukesh.permissions.EasyPermissions;
import com.mukesh.permissions.OnPermissionListener;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    TextView medicalRecTitle, pretext, emptytxt;

    private int PICK_IMAGE_REQUEST = 1;
    Uri fileUri;
    String filePath = null;

    User user;

    EasyPermissions easyPermissions;
    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};

    RecyclerView recyclerView;
    MyMedicialRecords myMedRecAdp;
    List<MRecords> recordsList;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

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
        recyclerView = findViewById(R.id.mrecordrview);
        progressBar = findViewById(R.id.progrsmrec);
        swipeRefreshLayout = findViewById(R.id.refrshmrec);
        emptytxt = findViewById(R.id.emptyaptxtmedrec);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyrec();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        getMyrec();

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

    private void getMyrec() {

        recordsList = new ArrayList<>();

        Call<String> getMreCall = ApiUtils.getScalarUrl().getmrecords(SharedPreferencesManager.getCurrentUser().getUid());
        getMreCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Toast.makeText(MedicalRecords.this, "ok "+response.body(), Toast.LENGTH_SHORT).show();
                getRec(response.body());
                Log.d("myrec", ""+response);
                Log.d("myrec", ""+response.errorBody());
                Log.d("myrec", ""+response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MedicalRecords.this, "er "+t.toString(), Toast.LENGTH_SHORT).show();
                Log.d("myrec", ""+t.getMessage());
                Log.d("myrec", ""+t.getLocalizedMessage());
            }
        });


    }

    private void getRec(String res) {
        progressBar.setVisibility(View.VISIBLE);
        try{
            JSONObject jsonObject = new JSONObject(res);
            ArrayList<MRecords> arrayList = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("mrecords");
            for(int i=0;i<jsonArray.length();i++){
                MRecords mRecords = new MRecords();
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                mRecords.setMrecid(jsonObject1.getString("mrecid"));
                mRecords.setMfile(jsonObject1.getString("mfile"));
                arrayList.add(mRecords);
            }

            //arrayList.clear();

            if(arrayList.isEmpty()){
                emptytxt.setVisibility(View.VISIBLE);
            } else {
                emptytxt.setVisibility(View.GONE);
            }

            MyMedicialRecords myMedicialRecords = new MyMedicialRecords(this, arrayList);

            recyclerView.setAdapter(myMedicialRecords);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            //progressBar.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }

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

        Call<com.example.harihara_medicals.Model.Response> call = ApiUtils.getScalarUrl().mrecords(body, userid);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        call.enqueue(new Callback<com.example.harihara_medicals.Model.Response>() {
            @Override
            public void onResponse(Call<com.example.harihara_medicals.Model.Response> call, Response<com.example.harihara_medicals.Model.Response> response) {
                //Toast.makeText(MedicalRecords.this, "Success "+response.body(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MedicalRecords.this, "Uploaded Successfully "+response.body()+" "+response, Toast.LENGTH_SHORT).show();
                Log.d("myrecu", ""+response);
                Log.d("myrecu", ""+response.errorBody());
                Log.d("myrecu", ""+response.body());
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
