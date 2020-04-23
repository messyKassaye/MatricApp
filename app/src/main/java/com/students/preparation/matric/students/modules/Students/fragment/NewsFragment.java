package com.students.preparation.matric.students.modules.Students.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.students.Constants;
import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.adapter.NewsRecyclerViewAdapter;
import com.students.preparation.matric.students.model.News;

import java.util.ArrayList;


public class NewsFragment extends Fragment {

    private TextView noNews;
    private ProgressBar progressBar;

    private RecyclerView recyclerView;
    private ArrayList<News> newsArrayList = new ArrayList<>();
    private NewsRecyclerViewAdapter adapter;
    private DatabaseReference databaseReference;


    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_news, container, false);

        databaseReference = FirebaseDatabase
                .getInstance().getReference(Constants.DATABASE_PATH_NEWS);

        progressBar = view.findViewById(R.id.newsProgressBar);

        noNews = view.findViewById(R.id.noNews);

        adapter = new NewsRecyclerViewAdapter(getContext(),newsArrayList);
        recyclerView = view.findViewById(R.id.newRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    for (DataSnapshot tutorialSnapshot : dataSnapshot.getChildren()) {
                        News uploadsModel = tutorialSnapshot.getValue(News.class);
                        newsArrayList.add(uploadsModel);
                    }

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    noNews.setText("There is no uploaded news");
                }

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
