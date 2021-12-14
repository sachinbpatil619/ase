package com.shimoga.asesol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shimoga.asesol.Common.ProfileData;

public class ViewProfile extends AppCompatActivity {

    TextView vFullName,vPhone;
    ImageView vProfileImage;
    TextInputEditText tvfullname,tvemail,tvaddress,tvcity,tvpin;

    DatabaseHelper db1;

    FirebaseDatabase database;
    DatabaseReference user;

    ProfileData profileData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
    }
}