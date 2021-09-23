package com.sib.healthcare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sib.healthcare.R;

public class SplashScreenActivity extends AppCompatActivity {
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed((Runnable) () -> {
            if(user != null && user.isEmailVerified())
                startActivity(new Intent(this,MainActivity.class));
            else
                startActivity(new Intent(this,LoginScreenActivity.class));
        },3000);
    }
}