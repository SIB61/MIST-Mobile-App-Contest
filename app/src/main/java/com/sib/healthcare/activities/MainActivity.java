package com.sib.healthcare.activities;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sib.healthcare.Medicine.AddMedicine;
import com.sib.healthcare.Medicine.Aksing;
import com.sib.healthcare.Medicine.OwnerProfile;
import com.sib.healthcare.Medicine.User_Section;
import com.sib.healthcare.Medicine.medicine_main_activity;
import androidx.appcompat.app.AppCompatActivity;

import com.sib.healthcare.activities.consultancy.EditProfileActivity;
import com.sib.healthcare.covid_section.covid_main_activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sib.healthcare.activities.consultancy.ConsFirstActivity;
import com.sib.healthcare.R;
import com.sib.healthcare.models.UserDataModel;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private DocumentReference documentReference;
    private UserDataModel userDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestore.getInstance().document("Users/" + FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(documentSnapshot -> userDataModel = documentSnapshot.toObject(UserDataModel.class));
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
        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        String phone2 = hm.get(SessionManager.EMAIL);
        String BNAME = hm.get(SessionManager.BNAME);
        String t = hm.get(SessionManager.WHAT);

        if (t.equals("NormalUser")) {
            startActivity(new Intent(getApplicationContext(), User_Section.class));
            finish();
        } else if (t.equals("Normal")) {
            startActivity(new Intent(getApplicationContext(), Aksing.class));
            finish();
        } else {
            if (BNAME.equals("No")) {
                startActivity(new Intent(getApplicationContext(), AddMedicine.class).putExtra("Work","yui"));
                finish();
            } else {
                startActivity(new Intent(getApplicationContext(), OwnerProfile.class));
            }
        }
    }
        public void goToProfile (View view){
            startActivity(new Intent(this, EditProfileActivity.class).putExtra("userDataModel", userDataModel));
        }
        public void goToAboutUs (View view){
        startActivity(new Intent(this,AboutUsActivity.class));
        }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder  builder = new AlertDialog.Builder(this);

        builder = new AlertDialog.Builder(this);

        builder.setMessage("Are you sure you want to exit??")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                        finish();

////            finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Exit?");
        alert.show();
    }
}