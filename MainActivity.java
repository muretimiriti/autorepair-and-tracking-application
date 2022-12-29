package com.example.garagevalley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register;
    private EditText editTextEmail,editTextPassword;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        register=(TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn=(Button)findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editTextEmail=(EditText)  findViewById(R.id.email);
        editTextPassword=(EditText) findViewById(R.id.password);

        progressBar=(ProgressBar) findViewById(R.id.progressbar);

        mAuth=FirebaseAuth.getInstance();


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                startActivity(new Intent(this,SelectRegistration.class));
                break;

            case R.id.signIn:
                userLogin();
                break;

        }
    }

    private void userLogin() {
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

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
        progressBar.setVisibility(View.GONE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Intent intent=new Intent(MainActivity.this,Landing_page.class);
                startActivity(intent);
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(MainActivity.this, "Failed to login!Please check your credentials", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    Intent intent=new Intent(MainActivity.this,Landing_page.class);

}