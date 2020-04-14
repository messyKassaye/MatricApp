package com.students.preparation.matric.exam.modules.Students.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.adapter.TutorialRecyclerViewAdapter;
import com.students.preparation.matric.exam.model.StudentsModel;
import com.students.preparation.matric.exam.model.Tutorials;

import java.util.ArrayList;

public class TutorialsFragment extends Fragment {

    private ProgressBar progressBar;

    private RecyclerView recyclerView;
    private ArrayList<Tutorials> tutorialsArrayList = new ArrayList<>();
    private TutorialRecyclerViewAdapter adapter;

    private DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_tutorials, container, false);

        progressBar = view.findViewById(R.id.tutorialBar);

        adapter = new TutorialRecyclerViewAdapter(getContext(),tutorialsArrayList);
        recyclerView = view.findViewById(R.id.tutorialRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        databaseReference = FirebaseDatabase.getInstance()
                .getReference(Constants.DATABASE_PATH_TUTORIALS);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                for (DataSnapshot tutorialSnapshot : dataSnapshot.getChildren()) {
                    Tutorials uploadsModel = tutorialSnapshot.getValue(Tutorials.class);
                    tutorialsArrayList.add(uploadsModel);
                }
                System.out.println("Size: "+tutorialsArrayList.size());
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
