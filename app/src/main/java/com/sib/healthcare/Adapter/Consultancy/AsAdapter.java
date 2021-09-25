package com.sib.healthcare.Adapter.Consultancy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sib.healthcare.Activities.Consultancy.SolutionsActivity;
import com.sib.healthcare.DataModels.QuestionModel;
import com.sib.healthcare.DataModels.UserDataModel;
import com.sib.healthcare.R;

import java.util.List;

public class AsAdapter extends RecyclerView.Adapter {
    private Context ctx;
    private List<QuestionModel> questionModels;
    private UserDataModel userDataModel;
    public AsAdapter(Context ctx, List<QuestionModel> questionModels, UserDataModel userDataModel) {
        this.questionModels=questionModels;
        this.ctx=ctx;
        this.userDataModel=userDataModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.ask_specialist_list_item,parent,false)) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView textView=holder.itemView.findViewById(R.id.problemText_AS);
        Button button= holder.itemView.findViewById(R.id.solutions_AS);
        textView.setText(formatText(questionModels.get(position)));
        button.setOnClickListener(v -> {
            ctx.startActivity(new Intent(ctx, SolutionsActivity.class).putExtra("userDataModel",userDataModel).putExtra("questionId",questionModels.get(position).getQuestionId()));
        });
    }
    @Override
    public int getItemCount() {
        return questionModels.size();
    }

    public String formatText(QuestionModel ap){
        String name= ap.getName();
        if(name==null)
        name="Anonymous";
        String s = "Name : " + name+"\nAge : "+ap.getAge()
                +"\ngender : "+ap.getGender()+"\nheight : "+ap.getHeight()
                +"\nweight : "+ap.getWeight()+ "\nDescription : "+ap.getQuestion();
        return s;

    }



}
