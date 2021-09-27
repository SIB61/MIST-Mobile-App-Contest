package com.sib.healthcare.covid_section;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hbb20.CountryCodePicker;
import com.sib.healthcare.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Statistics extends AppCompatActivity {

    TextView totall_case;
    TextView totall_death;
    TextView totall_recover;
    TextView totall_active;
    TextView totall_serious;
    TextView country_name;
    ImageView flags;
    CountryCodePicker  countryCodePicker;

    View back_to_covid_home_page;
    String url = new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.covid_statistics_activity);

        back_to_covid_home_page = findViewById(R.id.covid_statistics_activity_back);
        totall_case = findViewById(R.id.covid_statistic_total_case);
        totall_death = findViewById(R.id.covid_statistic_totall_death);
        totall_recover = findViewById(R.id.covid_statistic_totall_recovered);
        totall_active = findViewById(R.id.covid_statistic_totall_active);
        totall_serious = findViewById(R.id.covid_statistic_totall_serious);
        country_name = findViewById(R.id.covid_statistic_country_name);
        countryCodePicker = findViewById(R.id.covid_statistic_country_code_picker);

        flags = findViewById(R.id.flag);

        back_to_covid_home_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Statistics.this, covid_main_activity.class));
            }
        });

        countryCodePicker.showFullName(true);
        countryCodePicker.setShowPhoneCode(false);
        countryCodePicker.showNameCode(false);
        countryCodePicker.setCcpDialogShowPhoneCode(false);

        countryCodePicker.getDefaultCountryName();

        countryCodePicker.getSelectedCountryEnglishName();

        Toast.makeText(Statistics.this, countryCodePicker.getSelectedCountryEnglishName(), Toast.LENGTH_SHORT).show();

    }

    private void updateData(int i) {

        if(i==0){
            url = "https://coronavirus-19-api.herokuapp.com/countries/bangladesh";
            country_name.setText("Bangladesh");
        }
        else if(i == 1){
            url = "https://coronavirus-19-api.herokuapp.com/countries/india";
            country_name.setText("India");
        }
        else if(i==2){
            url = "https://coronavirus-19-api.herokuapp.com/countries/usa";
            country_name.setText("USA");
        }
        else if(i == 3){
            url = "https://coronavirus-19-api.herokuapp.com/countries/Malaysia";
            country_name.setText("Malaysia");
        }
        else if(i == 4){
            url = "https://coronavirus-19-api.herokuapp.com/countries/afghanistan";
            country_name.setText("Afghanistan");
        }
        else if(i==5){
            url = "https://coronavirus-19-api.herokuapp.com/countries/Mexico";
            country_name.setText("Mexico");
        }

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

                            // after extracting all the data we are
                            // setting that data to all our views.
                            totall_case.setText(t_case);
                            totall_death.setText(t_deaths);
                            totall_recover.setText(t_recovers);
                            totall_active.setText(t_active);
                            totall_serious.setText(t_critical);



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


}