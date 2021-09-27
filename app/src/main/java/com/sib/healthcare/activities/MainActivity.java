package com.sib.healthcare.activities;


import com.sib.healthcare.Medicine.medicine_main_activity;
import androidx.appcompat.app.AppCompatActivity;
import com.sib.healthcare.covid_section.covid_main_activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sib.healthcare.activities.consultancy.ConsFirstActivity;
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
        startActivity(new Intent(this, PostsandWatch.class));
    }

    public void goToCovid(View view) {
        startActivity(new Intent(this, covid_main_activity.class));
    }

    public void goToMedicine(View view) {
        startActivity(new Intent(this, medicine_main_activity.class));
    }

    public void goToProfile(View view) {
    }

    public void goToAboutUs(View view) {
    }
}