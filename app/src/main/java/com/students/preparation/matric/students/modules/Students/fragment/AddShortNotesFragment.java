package com.students.preparation.matric.students.modules.Students.fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.students.preparation.matric.students.Constants;
import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.roomDB.entity.Notes;
import com.students.preparation.matric.students.roomDB.MatricAppDatabase;


public class AddShortNotesFragment extends AppCompatActivity {

    private EditText title,otherSubjectEdit;
    private EditText content;
    private Button addNotes;
    private Spinner subject;
    private String selectedSubject;
    private LinearLayout subjectLayout,otherSubjectRegistrationLayout;
    private boolean otherSelected = false;
    private TextView errorShower;
    private LinearLayout loadingLayout;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_short_notes);

        databaseReference = FirebaseDatabase
                .getInstance().getReference(Constants.DATABASE_PATH_MY_SHORTNOTES);

        title = findViewById(R.id.notesTitle);
        content = findViewById(R.id.notesContent);
        addNotes = findViewById(R.id.addNotes);

        errorShower = findViewById(R.id.notesErrorShower);
        loadingLayout = findViewById(R.id.loadingLayout);

        //subjects adapter
        subjectLayout = findViewById(R.id.subjectMainLayout);
        otherSubjectRegistrationLayout = findViewById(R.id.otherSubjectLayout);
        otherSubjectEdit = findViewById(R.id.otherSubjectEdit);
        subject = findViewById(R.id.subjectSpinner);
        final String[] subjectsArray = getResources().getStringArray(R.array.subjects);
        ArrayAdapter<CharSequence> subjectAdapter = new ArrayAdapter<CharSequence>(this,
                R.layout.spinner_text, subjectsArray );
        subjectAdapter.setDropDownViewResource(R.layout.simple_spinner_drop_down);
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

        content = findViewById(R.id.notesContent);
        addNotes = findViewById(R.id.addNotes);

        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notesTitle = title.getText().toString();
                String notesContent = content.getText().toString();
                String editSubject = otherSubjectEdit.getText().toString();
                if(otherSelected){
                    selectedSubject = editSubject;
                }
                if (notesTitle.equals("")){
                    errorShower.setText("Please enter your notes title");
                    errorShower.setTextColor(Color.RED);
                }else if (notesContent.equals("")){
                    errorShower.setText("Please enter your notes Content");
                    errorShower.setTextColor(Color.RED);
                }else if(selectedSubject==null&&editSubject.equals("")){
                    errorShower.setText("Please select or add  tutorial subject");
                }else {
                    errorShower.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.VISIBLE);
                    addNotes.setVisibility(View.GONE);

                    String noteId = databaseReference.push().getKey();
                    Notes notes = new Notes(
                            Constants.getUniqueIdentifyer(getApplicationContext()),
                            notesTitle,selectedSubject,notesContent);
                    if (Constants.isOnline(getApplicationContext())){
                        databaseReference.child(noteId).setValue(notes)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        errorShower.setText("Your note is saved successfully");
                                        errorShower.setVisibility(View.VISIBLE);
                                        errorShower.setTextColor(Color.GREEN);
                                        loadingLayout.setVisibility(View.GONE);
                                        addNotes.setVisibility(View.VISIBLE);
                                        saveLocalData(notes);
                                    }
                                });
                    }else {
                        saveLocalData(notes);
                    }

                }
            }
        });


    }

public void saveLocalData(Notes notes){
    AsyncTask.execute(new Runnable() {
        @Override
        public void run() {
            MatricAppDatabase.getDatabase(getApplicationContext())
                    .getMyNotesDAO().Store(notes);
        }
    });
}

}
