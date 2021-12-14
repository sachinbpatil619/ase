package com.shimoga.asesol.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.shimoga.asesol.Common.ItemClickListener;
import com.shimoga.asesol.R;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodViewHolderNew extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView food_name;
    public TextView food_description;
    public TextView food_price;
    public ImageView food_image;
    public ElegantNumberButton numberButton;
    public Button btnAdd;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodViewHolderNew(@NotNull View itemView) {
        super(itemView);

        food_name = (TextView) itemView.findViewById(R.id.food_name_new);
        food_price = (TextView) itemView.findViewById(R.id.food_price_new);
        food_description = (TextView) itemView.findViewById(R.id.food_recomonded);
        food_image = (ImageView) itemView.findViewById(R.id.food_image_new);
        numberButton = (ElegantNumberButton) itemView.findViewById(R.id.number_of_items);
        btnAdd = (Button) itemView.findViewById(R.id.btnAdd);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
