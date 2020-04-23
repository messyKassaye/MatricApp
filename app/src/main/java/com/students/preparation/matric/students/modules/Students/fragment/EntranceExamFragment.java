package com.students.preparation.matric.students.modules.Students.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.adapter.TabViewPageAdapter;


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
