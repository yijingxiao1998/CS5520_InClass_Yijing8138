package com.example.cs5520_inclass_yijing8138.InClass08.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs5520_inclass_yijing8138.R;

import java.util.ArrayList;

public class FriendAdaptor extends RecyclerView.Adapter<FriendAdaptor.ViewHolder> {
    private ArrayList<Friend> friends;
    private friendAdaptor friendAdaptorListener;

    public FriendAdaptor() {
    }

    public FriendAdaptor(ArrayList<Friend> friends, Context context) {
        this.friends = friends;
        if(context instanceof friendAdaptor){
            friendAdaptorListener = (friendAdaptor) context;
        }
        else{
            throw new RuntimeException(context.toString() + " must implement interface!");
        }
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    @NonNull
    @Override
    public FriendAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_list, parent, false);
        return new FriendAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdaptor.ViewHolder holder, int position) {
        String name = friends.get(position).getName();
        String email = friends.get(position).getEmail();
        holder.getFriendName().setText(name);
        holder.getFriendEmail().setText(email);
        holder.getChatButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendAdaptorListener.chatWithFriend(email);
            }
        });
        holder.getDeleteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendAdaptorListener.deleteFriendFromRecycleView(email);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView friendName, friendEmail;
        private final Button chatButton, deleteButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            friendName = itemView.findViewById(R.id.textViewFriendName);
            friendEmail = itemView.findViewById(R.id.textViewFriendEmail);
            chatButton = itemView.findViewById(R.id.chatButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public TextView getFriendName() {
            return friendName;
        }

        public TextView getFriendEmail() {
            return friendEmail;
        }

        public Button getChatButton() {
            return chatButton;
        }

        public Button getDeleteButton() {
            return deleteButton;
        }
    }

    public interface friendAdaptor{
        void chatWithFriend(String friendEmail);
        void deleteFriendFromRecycleView(String friendEmail);
    }
}
