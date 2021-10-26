package com.sib.healthcare.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.R;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotiAdapter extends RecyclerView.Adapter<NotiAdapter.Notifi> {
    Context c;
    List<NotiData> list;
    String comm;
    public NotiAdapter(Context c, List<NotiData>list){
        this.c=c;
        this.list=list;
    }

    @NonNull
    @Override
    public Notifi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.noti, parent, false);
        return new Notifi(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Notifi holder, @SuppressLint("RecyclerView") int i) {
        SessionManager sh = new SessionManager(c, SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        String email = hm.get(SessionManager.EMAIL);
        String donor = hm.get(SessionManager.DONOR);
        String email1="";
        for(int ik=0;ik<email.length();ik++)
        {
            if(email.charAt(ik)=='@') {
                break;
            }
                email1+=email.charAt(ik);


        }
      //
        // Toast.makeText(c, donor, Toast.LENGTH_LONG).show();
        FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Clicked").child(list.get(i).getDate()+" "+list.get(i).getTime()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              for(DataSnapshot s:snapshot.getChildren())
              {
                  String clicked=s.getValue().toString();
               //   Toast.makeText(c, clicked, Toast.LENGTH_LONG).show();
                  if(clicked.equals("No"))
                      holder.change.setBackgroundResource(R.color.grey);

              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        StorageReference storageReference= FirebaseStorage.getInstance().getReference(list.get(i).getUrl());
        //Glide.with(holder.itemView.getContext()).load(storageReference).into(imageView);
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            //     Toast.makeText(getApplicationContext(), url,Toast.LENGTH_LONG).show()
            try {
                Glide.with(c).load(uri).into(holder.profile_image);
            }
            catch (Exception e){

            }

        });

        holder.urgent.setText(list.get(i).getDivision());
        holder.date.setText(list.get(i).getTime()+" : "+list.get(i).getDate());
        String finalEmail = email1;
        holder.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap op = new HashMap();
                op.put("Clicked", "Yes");
                FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).child("Clicked").
                        child(list.get(i).getDate() + " " + list.get(i).getTime()).updateChildren(op);
                holder.change.setBackgroundResource(R.color.White);
                if (list.get(i).getDivision().contains("Urgent")) {
                    c.startActivity(new Intent(c, ShowPosts.class).putExtra("Name", list.get(i).getName1()).putExtra("Address", list.get(i).getLocation() + " " + list.get(i).getDistrict()
                    ).putExtra("Url", list.get(i).getUrl()).putExtra("Contact", list.get(i).getPhone()).putExtra("Patient", list.get(i).getPatientName()).
                            putExtra("Disease", list.get(i).getDisease()).putExtra("Email", list.get(i).getEmail())
                            .putExtra("Blood", list.get(i).getBlood()).putExtra("Time", list.get(i).getTime()).putExtra("Date", list.get(i).getDate()));

                } else {
                    c.startActivity(new Intent(c, Comment.class).putExtra("Date", list.get(i).getDate()).
                            putExtra("Gh", list.get(i).getGh()).putExtra("Email", list.get(i).getEmail()).
                            putExtra("Dis", list.get(i).getDistrict()).putExtra("Div", list.get(i).getDivision())
                            .putExtra("Blood", list.get(i).getBlood()).putExtra("Patient", list.get(i).getPatientName())
                            .putExtra("Pat", list.get(i).getPhone()).putExtra("Location", list.get(i).getLocation())
                            .putExtra("Url", list.get(i).getUrl()).putExtra("Disease", list.get(i).getDisease()).putExtra("Noti", list.get(i).getTime()));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class Notifi extends RecyclerView.ViewHolder {
        CircleImageView profile_image;
        TextView urgent,date;
        LinearLayout change;
        public Notifi(@NonNull View v) {
            super(v);
            profile_image=v.findViewById(R.id.profile_image);
            date=v.findViewById(R.id.date);
            urgent=v.findViewById(R.id.urgent);
            change=v.findViewById(R.id.change);
        }
    }
}
