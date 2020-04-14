package com.students.preparation.matric.exam.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import com.students.preparation.matric.exam.model.EntranceExamModel;

import static android.app.Activity.RESULT_OK;

public class AddEntModelExam extends Fragment implements View.OnClickListener {

    final static int PICK_Q_IMAGE_CODE = 2343;

    Uri uploadingData;
    //these are the views
    TextView textViewStatus;

    EditText admin_exam_input_year;
    EditText admin_exam_input_question;
    EditText admin_exam_input_choice1;
    EditText admin_exam_input_choice2;
    EditText admin_exam_input_choice3;
    EditText admin_exam_input_choice4;
    EditText admin_exam_input_answer_explanation;
    Spinner admin_exam_subjects, admin_exam_answer_choice, e_type;

    ProgressBar progressBar;

    //the fire base objects for storage and database
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final  View view = inflater.inflate(R.layout.activity_admin_add_ent_model_exam,
                container,false);


        admin_exam_input_year = view.findViewById(R.id.admin_exam_input_year);
        admin_exam_input_question = view.findViewById(R.id.admin_exam_input_question);
        admin_exam_input_choice1 = view.findViewById(R.id.admin_exam_input_choice1);
        admin_exam_input_choice2 = view.findViewById(R.id.admin_exam_input_choice2);
        admin_exam_input_choice3 = view.findViewById(R.id.admin_exam_input_choice3);
        admin_exam_input_choice4 = view.findViewById(R.id.admin_exam_input_choice4);
        admin_exam_input_answer_explanation = view.findViewById(R.id.admin_exam_input_answer_explanation);
        admin_exam_subjects = view.findViewById(R.id.admin_exam_subjects);
        admin_exam_answer_choice = view.findViewById(R.id.admin_exam_answer_choice);

        textViewStatus = view.findViewById(R.id.admin_exam_button_text_view_status);

        progressBar = view.findViewById(R.id.admin_exam_button_progressbar);

        //attaching listeners to views
        view.findViewById(R.id.admin_exam_button_upload_image).setOnClickListener(this);
        view.findViewById(R.id.admin_exam_button_add_question).setOnClickListener(this);

        mStorageReference = FirebaseStorage.getInstance().getReference();


        e_type = view.findViewById(R.id.admin_exam_type);
        /*
        e_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_ENTRANCE_EXAM + "/" + e_type.getSelectedItem().toString() + admin_exam_input_year.getText());

                } else if (position == 2) {
                    mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_MODEL_EXAM + "/" + e_type.getSelectedItem().toString() + admin_exam_input_year.getText());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */

return view;
    }
    private void getImage() {
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
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_Q_IMAGE_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_Q_IMAGE_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                uploadingData = data.getData();
                textViewStatus.setText("Image is Selected");
            } else {
                Toast.makeText(getActivity(), "No file chosen", Toast.LENGTH_SHORT).show();

                uploadingData = null;
            }
        }
    }

    private void addQuestionToDB(Uri data) {
        progressBar.setVisibility(View.VISIBLE);


        if (data != null) {
            final StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".jpg");


            sRef.putFile(data)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @SuppressWarnings("VisibleForTests")
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            textViewStatus.setText("Image Uploaded Successfully");
                            Toast.makeText(getActivity(), "UploadsModel success! URL - OLD", Toast.LENGTH_SHORT).show();


                            sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUrl) {
                                    Toast.makeText(getActivity(), "UploadsModel success! URL - " + downloadUrl.toString(), Toast.LENGTH_SHORT).show();

                                    addTheQuestion(downloadUrl);
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
        } else {
            Uri uri = null;
            addTheQuestion(uri);

        }
    }

    private void addTheQuestion(Uri downloadUrl) {
        String eId = mDatabaseReference.push().getKey();
        String uri = "null";
        if (downloadUrl != null) {
            uri = downloadUrl.toString();
        }

        EntranceExamModel uploadsModel = new EntranceExamModel(
                eId,
                admin_exam_subjects.getSelectedItem().toString(),
                admin_exam_input_year.getText().toString(),
                admin_exam_input_question.getText().toString(),
                admin_exam_input_choice1.getText().toString(),
                admin_exam_input_choice2.getText().toString(),
                admin_exam_input_choice3.getText().toString(),
                admin_exam_input_choice4.getText().toString(),
                uri,
                String.valueOf(admin_exam_answer_choice.getSelectedItemPosition()),
                admin_exam_input_answer_explanation.getText().toString());


        if (eId != null) {
            mDatabaseReference.child(eId).setValue(uploadsModel);
            progressBar.setVisibility(View.GONE);

            //SUCCESS MSG
            showSuccessConfirmation();
        }else {
            Toast.makeText(getActivity() , "Please Try Again" , Toast.LENGTH_LONG).show();
        }


    }

    private void showSuccessConfirmation() {
        Toast.makeText(getActivity() , "Question Inserted Successfully" , Toast.LENGTH_LONG).show();

        uploadingData = null;

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Success");
        alert.setMessage("Question Inserted Successfully");
        alert.setPositiveButton("OK", null);
        alert.show();

        //Reset Text
        admin_exam_input_question.setText("");
        admin_exam_input_answer_explanation.setText("");
        admin_exam_input_choice4.setText("");
        admin_exam_input_choice3.setText("");
        admin_exam_input_choice2.setText("");
        admin_exam_input_choice1.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.admin_exam_button_upload_image:
                getImage();
                break;
            case R.id.admin_exam_button_add_question:
                //startActivity(new Intent(this, ViewUploadsActivity.class));
                //addQuestionToDB();
                if(e_type.getSelectedItemPosition()!=0 && admin_exam_subjects.getSelectedItemPosition() != 0 && admin_exam_input_year.getText().length() != 0 && admin_exam_input_question.getText().length() != 0 && admin_exam_input_choice1.getText().length() != 0 && admin_exam_input_choice2.getText().length() != 0 && admin_exam_input_choice3.getText().length() != 0 && admin_exam_input_choice4.getText().length() != 0 && admin_exam_answer_choice.getSelectedItemPosition() != 0) {
                    if (e_type.getSelectedItemPosition() == 1) {
                        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_ENTRANCE_EXAM + "/" + admin_exam_subjects.getSelectedItem().toString() + "/" + admin_exam_input_year.getText());
                        addQuestionToDB(uploadingData);

                    } else if (e_type.getSelectedItemPosition() == 2) {
                        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_MODEL_EXAM + "/" + admin_exam_subjects.getSelectedItem().toString() + "/" + admin_exam_input_year.getText());
                        addQuestionToDB(uploadingData);

                    }
                }else{
                    Toast.makeText(getActivity() , "Please check the form again, and fill all the required information." , Toast.LENGTH_LONG).show();

                }

                break;
        }
    }
}
