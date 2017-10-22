package com.example.matth.findmygroup;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
public class SettingsActivity extends AppCompatActivity {

    private static final String ACCOUNT_INFO = "accountInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String acctInfo = prefs.getString(ACCOUNT_INFO, null);

        String[] listItems = new String[0];
        if (acctInfo != null && acctInfo.contains("%")) {
            listItems = acctInfo.split("%");
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.activity_settings_listview, listItems);

        ListView listView = (ListView) findViewById(R.id.profile_info);
        listView.setAdapter(adapter);
    }
}
