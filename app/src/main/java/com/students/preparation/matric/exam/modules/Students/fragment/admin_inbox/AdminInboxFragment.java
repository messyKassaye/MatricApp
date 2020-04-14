package com.students.preparation.matric.exam.modules.Students.fragment.admin_inbox;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.UploadsModel;

import java.util.ArrayList;
import java.util.List;

public class AdminInboxFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //the listview
    ListView listView;

    //list to store uploads data
    List<UploadsModel> uploadsModelList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_uploads, container, false);

        viewPager = root.findViewById(R.id.textbook_viewpager);
        setupViewPager(viewPager);

        tabLayout = root.findViewById(R.id.textbook_tabs);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        // 1 natural
        // 2 social

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String stream = prefs.getString(Constants.LOGGED_IN_USER_STREAM, null);
        //String school = prefs.getString(Constants.LOGGED_IN_USER_SCHOOL, null);


        if (stream.compareToIgnoreCase("Natural") == 0) {

            //adapter.addFragment(new AdminInboxNaturalFragment(11, 1), "Natural 11");
            //adapter.addFragment(new AdminInboxNaturalFragment(12, 1), "Natural 12");
            adapter.addFragment(new AdminInboxNaturalFragment() , "Natural");

        } else {
            adapter.addFragment(new AdminInboxSocialFragment() , "Social");

        }
        //}

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }
}