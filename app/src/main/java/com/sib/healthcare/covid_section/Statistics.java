package com.sib.healthcare.covid_section;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sib.healthcare.R;

public class Statistics extends AppCompatActivity {

    View back_to_covid_home_page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.covid_statistics_activity);

        back_to_covid_home_page = findViewById(R.id.covid_statistics_activity_back);

        back_to_covid_home_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Statistics.this, covid_main_activity.class));
            }
        });
    }
}