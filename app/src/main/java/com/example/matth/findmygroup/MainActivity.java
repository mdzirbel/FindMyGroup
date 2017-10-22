package com.example.matth.findmygroup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static RelativeLayout groupLayout;
    static RelativeLayout compassLayout;

    static int groupClicked;
    static Map<Integer, String> groups;
    static Map<Integer, Boolean> groupActivity  = new HashMap<Integer, Boolean>();

    int width;
    int height;

    String red = "#ff0000";
    String green = "#00ff00";

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

    // Stops the back button
    @Override
    public void onBackPressed() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        groupLayout = (RelativeLayout) findViewById(R.id.group);
        compassLayout = (RelativeLayout) findViewById(R.id.compass);

        groups = getGroups();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        makeGroups(groups);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate our menu from the resources by using the menu inflater.
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                result = true;
        }
        return result;
    }

    class toggleClick implements View.OnClickListener {
        int id;
        public toggleClick(int id)
        {
            this.id = id;
        }
        @Override
        public void onClick(View v) {
            boolean active = groupActivity.get(id);
            if (active) {
                sendActive(id, false);
                v.setBackgroundColor(Color.parseColor(red));
            }
            else {
                sendActive(id, true);
                v.setBackgroundColor(Color.parseColor(green));
            }
            groupActivity.put(id, !active);
        }
    }
    class groupClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            groupClicked = (int) findViewById(view.getId()).getTag();
            Intent intent = new Intent(MainActivity.this, GroupActivity.class);
            startActivity(intent);
        }
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

    void makeGroups(Map<Integer, String> groups) {
        for (int i=1;i<=groups.size();i++) {
            String name = (String) groups.values().toArray()[i-1];
            Integer id = (Integer) groups.keySet().toArray()[i-1];
            makeGroup(name,id,i);
        }
    }
    void makeGroup(String name, int id, int number) {
        // IDs are: groupLayout=number*10, groupName=number*10+1, groupActive=number*10+2 spacerBar=number*10+3, toggle=number*10+4
        // number indexes from 1 to prevent overlap with other 0 indexes
        RelativeLayout groupLayout = makeGroupLayout(number, id);
        makeGroupName(name, number, groupLayout);
        makeGroupPeople(number, groupLayout);
        makeSpacer(number, groupLayout);
        makeToggle(number, id);
    }
    RelativeLayout makeGroupLayout(int number, int id) {
        RelativeLayout newGroupLayout = new RelativeLayout(this);
        RelativeLayout groupsView = (RelativeLayout) findViewById(R.id.groups_view);
        newGroupLayout.setId(number*10);
        newGroupLayout.setTag(id);
        newGroupLayout.setOnClickListener(new groupClick());
        groupsView.addView(newGroupLayout);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) newGroupLayout.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, (number-1)*10);
        params.topMargin = 10;
        params.leftMargin = 12;
        params.rightMargin = 12;
        params.width = (int) (width *.75);
        return newGroupLayout;
    }
    void makeGroupName(String name, int number, RelativeLayout groupLayout) {
        TextView groupName = new TextView(this);
        groupName.setId(number*10+1);
        groupName.setTextSize(22);
        groupName.setText(name);
        groupLayout.addView(groupName);
    }
    void makeGroupPeople(int number, RelativeLayout groupLayout) {
        TextView groupActive = new TextView(this);
        groupActive.setId(number*10+2);
        groupActive.setTextSize(15);
        groupActive.setText("Loading Active Members...");
        groupLayout.addView(groupActive);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) groupActive.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, number*10+1);
    }
    void makeSpacer(int number, RelativeLayout groupLayout) {
        View spacerBar = new View(this);
        spacerBar.setId(number*10+3);
        spacerBar.setBackgroundColor(Color.parseColor("#404040"));
        groupLayout.addView(spacerBar);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) spacerBar.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, number*10+2);
        params.topMargin = 8;
        params.height = 1;
    }
    void makeToggle(int number, int id) {
        View newToggle = new View(this);
        newToggle.setId(number*10+4);
        newToggle.setTag(id);
        newToggle.setBackgroundColor(Color.parseColor(red));
        groupActivity.put(id,false);
        RelativeLayout parent = (RelativeLayout) findViewById(R.id.groups_view);
        newToggle.setOnClickListener(new toggleClick(id));
        parent.addView(newToggle);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) newToggle.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_TOP, number*10);
        params.addRule(RelativeLayout.ALIGN_BOTTOM, number*10);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.width = (int) (width * .15);
        params.rightMargin = 10;
        params.bottomMargin = 10;
    }

    Map<Integer, String> getGroups() {
        Map <Integer, String> map = new HashMap<Integer, String>();
        map.put(0,"Group");
        map.put(1,"Fam");
        map.put(2,"Friends");
        return map;
    }

    void sendActive(int id, boolean active) {

    }

    // Compass stuff

}
