package com.example.matth.findmygroup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static RelativeLayout groupLayout;
    static RelativeLayout compassLayout;

    DisplayMetrics displayMetrics = new DisplayMetrics();
    int width = displayMetrics.widthPixels;
    int height = displayMetrics.heightPixels;

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

//        String groups[] = getGroupsFromMemory();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        makeGroup("MyGroup");
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

    void makeGroup(String name) {
        RelativeLayout groupLayout = makeGroupLayout(name);
        TextView groupName = makeGroupName(name, groupLayout);
        makeGroupPeople(name, groupLayout, groupName);
    }
    RelativeLayout makeGroupLayout(String name) {
        RelativeLayout newGroupLayout = new RelativeLayout(this);
        newGroupLayout.setTag(name+"layout");
        RelativeLayout groupsView = (RelativeLayout) findViewById(R.id.groups_view);
        groupsView.addView(newGroupLayout);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) newGroupLayout.getLayoutParams();
        params.topMargin = 10;
        params.leftMargin = 12;
        params.rightMargin = 12;
        // This still needs an onclick listener
        return newGroupLayout;
    }
    TextView makeGroupName(String name, RelativeLayout groupLayout) {
        TextView groupName = new TextView(this);
        groupName.setTag(name+"name");
        groupName.setTextSize(22);
        groupName.setText(name);
        groupLayout.addView(groupName);
        return groupName;
    }
    void makeGroupPeople(String name, RelativeLayout groupLayout, TextView groupName) {
        TextView groupActive = new TextView(this);
        groupActive.setTag(name+"active");
        groupActive.setTextSize(15);
        groupActive.setText("Loading Active Members...");
        groupLayout.addView(groupActive);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) groupActive.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, groupName.getId());
    }

}
