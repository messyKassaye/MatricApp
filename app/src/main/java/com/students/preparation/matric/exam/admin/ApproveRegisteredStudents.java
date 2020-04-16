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
import com.students.preparation.matric.exam.adapter.NewAndApprovedStudentAdapter;
import com.students.preparation.matric.exam.model.StudentsModel;

import java.util.ArrayList;
import java.util.List;

public class ApproveRegisteredStudents extends Fragment {
    private NewAndApprovedStudentAdapter adapter;
    private ArrayList<StudentsModel> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.activity_approve_registred_students,
                container, false);
        init(root);
        return root;
    }

    private void init(View view) {
        //studentsModelList = new ArrayList<>();
        adapter = new NewAndApprovedStudentAdapter(getContext(),arrayList,"new");
        recyclerView = view.findViewById(R.id.approveStudentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Populate the list view
        populateRegStudents();

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
                    arrayList.add(uploadsModel);
                }
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
