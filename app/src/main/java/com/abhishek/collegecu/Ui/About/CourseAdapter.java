package com.abhishek.collegecu.Ui.About;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.collegecu.R;

import java.util.List;

public class CourseAdapter extends PagerAdapter {
    private Context context;
    private List<CourseModel> list;

    public CourseAdapter(Context context, List<CourseModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.branch_item_layout,
                container,false);

        ImageView branchIcon;
        TextView branchName , branchDescription;

        branchIcon = view.findViewById(R.id.branch_icon);
        branchName = view.findViewById(R.id.branch_title);
        branchDescription = view.findViewById(R.id.branchDescription);
        branchIcon.setImageResource(list.get(position).getImg());
        branchName.setText(list.get(position).getCtitle());
        branchDescription.setText(list.get(position).getDescription());

        container.addView(view,0);


        return view;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
