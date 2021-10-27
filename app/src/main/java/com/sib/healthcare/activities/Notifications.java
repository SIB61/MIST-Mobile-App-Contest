package com.sib.healthcare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sib.healthcare.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Notifications extends AppCompatActivity {
    RecyclerView noti;
    NotiAdapter adapter;
    String email;
    ImageView back;
    List<NotiData> list = new ArrayList<>();
    List<NotiData> list1 = new ArrayList<>();
    MaterialToolbar toolbar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        toolbar=findViewById(R.id.NotiTool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        noti = (RecyclerView) findViewById(R.id.noti);
        //back = (ImageView) findViewById(R.id.back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        noti.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotiAdapter(this, list);
        noti.setAdapter(adapter);
        final SessionManager sh = new SessionManager(this, SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        email = hm.get(SessionManager.EMAIL);
        String email1 = "";
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@')
                break;
            email1 += email.charAt(i);
        }
        String finalEmail = email1;
        FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot s : snapshot.getChildren()) {
                    NotiData pd = s.getValue(NotiData.class);
                    list.add(pd);
                }
                Collections.sort(list, new Comparator<NotiData>() {
                    @Override
                    public int compare(NotiData t1, NotiData t2) {
                        String day1 = "",year="",monthY="";
                        String day2 = "",year2="",monthY2="";
                        for(int i=0;i<t1.getDate().length();i++)
                        {
                            if(Character.isWhitespace(t1.getDate().charAt(i)))
                                break;
                            day1+=t1.getDate().charAt(i);
                        }
                        for(int i=0;i<t2.getDate().length();i++)
                        {
                            if(Character.isWhitespace(t2.getDate().charAt(i)))
                                break;
                            day2+=t2.getDate().charAt(i);
                        }
                        for (int i = t1.getDate().length() - 6; i >= 0; i--) {
                            if (Character.isWhitespace(t1.getDate().charAt(i)))
                                break;
                            monthY += t1.getDate().charAt(i);
                        }
                        StringBuilder input1 = new StringBuilder();
                        input1.append(monthY);
                        monthY = String.valueOf(input1.reverse());
                        for (int i = t2.getDate().length() - 6; i >= 0; i--) {
                            if (Character.isWhitespace(t2.getDate().charAt(i)))
                                break;
                            monthY2 += t2.getDate().charAt(i);
                        }
                        StringBuilder input2 = new StringBuilder();
                        input2.append(monthY2);
                        monthY2= String.valueOf(input2.reverse());
                        for (int i = t1.getDate().length() - 1; i >= 0; i--) {
                            if (Character.isWhitespace(t1.getDate().charAt(i)))
                                break;
                            year += t1.getDate().charAt(i);
                        }
                        StringBuilder input3 = new StringBuilder();
                        input3.append(year);
                        year = String.valueOf(input3.reverse());
                        for (int i = t2.getDate().length()-1; i >= 0; i--) {
                            if (Character.isWhitespace(t2.getDate().charAt(i)))
                                break;
                            year2 += t2.getDate().charAt(i);
                        }
                        StringBuilder input4 = new StringBuilder();
                        input4.append(year2);
                        year2= String.valueOf(input4.reverse());
                        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");

                        int mon,mon1;
                        if(monthY.equals("January"))
                            mon=1;
                        else if(monthY.equals("February"))
                            mon=2;
                        else if (monthY.equals("March"))
                            mon=3;
                        else if(monthY.equals("April"))
                            mon=4;
                        else if(monthY.equals("May"))
                            mon=5;
                        else if(monthY.equals("June"))
                            mon=6;
                        else if(monthY.equals("July"))
                            mon=7;
                        else if(monthY.equals("August"))
                            mon=8;
                        else if(monthY.equals("September"))
                            mon=9;
                        else if(monthY.equals("October"))
                            mon=10;
                        else if(monthY.equals("November"))
                            mon=11;
                        else
                            mon=12;
                        if(monthY2.equals("January"))
                            mon1=1;
                        else if(monthY2.equals("February"))
                            mon1=2;
                        else if (monthY2.equals("March"))
                            mon1=3;
                        else if(monthY2.equals("April"))
                            mon1=4;
                        else if(monthY2.equals("May"))
                            mon1=5;
                        else if(monthY2.equals("June"))
                            mon1=6;
                        else if(monthY2.equals("July"))
                            mon1=7;
                        else if(monthY2.equals("August"))
                            mon1=8;
                        else if(monthY2.equals("September"))
                            mon1=9;
                        else if(monthY2.equals("October"))
                            mon1=10;
                        else if(monthY2.equals("November"))
                            mon1=11;
                        else
                            mon1=12;
                        String mi=mon+"";
                        String mi2=mon1+"";
                        Date d1 = null,d2 = null;
                        try {
                            d1=sdf3.parse(year+"-"+mi+"-"+day1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            d2=sdf3.parse(year2+"-"+mi2+"-"+day2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.d("TAg",d1+"+"+d2+"");
                        Log.d("TAg",monthY+"+"+monthY2+"");
                        return d2.compareTo(d1);
                    }
                });

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}