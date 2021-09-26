package com.sib.healthcare.Activities.Consultancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.sib.healthcare.DataModels.AppointmentModel;
import com.sib.healthcare.DataModels.QuestionModel;
import com.sib.healthcare.DataModels.SolutionModel;
import com.sib.healthcare.DataModels.UserDataModel;
import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivityAskSpecialistsBinding;

public class AskSpecialistsActivity extends AppCompatActivity {
private ActivityAskSpecialistsBinding binding;
private CollectionReference collectionReference;
private Query query1,query2;
private UserDataModel userDataModel;
private String filter;
private String[] filters={"All questions","My questions"};
private FirestoreRecyclerOptions<QuestionModel> options1,options2;
private FirestoreRecyclerAdapter<QuestionModel , QuestionViewHolder> adapter1,adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAskSpecialistsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarAS);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ask specialists");
        collectionReference= FirebaseFirestore.getInstance().collection("Questions");
        FirebaseFirestore.getInstance().document("Users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnSuccessListener(documentSnapshot -> userDataModel=documentSnapshot.toObject(UserDataModel.class));
        ArrayAdapter arrayAdapter= new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,filters);
        binding.filterAS.setAdapter(arrayAdapter);
        filter=binding.filterAS.getText().toString();
        String uId=FirebaseAuth.getInstance().getCurrentUser().getUid();
        query1 = collectionReference.whereEqualTo("uId", uId).orderBy("timestamp", Query.Direction.DESCENDING);
        query2 = collectionReference.orderBy("timestamp", Query.Direction.DESCENDING);
        options1 = new FirestoreRecyclerOptions.Builder<QuestionModel>()
                .setQuery(query1,QuestionModel.class).build();
        options2 = new FirestoreRecyclerOptions.Builder<QuestionModel>()
                .setQuery(query2,QuestionModel.class).build();
        adapter1 =new FirestoreRecyclerAdapter<QuestionModel, QuestionViewHolder>(options1) {
           @Override
           protected void onBindViewHolder(@NonNull QuestionViewHolder holder, int position, @NonNull QuestionModel model) {
               Log.d("TAG", "onBindViewHolder: "+model.toString());
               holder.question.setText(formatText(model));
               holder.solutions.setOnClickListener(v -> {
                   startActivity(new Intent(AskSpecialistsActivity.this,SolutionsActivity.class).putExtra("qId",model.getQuestionId()).putExtra("userDataModel",userDataModel));
               });
           }
           @NonNull
           @Override
           public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               return new QuestionViewHolder(LayoutInflater.from(AskSpecialistsActivity.this).inflate(R.layout.question_item,parent,false));
           }
       };
        adapter2 =new FirestoreRecyclerAdapter<QuestionModel, QuestionViewHolder>(options2) {
            @Override
            protected void onBindViewHolder(@NonNull QuestionViewHolder holder, int position, @NonNull QuestionModel model) {
                Log.d("TAG", "onBindViewHolder: "+model.toString());
                holder.question.setText(formatText(model));
                holder.solutions.setOnClickListener(v -> {
                    startActivity(new Intent(AskSpecialistsActivity.this,SolutionsActivity.class).putExtra("qId",model.getQuestionId()).putExtra("userDataModel",userDataModel));
                });
            }
            @NonNull
            @Override
            public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new QuestionViewHolder(LayoutInflater.from(AskSpecialistsActivity.this).inflate(R.layout.question_item,parent,false));
            }
        };
        binding.questionListAS.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.questionListAS.setAdapter(adapter2);
        adapter2.startListening();

        binding.filterAS.setOnItemClickListener((parent, view, position, id) -> {
            if(binding.filterAS.getText().toString().equals(filters[0])) {
                adapter1.stopListening();
                binding.questionListAS.setAdapter(adapter2);
                adapter2.startListening();
            }
            else {
                adapter2.stopListening();
                binding.questionListAS.setAdapter(adapter1);
                adapter1.startListening();
            }

        });

        binding.floatingActionButtonAS.setOnClickListener(v -> {
           startActivity(new Intent(this,QuestionActivity.class).putExtra("userDataModel",userDataModel));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter2.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter2.stopListening();
        adapter1.stopListening();
    }
    public String formatText(QuestionModel questionModel){
        String t;
        t="Name : " + questionModel.getName()+"\nAge : "+questionModel.getAge()
                +"\ngender : "+questionModel.getGender()+"\nheight : "+questionModel.getHeight()
                +"\nweight : "+questionModel.getWeight()+ "\n\nDescription :"+questionModel.getQuestion();
        return t;
    }

    private class QuestionViewHolder extends RecyclerView.ViewHolder {
        Button solutions;
        TextView question;
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            solutions=itemView.findViewById(R.id.solutions_AS);
            question=itemView.findViewById(R.id.problemText_AS);
        }
    }


}