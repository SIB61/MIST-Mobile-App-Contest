package com.sib.healthcare.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.Posts> {
    Context c;
    List<PostingData> list;
    public PostsAdapter(Context c, List<PostingData> pd) {
        this.c=c;
    this.list=pd;
    }

    @NonNull
    @Override
    public Posts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.posts, parent, false);
        return new Posts(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull Posts holder, int i) {
        SessionManager sh = new SessionManager(c, SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        String email = hm.get(SessionManager.EMAIL);
        String email1="";
        if(email.equals(list.get(i).getEmail()))
            holder.bottom.setVisibility(View.VISIBLE);
        for(int u=0;u<email.length();u++)
        {
            if(email.charAt(u)=='@')
                break;
        email1+=email.charAt(u);
        }
        String date=list.get(i).getDate();
        String se="";
        for (int d = date.length() - 6; d >= 0; d--) {
            if (Character.isWhitespace(date.charAt(d)))
                break;
            se += date.charAt(d);
        }
        StringBuilder input1 = new StringBuilder();
        Random ru=new Random();
        long ryu=ru.nextInt(10000000);
        // append a string into StringBuilder input1
        input1.append(se);
      //  Toast.makeText(c,list.get(i).getUrl(),Toast.LENGTH_LONG).show();
        // reverse StringBuilder input1
        se = String.valueOf(input1.reverse());
        String finalSe = se;
        String finalSe1 = se;
        String finalEmail = email1;
        holder.bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bsd=new BottomSheetDialog(c, R.style.BottomSheetDialogTheme);
                View bs = null;
                bs=LayoutInflater.from(c).inflate(R.layout.bottomsheet, null);
                bs.findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(c,finalSe1,Toast.LENGTH_LONG).show();
                       c.startActivity(new Intent(c,Posting.class).putExtra("Date",list.get(i).getDate()+" "+list.get(i).getGh()).
                                putExtra("Work","Edit").putExtra("Month", finalSe));
                    }
                });
                bs.findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase.getInstance().getReference("PostsDi").child(list.get(i).getDistrict()).child(finalSe1).child(list.get(i).getDate()+" "+list.get(i).getGh()).removeValue();
                        FirebaseDatabase.getInstance().getReference("PostsDi").child(list.get(i).getDivision()).child(finalSe1).child(list.get(i).getDate()+" "+list.get(i).getGh()).removeValue();
                        FirebaseDatabase.getInstance().getReference("Posts").child(finalEmail).child(finalSe1).child(list.get(i).getDate()+" "+list.get(i).getGh()).removeValue();

                   Toast.makeText(c,list.get(i).getDistrict(),Toast.LENGTH_LONG).show();
                        FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).child("Posts").child(finalSe1).
                                child(list.get(i).getDate()+" "+list.get(i).getGh()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(c,"Post deleted successflly!!",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                bsd.setContentView(bs);
                bsd.show();
            }
        });
        StorageReference storageReference= FirebaseStorage.getInstance().getReference(list.get(i).getUrl());
        //Glide.with(holder.itemView.getContext()).load(storageReference).into(imageView);
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
       //     Toast.makeText(getApplicationContext(), url,Toast.LENGTH_LONG).show();
            Glide.with(c).load(uri).into(holder.profile_image);
        });
        holder.profile_name.setText(list.get(i).getName());
        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.startActivity(new Intent(c,Comment.class).putExtra("Date",list.get(i).getDate()).
                        putExtra("Gh",list.get(i).getGh()).putExtra("Email",list.get(i).getEmail()).
                        putExtra("Dis",list.get(i).getDistrict()).putExtra("Div",list.get(i).getDivision())
                    .putExtra("Blood",list.get(i).getBlood()).putExtra("Patient",list.get(i).getPatientName())
                        .putExtra("Pat",list.get(i).getPhone()).putExtra("Location",list.get(i).getLocation())
                   .putExtra("Url",list.get(i).getUrl())   .putExtra("Disease",list.get(i).getDisease()));
            }
        });
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse("tel:"+list.get(i).getPhone()));
                c.startActivity(in);
            }
        });
        holder.date.setText(list.get(i).getDate());
        holder.blood.setText(list.get(i).getBlood()+" blood is needed at "+list.get(i).getLocation()+", "+list.get(i).getDistrict()+", "+list.get(i).getDivision());
        holder.patient.setText("Patient name is "+list.get(i).getPatientName()+". The blood is needed for "+list.get(i).getDisease());
        holder.contact.setText("Contact Number: "+list.get(i).getPhone());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class Posts extends RecyclerView.ViewHolder {
TextView profile_name,date,blood,contact,patient;
CircleImageView profile_image;
Button submit,call;
ImageView bottom;
        public Posts(@NonNull View i) {
            super(i);
            profile_name=i.findViewById(R.id.profile_name);
            date=i.findViewById(R.id.date);
            blood=i.findViewById(R.id.blood);
            contact=i.findViewById(R.id.contact);
            patient=i.findViewById(R.id.patient);
            profile_image=i.findViewById(R.id.profile_image);
            submit=i.findViewById(R.id.chat);
            call=i.findViewById(R.id.Call);
            bottom=i.findViewById(R.id.bottom);

        }
    }
}
