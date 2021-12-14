package com.shimoga.asesol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shimoga.asesol.Database.Database;
import com.shimoga.asesol.Model.Food;
import com.shimoga.asesol.Model.Mobile;
import com.shimoga.asesol.Model.Order;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FoodDetails extends AppCompatActivity {

    List<Order> cart = new ArrayList<>();
    double totalAmt = 0;
    TextView food_name, food_price, food_description,tvOutOfStock;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Button btnCart;
    ElegantNumberButton numberButton;
    Button btngotoCart;
    //TextView txtTotalPrice;

    String foodId = "";
    FirebaseDatabase database;
    DatabaseReference foods;

    Food currentFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Food");

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (Button) findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentFood.getOutOfStock().equals("YES")){
                    Toast.makeText(FoodDetails.this, "This Product is not available !", Toast.LENGTH_SHORT).show();
                }
                else{
                    new Database(getBaseContext()).addToCart(new Order(
                            foodId,
                            currentFood.getName(),
                            numberButton.getNumber(),
                            currentFood.getPrice(),
                            currentFood.getDiscount()
                    ));
                    Toast.makeText(FoodDetails.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

        food_description = (TextView) findViewById(R.id.food_description);
        food_name = (TextView) findViewById(R.id.food_name);
        food_price = (TextView) findViewById(R.id.food_price);
        food_image = (ImageView) findViewById(R.id.img_food);
        tvOutOfStock=findViewById(R.id.tvOutOfStock);
        //txtTotalPrice = findViewById(R.id.mtvtotal);
        btngotoCart = findViewById(R.id.btngotoCart);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if (getIntent() != null){
            foodId = getIntent().getStringExtra("FoodId");
            Log.d("myTag", "This is my message "+foodId);
        }
        if (!foodId.isEmpty()) {
            getDetailsFood(foodId);
        }
        btngotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FoodDetails.this,Cart.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void getDetailsFood(final String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentFood = snapshot.getValue(Food.class);

                Picasso.with(getBaseContext()).load(currentFood.getImage())
                        .into(food_image);

                collapsingToolbarLayout.setTitle(currentFood.getName());
                food_price.setText(currentFood.getPrice());
                food_name.setText(currentFood.getName());
                food_description.setText(currentFood.getDescription());
                if (currentFood.getOutOfStock().equals("YES"))
                {
                    tvOutOfStock.setText("Out of Stock");
                    tvOutOfStock.setTextColor(Color.RED);
                }
                cart = new Database(FoodDetails.this).getCarts();
                for (Order order : cart)
                    totalAmt += (Double.parseDouble(order.getPrice())) * (Double.parseDouble(order.getQuantity()));
                Locale locale = new Locale("en", "IN");
                NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

                //txtTotalPrice.setText("Total: " + fmt.format(totalAmt));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}