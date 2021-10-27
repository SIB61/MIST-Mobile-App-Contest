package com.sib.healthcare.Adapter.Consultancy;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.sib.healthcare.activities.consultancy.DoctorProfileActivity;
import com.sib.healthcare.models.UserDataModel;
import com.sib.healthcare.R;

import java.util.ArrayList;
import java.util.List;

public class DrListAdapter extends RecyclerView.Adapter {

    private List<UserDataModel> drList=new ArrayList<>() ;

    public DrListAdapter(List<UserDataModel> drList) {
        this.drList = drList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dr_list_item,parent,false)) {};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageView drImage=holder.itemView.findViewById(R.id.image);
        TextView drName=holder.itemView.findViewById(R.id.name);
        TextView specialist=holder.itemView.findViewById(R.id.specialist);
        drName.setText(drList.get(position).getName());
        specialist.setText(drList.get(position).getType());
        FirebaseStorage.getInstance().getReference(drList.get(position).getImage())
                .getDownloadUrl().addOnSuccessListener( uri -> {

                    try{
                        Glide.with(holder.itemView.getContext()).load(uri).into(drImage);
                    }
                    catch (Exception e){

                    }

        });

        holder.itemView.setOnClickListener(v -> {
            holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), DoctorProfileActivity.class).putExtra("userDataModel",drList.get(position)));
        });


    }

    @Override
    public int getItemCount() {
        return drList.size();
    }
}
