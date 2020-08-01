package com.example.harihara_medicals.Medicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyachi.stepview.VerticalStepView;
import com.example.harihara_medicals.R;

import java.util.ArrayList;
import java.util.List;

public class Order_status extends AppCompatActivity {

    private ImageView backbtn;
    private TextView textView;
    private VerticalStepView verticalStepView;
    private int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        backbtn = findViewById(R.id.back_icon);
        textView = findViewById(R.id.titletxt);
        verticalStepView = findViewById(R.id.orderstatus);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent=getIntent();
        String ordername=intent.getStringExtra("ordername");
        String status=intent.getStringExtra("orderstatus");
        int st = Integer.parseInt(status);

        if(st == 0) {
            a = 2;
        } else if(st == 1){
            a = 1;
        } else {
            a = 0;
        }

        List<String> list = new ArrayList<>();
        list.add("Ordered ("+ordername+")");
        list.add("Ready");
        list.add("Complete");

        verticalStepView.setStepsViewIndicatorComplectingPosition(list.size() - a)
                .reverseDraw(false)
                .setStepViewTexts(list)
                .setLinePaddingProportion(2.4f)
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, android.R.color.black))
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.uc))
                .setStepViewComplectedTextColor(ContextCompat.getColor(this, android.R.color.black))
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.ic_done_black_24dp))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.ic_adjust_black_24dp))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.attention));
    }

}