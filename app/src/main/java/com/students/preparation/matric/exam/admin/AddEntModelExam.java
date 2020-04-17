package com.students.preparation.matric.exam.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.EntranceExamModel;

import static android.app.Activity.RESULT_OK;

public class AddEntModelExam extends Fragment {
    private Spinner examTypeSpinner,examSubjectSpinner;
    private String selectedExamType,selecteExamSubject;
    private LinearLayout subjectLayout,otherSubjectsLayout;
    private EditText otherSubjectEditText;
    private EditText examYearEditText,totalQuestionNumbersEditText,examTime;
    private TextView errorShower;
    private Button registerExam;
    private boolean otherSelected= false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_admin_add_ent_model_exam,
                container, false);

        subjectLayout = view.findViewById(R.id.adminSubjectLayout);

        otherSubjectsLayout = view.findViewById(R.id.otherSubjectLayout);
        otherSubjectEditText = view.findViewById(R.id.otherSubjectEdit);

        //grade select spinner
        examTypeSpinner = view.findViewById(R.id.adminExamTypeSpinner);
        final String[] gradesArray = getResources().getStringArray(R.array.exam_type);
        ArrayAdapter<CharSequence> gradeAdapter = new ArrayAdapter<CharSequence>(getActivity(),
                R.layout.spinner_text, gradesArray );
        gradeAdapter.setDropDownViewResource(R.layout.simple_spinner_drop_down);
        examTypeSpinner.setAdapter(gradeAdapter);
        examTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = ((TextView)view).getText().toString();
                if(!text.equals(gradesArray[0])){
                    selectedExamType = text;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //subjects adapter
        examSubjectSpinner = view.findViewById(R.id.adminExamSubjectSpinner);
        final String[] subjectsArray = getResources().getStringArray(R.array.subjects);
        ArrayAdapter<CharSequence> subjectAdapter = new ArrayAdapter<CharSequence>(getActivity(),
                R.layout.spinner_text, subjectsArray );
        gradeAdapter.setDropDownViewResource(R.layout.simple_spinner_drop_down);
        examSubjectSpinner.setAdapter(subjectAdapter);
        examSubjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String subject = ((TextView)view).getText().toString();
                if(subject.equals("Other")){
                    subjectLayout.setVisibility(View.GONE);
                    otherSubjectsLayout.setVisibility(View.VISIBLE);
                    otherSelected = true;
                }else if (!subject.equals(subjectsArray[0])){
                    selecteExamSubject = subject;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        examYearEditText = view.findViewById(R.id.admin_exam_input_year);
        totalQuestionNumbersEditText = view.findViewById(R.id.adminExamTotalQuestionNumberEditText);

        examTime= view.findViewById(R.id.adminExamTime);

        errorShower = view.findViewById(R.id.errorShower);

        registerExam = view.findViewById(R.id.registerExamButton);
        registerExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otherSubject = otherSubjectEditText.getText().toString();
                String examYear = examYearEditText.getText().toString();
                String totalQuestionNumbers = totalQuestionNumbersEditText.getText().toString();
                String examTimeValue = examTime.getText().toString();
                if(otherSelected){
                    selecteExamSubject = otherSubject;
                }

                if (examYear.equals("")){
                    errorShower.setText("Please enter exam year");
                }else if (totalQuestionNumbers.equals("")){
                    errorShower.setText("Please enter total question numbers");
                }else if(selecteExamSubject==null&&otherSubject.equals("")){
                    errorShower.setText("Please select or add  tutorial subject");
                }else if (examTimeValue.equals("")){
                  errorShower.setText("Please enter exam time");
                } else {
                    Fragment newFragment = new RegisterQuestionsFragment(selectedExamType,
                            selecteExamSubject,Integer.valueOf(examYear),Integer.valueOf(totalQuestionNumbers),examTimeValue);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, newFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });

        return view;
    }



}
