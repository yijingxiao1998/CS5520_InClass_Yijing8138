/**
 * Name: Yijing Xiao
 * Assignment: InClass 03
 */
package com.example.cs5520_inclass_yijing8138.InClass03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import com.example.cs5520_inclass_yijing8138.InClass02.Profile;
import com.example.cs5520_inclass_yijing8138.R;

public class InClass03 extends AppCompatActivity
        implements FirstFragment.fromFirstFragmentToFirstFragment,
        MainFragment.fromMainFragmentToActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class03);
        setTitle("Edit Profile");

        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainFragmentContainer, new MainFragment(), "mainFragment")
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void fromFirstFragment(int photoID) {
        MainFragment fragmentMain = (MainFragment) getSupportFragmentManager()
                .findFragmentByTag("mainFragment");
        fragmentMain.updateValues(photoID);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("fragment1");
        getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void profilePhotoSelect() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainFragmentContainer, new FirstFragment(), "fragment1")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void finalDisplay(Profile profile) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFragmentContainer, new SecondFragment(profile),
                        "fragment2")
                .addToBackStack(null)
                .commit();
    }
}