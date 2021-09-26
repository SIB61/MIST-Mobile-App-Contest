package com.sib.healthcare.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sib.healthcare.DataModels.UserDataModel;
import com.sib.healthcare.databinding.ActivityRegisterScreenBinding;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Objects;

public class RegisterScreenActivity extends AppCompatActivity {
    private ActivityRegisterScreenBinding binding;
    private FirebaseAuth mAuth;
    private String image ,name,email,password,rePassword,uid;
    private ProgressDialog progressDialog;
    private Uri resultUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.materialToolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()!=null&&mAuth.getCurrentUser().isEmailVerified())
        {
           // updateUI(mAuth.getCurrentUser());
        }
    }




    public void signUp(View view) {
        name=binding.nameSU.getText().toString().trim();
        email=binding.emailSU.getText().toString().trim();
        password=binding.passwordSU.getText().toString().trim();
        rePassword=binding.rePasswordSU.getText().toString().trim();
        if(name.isEmpty())
        {
            binding.nameSU.setError("name is empty");
        }
        else if(name.length()<3)
        {
            binding.nameSU.setError("length must be at least 3");
        }

        else if(email.isEmpty())
        {
            binding.emailSU.setError("email is empty");
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            binding.emailSU.setError("invalid email");
        }
        else if(password.length()<6)
        {
            binding.passwordSU.setError("length must be at least 6");
        }
        else if (rePassword.isEmpty())
        {
            binding.rePasswordSU.setError("re-password is empty");
        }
        else if(!rePassword.equals(password))
        {
            binding.rePasswordSU.setError("passwords didn't match");
        }
        if(resultUri==null)
        {
            Toast.makeText(this,"Please pick your profile image",Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.setTitle("Signing up...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String path="ProfilePics/"+user.getUid()+".jpg";
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference(path);
                            storageReference.putFile(resultUri).addOnCompleteListener(
                                    task3 -> {
                                        if(task3.isSuccessful())
                                            image=path;
                                        UserDataModel userDataModel=new UserDataModel(user.getUid(),image,name,email,false,false);
                                        FirebaseFirestore.getInstance().document("Users/"+user.getUid())
                                                .set(userDataModel).addOnCompleteListener(task1 -> {
                                            if(task1.isSuccessful())
                                            {
                                                user.sendEmailVerification().addOnCompleteListener(task2 -> {
                                                    if(task2.isSuccessful()){
                                                        progressDialog.dismiss();
                                                        Toast.makeText(getApplicationContext(),"Verification link sent to your email",Toast.LENGTH_LONG).show();
                                                        FirebaseInstanceId.getInstance().getInstanceId()
                                                                .addOnCompleteListener(task4 -> {
                                                                    if (task4.isSuccessful()) {
                                                                        String token = Objects.requireNonNull(task4.getResult()).getToken();
                                                                        SessionManager sh=new SessionManager(RegisterScreenActivity.this,SessionManager.USERSESSION);
                                                                        sh.loginSession(name,email,"No",password,path,"No",token,"No","No");

                                                                        startActivity(new Intent(RegisterScreenActivity.this,LoginScreenActivity.class).putExtra("Work","Reg"));
                                                                    }
                                                                    else{
                                                                        Log.d("TAG", "signUp: "+task4.getException());
                                                                    }
                                                                });
                                                    }
                                                });
                                            }
                                            else
                                            {
                                                Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show();
                                                user.delete();
                                                Log.d("TAG", "signUp: ",task1.getException());
                                            }
                                        });
                                    }
                            );
                        }

                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterScreenActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    });
        }
    }



    private void updateUI(FirebaseUser user) {
        if (user!=null && user.isEmailVerified())
        {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    public void getImg(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("Profile Pic")
                .setAspectRatio(1,1)
                .setAutoZoomEnabled(true)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setOutputCompressQuality(70)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                Picasso.get().load(resultUri).into(binding.profilePicSU);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}