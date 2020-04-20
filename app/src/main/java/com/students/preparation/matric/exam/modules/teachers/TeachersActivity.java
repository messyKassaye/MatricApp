package com.students.preparation.matric.exam.modules.teachers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.students.preparation.matric.exam.admin.ViewUploadsActivity;
import com.students.preparation.matric.exam.model.TeachersModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class TeachersActivity  extends Fragment implements View.OnClickListener {

    //this is the pic pdf code used in file chooser
    final static int PICK_PDF_CODE = 2343;

    //these are the views
    TextView textViewStatus;
    EditText documentTitle;
    ProgressBar progressBar;

    //the fire base objects for storage and database
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    Spinner stream, subject, grade;
    private AppCompatAutoCompleteTextView school;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final  View view = inflater.inflate(R.layout.activity_teachers,container,false);

    school = view.findViewById(R.id.teachers_school);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, Constants.schoolList);
        school.setThreshold(1); //will start working from first character
        school.setAdapter(adapter);

        //getting firebase objects
        mStorageReference = FirebaseStorage.getInstance().getReference();

        //getting the views
        textViewStatus = (TextView) view.findViewById(R.id.textViewStatus);
        documentTitle = (EditText) view.findViewById(R.id.teachers_input_book_title);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        //attaching listeners to views
        view.findViewById(R.id.teachersButtonUploadFile).setOnClickListener(this);
        view.findViewById(R.id.teachersTextViewUploads).setOnClickListener(this);

        stream = view.findViewById(R.id.teachers_stream);
        grade = view.findViewById(R.id.teachers_grade);
        //type = findViewById(R.id.teachers_type);
        subject = view.findViewById(R.id.teachers_subject);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_TEACHERS);
        return view;

    }

    //this function will get the pdf from the storage
    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getActivity().getPackageName()));
            startActivity(intent);
            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                uploadFile(data.getData());
            } else {
                Toast.makeText(getContext(), "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //this method is uploading the file
    //the code is same as the previous tutorial
    //so we are not explaining it
    private void uploadFile(Uri data) {
        progressBar.setVisibility(View.VISIBLE);
        final StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");


        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressBar.setVisibility(View.GONE);
                        textViewStatus.setText("File Uploaded Successfully");
                        Toast.makeText(getContext(), "UploadsModel success!", Toast.LENGTH_SHORT).show();


                        sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUrl) {
                                //Toast.makeText(getBaseContext(), "UploadsModel success! URL - " + downloadUrl.toString(), Toast.LENGTH_SHORT).show();

                                String timestamp = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss").format(new Date());
                                //Log.d("StudentDashboard", "Current Timestamp: " + format);

                                TeachersModel tachersModel = new TeachersModel(documentTitle.getText().toString(), downloadUrl.toString(), stream.getSelectedItem().toString(), grade.getSelectedItem().toString(),school.getText().toString(), subject.getSelectedItem().toString() , timestamp);
                                mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(tachersModel);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        textViewStatus.setText((int) progress + "% Uploading...");
                    }
                });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.teachersButtonUploadFile:
                if(subject.getSelectedItemPosition() !=0) {

                dbPath();
                getPDF();
        }   else{
                Toast.makeText(getContext(), "Please check Subject", Toast.LENGTH_LONG).show();
            }
                break;
            case R.id.teachersTextViewUploads:
                startActivity(new Intent(getContext(), ViewUploadsActivity.class));
                break;
        }
    }

    private void dbPath() {

        if (stream.getSelectedItemPosition() == 0) {

            if (grade.getSelectedItemPosition() == 0) {
                mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_TEACHERS + "/" + Constants.DATABASE_PATH_NATURAL + "_" + Constants.DATABASE_PATH_GRADE_11);
            } else {
                mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_TEACHERS + "/" + Constants.DATABASE_PATH_NATURAL + "_" + Constants.DATABASE_PATH_GRADE_12);
            }
        } else {
            if (grade.getSelectedItemPosition() == 0) {
                mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_TEACHERS + "/" + Constants.DATABASE_PATH_SOCIAL + "_" + Constants.DATABASE_PATH_GRADE_11);
            } else {
                mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_TEACHERS + "/" + Constants.DATABASE_PATH_SOCIAL + "_" + Constants.DATABASE_PATH_GRADE_12);
            }
        }

    }

}