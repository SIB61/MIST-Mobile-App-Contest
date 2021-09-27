package com.sib.healthcare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivitySplashScreenBinding;

import java.util.HashMap;

public class SplashScreenActivity extends AppCompatActivity {
String email="";
ActivitySplashScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();

       Animation animation= AnimationUtils.loadAnimation(this,R.anim.splash_anim);
       Animation animation1= AnimationUtils.loadAnimation(this,R.anim.splash_anim2);
       binding.imageView.setAnimation(animation);
       binding.textView23.setAnimation(animation1);
       binding.textView26.setAnimation(animation1);

       email = hm.get(SessionManager.EMAIL);
        new Handler().postDelayed((Runnable) () -> {
            if(email==null||email.equals("")||FirebaseAuth.getInstance().getCurrentUser()==null|| !FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                startActivity(new Intent(this, LoginScreenActivity.class).putExtra("Work","Splash"));
                finish();
            }
            else {
                startActivity(new Intent(this, MainActivity.class).putExtra("Work","Splash"));
                finish();
            }
        },3000);

    }
}