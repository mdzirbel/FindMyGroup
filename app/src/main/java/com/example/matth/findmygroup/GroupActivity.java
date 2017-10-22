package com.example.matth.findmygroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class GroupActivity extends AppCompatActivity {

    TextView activePlayers;
    TextView inactivePlayers;

    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        activePlayers = (TextView) findViewById(R.id.activePlayers);
        inactivePlayers = (TextView) findViewById(R.id.inactivePlayers);

        try {
            getSupportActionBar().setTitle(MainActivity.groups.get(MainActivity.groupClicked));
        }
        catch (NullPointerException e) {
            Log.d("ERROR",e+"");
        }
        activePlayers.setText("Active Users:\n"+getActivePlayers());
        inactivePlayers.setText("Inactive Users:\n"+getInactivePlayers());

    }
    void leaveGroup(View v) {
        sendLeaveGroup();
        Intent intent = new Intent(GroupActivity.this, MainActivity.class);
        startActivity(intent);
    }

    String getActivePlayers() {
        return "hello";
    }
    String getInactivePlayers() {
        return "hello";
    }
    void sendLeaveGroup() {

    }

}
