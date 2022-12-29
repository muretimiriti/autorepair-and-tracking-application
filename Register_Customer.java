package com.example.garagevalley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Customer extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView banner,registerUser;
    private EditText editTextFullName,editTextTelephone,editTextEmail,editTextPassword;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);
        mAuth = FirebaseAuth.getInstance();

        banner=(TextView) findViewById(R.id.bannerDescription);
        banner.setOnClickListener(this);

        registerUser=(Button)findViewById(R.id.registeruser);
        registerUser.setOnClickListener(this);

        editTextFullName=(EditText) findViewById(R.id.fullName);
        editTextTelephone=(EditText) findViewById(R.id.phoneNumber);
        editTextEmail=(EditText) findViewById(R.id.email);
        editTextPassword=(EditText) findViewById(R.id.password);

        progressBar=(ProgressBar) findViewById(R.id.progressbar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.registeruser:
                registerUser();
        }

    }

    private void registerUser() {
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        String fullName=editTextFullName.getText().toString().trim();
        String telephone=editTextTelephone.getText().toString().trim();

        if (fullName.isEmpty()){
            editTextFullName.setError("Full Name is required!");
            editTextFullName.requestFocus();
            return;
        }
        if (telephone.isEmpty()){
            editTextTelephone.setError("Telephone is required!");
            editTextTelephone.requestFocus();
            return;
        }
        if (email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            editTextPassword.setError("Min password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user=new User(fullName,telephone,email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){
                                                Intent intent=new Intent(Register_Customer.this,MainActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(Register_Customer.this, "Customer has been registered successfully!", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);

                                                //redirect to login


                                            }else{
                                                Toast.makeText(Register_Customer.this, "Failed to register customer! Try again!", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }

                                        }
                                    });



                        }else{
                            Toast.makeText(Register_Customer.this, "Failed to register customer! Try again!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });




    }
}