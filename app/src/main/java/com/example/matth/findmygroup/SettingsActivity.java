package com.example.matth.findmygroup;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import im.delight.android.location.SimpleLocation;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String ACCOUNT_INFO = "accountInfo";
    private SimpleLocation location;
    private String[] listItems = new String[0];
    private int LISTLENGTH = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String acctInfo = prefs.getString(ACCOUNT_INFO, null);

        if (acctInfo != null && acctInfo.contains("%")) {
            listItems = acctInfo.split("%");
        }

        LISTLENGTH = listItems.length;

        makeList();

        location = new SimpleLocation(this);

        findViewById(R.id.locBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double altitude = location.getAltitude();

        listItems[LISTLENGTH] = String.valueOf(latitude);
        listItems[LISTLENGTH + 1] = String.valueOf(longitude);
        listItems[LISTLENGTH + 2] = String.valueOf(altitude);

        makeList();

    }

    private void makeList() {
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.activity_settings_listview, listItems);

        ListView listView = findViewById(R.id.profile_info);
        listView.setAdapter(adapter);
    }
}
