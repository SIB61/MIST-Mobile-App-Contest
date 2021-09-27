package com.sib.healthcare.activities.consultancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.models.SolutionModel;
import com.sib.healthcare.models.UserDataModel;
import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivitySolutionsBinding;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class SolutionsActivity extends AppCompatActivity {
private ActivitySolutionsBinding binding;
private CollectionReference collectionReference;
private UserDataModel userDataModel ;
private String qId;
private FirestoreRecyclerOptions<SolutionModel> options;
private Query query;
private FirestoreRecyclerAdapter<SolutionModel, SolutionHolder> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySolutionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarASol);
        getSupportActionBar().setTitle("Solutions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       /* FirebaseFirestore.getInstance().document("Users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnSuccessListener(documentSnapshot -> {
                    userDataModel=documentSnapshot.toObject(UserDataModel.class);

                });*/
        userDataModel=getIntent().getParcelableExtra("userDataModel");
        if(!userDataModel.isDoctor())
        {
            binding.editTextAsol.setVisibility(View.GONE);
            binding.sendAsol.setVisibility(View.GONE);
        }
        qId=getIntent().getStringExtra("qId");
        collectionReference=FirebaseFirestore.getInstance().collection("Questions/"+qId+"/Solutions");
        query=collectionReference
                .orderBy("timestamp", Query.Direction.DESCENDING);
        options = new FirestoreRecyclerOptions.Builder<SolutionModel>()
                .setQuery(query,SolutionModel.class).build();
        query.get().addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                Log.d("TAG", "onCreate: "+task.getResult().getDocuments().toString());
            }
        });
        adapter=new FirestoreRecyclerAdapter<SolutionModel, SolutionHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SolutionHolder holder, int position, @NonNull SolutionModel model) {
                Log.d("TAG", "onBindViewHolder: "+model.toString());
                     holder.solution.setText(model.getSolution());
                     holder.name.setText(model.getName());
                     @SuppressLint("SimpleDateFormat") String time = new SimpleDateFormat( "d MMM yyyy, h:mm a" ).format ( model.getTimestamp().toDate() );
                     holder.time.setText(time);
                     holder.type.setText(model.getType());
                     StorageReference storageReference= FirebaseStorage.getInstance().getReference(model.getProfile());
                     storageReference.getDownloadUrl().addOnSuccessListener( uri -> {
                         Picasso.get().load(uri).into(holder.image);
                     });
            }
            @NonNull
            @Override
            public SolutionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new SolutionHolder(LayoutInflater.from(SolutionsActivity.this).inflate(R.layout.solution_item,parent,false));
            }
        };
        binding.solutionListASol.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.solutionListASol.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }



    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void sendSolution(View view) {
        String s= Objects.requireNonNull(binding.editTextAsol.getText()).toString().trim();
        if(!s.isEmpty())
        {
            SolutionModel p = new SolutionModel(userDataModel.getuId(),userDataModel.getName(),userDataModel.getImage(),s,Timestamp.now());
            p.setType(userDataModel.getType());
            collectionReference.add(p).addOnFailureListener(e -> Toast.makeText(SolutionsActivity.this,"something went wrong",Toast.LENGTH_SHORT).show())
            .addOnSuccessListener(documentReference -> binding.editTextAsol.setText(""));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private class SolutionHolder extends RecyclerView.ViewHolder{
        private TextView solution,name,time,type;
        private CircleImageView image;
        public SolutionHolder(@NonNull View itemView) {
            super(itemView);
            solution=itemView.findViewById(R.id.text_SI);
            name=itemView.findViewById(R.id.name_SI);
            image=itemView.findViewById(R.id.pic_SI);
            time=itemView.findViewById(R.id.dateText_SI);
            type=itemView.findViewById(R.id.type_SI);
        }
    }

}