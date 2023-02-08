/**
 * Name: Yijing Xiao
 * Assignment: InClass 03
 */
package com.example.cs5520_inclass_yijing8138.InClass03;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs5520_inclass_yijing8138.InClass02.DisplayActivity;
import com.example.cs5520_inclass_yijing8138.InClass02.InClass02;
import com.example.cs5520_inclass_yijing8138.InClass02.Profile;
import com.example.cs5520_inclass_yijing8138.R;

public class MainFragment extends Fragment {
    EditText name, email;
    ImageButton imageButton;
    ImageView dynamicView;
    SeekBar seekBar;
    RadioGroup radioGroup;
    Button submitButton;
    TextView moodResult;
    Profile profile = new Profile();
    Boolean flag1 = false;
    Boolean flag2 = false;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        getActivity().setTitle("Edit Profile");

        name = view.findViewById(R.id.editName);
        email = view.findViewById(R.id.editEmail);

        imageButton = view.findViewById(R.id.imageButtonInFragment);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData.profilePhotoSelect();
            }
        });

        seekBar = view.findViewById(R.id.seekBarInFragment);
        dynamicView = view.findViewById(R.id.dynamicImageViewInFragment);
        moodResult = view.findViewById(R.id.moodResultInFragment);
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

        radioGroup = view.findViewById(R.id.radioGroupInFragment);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.Android)
                {
                    profile.setDevice("Android");
                    flag2 = true;
                }
                else if(i == R.id.iOS) {
                    profile.setDevice("iOS");
                    flag2 = true;
                }
            }
        });

        submitButton = view.findViewById(R.id.submitButtonInFragment);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "You must input name!", Toast.LENGTH_LONG).show();
                } else if (email.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "You must input email!", Toast.LENGTH_LONG).show();
                } else if (!(email.getText().toString().contains("@")
                        && email.getText().toString().contains(".")
                        && (email.getText().toString().matches(".*[0-9].*")
                        || email.getText().toString().matches(".*[a-zA-z].*")))) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Email format is not correct!", Toast.LENGTH_LONG).show();
                } else if (!flag1) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "You must pick a profile image!", Toast.LENGTH_LONG).show();
                } else if (!flag2) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "You must choose your device!", Toast.LENGTH_LONG).show();
                } else {
                    profile.setName(name.getText().toString());
                    profile.setEmail(email.getText().toString());
                    sendData.finalDisplay(profile);
                }

            }
        });
        return view;
    }

    public void updateValues(int photoID)
    {
        getActivity().setTitle("Edit Profile");
        imageButton = getView().findViewById(R.id.imageButtonInFragment);
        imageButton.setImageResource(photoID);
        profile.setProfilePhotoId(photoID);
        flag1 = true;
    }

    fromMainFragmentToActivity sendData;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof fromMainFragmentToActivity) {
            sendData = (fromMainFragmentToActivity) context;
        }
        else {
            throw new RuntimeException(context.toString() + "Must implement this interface!");
        }
    }

    public interface fromMainFragmentToActivity
    {
        void profilePhotoSelect();
        void finalDisplay(Profile profile);
    }
}