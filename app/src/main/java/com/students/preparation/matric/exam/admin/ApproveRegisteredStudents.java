package com.students.preparation.matric.exam.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.StudentsModel;

import java.util.ArrayList;
import java.util.List;

public class ApproveRegisteredStudents extends AppCompatActivity {

    //the listview
    ListView listView;

    //list to store uploads data
    List<StudentsModel> studentsModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_registred_students);

        init();


    }

    private void init() {
        studentsModelList = new ArrayList<>();
        listView = findViewById(R.id.list_admin_reg_stu);

        //Populate the list view
        populateRegStudents();


        //adding a click listener on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the uploadsModel

                final StudentsModel uploadsModel = studentsModelList.get(i);

                //Confirm Approval


                AlertDialog.Builder builder = new AlertDialog.Builder(ApproveRegisteredStudents.this);
                builder.setCancelable(true);
                builder.setTitle("Are You Sure?");
                builder.setMessage("Do you want to Approve " + uploadsModel.get_fullName() + "?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Approve Student
                                registerStudentToDatabase(uploadsModel);

                                //Delete from registration database


                            }
                        });

                builder.setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });


                builder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeRegStudent(uploadsModel);

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    private void removeRegStudent(StudentsModel uploadsModel) {
        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_REG_STUDENTS)
                .child(uploadsModel.get_studentId());

        mPostReference.getRef().removeValue();

        //listView.setAdapter(null);
        //populateRegStudents();
        init();
    }

    private void populateRegStudents() {
        //getting the database reference
        //database reference to get uploads data
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_REG_STUDENTS);


        //retrieving upload data from firebase database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    StudentsModel uploadsModel = postSnapshot.getValue(StudentsModel.class);
                    studentsModelList.add(uploadsModel);
                }

                String[] uploads = new String[studentsModelList.size()];

                for (int i = 0; i < uploads.length; i++) {
                    uploads[i] =
                            "Name: " + studentsModelList.get(i).get_fullName() + "\n" +
                                    "Mob: " + studentsModelList.get(i).get_mobileNumber() + "\n" +
                                    "School: " + studentsModelList.get(i).get_school() + "\n" +
                                    "Bank: " + studentsModelList.get(i).get_bank() + "\n" +
                                    "TxR: " + studentsModelList.get(i).get_txRefNum() + "\n" +
                                    "Stream: " + studentsModelList.get(i).get_stream();

                }

                //displaying it to list
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        View view = super.getView(position, convertView, parent);
                        TextView text = (TextView) view.findViewById(android.R.id.text1);

                        //if (flag == True) {
                        text.setTextColor(Color.BLACK);
                        //}

                        return view;
                    }
                };
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void registerStudentToDatabase(final StudentsModel registrationModel) {

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_APPROVED_STUDENTS);

        //create a new id for new student
        //String sid = mDatabaseReference.push().getKey();

        //if (sid != null) {
        mDatabaseReference.child(registrationModel.get_studentId()).setValue(registrationModel)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Approval Failed Please Try Again Latter", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        /*DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_REG_STUDENTS)
                                .child(registrationModel.get_studentId());

                        mPostReference.getRef().removeValue();

                        //Update listview
                        listView.setAdapter(null);
                        populateRegStudents();*/

                        removeRegStudent(registrationModel);


                    }
                });


        // }


    }


}
