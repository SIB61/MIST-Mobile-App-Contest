package com.sib.healthcare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sib.healthcare.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowDonors extends AppCompatActivity {
RecyclerView donor;
List<Donor> list=new ArrayList<>();
DonorAdapter post;
CardView A,B,AB,O,a,b,ab,o;
TextView type,no;
String email;
LinearLayout snack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_donors);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        donor=(RecyclerView)findViewById(R.id.donors);
        snack=(LinearLayout) findViewById(R.id.snack);
        final SessionManager sh=new SessionManager(this,SessionManager.USERSESSION);
        HashMap<String,String> hm=sh.returnData();
        email=hm.get(SessionManager.EMAIL);
        A=(CardView)findViewById(R.id.A);
        B=(CardView)findViewById(R.id.B);
        AB=(CardView)findViewById(R.id.AB);
        O=(CardView)findViewById(R.id.O);
        a=(CardView)findViewById(R.id.a);
        b=(CardView)findViewById(R.id.b);
        ab=(CardView)findViewById(R.id.ab);
        o=(CardView)findViewById(R.id.o);
        type=(TextView)findViewById(R.id.type);
        no=(TextView)findViewById(R.id.no);
        donor.setLayoutManager(new LinearLayoutManager(this));
        post = new DonorAdapter(ShowDonors.this, list,snack);
        donor.setAdapter(post);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   list.clear();
                post.notifyDataSetChanged();

                A.setBackgroundResource(R.color.White);
                AB.setBackgroundResource(R.color.White);
                O.setBackgroundResource(R.color.White);
                B.setBackgroundResource(R.color.White);
                b.setBackgroundResource(R.color.White);
                ab.setBackgroundResource(R.color.White);
                o.setBackgroundResource(R.color.White);
                type.setText("Showing results for A+ blood group:");
            a.setBackgroundResource(R.color.Blood);
                FirebaseDatabase.getInstance().getReference("Donor").child("A+").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        post.notifyDataSetChanged();
                        int k=0;
                        if(!snapshot.hasChildren())
                        {
                            Snackbar snackbar = Snackbar.make(snack, "Sorry!! No donor registered", Snackbar.LENGTH_SHORT)
                                    .setAction("UNDO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Snackbar snackbar1 = Snackbar.make(snack, "Undo successful", Snackbar.LENGTH_SHORT);
                                            snackbar1.show();
                                        }
                                    })
                                    .setActionTextColor(Color.RED);

         /*   View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/

                            snackbar.show();
                        }
                        for (DataSnapshot d: snapshot.getChildren())
                        {
                           String em=d.getValue().toString();
                            String em1=em+"@gmail.com";
                            if(em1.equals(email))
                                continue;
                            k++;
                            Toast.makeText(getApplicationContext(),k+" ",Toast.LENGTH_LONG).show();
                            FirebaseDatabase.getInstance().getReference("Donors").child(em).addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   Donor dn=snapshot.getValue(Donor.class);
                                 //  Toast.makeText(getApplicationContext(),dn.getBlood(),Toast.LENGTH_LONG).show();
                                   list.add(dn);
                                   post.notifyDataSetChanged();
                               }


                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {

                               }
                           });
                            //Toast.makeText(getApplicationContext(),k+"Se",Toast.LENGTH_LONG).show();

if(k==0)
{

  //  Toast.makeText(c,list.size()+" ",Toast.LENGTH_LONG).show();
    Snackbar snackbar = Snackbar.make(snack, "Sorry!! No donor registered", Snackbar.LENGTH_SHORT)
            .setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar snackbar1 = Snackbar.make(snack, "Undo successful", Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                }
            })
            .setActionTextColor(Color.RED);

         /*   View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/

    snackbar.show();
}
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                post.notifyDataSetChanged();
                A.setBackgroundResource(R.color.White);
                AB.setBackgroundResource(R.color.White);
                O.setBackgroundResource(R.color.White);
                a.setBackgroundResource(R.color.White);
                B.setBackgroundResource(R.color.White);
                ab.setBackgroundResource(R.color.White);
                o.setBackgroundResource(R.color.White);
                type.setText("Showing results for B+ blood group:");
                b.setBackgroundResource(R.color.Blood);
                FirebaseDatabase.getInstance().getReference("Donor").child("B+").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();

                        post.notifyDataSetChanged();
                        if(!snapshot.hasChildren())
                        {
                            Snackbar snackbar = Snackbar.make(snack, "Sorry!! No donor registered", Snackbar.LENGTH_SHORT)
                                    .setAction("UNDO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Snackbar snackbar1 = Snackbar.make(snack, "Undo successful", Snackbar.LENGTH_SHORT);
                                            snackbar1.show();
                                        }
                                    })
                                    .setActionTextColor(Color.RED);

         /*   View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/

                            snackbar.show();
                        }
                        int k=0;
                        for (DataSnapshot d: snapshot.getChildren())
                        {
                            int j[]= new int[1];
                            String em=d.getValue().toString();
                            String em1=em+"@gmail.com";
                            if(em1.equals(email))
                                continue;
                            k++;
                            FirebaseDatabase.getInstance().getReference("Donors").child(em).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Donor dn=snapshot.getValue(Donor.class);
                                    //  Toast.makeText(getApplicationContext(),dn.getBlood(),Toast.LENGTH_LONG).show();
                                    list.add(dn);
                                    j[0]=1;

                                    post.notifyDataSetChanged();
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }

                            });

                    }
                        if(k==0)
                        {
                            Snackbar snackbar = Snackbar.make(snack, "Sorry!! No donor registered", Snackbar.LENGTH_SHORT)
                                    .setAction("UNDO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Snackbar snackbar1 = Snackbar.make(snack, "Undo successful", Snackbar.LENGTH_SHORT);
                                            snackbar1.show();
                                        }
                                    })
                                    .setActionTextColor(Color.RED);

         /*   View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/

                            snackbar.show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                post.notifyDataSetChanged();
                A.setBackgroundResource(R.color.White);
                AB.setBackgroundResource(R.color.White);
                O.setBackgroundResource(R.color.White);
                a.setBackgroundResource(R.color.White);
                b.setBackgroundResource(R.color.White);
                B.setBackgroundResource(R.color.White);
                o.setBackgroundResource(R.color.White);
                type.setText("Showing results for AB+ blood group:");
                ab.setBackgroundResource(R.color.Blood);
                FirebaseDatabase.getInstance().getReference("Donor").child("AB+").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        post.notifyDataSetChanged();
                        int k=0;
                        if(!snapshot.hasChildren())
                        {
                            Snackbar snackbar = Snackbar.make(snack, "Sorry!! No donor registered", Snackbar.LENGTH_SHORT)
                                    .setAction("UNDO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Snackbar snackbar1 = Snackbar.make(snack, "Undo successful", Snackbar.LENGTH_SHORT);
                                            snackbar1.show();
                                        }
                                    })
                                    .setActionTextColor(Color.RED);

         /*   View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/

                            snackbar.show();
                        }
                        k++;
                        for (DataSnapshot d: snapshot.getChildren())
                        {
                            String em=d.getValue().toString();
                            String em1=em+"@gmail.com";
                            if(em1.equals(email))
                                continue;
                            FirebaseDatabase.getInstance().getReference("Donors").child(em).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Donor dn=snapshot.getValue(Donor.class);
                                    //  Toast.makeText(getApplicationContext(),dn.getBlood(),Toast.LENGTH_LONG).show();
                                    list.add(dn);
                                    post.notifyDataSetChanged();
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                post.notifyDataSetChanged();
                A.setBackgroundResource(R.color.White);
                AB.setBackgroundResource(R.color.White);
                O.setBackgroundResource(R.color.White);
                a.setBackgroundResource(R.color.White);
                b.setBackgroundResource(R.color.White);
                ab.setBackgroundResource(R.color.White);
                B.setBackgroundResource(R.color.White);
                type.setText("Showing results for O+ blood group:");
                o.setBackgroundResource(R.color.Blood);
                FirebaseDatabase.getInstance().getReference("Donor").child("O+").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        post.notifyDataSetChanged();
                        if(!snapshot.hasChildren())
                        {
                            Snackbar snackbar = Snackbar.make(snack, "Sorry!! No donor registered", Snackbar.LENGTH_SHORT)
                                    .setAction("UNDO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Snackbar snackbar1 = Snackbar.make(snack, "Undo successful", Snackbar.LENGTH_SHORT);
                                            snackbar1.show();
                                        }
                                    })
                                    .setActionTextColor(Color.RED);

         /*   View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/

                            snackbar.show();
                        }
                        int k=0;
                        for (DataSnapshot d: snapshot.getChildren())
                        {
                            String em=d.getValue().toString();
                            String em1=em+"@gmail.com";
                            if(em1.equals(email))
                                continue;
                            k++;
                            // Toast.makeText(getApplicationContext(),em,Toast.LENGTH_LONG).show();
                            FirebaseDatabase.getInstance().getReference("Donors").child(em).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Donor dn=snapshot.getValue(Donor.class);
                                    //  Toast.makeText(getApplicationContext(),dn.getBlood(),Toast.LENGTH_LONG).show();
                                    list.add(dn);
                                    post.notifyDataSetChanged();
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            if(k==0)
                            {
                                Snackbar snackbar = Snackbar.make(snack, "Sorry!! No donor registered", Snackbar.LENGTH_SHORT)
                                        .setAction("UNDO", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Snackbar snackbar1 = Snackbar.make(snack, "Undo successful", Snackbar.LENGTH_SHORT);
                                                snackbar1.show();
                                            }
                                        })
                                        .setActionTextColor(Color.RED);

         /*   View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/

                                snackbar.show();
                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                post.notifyDataSetChanged();
                B.setBackgroundResource(R.color.White);
                AB.setBackgroundResource(R.color.White);
                O.setBackgroundResource(R.color.White);
                a.setBackgroundResource(R.color.White);
                b.setBackgroundResource(R.color.White);
                ab.setBackgroundResource(R.color.White);
                o.setBackgroundResource(R.color.White);
                type.setText("Showing results for A- blood group:");
                A.setBackgroundResource(R.color.Blood);
                FirebaseDatabase.getInstance().getReference("Donor").child("A-").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        post.notifyDataSetChanged();
                        if(!snapshot.hasChildren())
                        {
                            Snackbar snackbar = Snackbar.make(snack, "Sorry!! No donor registered", Snackbar.LENGTH_SHORT)
                                    .setAction("UNDO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Snackbar snackbar1 = Snackbar.make(snack, "Undo successful", Snackbar.LENGTH_SHORT);
                                            snackbar1.show();
                                        }
                                    })
                                    .setActionTextColor(Color.RED);

         /*   View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/

                            snackbar.show();
                        }
                        int k=0;
                        for (DataSnapshot d: snapshot.getChildren())
                        {
                            String em=d.getValue().toString();
                            String em1=em+"@gmail.com";
                            if(em1.equals(email))
                                continue;
                            k++;
                            // Toast.makeText(getApplicationContext(),em,Toast.LENGTH_LONG).show();
                            FirebaseDatabase.getInstance().getReference("Donors").child(em).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Donor dn=snapshot.getValue(Donor.class);
                                    //  Toast.makeText(getApplicationContext(),dn.getBlood(),Toast.LENGTH_LONG).show();
                                    list.add(dn);
                                    post.notifyDataSetChanged();
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            if(k==0)
                            {
                                Snackbar snackbar = Snackbar.make(snack, "Sorry!! No donor registered", Snackbar.LENGTH_SHORT)
                                        .setAction("UNDO", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Snackbar snackbar1 = Snackbar.make(snack, "Undo successful", Snackbar.LENGTH_SHORT);
                                                snackbar1.show();
                                            }
                                        })
                                        .setActionTextColor(Color.RED);

         /*   View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/

                                snackbar.show();
                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                post.notifyDataSetChanged();
                A.setBackgroundResource(R.color.White);
                AB.setBackgroundResource(R.color.White);
                O.setBackgroundResource(R.color.White);
                a.setBackgroundResource(R.color.White);
                b.setBackgroundResource(R.color.White);
                ab.setBackgroundResource(R.color.White);
                o.setBackgroundResource(R.color.White);
                type.setText("Showing results for B- blood group:");
                B.setBackgroundResource(R.color.Blood);
                FirebaseDatabase.getInstance().getReference("Donor").child("B-").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        post.notifyDataSetChanged();   if(!snapshot.hasChildren())
                        {
                            Snackbar snackbar = Snackbar.make(snack, "Sorry!! No donor registered", Snackbar.LENGTH_SHORT)
                                    .setAction("UNDO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Snackbar snackbar1 = Snackbar.make(snack, "Undo successful", Snackbar.LENGTH_SHORT);
                                            snackbar1.show();
                                        }
                                    })
                                    .setActionTextColor(Color.RED);

         /*   View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/

                            snackbar.show();
                        }
                        int k=0;

                        for (DataSnapshot d: snapshot.getChildren())
                        {
                            String em=d.getValue().toString();
                            String em1=em+"@gmail.com";
                            if(em1.equals(email))
                                continue;
                            k++;
                            // Toast.makeText(getApplicationContext(),em,Toast.LENGTH_LONG).show();
                            FirebaseDatabase.getInstance().getReference("Donors").child(em).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Donor dn=snapshot.getValue(Donor.class);
                                    //  Toast.makeText(getApplicationContext(),dn.getBlood(),Toast.LENGTH_LONG).show();
                                    list.add(dn);
                                    post.notifyDataSetChanged();
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            if(k==0)
                            {
                                Snackbar snackbar = Snackbar.make(snack, "Sorry!! No donor registered", Snackbar.LENGTH_SHORT)
                                        .setAction("UNDO", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Snackbar snackbar1 = Snackbar.make(snack, "Undo successful", Snackbar.LENGTH_SHORT);
                                                snackbar1.show();
                                            }
                                        })
                                        .setActionTextColor(Color.RED);

         /*   View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/

                                snackbar.show();
                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        AB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                post.notifyDataSetChanged();
                A.setBackgroundResource(R.color.White);
                B.setBackgroundResource(R.color.White);
                O.setBackgroundResource(R.color.White);
                a.setBackgroundResource(R.color.White);
                b.setBackgroundResource(R.color.White);
                ab.setBackgroundResource(R.color.White);
                o.setBackgroundResource(R.color.White);

                type.setText("Showing results for AB- blood group:");
                AB.setBackgroundResource(R.color.Blood);
                FirebaseDatabase.getInstance().getReference("Donor").child("AB-").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        post.notifyDataSetChanged();
                        if(!snapshot.hasChildren())
                        {
                            Snackbar snackbar = Snackbar.make(snack, "Sorry!! No donor registered", Snackbar.LENGTH_SHORT)
                                    .setAction("UNDO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Snackbar snackbar1 = Snackbar.make(snack, "Undo successful", Snackbar.LENGTH_SHORT);
                                            snackbar1.show();
                                        }
                                    })
                                    .setActionTextColor(Color.RED);

         /*   View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/

                            snackbar.show();
                        }
                        int k=0;
                        for (DataSnapshot d: snapshot.getChildren())
                        {
                            String em=d.getValue().toString();
                            String em1=em+"@gmail.com";
                            if(em1.equals(email))
                                continue;
                            k++;
                            // Toast.makeText(getApplicationContext(),em,Toast.LENGTH_LONG).show();
                            FirebaseDatabase.getInstance().getReference("Donors").child(em).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Donor dn=snapshot.getValue(Donor.class);
                                    //  Toast.makeText(getApplicationContext(),dn.getBlood(),Toast.LENGTH_LONG).show();
                                    list.add(dn);
                                    post.notifyDataSetChanged();
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            if(k==0)
                            {
                                Snackbar snackbar = Snackbar.make(snack, "Sorry!! No donor registered", Snackbar.LENGTH_SHORT)
                                        .setAction("UNDO", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Snackbar snackbar1 = Snackbar.make(snack, "Undo successful", Snackbar.LENGTH_SHORT);
                                                snackbar1.show();
                                            }
                                        })
                                        .setActionTextColor(Color.RED);

         /*   View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/

                                snackbar.show();
                            }

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        O.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                post.notifyDataSetChanged();


                A.setBackgroundResource(R.color.White);
                AB.setBackgroundResource(R.color.White);
                B.setBackgroundResource(R.color.White);
                a.setBackgroundResource(R.color.White);
                b.setBackgroundResource(R.color.White);
                ab.setBackgroundResource(R.color.White);
                o.setBackgroundResource(R.color.White);
                type.setText("Showing results for O- blood group:");
                O.setBackgroundResource(R.color.Blood);
                FirebaseDatabase.getInstance().getReference("Donor").child("O-").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        post.notifyDataSetChanged();
                        int k=0;
                        if(!snapshot.hasChildren())
                        {
                            Snackbar snackbar = Snackbar.make(snack, "Sorry!! No donor registered", Snackbar.LENGTH_SHORT)
                                    .setAction("UNDO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Snackbar snackbar1 = Snackbar.make(snack, "Undo successful", Snackbar.LENGTH_SHORT);
                                            snackbar1.show();
                                        }
                                    })
                                    .setActionTextColor(Color.RED);

         /*   View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/

                            snackbar.show();
                        }
                        for (DataSnapshot d: snapshot.getChildren())
                        {
                            String em=d.getValue().toString();
                            String em1=em+"@gmail.com";
                            if(em1.equals(email))
                                continue;
                            k++;
                            // Toast.makeText(getApplicationContext(),em,Toast.LENGTH_LONG).show();
                            FirebaseDatabase.getInstance().getReference("Donors").child(em).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Donor dn=snapshot.getValue(Donor.class);
                                    //  Toast.makeText(getApplicationContext(),dn.getBlood(),Toast.LENGTH_LONG).show();

                                    list.add(dn);
                                    post.notifyDataSetChanged();
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            if(k==0)
                            {
                                Snackbar snackbar = Snackbar.make(snack, "Sorry!! No donor registered", Snackbar.LENGTH_SHORT)
                                        .setAction("UNDO", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Snackbar snackbar1 = Snackbar.make(snack, "Undo successful", Snackbar.LENGTH_SHORT);
                                                snackbar1.show();
                                            }
                                        })
                                        .setActionTextColor(Color.RED);

         /*   View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/

                                snackbar.show();
                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        FirebaseDatabase.getInstance().getReference("Donors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                post.notifyDataSetChanged();
                if(!snapshot.hasChildren())
                {
                    Snackbar snackbar = Snackbar.make(snack, "Sorry!! No donor registered", Snackbar.LENGTH_SHORT)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Snackbar snackbar1 = Snackbar.make(snack, "Undo successful", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            })
                            .setActionTextColor(Color.RED);

         /*   View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);*/

                    snackbar.show();
                }
                int k=0;
                for(DataSnapshot d:snapshot.getChildren())
                {
                    Donor dn=d.getValue(Donor.class);
                    if(dn.getEmail().equals(email))
                        continue;
                    list.add(dn);
                //    Toast.makeText(getApplicationContext(),dn.getName(),Toast.LENGTH_LONG).show();
                }
              post.notifyDataSetChanged();
            //    Toast.makeText(getApplicationContext(),list.size()+"",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}