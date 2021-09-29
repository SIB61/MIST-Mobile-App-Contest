package com.sib.healthcare.covid_section;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hbb20.CountryCodePicker;
import com.sib.healthcare.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.SplittableRandom;


public class Statistics extends AppCompatActivity {
    TextView totall_case;
    TextView totall_death;
    TextView totall_recover;
    TextView totall_active;
    TextView totall_serious;
    TextView today_news;
    TextView totall_news;

    LinearLayout chartlayout;
    ImageView flags;
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList<BarEntry> barEntries;


    String todays_affected = new String();
    String todays_deaths = new String();
    Button country , world ;
    private String countryName = "Bangladesh";
    CountryCodePicker countryCodePicker;
    CardView serious_case ,active_case,recover_case;


    View back_to_covid_home_page;
    String url = new String();
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.covid_statistics_activity);

        back_to_covid_home_page = findViewById(R.id.covid_statistics_activity_back);
        totall_case = findViewById(R.id.covid_statistic_totall_case);
        totall_death = findViewById(R.id.covid_statistic_totall_death);
        totall_recover = findViewById(R.id.covid_statistic_totall_recovered);
        totall_active = findViewById(R.id.covid_statistic_totall_active);
        totall_serious = findViewById(R.id.covid_statistic_totall_serious);
        countryCodePicker = findViewById(R.id.covid_statistic_country_code_picker);
        chartlayout = findViewById(R.id.covid_statistic_chartlayout);
       // golbal_info = findViewById(R.id.covid_statistic_global_info);
        active_case = findViewById(R.id.cardView3);
        serious_case = findViewById(R.id.cardView5);
        recover_case = findViewById(R.id.cardView4);
        country = findViewById(R.id.covid_statistic_country_name);
        world=findViewById(R.id.button4);
        today_news = findViewById(R.id.covid_statistic_today);
        totall_news = findViewById(R.id.covid_statistic_totall);
        barChart=findViewById(R.id.barChart);
        barEntries = new ArrayList<>();


        barEntries.add(new BarEntry(1f,Integer.parseInt("667765")));
        barEntries.add(new BarEntry(2f,Integer.parseInt("9795")));
        barEntries.add(new BarEntry(3f,Integer.parseInt("5765")));

        barEntries.add(new BarEntry(4f,Integer.parseInt("370365")));
        barEntries.add(new BarEntry(5f,Integer.parseInt("12786")));

        barDataSet=new BarDataSet(barEntries,"Data Set");
        barData=new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(12.0f);


        flags = findViewById(R.id.flag);

        updateData(countryCodePicker.getSelectedCountryName());
        back_to_covid_home_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Statistics.this, covid_main_activity.class));
            }
        });

        countryCodePicker.setOnCountryChangeListener(() -> {
             countryName=countryCodePicker.getSelectedCountryName();
             country.setText(countryName);
             updateData(countryName);

        });

        world.setOnClickListener(v -> {
            updateData("world");


            country.setBackgroundColor(getColor(R.color.purple_200));
            country.setTextColor(getColor(R.color.white));
            world.setBackgroundColor(getColor(R.color.white));
            world.setTextColor(getColor(R.color.black));

        });

        country.setOnClickListener(v -> {
            updateData(countryName);

            active_case.setVisibility(View.VISIBLE);
            serious_case.setVisibility(View.VISIBLE);

            world.setBackgroundColor(getColor(R.color.purple_200));
            world.setTextColor(getColor(R.color.white));
            country.setBackgroundColor(getColor(R.color.white));
            country.setTextColor(getColor(R.color.black));
        });





    }


    private void updateData(String c) {

        url = "https://coronavirus-19-api.herokuapp.com/countries/"+c;
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Statistics.this);

        // to get the response from our API.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                // inside the on response method.
//                // we are hiding our progress bar.
//                loadingPB.setVisibility(View.GONE);
//
//                // in below line we are making our card
//                // view visible after we get all the data.
//                courseCV.setVisibility(View.VISIBLE);
                        try {
                            // now we get our response from API in json object format.
                            // in below line we are extracting a string with its key
                            // value from our json object.
                            // similarly we are extracting all the strings from our json object.


                            String t_case = response.getString("cases");
                            String t_deaths = response.getString("deaths");
                            String t_recovers = response.getString("recovered");
                            String t_active = response.getString("active");
                            String t_critical = response.getString("critical");

                            todays_affected = response.getString("todayCases");
                            todays_deaths = response.getString("todayDeaths");

                            // after extracting all the data we are
                            // setting that data to all our views.
                            totall_active.setText(t_active);
                            totall_serious.setText(t_critical);
                            totall_case.setText(t_case);
                            totall_death.setText(t_deaths);
                            totall_recover.setText(t_recovers);

                            getEntries(t_case, t_deaths, t_recovers, t_active, t_critical);




                        } catch (JSONException e) {
                            // if we do not extract data from json object properly.
                            // below line of code is use to handle json exception
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    // this is the error listener method which
                    // we will call if we get any error from API.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // below line is use to display a toast message along with our error.
                        Toast.makeText(Statistics.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
                    }
                });
        // at last we are adding our json
        // object request to our request
        // queue to fetch all the json data.
        queue.add(jsonObjectRequest);

    }

    public void getEntries(String affected, String deaths, String recover, String active, String serious)
    {
        barChart.setAutoScaleMinMaxEnabled(true);
        barChart.invalidate();
        barChart.setTouchEnabled(false);
        barEntries.set(0, new BarEntry(1f,Integer.parseInt(affected)));
        barEntries.set(1, new BarEntry(2f,Integer.parseInt(deaths)));
        barEntries.set(2, new BarEntry(3f,Integer.parseInt(recover)));
        barEntries.set(3, new BarEntry(4f,Integer.parseInt(active)));
        barEntries.set(4, new BarEntry(5f,Integer.parseInt(serious)));

    }
}