package com.sib.healthcare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sib.healthcare.Activities.Consultancy.ConsFirstActivity;
import com.sib.healthcare.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToConsultancy(View view) {
       startActivity(new Intent(this, ConsFirstActivity.class));
    }

    public void goToBloodBank(View view) {
    }

    public void goToCovid(View view) {
    }

    public void goToMedicine(View view) {
    }

    public void goToProfile(View view) {
    }

    public void goToAboutUs(View view) {
    }
}