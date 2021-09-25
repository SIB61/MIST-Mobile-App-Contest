package com.sib.healthcare.Activities.Consultancy;

import static java.util.Arrays.sort;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.sib.healthcare.Adapter.Consultancy.DrListAdapter;
import com.sib.healthcare.DataModels.UserDataModel;
import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivityDoctorListBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DoctorListActivity extends AppCompatActivity {
private ActivityDoctorListBinding binding;
private DrListAdapter adapter;
private List<UserDataModel> drList,drList1;
private String type;
private String location;
private CollectionReference collectionReference;
private Query query;
private   String[] DistrictList={"All","Dhaka", "Faridpur" ,"Gazipur", "Gopalganj", "Jamalpur", "Kishoreganj", "Madaripur", "Manikganj", "Munshiganj",
        "Mymensingh", "Narayanganj", "Narsingdi", "Netrokona", "Rajbari", "Shariatpur", "Sherpur", "Tangail", "Bogura", "Joypurhat", "Naogaon",
        "Natore", "Nawabganj", "Pabna", "Rajshahi", "Sirajgonj", "Dinajpur", "Gaibandha", "Kurigram", "Lalmonirhat", "Nilphamari", "Panchagarh", "Rangpur",
        "Thakurgaon", "Barguna", "Barishal", "Bhola", "Jhalokati", "Patuakhali", "Pirojpur", "Bandarban", "Brahmanbaria", "Chandpur", "Chattogram", "Cumilla",
       "Cox's Bazar", "Feni", "Khagrachari", "Lakshmipur", "Noakhali", "Rangamati", "Habiganj", "Maulvibazar", "Sunamganj", "Sylhet", "Bagerhat", "Chuadanga", "Jashore", "Jhenaidah",
       "Khulna", "Kushtia", "Magura", "Meherpur", "Narail", "Satkhira"};
private  String[] Types={"Allergists/Immunologists","All","Cardiologists","Colon & Rectal Surgeons","Dermatologists","Endocrinologists",
        "Gastroenterologists","Nephrologists","pathologists","Pediatricians","Psychiatrists","Radiologists","Medicine Specialist","Dental"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDoctorListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.doctorListToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        collectionReference= FirebaseFirestore.getInstance().collection("Doctors");

        type=getIntent().getStringExtra("type");
        location="All";
        binding.spinner.setText(type);

        binding.drListRecycleView.setLayoutManager(new GridLayoutManager(this,2, GridLayoutManager.VERTICAL,false));
        drList=new ArrayList<>();
        Arrays.sort(DistrictList);
        Arrays.sort(Types);
        adapter=new DrListAdapter(drList);
        binding.drListRecycleView.setAdapter(adapter);

        ArrayAdapter adapter1=new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,Types);
        ArrayAdapter adapter2=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,DistrictList);

        binding.spinner.setAdapter(adapter1);
        binding.spinner2.setAdapter(adapter2);

        binding.spinner.setOnItemClickListener((parent, view, position, id) -> {
            type=Types[position];
            loadList(type,location);
        });

        binding.spinner2.setOnItemClickListener((parent, view, position, id) -> {
            location=DistrictList[position];
            loadList(type,location);
        });






    }

    @Override
    protected void onStart() {
        super.onStart();
        loadList(type,location);
    }

    private void loadList(String t, String l){
        binding.progressBarDL.setVisibility(View.VISIBLE);

        if(t.equals("All")&&l.equals("All")){
            query=collectionReference;
        }
        else if(!t.equals("All")&&!l.equals("All"))
        {
            query=collectionReference.whereEqualTo("type",t)
                    .whereEqualTo("district",l);
        }
        else if (!t.equals("All"))
        {
            query=collectionReference.whereEqualTo("type",t);

        }
        else {
            query=collectionReference.whereEqualTo("district",l);
        }
        drList=new ArrayList<>();
        query.get().addOnCompleteListener( task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot dc : Objects.requireNonNull(task.getResult())) {
                    drList.add(dc.toObject(UserDataModel.class));
                }
                adapter = new DrListAdapter(drList);
                binding.drListRecycleView.setAdapter(adapter);
                binding.progressBarDL.setVisibility(View.GONE);
            }
            else {
                Log.d("TAG", "loadList: "+ Objects.requireNonNull(task.getException()).toString());
            }
        });
    }

    }



