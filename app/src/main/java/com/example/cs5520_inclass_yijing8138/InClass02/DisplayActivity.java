package com.example.cs5520_inclass_yijing8138.InClass02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cs5520_inclass_yijing8138.MainActivity;
import com.example.cs5520_inclass_yijing8138.R;

public class DisplayActivity extends AppCompatActivity {
    Button quitButton;
    ImageView profilePhoto, moodPhoto;
    TextView name, email, device, mood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        setTitle("Display Activity");

        Intent result = getIntent();

        Profile profile = (Profile) result.getSerializableExtra(InClass02.keyToDisplay);

        profilePhoto = findViewById(R.id.profilePhoto);
        int imageNumber = profile.getProfilePhotoId();
        profilePhoto.setImageResource(imageNumber);

        device = findViewById(R.id.deviceTextView);
        String deviceReceive = profile.getDevice();
        device.setText(deviceReceive);

        name = findViewById(R.id.nameTextView);
        String nameReceive = profile.getName();
        name.setText(nameReceive);

        email = findViewById(R.id.emailTextView);
        String emailReceive = profile.getEmail();
        email.setText(emailReceive);

        mood = findViewById(R.id.mood);
        String moodReceive = profile.getMoodResult();
        mood.setText(moodReceive);

        moodPhoto = findViewById(R.id.moodPhoto);

        switch (moodReceive) {
            case "Angry":
                moodPhoto.setImageResource(R.drawable.angry);
                break;
            case "Sad":
                moodPhoto.setImageResource(R.drawable.sad);
                break;
            case "Happy":
                moodPhoto.setImageResource(R.drawable.happy);
                break;
            case "Awesome":
                moodPhoto.setImageResource(R.drawable.awesome);
                break;
        }

        quitButton = findViewById(R.id.backButton);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayActivity.this, MainActivity.class);
                startActivities(new Intent[]{intent});
            }
        });
    }
}