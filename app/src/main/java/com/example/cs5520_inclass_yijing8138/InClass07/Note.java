package com.example.cs5520_inclass_yijing8138.InClass07;

import java.io.Serializable;

public class Note implements Serializable {
    private String note;
    private String noteID;

    public Note() {
    }

    public Note(String note, String noteID) {
        this.note = note;
        this.noteID = noteID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNoteID() {
        return noteID;
    }

    public void setNoteID(String noteID) {
        this.noteID = noteID;
    }
}
