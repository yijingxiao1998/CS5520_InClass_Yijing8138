/**
 * Name: Yijing Xiao
 * Assignment: InClass 03
 */
package com.example.cs5520_inclass_yijing8138.InClass03;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cs5520_inclass_yijing8138.InClass02.Profile;
import com.example.cs5520_inclass_yijing8138.R;

public class SecondFragment extends Fragment {
    private String name, email, device, moodResult;
    private TextView nameText, emailText, moodText, getDevice;
    private int profileID;
    private ImageView profilePhoto, moodView;

    public SecondFragment() {
        // Required empty public constructor
    }

    public SecondFragment(Profile profile)
    {
        name = profile.getName();
        email = profile.getEmail();
        profileID = profile.getProfilePhotoId();
        device = profile.getDevice();
        moodResult = profile.getMoodResult();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        getActivity().setTitle("Display Activity");

        profilePhoto = view.findViewById(R.id.profilePhotoInFragment);
        profilePhoto.setImageResource(profileID);

        getDevice = view.findViewById(R.id.deviceTextViewInFragment);
        getDevice.setText(device);

        nameText = view.findViewById(R.id.nameTextViewInFragment);
        nameText.setText(name);

        emailText = view.findViewById(R.id.emailTextViewInFragment);
        emailText.setText(email);

        moodText = view.findViewById(R.id.moodInFragment);
        moodText.setText(moodResult);

        moodView = view.findViewById(R.id.moodPhotoInFragment);

        switch (moodResult) {
            case "Angry":
                moodView.setImageResource(R.drawable.angry);
                break;
            case "Sad":
                moodView.setImageResource(R.drawable.sad);
                break;
            case "Happy":
                moodView.setImageResource(R.drawable.happy);
                break;
            case "Awesome":
                moodView.setImageResource(R.drawable.awesome);
                break;
        }
        return view;
    }
}