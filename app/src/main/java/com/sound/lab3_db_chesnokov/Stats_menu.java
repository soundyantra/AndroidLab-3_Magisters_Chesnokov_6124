package com.sound.lab3_db_chesnokov;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Stats_menu extends AppCompatActivity {
    Button buttonPieChart;
    Button mosttime;
    Button mostoften;
    Button startdate;
    Button enddate;
    Calendar dateAndTime=Calendar.getInstance();
    private Calendar startTime = Calendar.getInstance();
    private Calendar endTime = Calendar.getInstance();

    private final String TIME_FORMAT = "dd.MM.yyyy";
    private SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
    TextView DateTimeStart;
    TextView DateTimeEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_menu);
        this.buttonPieChart=(Button) findViewById(R.id.piechart);

        startdate = findViewById(R.id.start_stats);
        enddate = findViewById(R.id.end_stats);
        DateTimeStart = findViewById((R.id.starttext));
        DateTimeEnd = findViewById((R.id.endtext));
        setInitialDateTime();
        buttonPieChart.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StatisticsActivity.class);
                intent.putExtra("start",DateTimeStart.getText().toString());
                intent.putExtra("end",DateTimeEnd.getText().toString());
                //intent.putExtra("add","yes");
                startActivity(intent);
            }
        });
        this.mosttime=(Button) findViewById(R.id.most_time);
        mosttime.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecordListActivity.class);
                intent.putExtra("start",DateTimeStart.getText().toString());
                intent.putExtra("end",DateTimeEnd.getText().toString());
                intent.putExtra("sort","most time");
                startActivity(intent);
            }
        });

        this.mostoften = findViewById(R.id.most_often);
        mostoften.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MoreStats.class);
                intent.putExtra("start",DateTimeStart.getText().toString());
                intent.putExtra("end",DateTimeEnd.getText().toString());
                startActivity(intent);
            }
        });

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {{
                new DatePickerDialog(Stats_menu.this,startTimeDialog , startTime.get(Calendar.YEAR),
                        startTime.get(Calendar.MONTH), 3)
                        .show();

            }
                //photo.setImageDrawable();
                //addphoto
                //
            }
        });;

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Stats_menu.this,endTimeDialog , endTime.get(Calendar.YEAR),
                        endTime.get(Calendar.MONTH), 3)
                        .show();
            }
        });



    }
    DatePickerDialog.OnDateSetListener startTimeDialog = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            startTime.set(Calendar.YEAR, year);
            startTime.set(Calendar.MONTH, month);
            startTime.set(Calendar.DAY_OF_MONTH, day);
            DateTimeStart.setText(timeFormat.format(startTime.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener endTimeDialog = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            endTime.set(Calendar.YEAR, year);
            endTime.set(Calendar.MONTH, month);
            endTime.set(Calendar.DAY_OF_MONTH, day);
            DateTimeEnd.setText(timeFormat.format(endTime.getTime()));
        }
    };

    private void setInitialDateTime() {

        DateTimeStart.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_NUMERIC_DATE|DateUtils.FORMAT_24HOUR));

        DateTimeEnd.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_NUMERIC_DATE|DateUtils.FORMAT_24HOUR));

    }
}
