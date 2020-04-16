package com.students.preparation.matric.exam.admin;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.adapter.NewAndApprovedStudentAdapter;
import com.students.preparation.matric.exam.model.StudentsModel;

import java.util.ArrayList;
import java.util.List;

public class ViewApprovedStudents extends Fragment {

    //the listview
    //ListView listView;

    //list to store uploads data
    //List<StudentsModel> studentsModelList;

    private NewAndApprovedStudentAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<StudentsModel> arrayList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.activity_view_uproved_students,
                container, false);
        init(root);
        return root;
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_uproved_students);

        init();

    }*/

    private void init(View view) {
        //studentsModelList = new ArrayList<>();

        adapter = new NewAndApprovedStudentAdapter(getContext(),arrayList,"approved");
        recyclerView = view.findViewById(R.id.viewApproveStudentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Populate the list view
        populateRegStudents();
/*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final StudentsModel uploadsModel = studentsModelList.get(position);

                //Confirm Approval


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle("Reject Approval?");
                builder.setMessage("Do you want to reject " + uploadsModel.get_fullName() + "?");


                builder.setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });


                builder.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeApprovedStudent(uploadsModel);

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });*/
    }

    private void removeApprovedStudent(StudentsModel uploadsModel) {
        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_APPROVED_STUDENTS)
                .child(uploadsModel.get_studentId());

        mPostReference.getRef().removeValue();

        //listView.setAdapter(null);
        //populateRegStudents();

        init(getView());
    }

    private void populateRegStudents() {
        //getting the database reference
        //database reference to get uploads data
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_APPROVED_STUDENTS);


        //retrieving upload data from firebase database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    StudentsModel uploadsModel = postSnapshot.getValue(StudentsModel.class);
                    arrayList.add(uploadsModel);
                }

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                /*String[] uploads = new String[studentsModelList.size()];

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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, uploads){
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
                listView.setAdapter(adapter);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
