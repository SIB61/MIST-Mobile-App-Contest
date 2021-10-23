package com.sib.healthcare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sib.healthcare.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Comment extends AppCompatActivity {
RecyclerView comment;
String need="";
AutoCompleteTextView write;
String names[]=new String[10000];
String token[]=new String[10000];
String ems[]=new String[10000];
CommentAdapter ca;
String em123="";
ImageView send;
List<CommnetsData> list=new ArrayList<>();
String gh,date,alemail,email,name,url,email1,dis,div,email2,blood,phone,patient,location,Url,disease;
int co=0;
ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        comment=(RecyclerView)findViewById(R.id.comments);
        write=(AutoCompleteTextView) findViewById(R.id.write);
        date=getIntent().getStringExtra("Date");
        gh=getIntent().getStringExtra("Gh");
        dis=getIntent().getStringExtra("Dis");
        div=getIntent().getStringExtra("Div");
        alemail=getIntent().getStringExtra("Email");
        patient=getIntent().getStringExtra("Patient");
        phone=getIntent().getStringExtra("Pat");
        blood=getIntent().getStringExtra("Blood");
        location=getIntent().getStringExtra("Location");
        Url=getIntent().getStringExtra("Url");
        disease=getIntent().getStringExtra("Disease");
        send=(ImageView) findViewById(R.id.send);
   //Toast.makeText(getApplicationContext(),date+" "+gh,Toast.LENGTH_LONG).show();

        FirebaseDatabase.getInstance().getReference("Comments").child(date+" "+gh).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot4) {
                  list.clear();
                if (snapshot4.hasChildren()) {
               //     Toast.makeText(getApplicationContext(),date+" "+gh,Toast.LENGTH_LONG).show();
                    for (DataSnapshot s : snapshot4.getChildren()) {
                        CommnetsData de = s.getValue(CommnetsData.class);
                    //    Toast.makeText(getApplicationContext(), de.getTimed(), Toast.LENGTH_LONG).show();
                        list.add(de);
                    }
                    ca = new CommentAdapter(Comment.this, list);
                    comment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    comment.setAdapter(ca);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Names").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:snapshot.getChildren())
                {
                    names[co]=s.child("name").getValue().toString();
                    token[co]=s.child("token").getValue().toString();
                    ems[co]=s.child("email").getValue().toString();
            //        Toast.makeText(getApplicationContext(),write.getText().toString(),Toast.LENGTH_LONG).show();
                    co++;
                }
                String[] na=new String[co];
                for(int i=0;i<co;i++)
                    na[i]=names[i];

                adapter = new ArrayAdapter<String>(Comment.this,
                        android.R.layout.simple_list_item_1, na);
                write.setAdapter(adapter);
                write.requestFocus();
                write.setThreshold(1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        email = hm.get(SessionManager.EMAIL);
      name = hm.get(SessionManager.FULLNAME);
         url = hm.get(SessionManager.URL);
         email1 = "";
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@')
                break;
            email1 += email.charAt(i);
        }
        email2="";
        for (int i = 0; i < alemail.length(); i++) {
            if (alemail.charAt(i) == '@')
                break;
            email2 += alemail.charAt(i);
        }

if(write.getText().toString().length()>0)
{
    send.setVisibility(View.VISIBLE);
}

send.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        int d = 0;


        String mention = "",t="";
     //   Toast.makeText(getApplicationContext(), write.getText().toString(), Toast.LENGTH_LONG).show();
       for(int i=0;i<co;i++)
        {
            if(write.getText().toString().contains(names[i]))
            {
               d++;
               mention=names[i];
               t=token[i];
               em123=ems[i];

               break;
            }
        }
       String em1234="";
        for (int i = 0; i < em123.length(); i++) {
            if (em123.charAt(i) == '@')
                break;
            em1234 += em123.charAt(i);
        }
        if (d == 0)
            mention = "No";
        Calendar cal = Calendar.getInstance();
        int cday = cal.get(Calendar.DAY_OF_MONTH);
        int cmonth = cal.get(Calendar.MONTH);
        int cy = cal.get(Calendar.YEAR);
        String month = "";
        if (cmonth == 1 - 1)
            month = "January";
        else if (cmonth == 2 - 1)
            month = "February";
        else if (cmonth == 3 - 1)
            month = "March";
        else if (cmonth == 4 - 1)
            month = "April";
        else if (cmonth == 5 - 1)
            month = "May";
        else if (cmonth == 6 - 1)
            month = "June";
        else if (cmonth == 7 - 1)
            month = "July";
        else if (cmonth == 8 - 1)
            month = "August";
        else if (cmonth == 9 - 1)
            month = "September";
        else if (cmonth == 10 - 1)
            month = "October";
        else if (cmonth == 11 - 1)
            month = "November";
        else month = "December";
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        String time = sdf.format(cal.getTime());
        final String[] se = {""};
        String finalMonth = month;
        String finalMain = mention;
        String finalT = t;
        String finalMention = mention;
        String finalEm123 = em1234;
        FirebaseDatabase.getInstance().getReference("Users").child(email2).child("Tokens").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    need = s.getValue().toString();
                }
                for (int i = date.length() - 6; i >= 0; i--) {
                    if (Character.isWhitespace(date.charAt(i)))
                        break;
                    se[0] += date.charAt(i);
                }
                StringBuilder input1 = new StringBuilder();
                Random ru=new Random();
                long ryu=ru.nextInt(10000000);
                // append a string into StringBuilder input1
                input1.append(se[0]);
              // Toast.makeText(getApplicationContext(),gh,Toast.LENGTH_LONG).show();
                // reverse StringBuilder input1
                se[0] = String.valueOf(input1.reverse());
                String main=write.getText().toString();
                String m="";
                if(!finalMain.equals("No"))
                   m= main.replace(finalMain,"");
                else
                    m=main;

                CommnetsData cd = new CommnetsData(name, email,m , finalMain, url, cday + " " + finalMonth + " " + cy + " " + time);
                HashMap op = new HashMap();
                op.put("Clicked", "No");
                FirebaseDatabase.getInstance().getReference("Comments").
                        child(cday + " " + finalMonth + " " + cy + " " + gh).child(cday + " " + finalMonth + " " + cy + " " + time).setValue(cd);
                NotiData nd = new NotiData(blood, patient,disease ,
                        location, phone, cday + " " + finalMonth + " " + cy, url, name, email, dis, name + " commented on your post", time, name, gh + "");



                FirebaseDatabase.getInstance().getReference("Users").child(email2).child("Clicked").child(cday + " " + finalMonth + " " + cy + " " + time).setValue(op);
                FirebaseDatabase.getInstance().getReference("Users").child(email2).child("Notifications").
                        child(cday + " " + finalMonth + " " + cy + " " + time).setValue(nd);
                  //Toast.makeText(getApplicationContext(),need+"",Toast.LENGTH_LONG).show();
                FcmNotificationsSender fcm = new FcmNotificationsSender(need, "Comments", name + " commented on your post", getApplicationContext(), Comment.this);
                // Toast.makeText(getApplicationContext(), dn.getToken(), Toast.LENGTH_LONG).show();
                fcm.SendNotifications();
                Log.d("TAG",finalT);
                if(finalT!=null) {
                    HashMap op1 = new HashMap();
                    op.put("Clicked", "No");
                    FirebaseDatabase.getInstance().getReference("Users").child(finalEm123).child("Clicked").child(cday + " " + finalMonth + " " + cy + " " + time).setValue(op1);

                    NotiData nd1 = new NotiData(blood, patient, disease,
                            location, phone, cday + " " + finalMonth + " " + cy, url, name, finalEm123, dis, name + " mentioned you in a comment", time, name, gh + "");
                    FirebaseDatabase.getInstance().getReference("Users").child(finalEm123).child("Notifications").
                            child(cday + " " + finalMonth + " " + cy + " " + time).setValue(nd1);

                    FcmNotificationsSender fcm1 = new FcmNotificationsSender(finalT, "Comments", name + " mentioned you in a comment", getApplicationContext(), Comment.this);
                     Toast.makeText(getApplicationContext(), finalEm123, Toast.LENGTH_LONG).show();
                    fcm1.SendNotifications();
                }
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                write.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
        });
}

    }