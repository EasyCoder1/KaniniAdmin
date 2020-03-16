package com.example.kaniniadmin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.kaniniadmin.Activities.myMaps;
import com.example.kaniniadmin.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyDataAdapter extends RecyclerView.Adapter<MyDataAdapter.MViewHolder> {

    private Context context;
    private List<MyData> myData;



    public MyDataAdapter(Context context, List myData) {
        this.context = context;
        this.myData = myData;

    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MViewHolder holder, int position) {
        MyData md= myData.get(position);
        holder.Tname.setText(md.getName());
        holder.Tphone.setText(md.getPhone());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,myMaps.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("username",myData.get(holder.getAdapterPosition()).getName());
                intent.putExtra("phoneNo",myData.get(holder.getAdapterPosition()).getPhone());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myData.size();
    }


    class MViewHolder extends RecyclerView.ViewHolder {
        TextView Tname,Tphone;
        RelativeLayout parentLayout;

        MViewHolder(@NonNull View itemView) {
            super(itemView);
            Tname = itemView.findViewById(R.id.username1);
            Tphone=itemView.findViewById(R.id.phoneNo);
            parentLayout=itemView.findViewById(R.id.cardRelativeLayout);
        }
    }
}
