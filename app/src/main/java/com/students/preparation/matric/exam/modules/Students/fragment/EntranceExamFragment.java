package com.students.preparation.matric.exam.modules.Students.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.adapter.StudentExamSubjectRecyclerViewAdapter;
import com.students.preparation.matric.exam.adapter.SubjectsExamRecyclerViewAdapter;
import com.students.preparation.matric.exam.adapter.TabViewPageAdapter;
import com.students.preparation.matric.exam.model.ExamSubjects;
import com.students.preparation.matric.exam.model.Exams;
import com.students.preparation.matric.exam.model.Tutorials;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import javax.security.auth.Subject;


public class EntranceExamFragment extends Fragment {



    //tab layout
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabViewPageAdapter viewPageAdapter;

    public EntranceExamFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_entrance_exam2, container, false);

        viewPageAdapter = new TabViewPageAdapter(getActivity().getSupportFragmentManager());
        viewPageAdapter.addFragment(new NaturalScienceFragment(),"Natural");
        viewPageAdapter.addFragment(new SocialScienceFragment(),"Social");
        tabLayout = view.findViewById(R.id.subjectTab);
        viewPager = view.findViewById(R.id.subjectsViewpager);
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
}
