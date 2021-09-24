package com.sib.healthcare.Activities.Consultancy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.DocumentViewChange;
import com.sib.healthcare.Adapter.Consultancy.AppointmentListAdapter;
import com.sib.healthcare.DataModels.AppointmentModel;
import com.sib.healthcare.databinding.ActivityDrAppointmentListBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class DrAppointmentListActivity extends AppCompatActivity {

    private ActivityDrAppointmentListBinding binding;
    private String time;
    private List<AppointmentModel> appointmentModels;
    private AppointmentModel appointmentModel;
    private CollectionReference collectionReference;
    private AppointmentListAdapter adapter;
    private FirebaseUser user;
    private Query query;
    int y,m,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDrAppointmentListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.materialToolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user=FirebaseAuth.getInstance().getCurrentUser();
        getSupportActionBar().setTitle("Appointment List");
        collectionReference = FirebaseFirestore.getInstance().collection("Appointments/Doctors/"+user.getUid());
        appointmentModels=new ArrayList<>();
        binding.listAL.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter=new AppointmentListAdapter(this,appointmentModels);
        binding.listAL.setAdapter(adapter);
        Calendar c= Calendar.getInstance();
        y=c.get(Calendar.YEAR);
        m=c.get(Calendar.MONTH);
        d=c.get(Calendar.DAY_OF_MONTH);
        binding.calendarView.setDate(Calendar.getInstance().getTimeInMillis(),false,true);
        binding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            time=dayOfMonth+"-"+month+"-"+year;
            Log.d("TAG", "onCreate: "+time);
            loadList(time);
        });

    }

    private void loadList(String date) {
        appointmentModels=new ArrayList<>();
        Query query=collectionReference.whereEqualTo("date",date);
        query.addSnapshotListener((value, error) -> {
            if (value != null) {
                for (DocumentChange dc: value.getDocumentChanges())
                {
                    //if(dc.getType()==ADDED)
                    appointmentModels.add(dc.getDocument().toObject(AppointmentModel.class));
                    Log.d("TAG", "loadList: "+dc.getDocument().toObject(AppointmentModel.class).toString());
                }
                adapter=new AppointmentListAdapter(this,appointmentModels);
                binding.listAL.setAdapter(adapter);
            }
        });
    }

}