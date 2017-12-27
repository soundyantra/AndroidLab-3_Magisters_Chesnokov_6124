package com.sound.lab3_db_chesnokov;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MoreStats extends AppCompatActivity {

    EditText text;
    EditText sortbyoften;
    EditText forselectedcategory;
    List<Category> cats;
    List<Record> recs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_stats);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        text = findViewById(R.id.dattext);
        text.setFocusable(false);
        sortbyoften = findViewById(R.id.sortbyoften);
        sortbyoften.setFocusable(false);
        forselectedcategory = findViewById(R.id.forselectedcategory);
        forselectedcategory.setFocusable(false);
        CategoryLab cb = CategoryLab.get(getApplicationContext());
        RecordLab rb = RecordLab.get(getApplicationContext());
        cats = cb.getCategories();
        recs = rb.getRecords();

        String startdate = getIntent().getStringExtra("start");
        String enddate = getIntent().getStringExtra("end");

        for (int i =0;i<recs.size();i++){
            if(!recs.get(i).isbetween(startdate,enddate)){
                recs.remove(i);
            }
        }

        //частота задач по категориям
        long[] times = new long[cats.size()];
        long[] count = new long[cats.size()];
        long sum=0;
        for(int j=0;j<cats.size();j++){
            for (int i=0; i<recs.size();i++){
                if(recs.get(i).getCategory()== cats.get(j).getId()){
                    times[j]+=recs.get(i).getTimeInterval();
                    count[j]++;
                }
            }
            sum+=times[j];
        }
        String output="";
        String sort_by_how_often="";
        for (int i=0;i<cats.size();i++){
            output += cats.get(i).getTitle() + "  -  " + times[i] + "\n";
            sort_by_how_often += cats.get(i).getTitle() + "  -  " + count[i] + "\n";
        }
        text.setText(output);
        sortbyoften.setText(sort_by_how_often);

        cats = cb.getCategories();
        List<String> cat_names = new ArrayList<String>();
        for (int i=0;i<cats.size();i++){
            cat_names.add(cats.get(i).getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cat_names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //int category_id;

        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Categories");
        // выделяем элемент
        spinner.setSelection(0);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента

                //category_id=position;
                int categ_id=0;
                for (int i=0;i<cats.size();i++){
                    if (cats.get(i).getTitle().equals(spinner.getSelectedItem())){
                        categ_id = cats.get(i).getId();
                    }
                }

                long times2 = 0;
                long[] count = new long[cats.size()];
                long sum2=0;

                    for (int i=0; i<recs.size();i++){
                        if(recs.get(i).getCategory()== categ_id){
                            times2+=recs.get(i).getTimeInterval();
                            //count[j]++;
                        }
                    }
                    sum2+=times2;


                forselectedcategory.setText(String.valueOf(sum2));
                //r = new Record();
                //r.setName(nameview.getText().toString());
                //r.setCategory_id(categ_id);
                //Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }
}
