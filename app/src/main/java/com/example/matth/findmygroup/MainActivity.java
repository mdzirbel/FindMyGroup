package com.example.matth.findmygroup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static RelativeLayout groupLayout;
    static RelativeLayout compassLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_groups:
                    hideCompass();
                    showGroups();
                    return true;
                case R.id.navigation_compass:
                    hideGroups();
                    showCompass();
                    return true;
            }
            return false;
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        groupLayout = (RelativeLayout) findViewById(R.id.group);
        compassLayout = (RelativeLayout) findViewById(R.id.compass);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    static void hideCompass() {
        compassLayout.setVisibility(View.GONE);
    }
    static void hideGroups() {
        groupLayout.setVisibility(View.GONE);
    }
    static void showCompass() {
        compassLayout.setVisibility(View.VISIBLE);
    }
    static void showGroups() {
        groupLayout.setVisibility(View.VISIBLE);
    }

}
