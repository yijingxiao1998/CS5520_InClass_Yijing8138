package com.example.cs5520_inclass_yijing8138.InClass08;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs5520_inclass_yijing8138.R;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayImageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_URI = "imageUri";
    private Uri imageUri;
    private ImageView imageViewPhoto;
    private Button buttonRetake;
    private Button buttonUpload;
    private RetakePhoto mListener;
    private ProgressBar progressBar;

    public DisplayImageFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DisplayImageFragment newInstance(Uri imageUri) {
        DisplayImageFragment fragment = new DisplayImageFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_URI, imageUri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUri = getArguments().getParcelable(ARG_URI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_display_image, container, false);
//        ProgressBar setup init.....
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        imageViewPhoto = view.findViewById(R.id.imageViewPhoto);
        buttonRetake = view.findViewById(R.id.buttonRetake);
        buttonUpload = view.findViewById(R.id.buttonUpload);
        Glide.with(view)
                .load(imageUri)
                .centerCrop()
                .into(imageViewPhoto);

        buttonRetake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onRetakePressed();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onUploadButtonPressed(imageUri, progressBar);
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof TakePhotoFragment.DisplayTakenPhoto){
            mListener = (RetakePhoto) context;
        }else{
            throw new RuntimeException(context+" must implement RetakePhoto");
        }
    }

    public interface RetakePhoto{
        void onRetakePressed();

        void onUploadButtonPressed(Uri imageUri, ProgressBar progressBar);
    }
}