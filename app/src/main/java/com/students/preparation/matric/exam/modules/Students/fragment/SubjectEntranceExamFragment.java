package com.students.preparation.matric.exam.modules.Students.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.students.preparation.matric.exam.R;


public class SubjectEntranceExamFragment extends Fragment {


    private String subject;

    public SubjectEntranceExamFragment(String subject) {
        this.subject = subject;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subject_entrance_exam, container, false);
    }


}
