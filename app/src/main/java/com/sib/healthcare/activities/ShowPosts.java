package com.sib.healthcare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowPosts extends AppCompatActivity {
String  name,blood,address,contact,url,patient,disease,time,date1;
TextView profile_name,date,contact1,patient1,blood1;
CircleImageView profile_image;
Button chat,call;
    PostingData pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_posts);
        name=getIntent().getStringExtra("Name");
        contact=getIntent().getStringExtra("Contact");
        time=getIntent().getStringExtra("Time");
        date1=getIntent().getStringExtra("Date");
        blood=getIntent().getStringExtra("Blood");
        url=getIntent().getStringExtra("Url");
        address=getIntent().getStringExtra("Address");
        patient=getIntent().getStringExtra("Patient");
        disease=getIntent().getStringExtra("Disease");
        blood1=(TextView)findViewById(R.id.blood_EP);
        profile_name=(TextView)findViewById(R.id.profile_name);
        date=(TextView)findViewById(R.id.date);
        patient1=(TextView)findViewById(R.id.patient);
        chat=(Button)findViewById(R.id.chat);
        call=(Button)findViewById(R.id.Call);
        date.setText(date1+" "+time);
        contact1=(TextView)findViewById(R.id.contact);
        profile_image=(CircleImageView)findViewById(R.id.profile_image);
        patient1.setText("The name of the patient is "+patient+". The blood is needed for "+disease);
        blood1.setText(blood+" blood is needed at "+address+".");
        profile_name.setText(name);
        contact1.setText("Contact number: "+contact);
        StorageReference storageReference= FirebaseStorage.getInstance().getReference(url);
        //Glide.with(holder.itemView.getContext()).load(storageReference).into(imageView);
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            //  Toast.makeText(getApplicationContext(), url,Toast.LENGTH_LONG).show();
            Glide.with(this).load(uri).into(profile_image);
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse("tel:"+contact));
                startActivity(in);
            }
        });
        String monthY="";
        for (int i = date1.length() - 6; i >= 0; i--) {
            if (Character.isWhitespace(date1.charAt(i)))
                break;
            monthY += date1.charAt(i);
        }
        StringBuilder input1 = new StringBuilder();
        input1.append(monthY);
        monthY = String.valueOf(input1.reverse());
        String finalMonthY = monthY;
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Posts").child(finalMonthY).child(date1+" "+time).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren())
                        {
                            pd=snapshot.getValue(PostingData.class);
                            startActivity(new Intent(getApplicationContext(),Comment.class).putExtra("Date",pd.getDate()).
                                    putExtra("Gh",pd.getTime()).putExtra("Email",pd.getEmail()).
                                    putExtra("Dis",pd.getDistrict()).putExtra("Div",pd.getDivision())
                                    .putExtra("Blood",pd.getBlood()).putExtra("Patient",pd.getPatientName())
                                    .putExtra("Pat",pd.getPhone()).putExtra("Location",pd.getLocation())
                                    .putExtra("Url",pd.getUrl())   .putExtra("Disease",pd.getDisease()));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}