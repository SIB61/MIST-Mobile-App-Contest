package com.sib.healthcare.Activities.Consultancy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.DataModels.AppointmentModel;
import com.sib.healthcare.DataModels.UserDataModel;
import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivityDoctorProfileBinding;

import java.util.Objects;

public class DoctorProfileActivity extends AppCompatActivity {
private ActivityDoctorProfileBinding binding;
private UserDataModel userDataModel;
private StorageReference storageReference;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDoctorProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.profilePic.setImageResource(R.drawable.doctor);
        userDataModel=getIntent().getParcelableExtra("userDataModel");
        storageReference= FirebaseStorage.getInstance().getReference(userDataModel.getImage());
        setView();
        setSupportActionBar(binding.toolbarDP);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if(userDataModel.getuId().equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()))
        {
            binding.buttonDP.setText("Edit Profile Info");
        }
        binding.buttonDP.setOnClickListener( v -> {
            try {
                startActivity(new Intent(this, AppointmentBookingActivity.class).putExtra("userDataModel", userDataModel));
            }
            catch (Exception e){
                Log.d("TAG", "onCreate: "+e.toString());
            }
               });
    }

    private void setView() {
        binding.nameDP.setText(userDataModel.getName());
        binding.typeDP.setText(userDataModel.getType());
       storageReference.getDownloadUrl().addOnSuccessListener( uri -> {
          Glide.with(this).load(uri).into(binding.profilePic);
       });
    }

}