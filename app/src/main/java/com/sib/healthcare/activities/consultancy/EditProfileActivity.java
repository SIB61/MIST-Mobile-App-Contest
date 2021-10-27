package com.sib.healthcare.activities.consultancy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.activities.Donor;
import com.sib.healthcare.activities.LoginScreenActivity;
import com.sib.healthcare.activities.SessionManager;
import com.sib.healthcare.models.UserDataModel;
import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivityEditProfileBinding;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class EditProfileActivity extends AppCompatActivity {
private ActivityEditProfileBinding binding;
private String name,image,mbbs,degrees,type,clinicAddress,district,day1,day2,time1,time2,gender,division,blood,number,age,height,weight;
private String buttonText,time;
private StorageReference storageReference;
private DocumentReference drDocumentRef;
private DocumentReference userDocumentRef;
private Uri resultUri;
private UserDataModel userDataModel;
private String userId;
boolean isSuccess;
private ArrayAdapter dayAdapter,typeAdapter,genderAdapter,districtAdapter,divisionAdapter,bloodAdapter,nullAdapter;
private ProgressDialog progressDialog;
private AlertDialog.Builder builder;

private int from ;
private int d1,d2,max;
private Task t1,t2,t3,t4;
private UserDataModel u;
private final String[] genders={"male","female"};
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
        userId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        //viewModel=new ViewModelProvider(this).get(EditProfileActivityViewModel.class);
        drDocumentRef= FirebaseFirestore.getInstance().document("Doctors/"+userId);
        buttonText=getIntent().getStringExtra("TAG");
        userDocumentRef= FirebaseFirestore.getInstance().document("Users/"+userId);
        userDataModel=getIntent().getParcelableExtra("userDataModel");
        storageReference=FirebaseStorage.getInstance().getReference(userDataModel.getImage());
        setView();

        dayAdapter=ArrayAdapter.createFromResource(this, R.array.day_names,R.layout.support_simple_spinner_dropdown_item);
        binding.day1EP.setAdapter(dayAdapter);
        binding.day2EP.setAdapter(dayAdapter);

        typeAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,Types);
        binding.typeEP.setAdapter(typeAdapter);


        bloodAdapter=ArrayAdapter.createFromResource(this,R.array.BloodTypes,R.layout.support_simple_spinner_dropdown_item);
        binding.bloodEP.setAdapter(bloodAdapter);

        divisionAdapter=ArrayAdapter.createFromResource(this,R.array.divisions,R.layout.support_simple_spinner_dropdown_item);
        binding.divisionEP.setAdapter(divisionAdapter);

        districtAdapter=ArrayAdapter.createFromResource(this,R.array.districts,R.layout.support_simple_spinner_dropdown_item);
        binding.districtEP.setAdapter(districtAdapter);

        genderAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,genders);
        binding.genderEP.setAdapter(genderAdapter);

        from=getIntent().getIntExtra("from",0);
        image=userDataModel.getImage();
        nullAdapter=null;



        binding.DrCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
            {
                doctorInfoView(View.VISIBLE);
            }
            else
            {
                doctorInfoView(View.GONE);
            }
        });

        binding.DonorCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
            {
                donorInfo(true);

            }
            else
            {
                donorInfo(false);
            }
        });


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
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profilemenu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void showProgress(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("saving");
        progressDialog.show();
    }


    private void setView() {
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            Glide.with(this).load(uri).into(binding.imageEP);
        });
        binding.nameEP.setText(userDataModel.getName());
        binding.numberEP.setText(userDataModel.getPhoneNumber());
        binding.bloodEP.setText(userDataModel.getBloodGroup());
        binding.divisionEP.setText(userDataModel.getDivision());
        binding.districtEP.setText(userDataModel.getDistrict());
        if(userDataModel.isDonor())
        {
            binding.DonorCheckBox.setChecked(true);
            donorInfo(true);
        }
        if(userDataModel.isDoctor())
        {
            binding.DrCheckBox.setChecked(true);
            binding.clinicAddressEP.setText(userDataModel.getClinicAddress());
            binding.degreesEP.setText(userDataModel.getDegrees());
         //   binding.districtEP.setText(userDataModel.getDistrict());
            binding.day1EP.setText(userDataModel.getDay1());
            binding.day2EP.setText(userDataModel.getDay2());
            binding.time1EP.setText(userDataModel.getTime1());
            binding.time2EP.setText(userDataModel.getTime2());
            binding.mbbsEP.setText(userDataModel.getMbbs());
            binding.maxEP.setText(userDataModel.getMaxAppointmentsPerDay()+"");
            binding.typeEP.setText(userDataModel.getType());
            doctorInfoView(View.VISIBLE);
        }
        else
        {
            binding.DrCheckBox.setChecked(false);
            doctorInfoView(View.GONE);
        }
        if(userDataModel.isDonor())
        {
            binding.DonorCheckBox.setChecked(true);
            donorInfo(true);
        }
        else
        {
            binding.DonorCheckBox.setChecked(false);
            donorInfo(false);
        }


    }

    public void save(View view) {

        name=binding.nameEP.getText().toString().trim();
        mbbs=binding.mbbsEP.getText().toString().trim();
        degrees=binding.degreesEP.getText().toString().trim();
        type=binding.typeEP.getText().toString().trim();
        clinicAddress=binding.clinicAddressEP.getText().toString().trim();
        district=binding.districtEP.getText().toString().trim();
        day1=binding.day1EP.getText().toString().trim();
        day2=binding.day2EP.getText().toString().trim();
        time1=binding.time1EP.getText().toString().trim();
        time2=binding.time2EP.getText().toString().trim();
        division=binding.divisionEP.getText().toString().trim();
        blood=binding.bloodEP.getText().toString().trim();
        number=binding.numberEP.getText().toString().trim();
        if(!day1.isEmpty())
        d1=dayAdapter.getPosition(day1);
        if(!day2.isEmpty())
        d2=dayAdapter.getPosition(day2);
        age=binding.ageEP.getText().toString().trim();
        height=binding.heightEP.getText().toString().trim();
        gender=binding.genderEP.getText().toString().trim();
        weight=binding.weightEP.getText().toString().trim();
        max=Integer.parseInt(binding.maxEP.getText().toString().trim());
        if(name.isEmpty()||image.isEmpty())
            Toast.makeText(this,"name and image can't be empty",Toast.LENGTH_SHORT).show();
        else if (name.length()<3)
            binding.nameEP.setError("length must be at least 3");
        else {
            u=new UserDataModel(userId,image,name,mbbs,degrees,type,clinicAddress,district,day1,day2,time1,time2,height,weight,age,gender,division, userDataModel.getEmail(), number,blood,binding.DrCheckBox.isChecked(),binding.DonorCheckBox.isChecked(),userDataModel.getAppointments(),max,d1,d2);

            if(binding.DrCheckBox.isChecked())
            {
                if(mbbs.isEmpty()||clinicAddress.isEmpty()||district.isEmpty()||degrees.isEmpty()||type.isEmpty()||day1.isEmpty()||day2.isEmpty()||time1.isEmpty()||time2.isEmpty())
                {
                    Toast.makeText(EditProfileActivity.this, "required information can't be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    t1=drDocumentRef.set(u);
                    saveUser();
                }
            }
            if(binding.DonorCheckBox.isChecked())
            {
                if(number.isEmpty()||district.isEmpty()||division.isEmpty()||blood.isEmpty())
                {
                    Toast.makeText(this,"required field can't be empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    saveDonor();
                    saveUser();
                }
            }
            if (resultUri != null) {
                t3=storageReference.putFile(resultUri);
            }
            if(t1!=null&&t2!=null)
            Tasks.whenAll(t1,t2).addOnCompleteListener(task1 -> {
                if(task1.isSuccessful()){
                    Toast.makeText(this,"saved",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show();
                    userDocumentRef.set(userDataModel);
                    drDocumentRef.set(userDataModel);
                }
            });
            else
            {
                userDocumentRef.set(userDataModel);
                drDocumentRef.set(userDataModel);
            }

        }
    }

    private void saveUser() {
        t2=userDocumentRef.set(u);
    }


    private void saveDonor() {
        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        SessionManager sh2 = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        String email = hm.get(SessionManager.EMAIL);
        String name = hm.get(SessionManager.FULLNAME);
        String url = hm.get(SessionManager.URL);
        String token = hm.get(SessionManager.TOKEN);
        String pass = hm.get(SessionManager.PASS);
        String email1 = "";
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@')
                break;
            email1 += email.charAt(i);
        }

        HashMap pu = new HashMap();
        pu.put("Phone", number);
        pu.put("Division", district);
        pu.put("District", division);
        Toast.makeText(getApplicationContext(),number,Toast.LENGTH_LONG).show();
        sh2.loginSession(name, email, number, pass, url, "Yes", token, division, district, "Normal", "No");
        FirebaseDatabase.getInstance().getReference("Users").child(email1).
                updateChildren(pu);
        Random rn = new Random();
        long pk = rn.nextInt(10000000);

        Donor d = new Donor(district, division, number,
                blood, email, url, name, token);
        FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Donor").setValue(d);
        FirebaseDatabase.getInstance().getReference("Donor").child(division).
                child(pk + "").setValue(email1);
        FirebaseDatabase.getInstance().getReference("Donor").child(district).
                child(pk + "").setValue(email1);
        FirebaseDatabase.getInstance().getReference("Donor").child(blood).
                child(pk + "").setValue(email1);
        FirebaseDatabase.getInstance().getReference("Donors").child(email1).setValue(d);

    }


    public  void doctorInfoView(int i)
    {
       binding.districtLayoutEP.setHelperText("required*");
       binding.districtLayoutEP.setHelperTextEnabled(i==View.VISIBLE||binding.DonorCheckBox.isChecked());
        binding.addressLayoutEP.setVisibility(i);
        binding.day1LayoutEP.setVisibility(i);
        binding.day2LayoutEP.setVisibility(i);
        binding.degreeLayoutEP.setVisibility(i);
        binding.maxLayoutEP.setVisibility(i);
        binding.time1LayoutEP.setVisibility(i);
        binding.time2LayoutEP.setVisibility(i);
        binding.avEP.setVisibility(i);
        binding.mbbsLayoutEP.setVisibility(i);
        binding.fromLEP.setVisibility(i);
        binding.toLEP.setVisibility(i);
        binding.typeLayoutEP.setVisibility(i);
    }



    public void donorInfo(boolean b)
    {
        String s="required*";
        binding.districtLayoutEP.setHelperText(s);
        binding.districtLayoutEP.setHelperTextEnabled(b||binding.DrCheckBox.isChecked());
        binding.numberLayoutEP.setHelperText(s);
        binding.numberLayoutEP.setHelperTextEnabled(b);

        binding.divisionLayoutEP.setHelperText(s);
        binding.divisionLayoutEP.setHelperTextEnabled(b);

        binding.bloodLayoutEP.setHelperText(s);
        binding.bloodLayoutEP.setHelperTextEnabled(b);
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
                 Glide.with(this).load(resultUri).into(binding.imageEP);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    public void back(View view) {
        onBackPressed();
    }
}