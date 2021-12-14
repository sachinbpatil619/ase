package com.shimoga.asesol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import io.paperdb.Paper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shimoga.asesol.Common.Common;
import com.shimoga.asesol.Common.LoggedUser;
import com.shimoga.asesol.Model.User;

import org.w3c.dom.Text;

public class IntroductoryActivity extends AppCompatActivity {

    ImageView logo, splashImg;
    TextView appName;
    LottieAnimationView lottieAnimationView;

    private static final int NUM_PAGES = 3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;

    Animation anim;

    private static int SPLASH_TIME_OUT = 5000;
    SharedPreferences mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_introductory);

        logo = findViewById(R.id.logo);
        splashImg = findViewById(R.id.img);
        appName = findViewById(R.id.app_name);
        //lottieAnimationView = findViewById(R.id.lottie);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        Paper.init(this);

        final String usr = Paper.book().read(Common.USR_KEY);
        final String pwd = Paper.book().read(Common.PWD_KEY);

        //anim= AnimationUtils.loadAnimation(this,R.anim.o_b_anim);
        //viewPager.startAnimation(anim);

        splashImg.animate().translationY(-3200).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(3200).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(3200).setDuration(1000).setStartDelay(4000);
        //lottieAnimationView.animate().translationY(3200).setDuration(1000).setStartDelay(4000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSharedPref = getSharedPreferences("SharedPref", MODE_PRIVATE);
                boolean isFirstTime = mSharedPref.getBoolean("firstTime", true);

                if (usr != null && pwd != null) {
                    login(usr, pwd);
                }  else if (isFirstTime) {
                    SharedPreferences.Editor editor = mSharedPref.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();
                }else  {
                    Intent intent = new Intent(IntroductoryActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);


    }

    private void login(final String usr, final String pwd) {
        final ProgressDialog mDialog = new ProgressDialog(IntroductoryActivity.this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        mDialog.setMessage("Please wait, Loading...");
        mDialog.show();
        //mob = phone.getText().toString();
        //usr = "+91" + usr;

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //check if user not exist on database
                if (dataSnapshot.child(usr).exists()) {
                    mDialog.dismiss();
                    //Get user info
                    User user = dataSnapshot.child(usr).getValue(User.class);
                    user.setPhone(usr);//set Phone Number
                    if (user.getPassword().equals(pwd)) {
                        Toast.makeText(IntroductoryActivity.this, "Log-in Successful", Toast.LENGTH_SHORT).show();
                         /* boolean isInserted = db1.addUser(user);
                           if (isInserted)
                                   Toast.makeText(Login.this, "Sign-in Successfull", Toast.LENGTH_SHORT).show();
                               else
                                   Toast.makeText(Login.this, "Sign-in Failed due to Database", Toast.LENGTH_SHORT).show();    */

                        Intent intent = new Intent(IntroductoryActivity.this, HomeActivity.class);
                        Common.currentUser = user;
                        user.setPhone(usr);
                        LoggedUser loggedUser = new LoggedUser();
                        loggedUser.setPhone(usr);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(IntroductoryActivity.this, "failed to Log-in", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mDialog.dismiss();
                    Toast.makeText(IntroductoryActivity.this, "User not exist " + usr, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    OnBoardingFragment1 tab1 = new OnBoardingFragment1();
                    return tab1;
                case 1:
                    OnBoardingFragment2 tab2 = new OnBoardingFragment2();
                    return tab2;
                case 2:
                    OnBoardingFragment3 tab3 = new OnBoardingFragment3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}