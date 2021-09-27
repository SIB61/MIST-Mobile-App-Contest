package com.sib.healthcare.activities.consultancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.sib.healthcare.Adapter.Consultancy.AppointmentListAdapter;
import com.sib.healthcare.models.AppointmentModel;
import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivityDrAppointmentListBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DrAppointmentListActivity extends AppCompatActivity {

    private ActivityDrAppointmentListBinding binding;
    private List<AppointmentModel> appointmentModels;
    private AppointmentModel appointmentModel;
    private CollectionReference collectionReference;
    private AppointmentListAdapter adapter;
    private FirebaseUser user;
    private String date;
    private String type;
    private FirestoreRecyclerOptions<AppointmentModel> options;
   // private FirestoreRecyclerAdapter<AppointmentModel,AppointmentListViewHolder> adapter;
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
        collectionReference = FirebaseFirestore.getInstance().collection("Users/"+user.getUid()+"/Appointments");
        appointmentModels=new ArrayList<>();
        binding.listAL.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter=new AppointmentListAdapter(this,appointmentModels);
        if(!getIntent().getBooleanExtra("isDoctor", false))
        {
            binding.linearLayout3.setVisibility(View.GONE);
        }
        binding.listAL.setAdapter(adapter);
        Calendar c= Calendar.getInstance();
        y=c.get(Calendar.YEAR);
        m=c.get(Calendar.MONTH);
        d=c.get(Calendar.DAY_OF_MONTH);
        date=d+"-"+m+"-"+y;
        type="wd";
        binding.calendarView.setDate(Calendar.getInstance().getTimeInMillis(),false,true);
        binding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            date=dayOfMonth+"-"+month+"-"+year;
            Log.d("TAG", "onCreate: "+date);
            loadList();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadList();
    }

    private void loadList() {
        appointmentModels=new ArrayList<>();
        Query query=collectionReference.whereEqualTo("date",date).whereEqualTo("type",type);
        query.addSnapshotListener((value, error) -> {
            if (value != null) {
                for (DocumentChange dc: value.getDocumentChanges())
                {
                    if(dc.getType()== DocumentChange.Type.ADDED)
                    appointmentModels.add(dc.getDocument().toObject(AppointmentModel.class));
                    Log.d("TAG", "loadList: "+dc.getDocument().toObject(AppointmentModel.class).toString());
                }
                adapter=new AppointmentListAdapter(this,appointmentModels);
                binding.listAL.setAdapter(adapter);
            }
        });
    }

    public void doctors(View view) {
        binding.dBar.setBackgroundColor(getColor(R.color.white));
        binding.pBar.setBackgroundColor(getColor(R.color.purple_200));
        type="wd";
        loadList();
    }

    public void Patients(View view) {
        binding.dBar.setBackgroundColor(getColor(R.color.purple_200));
        binding.pBar.setBackgroundColor(getColor(R.color.white));
        type="wp";
        loadList();
    }

    private class AppointmentListViewHolder extends RecyclerView.ViewHolder {

        public AppointmentListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}