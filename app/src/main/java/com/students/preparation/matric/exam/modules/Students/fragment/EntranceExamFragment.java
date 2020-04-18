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

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.adapter.StudentExamSubjectRecyclerViewAdapter;
import com.students.preparation.matric.exam.adapter.SubjectsExamRecyclerViewAdapter;
import com.students.preparation.matric.exam.model.ExamSubjects;
import com.students.preparation.matric.exam.model.Exams;
import com.students.preparation.matric.exam.model.Tutorials;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import javax.security.auth.Subject;


public class EntranceExamFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<ExamSubjects> arrayList = new ArrayList<>();
    private StudentExamSubjectRecyclerViewAdapter adapter;

    private TextView noDataIsFound;


    //subject exam
    private ArrayList<Exams> examSubjectArray = new ArrayList<>();
    private SubjectsExamRecyclerViewAdapter examsubjectsAdapter;
    private RecyclerView subjectExamRecyclerView;
    private ProgressBar progressBar;

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

        noDataIsFound = view.findViewById(R.id.noDataIsFound);

        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.EXAM_FILES_PATH);

        adapter = new StudentExamSubjectRecyclerViewAdapter(this,getContext(),arrayList);
        recyclerView = view.findViewById(R.id.examRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final String[] subjectsArray = getResources().getStringArray(R.array.subjects_array);

        for (int i=0;i<subjectsArray.length;i++){
            ExamSubjects subjects = new ExamSubjects();
            subjects.setSubject(subjectsArray[i]);
            subjects.setSubjectImage(getImagePath(subjectsArray[i]));
            arrayList.add(subjects);
        }
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //subject exam
        examsubjectsAdapter = new SubjectsExamRecyclerViewAdapter(getContext(),examSubjectArray);
        subjectExamRecyclerView = view.findViewById(R.id.subjectsExamRecyclerView);
        progressBar = view.findViewById(R.id.subjectLoader);




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
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        subjectExamRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        subjectExamRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fetchData(subjects);
    }

    public void fetchData(String subjects){
        ((AppCompatActivity)getContext()).getSupportActionBar().setTitle(subjects+" exam");
        final DatabaseReference databaseReference = FirebaseDatabase
                .getInstance().getReference(Constants.EXAM_FILES_PATH);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                subjectExamRecyclerView.setVisibility(View.VISIBLE);
                for (DataSnapshot tutorialSnapshot : dataSnapshot.getChildren()) {
                    Exams uploadsModel = tutorialSnapshot.getValue(Exams.class);
                    if (uploadsModel.getExamSubject().equalsIgnoreCase(subjects)){
                        examSubjectArray.add(uploadsModel);
                    }
                }

                if (examSubjectArray.size()>0) {
                    subjectExamRecyclerView.setAdapter(examsubjectsAdapter);
                    examsubjectsAdapter.notifyDataSetChanged();
                }else {
                    progressBar.setVisibility(View.GONE);
                    subjectExamRecyclerView.setVisibility(View.GONE);
                    noDataIsFound.setText("Exam is not added for "+subjects);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
