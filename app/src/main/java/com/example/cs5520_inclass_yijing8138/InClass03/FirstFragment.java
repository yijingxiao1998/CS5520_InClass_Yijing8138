/**
 * Name: Yijing Xiao
 * Assignment: InClass 03
 */
package com.example.cs5520_inclass_yijing8138.InClass03;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.cs5520_inclass_yijing8138.R;

public class FirstFragment extends Fragment {
    public FirstFragment() {
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
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        view.setBackgroundColor(Color.WHITE);
        getActivity().setTitle("Select Avatar");

        ImageButton avatarf1, avatarf2, avatarf3, avatarm1, avatarm2, avatarm3;

        avatarf1 = view.findViewById(R.id.imageButton_f1);
        avatarf2 = view.findViewById(R.id.imageButton_f2);
        avatarf3 = view.findViewById(R.id.imageButton_f3);
        avatarm1 = view.findViewById(R.id.imageButton_m1);
        avatarm2 = view.findViewById(R.id.imageButton_m2);
        avatarm3 = view.findViewById(R.id.imageButton_m3);

        avatarf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData.fromFirstFragment(R.drawable.avatar_f_1);
            }
        });
        avatarf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData.fromFirstFragment(R.drawable.avatar_f_2);
            }
        });
        avatarf3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData.fromFirstFragment(R.drawable.avatar_f_3);
            }
        });
        avatarm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData.fromFirstFragment(R.drawable.avatar_m_1);
            }
        });
        avatarm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData.fromFirstFragment(R.drawable.avatar_m_2);
            }
        });
        avatarm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData.fromFirstFragment(R.drawable.avatar_m_3);
            }
        });

        return view;
    }

    fromFirstFragmentToFirstFragment sendData;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof fromFirstFragmentToFirstFragment) {
            sendData = (fromFirstFragmentToFirstFragment) context;
        }
        else {
            throw new RuntimeException(context.toString() + "Must implement this interface!");
        }
    }

    public interface fromFirstFragmentToFirstFragment
    {
        void fromFirstFragment(int photoID);
    }
}