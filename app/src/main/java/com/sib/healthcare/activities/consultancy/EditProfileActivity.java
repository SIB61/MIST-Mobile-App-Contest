package com.sib.healthcare.activities.consultancy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.models.UserDataModel;
import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivityEditProfileBinding;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {
private ActivityEditProfileBinding binding;
private String name,image,mbbs,degrees,type,clinicAddress,district,day1,day2,time1,time2;
private String buttonText,time;
private StorageReference storageReference;
private DocumentReference drDocumentRef;
private DocumentReference userDocumentRef;
private Uri resultUri;
private UserDataModel userDataModel;
private String userId;
boolean isSuccess;
private ArrayAdapter dayAdapter,typeAdapter,locationAdapter;
private ProgressDialog progressDialog;

private MaterialTimePicker timePicker;
    private   String[] DistrictList={"All","Dhaka", "Faridpur" ,"Gazipur", "Gopalganj", "Jamalpur", "Kishoreganj", "Madaripur", "Manikganj", "Munshiganj",
            "Mymensingh", "Narayanganj", "Narsingdi", "Netrokona", "Rajbari", "Shariatpur", "Sherpur", "Tangail", "Bogura", "Joypurhat", "Naogaon",
            "Natore", "Nawabganj", "Pabna", "Rajshahi", "Sirajgonj", "Dinajpur", "Gaibandha", "Kurigram", "Lalmonirhat", "Nilphamari", "Panchagarh", "Rangpur",
            "Thakurgaon", "Barguna", "Barishal", "Bhola", "Jhalokati", "Patuakhali", "Pirojpur", "Bandarban", "Brahmanbaria", "Chandpur", "Chattogram", "Cumilla",
            "Cox's Bazar", "Feni", "Khagrachari", "Lakshmipur", "Noakhali", "Rangamati", "Habiganj", "Maulvibazar", "Sunamganj", "Sylhet", "Bagerhat", "Chuadanga", "Jashore", "Jhenaidah",
            "Khulna", "Kushtia", "Magura", "Meherpur", "Narail", "Satkhira"};
    private  String[] Types={"Allergists/Immunologists","Cardiologists","Colon & Rectal Surgeons","Dermatologists","Endocrinologists",
            "Gastroenterologists","Nephrologists","pathologists","Pediatricians","Psychiatrists","Radiologists","Medicine Specialist","Dental"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.editProfileToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        userId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        //viewModel=new ViewModelProvider(this).get(EditProfileActivityViewModel.class);
        dayAdapter=ArrayAdapter.createFromResource(this, R.array.day_names,R.layout.support_simple_spinner_dropdown_item);
        binding.day1EP.setAdapter(dayAdapter);
        binding.day2EP.setAdapter(dayAdapter);
        drDocumentRef= FirebaseFirestore.getInstance().document("Doctors/"+userId);
        buttonText=getIntent().getStringExtra("TAG");
        userDocumentRef= FirebaseFirestore.getInstance().document("Users/"+userId);
        userDataModel=new UserDataModel();
        userDataModel=getIntent().getParcelableExtra("userDataModel");
        typeAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,Types);
        locationAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,DistrictList);
        binding.locationEP.setAdapter(locationAdapter);
        binding.typeEP.setAdapter(typeAdapter);

        userDataModel=getIntent().getParcelableExtra("userDataModel");
        image=userDataModel.getImage();
        setView();


        binding.time1LayoutEP.setEndIconOnClickListener( v -> {
            timePicker = new MaterialTimePicker.Builder().build();
            timePicker.show(getSupportFragmentManager(),"select time");
            timePicker.addOnPositiveButtonClickListener( v1 -> {
                int h = timePicker.getHour();
                String m=timePicker.getMinute()+"";
                String a;
                a=  h<12 ? "AM":"PM" ;
                h= h<=12 ? h : h-12;
                h= h==0 ? 12 : h;
                if(m.length()==1) m="0"+m;
                time1=h+" : "+m+" "+a;
                userDataModel.setTime1(time1);
                binding.time1EP.setText(time1);
            });
        });
        binding.time2LayoutEP.setEndIconOnClickListener( v -> {
            timePicker = new MaterialTimePicker.Builder().build();
            timePicker.show(getSupportFragmentManager(),"select time");
            timePicker.addOnPositiveButtonClickListener( v1 -> {
                int h = timePicker.getHour();
                String m=timePicker.getMinute()+"";
                String a;
                a=  h<12 ? "AM":"PM" ;
                h= h<=12 ? h : h-12;
                h= h==0 ? 12 : h;
                if(m.length()==1) m="0"+m;
                time2=h+" : "+m+" "+a;
                userDataModel.setTime2(time2);
                binding.time2EP.setText(time2);
            });
        });

        binding.button4.setText(buttonText);

        }

    private void showProgress(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("saving");
        progressDialog.show();
    }
    private void setView() {
        storageReference=FirebaseStorage.getInstance().getReference(image);
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            Glide.with(this).load(uri).into(binding.imageEP);
        });
        binding.nameEP.setText(userDataModel.getName());
       // binding.clinicAddressEP.setText(userDataModel.getClinicAddress());
      //  binding.degreesEP.setText(userDataModel.getDegrees());
      //  binding.locationEP.setText(userDataModel.getDistrict());
       // binding.day1EP.setText(userDataModel.getDay1());
      //  binding.day2EP.setText(userDataModel.getDay2());
       // binding.time1EP.setText(userDataModel.getTime1());
       // binding.time2EP.setText(userDataModel.getTime2());
       // binding.mbbsEP.setText(userDataModel.getMbbs());
    }

    public void save(View view) {

        name=binding.nameEP.getText().toString().trim();
        mbbs=binding.mbbsEP.getText().toString().trim();
        degrees=binding.degreesEP.getText().toString().trim();
        type=binding.typeEP.getText().toString().trim();
        clinicAddress=binding.clinicAddressEP.getText().toString().trim();
        district=binding.locationEP.getText().toString().trim();
        day1=binding.day1EP.getText().toString().trim();
        day2=binding.day2EP.getText().toString().trim();
        time1=binding.time1EP.getText().toString().trim();
        time2=binding.time2EP.getText().toString().trim();


        if(name.isEmpty()||image.isEmpty()||mbbs.isEmpty()||degrees.isEmpty()||type.isEmpty()||clinicAddress.isEmpty()
           ||district.isEmpty()||day1.isEmpty()||day2.isEmpty()||time1.isEmpty()||time2.isEmpty())
            Toast.makeText(this,"any field can not be empty",Toast.LENGTH_SHORT).show();
        else if (name.length()<3)
            binding.nameEP.setError("length must be at least 3");
        else {
            if (resultUri != null) {
                storageReference.putFile(resultUri).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        saveDocument();
                    }
                });
            } else saveDocument();
        }
    }
    public void saveDocument(){
        userDataModel.setName(name);
        userDataModel.setMbbs(mbbs);
        userDataModel.setDegrees(degrees);
        userDataModel.setType(type);
        userDataModel.setDistrict(district);
        userDataModel.setClinicAddress(clinicAddress);
        userDataModel.setDay1(day1);
        userDataModel.setDay2(day2);
        userDataModel.setTime1(time1);
        userDataModel.setTime2(time2);
        userDataModel.setDoctor(true);
        userDataModel.setImage(image);
        userDataModel.setAppointments(0);
        drDocumentRef.set(userDataModel).addOnCompleteListener(
                task1 -> {
                    if(task1.isSuccessful())
                    {
                        userDocumentRef.set(userDataModel).addOnSuccessListener(unused -> {
                            Toast.makeText(EditProfileActivity.this,"Successfully registered as a doctor",Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }
                    else Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();

                }
        );
    }



    public void setProfilePic(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                 resultUri = result.getUri();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}