package com.example.cs5520_inclass_yijing8138;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.cs5520_inclass_yijing8138.InClass01.InClass01;
import com.example.cs5520_inclass_yijing8138.InClass02.InClass02;
import com.example.cs5520_inclass_yijing8138.Practice.PracticeActivity;

public class MainActivity extends AppCompatActivity {
    private Button buttonPractice, buttonInClass01, buttonInClass02, buttonInClass03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPractice = findViewById(R.id.buttonPractice);
        buttonInClass01 = findViewById(R.id.InClass01_button);
        buttonInClass02 = findViewById(R.id.InClass02_button);
        buttonInClass03 = findViewById(R.id.InClass03_button);

        buttonPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPracticeActivity = new Intent(MainActivity.this,
                        PracticeActivity.class);

                startActivity(toPracticeActivity);
            }
        });

        buttonInClass01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass01Activity = new Intent(MainActivity.this,
                        InClass01.class);

                startActivities(new Intent[]{toInClass01Activity});
            }
        });

        buttonInClass02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass02Activity = new Intent(MainActivity.this,
                        InClass02.class);

                startActivities(new Intent[]{toInClass02Activity});
            }
        });

        buttonInClass03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass03Activity = new Intent(MainActivity.this,
                        InClass03.class);

                startActivities(new Intent[]{toInClass03Activity});
            }
        });
    }
}