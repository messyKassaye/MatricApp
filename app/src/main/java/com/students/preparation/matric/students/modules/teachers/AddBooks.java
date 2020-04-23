package com.students.preparation.matric.students.modules.teachers;


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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.students.preparation.matric.students.Constants;
import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.model.UploadsModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class AddBooks extends Fragment implements View.OnClickListener {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       final View view =  inflater.inflate(R.layout.teachers_add_books,
               container,false);


        //getting firebase objects
        mStorageReference = FirebaseStorage.getInstance().getReference();

        //getting the views
        textViewStatus = view.findViewById(R.id.textViewStatus);
        documentTitle = view.findViewById(R.id.admin_input_book_title);
        progressBar = view.findViewById(R.id.progressbar);

        //attaching listeners to views
        view.findViewById(R.id.buttonUploadFile).setOnClickListener(this);
        view.findViewById(R.id.textViewUploads).setOnClickListener(this);

        stream = view.findViewById(R.id.admin_stream);
        grade = view.findViewById(R.id.admin_grade);
        type = view.findViewById(R.id.admin_type);
        subject = view.findViewById(R.id.admin_subject);


        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               // Toast.makeText(getApplicationContext() , "POS: " + position , Toast.LENGTH_LONG).show();
                 if (position == 1) {
                    //mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_TEACHERS_GUIDE);
                    typeSelected = Constants.DATABASE_PATH_TEACHERS_GUIDE;

                } else {
                     Toast.makeText(getContext(),"Please select book type",Toast.LENGTH_LONG).show();
                 }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    //this function will get the pdf from the storage
    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getActivity(),
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
                Toast.makeText(getActivity(), "No file chosen", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), "UploadsModel success!", Toast.LENGTH_SHORT).show();


                        sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUrl) {
                                //Toast.makeText(getBaseContext(), "UploadsModel success! URL - " + downloadUrl.toString(), Toast.LENGTH_SHORT).show();

                                String timestamp = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss")
                                        .format(new Date());
                                //Log.d("StudentDashboard", "Current Timestamp: " + format);

                                UploadsModel uploadsModel = new UploadsModel(
                                        documentTitle.getText().toString(),
                                        downloadUrl.toString(),
                                        stream.getSelectedItem().toString(),
                                        grade.getSelectedItem().toString(),
                                        type.getSelectedItem().toString(),
                                        subject.getSelectedItem().toString(),
                                        timestamp);
                                mDatabaseReference.child(mDatabaseReference.push().getKey())
                                        .setValue(uploadsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        textViewStatus.setText("This reference book is " +
                                                "registered successfully");
                                    }
                                });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getActivity(), exception.getMessage(),
                                Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity() , "Please check the form again, and fill all the required information." , Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void dbPath() {

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_REFERENCE_BOOKS);
    }
}