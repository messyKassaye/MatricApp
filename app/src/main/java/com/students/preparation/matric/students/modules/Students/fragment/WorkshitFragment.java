package com.students.preparation.matric.students.modules.Students.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.students.Constants;
import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.adapter.AdminShortNotesAdapter;
import com.students.preparation.matric.students.adapter.WorksheetAdapter;
import com.students.preparation.matric.students.model.NoteTipModel;
import com.students.preparation.matric.students.model.Worksheet;

import java.util.ArrayList;


public class WorkshitFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private RecyclerView recyclerView;
    private TextView noNotesFound;

    private WorksheetAdapter adapter;
    private ArrayList<Worksheet> notesArrayList = new ArrayList<>();
    DatabaseReference mDatabaseReference;

    public WorkshitFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_workshit, container, false);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_WORKSHEET);

        noNotesFound = view.findViewById(R.id.adminNoNotesFound);
        adapter = new WorksheetAdapter(getContext(),notesArrayList);
        recyclerView = view.findViewById(R.id.adminShortNotesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Worksheet uploadsModel = postSnapshot.getValue(Worksheet.class);
                        notesArrayList.add(uploadsModel);
                    }
                }else {
                    noNotesFound.setVisibility(View.VISIBLE);
                }

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }


}
