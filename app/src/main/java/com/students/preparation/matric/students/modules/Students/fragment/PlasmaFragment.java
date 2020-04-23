package com.students.preparation.matric.students.modules.Students.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.students.Constants;
import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.adapter.PlasmaAdapter;
import com.students.preparation.matric.students.model.Tutorials;

import java.util.ArrayList;


public class PlasmaFragment extends Fragment {
    private ProgressBar progressBar;
    protected RecyclerView recyclerView;
    private PlasmaAdapter adapter;
    private ArrayList<Tutorials> arrayList = new ArrayList<>();

    private DatabaseReference databaseReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_plasma, container, false);

        progressBar = view.findViewById(R.id.plasmaBar);

        adapter = new PlasmaAdapter(getContext(),arrayList);
        recyclerView = view.findViewById(R.id.plasmaRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        databaseReference = FirebaseDatabase.getInstance()
                .getReference(Constants.DATABASE_PATH_PLASMA);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                for (DataSnapshot tutorialSnapshot : dataSnapshot.getChildren()) {
                    Tutorials uploadsModel = tutorialSnapshot.getValue(Tutorials.class);
                    arrayList.add(uploadsModel);
                }
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

}
