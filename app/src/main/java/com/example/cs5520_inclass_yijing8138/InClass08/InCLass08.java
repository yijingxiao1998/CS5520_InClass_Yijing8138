package com.example.cs5520_inclass_yijing8138.InClass08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cs5520_inclass_yijing8138.R;
import com.example.cs5520_inclass_yijing8138.InClass08.model.FriendAdaptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InCLass08 extends AppCompatActivity implements LoginPageFragment.loginPage,
        RegisterPageFragment.registerPage, MainPageFragment.mainPage,
        FriendAdaptor.friendAdaptor, ChatPageFragment.chatPage {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class08);
        setTitle("InClass08");

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        populateTheScreen();
    }

    public void populateTheScreen()
    {
        if(currentUser != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, MainPageFragment.newInstance(),
                            "mainFragment")
                    .commit();
        }
        else {
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
        populateTheScreen();;
    }

    @Override
    public void goToLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, LoginPageFragment.newInstance(),
                        "loginFragment")
                .commit();
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
    public void chatWithFriend(String friendEmail) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, ChatPageFragment.newInstance(friendEmail),
                        "chatPageFragment")
                .commit();
    }

    @Override
    public void deleteFriendFromRecycleView(String friendEmail) {
        MainPageFragment mainPageFragment = (MainPageFragment) getSupportFragmentManager()
                .findFragmentByTag("mainFragment");
        mainPageFragment.deleteUser(friendEmail);
    }

    @Override
    public void backToMainPage() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, MainPageFragment.newInstance(),
                        "mainFragment")
                .commit();
    }
}