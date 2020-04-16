package com.students.preparation.matric.exam.modules.Students.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.adapter.HomeListAdapter;
import com.students.preparation.matric.exam.modules.Students.fragment.admin_inbox.AdminInboxFragment;
import com.students.preparation.matric.exam.modules.Students.fragment.entranceexam.EntranceExamListFragment;
import com.students.preparation.matric.exam.modules.Students.fragment.modelexam.ModelExamFragment;
import com.students.preparation.matric.exam.modules.Students.fragment.shortnotes.MyShortnotesFragment;
import com.students.preparation.matric.exam.modules.Students.fragment.shortnotes.ShortNotesFragment;
import com.students.preparation.matric.exam.modules.Students.fragment.studytips.StudytipsFragment;
import com.students.preparation.matric.exam.modules.Students.fragment.teachers_inbox.TeachersFragment;
import com.students.preparation.matric.exam.modules.Students.fragment.teachersguide.GuideFragment;
import com.students.preparation.matric.exam.modules.Students.fragment.textbook.TextbookFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    private ListView listView;


    HomeListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        final String[] titles = getResources().getStringArray(R.array.home_list_titles);
        final String[] subTitles = getResources().getStringArray(R.array.home_list_subtitles);
        final String[] icons = getResources().getStringArray(R.array.home_list_icons);

        ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < titles.length; i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            // adding each child node to HashMap key => value
            map.put(Constants.HOME_KEY_ID, ""+i);
            map.put(Constants.HOME_KEY_TITLE, titles[i]);
            map.put(Constants.HOME_KEY_SUBTITLE, subTitles[i]);
            // map.put(KEY_DURATION, title[i]);
            map.put(Constants.HOME_KEY_THUMB_ICON, icons[i]);

            // adding HashList to ArrayList
            arrayList.add(map);
        }

        adapter = new HomeListAdapter(getActivity(), arrayList);

        listView = root.findViewById(R.id.home_list_menu);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Fragment fragment = null;
                    switch (position) {
                        case 0:
                            fragment = new TextbookFragment();
                            break;
                        case 1:
                            fragment = new GuideFragment();
                            break;
                        case 2:
                            fragment = new ShortNotesFragment();
                            break;
                        case 3:
                            fragment = new EntranceExamListFragment();
                            break;
                        case 4:
                            fragment = new ModelExamFragment();
                            break;
                        case 5:
                            fragment = new StudytipsFragment();
                            break;
                        case 6:
                            fragment = new StudytipsFragment();
                            break;
                        case 7:
                            fragment = new TeachersFragment();
                            break;

                        default:
                            break;
                    }

                    if (fragment != null) {
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.nav_host_fragment, fragment).commit();

                        //NavigationView navigationView = (NavigationView) root.findViewById(R.id.nav_view);
                        //StudentDashboard.navigationView.getMenu().getItem(position+1).setChecked(true);

                        // update selected item and title, then close the drawer
                        //StudentDashboard.navigationView.setCheckedItem(position)
                        //StudentDashboard.navigationView.setSelection(position);
                        //StudentDashboard.navigationViewsetItemChecked(position, true);
                        //mDrawerList.setSelection(position);
                        //setTitle(navMenuTitles[position]);
                        //mDrawerLayout.closeDrawer(mDrawerList);
                    } else {
                        // error in creating fragment
                        Log.e("StudentDashboard", "Error in creating fragment");
                    }
                }

        });

        return root;
    }
}