package com.students.preparation.matric.exam.admin;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.Tutorials;


public class AddTutorialFragment extends AppCompatActivity {

    private DatabaseReference databaseReference;

    //views variable
    private Spinner streams,grade,subject;
    private String selectedStream,selectedGrade,selectedSubject;
    private TextView errorShower;
    private EditText youtubeLink;
    private Button addTutorial;
    private LinearLayout loadingLayout;

    private boolean otherSelected =false;
    private LinearLayout  subjectLayout,otherSubjectRegistrationLayout;
    private EditText otherSubjectEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_tutorial);

        databaseReference = FirebaseDatabase.getInstance()
                .getReference(Constants.DATABASE_PATH_TUTORIALS);

        //loading layout
        loadingLayout = findViewById(R.id.loadingLayout);

        //subject layout
        subjectLayout = findViewById(R.id.subjectMainLayout);
        otherSubjectRegistrationLayout = findViewById(R.id.otherSubjectLayout);
        otherSubjectEdit = findViewById(R.id.otherSubjectEdit);

        //view initializations
        streams = findViewById(R.id.tutorialStream);

        final String[] streamArray = getResources().getStringArray(R.array.meseretStream);

        ArrayAdapter<CharSequence> streamAdapter = new ArrayAdapter<CharSequence>(this,
                R.layout.spinner_text, streamArray );
        streamAdapter.setDropDownViewResource(R.layout.simple_spinner_drop_down);
        streams.setAdapter(streamAdapter);
        streams.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = ((TextView)view).getText().toString();
                if(!text.equals(streamArray[0])){
                    selectedStream = text;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //grade select spinner
        grade = findViewById(R.id.gradeSpinner);
        final String[] gradesArray = getResources().getStringArray(R.array.meseretGrade);
        ArrayAdapter<CharSequence> gradeAdapter = new ArrayAdapter<CharSequence>(this,
                R.layout.spinner_text, gradesArray );
        gradeAdapter.setDropDownViewResource(R.layout.simple_spinner_drop_down);
        grade.setAdapter(gradeAdapter);
        grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = ((TextView)view).getText().toString();
                if(!text.equals(gradesArray[0])){
                    selectedGrade = text;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //subjects adapter
        subject = findViewById(R.id.subjectSpinner);
        final String[] subjectsArray = getResources().getStringArray(R.array.subjects);
        ArrayAdapter<CharSequence> subjectAdapter = new ArrayAdapter<CharSequence>(this,
                R.layout.spinner_text, subjectsArray );
        gradeAdapter.setDropDownViewResource(R.layout.simple_spinner_drop_down);
        subject.setAdapter(subjectAdapter);
        subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String subject = ((TextView)view).getText().toString();
                if(subject.equals("Other")){
                    subjectLayout.setVisibility(View.GONE);
                    otherSubjectRegistrationLayout.setVisibility(View.VISIBLE);
                    otherSelected = true;
                }else if (!subject.equals(subjectsArray[0])){
                    selectedSubject = subject;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        youtubeLink = findViewById(R.id.youtubeLink);
        errorShower = findViewById(R.id.errorShower);

        addTutorial = findViewById(R.id.addTutorial);
        addTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link =youtubeLink.getText().toString();
                selectedSubject = otherSubjectEdit.getText().toString();
                if(link.equals("")){
                    errorShower.setText("Please add youtube link");
                }else if(selectedStream==null){
                    errorShower.setText("Please select tutorial stream");
                }else if(selectedGrade ==null){
                    errorShower.setText("Please select tutorial grade");
                }else if(selectedSubject==null){
                    errorShower.setText("Please select or add  tutorial subject");
                }else {
                    errorShower.setText("");
                    addTutorial.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.VISIBLE);

                    Tutorials tutorials = new Tutorials(
                            selectedStream,selectedGrade,selectedSubject,link
                    );

                   String tid = databaseReference.push().getKey();
                   if(tid!=null){
                       databaseReference.child(tid).setValue(tutorials)
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       loadingLayout.setVisibility(View.GONE);
                                       addTutorial.setVisibility(View.VISIBLE);
                                       errorShower.setText("Tutorial successfully added");
                                       errorShower.setTextColor(Color.GREEN);
                                   }
                               })
                               .addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                       loadingLayout.setVisibility(View.GONE);
                                       addTutorial.setVisibility(View.VISIBLE);
                                       errorShower.setText("Something is not good.Please check your" +
                                               " internet");
                                       errorShower.setTextColor(Color.RED);
                                   }
                               });
                   }

                }
            }
        });

    }


}
