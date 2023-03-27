package com.example.cs5520_inclass_yijing8138.InClass08.model;

import java.io.Serializable;

public class Friend implements Serializable {
    private String name;
    private String email;

    public Friend() {
    }

    public Friend(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
