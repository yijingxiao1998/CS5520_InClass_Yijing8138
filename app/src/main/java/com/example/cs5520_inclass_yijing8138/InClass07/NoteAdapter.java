package com.example.cs5520_inclass_yijing8138.InClass07;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs5520_inclass_yijing8138.R;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private ArrayList<Note> notes;
    private Boolean isEmpty = false;
    private delete mListener;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewNoteNumber, textViewNoteContents, textViewNote;
        private final Button buttonToDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNoteNumber = itemView.findViewById(R.id.textViewNoteNumber);
            textViewNoteContents = itemView.findViewById(R.id.textViewNoteContents);
            textViewNote = itemView.findViewById(R.id.textViewNote);
            buttonToDelete = itemView.findViewById(R.id.buttonToDelete);
        }

        public TextView getTextViewNoteNumber() {
            return textViewNoteNumber;
        }

        public TextView getTextViewNoteContents() {
            return textViewNoteContents;
        }

        public TextView getTextViewNote() {
            return textViewNote;
        }

        public Button getButtonToDelete() {
            return buttonToDelete;
        }
    }

    public NoteAdapter(ArrayList<Note> notes, Context context) {
        this.notes = notes;
        if(context instanceof delete){
            mListener = (delete) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement interface");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("demo", "onBindViewHolder: " + notes);
        holder.getTextViewNote().setVisibility(View.VISIBLE);
        holder.getTextViewNoteContents().setVisibility(View.VISIBLE);
        holder.getTextViewNoteNumber().setVisibility(View.VISIBLE);
        holder.getButtonToDelete().setVisibility(View.VISIBLE);

        holder.getTextViewNoteNumber().setText(String.valueOf(position));
        holder.getTextViewNoteContents().setText(notes.get(position).getNote());
        holder.getButtonToDelete().setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                mListener.deleteNote(notes.get(holder.getAdapterPosition()));
                notes.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(isEmpty)
        {
            return 1;
        }
        else {
            return notes.size();
        }
    }

    public interface delete {
        void deleteNote(Note note);
    }
}
