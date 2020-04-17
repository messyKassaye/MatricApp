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



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }
}