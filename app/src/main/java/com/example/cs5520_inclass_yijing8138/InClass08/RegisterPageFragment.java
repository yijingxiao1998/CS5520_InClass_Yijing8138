package com.example.cs5520_inclass_yijing8138.InClass08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs5520_inclass_yijing8138.InClass08.model.Friend;
import com.example.cs5520_inclass_yijing8138.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterPageFragment extends Fragment {
    private EditText userName, registerEmail, registerPassword;
    private TextView textViewToLogin;
    private Button buttonNextStep;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    private registerPage registerPageListener;

    public RegisterPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RegisterPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterPageFragment newInstance() {
        RegisterPageFragment fragment = new RegisterPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof registerPage)
        {
            registerPageListener = (registerPage) context;
        }
        else{
            throw new RuntimeException(context.toString() + " must implement interface!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_page, container, false);

        getActivity().setTitle("Register");

        userName = view.findViewById(R.id.personUserName);
        registerEmail = view.findViewById(R.id.editTextRegisterByEmailAddress);
        registerPassword = view.findViewById(R.id.editTextRegisterPassword);
        textViewToLogin = view.findViewById(R.id.textViewHintToLogin);
        buttonNextStep = view.findViewById(R.id.nextStepButton);

        textViewToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPageListener.goToLogin();
            }
        });

        buttonNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString();
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(getContext(), "The input user name cannot be empty!",
                            Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(getContext(), "The input email cannot be empty!",
                            Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(getContext(), "The input password cannot be empty!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        firebaseUser = task.getResult().getUser();

                                        // Put a default note.
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("default", null);
                                        db.collection("users")
                                                .document(firebaseUser.getEmail())
                                                .set(hashMap);

                                        // Add user's information into firebase.
                                        db.collection("users")
                                                .document(firebaseUser.getEmail())
                                                .collection("me")
                                                .document(firebaseUser.getEmail())
                                                .set(new Friend(name, firebaseUser.getEmail()));

                                        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    ArrayList<String> arrayList = new ArrayList<>();
                                                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                                        arrayList.add(queryDocumentSnapshot.getId());
                                                    }
                                                    addAllUsersAsFriends(arrayList);
                                                }
                                            }
                                        });

                                        registerPageListener.takeProfilePhoto();

                                        UserProfileChangeRequest userProfileChangeRequest
                                                = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(name)
                                                .setPhotoUri(firebaseUser.getPhotoUrl())
                                                .build();

                                        firebaseUser.updateProfile(userProfileChangeRequest)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            registerPageListener.clickRegisterButton(firebaseUser);
                                                        }
                                                    }
                                                });

                                    }
                                }
                            });
                }
            }
        });

        return view;
    }

    private void addAllUsersAsFriends(ArrayList<String> arrayList){
        for(String friendEmail : arrayList) {
            if(!Objects.equals(friendEmail, firebaseUser.getEmail())) {
                db.collection("users")
                        .document(friendEmail).collection("me")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                ArrayList<Friend> newFriends = new ArrayList<>();
                                for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                                    db.collection("users").document(firebaseUser.getEmail())
                                                .collection("friends")
                                                .document(friendEmail)
                                                .set(documentSnapshot.toObject(Friend.class));
                                }
                            }
                        });

                db.collection("users")
                        .document(friendEmail)
                        .collection("friends")
                        .document(firebaseUser.getEmail())
                        .set(new Friend(firebaseUser.getDisplayName(),
                                firebaseUser.getEmail()));
            }

        }
    }

    public interface registerPage{
        void goToLogin();
        void takeProfilePhoto();
        void clickRegisterButton(FirebaseUser firebaseUser);
    }
}