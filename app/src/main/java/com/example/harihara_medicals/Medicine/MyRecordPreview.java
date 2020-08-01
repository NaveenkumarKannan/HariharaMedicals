package com.example.harihara_medicals.Medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.harihara_medicals.R;

public class MyRecordPreview extends AppCompatActivity {

    ImageView imageView, backicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_record_preview);

        imageView = findViewById(R.id.imageVieww);
        backicon = findViewById(R.id.back);

        Intent intent1=getIntent();
        String url_img = intent1.getStringExtra("image_url");

        Glide.with(MyRecordPreview.this)
                .load(url_img)
                .into(imageView);

        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
