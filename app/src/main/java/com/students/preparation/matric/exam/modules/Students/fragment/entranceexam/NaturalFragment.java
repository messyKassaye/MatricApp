package com.students.preparation.matric.exam.modules.Students.fragment.entranceexam;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.adapter.CustomNaturalGridAdapter;


public class NaturalFragment extends Fragment {

    GridView grid;

    public NaturalFragment() {
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
        View root = inflater.inflate(R.layout.fragment_natural, container, false);

        CustomNaturalGridAdapter adapter = new CustomNaturalGridAdapter(getActivity());//, web, imageId
        grid = root.findViewById(R.id.grid_natural_subjects);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent examYearList = new Intent(getActivity(), ExamsYearList.class);
                //examYearList.putExtra(Constants.EXAM_KEY_STREAM, Constants.DATABASE_PATH_NATURAL);
                if (position == 0) {
                    examYearList.putExtra(Constants.EXAM_KEY_SUBJECT, Constants.EXAM_KEY_SUBJECT_MATH_N);

                } else if (position == 1) {
                    examYearList.putExtra(Constants.EXAM_KEY_SUBJECT, Constants.EXAM_KEY_SUBJECT_ENGLISH);

                } else if (position == 2) {
                    examYearList.putExtra(Constants.EXAM_KEY_SUBJECT, Constants.EXAM_KEY_SUBJECT_PHYSICS);

                } else if (position == 3) {
                    examYearList.putExtra(Constants.EXAM_KEY_SUBJECT, Constants.EXAM_KEY_SUBJECT_BIOLOGY);

                } else if (position == 4) {
                    examYearList.putExtra(Constants.EXAM_KEY_SUBJECT, Constants.EXAM_KEY_SUBJECT_CHEMISTRY);

                } else if (position == 5) {
                    examYearList.putExtra(Constants.EXAM_KEY_SUBJECT, Constants.EXAM_KEY_SUBJECT_APTITUDE);

                }else if (position == 6) {
                    examYearList.putExtra(Constants.EXAM_KEY_SUBJECT, Constants.EXAM_KEY_SUBJECT_CIVIC);

                }
                startActivity(examYearList);

            }
        });
        return root;
    }

}