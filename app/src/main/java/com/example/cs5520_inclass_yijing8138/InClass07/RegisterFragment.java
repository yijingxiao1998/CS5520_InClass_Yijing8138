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
import android.widget.Toast;

import com.example.cs5520_inclass_yijing8138.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private register mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        getActivity().setTitle("Register");

        EditText editTextName = view.findViewById(R.id.registerPersonName);
        EditText editTextEmail = view.findViewById(R.id.registerEmailAddress);
        EditText editTextPassword = view.findViewById(R.id.registerPassword);
        Button buttonRegister = view.findViewById(R.id.registerButton);
        Button buttonCancel = view.findViewById(R.id.cancelButtonInRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                if(name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getContext(), "The name or email or password could not be empty!",
                            Toast.LENGTH_SHORT).show();
                }
                if(!email.contains("@") || !email.contains(".com"))
                {
                    Toast.makeText(getContext(), "The email's format is invalid (please input"
                                    + " your email like test@gmail.com!",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    mListener.registerSuccessfully(name, email, password);
                    editTextName.setText("");
                    editTextEmail.setText("");
                    editTextPassword.setText("");
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelRegister();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof register)
        {
            mListener = (register) context;
        }
        else{
            throw new RuntimeException(context.toString() + " must implement interface!");
        }
    }

    public interface register {
        void registerSuccessfully(String name, String email, String password);
        void cancelRegister();
    }
}