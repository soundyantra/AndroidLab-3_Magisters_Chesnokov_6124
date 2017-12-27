package com.sound.lab3_db_chesnokov;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;

/**
 * Created by sound on 14.12.2017.
 */

public class RecordListActivity extends AppCompatActivity {
    FloatingActionButton buttonChange;
    FloatingActionButton buttonStat;
    FloatingActionButton buttonHome;
    FloatingActionButton Categor;
    //@Override
    protected RecordListFragment createFragment() {
        return new RecordListFragment();
    }

    //public Fragment createFragment();
    @Override
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
                Intent intent = new Intent(getApplicationContext(), EditRecordActivity.class);
                intent.putExtra("add","yes");
                startActivity(intent);
            }
        });
        this.buttonStat=(FloatingActionButton) findViewById(R.id.floatingActionButton);
        buttonStat.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Stats_menu.class);
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

        this.Categor=(FloatingActionButton) findViewById(R.id.categor);
        Categor.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryListActivity.class);
                //intent.putExtra("add","yes");
                startActivity(intent);
            }
        });

        JodaTimeAndroid.init(this);
        DateTime one = new DateTime();
         DateTime two = new DateTime();
        Interval hey = new Interval(one, two);
        long minutes = hey.toDuration().getStandardMinutes();
        String sda = one.toString();
        DateTime date = DateTime.parse("04/02/2011 20:27:05",
                DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"));

        DateTime wow = DateTime.parse(sda);


    }


}
