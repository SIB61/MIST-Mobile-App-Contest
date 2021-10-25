package com.sib.healthcare.Medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.sib.healthcare.R;
import com.sib.healthcare.activities.SessionManager;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Edit_Medicine extends AppCompatActivity {
    Spinner type;
    private Uri resultUri;
    Spinner disease;
    EditText name1, mname, price;
    LinearLayout a;
    String t[], d[],work;
    Button add, submit,finish,cancel,choose;
    String email,div,dis,url,phone,donor,token,pass,email1="",URL;
    TextInputLayout na;
    int year,cmonth,day,day1,cmonth1,year1,cday,cy;
    String month="",time="";
    StorageReference st;
    EditText des,qua;
    String ran="",disease2;
    HashMap<String, String> hm;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_medicine);
        na = (TextInputLayout) findViewById(R.id.na);
        t = getResources().getStringArray(R.array.TypeM);
        d = getResources().getStringArray(R.array.What);
        a = (LinearLayout) findViewById(R.id.a);
        type = (Spinner) findViewById(R.id.type);
        name1 = (EditText) findViewById(R.id.name);
        des = (EditText) findViewById(R.id.des);
        qua = (EditText) findViewById(R.id.qua);

        mname = (EditText) findViewById(R.id.mname);
        price = (EditText) findViewById(R.id.price);
        submit = (Button) findViewById(R.id.submit);
        cancel = (Button) findViewById(R.id.finish);
        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        SessionManager sh2 = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        hm = sh.returnData();
        email = hm.get(SessionManager.EMAIL);
        url = hm.get(SessionManager.URL);
        pass = hm.get(SessionManager.PASS);
        dis = hm.get(SessionManager.DISTRICT);
        div = hm.get(SessionManager.DIVISION);
        token = hm.get(SessionManager.TOKEN);
        donor = hm.get(SessionManager.DONOR);
        phone = hm.get(SessionManager.PHONE);
        ArrayAdapter<String> ar = new ArrayAdapter<String>(this, R.layout.education, R.id.Edu, t);
        type.setAdapter(ar);

        for(int i=0;i<email.length();i++)
        {
            if(email.charAt(i)=='@')
                break;
            email1+=email.charAt(i);

        }

        ran=getIntent().getStringExtra("Ran");
        FirebaseDatabase.getInstance().getReference("AllMedicines").child(ran).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              String pr=snapshot.child("price").getValue(String.class);
              String mname2=snapshot.child("mname").getValue(String.class);
               disease2=snapshot.child("disease").getValue(String.class);
              String qua2=snapshot.child("qua").getValue(String.class);
              String des2=snapshot.child("des").getValue(String.class);
              String ty=snapshot.child("type").getValue(String.class);
              mname.setText(mname2);
              price.setText(pr);
              qua.setText(qua2);
              des.setText(des2);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String pr=price.getText().toString();
        String mname2=mname.getText().toString();
        String qua2=qua.getText().toString();
        String des2=des.getText().toString();
        String ty=type.getSelectedItem().toString();

        HashMap mop=new HashMap();

        mop.put("price",pr);
        mop.put("mname",mname2);
        mop.put("qua",qua2);
        mop.put("des",des2);
        mop.put("type",ty);
        FirebaseDatabase.getInstance().getReference("MedicineOwners").child(email1).child("Medicines").child(disease2).child(ran).updateChildren(mop);
        FirebaseDatabase.getInstance().getReference("Medicines").child(disease2).child(ran).updateChildren(mop);
        FirebaseDatabase.getInstance().getReference("AllMedicines").child(ran).updateChildren(mop);
        FirebaseDatabase.getInstance().getReference("All").child(email1).child(ran).updateChildren(mop);

        Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(),OwnerProfile.class));
        finish();
    }
});

cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(),OwnerProfile.class));
        finish();
    }
});

    }
}