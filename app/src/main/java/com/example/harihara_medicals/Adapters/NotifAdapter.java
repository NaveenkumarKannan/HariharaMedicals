package com.example.harihara_medicals.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harihara_medicals.Model.Doctor_list;
import com.example.harihara_medicals.Model.Notif;
import com.example.harihara_medicals.R;

import java.util.ArrayList;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Notif> notifArrayList;

    public NotifAdapter(Context context, ArrayList<Notif> notifArrayList) {
        this.inflater = LayoutInflater.from(context);
        this.notifArrayList = notifArrayList;
    }

    @NonNull
    @Override
    public NotifAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.notification_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotifAdapter.MyViewHolder holder, int position) {
        holder.notificationtxt.setText(notifArrayList.get(position).getNotificationtxt());
    }

    @Override
    public int getItemCount() {
        return notifArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView notificationtxt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            notificationtxt = itemView.findViewById(R.id.notfytxt);

        }

    }
}
