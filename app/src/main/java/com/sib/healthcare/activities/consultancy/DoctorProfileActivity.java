package com.sib.healthcare.activities.consultancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.models.UserDataModel;
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
        setSupportActionBar(binding.toolbarDP);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Doctor Profile");
        binding.profilePic.setImageResource(R.drawable.doctor);
        userDataModel=getIntent().getParcelableExtra("userDataModel");
        storageReference= FirebaseStorage.getInstance().getReference(userDataModel.getImage());
        setView();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if(userDataModel.getuId().equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()))
        {
            binding.buttonDP.setText("Edit Profile Info");
        }
        binding.buttonDP.setOnClickListener( v -> {
            try {
                if(userDataModel.getuId().equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()))
                {
                    startActivity(new Intent(this, EditProfileActivity.class).putExtra("userDataModel", userDataModel));
                }
                else
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
       String str = "MBBS from "+userDataModel.getMbbs()+"\n"+userDataModel.getDegrees()+"\n\nClinic Address : "
               +userDataModel.getClinicAddress()+"\n\nAvailable at "+userDataModel.getTime1()+" to "+userDataModel.getTime2()
               +" every "+userDataModel.getDay1()+"day"+" to "+userDataModel.getDay2()+"day";
       binding.desAD.setText(str);
    }

    public void goToChat(View view) {
        startActivity(new Intent(DoctorProfileActivity.this,ChatActivity.class).putExtra("uId",userDataModel.getuId()));
    }
}