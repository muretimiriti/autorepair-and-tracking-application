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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Mechanic_registration extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView banner,registerMechanic;
    private EditText editTextFullName,editTextTelephone,editTextEmail,editTextPassword;

    private Spinner availabilitySpinner,departmentSpinner;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_registration);
        mAuth = FirebaseAuth.getInstance();

        banner=(TextView) findViewById(R.id.bannerDescription);
        banner.setOnClickListener(this);

        registerMechanic=(Button)findViewById(R.id.registermechanic);
        registerMechanic.setOnClickListener(this);

        editTextFullName=(EditText) findViewById(R.id.fullName);
        editTextTelephone=(EditText) findViewById(R.id.phoneNumber);
        editTextEmail=(EditText) findViewById(R.id.email);
        editTextPassword=(EditText) findViewById(R.id.password);
        availabilitySpinner=findViewById(R.id.availabilityspinner);
        departmentSpinner=findViewById(R.id.availabilityspinner);

        progressBar=(ProgressBar) findViewById(R.id.progressbar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.registermechanic:
                registerUser();
        }

    }

    private void registerUser() {
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        String fullName=editTextFullName.getText().toString().trim();
        String telephone=editTextTelephone.getText().toString().trim();
        String department=departmentSpinner.getSelectedItem().toString();
        String availability=availabilitySpinner.getSelectedItem().toString();

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
        if (department.equals("Select the department")){
            Toast.makeText(this, "Select Department", Toast.LENGTH_SHORT).show();
            return;
        }
        if (availability.equals("Select the time")){
            Toast.makeText(this, "Select time", Toast.LENGTH_SHORT).show();
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
                                                Intent intent=new Intent(Mechanic_registration.this,MainActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(Mechanic_registration.this, "Mechanic has been registered successfully!", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);


                                                //redirect to login

                                            }else{
                                                Toast.makeText(Mechanic_registration.this, "Failed to register mechanic! Try again!", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }

                                        }
                                    });



                            }else{
                            Toast.makeText(Mechanic_registration.this, "Failed to register mechanic! Try again!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });



    }
}