package com.shimoga.asesol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shimoga.asesol.Database.Database;
import com.shimoga.asesol.Model.Category;
import com.shimoga.asesol.Model.CategoryOld;
import com.shimoga.asesol.Model.Food;
import com.shimoga.asesol.Model.Order;
import com.shimoga.asesol.ViewHolder.CartAdapter;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Coconut extends AppCompatActivity {

    private static final int CALL_PERMISSION_CODE = 100;

    Button btnLogout;
    FirebaseUser firebaseUser;
    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;
    RecyclerView recyclerView;

    int skinLess=0,cleaned=0;
    double totalAmt = 0;
    String nameOfTheFood="";
    String newNameOfTheFood="";
    int newPrice=0;
    String strNewPrice="";

    TextView food_name, food_price, food_description, food_kg, food_kg1,tvOutOfStock;
    ImageView food_image;
    EditText weightFood;
    TextView txtTotalPrice;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    Button gotoCart, btnWhatsapp, btnCall,btngotoCart;
    ElegantNumberButton numberButton;
    CardView weigtdiv;
    Switch aSwitch1;
    String foodId = "";
    FirebaseDatabase database;
    DatabaseReference foods;

    CategoryOld currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coconut);

        food_description = findViewById(R.id.food_description);
        food_name = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);
        food_image = findViewById(R.id.img_food);
        food_kg = findViewById(R.id.food_kg);
        food_kg1 = findViewById(R.id.food_kg1);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        aSwitch1 = findViewById(R.id.switch1);

        //firebase init
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Coconut");

        //init view
        numberButton = findViewById(R.id.number_button);
        btnCart = findViewById(R.id.btnCart);
        gotoCart = findViewById(R.id.btngotoCart);
        txtTotalPrice = findViewById(R.id.tvtotal);
        weigtdiv = findViewById(R.id.weightDiv);
        btnWhatsapp = findViewById(R.id.btnWhatsapp);
        btnCall = findViewById(R.id.btnCall);
        weightFood = findViewById(R.id.edtWeight);
        tvOutOfStock=findViewById(R.id.tvOutOfStock);
        btngotoCart=findViewById(R.id.btngotoCart);

        loadListFood();

        if (getIntent() != null)
            foodId = getIntent().getStringExtra("MenuId");
        if (!foodId.isEmpty()) {
            getDetailFood(foodId);
        } else
            Toast.makeText(this, "sorry... error occurred", Toast.LENGTH_SHORT).show();

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentFood.getOutOfStock().equals("YES"))
                    Toast.makeText(Coconut.this, "This Product is not available !", Toast.LENGTH_SHORT).show();
                else
                    addFoodsToCart();
            }
        });

        btngotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Coconut.this,Cart.class);
                startActivity(intent);
                finish();
            }
        });


        aSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    weigtdiv.setVisibility(View.VISIBLE);
                    food_price.setText(currentFood.getWprice());
                    aSwitch1.setText("In KG (Rs."+currentFood.getWprice()+" /KG)");
                    btnCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            addFoodsToCartbyHand();
                        }
                    });

                } else {
                    weigtdiv.setVisibility(View.GONE);
                    food_price.setText(currentFood.getPrice());
                    aSwitch1.setText("In KG (Rs."+currentFood.getWprice()+" /KG)");
                    btnCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            addFoodsToCart();
                        }
                    });

                }
            }
        });
    }

    private void getDetailFood(final String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(CategoryOld.class);

                //set image
                Picasso.with(getBaseContext()).load(currentFood.getImage())
                        .into(food_image);

                collapsingToolbarLayout.setTitle(currentFood.getName());
                food_price.setText(currentFood.getPrice());
                food_name.setText(currentFood.getName());
                food_description.setText(currentFood.getDescription());
                aSwitch1.setText("In KG (Rs."+currentFood.getWprice()+" /KG)");

                newPrice=Integer.parseInt(currentFood.getPrice());

                nameOfTheFood=currentFood.getName();

                if (currentFood.getOutOfStock().equals("YES"))
                {
                    tvOutOfStock.setText("Out of Stock");
                    tvOutOfStock.setTextColor(Color.RED);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadListFood() {
        cart = new Database(this).getCarts();
        //Price Calculation
        for (Order order : cart)
            totalAmt += (Double.parseDouble(order.getPrice())) * (Double.parseDouble(order.getQuantity()));
        Locale locale = new Locale("en", "IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText("Total: " + fmt.format(totalAmt));
    }



    private void addFoodsToCart() {
        new Database(getBaseContext()).addToCart(new
                Order(foodId,
                currentFood.getName(),
                numberButton.getNumber(),
                currentFood.getPrice(),
                currentFood.getDiscount()
        ));
        Toast.makeText(Coconut.this, "Added to Cart", Toast.LENGTH_SHORT).show();
        loadListFood();
    }

    private void addFoodsToCartbyHand() {
        double weightx=Double.parseDouble(weightFood.getText().toString().trim());
        weightx=round(weightx,2);
        weightx=weightx/1000;

        new Database(getBaseContext()).addToCart(new
                Order(foodId,
                currentFood.getName(),
                String.valueOf(weightx),
                currentFood.getWprice() ,
                currentFood.getDiscount()
        ));
        Toast.makeText(Coconut.this, "Added to Cart", Toast.LENGTH_SHORT).show();
        loadListFood();
    }

    public static double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}