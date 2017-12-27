package com.sound.lab3_db_chesnokov;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class EditRecordActivity extends AppCompatActivity {
    int category_id;
    int record_id;
    int photoid;
    RecordLab rl;
    PhotoLab pl;
    Button changedate;

    boolean add=false;
    boolean photochanged=false;
    Record r = new Record();
    List<Category> cats;
    TextView DateTimeStart;
    TextView DateTimeEnd;
    Calendar dateAndTime=Calendar.getInstance();
    private Calendar startTime = Calendar.getInstance();
    private Calendar endTime = Calendar.getInstance();
    private Calendar startDate = Calendar.getInstance();
    private final String TIME_FORMAT = "HH:mm";
    private final String DATE_FORMAT = "dd.MM.yyyy";
    private SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    TextView thedate;

    private ImageButton mPhotoView;
    private ImageButton mPhotoView2;
    private ImageButton mPhotoView3;

    ImageView photobox_tochange;
    int photobox_num;

    PhotoLab ph;


    int[] phids;
    //final ImageButton photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //datetime
        super.onCreate(savedInstanceState);
        rl = new RecordLab(getApplicationContext());
        pl = PhotoLab.get(getApplicationContext());
        CategoryLab cb = new CategoryLab(getApplicationContext());
        //Record r = new Record();
        //boolean add=false;

        setContentView(R.layout.activity_edit_record);

        DateTimeStart=(TextView) findViewById(R.id.datestart);
        DateTimeEnd=(TextView) findViewById(R.id.dateend);
        thedate = findViewById(R.id.thedate);
        setInitialDateTime();
        DateTimeStart.getText();

        //ПОЛУЧИЛОСЬ

        final TextView nameview = (TextView) findViewById(R.id.name);
        final TextView summary = (TextView) findViewById(R.id.summary);
        final TextView datestart = (TextView) findViewById(R.id.datestart);
        final TextView dateend = (TextView) findViewById(R.id.dateend);
        //final ImageButton photo = (ImageButton) findViewById(R.id.photo);
        Button ok = (Button) findViewById(R.id.AcceptChanges);
        Button cancel = (Button) findViewById(R.id.cancel);
        Button change = (Button) findViewById(R.id.chagecat);
        Button selectimage = (Button) findViewById(R.id.selectphoto);
        Button starttime = (Button) findViewById(R.id.butstatime);
        Button endtime = (Button) findViewById(R.id.endbutton);


        changedate = findViewById(R.id.changedate);

        mPhotoView = (ImageButton) findViewById(R.id.photo);
        mPhotoView2 = (ImageButton) findViewById(R.id.second);
        mPhotoView3 = (ImageButton) findViewById(R.id.third);


        final Spinner spinner = (Spinner) findViewById(R.id.categories_list);

        // адаптер
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

                category_id=position;
                //Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        String value = getIntent().getStringExtra("add");
        phids = new int[3];
        phids[0]=0;
        phids[1]=0;
        phids[2]=0;
        ph = new PhotoLab(getApplicationContext());
        //List<Photo> photolist = ph.getPhotos();
        String photopath="error";
        List<String> photopaths = new ArrayList<String>();
        List<Photo> photolist = ph.getPhotos();
        if (value.equals("yes")){
            add=true;
            Random myRandom = new Random();
            record_id=myRandom.nextInt();
        }
        else{
            nameview.setText(getIntent().getStringExtra("name"));
            datestart.setText(getIntent().getStringExtra("startts"));
            dateend.setText(getIntent().getStringExtra("endts"));
            String s =getIntent().getStringExtra("startts");
            String s2 =getIntent().getStringExtra("endts");
            DateTimeStart.setText(s.split(" ")[1]);
            DateTimeEnd.setText(s2.split(" ")[1]);
            thedate.setText(s.split(" ")[0]);
            summary.setText(getIntent().getStringExtra("summary"));
            spinner.setPrompt(cb.getRecord(getIntent().getIntExtra("category",0)).getTitle());
            record_id = getIntent().getIntExtra("record_id",0);

            //image stuff


            int k =0;
            for (int i =0;i<photolist.size();i++){
                if (photolist.get(i).getRecord_id()==getIntent().getIntExtra("record_id",0)){
                    photopath = getApplicationContext().getFilesDir().getAbsolutePath()+ "/" + photolist.get(i).getId();
                    photopaths.add(photopath);
                    if (k<3){
                        phids[k] = photolist.get(i).getId();
                        k++;
                    }

                }
            }

            //List<ImageView> imlist = new ArrayList<ImageView>();
            List<ImageView> imlist = new ArrayList<ImageView>();
            imlist.add(mPhotoView);
            imlist.add(mPhotoView2);
            imlist.add(mPhotoView3);

            for (int i=0;i<photopaths.size();i++){
                if(i<3){
                    imlist.get(i).setImageBitmap(BitmapFactory.decodeFile(photopaths.get(i)));
                }
            }




        }

        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                List<Photo> photolist = ph.getPhotos();
                if (phids[0]==0){
                    //add
                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //intent.putExtra("imagebox",0);
                    photobox_tochange = mPhotoView;
                    photobox_num = 0;
                    startActivityForResult(intent,1);

                }
                else{
                    //delete
                    ph.deletePhoto_by_id(phids[0]);
                    mPhotoView.setImageDrawable(getResources().getDrawable(R.mipmap.ok));;
                    phids[0]=0;
                }

            }});

        mPhotoView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                List<Photo> photolist = ph.getPhotos();
                if (phids[1]==0){
                    //add
                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //intent.putExtra("imagebox",0);
                    photobox_tochange = mPhotoView2;
                    photobox_num = 1;
                    startActivityForResult(intent,1);

                }
                else{
                    //delete
                    ph.deletePhoto_by_id(phids[1]);
                    mPhotoView2.setImageDrawable(getResources().getDrawable(R.mipmap.ok));;
                    phids[1]=0;
                }

            }});
        mPhotoView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                List<Photo> photolist = ph.getPhotos();
                if (phids[2]==0){
                    //add
                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //intent.putExtra("imagebox",0);
                    photobox_tochange = mPhotoView3;
                    photobox_num = 2;
                    startActivityForResult(intent,1);

                }
                else{
                    //delete
                    ph.deletePhoto_by_id(phids[2]);
                    mPhotoView3.setImageDrawable(getResources().getDrawable(R.mipmap.ok));;
                    phids[2]=2;
                }

            }});
        //getIntent().getStringExtra("name");

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {{
                new TimePickerDialog(EditRecordActivity.this, startTimeDialog, startTime.get(Calendar.HOUR_OF_DAY),
                        startTime.get(Calendar.MINUTE), true)
                        .show();

            }

            }
        });;

        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(EditRecordActivity.this, endTimeDialog, endTime.get(Calendar.HOUR_OF_DAY),
                        endTime.get(Calendar.MINUTE), true)
                        .show();
            }
        });

        changedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {{
                new DatePickerDialog(EditRecordActivity.this,startDateDialog , startDate.get(Calendar.YEAR),
                        startDate.get(Calendar.MONTH), 3)
                        .show();

            }

            }
        });;

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your code in here!
                int categ_id=0;
                for (int i=0;i<cats.size();i++){
                    if (cats.get(i).getTitle().equals(spinner.getSelectedItem())){
                        categ_id = cats.get(i).getId();
                    }
                }
                r = new Record();
                r.setName(nameview.getText().toString());
                r.setCategory_id(categ_id);
                r.setSummary(summary.getText().toString());
                r.setStartTS(thedate.getText().toString() + " " + datestart.getText().toString()); //change
                r.setEndTS(thedate.getText().toString() + " " + dateend.getText().toString());


                Random myRandom = new Random();
                if (add){
                    r.setId(record_id);
                    rl.addrecord(r);
                }
                else {
                    r.setId(record_id);
                    //r.setName(nameview.getText().toString());
                    rl.updateRecord(r);
                }

                if (photochanged){
                    //pl.addrecord(new Photo(photoid,"ok",r.getId()));
                }

                Intent intent = new Intent(getApplicationContext(), RecordListActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RecordListActivity.class);
                startActivity(intent);
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CategoryListActivity.class);
                startActivity(intent);
            }
        });

        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);

            }
        });


    }

    //DATETIME STUFFFFFF

    DatePickerDialog.OnDateSetListener startDateDialog = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            startDate.set(Calendar.YEAR, year);
            startDate.set(Calendar.MONTH, month);
            startDate.set(Calendar.DAY_OF_MONTH, day);
            thedate.setText(dateFormat.format(startDate.getTime()));
        }
    };

    TimePickerDialog.OnTimeSetListener startTimeDialog = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            startTime.set(Calendar.HOUR_OF_DAY, i);
            startTime.set(Calendar.MINUTE, i1);
            DateTimeStart.setText(timeFormat.format(startTime.getTime()));
        }
    };

    TimePickerDialog.OnTimeSetListener endTimeDialog = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            endTime.set(Calendar.HOUR_OF_DAY, i);
            endTime.set(Calendar.MINUTE, i1);
            DateTimeEnd.setText(timeFormat.format(endTime.getTime()));
        }
    };

    // установка начальных даты и времени
    private void setInitialDateTime() {

        DateTimeStart.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME| DateUtils.FORMAT_24HOUR));

        DateTimeEnd.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                 DateUtils.FORMAT_SHOW_TIME| DateUtils.FORMAT_24HOUR));

        thedate.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        |  DateUtils.FORMAT_NUMERIC_DATE));

    }


    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        photochanged = true;

        if (requestCode == 1) {

                Uri selectedImage = data.getData();
            Bitmap currentImage = null;
            try {
                currentImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //selectedImage.setImageBitmap(currentImage);
                //BitmapFactory.decodeFile(selectedImage);
                String[] filePath = { MediaStore.Images.Media.DATA };

                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

            Random myRandom = new Random();
            photoid = myRandom.nextInt();
                String picturePath = c.getString(columnIndex);
                File from = new File(picturePath);
                File to = new File(getBaseContext().getFilesDir(), String.valueOf(photoid));
            try {
                copyFile(from,to);
            } catch (IOException e) {
                e.printStackTrace();
            }
            c.close();



                String path = getBaseContext().getFilesDir().getAbsolutePath()+"/" + photoid;
                Bitmap trueimage = (BitmapFactory.decodeFile(path));
                //Log.w("path of image from gallery......******************.........", picturePath+"");

                ImageView photo = findViewById(R.id.photo);

                //photo.setImageBitmap(trueimage);

                phids[photobox_num]=photoid;
                pl.addrecord(new Photo(photoid,"ok",record_id));
                photobox_tochange.setImageBitmap(trueimage);


            }
    }

    public static void copyFile(File src, File dst) throws IOException
    {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try
        {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }
        finally
        {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }


}
