package com.sib.healthcare.Activities.Consultancy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.sib.healthcare.Adapter.Consultancy.AsAdapter;
import com.sib.healthcare.DataModels.AppointmentModel;
import com.sib.healthcare.databinding.ActivityAskSpecialistsBinding;

import java.util.ArrayList;
import java.util.List;

public class AskSpecialistsActivity extends AppCompatActivity {
private ActivityAskSpecialistsBinding binding;
private CollectionReference collectionReference;
private Query query;
private FirebaseUser user;
private AsAdapter adapter;
private AppointmentModel appointmentModel;
private List<AppointmentModel> appointmentModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAskSpecialistsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarAS);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ask the specialists");
        collectionReference= FirebaseFirestore.getInstance().collection("Questions");
        user= FirebaseAuth.getInstance().getCurrentUser();
        query=collectionReference.whereEqualTo("uId",user.getUid());
        appointmentModels = new ArrayList<>();
        adapter = new AsAdapter(this, appointmentModels);
        binding.questionListAS.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.questionListAS.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        query.addSnapshotListener( (value, error) -> {

        });
    }
}