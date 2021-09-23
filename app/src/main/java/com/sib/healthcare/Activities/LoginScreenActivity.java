package com.sib.healthcare.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.sib.healthcare.R;
import com.sib.healthcare.databinding.ActivityLoginScreenBinding;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class LoginScreenActivity extends AppCompatActivity {
    private ActivityLoginScreenBinding binding;
    private FirebaseAuth mAuth;
    private String email,password,name,Url;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser!=null&&currentUser.isEmailVerified())
            startActivity(new Intent(LoginScreenActivity.this,RegisterScreenActivity.class));
      /*  {
            email=getIntent().getStringExtra("Email");
            password=getIntent().getStringExtra("Password");
            password=getIntent().getStringExtra("Password");
            String email1="";
            HashMap mp=new HashMap();
            mp.put("Name",name);
            mp.put("Email",email);
            mp.put("Password",email);
            mp.put("url",Url);
            for(int i=0;i<email.length();i++)
            {
                if(email.charAt(i)=='@')
                    break;
                email1+=email.charAt(i);
            }
            FirebaseDatabase.getInstance().getReference("Users").child(email1).setValue(mp);

            String finalEmail = email1;
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (task.isSuccessful()) {
                                String token = Objects.requireNonNull(task.getResult()).getToken();
                                HashMap t=new HashMap();
                                t.put("token",token);
                                HashMap iu=new HashMap();
                                iu.put("token",token);
                                Random rn=new Random();
                                long yh=rn.nextInt(10000000);
                                iu.put("name",name);
                                FirebaseDatabase.getInstance().getReference("Names").child(yh+"").updateChildren(iu);
                                SessionManager sh=new SessionManager(LoginScreenActivity.this,SessionManager.USERSESSION);
                                sh.loginSession(name,email,"No",password,Url,"No",token,"No","No");
                                FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).child("Tokens").updateChildren(t);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }

                        }
                    });
        } */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
    }

    public void goToSignUp(View view) {
        startActivity(new Intent(this, RegisterScreenActivity.class));
    }

    public void signIn(View view) {
        email=binding.email.getText().toString().trim();
        password=binding.password.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginScreenActivity.this, email+password,
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

}