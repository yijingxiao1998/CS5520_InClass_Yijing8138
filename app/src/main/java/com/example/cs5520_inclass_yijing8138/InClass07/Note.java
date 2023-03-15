package com.example.cs5520_inclass_yijing8138.InClass07;

import java.io.Serializable;

public class Note implements Serializable {
    private String note;
    public Note() {
    }

    public Note(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
