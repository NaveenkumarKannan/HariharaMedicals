package com.example.harihara_medicals.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.harihara_medicals.Doctor.Doctor_appoinment;
import com.example.harihara_medicals.Medicine.MyRecordPreview;
import com.example.harihara_medicals.Model.MRecords;
import com.example.harihara_medicals.R;

import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import retrofit2.http.Url;

public class MyMedicialRecords extends RecyclerView.Adapter<MyMedicialRecords.MyviewHolder> {

    private Context context;
    private ArrayList<MRecords> mRecordsArrayList;

    public MyMedicialRecords(Context context, ArrayList<MRecords> mRecordsArrayList) {
        this.context = context;
        this.mRecordsArrayList = mRecordsArrayList;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyviewHolder(LayoutInflater.from(context).inflate(R.layout.myrec_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        //holder.mfile.setImageResource(mRecordsArrayList.get(position).);
        String urlString = "https://grubby.co.za/harihara/API/mrecords/"+mRecordsArrayList.get(position).getMfile();

        Glide.with(context)
                .load(urlString)
                .into(holder.mfile);

        holder.mfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), MyRecordPreview.class);
                intent.putExtra("image_url", urlString);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecordsArrayList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        ImageView mfile;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            mfile = itemView.findViewById(R.id.mrecord);

        }

    }
}