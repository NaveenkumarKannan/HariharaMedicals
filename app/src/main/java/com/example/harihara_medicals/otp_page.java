package com.example.harihara_medicals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harihara_medicals.Model.LoginData;
import com.example.harihara_medicals.Model.User;
import com.example.harihara_medicals.Retrofit.ApiUtils;
import com.example.harihara_medicals.utils.BitmapUtils;
import com.example.harihara_medicals.utils.DirManager;
import com.example.harihara_medicals.utils.FireManager;
import com.example.harihara_medicals.utils.SharedPreferencesManager;
import com.example.harihara_medicals.utils.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class otp_page extends AppCompatActivity {
    TextView sumbitotp, resendotp;
    String text;
    FirebaseAuth auth;
    EditText edtotp;
    private String verificationcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_page);

        auth = FirebaseAuth.getInstance();
        edtotp = findViewById(R.id.enter_otp);

        Intent intent = getIntent();
        String mobile = intent.getStringExtra("mobile");
        sendVerificationCode(mobile);

        sumbitotp = findViewById(R.id.submit_text);

        sumbitotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = edtotp.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    edtotp.setError("Enter valid code");
                    edtotp.requestFocus();
                    return;
                }
                veriFicationCode(code);

            }
        });
        resendotp = findViewById(R.id.resend_text);
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "OTP send", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void veriFicationCode(String code) {
        try {
            //creating the credential
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationcode, code);
            //signing the user
            signInWithPhoneAuthCredential(credential);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Some Error Occurred! Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void log(String message) {
        Log.e(getClass().getSimpleName(), message);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(otp_page.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Call<LoginData> call = ApiUtils.getProductApi().checkLogin(FireManager.getUid());
                            // Log.d("data_failed","nope");
                            call.enqueue(new Callback<LoginData>() {
                                @Override
                                public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                                    if (response.isSuccessful()) {
                                        LoginData loginData = response.body();
                                        if (loginData.getSuccess()) {

                                            User user = loginData.getUser();
                                            log("onResponse " + response);
                                            Toast.makeText(otp_page.this, loginData.getMessage(), Toast.LENGTH_SHORT).show();

                                            /*final File file = DirManager.generateUserProfileImage();

                                            //it is not recommended to change IMAGE_QUALITY_COMPRESS as it may become
                                            //too big and this may cause the app to crash due to large thumbImg
                                            //therefore the thumb img may became un-parcelable through activities
                                            int IMAGE_QUALITY_COMPRESS = 30;
                                            BitmapUtils.compressImage(fileUri.getPath(), file, IMAGE_QUALITY_COMPRESS);

                                            //generate circle bitmap
                                            Bitmap circleBitmap = BitmapUtils.getCircleBitmap(BitmapUtils.convertFileImageToBitmap(file.getPath()));
                                            //decode the image as base64 string
                                            String decodedImage = BitmapUtils.decodeImageAsPng(circleBitmap);

                                            SharedPreferencesManager.saveMyThumbImg(decodedImage);
                                            SharedPreferencesManager.saveMyPhoto(file.getPath());*/
                                            SharedPreferencesManager.savePhoneNumber(user.getPhone());
                                            SharedPreferencesManager.saveMyUsername(user.getFirstname() + " " + user.getLastname());
                                            SharedPreferencesManager.saveFirstTimeLogin();
                                            Intent intent = new Intent(otp_page.this, HomePageActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        } else {
                                            //verification successful we will start the profile activity
                                            Intent intent = new Intent(otp_page.this, regisration_page.class);
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

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }
                    }
                });

    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);


    }

    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                edtotp.setText(code);
                //verifying the code
                veriFicationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(otp_page.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            verificationcode = s;
        }
    };

    @Override
    public void onBackPressed() {
        Util.onBack(this);
    }
}
