package com.sib.healthcare.Medicine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sib.healthcare.R;
import com.sib.healthcare.activities.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Favorite extends AppCompatActivity {
GridView grid;
GridAdapter gr;
String name,email,url,email1="";
LinearLayout sh1;
TextView no;
Button Show;

List<Med> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_favorite);
        grid=(GridView)findViewById(R.id.grid);
        sh1=(LinearLayout) findViewById(R.id.sh);
        Show=(Button) findViewById(R.id.Show);
        no=(TextView) findViewById(R.id.no);
        gr=new GridAdapter(this,list);
        grid.setAdapter(gr);
        ImageView back;
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        email = hm.get(SessionManager.EMAIL);
        name = hm.get(SessionManager.FULLNAME);
        email1 = "";
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@')
                break;
            email1 += email.charAt(i);
        }

        url = hm.get(SessionManager.URL);
        FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Fav").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren())
                {
                    for(DataSnapshot s:snapshot.getChildren())
                    {
                        String ran=s.child("Ran").getValue().toString();
                        FirebaseDatabase.getInstance().getReference("AllMedicines").child(ran).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Med ds=snapshot.getValue(Med.class);
                                list.add(ds);
                                gr.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
                else
                {
                       sh1.setVisibility(View.VISIBLE);
                       Show.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               startActivity(new Intent(getApplicationContext(),User_Section.class));
                           }
                       });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getApplicationContext(), ShowProduct.class).putExtra("Url", list.get(i).getURL()
                ).putExtra("Name", list.get(i).getName()).putExtra("Mname", list.get(i).getMname()).
                        putExtra("Qua", list.get(i).getQua()).putExtra("Des", list.get(i).getDes()).
                        putExtra("Dis", list.get(i).getDisease()).putExtra("Ran", list.get(i).getRan()).putExtra("Price", list.get(i).getPrice()));

            }
        });

    }
}