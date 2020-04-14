package com.students.preparation.matric.exam.admin;


import android.net.Uri;
import android.os.Bundle;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.NoteTipModel;
import com.students.preparation.matric.exam.model.UploadsModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNoteTipsReferences extends Fragment implements View.OnClickListener {


    //these are the views
    TextView textViewStatus;
    EditText documentTitle, documentContent;
    ProgressBar progressBar;

    //the fire base objects for storage and database
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    Spinner stream, type, subject, grade;
    String typeSelected = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       final View view = inflater.inflate(R.layout.activity_admin_add_note_tip_references,container,
               false);


        //getting firebase objects
        mStorageReference = FirebaseStorage.getInstance().getReference();

        //getting the views
        textViewStatus = (TextView) view.findViewById(R.id.textViewStatus);
        documentTitle = (EditText) view.findViewById(R.id.admin_input_book_title);
        documentContent = (EditText) view.findViewById(R.id.admin_content_);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        //attaching listeners to views
        view.findViewById(R.id.btn_admin_submit).setOnClickListener(this);
        //findViewById(R.id.textViewUploads).setOnClickListener(this);

        stream = view.findViewById(R.id.admin_stream);
        grade = view.findViewById(R.id.admin_grade);
        type = view.findViewById(R.id.admin_type);
        subject = view.findViewById(R.id.admin_subject);

        //submit = findViewById(R.id.btn_admin_submit)

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    //mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_SHORTNOTES);
                    typeSelected = Constants.DATABASE_PATH_SHORTNOTES;

                } else if (position == 2) {
                    //mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_STUDYTIPS);
                    typeSelected = Constants.DATABASE_PATH_STUDYTIPS;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
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

                                String timestamp = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss").format(new Date());
                                //Log.d("StudentDashboard", "Current Timestamp: " + format);

                                UploadsModel uploadsModel = new UploadsModel(documentTitle.getText().toString(), downloadUrl.toString(), stream.getSelectedItem().toString(), grade.getSelectedItem().toString(), type.getSelectedItem().toString(), subject.getSelectedItem().toString(), timestamp);
                                mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(uploadsModel);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
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
            case R.id.btn_admin_submit:
                //dbPath();
                //getPDF();

                if(documentContent.getText().length() != 0 && documentTitle.getText().length() != 0 && type.getSelectedItemPosition() != 0 && subject.getSelectedItemPosition() !=0) {
                    mDatabaseReference = FirebaseDatabase.getInstance().getReference(typeSelected);

                    NoteTipModel noteTipsModel = new NoteTipModel(documentTitle.getText().toString(), stream.getSelectedItem().toString(), grade.getSelectedItem().toString(), type.getSelectedItem().toString(), subject.getSelectedItem().toString(), documentContent.getText().toString());
                    Task<Void> xx = mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(noteTipsModel);
                    xx.addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            alert.setTitle("Success");
                            alert.setMessage("Inserted Successfully");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                        }
                    });
                }else{
                    Toast.makeText(getActivity() , "Please check the form again, and fill all the required information." , Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.textViewUploads:
                //startActivity(new Intent(this, ViewUploadsActivity.class));
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