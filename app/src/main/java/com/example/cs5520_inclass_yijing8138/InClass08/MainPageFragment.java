
package com.example.cs5520_inclass_yijing8138.InClass08;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs5520_inclass_yijing8138.R;
import com.example.cs5520_inclass_yijing8138.InClass08.model.Friend;
import com.example.cs5520_inclass_yijing8138.InClass08.model.FriendAdaptor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_FRIENDS = "friendsArray";

    // TODO: Rename and change types of parameters
    private TextView title;
    private EditText newFriendName, newFriendEmail;
    private Button addNewFriendButton, logoutButton;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager recyclerViewManger;
    private ArrayList<Friend> friendArrayList;
    private FriendAdaptor friendAdaptor;
    private mainPage mainPageListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;
    private ArrayList<String> existUser = new ArrayList<>();
    public MainPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChatPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainPageFragment newInstance() {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FRIENDS, new ArrayList<Friend>());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof mainPage){
            mainPageListener = (mainPage) context;
        }
        else{
            throw new RuntimeException(context.toString() + " must implement interface!");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if(getArguments().containsKey(ARG_FRIENDS)) {
                friendArrayList = (ArrayList<Friend>) getArguments().getSerializable(ARG_FRIENDS);
            }
            firebaseAuth = FirebaseAuth.getInstance();
            currentUser = firebaseAuth.getCurrentUser();
            db = FirebaseFirestore.getInstance();

            loadData();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        title = view.findViewById(R.id.userNameShowInTitle);
        newFriendName = view.findViewById(R.id.newFriendUserName);
        newFriendEmail = view.findViewById(R.id.editTextNewFriendEmail);
        addNewFriendButton = view.findViewById(R.id.addNewFriendButton);
        logoutButton = view.findViewById(R.id.logoutButton);
        recyclerView = view.findViewById(R.id.recycleViewOfFriend);

        // Set the welcome title
        title.setText("Welcome " + firebaseAuth.getCurrentUser().getDisplayName());

        // Set up recyclerView
        recyclerViewManger = new LinearLayoutManager(getContext());
        friendAdaptor = new FriendAdaptor(friendArrayList, getContext());
        recyclerView.setLayoutManager(recyclerViewManger);
        recyclerView.setAdapter(friendAdaptor);

        // Add a new friend button listener
        addNewFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = newFriendName.getText().toString();
                String email = newFriendEmail.getText().toString();

                if(name.isEmpty() || email.isEmpty()){
                    Toast.makeText(getContext(), "Friend's name and email cannot be empty!",
                            Toast.LENGTH_SHORT).show();
                } else if (email.equals(currentUser.getEmail())) {
                    Toast.makeText(getContext(), "You cannot add yourself as a friend!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<String> arrayList = new ArrayList<>();
                                for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                    arrayList.add(queryDocumentSnapshot.getId());
                                }
                                Friend friend = new Friend(name, email);
                                addNewFriendToFirebase(friend, arrayList.contains(email));
                            }
                        }
                    });
                }
                clearFields();
            }
        });

        // Logout button listener
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPageListener.logout();
            }
        });

        // Create a listener for firebase data change
        db.collection("users")
                .document(currentUser.getEmail())
                .collection("friends")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error == null) {
                            // Retrieving all the elements from firebase
                            ArrayList<Friend> newFriends = new ArrayList<>();
                            for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                                newFriends.add(documentSnapshot.toObject(Friend.class));
                            }
                            // Replace all the items in the current recycleView with the received elements
                            friendAdaptor.setFriends(newFriends);
                            friendAdaptor.notifyDataSetChanged();
                        }
                    }
                });

        return view;
    }

    /**
     * Add the new friend to firebase cloud FirebaseStore.
     * @param friend the new friend.
     */
    public Boolean addNewFriendToFirebase(Friend friend, Boolean isExist){
        if(isExist) {
            db.collection("users")
                    .document(currentUser.getEmail())
                    .collection("friends")
                    .document(friend.getEmail())
                    .set(friend)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Friend added!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Failed to add a friend!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            db.collection("users")
                    .document(friend.getEmail())
                    .collection("friends")
                    .document(currentUser.getEmail())
                    .set(new Friend(currentUser.getDisplayName(), currentUser.getEmail()));
            return true;
        }
        else {
            Toast.makeText(getContext(), "This user is not registered!",
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     * Clear the fields.
     */
    public void clearFields(){
        newFriendName.setText("");
        newFriendEmail.setText("");
    }

    /**
     * Load friends array from database.
     */
    public void loadData(){
        ArrayList<Friend> friends = new ArrayList<>();
        db.collection("users")
                .document(currentUser.getEmail())
                .collection("friends")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                Friend friend = queryDocumentSnapshot.toObject(Friend.class);
                                friends.add(friend);
                            }
                            updateRecyclerView(friends);
                        }
                    }
                });
    }

    /**
     * Update the recyclerView when something gets changed.
     * @param friends new arraylist of friends.
     */
    public void updateRecyclerView(ArrayList<Friend> friends){
        friendArrayList = friends;
        friendAdaptor.notifyDataSetChanged();
    }

    public void deleteUser(String friendEmail){
        db.collection("users")
                .document(currentUser.getEmail())
                .collection("friends")
                .document(friendEmail)
                .delete();
        db.collection("users")
                .document(friendEmail)
                .collection("friends")
                .document(currentUser.getEmail())
                .delete();
    }

    public interface mainPage {
        void logout();
    }
}