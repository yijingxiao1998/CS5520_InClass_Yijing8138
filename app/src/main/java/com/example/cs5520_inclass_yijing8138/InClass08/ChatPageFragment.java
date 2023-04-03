package com.example.cs5520_inclass_yijing8138.InClass08;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import com.example.cs5520_inclass_yijing8138.R;
import com.example.cs5520_inclass_yijing8138.InClass08.model.ChatMessage;
import com.example.cs5520_inclass_yijing8138.InClass08.model.ChatMessageAdaptor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CHAT_MESSAGE = "chat_message";
    private static final String ARG_FRIEND_EMAIL = "friend_email";
    private EditText inputMessage;
    private Button sendImageButton, postButton;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager recyclerViewManger;
    private ChatMessageAdaptor chatMessageAdaptor;
    private Button backButton;
    private ArrayList<ChatMessage> chatMessageArrayList;
    private String chatFriendEmail;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private chatPage chatPageListener;
    public ChatPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatPageFragment newInstance(String chatFriendEmail) {
        ChatPageFragment fragment = new ChatPageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CHAT_MESSAGE, new ArrayList<ChatMessage>());
        args.putString(ARG_FRIEND_EMAIL, chatFriendEmail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if(getArguments().containsKey(ARG_CHAT_MESSAGE)) {
                chatMessageArrayList = (ArrayList<ChatMessage>) getArguments().getSerializable(ARG_CHAT_MESSAGE);
            }
            chatFriendEmail = getArguments().getString(ARG_FRIEND_EMAIL);
            firebaseAuth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
            loadMessage();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof chatPage){
            chatPageListener = (chatPage) context;
        }
        else{
            throw new RuntimeException(context.toString() + " must implement interface!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_page, container, false);

        inputMessage = view.findViewById(R.id.messageMultiLine);
        postButton = view.findViewById(R.id.sendMessageButton);
        backButton = view.findViewById(R.id.backToMainPageButton);
        recyclerView = view.findViewById(R.id.recyclerViewOfChatPage);
        sendImageButton = view.findViewById(R.id.sendImageButton);

        // Set up recyclerView
        recyclerViewManger = new LinearLayoutManager(getContext());
        chatMessageAdaptor = new ChatMessageAdaptor(chatMessageArrayList, getContext());
        recyclerView.setLayoutManager(recyclerViewManger);
        recyclerView.setAdapter(chatMessageAdaptor);

        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                chatPageListener.sendImageButtonClicked();
//                sendImageButtonClicked();
                Intent toInClass09 = new Intent(getActivity(), InClass09.class);
                startToActivity.launch(toInClass09);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = inputMessage.getText().toString();
                if(!message.isEmpty()) {
                    ChatMessage chatMessage = new ChatMessage(message,
                            firebaseAuth.getCurrentUser().getDisplayName(),
                            String.valueOf(chatMessageArrayList.size() + 1), "false");
                    postMessage(chatMessage);
                    inputMessage.setText("");
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatPageListener.backToMainPage();
            }
        });

        // Create a listener for firebase data change
        db.collection("users")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("friends")
                .document(chatFriendEmail)
                .collection("message")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        // Retrieving all the elements from firebase
                        if(error == null) {
                            ArrayList<ChatMessage> chatMessages = new ArrayList<>();
                            for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                                chatMessages.add(documentSnapshot.toObject(ChatMessage.class));
                            }
                            // Replace all the items in the current recycleView with the received elements
                            chatMessageAdaptor.setChatMessageArrayList(chatMessages);
                            chatMessageAdaptor.notifyDataSetChanged();
                            updateRecyclerView(chatMessages);
                        }
                    }
                });

        return view;
    }

    public void postMessage(ChatMessage chatMessage){
        db.collection("users")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("friends")
                .document(chatFriendEmail)
                .collection("message")
                .document(String.valueOf(chatMessage.getNumber()))
                .set(chatMessage);

        db.collection("users")
                .document(chatFriendEmail)
                .collection("friends")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("message")
                .document(String.valueOf(chatMessage.getNumber()))
                .set(chatMessage);
    }

    public void loadMessage(){
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        db.collection("users")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("friends")
                .document(chatFriendEmail)
                .collection("message")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                ChatMessage chatMessage = queryDocumentSnapshot.toObject(ChatMessage.class);
                                chatMessages.add(chatMessage);
                            }
                            updateRecyclerView(chatMessages);
                        }
                    }
                });
    }

    public void updateRecyclerView(ArrayList<ChatMessage> chatMessages){
        chatMessageArrayList = chatMessages;
        chatMessageAdaptor.notifyDataSetChanged();
    }

    ActivityResultLauncher<Intent> startToActivity
            = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK)
                    {
                        //imageUri
                        Intent data = result.getData();
                        String selectedImageUri = data.getStringExtra("imageUri");
                        ChatMessage chatMessage = new ChatMessage(selectedImageUri,
                                firebaseAuth.getCurrentUser().getDisplayName(),
                                String.valueOf(chatMessageArrayList.size() + 1), "true");
                        postMessage(chatMessage);
                    }
                }
            });

    public interface chatPage{
        void backToMainPage();
    }
}