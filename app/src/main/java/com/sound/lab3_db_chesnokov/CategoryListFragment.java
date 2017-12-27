package com.sound.lab3_db_chesnokov;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sound on 14.12.2017.
 */

public class CategoryListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CategoryAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container,
                false);
        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.category_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager
                (getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        CategoryLab crimeLab = CategoryLab.get(getActivity());
        List<Category> cats = crimeLab.getCategories();
        mAdapter = new CategoryAdapter(cats);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    private class CategoryHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private ImageButton mIconView;
        private Button edit;
        private Button delete;
        public CategoryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_category, parent, false));

            mTitleTextView = (TextView) itemView.findViewById(R.id.title);
            mIconView = (ImageButton) itemView.findViewById(R.id.icon);
            edit = (Button) itemView.findViewById(R.id.editcategory);
            delete = (Button) itemView.findViewById(R.id.deletecategory);
        }

        private Category mCategory;

        public void bind(Category cat) {
            mCategory = cat;
            mTitleTextView.setText(cat.getTitle());
            //mIconView.setImageDrawable(Drawable.createFromPath("D:\\Pictures\\ok.png"));
            mIconView.setImageDrawable(getResources().getDrawable(R.mipmap.ok));

            Resources resources = getContext().getResources();
            int resourceId = resources.getIdentifier(cat.getIcon(), "mipmap",
                    getContext().getPackageName());
            Drawable draw = resources.getDrawable(resourceId);
            mIconView.setImageDrawable(draw);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Add your code in here!
                    CategoryLab crimeLab = CategoryLab.get(getActivity());
                    crimeLab.deleteCategory(mCategory);
                    updateUI();
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Add your code in here!
                    Intent intent = new Intent(mCrimeRecyclerView.getContext(), EditCategoryActivity.class);

                    intent.putExtra("title" , mCategory.getTitle());
                    intent.putExtra("category_id" , mCategory.getId());
                    intent.putExtra("icon" , mCategory.getIcon());
                    intent.putExtra("add" , "no");
                    startActivity(intent);
                }
            });
        }


    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {
        private List<Category> mCategory;
        public CategoryAdapter(List<Category> cats) {
            mCategory = cats;
        }

        @Override
        public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CategoryHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CategoryHolder holder, int position) {
            Category crime = mCategory.get(position);
            holder.bind(crime);
        }
        @Override
        public int getItemCount() {
            return mCategory.size();
        }
    }
}
