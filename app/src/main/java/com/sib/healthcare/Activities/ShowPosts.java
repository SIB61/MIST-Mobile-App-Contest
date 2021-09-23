package com.sib.healthcare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

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
        blood1=(TextView)findViewById(R.id.blood);
        profile_name=(TextView)findViewById(R.id.profile_name);
        patient1=(TextView)findViewById(R.id.patient);
        contact1=(TextView)findViewById(R.id.contact);
        profile_image=(CircleImageView)findViewById(R.id.profile_image);
        patient1.setText("The name of the patient is "+patient+". The blood is needed for "+disease);
        blood1.setText(blood+" blood is needed at "+address+".");
        profile_name.setText(name);
        contact1.setText("Contact number: "+contact);
        Picasso.get().load(url).fit().centerCrop().into(profile_image);


    }
}