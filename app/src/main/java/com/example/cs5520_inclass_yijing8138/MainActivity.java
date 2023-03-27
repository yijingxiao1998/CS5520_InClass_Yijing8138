package com.example.cs5520_inclass_yijing8138;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cs5520_inclass_yijing8138.InClass01.InClass01;
import com.example.cs5520_inclass_yijing8138.InClass02.InClass02;
import com.example.cs5520_inclass_yijing8138.InClass03.InClass03;
import com.example.cs5520_inclass_yijing8138.InClass04.InClass04;
import com.example.cs5520_inclass_yijing8138.InClass05.InClass05;
import com.example.cs5520_inclass_yijing8138.InClass06.InClass06;
import com.example.cs5520_inclass_yijing8138.InClass07.InClass07;
import com.example.cs5520_inclass_yijing8138.InClass08.InCLass08;
import com.example.cs5520_inclass_yijing8138.Practice.PracticeActivity;

public class MainActivity extends AppCompatActivity {
    private Button buttonPractice, buttonInClass01, buttonInClass02, buttonInClass03;
    private Button buttonInClass04, buttonInClass05, buttonInClass06, buttonInClass07;
    private Button buttonInClass08;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPractice = findViewById(R.id.buttonPractice);
        buttonInClass01 = findViewById(R.id.InClass01_button);
        buttonInClass02 = findViewById(R.id.InClass02_button);
        buttonInClass03 = findViewById(R.id.InClass03_button);
        buttonInClass04 = findViewById(R.id.InClass04_button);
        buttonInClass05 = findViewById(R.id.InClass05_button);
        buttonInClass06 = findViewById(R.id.InClass06_button);
        buttonInClass07 = findViewById(R.id.InCLass07_button);
        buttonInClass08 = findViewById(R.id.InCLass08_button);

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

        buttonInClass04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass04Activity = new Intent(MainActivity.this,
                        InClass04.class);

                startActivities(new Intent[]{toInClass04Activity});
            }
        });

        buttonInClass05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass05Activity = new Intent(MainActivity.this,
                        InClass05.class);

                startActivities(new Intent[]{toInClass05Activity});
            }
        });

        buttonInClass06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass06Activity = new Intent(MainActivity.this,
                        InClass06.class);

                startActivities(new Intent[]{toInClass06Activity});
            }
        });

        buttonInClass07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass07Activity = new Intent(MainActivity.this,
                        InClass07.class);

                startActivities(new Intent[]{toInClass07Activity});
            }
        });

        buttonInClass08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass08Activity = new Intent(MainActivity.this,
                        InCLass08.class);

                startActivities(new Intent[]{toInClass08Activity});
            }
        });
    }
}