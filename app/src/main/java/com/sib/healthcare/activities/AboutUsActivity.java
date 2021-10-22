package com.sib.healthcare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivityAboutUsBinding;

import java.util.Objects;

public class AboutUsActivity extends AppCompatActivity {
private ActivityAboutUsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_about_us);
        setSupportActionBar(binding.materialToolbar5);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}