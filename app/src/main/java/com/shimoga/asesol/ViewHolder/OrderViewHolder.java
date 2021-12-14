package com.shimoga.asesol.ViewHolder;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shimoga.asesol.Interface.ItemClickListener;
import com.shimoga.asesol.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtorderAddress;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        txtorderAddress=(TextView) itemView.findViewById(R.id.order_address);
        txtOrderId=(TextView) itemView.findViewById(R.id.order_id);
        txtOrderStatus=(TextView) itemView.findViewById(R.id.order_status);
        txtOrderPhone=(TextView) itemView.findViewById(R.id.order_phone);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Thanks for Ordering your order will arrive soon", Toast.LENGTH_SHORT).show();
        //itemClickListener.onClick(view, getAdapterPosition(),false);
    }
}
