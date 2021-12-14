package com.shimoga.asesol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.shimoga.asesol.Common.ItemClickListener;
import com.shimoga.asesol.Database.Database;
import com.shimoga.asesol.Model.Food;
import com.shimoga.asesol.Model.Order;
import com.shimoga.asesol.ViewHolder.FoodViewHolder;
import com.shimoga.asesol.ViewHolder.FoodViewHolderNew;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodListNew extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;

    String categoryId = "";

    FirebaseRecyclerAdapter<Food, FoodViewHolderNew> adapter;

    //Search Bar Functionality
    FirebaseRecyclerAdapter<Food, FoodViewHolderNew> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    int count = 0;
    Food currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list_new);

        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Food");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_food_new);
        //recyclerView.setHasFixedSize(true);
        {
            if (getIntent() != null)
                categoryId = getIntent().getStringExtra("CategoryId");
            //categoryId = "2";


            if (!categoryId.isEmpty() && categoryId != null) {
                loadListFood(categoryId);
            }
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        }
    }

    private void loadListFood(String categoryId) {
        adapter=new FirebaseRecyclerAdapter<Food, FoodViewHolderNew>(Food.class, R.layout.food_item_new, FoodViewHolderNew.class, foodList.orderByChild("menuId").equalTo(categoryId) ) {
            @Override
            protected void populateViewHolder(FoodViewHolderNew viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                viewHolder.food_price.setText(model.getPrice());
                viewHolder.food_description.setText(model.getDescription());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);
                viewHolder.btnAdd.setVisibility(View.INVISIBLE);

                final Food local=model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(FoodListNew.this, "we are working on this", Toast.LENGTH_SHORT).show();
                    }
                });

                viewHolder.numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                    @Override
                    public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                        String num = viewHolder.numberButton.getNumber();

                        if (newValue > 0) {
                            viewHolder.btnAdd.setVisibility(View.VISIBLE);
                            count = newValue;
                        } else
                            viewHolder.btnAdd.setVisibility(View.INVISIBLE);
                    }
                });

                viewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(FoodListNew.this, model.getMenuId(), Toast.LENGTH_SHORT).show();
                       /* if (currentFood.getOutOfStock().equals("YES")){
                            Toast.makeText(FoodListNew.this, "This Product is not available !", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            new Database(getBaseContext()).addToCart(new Order(
                                    foodId,
                                    currentFood.getName(),
                                    numberButton.getNumber(),
                                    currentFood.getPrice(),
                                    currentFood.getDiscount()
                            ));
                            Toast.makeText(FoodListNew.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                        }*/
                    }
                });
            }
        };
        //set adapter
        recyclerView.setAdapter(adapter);
    }

}