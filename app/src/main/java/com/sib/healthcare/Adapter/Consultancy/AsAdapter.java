package com.sib.healthcare.Adapter.Consultancy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sib.healthcare.Activities.Consultancy.SolutionsActivity;
import com.sib.healthcare.DataModels.AppointmentModel;
import com.sib.healthcare.R;

import java.util.List;

public class AsAdapter extends RecyclerView.Adapter {
    private Context ctx;
    private List<AppointmentModel> appointmentModels;
    public AsAdapter(Context ctx, List<AppointmentModel> appointmentModels) {
        this.appointmentModels=appointmentModels;
        this.ctx=ctx;
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
        button.setOnClickListener(v -> {
            ctx.startActivity(new Intent(ctx, SolutionsActivity.class));
        });
    }
    @Override
    public int getItemCount() {
        return appointmentModels.size();
    }

}
