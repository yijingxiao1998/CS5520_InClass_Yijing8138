package com.example.cs5520_inclass_yijing8138.InClass07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs5520_inclass_yijing8138.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "id";
    private static final String ARG_NAME = "name";
    private static final String ARG_EMAIL = "email";

    // TODO: Rename and change types of parameters
    private String mParamID = null;
    private String mParamName = null;
    private String mParamEmail = null;
    private meFragmentShowing mListener;
    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment newInstance(String id, String name, String email) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        args.putString(ARG_NAME, name);
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if(getArguments().containsKey(ARG_ID)) {
                mParamID = getArguments().getString(ARG_ID);
                mParamName = getArguments().getString(ARG_NAME);
                mParamEmail = getArguments().getString(ARG_EMAIL);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        getActivity().setTitle("Note");

        Button buttonLogOut = view.findViewById(R.id.buttonLogOut);
        Button buttonPost = view.findViewById(R.id.buttonPostNewNote);
        TextView id = view.findViewById(R.id.textViewIDResult);
        TextView name = view.findViewById(R.id.textViewNameResult);
        TextView email = view.findViewById(R.id.textViewEmailResult);
        EditText editTextNote = view.findViewById(R.id.editTextTextMultiLine);

        if(mParamID != null && mParamName != null && mParamEmail != null) {
            id.setText(mParamID);
            name.setText(mParamName);
            email.setText(mParamEmail);
        }

        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNote = editTextNote.getText().toString();
                if(newNote.isEmpty())
                {
                    Toast.makeText(getContext(), "The new post cannot be empty!",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    mListener.postButtonClicked(newNote);
                    Toast.makeText(getContext(), "New note post successfully!",
                            Toast.LENGTH_SHORT).show();

                    editTextNote.setText("");
                }
            }
        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.requestLogOut();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof meFragmentShowing)
        {
            mListener = (meFragmentShowing) context;
        }
        else{
            throw new RuntimeException(context.toString() + " must implement interface!");
        }
    }

    public interface meFragmentShowing{
        void requestLogOut();
        void postButtonClicked(String note);
    }
}