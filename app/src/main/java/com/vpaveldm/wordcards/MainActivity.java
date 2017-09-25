package com.vpaveldm.wordcards;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mManager = getSupportFragmentManager();
        Fragment fragment = mManager.findFragmentById(R.id.container);
        if (fragment == null){
            fragment = new MainActivityFragment();
            mManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }
}