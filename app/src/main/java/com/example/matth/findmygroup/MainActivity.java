package com.example.matth.findmygroup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static RelativeLayout groupLayout;
    static RelativeLayout compassLayout;

    int width;
    int height;

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

        String groups[] = getGroupsFromMemory();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        makeGroups(groups);

    }

    class groupClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
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

    void makeGroups(String[] groups) {
        for (int i=1;i<=groups.length;i++) {
            makeGroup(groups[i-1],i);
        }
    }
    void makeGroup(String name, int number) {
        // Id's are: groupLayout = number*10, groupName = number*10+1, groupActive = number*10+2 spacerBar = number*1+3
        // number indexes from 1 to prevent overlap with other 0 indexes
        RelativeLayout groupLayout = makeGroupLayout(number);
        TextView groupName = makeGroupName(name, number, groupLayout);
        makeGroupPeople(number, groupLayout);
        makeSpacer(number, groupLayout);
    }
    RelativeLayout makeGroupLayout(int number) {
        RelativeLayout newGroupLayout = new RelativeLayout(this);
        RelativeLayout groupsView = (RelativeLayout) findViewById(R.id.groups_view);
        newGroupLayout.setId(number*10);
        newGroupLayout.setOnClickListener(new groupClick());
        groupsView.addView(newGroupLayout);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) newGroupLayout.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, (number-1)*10);
        params.topMargin = 10;
        params.leftMargin = 12;
        params.rightMargin = 12;
        return newGroupLayout;
    }
    TextView makeGroupName(String name, int number, RelativeLayout groupLayout) {
        TextView groupName = new TextView(this);
        groupName.setId(number*10+1);
        groupName.setTextSize(22);
        groupName.setText(name);
        groupLayout.addView(groupName);
        return groupName;
    }
    void makeGroupPeople(int number, RelativeLayout groupLayout) {
        TextView groupActive = new TextView(this);
        groupActive.setId(number*10+2);
        groupActive.setTextSize(15);
        groupActive.setText("Loading Active Members...");
        groupLayout.addView(groupActive);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) groupActive.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, number*10+1);
        Log.d("ID: ",number*10+1+"");
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

    String[] getGroupsFromMemory() {
        String temp[] = {"Team","Fam","Group","Friends","Enemies"};
        return temp;
    }

}
