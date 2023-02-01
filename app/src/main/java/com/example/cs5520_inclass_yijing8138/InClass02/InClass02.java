package com.example.cs5520_inclass_yijing8138.InClass02;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs5520_inclass_yijing8138.R;

public class InClass02 extends AppCompatActivity {
    EditText name, email;
    ImageButton imageButton;
    ImageView dynamicView;
    SeekBar seekBar;
    RadioGroup radioGroup;
    Button submitButton;
    TextView moodResult;
    Profile profile = new Profile();

    final static String keyToSelectAvatar = "toSelectAvatar";
    final static String keyToDisplay = "toDisplay";
    Boolean flag1 = false;
    Boolean flag2 = false;

    ActivityResultLauncher<Intent> startActivity
            = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK)
                    {
                        assert result.getData() != null;
                        int imageNumber = getResources()
                                .getIdentifier(result.getData().getStringExtra(keyToSelectAvatar),
                                        "drawable", getPackageName());
                        imageButton.setImageResource(imageNumber);
                        profile.setProfilePhotoId(imageNumber);
                        flag1 = true;
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class02);
        setTitle("Edit Profile Activity");

        name = findViewById(R.id.editTextPersonName);
        email = findViewById(R.id.editTextEmailAddress);
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSelectAvatar = new Intent(InClass02.this, SelectAvatar.class);
                startActivity.launch(toSelectAvatar);
            }
        });

        seekBar = findViewById(R.id.seekBar);
        dynamicView = findViewById(R.id.dynamicImageView);
        moodResult = findViewById(R.id.moodResult);
        seekBar.setProgress(2);
        moodResult.setText(R.string.moodDefault);
        profile.setMoodResult(moodResult.getText().toString());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i == 0)
                {
                    dynamicView.setImageResource(R.drawable.angry);
                    moodResult.setText(R.string.Angry);
                } else if (i == 1) {
                    dynamicView.setImageResource(R.drawable.sad);
                    moodResult.setText(R.string.Sad);
                } else if (i == 2) {
                    dynamicView.setImageResource(R.drawable.happy);
                    moodResult.setText(R.string.moodDefault);
                } else if (i == 3) {
                    dynamicView.setImageResource(R.drawable.awesome);
                    moodResult.setText(R.string.Awesome);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                profile.setMoodResult(moodResult.getText().toString());
            }
        });

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Intent toDisplay = new Intent(InClass02.this, DisplayActivity.class);
                if(i == R.id.radioButtonAndroid)
                {
                    profile.setDevice("Android");
                    flag2 = true;
                }
                else if(i == R.id.radioButtoniOS) {
                    profile.setDevice("iOS");
                    flag2 = true;
                }
            }
        });

        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You must input name!",
                            Toast.LENGTH_LONG).show();
                } else if (email.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You must input email!",
                            Toast.LENGTH_LONG).show();
                } else if (!(email.getText().toString().contains("@")
                        && email.getText().toString().contains(".")
                        && (email.getText().toString().matches(".*[0-9].*")
                        || email.getText().toString().matches(".*[a-zA-z].*")))) {
                    Toast.makeText(getApplicationContext(), "Email format is not correct!",
                            Toast.LENGTH_LONG).show();
                } else if (!flag1) {
                    Toast.makeText(getApplicationContext(), "You must pick a profile image!",
                            Toast.LENGTH_LONG).show();
                } else if (!flag2) {
                    Toast.makeText(getApplicationContext(), "You must choose your device!",
                            Toast.LENGTH_LONG).show();
                } else {
                    profile.setName(name.getText().toString());
                    profile.setEmail(email.getText().toString());
                    Intent toDisplay = new Intent(InClass02.this, DisplayActivity.class);
                    toDisplay.putExtra(keyToDisplay, profile);
                    startActivity(toDisplay);
                    finish();
                }

            }
        });
    }
}