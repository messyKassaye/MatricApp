package com.students.preparation.matric.exam.admin;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import com.students.preparation.matric.exam.model.UploadsModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddReferences extends AppCompatActivity implements View.OnClickListener {

    //this is the pic pdf code used in file chooser
    final static int PICK_PDF_CODE = 2342;

    //these are the views
    TextView textViewStatus;
    EditText documentTitle;
    ProgressBar progressBar;

    //the fire base objects for storage and database
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    Spinner stream, type, subject, grade;
    String typeSelected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_references);


        //getting firebase objects
        mStorageReference = FirebaseStorage.getInstance().getReference();

        //getting the views
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        documentTitle = (EditText) findViewById(R.id.admin_input_book_title);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        //attaching listeners to views
        findViewById(R.id.buttonUploadFile).setOnClickListener(this);
        findViewById(R.id.textViewUploads).setOnClickListener(this);

        stream = findViewById(R.id.admin_stream);
        grade = findViewById(R.id.admin_grade);
        type = findViewById(R.id.admin_type);
        subject = findViewById(R.id.admin_subject);


        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               // Toast.makeText(getApplicationContext() , "POS: " + position , Toast.LENGTH_LONG).show();
                if (position == 1) {
                    //mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_TEXTBOOKS);
                    typeSelected = Constants.DATABASE_PATH_TEXTBOOKS;

                } else if (position == 2) {
                    //mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_TEACHERS_GUIDE);
                    typeSelected = Constants.DATABASE_PATH_TEACHERS_GUIDE;

                } else if (position == 3) {
                    //mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_ADMIN_UPLOADS);
                    typeSelected = Constants.DATABASE_PATH_ADMIN_UPLOADS;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //this function will get the pdf from the storage
    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                uploadFile(data.getData());
            } else {
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getBaseContext(), "UploadsModel success!", Toast.LENGTH_SHORT).show();


                        sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUrl) {
                                //Toast.makeText(getBaseContext(), "UploadsModel success! URL - " + downloadUrl.toString(), Toast.LENGTH_SHORT).show();

                                String timestamp = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss").format(new Date());
                                //Log.d("MainActivity", "Current Timestamp: " + format);

                                UploadsModel uploadsModel = new UploadsModel(documentTitle.getText().toString(), downloadUrl.toString(), stream.getSelectedItem().toString(), grade.getSelectedItem().toString(), type.getSelectedItem().toString(), subject.getSelectedItem().toString(), timestamp);
                                mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(uploadsModel);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
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
            case R.id.buttonUploadFile:
                if(documentTitle.getText().length() != 0 && type.getSelectedItemPosition() != 0 && subject.getSelectedItemPosition() !=0) {
                    dbPath();
                    getPDF();

                }else{
                    Toast.makeText(getApplicationContext() , "Please check the form again, and fill all the required information." , Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.textViewUploads:
                startActivity(new Intent(this, ViewUploadsActivity.class));
                break;
        }
    }

    private void dbPath() {

        if (stream.getSelectedItemPosition() == 0) {

            if (grade.getSelectedItemPosition() == 0) {
                mDatabaseReference = FirebaseDatabase.getInstance().getReference(typeSelected + "/" + Constants.DATABASE_PATH_NATURAL + "_" + Constants.DATABASE_PATH_GRADE_11);
            } else {
                mDatabaseReference = FirebaseDatabase.getInstance().getReference(typeSelected + "/" + Constants.DATABASE_PATH_NATURAL + "_" + Constants.DATABASE_PATH_GRADE_12);
            }
        } else {
            if (grade.getSelectedItemPosition() == 0) {
                mDatabaseReference = FirebaseDatabase.getInstance().getReference(typeSelected + "/" + Constants.DATABASE_PATH_SOCIAL + "_" + Constants.DATABASE_PATH_GRADE_11);
            } else {
                mDatabaseReference = FirebaseDatabase.getInstance().getReference(typeSelected + "/" + Constants.DATABASE_PATH_SOCIAL + "_" + Constants.DATABASE_PATH_GRADE_12);
            }
        }

    }
}