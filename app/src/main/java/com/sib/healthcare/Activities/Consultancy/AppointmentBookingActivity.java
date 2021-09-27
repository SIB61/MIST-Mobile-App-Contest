package com.sib.healthcare.Activities.Consultancy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sib.healthcare.Activities.SessionManager;
import com.sib.healthcare.DataModels.AppointmentModel;
import com.sib.healthcare.DataModels.UserDataModel;
import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivityAppointmentBookingBinding;
import java.util.Calendar;

public class AppointmentBookingActivity extends AppCompatActivity {
private ActivityAppointmentBookingBinding binding;
private DatePickerDialog datePicker;
private AppointmentModel ap;
private String[] Genders={"male","female"};
private Calendar c;
private int y,m,d;
private String drName,drUid;
private String uid,name,age,gender,height,weight,description,date;
private CollectionReference dRef,pRef;
private String[] days={"sat","sun","mon","tue","wed","thu","fri"};
private DocumentReference drRef;
private UserDataModel dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAppointmentBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dr=getIntent().getParcelableExtra("userDataModel");
        uid= FirebaseAuth.getInstance().getUid();
        dRef= FirebaseFirestore.getInstance().collection("Users/"+drUid+"/Appointments");
        pRef=FirebaseFirestore.getInstance().collection("Users/"+uid+"/Appointments");
        drRef=FirebaseFirestore.getInstance().document("Doctors/"+drUid);

        ArrayAdapter adapter1=new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,Genders);
        binding.genderAB.setAdapter(adapter1);

        binding.dateLayoutAB.setEndIconOnClickListener( v -> {
            c=Calendar.getInstance();
            y=c.get(Calendar.YEAR);
            m=c.get(Calendar.MONTH);
            d=c.get(Calendar.DAY_OF_MONTH);
            datePicker=new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                binding.dateAB.setText(dayOfMonth+"-"+month+"-"+year);
            },y,m,d);
            datePicker.show();
        });

    }

    public void bookAppointment(View view) {
      name=binding.nameAB.getText().toString().trim();
      description=binding.descriptionAB.getText().toString().trim();
      age=binding.ageAB.getText().toString().trim();
      gender=binding.genderAB.getText().toString().trim();
      height=binding.heightAB.getText().toString().trim();
      weight=binding.weigntAB.getText().toString().trim();
      date=binding.dateAB.getText().toString().trim();
      if(name.isEmpty()||description.isEmpty()||height.isEmpty()||weight.isEmpty()||gender.isEmpty()||age.isEmpty()||date.isEmpty())
      {
          Toast.makeText(AppointmentBookingActivity.this,"required fields can't be empty",Toast.LENGTH_SHORT).show();
      }
      else
      {
          ap=new AppointmentModel(dr.getName(),dr.getuId(),"wd",dr.getClinicAddress(),uid,name,age,gender,height,weight,description,date,null,null);
          pRef.add(ap).addOnSuccessListener(documentReference -> {
             ap.setType("wp");
              dRef.add(ap).addOnSuccessListener(documentReference1 -> {
                  Toast.makeText(AppointmentBookingActivity.this,"Appointment booked successfully",Toast.LENGTH_SHORT).show();
                  drRef.update("appointments",dr.getAppointments()+1);
                  onBackPressed();
              });
          });
      }
    }
}