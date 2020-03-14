package com.example.harihara_medicals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mukesh.permissions.EasyPermissions;
import com.mukesh.permissions.OnPermissionListener;

import java.util.List;

public class Startpage extends AppCompatActivity {

    ImageView radssoonSymbol,aboveWhiteline,belowWhiteline;
    EasyPermissions easyPermissions;
    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseMessaging.getInstance().subscribeToTopic("Harihara_Medicals");

        setContentView(R.layout.activity_startpage);
        init();
        radssoonSymbol = (ImageView) findViewById(R.id.radssoon_symbol);
        aboveWhiteline = (ImageView) findViewById(R.id.above_whiteline);
        belowWhiteline = (ImageView) findViewById(R.id.below_whiteline);
        if (easyPermissions.hasPermission(permissions)) {
            startAnimation();
        } else {
            Toast.makeText(Startpage.this, "Camera and Storage permission is needed to use this app", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
        }
/*        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               *//* final Intent i = new Intent(Startpage.this,Loginpage.class);
                Thread timer= new Thread(){
                    public void run() {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finally {
                            startActivity(i);
                            finish();

                        }
                    }
                };*//*
               startActivity(new Intent(Startpage.this,Loginpage.class));
            }
        },SPLASH_TIME_OUT);*/
    }

    private void startAnimation() {
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_right);
        aboveWhiteline.startAnimation(animation1);

        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        radssoonSymbol.startAnimation(animation2);

        Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_left);
        belowWhiteline.startAnimation(animation3);
        final Intent i = new Intent(this,Loginpage.class);
        Thread timer= new Thread(){
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();

                }
            }
        };
        timer.start();
    }

    private void init() {
        easyPermissions = new EasyPermissions.Builder()
                .with(this)
                .listener(new OnPermissionListener() {
                    @Override
                    public void onAllPermissionsGranted(@NonNull List<String> list) {
                        startAnimation();
                    }

                    @Override
                    public void onPermissionsGranted(@NonNull List<String> list) {

                    }

                    @Override
                    public void onPermissionsDenied(@NonNull List<String> list) {
                        easyPermissions.request(permissions);
                    }
                })
                .build();
        easyPermissions.request(permissions);
    }
}
