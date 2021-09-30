package com.sib.healthcare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowPosts extends AppCompatActivity {
String  name,blood,address,contact,url,patient,disease;
TextView profile_name,date,contact1,patient1,blood1;
CircleImageView profile_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_posts);
        name=getIntent().getStringExtra("Name");
        contact=getIntent().getStringExtra("Contact");
        blood=getIntent().getStringExtra("Blood");
        url=getIntent().getStringExtra("Url");
        address=getIntent().getStringExtra("Address");
        patient=getIntent().getStringExtra("Patient");
        disease=getIntent().getStringExtra("Disease");
        blood1=(TextView)findViewById(R.id.blood_EP);
        profile_name=(TextView)findViewById(R.id.profile_name);
        patient1=(TextView)findViewById(R.id.patient);
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

    }
}