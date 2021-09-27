package com.sib.healthcare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.models.UserDataModel;
import com.sib.healthcare.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsandWatch extends AppCompatActivity {
TextView po;
RecyclerView posts;
CircleImageView profile_image;
String url;
List<PostingData> list=new ArrayList<>();
PostsAdapter post;
    String email,dis,div;
    Button yes, no;
    LinearLayout ask;
    BottomNavigationView bm;
    UserDataModel dsf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_postsand_watch);
        po=(TextView)findViewById(R.id.po);
        posts=(RecyclerView)findViewById(R.id.posts);
        yes=(Button)findViewById(R.id.yes);
        no=(Button)findViewById(R.id.no);
        ask=(LinearLayout)findViewById(R.id.ask);
        final SessionManager sh=new SessionManager(this,SessionManager.USERSESSION);
        HashMap<String,String> hm=sh.returnData();
        url=hm.get(SessionManager.URL);
     //   Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();
        String donor=hm.get(SessionManager.DONOR);
       //Toast.makeText(getApplicationContext(), donor+"Abid", Toast.LENGTH_LONG).show();
         dis=hm.get(SessionManager.DISTRICT);
         div=hm.get(SessionManager.DIVISION);
        bm = (BottomNavigationView) findViewById(R.id.bottomnav);
        bm.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if (item.getItemId()== R.id.s) {
                    startActivity(new Intent(PostsandWatch.this, PostsandWatch.class));
                }  else if (item.getItemId() == R.id.donors) {
                    startActivity(new Intent(PostsandWatch.this, ShowDonors.class));

                }
                else if(item.getItemId()==R.id.noti)
                {
                    startActivity(new Intent(PostsandWatch.this, Notifications.class));
                }
                else if(item.getItemId()==R.id.profile)
                {
                  //  startActivity(new Intent(UserPrfofilew.this, WatchLater.class));

                }
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
                startActivity(new Intent(getApplicationContext(),DonorReg.class));
            }
        });
        profile_image=(CircleImageView) findViewById(R.id.profile_image);

       po.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

                         startActivity(new Intent(getApplicationContext(),Posting.class).putExtra("Work","No"));
                         finish();
           }
       });
        StorageReference storageReference= FirebaseStorage.getInstance().getReference(url);
        //Glide.with(holder.itemView.getContext()).load(storageReference).into(imageView);
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            Toast.makeText(getApplicationContext(), url,Toast.LENGTH_LONG).show();
            Glide.with(this).load(uri).into(profile_image);
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
            cm1 = "November";
        }
        else {
            month = "December";
            cm1 = "November";
        }
        String finalMonth = month;
        FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Posts").child(month).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              ///  list.clear();
                for(DataSnapshot s:snapshot.getChildren())
                {
                   // Toast.makeText(getApplicationContext(), finalMonth,Toast.LENGTH_LONG).show();
                    PostingData pd=s.getValue(PostingData.class);
                    list.add(pd);
                }

              post.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Posts").child(month).addValueEventListener(new ValueEventListener() {
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
        FirebaseDatabase.getInstance().getReference("Posts").child(cm1).addValueEventListener(new ValueEventListener() {
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
    }
}