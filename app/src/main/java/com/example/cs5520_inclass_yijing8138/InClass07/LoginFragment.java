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
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_EMAIL = "email";
    private static final String ARG_PASSWORD = "password";

    // TODO: Rename and change types of parameters
    private String mParamEmail;
    private String mParamPassword;
    private login mListener;
    public LoginFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        getActivity().setTitle("Login");

        EditText editTextEmail = view.findViewById(R.id.loginEmailAddress);
        EditText editTextPassword = view.findViewById(R.id.loginPassword);
        Button buttonLogin = view.findViewById(R.id.loginButton);
        Button buttonCancel = view.findViewById(R.id.cancelButtonInLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(getContext(), "The email and password cannot be empty!",
                            Toast.LENGTH_SHORT).show();
                }
                if(!email.contains("@") || !email.contains(".com"))
                {
                    Toast.makeText(getContext(), "The email's format is invalid (please input"
                                    + " your email like test@gmail.com!",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    mListener.loginSuccessfully(email, password);
                    editTextEmail.setText("");
                    editTextPassword.setText("");
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelLogin();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof login)
        {
            mListener = (login) context;
        }
        else{
            throw new RuntimeException(context.toString() + " must implement interface!");
        }
    }

    public interface login {
        void loginSuccessfully(String email, String password);
        void requestInfoFromServe(String token);
        void cancelLogin();
    }
}