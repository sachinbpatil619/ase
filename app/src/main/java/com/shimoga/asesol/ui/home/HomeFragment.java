package com.shimoga.asesol.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shimoga.asesol.Coconut;
import com.shimoga.asesol.Common.ItemClickListener;
import com.shimoga.asesol.DoctorDetailsActivity;
import com.shimoga.asesol.FoodList;
import com.shimoga.asesol.FoodListNew;
import com.shimoga.asesol.MenuList;
import com.shimoga.asesol.Model.Banner;
import com.shimoga.asesol.Model.Category;
import com.shimoga.asesol.R;
import com.shimoga.asesol.ViewHolder.MenuViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    //Init Firebase
    FirebaseDatabase database;
    DatabaseReference category;
    HashMap<String, String> image_list;
    SliderLayout mSlider;

    ImageView imgFood,imgDoctor,imgCoconut,imgGroceries;

    @Override
    public void onStop() {
        super.onStop();
        mSlider.stopAutoCycle();
    }

    @Override
    public void onStart() {
        super.onStart();
        mSlider.startAutoCycle();
    }

    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");
        mSlider = root.findViewById(R.id.slider);

        imgFood=(ImageView)root.findViewById(R.id.imgFood);
        imgDoctor=(ImageView)root.findViewById(R.id.imgDoctor);
        imgCoconut=(ImageView)root.findViewById(R.id.imgCoconut);
        imgGroceries=(ImageView)root.findViewById(R.id.imgGroceries);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                setUpSlider();

                //FOOD SECTION
                imgFood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent menuIntent = new Intent(getContext(), MenuList.class);
                        startActivity(menuIntent);
                    }
                });


                //COCONUT SECTION
                imgCoconut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent menuIntent = new Intent(getContext(), Coconut.class);
                        menuIntent.putExtra("MenuId","coconut");
                        startActivity(menuIntent);
                    }
                });

                //DOCTOR SECTION
                imgDoctor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent menuIntent = new Intent(getContext(), DoctorDetailsActivity.class);
                        menuIntent.putExtra("DoctorId","01");
                        startActivity(menuIntent);
                    }
                });

                //GROCERY SECTION
                imgGroceries.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent menuIntent = new Intent(getContext(), FoodListNew.class);
                        //menuIntent.putExtra("DoctorId","01");
                        startActivity(menuIntent);
                    }
                });
            }

        });
        return root;
    }
    private void setUpSlider() {
        image_list = new HashMap<>();

        final DatabaseReference banner = database.getReference("Banner");

        banner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Banner banner = postSnapShot.getValue(Banner.class);
                    //concat String name and id
                    image_list.put(banner.getName() + "_" + banner.getId(), banner.getImage());
                }
                for (String key : image_list.keySet()) {
                    String[] keySplit = key.split("_");
                    String nameOfFood = keySplit[0];
                    String idOfFood = keySplit[1];

                    //create slider
                    final TextSliderView textSliderView = new TextSliderView(getContext());
                    textSliderView
                            .description(nameOfFood)
                            .image(image_list.get(key))
                            .setScaleType(BaseSliderView.ScaleType.Fit  )
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    //go to next activity on click to show the product
                                    //in not needed just ignore this
                                    Toast.makeText(getContext(), "Did you know ?", Toast.LENGTH_SHORT).show();
                                    //Intent intent=new Intent(getContext(),Dashboard.class);
                                    //intent.putExtras(textSliderView.getBundle());
                                    //startActivity(intent);
                                }
                            });
                    //add extra bundle
                    //textSliderView.bundle(new Bundle());
                    //textSliderView.getBundle().putString();

                    mSlider.addSlider(textSliderView);
                    //remove event after finish
                    banner.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);

    }
}
