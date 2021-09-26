package com.sib.healthcare.Activities.Consultancy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sib.healthcare.DataModels.QuestionModel;
import com.sib.healthcare.DataModels.UserDataModel;
import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivityQuestionBinding;

import java.util.Calendar;
import java.util.Objects;

public class QuestionActivity extends AppCompatActivity {
private ActivityQuestionBinding binding;
private CollectionReference collectionReference;
private UserDataModel userDataModel;
private String[] gender={"male","female"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarAQ);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        collectionReference= FirebaseFirestore.getInstance().collection("Questions");
        userDataModel=getIntent().getParcelableExtra("userDataModel");
        ArrayAdapter adapter=new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,gender);
        binding.genderAQ.setAdapter(adapter);
        binding.nameAQ.setText(userDataModel.getName());
    }

    public void post(View view) {

        String name = binding.nameAQ.getText().toString().trim();
        String age = binding.ageAQ.getText().toString().trim();
        String gender = binding.genderAQ.getText().toString().trim();
        String height = binding.heightAQ.getText().toString().trim();
        String weight = binding.heightAQ.getText().toString().trim();
        String description = binding.descriptionAQ.getText().toString().trim();


        if(age.isEmpty()||gender.isEmpty()||height.isEmpty()||weight.isEmpty()||description.isEmpty())
        {
            Toast.makeText(this,"required fields can't be empty",Toast.LENGTH_SHORT).show();
        }
        else {

            String uId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            QuestionModel model=new QuestionModel(uId,null,"anonymous",age,gender,height,weight,description,Timestamp.now());
            if(!name.isEmpty())
                model.setName(name);
            collectionReference.add(model).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    String questionId=task.getResult().getId();
                    collectionReference.document(questionId).update("questionId",questionId).addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful())
                        {
                            Toast.makeText(this, "posted successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
                    });
                }
                else {
                    Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}