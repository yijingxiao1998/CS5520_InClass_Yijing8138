package com.example.cs5520_inclass_yijing8138.InClass02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.cs5520_inclass_yijing8138.R;

public class SelectAvatar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);
        setTitle("Select Avatar");

        ImageButton avatarf1, avatarf2, avatarf3, avatarm1, avatarm2, avatarm3;

        avatarf1 = findViewById(R.id.avatarf1);
        avatarf2 = findViewById(R.id.avatarf2);
        avatarf3 = findViewById(R.id.avatarf3);
        avatarm1 = findViewById(R.id.avatarm1);
        avatarm2 = findViewById(R.id.avatarm2);
        avatarm3 = findViewById(R.id.avatarm3);

        avatarf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass02 = new Intent();
                toInClass02.putExtra(InClass02.keyToSelectAvatar, "avatar_f_1");
                setResult(RESULT_OK, toInClass02);
                finish();
            }
        });
        avatarf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass02 = new Intent();
                toInClass02.putExtra(InClass02.keyToSelectAvatar, "avatar_f_2");
                setResult(RESULT_OK, toInClass02);
                finish();
            }
        });
        avatarf3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass02 = new Intent();
                //toInClass02.putExtra(InClass02.keyToSelectAvatar, getResources()
                //        .getIdentifier("avatar_f_3", "drawable", getPackageName()));
                toInClass02.putExtra(InClass02.keyToSelectAvatar, "avatar_f_3");
                setResult(RESULT_OK, toInClass02);
                finish();
            }
        });
        avatarm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass02 = new Intent();
                toInClass02.putExtra(InClass02.keyToSelectAvatar, "avatar_m_1");
                setResult(RESULT_OK, toInClass02);
                finish();
            }
        });
        avatarm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass02 = new Intent();
                toInClass02.putExtra(InClass02.keyToSelectAvatar, "avatar_m_2");
                setResult(RESULT_OK, toInClass02);
                finish();
            }
        });
        avatarm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInClass02 = new Intent();
                toInClass02.putExtra(InClass02.keyToSelectAvatar, "avatar_m_3");
                setResult(RESULT_OK, toInClass02);
                finish();
            }
        });
    }
}