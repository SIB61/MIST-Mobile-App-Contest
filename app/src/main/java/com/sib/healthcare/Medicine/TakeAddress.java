package com.sib.healthcare.Medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sib.healthcare.R;
import com.sib.healthcare.activities.SessionManager;

import java.util.HashMap;

public class TakeAddress extends AppCompatActivity {
    AutoCompleteTextView district;
    Spinner division;
    ArrayAdapter<String> adapter;
    String di[],div[];
    EditText phone,location;
    Button pay;
    String work;
    String email1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_address);
        district=(AutoCompleteTextView) findViewById(R.id.district);
        phone=(EditText) findViewById(R.id.phone);
        work=getIntent().getStringExtra("Work");
        pay=(Button) findViewById(R.id.pay);
        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        String email = hm.get(SessionManager.EMAIL);
        di=getResources().getStringArray(R.array.districts);
        div=getResources().getStringArray(R.array.divisions);
        ImageView back;
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        for(int u=0;u<email.length();u++)
        {
            if(email.charAt(u)=='@')
                break;
            email1+=email.charAt(u);
        }
        location=(EditText) findViewById(R.id.location);
        division=(Spinner)findViewById(R.id.division);
        district=(AutoCompleteTextView) findViewById(R.id.district);
        district.setThreshold(1);
        adapter = new ArrayAdapter<String>(TakeAddress.this,
                android.R.layout.simple_list_item_1, di);
        district.setAdapter(adapter);
        division=(Spinner)findViewById(R.id.division);
        div=getResources().getStringArray(R.array.divisions);
        district.setAdapter(adapter);
        ArrayAdapter<String> ar2=new ArrayAdapter<String>(this, R.layout.education,R.id.Edu,div);
        division.setAdapter(ar2);
        if(work!=null&&work.equals("Yes"))
        {
            FirebaseDatabase.getInstance().getReference("Users").child(email1).child("CartAd").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChildren()){
                        String dis3=snapshot.child("dis").getValue(String.class);
                        String dis4=snapshot.child("div").getValue(String.class);
                        String dis5=snapshot.child("home").getValue(String.class);
                        String dis6=snapshot.child("phone").getValue(String.class);
                       phone.setText(dis6);
                       district.setText(dis3);
                       location.setText(dis5);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
       pay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(phone.getText().toString().length()!=0&&location.getText().toString().length()!=0)
               {
                   HashMap mp=new HashMap();
                   mp.put("phone",phone.getText().toString());
                   mp.put("dis",district.getText().toString());
                   mp.put("div",division.getSelectedItem().toString());
                   mp.put("home",location.getText().toString());

                   FirebaseDatabase.getInstance().getReference("Users").child(email1).child("CartAd").setValue(mp);
                   startActivity(new Intent(getApplicationContext(),Proceed.class).putExtra("Location",location.getText().toString()+", "+division.getSelectedItem().toString()+", "+district.getText().toString()).putExtra("Phone",phone.getText().toString()));
               }
           }
       });

    }
}