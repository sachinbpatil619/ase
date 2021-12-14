package com.shimoga.asesol;

import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shimoga.asesol.Common.Common;
import com.shimoga.asesol.Common.LoggedUser;
import com.shimoga.asesol.Model.User;

public class LoginActivity extends AppCompatActivity {

    Button signup, login_btn;
    ImageView image;
    TextView logoText, sloganText;
    EditText phone, password;
    String mob;
    CheckBox ckbRemember;

    private SQLiteDatabase sql;
    DatabaseHelper db1;
//sdfcdz
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        signup = findViewById(R.id.newsignup);
        login_btn = findViewById(R.id.signin);
        image = findViewById(R.id.imageView);
        logoText = findViewById(R.id.logo_welcome_text);
        //sloganText=findViewById(R.id.signin_text);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);

        ckbRemember=(CheckBox)findViewById(R.id.ckbRemember);
        ckbRemember.setChecked(true);

        //INIT PAPERDB
        Paper.init(this);

        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        db1 = new DatabaseHelper(this);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Please wait, Loading...");
                mDialog.show();
                mob = phone.getText().toString();
                mob = "+91" + mob;

                if (ckbRemember.isChecked())
                {
                    Paper.book().write(Common.USR_KEY,mob.toString());
                    Paper.book().write(Common.PWD_KEY,password.getText().toString());
                    //Toast.makeText(LoginActivity.this, "Paper wrote", Toast.LENGTH_SHORT).show();
                }

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //check if user not exist on database
                        if (dataSnapshot.child(mob).exists()) {
                            mDialog.dismiss();
                            //Get user info
                            User user = dataSnapshot.child(mob).getValue(User.class);
                            user.setPhone(mob);//set Phone Number
                            if (user.getPassword().equals(password.getText().toString())) {
                                Toast.makeText(LoginActivity.this, "Log-in Successful", Toast.LENGTH_SHORT).show();
                                boolean isInserted = db1.addUser(mob);
                            /* if (isInserted)
                                   Toast.makeText(Login.this, "Sign-in Successfull", Toast.LENGTH_SHORT).show();
                               else
                                   Toast.makeText(Login.this, "Sign-in Failed due to Database", Toast.LENGTH_SHORT).show();    */

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                Common.currentUser = user;
                                user.setPhone(mob);
                                LoggedUser loggedUser = new LoggedUser();
                                loggedUser.setPhone(mob);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "failed to Log-in", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "User not exist " + mob, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PhoneNumber.class);
                startActivity(intent);

            }
        });
    }
}
