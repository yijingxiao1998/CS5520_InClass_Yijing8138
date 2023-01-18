package com.example.cs5520_inclass_yijing8138;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PracticeActivity extends AppCompatActivity {
    private Button buttonLog;
    private final String TAG = "demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        setTitle("Practice Activities:");
        buttonLog = findViewById(R.id.buttonLog);
        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Practice! Practice! Practice!");
            }
        });
    }
}