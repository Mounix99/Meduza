package com.meduza.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.meduza.application.fragments.Desk;
import com.meduza.application.fragments.Dialogs;
import com.meduza.application.fragments.Friends;
import com.meduza.application.fragments.Profile;

public class Navigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        BottomNavigationView bottomNav = findViewById(R.id.nav_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Dialogs()).commit();


    }

    public BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.desk_tab:
                            selectedFragment = new Desk();
                            break;
                        case R.id.friend_desk_tab:
                            selectedFragment = new Friends();
                            break;
                        case R.id.profile_tab:
                            selectedFragment = new Profile();
                            break;
                        case R.id.dialogs_tab:
                            selectedFragment = new Dialogs();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
