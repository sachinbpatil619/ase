package com.shimoga.asesol.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shimoga.asesol.Common.ItemClickListener;
import com.shimoga.asesol.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView doc_name,city,dept,hosp;
    public ImageView doc_img;

    private ItemClickListener itemClickListener;

    public DoctorViewHolder(@NonNull View itemView) {
        super(itemView);
        doc_name=(TextView)itemView.findViewById(R.id.doc_name);
        doc_img=(ImageView) itemView.findViewById(R.id.doc_image);
        city=(TextView) itemView.findViewById(R.id.doc_city);
        dept=(TextView) itemView.findViewById(R.id.doc_dept);
        hosp=(TextView) itemView.findViewById(R.id.doc_hosp);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View view) {
        itemView.setOnClickListener(this);
    }
}

