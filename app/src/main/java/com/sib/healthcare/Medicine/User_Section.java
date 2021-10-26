package com.sib.healthcare.Medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sib.healthcare.R;
import com.sib.healthcare.activities.MainActivity;
import com.sib.healthcare.activities.MedAdapter;
import com.sib.healthcare.activities.Notifications;
import com.sib.healthcare.activities.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User_Section extends AppCompatActivity {
    RecyclerView rco, rwo, rde, rho, rnu, rvi, rhe, rca, rdi, rpn, rme, rot, tip,RAll;
    TextView co, wo, de, ho, nu, vi, he, ca, dia, pn, me, ot, count, All;
    RelativeLayout scart;
    EditText se;
    SwipeRefreshLayout refresh;
    ImageView search, cart;
    Button add;
    List<Med> list = new ArrayList<>();
    List<Med> list1 = new ArrayList<>();
    List<Med> list2 = new ArrayList<>();
    List<Med> list3 = new ArrayList<>();
    List<Med> list4 = new ArrayList<>();
    List<Med> list5 = new ArrayList<>();
    List<Med> list6 = new ArrayList<>();
    List<Med> list7 = new ArrayList<>();
    List<Med> list8 = new ArrayList<>();
    List<Med> list9 = new ArrayList<>();
    List<Med> list10 = new ArrayList<>();
    List<Med> list11 = new ArrayList<>();
    List<Med> list12 = new ArrayList<>();
    List<Med> list13 = new ArrayList<>();
    List<Med> list14 = new ArrayList<>();
    List<Med> list15 = new ArrayList<>();
    LinearLayout snack;
    MedAdapter m;
    BottomNavigationView bm;
    String email, email1 = "",url,name;
    int img[] = {R.drawable.covid19, R.drawable.women, R.drawable.device, R.drawable.homeo, R.drawable.nut, R.drawable.vita, R.drawable.he
            , R.drawable.can, R.drawable.dia, R.drawable.pne, R.drawable.ment, R.drawable.ment, R.drawable.oth};

    TipAdapter ta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_section);
        bm = (BottomNavigationView) findViewById(R.id.bottomnav);
        bm.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.s){
                    startActivity(new Intent(User_Section.this, MainActivity.class));
                } else if (item.getItemId() == R.id.donors) {
                    startActivity(new Intent(User_Section.this, OrdersRe.class));

                }
                else if(item.getItemId()==R.id.noti)
                {
                    startActivity(new Intent(User_Section.this, medicine_main_activity.class));
                }
                else if(item.getItemId()==R.id.fav)
                {
                     startActivity(new Intent(User_Section.this, Favorite.class));

                }
            }
        });
        co = (TextView) findViewById(R.id.co);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        All = (TextView) findViewById(R.id.All);
        count = (TextView) findViewById(R.id.count);
        scart = (RelativeLayout) findViewById(R.id.scart);
        snack = (LinearLayout) findViewById(R.id.snack);
        se = (EditText) findViewById(R.id.se);
        search = (ImageView) findViewById(R.id.search);
        cart = (ImageView) findViewById(R.id.cart);
        add = (Button) findViewById(R.id.add);
        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        email = hm.get(SessionManager.EMAIL);
        name = hm.get(SessionManager.FULLNAME);
        url = hm.get(SessionManager.URL);
        email1 = "";
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(new Intent(getApplicationContext(),User_Section.class));
                finish();
            }
        });
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@')
                break;
            email1 += email.charAt(i);
        }
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
                    Toast.makeText(getApplicationContext(),"Add elements to cart first!!",Toast.LENGTH_LONG).show();
                }
            }
        });
        String na[] = getResources().getStringArray(R.array.What);
        wo = (TextView) findViewById(R.id.wo);
        de = (TextView) findViewById(R.id.de);
        ho = (TextView) findViewById(R.id.ho);
        nu = (TextView) findViewById(R.id.nu);
        ot = (TextView) findViewById(R.id.ot);
        pn = (TextView) findViewById(R.id.pn);
        he = (TextView) findViewById(R.id.he);
        ca = (TextView) findViewById(R.id.ca);
        vi = (TextView) findViewById(R.id.vi);
        me = (TextView) findViewById(R.id.me);
        dia = (TextView) findViewById(R.id.dia);
        rco = (RecyclerView) findViewById(R.id.rco);
        RAll = (RecyclerView) findViewById(R.id.RAll);
        tip = (RecyclerView) findViewById(R.id.tip);
        rhe = (RecyclerView) findViewById(R.id.rhe);
        rwo = (RecyclerView) findViewById(R.id.rwo);
        rde = (RecyclerView) findViewById(R.id.rde);
        rme = (RecyclerView) findViewById(R.id.rme);
        rho = (RecyclerView) findViewById(R.id.rho);
        rpn = (RecyclerView) findViewById(R.id.rpn);
        rnu = (RecyclerView) findViewById(R.id.rnu);
        rot = (RecyclerView) findViewById(R.id.rot);
        rca = (RecyclerView) findViewById(R.id.rca);
        rvi = (RecyclerView) findViewById(R.id.rvi);
        rdi = (RecyclerView) findViewById(R.id.rdi);
        ta = new TipAdapter(User_Section.this, img, na);
        tip.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        tip.setAdapter(ta);
        FirebaseDatabase.getInstance().getReference("Users").child(email1).child("CartCount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren())
                count.setText(snapshot.child("count").getValue(String.class));
                else
                    count.setText(0+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseDatabase.getInstance().getReference("Medicines").child(co.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list.add(me);
                    }
                    rco.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    m = new MedAdapter(User_Section.this, list,"Us");
                    rco.setAdapter(m);
                } else {
                    co.setVisibility(View.GONE);
                    rco.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Medicines").child(wo.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list1.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list1.add(me);
                    }
                    rwo.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    m = new MedAdapter(User_Section.this, list1,"Us");
                    rwo.setAdapter(m);
                } else {
                    rwo.setVisibility(View.GONE);
                    wo.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Medicines").child(de.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list2.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list2.add(me);
                    }
                    rde.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    m = new MedAdapter(User_Section.this, list2,"Us");
                    rde.setAdapter(m);
                } else {
                    rde.setVisibility(View.GONE);
                    de.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Medicines").child(ho.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list3.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list3.add(me);
                    }
                    rho.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    m = new MedAdapter(User_Section.this, list2,"Us");
                    rho.setAdapter(m);
                } else {
                    ho.setVisibility(View.GONE);
                    rho.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Medicines").child(nu.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list4.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list4.add(me);
                    }
                    rnu.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    m = new MedAdapter(User_Section.this, list4,"Us");
                    rnu.setAdapter(m);
                } else {
                    nu.setVisibility(View.GONE);
                    rnu.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Medicines").child(vi.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list5.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list5.add(me);
                    }
                    rvi.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    m = new MedAdapter(User_Section.this, list5,"Us");
                    rvi.setAdapter(m);
                } else {
                    vi.setVisibility(View.GONE);
                    rvi.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Medicines").child(he.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list6.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list6.add(me);
                    }
                    rhe.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    m = new MedAdapter(User_Section.this, list6,"Us");
                    rhe.setAdapter(m);
                } else {
                    he.setVisibility(View.GONE);
                    rhe.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Medicines").child(ca.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list7.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list7.add(me);
                    }
                    rca.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    m = new MedAdapter(User_Section.this, list7,"Us");
                    rca.setAdapter(m);
                } else {
                    ca.setVisibility(View.GONE);
                    rca.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Medicines").child(dia.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list8.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list8.add(me);
                    }
                    rdi.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    m = new MedAdapter(User_Section.this, list8,"Us");
                    rdi.setAdapter(m);
                } else {
                    dia.setVisibility(View.GONE);
                    rdi.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Medicines").child(pn.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list9.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list9.add(me);
                    }
                    rpn.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    m = new MedAdapter(User_Section.this, list9,"Us");
                    rpn.setAdapter(m);
                } else {
                    pn.setVisibility(View.GONE);
                    rpn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Medicines").child(me.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list10.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list10.add(me);
                    }
                    rme.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    m = new MedAdapter(User_Section.this, list10,"Us");
                    rme.setAdapter(m);
                } else {
                    me.setVisibility(View.GONE);
                    rme.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Medicines").child(ot.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list11.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list11.add(me);
                    }
                    rot.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    m = new MedAdapter(User_Section.this, list11,"Us");
                    rot.setAdapter(m);
                } else {
                    ot.setVisibility(View.GONE);
                    rot.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShowResults.class).putExtra("Re", se.getText().toString()));
            }
        });

        FirebaseDatabase.getInstance().getReference("AllMedicines").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list15.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list15.add(me);
                    }
                    RAll.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    m = new MedAdapter(User_Section.this, list15,"Us");
                    RAll.setAdapter(m);
                } else {
                    RAll.setVisibility(View.GONE);
                    All.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();

    }
}