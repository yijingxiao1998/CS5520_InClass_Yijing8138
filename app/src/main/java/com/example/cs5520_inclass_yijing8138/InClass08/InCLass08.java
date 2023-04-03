package com.example.cs5520_inclass_yijing8138.InClass08;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cs5520_inclass_yijing8138.R;
import com.example.cs5520_inclass_yijing8138.InClass08.model.FriendAdaptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class InCLass08 extends AppCompatActivity implements LoginPageFragment.loginPage,
        RegisterPageFragment.registerPage, MainPageFragment.mainPage,
        FriendAdaptor.friendAdaptor, ChatPageFragment.chatPage {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private String chatFriend = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class08);
        setTitle("InClass08 & InClass09");

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        populateTheScreen();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 2) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, TakePhotoFragment.newInstance(), "cameraFragment")
                    .commit();
        } else {
            Toast.makeText(this, "You must allow Camera and Storage permissions!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void populateTheScreen() {
        if (currentUser != null && currentUser.getPhotoUrl() != null) {
            if(chatFriend != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        ChatPageFragment.newInstance(chatFriend), "chatPageFragment")
                        .commit();
            }
            else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, MainPageFragment.newInstance(),
                                "mainFragment")
                        .commit();
            }

        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, LoginPageFragment.newInstance(),
                            "loginFragment")
                    .commit();
        }
    }

    @Override
    public void goToRegister() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, RegisterPageFragment.newInstance(),
                        "registerFragment")
                .commit();
    }

    @Override
    public void clickLoginButton(FirebaseUser firebaseUser) {
        this.currentUser = firebaseUser;
        populateTheScreen();
    }

    @Override
    public void goToLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, LoginPageFragment.newInstance(),
                        "loginFragment")
                .commit();
    }

    ActivityResultLauncher<Intent> startActivity
            = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        //imageUri
                        Intent data = result.getData();
                        Uri selectedImageUri = Uri.parse(data.getStringExtra("imageUri"));
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(selectedImageUri)
                                .build();
                        firebaseAuth.getCurrentUser().updateProfile(userProfileChangeRequest);
                    }
                }
            });

    @Override
    public void takeProfilePhoto() {
        Intent toInClass09 = new Intent(InCLass08.this, InClass09.class);
        startActivity.launch(toInClass09);
    }

    @Override
    public void clickRegisterButton(FirebaseUser firebaseUser) {
        currentUser = firebaseUser;
        populateTheScreen();
    }

    @Override
    public void logout() {
        firebaseAuth.signOut();
        currentUser = null;
        populateTheScreen();
    }

    @Override
    public void changeProfilePhoto() {
        takeProfilePhoto();
    }

    @Override
    public void chatWithFriend(String friendEmail) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, ChatPageFragment.newInstance(friendEmail),
                        "chatPageFragment")
                .addToBackStack("chatPageFragment")
                .commit();
        this.chatFriend = friendEmail;
    }

    @Override
    public void deleteFriendFromRecycleView(String friendEmail) {
        MainPageFragment mainPageFragment = (MainPageFragment) getSupportFragmentManager()
                .findFragmentByTag("mainFragment");
        mainPageFragment.deleteUser(friendEmail);
    }

    @Override
    public void backToMainPage() {
        this.chatFriend = null;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, MainPageFragment.newInstance(),
                        "mainFragment")
                .commit();
    }
}