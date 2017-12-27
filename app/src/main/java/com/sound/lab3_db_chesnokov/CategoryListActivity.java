package com.sound.lab3_db_chesnokov;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by sound on 14.12.2017.
 */

public class CategoryListActivity extends AppCompatActivity {
    FloatingActionButton buttonChange;
    FloatingActionButton buttonStat;
    FloatingActionButton buttonHome;

    protected CategoryListFragment createFragment() {
        return new CategoryListFragment();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }

        setContentView(R.layout.activity_fragment);
        this.buttonChange=(FloatingActionButton) findViewById(R.id.floatingActionButton3);
        buttonChange.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditCategoryActivity.class);
                intent.putExtra("add","yes");
                startActivity(intent);
            }
        });
        this.buttonStat=(FloatingActionButton) findViewById(R.id.floatingActionButton);
        buttonStat.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StatisticsActivity.class);
                //intent.putExtra("add","yes");
                startActivity(intent);
            }
        });
        this.buttonHome=(FloatingActionButton) findViewById(R.id.floatingActionButton2);
        buttonHome.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecordListActivity.class);
                //intent.putExtra("add","yes");
                startActivity(intent);
            }
        });


    }
}
