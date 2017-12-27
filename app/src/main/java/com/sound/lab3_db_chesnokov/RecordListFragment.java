package com.sound.lab3_db_chesnokov;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sound.lab3_db_chesnokov.database.TasksBaseHelper;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sound on 14.12.2017.
 */

public class RecordListFragment extends Fragment {
    PhotoLab ph;
    String sort_type;
    String start_date_from_intent;
    String end_date_from_intent;
    private RecyclerView mCrimeRecyclerView;
    private RecordListFragment.RecordAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_list, container,
                false);
        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.record_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager
                (getActivity()));
        sort_type = getActivity().getIntent().getStringExtra("sort");
        start_date_from_intent = getActivity().getIntent().getStringExtra("start");
        end_date_from_intent = getActivity().getIntent().getStringExtra("end");
        updateUI();

        return view;
    }

    private void updateUI() {
        RecordLab recordLab = RecordLab.get(getActivity());
        //ph = PhotoLab.get(getActivity());
        List<Record> recs = recordLab.getRecords();
        if (sort_type!=null) {


            switch (sort_type) {
                case "biggest time by category":
                    /*
                    List<Category> cb;
                    cb = CategoryLab.get(getContext()).getCategories();

                    for (int i=0; i<cb.size();i++){

                    }
                    */

                    break;
                case "most time":
                    recs = recordLab.getRecords_most_time();


                    for (int i =0;i<recs.size();i++){
                        if(!recs.get(i).isbetween(start_date_from_intent,end_date_from_intent)){
                            recs.remove(i);
                        }
                    }
                    for (int i=0;i<recs.size();i++){
                       // if (!recs.get(i).isbetween(Date1, Date2)){
                        //    recs.remove(i);
                        //}

                    }
                    break;
                default:
                    recs = recordLab.getRecords();
                    break;
            }
        }

        ph = new PhotoLab(getActivity());
        List<Photo> photolist = ph.getPhotos();
        //mAdapter = new RecordListFragment.RecordAdapter(recs);
        //mCrimeRecyclerView.setAdapter(mAdapter);

        if (mAdapter == null) {
            mAdapter = new RecordAdapter(recs);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setRecords(recs);
            mAdapter.notifyDataSetChanged();
        }

    }

    private class RecordHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mTitleTextView2;
        private TextView mName;
        private ImageView mPhotoView;
        private ImageView mPhotoView2;
        private ImageView mPhotoView3;
        private EditText Date1;
        private EditText Date2;
        private Button Edit;
        private Button Delete;
        private TextView duration;

        public RecordHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_record, parent, false));
            itemView.setOnClickListener(this);
            registerForContextMenu(itemView);

            //itemView.setOnLongClickListener(this);
            registerForContextMenu(itemView);
            //itemView.setOnContextClickListener(mCrimeRecyclerView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.category);
            mTitleTextView2 = (TextView) itemView.findViewById(R.id.summary);
            mName = (TextView) itemView.findViewById(R.id.rec_name);
            mPhotoView = (ImageView) itemView.findViewById(R.id.photo);
            mPhotoView2 = (ImageView) itemView.findViewById(R.id.second);
            mPhotoView3 = (ImageView) itemView.findViewById(R.id.third);
            Date1 = (EditText) itemView.findViewById(R.id.date_start);
            Date2 = (EditText) itemView.findViewById(R.id.date_end);
            Edit = (Button) itemView.findViewById(R.id.EditBut);
            Delete = (Button) itemView.findViewById(R.id.Delbut);
            duration = itemView.findViewById(R.id.duration);


        }

        private Record mRecord;

        public void bind(Record rec) {
            mRecord = rec;


            CategoryLab cb = CategoryLab.get(getActivity());

            mTitleTextView.setText(cb.getRecord(rec.getCategory()).getTitle());
            mTitleTextView2.setText(rec.getSummary());
            mName.setText(rec.getName());

            duration.setText(String.valueOf(rec.getTimeInterval()));
            //Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date1.setText(rec.getStartTS());
            Date1.setInputType(InputType.TYPE_NULL);
            Date2.setText(rec.getEndTS());
            Date2.setInputType(InputType.TYPE_NULL);
            //Instant.ofEpochMilli(new Date)
            //mIconView.setImageDrawable(Drawable.createFromPath("D:\\Pictures\\ok.png"));
            //mPhotoView2.setImageDrawable(getResources().getDrawable(R.mipmap.ok));
            //mPhotoView3.setImageDrawable(getResources().getDrawable(R.mipmap.ok));

            //photo!!!!!!!
            ph = new PhotoLab(getActivity());
            //List<Photo> photolist = ph.getPhotos();
            String photopath="error";
            List<String> photopaths = new ArrayList<String>();
            List<Photo> photolist = ph.getPhotos();
            for (int i =0;i<photolist.size();i++){
                if (photolist.get(i).getRecord_id()==rec.getId()){
                    photopath = getContext().getFilesDir().getAbsolutePath()+ "/" + photolist.get(i).getId();
                    photopaths.add(photopath);
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



            Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Add your code in here!
                    RecordLab recordLab = RecordLab.get(getActivity());
                    recordLab.deleteRecord(mRecord);
                    updateUI();
                }
            });

            Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Add your code in here!
                    Intent intent = new Intent(mCrimeRecyclerView.getContext(), EditRecordActivity.class);

                    intent.putExtra("name" , mRecord.getName());
                    intent.putExtra("record_id" , mRecord.getId());
                    intent.putExtra("startts" , mRecord.getStartTS());
                    intent.putExtra("endts" , mRecord.getEndTS());
                    intent.putExtra("category" , mRecord.getCategory());
                    intent.putExtra("summary" , mRecord.getSummary());
                    intent.putExtra("add" , "no");
                    startActivity(intent);
                }
            });

        }


        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(),
                    mRecord.getName() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
            //RecordLab recordLab = RecordLab.get(getActivity());
            //recordLab.deleteRecord(mRecord);
            //updateUI();
        }

        public void dele(View view){

        }


    }



    private class RecordAdapter extends RecyclerView.Adapter<RecordListFragment.RecordHolder> {
        private List<Record> mRecord;
        public RecordAdapter(List<Record> recs) {
            mRecord = recs;
        }

        @Override
        public RecordListFragment.RecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new RecordListFragment.RecordHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(RecordListFragment.RecordHolder holder, int position) {
            Record crime = mRecord.get(position);
            holder.bind(crime);
        }
        @Override
        public int getItemCount() {
            return mRecord.size();
        }

        public void setRecords(List<Record> Record) {
            mRecord = Record;
        }
    }
}
