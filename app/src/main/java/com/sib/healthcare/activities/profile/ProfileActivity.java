package com.sib.healthcare.activities.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.R;
import com.sib.healthcare.activities.LoginScreenActivity;
import com.sib.healthcare.activities.Posting;
import com.sib.healthcare.activities.PostingData;
import com.sib.healthcare.activities.PostsAdapter;
import com.sib.healthcare.activities.PostsandWatch;
import com.sib.healthcare.activities.SessionManager;
import com.sib.healthcare.activities.consultancy.DrAppointmentListActivity;
import com.sib.healthcare.activities.consultancy.EditProfileActivity;
import com.sib.healthcare.models.UserDataModel;

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

public class ProfileActivity extends AppCompatActivity {
  Button edt,app,post1;
  RecyclerView posts;
  CircleImageView profile_image,profile_imag;
  TextView pName,po;
    List<PostingData> list=new ArrayList<>();
    List<PostingData> list1=new ArrayList<>();
    PostsAdapter post;
    private UserDataModel userDataModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
        pName=(TextView)findViewById(R.id.name) ;
        po=(TextView)findViewById(R.id.po) ;
        profile_image=(CircleImageView) findViewById(R.id.profile_image) ;
        profile_imag=(CircleImageView) findViewById(R.id.profile_imag) ;
        edt=(Button) findViewById(R.id.edt) ;
        app=(Button) findViewById(R.id.app) ;
        post1=(Button) findViewById(R.id.post) ;
        FirebaseFirestore.getInstance().document("Users/" + FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(documentSnapshot -> userDataModel = documentSnapshot.toObject(UserDataModel.class));

        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        String email = hm.get(SessionManager.EMAIL);
        String name = hm.get(SessionManager.FULLNAME);
        String url = hm.get(SessionManager.URL);
        String email1="";
        for(int u=0;u<email.length();u++)
        {
            if(email.charAt(u)=='@')
                break;
            email1+=email.charAt(u);
        }
        pName.setText(name);

        StorageReference storageReference= FirebaseStorage.getInstance().getReference(url);
        //Glide.with(holder.itemView.getContext()).load(storageReference).into(imageView);
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            //   Toast.makeText(c, url,Toast.LENGTH_LONG).show();
            Glide.with(getApplicationContext()).load(uri).into(profile_image);
        });
        StorageReference storageReference2= FirebaseStorage.getInstance().getReference(url);
        //Glide.with(holder.itemView.getContext()).load(storageReference).into(imageView);
        storageReference2.getDownloadUrl().addOnSuccessListener(uri -> {
            //   Toast.makeText(c, url,Toast.LENGTH_LONG).show();
            Glide.with(getApplicationContext()).load(uri).into(profile_imag);
        });
        posts=(RecyclerView)findViewById(R.id.posts);

        posts.setLayoutManager(new LinearLayoutManager(this));
        post = new PostsAdapter(this, list);
        posts.setAdapter(post);
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
        String finalEmail1 = email1;
        FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Posts").child(cm1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //  list.clear();
                for (DataSnapshot s : snapshot.getChildren()) {
                    // Toast.makeText(getApplicationContext(), finalMonth,Toast.LENGTH_LONG).show();
                    PostingData pd = s.getValue(PostingData.class);
                    list.add(pd);
                }
                FirebaseDatabase.getInstance().getReference("Users").child(finalEmail1).child("Posts").child(finalMonth1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ///  list.clear();
                        for (DataSnapshot s : snapshot.getChildren()) {
                            // Toast.makeText(getApplicationContext(), finalMonth,Toast.LENGTH_LONG).show();
                            PostingData pd = s.getValue(PostingData.class);
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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
            });
         post1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(getApplicationContext(), PostsandWatch.class));
             }
         });
         edt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(getApplicationContext(), EditProfileActivity.class).putExtra("userDataModel", userDataModel));
             }
         });
        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DrAppointmentListActivity.class).putExtra("isDoctor",userDataModel.isDoctor()));
            }
        });
        po.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Posting.class).putExtra("Work","No"));
                finish();
            }
        });
        }

     public void logout(View view) {
          AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this);

            builder = new AlertDialog.Builder(this);

            builder.setMessage("Are you sure you want to log out??")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(ProfileActivity.this, LoginScreenActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Log Out?");
            alert.show();
    }
}
