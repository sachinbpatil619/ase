package com.shimoga.asesol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shimoga.asesol.Common.ItemClickListener;
import com.shimoga.asesol.Model.Category;
import com.shimoga.asesol.Model.Doctor;
import com.shimoga.asesol.ViewHolder.DoctorViewHolder;
import com.shimoga.asesol.ViewHolder.MenuViewHolder;
import com.shimoga.asesol.ui.home.HomeViewModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {

    //Init Firebase
    FirebaseDatabase database;
    DatabaseReference category;

    RecyclerView recycler_doctor;
    RecyclerView.LayoutManager layoutManager;

    String doctorId = "";

    FirebaseRecyclerAdapter<Doctor, DoctorViewHolder> adapter;

    HashMap<String, String> image_list;
    SliderLayout mSlider;


    Doctor currentDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        database = FirebaseDatabase.getInstance();
        category = database.getReference("Doctor");
        recycler_doctor = (RecyclerView) findViewById(R.id.recycler_doctor);
        recycler_doctor.setLayoutManager(layoutManager);
        loadMenu();
        recycler_doctor.setLayoutManager(new LinearLayoutManager(DoctorDetailsActivity.this));

    }

    private void loadMenu() {
        adapter = new FirebaseRecyclerAdapter<Doctor, DoctorViewHolder>(Doctor.class, R.layout.doctor_list, DoctorViewHolder.class, category) {
            @Override
            protected void populateViewHolder(DoctorViewHolder doctorViewHolder, Doctor doctor, int i) {
                doctorViewHolder.doc_name.setText(doctor.getName());
                doctorViewHolder.city.setText(doctor.getCity());
                doctorViewHolder.hosp.setText(doctor.getHospitalName());
                doctorViewHolder.dept.setText(doctor.getDept());
                Picasso.with(DoctorDetailsActivity.this).load(doctor.getImage()).into(doctorViewHolder.doc_img);
                final Doctor clickItem = doctor;

                doctorViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(DoctorDetailsActivity.this, "" + clickItem.getName(), Toast.LENGTH_SHORT).show();
                        /*Intent foodList = new Intent(DoctorDetailsActivity.this, FoodList.class);
                        foodList.putExtra("DoctorId", adapter.getRef(position).getKey());
                        startActivity(foodList);*/
                    }
                });
            }
        };
        recycler_doctor.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}