package com.example.matth.findmygroup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class GroupActivity extends AppCompatActivity {

    Toolbar mActionBarToolbar;
    TextView activePlayers;
    TextView inactivePlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        activePlayers = (TextView) findViewById(R.id.activePlayers);
        inactivePlayers = (TextView) findViewById(R.id.inactivePlayers);

        try {
            getSupportActionBar().setTitle(MainActivity.roleClicked);
        }
        catch (NullPointerException e) {
            Log.d("ERROR",e+"");
        }
        activePlayers.setText("Active Users:\n"+getActivePlayers());
        inactivePlayers.setText("Inactive Users:\n"+getInactivePlayers());

    }

    String getActivePlayers() {
        return "hello";
    }
    String getInactivePlayers() {
        return "hello";
    }

}
