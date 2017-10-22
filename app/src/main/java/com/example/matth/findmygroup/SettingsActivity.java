package com.example.matth.findmygroup;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

        Button btn = (Button)findViewById(R.id.locBtn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.locBtn:
                getLocation();
        }
    }

    public void getLocation() {
        Context context = this;
        location = new SimpleLocation(context, false);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double altitude = location.getAltitude();

        String s = "Lat = " + String.valueOf(latitude) + ";  " +
                "Long = " + String.valueOf(longitude) + ";  " +
                "Altitude = " + String.valueOf(altitude);

        Toast t = Toast.makeText(this, s, Toast.LENGTH_LONG);
        t.show();
    }

    private void makeList() {
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.activity_settings_listview, listItems);

        ListView listView = findViewById(R.id.profile_info);
        listView.setAdapter(adapter);
    }
}
