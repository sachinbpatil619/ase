package com.shimoga.asesol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shimoga.asesol.Common.*;
import com.shimoga.asesol.Model.Food;
import com.shimoga.asesol.Model.User;

import org.jetbrains.annotations.NotNull;

public class ProfileActivity extends AppCompatActivity {

    TextView vFullName,vPhone;
    ImageView vProfileImage;
    TextInputEditText tvfullname,tvemail,tvaddress,tvcity,tvpin;
    Button btnUpdateProfile;

    DatabaseHelper db1;

    FirebaseDatabase database;
    DatabaseReference user;

    ProfileData profileData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        vFullName=findViewById(R.id.txtVfullName);
        vPhone=findViewById(R.id.txtVphone);
        vProfileImage=findViewById(R.id.imgProfile);

        tvfullname=findViewById(R.id.tvfullname);
        tvemail=findViewById(R.id.tvemail);
        tvaddress=findViewById(R.id.tvaddress);
        tvcity=findViewById(R.id.tvcity);
        tvpin=findViewById(R.id.tvpincode);
        btnUpdateProfile=findViewById(R.id.btnUpdate);

        Paper.init(this);
        final String usr = Paper.book().read(Common.USR_KEY);

        database = FirebaseDatabase.getInstance();
        user = database.getReference("User");

        getDetailsFood(usr);

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Updated Soon...!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getDetailsFood(final String usr){
        user.child(usr).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                profileData= snapshot.getValue(ProfileData.class);

                vFullName.setText(profileData.getName());
                vPhone.setText(usr);

                tvfullname.setText(profileData.getName());
                tvemail.setText(profileData.getEmail());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}