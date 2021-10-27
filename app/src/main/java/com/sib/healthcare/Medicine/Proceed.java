package com.sib.healthcare.Medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.sib.healthcare.activities.SessionManager;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import de.hdodenhof.circleimageview.CircleImageView;

public class Proceed extends AppCompatActivity {
TextView total,order,deli;
RecyclerView scart;
List<CartData> list=new ArrayList<>();
ProceedAdapter pa;
LinearLayout snack;
TextView er;
String pro="";
CardView car;

int cy=0;
CircleImageView profile_image;
TextView profile_name,location,phone;
    TextView dh;
    String bname;
    Button change;
    String sEmail,sPass;
    String p1="";
    String pNames[]=new String[10000];
    int coi=0;
    int cday,cmonth;
    String month;
Button pay;
long to=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_proceed);
        total=(TextView) findViewById(R.id.total);
        location=(TextView) findViewById(R.id.location);
        car=(CardView) findViewById(R.id.car);
        ImageView back;
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        er=(TextView) findViewById(R.id.d);
        snack=(LinearLayout) findViewById(R.id.snack);
        pay=(Button) findViewById(R.id.pay);
        change=(Button) findViewById(R.id.change);
        dh=(TextView) findViewById(R.id.dh);
        profile_name=(TextView) findViewById(R.id.profile_name);
        phone=(TextView) findViewById(R.id.phone);
        profile_image=(CircleImageView) findViewById(R.id.profile_image);

        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        String email = hm.get(SessionManager.EMAIL);
        String email1="";
        for(int u=0;u<email.length();u++)
        {
            if(email.charAt(u)=='@')
                break;
            email1+=email.charAt(u);
        }
        String url = hm.get(SessionManager.URL);
        String nam = hm.get(SessionManager.FULLNAME);
        try {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(url);
            //Glide.with(holder.itemView.getContext()).load(storageReference).into(imageView);
            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                //   Toast.makeText(c, url,Toast.LENGTH_LONG).show();
                Glide.with(getApplicationContext()).load(uri).into(profile_image);
            });
        }
        catch(Exception e)
        {
            Log.d("TAG",e.getMessage());
        }
        profile_name.setText(nam);
        location.setText(getIntent().getStringExtra("Location"));
        phone.setText(getIntent().getStringExtra("Phone"));

        Calendar cal = Calendar.getInstance();
        cday = cal.get(Calendar.DAY_OF_MONTH);
         cmonth = cal.get(Calendar.MONTH);
         cy = cal.get(Calendar.YEAR);
         month = "";
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
        Random rn=new Random();
        long gh=rn.nextInt(10000000);

        deli=(TextView) findViewById(R.id.deli);
        order=(TextView) findViewById(R.id.order);
        scart=(RecyclerView) findViewById(R.id.scart);
        pa=new ProceedAdapter(Proceed.this,list);
        scart.setLayoutManager(new LinearLayoutManager(this));
        scart.setAdapter(pa);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),TakeAddress.class).putExtra("Work","Yes"));
            }
        });
        FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                to=0;

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        CartData cs = ds.getValue(CartData.class);
                        to += Integer.parseInt(cs.getTotal());
                        list.add(cs);
                        pro += "\n" + cs.getCount() + "x" + cs.getMname();
                    }
                    pa.notifyDataSetChanged();

                    String yt = location.getText().toString().toLowerCase();
                    total.setText(to + "Tk.");
                    if (yt.contains("sylhet")) {
                        deli.setText("0Tk.");
                        dh.setText("Delivery(Inside Sylhet)");
                    } else
                        to += 100;

                     if(to==0){
                         Toast.makeText(getApplicationContext(),"Nothing is added on cart!!",Toast.LENGTH_LONG).show();
                         pay.setText("Buy Products");
                         deli.setText("0Tk.");
                         car.setVisibility(View.GONE);
                         snack.setVisibility(View.GONE);
                         er.setVisibility(View.VISIBLE);
                     }
                    order.setText(to + "Tk.");



            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("sending").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot s) {


                sEmail=s.child("email").getValue().toString();
                sPass=s.child("password").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String finalEmail = email1;
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(to!=0) {
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                    FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).child("Cart").removeValue();
                    FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).child("CartCount").removeValue();
                    String time = sdf.format(cal.getTime());
                    Order od = new Order(pro, to + "", location.getText().toString(), phone.getText().toString(), cday + " " + month + " " + cy, time);
                    FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).child("Orders").child(cday + " " + month + " " + cy + " " + time).setValue(od);
                    String subject = "Medicine Order";
                    final String Email = sEmail;
                    final String pass = sPass;
                    Properties prop = new Properties();
                    prop.put("mail.smtp.host", "smtp.gmail.com");
                    prop.put("mail.smtp.port", "465");
                    prop.put("mail.smtp.auth", "true");
                    prop.put("mail.smtp.socketFactory.port", "465");
                    prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(Email, pass);
                        }
                    });
                    try {

                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(Email));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                        message.setSubject(subject);
                        message.setText("New order was successful. Total:" + to + "Tk. Delivery Location: " + location.getText().toString() + "\n\n For any query mail at hotelarshb7@gmail.com or you can call us at 01308376904 24 hours.");

                        new SendEmail().execute(message);

                    } catch (MessagingException e) {
                        e.printStackTrace();
                            /*StrictMode.ThreadPolicy po=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(po);*/
                    }
                    Toast.makeText(getApplicationContext(), "Order is successful!!", Toast.LENGTH_LONG).show();
                }
                startActivity(new Intent(getApplicationContext(),User_Section.class));
                finish();
            }
        });
    }
    private class SendEmail extends AsyncTask<Message, String, String> {

        @Override
        protected String doInBackground(Message... messages) {

            try {
                Transport.send(messages[0]);
                return "success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "error";
            }


        }
    }
}