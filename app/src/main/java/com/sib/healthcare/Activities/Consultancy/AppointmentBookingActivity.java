package com.sib.healthcare.Activities.Consultancy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sib.healthcare.DataModels.AppointmentModel;
import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivityAppointmentBookingBinding;
import java.util.Calendar;

public class AppointmentBookingActivity extends AppCompatActivity {
private ActivityAppointmentBookingBinding binding;
private DatePickerDialog datePicker;
private AppointmentModel appointmentModel;
private String[] Genders={"male","female"};
private Calendar c;
private int y,m,d;
private String drName,drUid;
private String uid,name,age,gender,height,weight,description,date;
private CollectionReference dRef,pRef;
private String[] days={"sat","sun","mon","tue","wed","thu","fri"};
private DocumentReference drRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAppointmentBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        appointmentModel=new AppointmentModel();
        appointmentModel=getIntent().getParcelableExtra("appointmentModel");
        drUid=appointmentModel.getDrUid();
        uid=appointmentModel.getpUid();
        name=appointmentModel.getpName();
        binding.nameAB.setText(name);
        dRef= FirebaseFirestore.getInstance().collection("Appointments/Doctors/"+drUid);
        pRef=FirebaseFirestore.getInstance().collection("Appointments/Patients/"+uid);
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
      appointmentModel.setpName(binding.nameAB.getText().toString().trim());
      appointmentModel.setDescription(binding.descriptionAB.getText().toString().trim());
      age=binding.ageAB.getText().toString().trim();
      gender=binding.genderAB.getText().toString().trim();
      height=binding.heightAB.getText().toString().trim();
      weight=binding.weigntAB.getText().toString().trim();
      date=binding.dateAB.getText().toString().trim();
      if(height.isEmpty()||weight.isEmpty()||gender.isEmpty()||age.isEmpty()||date.isEmpty())
      {
          Toast.makeText(AppointmentBookingActivity.this,"required fields can't be empty",Toast.LENGTH_SHORT).show();
      }
      else
      {
          appointmentModel.setAge(age);
          appointmentModel.setGender(gender);
          appointmentModel.setHeight(height);
          appointmentModel.setWeight(weight);
          appointmentModel.setDate(date);
          pRef.add(appointmentModel).addOnSuccessListener(documentReference -> {
              dRef.add(appointmentModel).addOnSuccessListener(documentReference1 -> {
                  Toast.makeText(AppointmentBookingActivity.this,"Appointment booked successfully",Toast.LENGTH_SHORT).show();
                  onBackPressed();
              });
          });


      }

    }
}