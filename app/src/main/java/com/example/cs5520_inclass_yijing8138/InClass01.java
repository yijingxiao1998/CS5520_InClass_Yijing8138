package com.example.cs5520_inclass_yijing8138;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InClass01 extends AppCompatActivity {
    final String TAG = "demo";
    EditText editTextWeight, editTextFeet, editTextInches;
    Button calculateBMIbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class01);
        setTitle("BMI calculator");

        editTextWeight = findViewById(R.id.editTextWeight);
        editTextFeet = findViewById(R.id.editTextFeet);
        editTextInches = findViewById(R.id.editTextInches);
        Toast.makeText(getApplicationContext(), "Click on Calculate BMI button to find your " +
                "BMI!", Toast.LENGTH_LONG).show();

        calculateBMIbutton = findViewById(R.id.button);
        calculateBMIbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Make sure all input are not empty.
                if (editTextWeight.getText().toString().isEmpty()
                        || editTextFeet.getText().toString().isEmpty()
                        || editTextInches.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "All input must not be empty, " +
                            "please check then continues!", Toast.LENGTH_LONG).show();
                } else {
                    Double weight = 0.0;
                    Double feet = 0.0;
                    Double inches = 0.0;

                    // Make sure all inputs are valid, if not such as input "." then make toast.
                    try {
                        weight = Double.parseDouble(editTextWeight.getText().toString());
                        feet = Double.parseDouble(editTextFeet.getText().toString());
                        inches = Double.parseDouble(editTextInches.getText().toString());
                    }
                    catch (Exception exception)
                    {
                        Toast.makeText(getApplicationContext(), "Invalid input!",
                                Toast.LENGTH_LONG).show();
                    }

                    // Make sure input weight is greater than 0, but for feet and inches we allow
                    // the user input one of them 0.
                    if (weight <= 0) {
                        Toast.makeText(getApplicationContext(), "Input weight must be bigger " +
                                "than 0!", Toast.LENGTH_LONG).show();
                    } else if (feet + inches == 0) {
                        Toast.makeText(getApplicationContext(), "Input Height must be bigger " +
                                "than 0!", Toast.LENGTH_LONG).show();
                    } else {
                        // Calculate BMI.
                        Double BMIresult = (weight / Math.pow(feet * 12 + inches, 2)) * 703;
                        String status = "";
                        // Get the status according to the BMI.
                        if (BMIresult <= 18.5) {
                            status = "Underweight";
                        } else if (18.5 < BMIresult && BMIresult < 24.9) {
                            status = "Normal Weight";
                        } else if (25 < BMIresult && BMIresult < 29.9) {
                            status = "Overweight";
                        } else if (BMIresult > 30) {
                            status = "Obese";
                        }
                        Toast.makeText(getApplicationContext(), "Your BMI: " + BMIresult
                                + "\nYou are " + status, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}