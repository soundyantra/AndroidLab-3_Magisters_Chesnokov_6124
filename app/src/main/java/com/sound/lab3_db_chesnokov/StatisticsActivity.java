package com.sound.lab3_db_chesnokov;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.github.mikephil.charting.charts.PieChart;


public class StatisticsActivity extends AppCompatActivity {  //PIE CHART

    private PieChart mChart;
    //PieChart pieChart ;
    ArrayList<PieEntry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        mChart = (PieChart) findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        List<PieEntry> entries = new ArrayList<>();
        CategoryLab cb = CategoryLab.get(this);
        RecordLab rb = RecordLab.get(this);
        List<Category> cats = cb.getCategories();
        List<Record> recs = rb.getRecords();


        String startdate = getIntent().getStringExtra("start");
        String enddate = getIntent().getStringExtra("end");

        for (int i =0;i<recs.size();i++){
            if(!recs.get(i).isbetween(startdate,enddate)){
                recs.remove(i);
            }
        }

        //частота задач по категориям
        long[] times = new long[cats.size()];
        long sum=0;
        for(int j=0;j<cats.size();j++){
            for (int i=0; i<recs.size();i++){
                if(recs.get(i).getCategory()== cats.get(j).getId()){
                    times[j]+=recs.get(i).getTimeInterval();
                }
            }
            sum+=times[j];
        }

        //доделать
        float[] sizes = new float[cats.size()];

        for(int i=0;i<cats.size();i++){
            sizes[i]=(float)times[i]/(float)sum*100;
        }

        for (int i=0;i<cats.size();i++){
            entries.add(new PieEntry(sizes[i], cats.get(i).getTitle()));
        }
        //частота задач по категориям

        PieDataSet set = new PieDataSet(entries, "Time by categories");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        set.setColors(colors);
        PieData data = new PieData(set);
        mChart.setData(data);
        mChart.invalidate(); // refresh


    }

}
