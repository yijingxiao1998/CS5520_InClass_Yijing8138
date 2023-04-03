package com.example.cs5520_inclass_yijing8138.InClass08.model;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cs5520_inclass_yijing8138.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ChatMessageAdaptor extends RecyclerView.Adapter<ChatMessageAdaptor.ViewHolder> {
    private ArrayList<ChatMessage> chatMessageArrayList;
    private FirebaseStorage storage;
    private Context context;
    public ChatMessageAdaptor() {
    }

    public ChatMessageAdaptor(ArrayList<ChatMessage> chatMessages, Context context) {
        chatMessageArrayList = chatMessages;
        this.context = context;
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
        storage = FirebaseStorage.getInstance();
        return new ChatMessageAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageAdaptor.ViewHolder holder, int position) {
        if (chatMessageArrayList.get(position).getIsImageUri().equals("true")){
            holder.getImageView().setVisibility(View.VISIBLE);
            holder.getChatMessage().setVisibility(View.INVISIBLE);
            Uri uri = Uri.parse(chatMessageArrayList.get(position).getMessageText());
            storage.getReference().child("images/" + uri.getLastPathSegment()).getDownloadUrl()
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
//                            holder.getImageView().setImageURI(task.getResult());
                            Glide.with(context)
                                    .load(task.getResult())
                                    .apply(new RequestOptions().override(200, 200))
                                    .centerCrop()
                                    .error(R.drawable.select_avatar)
                                    .into(holder.getImageView());
                        }
                    });
        }
        else {
            holder.getImageView().setVisibility(View.GONE);
            holder.getChatMessage().setVisibility(View.VISIBLE);
            holder.getChatMessage().setText(chatMessageArrayList.get(position).getMessageText());
        }
        holder.getChatFriendName().setText(chatMessageArrayList.get(position).getChatUser());
        holder.getChatTime().setText(chatMessageArrayList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return chatMessageArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView chatFriendName, chatTime, chatMessage;
        private final ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            chatFriendName = itemView.findViewById(R.id.textViewChatPersonName);
            chatTime = itemView.findViewById(R.id.textViewChatTime);
            chatMessage = itemView.findViewById(R.id.textViewChatMessage);
            imageView = itemView.findViewById(R.id.imageView);
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

        public ImageView getImageView() {
            return imageView;
        }
    }
}
