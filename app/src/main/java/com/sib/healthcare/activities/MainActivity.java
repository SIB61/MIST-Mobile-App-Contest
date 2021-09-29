package com.sib.healthcare.activities;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sib.healthcare.Medicine.medicine_main_activity;
import androidx.appcompat.app.AppCompatActivity;

import com.sib.healthcare.activities.consultancy.EditProfileActivity;
import com.sib.healthcare.covid_section.covid_main_activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sib.healthcare.activities.consultancy.ConsFirstActivity;
import com.sib.healthcare.R;
import com.sib.healthcare.models.UserDataModel;

public class MainActivity extends AppCompatActivity {
private DocumentReference documentReference;
private UserDataModel userDataModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestore.getInstance().document("Users/"+FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(documentSnapshot -> userDataModel=documentSnapshot.toObject(UserDataModel.class));
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
        startActivity(new Intent(this, EditProfileActivity.class).putExtra("userDataModel",userDataModel));
    }

    public void goToAboutUs(View view) {
    }
}