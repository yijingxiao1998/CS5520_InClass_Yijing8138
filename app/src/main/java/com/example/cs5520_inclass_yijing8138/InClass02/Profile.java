/**
 * Name: Yijing Xiao
 * Assignment: InClass 02
 */
package com.example.cs5520_inclass_yijing8138.InClass02;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class Profile implements Serializable {
    int profilePhotoId;
    String name, email, device, moodResult;

    public Profile(int profilePhoto, String name, String email, String device, String moodResult) {
        this.profilePhotoId = profilePhoto;
        this.name = name;
        this.email = email;
        this.device = device;
        this.moodResult = moodResult;
    }

    public Profile() {
    }

    public int getProfilePhotoId() {
        return profilePhotoId;
    }

    public void setProfilePhotoId(int profilePhotoId) {
        this.profilePhotoId = profilePhotoId;
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

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getMoodResult() {
        return moodResult;
    }

    public void setMoodResult(String moodResult) {
        this.moodResult = moodResult;
    }
}
