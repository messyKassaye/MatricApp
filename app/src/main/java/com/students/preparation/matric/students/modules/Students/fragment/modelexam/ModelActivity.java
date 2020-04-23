package com.students.preparation.matric.students.modules.Students.fragment.modelexam;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.students.Constants;
import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.adapter.ExamAdapter;
import com.students.preparation.matric.students.model.ExamQuestionsModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelActivity extends AppCompatActivity {

    String subject, year;
    List<ExamQuestionsModel> examQuestionsList;
    ListView listview_exam_questions;
    String[] examQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        examQuestionsList = new ArrayList<>();
        listview_exam_questions = findViewById(R.id.listview_exam_questions);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            subject = extras.getString(Constants.EXAM_KEY_SUBJECT);
            year = extras.getString(Constants.EXAM_KEY_YEAR);


            DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_MODEL_EXAM + "/" + subject + "/" + year);

            //retrieving upload data from firebase database
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        ExamQuestionsModel examQuestions = postSnapshot.getValue(ExamQuestionsModel.class);
                        examQuestionsList.add(examQuestions);
                    }

                    examQuestions = new String[examQuestionsList.size()];

                    for (int i = 0; i < examQuestions.length; i++) {
                        examQuestions[i] = examQuestionsList.get(i).getExamQuestion() + "\n" + examQuestionsList.get(i).getExamChoice1()+ "\n" + examQuestionsList.get(i).getExamChoice2()+ "\n" + examQuestionsList.get(i).getExamChoice3()+ "\n" + examQuestionsList.get(i).getExamChoice4() + "\n" + examQuestionsList.get(i).examAnswer;
                    }

                    ArrayList<HashMap<String, String>> questionsArrayList = new ArrayList<HashMap<String, String>>();

                    for (int i = 0; i < examQuestionsList.size(); i++) {

                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put(Constants.EXAM_QUESTION, examQuestionsList.get(i).getExamQuestion());
                        map.put(Constants.EXAM_CHOICE_1, examQuestionsList.get(i).getExamChoice1());
                        map.put(Constants.EXAM_CHOICE_2, examQuestionsList.get(i).getExamChoice2());
                        map.put(Constants.EXAM_CHOICE_3, examQuestionsList.get(i).getExamChoice3());
                        map.put(Constants.EXAM_CHOICE_4, examQuestionsList.get(i).getExamChoice4());
                        map.put(Constants.EXAM_ANSWER, examQuestionsList.get(i).getExamAnswer());
                        map.put(Constants.EXAM_EXPLANATION, examQuestionsList.get(i).getExamAnswerExplanation());
                        map.put(Constants.EXAM_OPTIONAL_IMAGE, examQuestionsList.get(i).getExamOptionalImageUrl());

                        questionsArrayList.add(map);

                        //Toast.makeText(getApplicationContext() , "URL: "+examQuestionsList.get(i).getExamOptionalImageUrl() , Toast.LENGTH_LONG).show();

                    }


                        ExamAdapter adapter = new ExamAdapter(getApplicationContext(), ModelActivity.this, questionsArrayList);// , list);

                    //displaying it to list
                    //ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, examQuestions);
                    listview_exam_questions.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }
}
