package com.sib.healthcare.activities.consultancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.sib.healthcare.models.UserDataModel;
import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivityConsFirstBinding;

import java.util.Objects;

public class ConsFirstActivity extends AppCompatActivity {
    private ActivityConsFirstBinding binding;
    private UserDataModel userDataModel;
    private FirebaseUser currentUser;
    private DocumentReference documentReference;
    private FirestoreRecyclerOptions<UserDataModel> options;
    private Query query;
    private FirestoreRecyclerAdapter<UserDataModel,TopDrViewHolder> adapter;

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
        documentReference= FirebaseFirestore.getInstance().document("Users/"+currentUser.getUid());
        //adapter=new TopDrListAdapter(userDataModels);
        query=FirebaseFirestore.getInstance().collection("Doctors").limit(10);
        options=new FirestoreRecyclerOptions.Builder<UserDataModel>()
                .setQuery(query,UserDataModel.class).build();
        adapter=new FirestoreRecyclerAdapter<UserDataModel, TopDrViewHolder>(options) {
            @SuppressLint("CheckResult")
            @Override
            protected void onBindViewHolder(@NonNull TopDrViewHolder holder, int position, @NonNull UserDataModel model) {
                holder.name.setText(model.getName());
                holder.des.setText(formatDes(model));
                FirebaseStorage.getInstance().getReference(model.getImage()).getDownloadUrl().addOnSuccessListener(uri -> {
                    try{
                        Glide.with(ConsFirstActivity.this).load(uri).into(holder.image);
                    }catch (Exception e){

                    }

                });
                holder.itemView.setOnClickListener(v -> startActivity(new Intent(ConsFirstActivity.this,DoctorProfileActivity.class).putExtra("userDataModel",model)));

            }

            @NonNull
            @Override
            public TopDrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new TopDrViewHolder(LayoutInflater.from(ConsFirstActivity.this).inflate(R.layout.top_dr_list_item,parent,false));
            }

        };
        binding.doctorListId.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.doctorListId.setAdapter(adapter);

    }

    private String formatDes(UserDataModel u) {
        String s=u.getType()+"\nMBBS from "+u.getMbbs()+"\n"+u.getDegrees();
        return  s;
    }

    @Override
    protected void onStart() {
        super.onStart();
        documentReference.addSnapshotListener((value, error) -> {
            if (value != null) {
                userDataModel=value.toObject(UserDataModel.class);

            }
        });
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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
            case R.id.ap:
                startActivity(new Intent(ConsFirstActivity.this,DrAppointmentListActivity.class).putExtra("isDoctor",userDataModel.isDoctor()));
                break;
            case R.id.chatMenu:
                startActivity(new Intent(ConsFirstActivity.this,ChatListActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToAskSpecialistsActivity(View view) {
        startActivity(new Intent(this, AskSpecialistsActivity.class).putExtra("userDataModel",userDataModel));
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
    private class TopDrViewHolder extends RecyclerView.ViewHolder{
        TextView name , des;
        ImageView image;
        public TopDrViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.doctorNameId);
            des=itemView.findViewById(R.id.doctorDesId);
            image=itemView.findViewById(R.id.topDrProfilePic);
        }
    }
}