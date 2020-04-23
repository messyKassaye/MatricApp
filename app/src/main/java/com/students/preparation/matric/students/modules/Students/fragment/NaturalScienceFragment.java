package com.students.preparation.matric.students.modules.Students.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.students.preparation.matric.students.Constants;
import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.adapter.StudentExamSubjectRecyclerViewAdapter;
import com.students.preparation.matric.students.model.ExamSubjects;
import com.students.preparation.matric.students.modules.Students.activities.SubjectsExamActivity;

import java.util.ArrayList;


public class NaturalScienceFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<ExamSubjects> arrayList = new ArrayList<>();
    private StudentExamSubjectRecyclerViewAdapter adapter;


    public NaturalScienceFragment() {
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
        final View view= inflater.inflate(R.layout.fragment_natural_science, container, false);


        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.EXAM_FILES_PATH);

        adapter = new StudentExamSubjectRecyclerViewAdapter(this,getContext(),arrayList);
        recyclerView = view.findViewById(R.id.examRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final String[] subjectsArray = getResources().getStringArray(R.array.natural_subjects);

        for (int i=0;i<subjectsArray.length;i++){
            ExamSubjects subjects = new ExamSubjects();
            subjects.setSubject(subjectsArray[i]);
            subjects.setSubjectImage(getImagePath(subjectsArray[i]));
            arrayList.add(subjects);
        }
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        return view;
    }

    public int getImagePath(String subject){
        if (subject.equalsIgnoreCase("Physics")){
            return  R.drawable.physics;
        }else if (subject.equalsIgnoreCase("Biology")){
            return R.drawable.biology;
        }else if (subject.equalsIgnoreCase("Aptitude")){
            return R.drawable.apptitude;
        } else if (subject.equalsIgnoreCase("chemistry")){
            return R.drawable.chemistry;
        }else if (subject.equalsIgnoreCase("Economics")){
            return R.drawable.economics;
        }else if (subject.equalsIgnoreCase("history")){
            return R.drawable.history;
        }else if (subject.equalsIgnoreCase("Geography")){
            return R.drawable.geography;
        }else if (subject.equalsIgnoreCase("Math Natural")){
            return R.drawable.math;
        }else if (subject.equalsIgnoreCase("Civics")){
            return R.drawable.civics;
        }else if (subject.equalsIgnoreCase("Math Social")){
            return R.drawable.math;
        }else if (subject.equalsIgnoreCase("english")){
            return R.drawable.english;
        }
        else {
            return R.drawable.book;
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public void showSubjectsExam(String subjects){
        Intent intent = new Intent(getContext(), SubjectsExamActivity.class);
        intent.putExtra("subject",subjects);
        getActivity().startActivity(intent);
    }



}
