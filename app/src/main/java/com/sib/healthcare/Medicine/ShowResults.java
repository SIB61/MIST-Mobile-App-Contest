package com.sib.healthcare.Medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sib.healthcare.R;
import com.sib.healthcare.activities.MedAdapter;
import com.sib.healthcare.activities.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowResults extends AppCompatActivity {
String re1;
TextView re;
RecyclerView rere;
MedAdapter ad;
String[] ty=new String [5];
int re3=0;
    RecyclerView gr;
    String email,email1="",url,name;
    MedAdapter agr;
List<Med> list=new ArrayList<>();
List<Med> list1=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_results);
        ImageView back;
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        re=(TextView) findViewById(R.id.re);
        gr=(RecyclerView) findViewById(R.id.grid);
        rere=(RecyclerView) findViewById(R.id.rere);
        re1=getIntent().getStringExtra("Re");
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

        rere.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ad=new MedAdapter(ShowResults.this,list,"Sh");
        rere.setAdapter(ad);
        re.setText("Showing results for "+re1);
        FirebaseDatabase.getInstance().getReference("AllMedicines").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:snapshot.getChildren())
                {
                    Med de=s.getValue(Med.class);
                    re1=re1.toLowerCase();

                    String n=de.getMname().toLowerCase();
                //    Toast.makeText(getApplicationContext(),n+" "+re1,Toast.LENGTH_LONG).show();
                    if(n.contains(re1)||n.equals(re1)||re1.contains(n)) {
                        if(re3<5)
                        {
                            ty[re3]=de.getDisease();
                            re3++;
                        }
                        list.add(de);
                    }
                }
                ad.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });        gr.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        agr=new MedAdapter(ShowResults.this,list,"Us");
        gr.setAdapter(agr);
        for(int i=0;i<re3;i++)
        {

            FirebaseDatabase.getInstance().getReference("Medicines").child(ty[i]).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot s: snapshot.getChildren())
                    {
                        Med de=s.getValue(Med.class);
                        list1.add(de);
                    }
                    agr.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}