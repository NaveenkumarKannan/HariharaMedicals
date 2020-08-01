package com.example.harihara_medicals.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harihara_medicals.Model.Medicien_list;
import com.example.harihara_medicals.R;

import java.util.ArrayList;

public class MedicienAdapter extends RecyclerView.Adapter<MedicienAdapter.ViewHolder> {

    private LayoutInflater inflater;
    public ArrayList<Medicien_list> medicienlistArrayList;
    private static OnClickListener onClickListener;

    public MedicienAdapter( Context mctx,ArrayList<Medicien_list> medicienlistArrayList){
        inflater=LayoutInflater.from(mctx);
        this.medicienlistArrayList=medicienlistArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.medicine_list,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
       // Medicien_list medicienlist = medicien.get(position);
       // Glide.with(mctx).load(medicienlist.getMedicien_img()).into(holder.medicien_img);
        //holder.medicien_addcards.setText(medicienlistArrayList.get(position).getMedicien_addto_cart());
        holder.medicien_prices.setText("Rs:"+medicienlistArrayList.get(position).getMedicien_price());
        holder.medicien_names.setText(medicienlistArrayList.get(position).getMedicien_name());
        //holder.medicien_count.setText(medicienlistArrayList.get(position).getMedicien_count());
        //final  int[] present_count={1};
         holder.medicien_counts.setText(String.valueOf(medicienlistArrayList.get(position).getMedi_count()));
        holder.medicien_adds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int count;
                    String presentValStr=holder.medicien_counts.getText().toString();
                    count=Integer.parseInt(presentValStr);
                    count++;
                    holder.medicien_counts.setText(String.valueOf(count));
                    medicienlistArrayList.get(position).setMedi_count(count);
                    log("Count = "+medicienlistArrayList.get(position).getMedi_count());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.medicien_subs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int count;
                    String presentValStr=holder.medicien_counts.getText().toString();
                    count=Integer.parseInt(presentValStr);
                    if (presentValStr.equalsIgnoreCase(String.valueOf(Integer.parseInt("1")))){
                        Toast.makeText(v.getContext(), "can't less the 1", Toast.LENGTH_SHORT).show();
                    }else {
                        count--;
                    }
                    holder.medicien_counts.setText(String.valueOf(count));
                    medicienlistArrayList.get(position).setMedi_count(count);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
/*
        holder.medicien_addcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count_item=0;
                count_item++;

                holder.cart_count.setText(String.valueOf(count_item));
                holder.cart_count.setVisibility(View.VISIBLE);
                sendpost(holder.medicien_names.getText().toString(),holder.medicien_counts.getText().toString(),holder.medicien_prices.getText().toString());
            }
        });
*/



    }
    void log(String message){
        Log.e(getClass().getSimpleName(),message);
    }
    @Override
    public int getItemCount() {
        return medicienlistArrayList.size();
    }

    public void filterlist(ArrayList<Medicien_list> listArrayList) {
        medicienlistArrayList = listArrayList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView medicien_names,medicien_prices,medicien_counts,medicien_adds,medicien_subs;//,cart_count;
        Button medicien_addcards;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medicien_names=(TextView) itemView.findViewById(R.id.medicine_name);
            medicien_adds=itemView.findViewById(R.id.medicine_add);
            medicien_subs=itemView.findViewById(R.id.medicine_sub);
            medicien_addcards=itemView.findViewById(R.id.medicine_add_cart);
            medicien_counts=(EditText) itemView.findViewById(R.id.medicine_count);
            medicien_prices=(TextView) itemView.findViewById(R.id.medicine_price);
            //cart_count=itemView.findViewById(R.id.textOne);
            medicien_addcards.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
    public void setOnItemClickListener(OnClickListener onClickListener) {
        MedicienAdapter.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onItemClick(int position, View v);
    }


}
