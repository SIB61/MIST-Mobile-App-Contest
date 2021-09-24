package com.sib.healthcare.Activities.Consultancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sib.healthcare.Adapter.Consultancy.TopDrListAdapter;
import com.sib.healthcare.DataModels.UserDataModel;
import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivityConsFirstBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConsFirstActivity extends AppCompatActivity {
    private ActivityConsFirstBinding binding;
    private UserDataModel userDataModel;
    private FirebaseUser currentUser;
    private DocumentReference documentReference;
    private TopDrListAdapter adapter;
    private List<UserDataModel> userDataModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConsFirstBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.toolbarId);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        userDataModel=new UserDataModel();
        userDataModels=new ArrayList<>();
        documentReference= FirebaseFirestore.getInstance().document("Users/"+currentUser.getUid());
        adapter=new TopDrListAdapter(userDataModels);
        binding.doctorListId.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.doctorListId.setAdapter(new TopDrListAdapter(userDataModels));

    }

    @Override
    protected void onStart() {
        super.onStart();
        documentReference.addSnapshotListener((value, error) -> {
            if (value != null) {
                userDataModel=value.toObject(UserDataModel.class);
                userDataModels.add(userDataModel);
                adapter=new TopDrListAdapter(userDataModels);
                binding.doctorListId.setAdapter(adapter);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_bar_menu,menu);
        if(userDataModel.isDoctor()){
            menu.findItem(R.id.reg).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.reg:
               startActivity(new Intent(ConsFirstActivity.this,EditProfileActivity.class).putExtra("TAG","register").putExtra("userDataModel",  userDataModel));
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    public void goToAskSpecialistsActivity(View view) {
        startActivity(new Intent(this, AskSpecialistsActivity.class));
    }

    public void goToDoctorListActivity(View view) {
       Intent intent=new Intent(this,DoctorListActivity.class);
        switch (view.getId())
        {
            case R.id.heart:
                intent.putExtra("type","Cardiologists");
                break;
            case R.id.brain:
                intent.putExtra("type","Neurologists");
                break;
            case R.id.dental:
                intent.putExtra("type","Dental");
                break;
            case R.id.all:
                intent.putExtra("type","All");
                break;
        }
     startActivity(intent);

    }
}