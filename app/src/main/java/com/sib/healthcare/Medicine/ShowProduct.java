package com.sib.healthcare.Medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.R;
import com.sib.healthcare.activities.MedAdapter;
import com.sib.healthcare.activities.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ShowProduct extends AppCompatActivity {
String Mname,Url,Name,Qua,Des,Ran,Dis,Price;
RecyclerView gr;
String email,email1="",url,name;
MedAdapter agr;
TextView bname,des,mname,pr,price,c,count;
    ImageView mpic;
    Button dec,inc,buy;
    RelativeLayout scart;
    String se;
    Button fav;
    LinearLayout ca;
    RelativeLayout ert,snack;
List<Med> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /// requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_product);
        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        email = hm.get(SessionManager.EMAIL);
        name = hm.get(SessionManager.FULLNAME);
        fav=(Button) findViewById(R.id.fav);
        email1 = "";
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@')
                break;
            email1 += email.charAt(i);
        }
        ImageView back;
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        snack=(RelativeLayout) findViewById(R.id.snack);
        url = hm.get(SessionManager.URL);
        Mname=getIntent().getStringExtra("Mname");
        Qua=getIntent().getStringExtra("Qua");
        Des=getIntent().getStringExtra("Des");
        Ran=getIntent().getStringExtra("Ran");
        Url=getIntent().getStringExtra("Url");
        Dis=getIntent().getStringExtra("Dis");
        Price=getIntent().getStringExtra("Price");
        Name=getIntent().getStringExtra("Name");
       gr=(RecyclerView)findViewById(R.id.grid);
       ca=(LinearLayout)findViewById(R.id.ca);
       mpic=(ImageView) findViewById(R.id.mpic);
       scart=(RelativeLayout) findViewById(R.id.scart);
       ert=(RelativeLayout) findViewById(R.id.ert);
       try {
           StorageReference storageReference = FirebaseStorage.getInstance().getReference(Url);
           //Glide.with(holder.itemView.getContext()).load(storageReference).into(imageView);
           storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
               //   Toast.makeText(c, url,Toast.LENGTH_LONG).show();
               try{
                   Glide.with(getApplicationContext()).load(uri).into(mpic);
               }catch (Exception e){

               }

           });
       }
       catch(Exception e)
       {
           Log.d("TAG",e.getMessage());
       }
       mname=(TextView) findViewById(R.id.mname);
       c=(TextView) findViewById(R.id.c);
       dec=(Button) findViewById(R.id.dec);
       inc=(Button) findViewById(R.id.inc);
       buy=(Button) findViewById(R.id.buy);
       bname=(TextView) findViewById(R.id.bname);
        count=(TextView) findViewById(R.id.count);
        bname.setText(Name);
        FirebaseDatabase.getInstance().getReference("Users").child(email1).child("CartCount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()) {
                    se = snapshot.child("count").getValue(String.class);
                    count.setText(se + "");
                    if(se.equals("0"))
                        ca.setVisibility(View.GONE);
                    else
                        c.setText(se+" Item    |");
                }
                else
                    ca.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!count.getText().toString().equals("0"))
                {
                    FirebaseDatabase.getInstance().getReference("Users").child(email1).child("CartAd").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChildren()){
                                String dis3=snapshot.child("dis").getValue(String.class);
                                String dis4=snapshot.child("div").getValue(String.class);
                                String dis5=snapshot.child("home").getValue(String.class);
                                String dis6=snapshot.child("phone").getValue(String.class);
                                startActivity(new Intent(getApplicationContext(), Proceed.class).putExtra("Location",dis5+" ,"+dis3+" ,"+dis4).putExtra("Phone",dis6));
                            }
                            else
                                startActivity(new Intent(getApplicationContext(),TakeAddress.class).putExtra("Work","No"));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Add Elements to Cart!!!",Toast.LENGTH_LONG).show();
                }
            }
        });

       dec.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int yu=Integer.parseInt(pr.getText().toString());
               yu--;
               pr.setText(yu+"");
           }
       });
        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int yu=Integer.parseInt(pr.getText().toString());
                yu++;
                pr.setText(yu+"");
            }
        });
        scart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!count.getText().toString().equals("0"))
                {
                    FirebaseDatabase.getInstance().getReference("Users").child(email1).child("CartAd").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChildren()){
                                String dis3=snapshot.child("dis").getValue(String.class);
                                String dis4=snapshot.child("div").getValue(String.class);
                                String dis5=snapshot.child("home").getValue(String.class);
                                String dis6=snapshot.child("phone").getValue(String.class);
                                startActivity(new Intent(getApplicationContext(), Proceed.class).putExtra("Location",dis5+" ,"+dis3+" ,"+dis4).putExtra("Phone",dis6));
                            }
                            else
                                startActivity(new Intent(getApplicationContext(),TakeAddress.class).putExtra("Work","No"));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Add Elements to Cart!!!",Toast.LENGTH_LONG).show();
                }
            }
        });
        FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Fav").child(Ran).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren())
                    fav.setText("Remove from Favorites");
                else
                    fav.setText("Add to Favorites");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fav.getText().toString().contains("Add"))
                {

                    HashMap map=new HashMap();
                    map.put("Name",Name);
                    map.put("Ran",Ran);
                    map.put("BNAME",bname.getText().toString());
                    FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Fav").child(Ran).setValue(map);
                    fav.setText("Remove From Favorites");
                    @SuppressLint("ResourceAsColor") Snackbar snackbar = Snackbar.make(snack, "Added to Favorites", Snackbar.LENGTH_SHORT)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Snackbar snackbar1 = Snackbar.make(snack, "Removed From Favorites", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            })
                            .setActionTextColor(R.color.Blood);

         /*   View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/

                    snackbar.show();

                }
                else
                {
                    // Toast.makeText(getApplicationContext(),"Removed",Toast.LENGTH_LONG).show();
                    @SuppressLint("ResourceAsColor") Snackbar snackbar = Snackbar.make(snack, "Removed From Favorites", Snackbar.LENGTH_SHORT)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Snackbar snackbar1 = Snackbar.make(snack, "Removed From Favorites", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            })
                            .setActionTextColor(R.color.Blood);
                    snackbar.show();
                    fav.setText("Add To Favorites");
                    FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Fav").child(Ran).removeValue();

                }
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ca.setVisibility(View.VISIBLE);


                long p=Integer.parseInt(count.getText().toString())+Integer.parseInt(pr.getText().toString());
                c.setText(p+" Item   |");

                HashMap mp=new HashMap();
                mp.put("count",p+"");

                count.setText(p+"");
                FirebaseDatabase.getInstance().getReference("Users").child(email1).child("CartCount").updateChildren(mp);
                Random rn=new Random();
                long op=rn.nextInt(10000000);


                FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Cart").child(Ran).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren())
                        {
                            String ui=snapshot.child("count").getValue().toString();
                            long p1=Integer.parseInt(ui);
                            p1+=Integer.parseInt(pr.getText().toString());
                            long total= p1*Integer.parseInt(Price);
                            CartData cd=new CartData(Mname,Price,p1+"",Name,Ran,total+"",Url);

                            FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Cart").child(Ran).setValue(cd);
                        }
                        else
                        {
                            long total=Integer.parseInt(pr.getText().toString())*Integer.parseInt(Price);
                            CartData cd=new CartData(Mname,Price,pr.getText().toString(),Name,Ran,total+"",Url);

                            FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Cart").child(Ran).setValue(cd);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
       count=(TextView) findViewById(R.id.count);
       scart=(RelativeLayout) findViewById(R.id.scart);
       mname.setText(Mname);
       bname=(TextView) findViewById(R.id.bname);
       bname.setText(Name);
       pr=(TextView) findViewById(R.id.pr);
       price=(TextView) findViewById(R.id.price);
       des=(TextView) findViewById(R.id.des);
       des.setText(Des);
       price.setText("The price of "+Qua+" is: "+Price+"Tk.");
        gr.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        agr=new MedAdapter(ShowProduct.this,list,"Us");
        gr.setAdapter(agr);


            FirebaseDatabase.getInstance().getReference("Medicines").child(Dis).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot s: snapshot.getChildren())
                    {
                        Med de=s.getValue(Med.class);
                        list.add(de);
                    }
                    agr.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }
}