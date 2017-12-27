package com.sound.lab3_db_chesnokov;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class EditCategoryActivity extends AppCompatActivity {
    int category_id;
    CategoryLab cb;
    boolean add=false;
    EditText title;
    Button ok;
    Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        cb = new CategoryLab(getApplicationContext());

        title = (EditText) findViewById(R.id.categorytitle);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_icons);



        Button ok = (Button) findViewById(R.id.accept);
        Button cancel = (Button) findViewById(R.id.cancelit);

        List<String> icons = new ArrayList<String>();
        icons.add("fun");
        icons.add("sport");
        icons.add("ok");
        icons.add("work");
        //spinner bullshit
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, icons);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //int category_id;
        spinner.setAdapter(adapter);
        spinner.setPrompt("Icons");
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        ///



        String value = getIntent().getStringExtra("add");
        if (value.equals("yes")){
            add=true;
        }
        else{
            title.setText(getIntent().getStringExtra("title"));
            spinner.setPrompt(getIntent().getStringExtra("icon"));
            category_id = getIntent().getIntExtra("category_id",0);

        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random myRandom = new Random();
                if (add){

                    Category c = new Category(title.getText().toString(),spinner.getSelectedItem().toString(),myRandom.nextInt());
                    cb.addCategory(c);
                }
                else {

                    Category c = new Category(title.getText().toString(),spinner.getSelectedItem().toString(),category_id);
                    //r.setName(nameview.getText().toString());
                    cb.updateCategory(c);
                }

                Intent intent = new Intent(getApplicationContext(), CategoryListActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CategoryListActivity.class);
                startActivity(intent);
            }
        });
    }
}
