package com.example.garagevalley;

import static com.example.garagevalley.R.id.RegisterCar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Diagnostics extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private Button RegisterCar;
    private EditText editTextnumberPlate,editTextChassisNumber,editTextcarModel,editTextmtrl_picker_text_input_date;



    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostics);
        mAuth = FirebaseAuth.getInstance();

        RegisterCar=(Button)findViewById(R.id.RegisterCar);
        RegisterCar.setOnClickListener(this);

        editTextnumberPlate=(EditText) findViewById(R.id.numberPlate);
        editTextChassisNumber=(EditText) findViewById(R.id.ChassisNumber);
        editTextcarModel=(EditText) findViewById(R.id.carModel);
        editTextmtrl_picker_text_input_date=(EditText) findViewById(R.id.mtrl_picker_text_input_date);
        progressBar=(ProgressBar) findViewById(R.id.progressbar);
    }



    @Override
    public void onClick(View v) {
        final String numberPlate=editTextnumberPlate.getText().toString().trim();
        final String ChassisNumber=editTextChassisNumber.getText().toString().trim();
        final String carModel=editTextcarModel.getText().toString().trim();
        final String mtrl_picker_text_input_date= editTextmtrl_picker_text_input_date.getText().toString().trim();

        if (TextUtils.isEmpty(numberPlate)){
            editTextnumberPlate.setError("number plate required!");
            return;
        }
        if (TextUtils.isEmpty(ChassisNumber)){
            editTextChassisNumber.setError("Chassis Number required!");
            return;
        }
        if (TextUtils.isEmpty(carModel)){
            editTextcarModel.setError("Car Model required!");
            return;
        }
        if (TextUtils.isEmpty(numberPlate)){
            editTextmtrl_picker_text_input_date.setError("Date required!");
            return;
        }
        else{

        }
    }

}