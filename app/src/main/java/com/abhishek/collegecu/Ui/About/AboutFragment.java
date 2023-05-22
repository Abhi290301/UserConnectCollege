package com.abhishek.collegecu.Ui.About;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.abhishek.collegecu.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;
import java.util.List;


public class AboutFragment extends Fragment {
    private ViewPager viewPager;
    private CourseAdapter adapter;
    private List<CourseModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        list = new ArrayList<>();
        list.add(new CourseModel(R.drawable.computerscience,"Computer Science","The Department of Computer Science is " +
                "established in 2012. The department of Computer Science and Engineering is a pioneering " +
                "academic centre for education at undergraduate level. The Computer Science and Engineering " +
                "department of our college holds prominent position in the degree programme as it constantly " +
                "favours encouragement of students to diversify their perspective. Laboratories of the department" +
                " are equipped with state of the art infrastructure to cater course work and research activities." +
                " This department of ABVGIET continuously trains students to be self-motivated Computer Science" +
                " Engineer with a very high moral. The main objective of the department is to prepare students" +
                " for software technology, industry, educational institutions and research organizations. This" +
                " department withholds 48 seats for every" +
                " batch involving a team of 07 dedicated faculty members & one Lab Technicians."));
        list.add(new CourseModel(R.drawable.electrical,"Electrical","The Electrical Engineering department of our college holds prominent " +
                "position in the degree programme as it constantly favours encouragement of students to diversify " +
                "their perspective. This department of ABVGIET continuously trains students to be self-motivated" +
                " Electrical Engineer with a very high moral. This department withholds 48 seats for every batch " +
                "involving a team of 09 dedicated faculty members & Two Lab Technicians."));
        list.add(new CourseModel(R.drawable.electronics,"Electronics","The Electronics and Communication" +
                " department of our college holds prominent position in the degree programme as it constantly favours" +
                " encouragement of students to diversify their perspective. This department of ABVGIET continuously" +
                " trains students to be self-motivated Electronics and Communication Engineer with a very high moral." +
                " This department withholds 48 seats for every batch involving a team of 09 dedicated faculty members & " +
                "Two Lab Technicians."));

        adapter = new CourseAdapter(getContext() ,list);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        ImageView imageView = view.findViewById(R.id.collegeImage);
        Glide.with(getContext())
                .load("https://abvgiet.ac.in/themes/abvgiet/assets/images/b1-new.jpg").into(imageView);


        return view;
    }
}