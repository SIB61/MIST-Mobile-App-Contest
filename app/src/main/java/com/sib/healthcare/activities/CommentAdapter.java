package com.sib.healthcare.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Comment> {
    List<CommnetsData> list;
    Context c;
    public CommentAdapter(Context c, List<CommnetsData> list)
    {
        this.list=list;
        this.c=c;
    }

    @NonNull
    @Override
    public Comment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comments, parent, false);
        return new Comment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Comment holder, int i) {
       //Toast.makeText(c,list.size()+"",Toast.LENGTH_LONG).show();

        holder.profile_name.setText(list.get(i).getName());
        holder.date.setText(list.get(i).getTimed());
        if(list.get(i).getMention().equals("No")) {
            holder.mention.setText("");
        }
        else
        {
            holder.mention.setText(list.get(i).getMention());
        }
        holder.main.setText(list.get(i).getMain());
        StorageReference storageReference= FirebaseStorage.getInstance().getReference(list.get(i).getUrl());
        //Glide.with(holder.itemView.getContext()).load(storageReference).into(imageView);
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            //     Toast.makeText(getApplicationContext(), url,Toast.LENGTH_LONG).show();
            try {
                Glide.with(c).load(uri).into(holder.profile_image);
            }
            catch (Exception e){

            }

        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class Comment extends RecyclerView.ViewHolder {
        CircleImageView profile_image;
        TextView profile_name,date,main,mention;
        public Comment(@NonNull View v) {
            super(v);
            profile_image=v.findViewById(R.id.profile_image);
            profile_name=v.findViewById(R.id.profile_name);
            date=v.findViewById(R.id.timed);
            main=v.findViewById(R.id.main);
            mention=v.findViewById(R.id.mention);
        }
    }
}
