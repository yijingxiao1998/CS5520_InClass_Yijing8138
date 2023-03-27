package com.example.cs5520_inclass_yijing8138.InClass08.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs5520_inclass_yijing8138.R;

import java.util.ArrayList;

public class ChatMessageAdaptor extends RecyclerView.Adapter<ChatMessageAdaptor.ViewHolder> {
    private ArrayList<ChatMessage> chatMessageArrayList;

    public ChatMessageAdaptor() {
    }

    public ChatMessageAdaptor(ArrayList<ChatMessage> chatMessages, Context context) {
        chatMessageArrayList = chatMessages;
    }

    public ArrayList<ChatMessage> getChatMessageArrayList() {
        return chatMessageArrayList;
    }

    public void setChatMessageArrayList(ArrayList<ChatMessage> chatMessageArrayList) {
        this.chatMessageArrayList = chatMessageArrayList;
    }

    @NonNull
    @Override
    public ChatMessageAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_list, parent, false);
        return new ChatMessageAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageAdaptor.ViewHolder holder, int position) {
        holder.getChatFriendName().setText(chatMessageArrayList.get(position).getChatUser());
        holder.getChatTime().setText(chatMessageArrayList.get(position).getTime());
        holder.getChatMessage().setText(chatMessageArrayList.get(position).getMessageText());
    }

    @Override
    public int getItemCount() {
        return chatMessageArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView chatFriendName, chatTime, chatMessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            chatFriendName = itemView.findViewById(R.id.textViewChatPersonName);
            chatTime = itemView.findViewById(R.id.textViewChatTime);
            chatMessage = itemView.findViewById(R.id.textViewChatMessage);
        }

        public TextView getChatFriendName() {
            return chatFriendName;
        }

        public TextView getChatTime() {
            return chatTime;
        }

        public TextView getChatMessage() {
            return chatMessage;
        }
    }
}
