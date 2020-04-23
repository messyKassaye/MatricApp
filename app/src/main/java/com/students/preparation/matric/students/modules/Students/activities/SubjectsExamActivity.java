package com.students.preparation.matric.students.modules.Students.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.students.Constants;
import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.adapter.SubjectsExamRecyclerViewAdapter;
import com.students.preparation.matric.students.model.Exams;

import java.util.ArrayList;

public class SubjectsExamActivity extends AppCompatActivity {

    //subject exam
    private ArrayList<Exams> examSubjectArray = new ArrayList<>();
    private SubjectsExamRecyclerViewAdapter examsubjectsAdapter;
    private RecyclerView subjectExamRecyclerView;
    private ProgressBar progressBar;
    private TextView noDataIsFound;

    private Toolbar toolbar;
    private String subjects;

    private ImageView exit;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_exam);
        toolbar = findViewById(R.id.toolbar);
        subjects = getIntent().getStringExtra("subject");
        setSupportActionBar(toolbar);

        exit = findViewById(R.id.exitSubject);
        title = findViewById(R.id.titles);
        title.setText(subjects+" Exams");
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //subject exam
        noDataIsFound = findViewById(R.id.noDataIsFound);
        examsubjectsAdapter = new SubjectsExamRecyclerViewAdapter(subjects,SubjectsExamActivity.this,examSubjectArray);
        subjectExamRecyclerView = findViewById(R.id.subjectsExamRecyclerView);
        progressBar = findViewById(R.id.subjectLoader);

        progressBar.setVisibility(View.VISIBLE);
        subjectExamRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false));
        subjectExamRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fetchData(subjects);

    }

    public void fetchData(String subjects){
        toolbar.setTitle(subjects+" exam");
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
