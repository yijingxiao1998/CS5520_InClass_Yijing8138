package com.example.cs5520_inclass_yijing8138.Practice;

import static android.widget.Toast.LENGTH_LONG;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cs5520_inclass_yijing8138.R;

public class PracticeActivity extends AppCompatActivity {
    private Button buttonLog, buttonToast;
    private final String TAG = "demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        setTitle("Practice Activities:");
        buttonLog = findViewById(R.id.buttonLog);
        buttonToast = findViewById(R.id.buttonToast);

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Practice! Practice! Practice!");
            }
        });

        buttonToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Now push to GitHub and Submit!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}