package com.example.harihara_medicals;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.harihara_medicals.Model.LoginData;
import com.example.harihara_medicals.Model.User;
import com.example.harihara_medicals.Retrofit.ApiUtils;
import com.example.harihara_medicals.utils.BitmapUtils;
import com.example.harihara_medicals.utils.DirManager;
import com.example.harihara_medicals.utils.FireManager;
import com.example.harihara_medicals.utils.SharedPreferencesManager;
import com.example.harihara_medicals.utils.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.mukesh.permissions.EasyPermissions;

import java.io.File;

import static com.example.harihara_medicals.utils.Util.log;

public class Loginpage extends AppCompatActivity {
    Button get_otp;
    EditText edit_phone;
    /*FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private  String verificationcode;
    String phonenumber;*/
    FirebaseUser user;

    @Override
    protected void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (SharedPreferencesManager.isLoggedIn()) {
                startActivity(new Intent(Loginpage.this, HomePageActivity.class));
                finish();
            } else {
                /*startActivity(new Intent(Loginpage.this, regisration_page.class));
                finish();*/
                signInUser();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        edit_phone = findViewById(R.id.edt);
        Button getotp = findViewById(R.id.getotpbtn);
        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moblie = edit_phone.getText().toString().trim();

                if (moblie.isEmpty() || moblie.length() < 10) {
                    edit_phone.setError("enter vaild number");
                    edit_phone.requestFocus();
                    return;
                }
                Intent intent = new Intent(Loginpage.this, otp_page.class);
                intent.putExtra("mobile", moblie);
                startActivity(intent);
            }
        });

    }


    private void signInUser() {
        Call<LoginData> call = ApiUtils.getProductApi().checkLogin(FireManager.getUid());
        // Log.d("data_failed","nope");
        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.isSuccessful()) {
                    LoginData loginData = response.body();
                    if (loginData.getSuccess()) {

                        User user = loginData.getUser();
                        String image_url = ApiUtils.PROFILE_IMAGE_URL+user.getImageUrl();
                        log("onResponse " + new Gson().toJson(loginData));
                        log("image_url = "+image_url);
                        UrlImageViewHelper.loadUrlDrawable(Loginpage.this, image_url, new UrlImageViewCallback() {
                            @Override
                            public void onLoaded(ImageView imageView,
                                                 Bitmap loadedBitmap, String url,
                                                 boolean loadedFromCache) {
                                if (loadedBitmap != null) {
                                    try {
                                        Toast.makeText(Loginpage.this, loginData.getMessage(), Toast.LENGTH_SHORT).show();

                                        final File file = DirManager.generateUserProfileImage();

                                        //it is not recommended to change IMAGE_QUALITY_COMPRESS as it may become
                                        //too big and this may cause the app to crash due to large thumbImg
                                        //therefore the thumb img may became un-parcelable through activities
                                        BitmapUtils.convertBitmapToJpeg(loadedBitmap, file);

                                        /*//generate circle bitmap
                                        Bitmap circleBitmap = BitmapUtils.getCircleBitmap(BitmapUtils.convertFileImageToBitmap(file.getPath()));
                                        //decode the image as base64 string
                                        String decodedImage = BitmapUtils.decodeImageAsPng(circleBitmap);

                                        SharedPreferencesManager.saveMyThumbImg(decodedImage);*/
                                        SharedPreferencesManager.saveMyPhoto(file.getPath());
                                        log("loadedFromCache = "+loadedFromCache);
                                        log("filePath = "+file.getPath());
                                    }catch (Exception e){
                                        e.printStackTrace();
                                        log(e.getMessage());
                                    }
                                }
                                SharedPreferencesManager.saveFirstTimeLogin();
                                SharedPreferencesManager.setCurrentUser(user);
                                Intent intent = new Intent(Loginpage.this, HomePageActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });
                    } else {
                        //verification successful we will start the profile activity
                        Intent intent = new Intent(Loginpage.this, regisration_page.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                Log.e("failed", t.getMessage());
            }
        });
    }
    @Override
    public void onBackPressed() {
        Util.onBack(this);
    }
}
