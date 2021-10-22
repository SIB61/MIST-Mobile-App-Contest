package com.sib.healthcare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebViewClient;

import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivitySurokkhaBinding;

public class SurokkhaActivity extends AppCompatActivity {
private ActivitySurokkhaBinding binding;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySurokkhaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.loadUrl("https://www.google.com");


    }
}