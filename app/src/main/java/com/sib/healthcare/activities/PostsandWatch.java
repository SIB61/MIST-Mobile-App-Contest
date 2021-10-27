package com.sib.healthcare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.activities.profile.ProfileActivity;
import com.sib.healthcare.models.UserDataModel;
import com.sib.healthcare.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsandWatch extends AppCompatActivity {
TextView po;
RecyclerView posts;
CircleImageView profile_image;
String url;
List<PostingData> list=new ArrayList<>();
List<PostingData> list1=new ArrayList<>();
PostsAdapter post;
    String email,dis,div;
    Button yes, no;
    LinearLayout ask;
    BottomNavigationView bm;
    UserDataModel dsf;
    SwipeRefreshLayout refresh;
    private UserDataModel userDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_postsand_watch);
        po=(TextView)findViewById(R.id.po);
        posts=(RecyclerView)findViewById(R.id.posts);
        refresh=(SwipeRefreshLayout)findViewById(R.id.refresh);
        yes=(Button)findViewById(R.id.yes);
        no=(Button)findViewById(R.id.no);
        ask=(LinearLayout)findViewById(R.id.ask);
        final SessionManager sh=new SessionManager(this,SessionManager.USERSESSION);
        HashMap<String,String> hm=sh.returnData();
        url=hm.get(SessionManager.URL);
        FirebaseFirestore.getInstance().document("Users/" + FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(documentSnapshot -> userDataModel = documentSnapshot.toObject(UserDataModel.class));
     //   Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();
        String donor=hm.get(SessionManager.DONOR);
       //Toast.makeText(getApplicationContext(), donor+"Abid", Toast.LENGTH_LONG).show();
         dis=hm.get(SessionManager.DISTRICT);
         div=hm.get(SessionManager.DIVISION);
        bm = (BottomNavigationView) findViewById(R.id.bottomnav);
        bm.setOnItemSelectedListener((item -> {
            if(item.getItemId()==R.id.s){
                onBackPressed();
            }
            else if (item.getItemId() == R.id.donors) {
                startActivity(new Intent(PostsandWatch.this, ShowDonors.class));
            } else if (item.getItemId() == R.id.noti) {
                startActivity(new Intent(PostsandWatch.this, Notifications.class));
            }
            return true;
//                else if (item.getItemId() == R.id.profile) {
//                    startActivity(new Intent(PostsandWatch.this, ProfileActivity.class));
//                }
        }));

        StorageReference storageReference= FirebaseStorage.getInstance().getReference(url);
        //Glide.with(holder.itemView.getContext()).load(storageReference).into(imageView);
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            //  Toast.makeText(getApplicationContext(), url,Toast.LENGTH_LONG).show();
            Glide.with(this).load(uri).into(profile_image);
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(new Intent(getApplicationContext(), PostsandWatch.class));
                finish();
            }
        });
        if(donor==null||donor.equals("No"))
            ask.setVisibility(View.VISIBLE);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ask.setVisibility(View.GONE);
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(getApplicationContext(), EditProfileActivity.class).putExtra("userDataModel", userDataModel));
                startActivity(new Intent(getApplicationContext(), DonorReg.class));
            }
        });
        profile_image=(CircleImageView) findViewById(R.id.profile_image);

       po.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

                         startActivity(new Intent(getApplicationContext(),Posting.class).putExtra("Work","No"));
                     //    finish();
           }
       });


        posts.setLayoutManager(new LinearLayoutManager(this));
       post = new PostsAdapter(this, list);
        posts.setAdapter(post);
         email = hm.get(SessionManager.EMAIL);
        String email1 = "";
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@')
                break;
            email1 += email.charAt(i);
        }
        Calendar cal = Calendar.getInstance();
        int cday = cal.get(Calendar.DAY_OF_MONTH);
        int cmonth = cal.get(Calendar.MONTH);
        int cy = cal.get(Calendar.YEAR);
        String month = "",cm1="";
        if (cmonth == 1 - 1) {
            month = "January";
            cm1 = "December";
        }
        else if (cmonth == 2 - 1) {
            month = "February";
        cm1="January";
        }
        else if (cmonth == 3 - 1) {
            month = "March";
            cm1="February";
        }
        else if (cmonth == 4 - 1) {
            month = "April";
            cm1="March";
        }
        else if (cmonth == 5 - 1) {
            month = "May";
            cm1="April";
        }else if (cmonth == 6 - 1) {
            month = "June";
            cm1 = "May";
        }
        else if (cmonth == 7 - 1) {
            month = "July";
            cm1 = "June";
        }
        else if (cmonth == 8 - 1) {
            month = "August";
            cm1 = "July";
        }
        else if (cmonth == 9 - 1) {
            month = "September";
            cm1 = "August";
        }
        else if (cmonth == 10 - 1) {
            month = "October";
            cm1 = "September";
        }
        else if (cmonth == 11 - 1) {
            month = "November";
            cm1 = "October";
        }
        else {
            month = "December";
            cm1 = "November";
        }

        String finalMonth = month;
        String finalEmail = email1;
        String finalMonth1 = month;
        String finalCm = cm1;
        FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Posts").child(cm1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //  list.clear();
                for(DataSnapshot s:snapshot.getChildren())
                {
                    // Toast.makeText(getApplicationContext(), finalMonth,Toast.LENGTH_LONG).show();
                    PostingData pd=s.getValue(PostingData.class);
                    list.add(pd);
                }
                FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).child("Posts").child(finalMonth1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ///  list.clear();
                        for(DataSnapshot s:snapshot.getChildren())
                        {
                            // Toast.makeText(getApplicationContext(), finalMonth,Toast.LENGTH_LONG).show();
                            PostingData pd=s.getValue(PostingData.class);
                            list.add(pd);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                FirebaseDatabase.getInstance().getReference("Posts").child(finalMonth1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot s:snapshot.getChildren())
                        {
                            //      Toast.makeText(getApplicationContext(), "Abid",Toast.LENGTH_LONG).show();
                            PostingData pd=s.getValue(PostingData.class);
                            if(pd.getEmail().equals(email))
                                continue;
                            list.add(pd);
                        }

                        post.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                FirebaseDatabase.getInstance().getReference("Posts").child(finalCm).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot s:snapshot.getChildren())
                        {
                            //      Toast.makeText(getApplicationContext(), "Abid",Toast.LENGTH_LONG).show();
                            PostingData pd=s.getValue(PostingData.class);
                            if(pd.getEmail().equals(email))
                                continue;
                            list.add(pd);
                        }
                        Collections.sort(list, new Comparator<PostingData>() {
                            @Override
                            public int compare(PostingData t1, PostingData t2) {
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
                        post.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                post.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onBackPressed() {
     startActivity(new Intent(PostsandWatch.this,MainActivity.class));
        finish();

    }
}