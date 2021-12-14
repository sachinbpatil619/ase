package com.shimoga.asesol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class PhoneNumber extends AppCompatActivity {
    EditText edtPhone;
    Button next,alreadyuser;
    ImageView closeactivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        edtPhone = findViewById(R.id.phoneVerify);
        next=findViewById(R.id.btnPhone);
        closeactivity=findViewById(R.id.closeactivity);
        alreadyuser=findViewById(R.id.alreadyuser);

        closeactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhoneNumber.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        alreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhoneNumber.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number=edtPhone.getText().toString().trim();
                String phonenumber = "+91" + number;
                Intent intent = new Intent(PhoneNumber.this, VerifyOTP.class);
                intent.putExtra("phonenumber", phonenumber);
                startActivity(intent);
            }
        });
    }
}
