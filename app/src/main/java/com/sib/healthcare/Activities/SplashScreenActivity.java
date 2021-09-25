package com.sib.healthcare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sib.healthcare.R;

import java.util.HashMap;

public class SplashScreenActivity extends AppCompatActivity {
String email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
       email = hm.get(SessionManager.EMAIL);
        new Handler().postDelayed((Runnable) () -> {
            if(email==null||email.equals("")) {
                startActivity(new Intent(this, LoginScreenActivity.class).putExtra("Work","Splash"));
                finish();
            }
            else {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        },3000);

    }
}