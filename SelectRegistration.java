package com.example.garagevalley;

import static com.example.garagevalley.R.id.registercustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectRegistration extends AppCompatActivity {

    private TextView back;
    private Button registermechanic;
    private Button registercustomer;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_registration);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelectRegistration.this, MainActivity.class);
                startActivity(intent);

            }
        });
        registermechanic=findViewById(R.id.registermechanic);
        registercustomer=findViewById(R.id.registercustomer);


        registermechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelectRegistration.this,Mechanic_registration.class);
                startActivity(intent);

            }
        });
        registercustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelectRegistration.this,Register_Customer.class);
                startActivity(intent);

            }
        });

    }
}