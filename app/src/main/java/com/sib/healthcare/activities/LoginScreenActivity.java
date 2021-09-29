package com.sib.healthcare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sib.healthcare.databinding.ActivityLoginScreenBinding;

import java.util.HashMap;
import java.util.Random;

public class LoginScreenActivity extends AppCompatActivity {
    private ActivityLoginScreenBinding binding;
    private FirebaseAuth mAuth;
    private String email,password,name,Url,work;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();


     //   updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        work=getIntent().getStringExtra("Work");
        if(currentUser!=null&&currentUser.isEmailVerified())
       {
            if(work!=null&&work.equals("Reg")){
            SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
            HashMap<String, String> hm = sh.returnData();
            String token = hm.get(SessionManager.TOKEN);
             name = hm.get(SessionManager.FULLNAME);
            Url = hm.get(SessionManager.URL);
            String email1 = "";
            HashMap mp = new HashMap();
            mp.put("Name", name);
            mp.put("Email", email);
            mp.put("Password", password);
            mp.put("url", Url);
            for (int i = 0; i < email.length(); i++) {
                if (email.charAt(i) == '@')
                    break;
                if(email.charAt(i)!='.')
                email1 += email.charAt(i);
            }

            String finalEmail = email1;
            HashMap t = new HashMap();
            t.put("token", token);
            HashMap iu = new HashMap();
            iu.put("token", token);
            Random rn = new Random();
            long yh = rn.nextInt(10000000);
            iu.put("name", name);
            FirebaseDatabase.getInstance().getReference("Names").child(yh + "").updateChildren(iu);
                FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).updateChildren(mp);
            FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).child("Tokens").updateChildren(t);
            FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).updateChildren(mp);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
            else
            {
                String email1="";
                for (int i = 0; i < email.length(); i++) {
                    if (email.charAt(i) == '@')
                        break;
                    if(email.charAt(i)!='.')
                        email1 += email.charAt(i);
                }
                String finalEmail = email1;
                FirebaseDatabase.getInstance().getReference("Users").child(email1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            Toast.makeText(getApplicationContext(), finalEmail, Toast.LENGTH_LONG).show();
                            name = snapshot.child("Name").getValue().toString();
                            Url = snapshot.child("url").getValue().toString();
                            FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).child("Donor").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                    Toast.makeText(LoginScreenActivity.this, snapshot1.toString(), Toast.LENGTH_SHORT).show();
                                    if(snapshot1.hasChildren()) {
                                        String phone = snapshot1.child("phone").getValue().toString();
                                        String district = snapshot1.child("district").getValue().toString();
                                        String division = snapshot1.child("division").getValue().toString();
                                        String blood = snapshot1.child("blood").getValue().toString();
                                        String url = snapshot1.child("url").getValue().toString();
                                        String token = snapshot1.child("token").getValue().toString();
                                        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
                                        HashMap<String, String> hm = sh.returnData();

                                        sh.loginSession(name, email, phone, password, url, "Yes", token, division, district);
                                    }
                                    else
                                    {
                                        FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).child("Tokens").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                                SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);

                                                sh.loginSession(name, email, "No", password, Url, "No", snapshot1.child("token").getValue().toString(), "No", "No");

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                      }
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }
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
        if(!email.isEmpty()&&!password.isEmpty())
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
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
                });
    }

    public void forgotpass(View view) {
        String e=binding.email.toString().trim();
        if(e.isEmpty())
        {
            Toast.makeText(this,"Please enter your email to get password reset link",Toast.LENGTH_SHORT).show();

        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(e).matches())
        {
            Toast.makeText(this,"Please enter correct email address",Toast.LENGTH_SHORT).show();
        }
        else{
            FirebaseAuth.getInstance().sendPasswordResetEmail(e).addOnSuccessListener( unused -> {
                Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show();
            });

        }
    }
}