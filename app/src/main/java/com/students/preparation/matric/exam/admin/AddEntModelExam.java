package com.students.preparation.matric.exam.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
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
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.EntranceExamModel;
import com.students.preparation.matric.exam.model.Exams;

import java.io.File;

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

    private LinearLayout adminExamMainLayout,additionMethodsLayout;
    private Exams examsData;
    private Button registerQuestion,bringFromFile;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private TextView uploadProgress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_admin_add_ent_model_exam,
                container, false);

        storageReference = FirebaseStorage.getInstance().getReference();


        subjectLayout = view.findViewById(R.id.adminSubjectLayout);

        adminExamMainLayout = view.findViewById(R.id.adminExamMainLayout);
        additionMethodsLayout = view.findViewById(R.id.examAdditionMethodsLayout);
        examsData = new Exams();
        registerQuestion = view.findViewById(R.id.registerQuestions);
        bringFromFile = view.findViewById(R.id.bringFromFile);
        uploadProgress = view.findViewById(R.id.updateInfo);

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

        errorShower = view.findViewById(R.id.adminExamErrorShowerText);

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
                    examsData.setExamType(selectedExamType);
                    examsData.setExamSubject(selecteExamSubject);
                    examsData.setExamYear(Integer.valueOf(examYear));
                    examsData.setTotalQuestionNumber(Integer.valueOf(totalQuestionNumbers));
                    examsData.setExamTime(examTimeValue);

                    adminExamMainLayout.setVisibility(View.GONE);
                    additionMethodsLayout.setVisibility(View.VISIBLE);



                    /**/
                }
            }
        });

        registerQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new RegisterQuestionsFragment(examsData.getExamType(),
                        examsData.getExamSubject(),examsData.getExamYear(),
                        examsData.getTotalQuestionNumber(),examsData.getExamTime());
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, newFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        bringFromFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ChooserDialog(getActivity())
                        .withFilter(false,true,"json")
                        .withChosenListener(new ChooserDialog.Result() {
                            @Override
                            public void onChoosePath(String path, File pathFile) {
                                File file = new File(path);
                                handleReceiveFile(file,examsData.getExamSubject()+"_"+examsData.getExamYear()+".json");
                            }
                        })
                        // to handle the back key pressed or clicked outside the dialog:
                        .withOnCancelListener(new DialogInterface.OnCancelListener() {
                            public void onCancel(DialogInterface dialog) {
                                Toast.makeText(getContext(),"File selection canceled",
                                        Toast.LENGTH_LONG).show();
                                dialog.cancel(); // MUST have
                            }
                        })
                        .build()
                        .show();

            }
        });

        return view;
    }


    public void handleReceiveFile(File file,String examName){

        final StorageReference storageRef = storageReference.child(Constants.EXAM_FILES_PATH+"/"+examName);

        Uri uri = Uri.fromFile(file);
        storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploadProgress.setText("Your exam is uploaded successfully. Now we are updating your exam database...");
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Exams exams = new Exams();
                        exams.setExamType(examsData.getExamType());
                        exams.setExamSubject(examsData.getExamSubject());
                        exams.setExamYear(examsData.getExamYear());
                        exams.setJsonDownloadUrl(uri.toString());
                        exams.setFileName(examName);
                        exams.setTotalQuestionNumber(examsData.getTotalQuestionNumber());
                        exams.setExamTime(examsData.getExamTime());
                        storeNewExam(exams);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @SuppressWarnings("VisibleForTests")
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                uploadProgress.setText((int) progress + "% Uploading...");
                uploadProgress.setTextColor(Color.GREEN);
                uploadProgress.setGravity(Gravity.CENTER);
            }
        });
    }

    public void storeNewExam(Exams exams){
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.EXAM_FILES_PATH);
        String examId = databaseReference.push().getKey();
        databaseReference.child(examId).setValue(exams)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        uploadProgress.setText("Database updated successfully");
                        uploadProgress.setTextColor(Color.GREEN);
                    }
                });
    }

}
