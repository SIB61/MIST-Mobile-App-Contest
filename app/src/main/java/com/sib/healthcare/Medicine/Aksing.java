package com.sib.healthcare.Medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;
import com.sib.healthcare.R;
import com.sib.healthcare.activities.SessionManager;

import java.util.HashMap;
import java.util.Random;

public class Aksing extends AppCompatActivity {
Button normal,owner;
String email,name,div,dis,url,phone,donor,token,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aksing);
        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        SessionManager sh2 = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        email = hm.get(SessionManager.EMAIL);
        name = hm.get(SessionManager.FULLNAME);
        url = hm.get(SessionManager.URL);
        pass = hm.get(SessionManager.PASS);
        dis = hm.get(SessionManager.DISTRICT);
        div = hm.get(SessionManager.DIVISION);
        token = hm.get(SessionManager.TOKEN);
        donor = hm.get(SessionManager.DONOR);
        phone = hm.get(SessionManager.PHONE);
        normal=(Button) findViewById(R.id.normal);
        owner=(Button) findViewById(R.id.owner);


        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sh2.loginSession(name,email,phone,pass,url,donor,token,div,dis,"NormalUser","No");
                startActivity(new Intent(getApplicationContext(),User_Section.class));
            }
        });
        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sh2.loginSession(name,email,phone,pass,url,donor,token,div,dis,"Owner","No");
                String email1="";
                for(int i=0;i<email.length();i++)
                {
                    if(email.charAt(i)=='@')
                        break;
                    email1+=email.charAt(i);

                }
                Random rn=new Random();
                long op=rn.nextInt(1000000);
                HashMap uy=new HashMap();
                uy.put("Email",email);
                uy.put("Token",token);
                uy.put("ran",op+"");
                FirebaseDatabase.getInstance().getReference("MedicineOwners").child("Owners").child(email1).setValue(uy);
                //startActivity(new Intent(getApplicationContext(),User_Section.class));
                startActivity(new Intent(getApplicationContext(),AddMedicine.class).putExtra("Work","Asking"));
                finish();
            }
        });
    }
}