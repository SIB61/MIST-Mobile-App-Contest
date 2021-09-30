package com.sib.healthcare.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.Donors> {
    Context c;
    List<Donor> list;
    LinearLayout snack;
    public DonorAdapter(Context c, List<Donor> list,LinearLayout snack){
        this.c=c;
        this.list=list;
        this.snack=snack;
    }
    @NonNull
    @Override
    public Donors onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.donor, parent, false);
        return new Donors(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Donors holder, @SuppressLint("RecyclerView") int i) {
        Toast.makeText(c,list.size()+" ",Toast.LENGTH_LONG).show();

        StorageReference storageReference= FirebaseStorage.getInstance().getReference(list.get(i).getUrl());
        //Glide.with(holder.itemView.getContext()).load(storageReference).into(imageView);
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            //  Toast.makeText(getApplicationContext(), url,Toast.LENGTH_LONG).show();
            Glide.with(c).load(uri).into(holder.profile_image);
        });
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse("tel:"+list.get(i).getPhone()));
                c.startActivity(in);
            }
        });
        holder.profile_name.setText(list.get(i).getName());
        holder.blood.setText("Blood Group: "+list.get(i).getBlood());
        holder.address.setText("Address: "+list.get(i).getDistrict()+", "+list.get(i).getDivision());
        holder.emer.setText(holder.emer.getText().toString()+" "+list.get(i).getPhone());
    }

    @Override
    public int getItemCount() {

        return list == null ? 0 : list.size();
    }

    public class Donors extends RecyclerView.ViewHolder {
        CircleImageView profile_image;
        TextView profile_name,address,emer,blood;
        Button chat;
        public Donors(@NonNull View v) {
            super(v);
            profile_image=v.findViewById(R.id.profile_image);
            profile_name=v.findViewById(R.id.profile_name);
            address=v.findViewById(R.id.address);
            emer=v.findViewById(R.id.emer);
            blood=v.findViewById(R.id.blood_EP);
            chat=v.findViewById(R.id.chat);

        }
    }
}
