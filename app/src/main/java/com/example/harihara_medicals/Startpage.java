package com.example.harihara_medicals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class Startpage extends AppCompatActivity {


    ImageView radssoonSymbol,aboveWhiteline,belowWhiteline;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseMessaging.getInstance().subscribeToTopic("Harihara_Medicals");

        setContentView(R.layout.activity_startpage);
        radssoonSymbol = (ImageView) findViewById(R.id.radssoon_symbol);
        aboveWhiteline = (ImageView) findViewById(R.id.above_whiteline);
        belowWhiteline = (ImageView) findViewById(R.id.below_whiteline);
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
}
