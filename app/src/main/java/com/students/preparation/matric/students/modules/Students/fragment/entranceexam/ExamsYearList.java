package com.students.preparation.matric.students.modules.Students.fragment.entranceexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.students.Constants;
import com.students.preparation.matric.students.R;

import java.util.ArrayList;
import java.util.List;

public class ExamsYearList extends AppCompatActivity {

    ListView listSubjectExamYear;
    String subject;
    String[] examYears;

    List<String> examModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams_year_list);

        listSubjectExamYear = findViewById(R.id.list_subject_exam_year);

        listSubjectExamYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent exam = new Intent(getApplicationContext(), ExamActivity.class);
                exam.putExtra(Constants.EXAM_KEY_SUBJECT, subject);
                exam.putExtra(Constants.EXAM_KEY_YEAR, examYears[position]);

                startActivity(exam);
            }
        });

        examModelList = new ArrayList<>();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //String stream = extras.getString(Constants.EXAM_KEY_STREAM);
            subject = extras.getString(Constants.EXAM_KEY_SUBJECT);


            DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_ENTRANCE_EXAM + "/" + subject);

            //put data on arraylist

            //retrieving upload data from firebase database
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        examModelList.add(postSnapshot.getKey());
                    }


                    examYears = new String[examModelList.size()];

                    for (int i = 0; i < examYears.length; i++) {
                        examYears[i] = examModelList.get(i);
                        //Toast.makeText(getApplicationContext() , ""+examModelList.get(i) , Toast.LENGTH_LONG).show();

                    }

                    //displaying it to list
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, examYears);
                    listSubjectExamYear.setAdapter(adapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }


    }
}
