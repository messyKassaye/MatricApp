package com.students.preparation.matric.students.modules.Students.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.students.Constants;
import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.adapter.ReferenceBooksAdapter;
import com.students.preparation.matric.students.model.Books;

import java.util.ArrayList;


public class TeachersGuideFragment extends Fragment {

    //firebase variable
    private DatabaseReference dbReference;

    //Views
    private TextView noReferenceBooksFound;
    private RecyclerView _recyclerView;
    private String logedUserStream;
    private Spinner bookType;
    private String selectedBooksType;



    private ArrayList<Books> referenceBooks = new ArrayList<>();
    private ReferenceBooksAdapter adapter;

    public TeachersGuideFragment() {
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
        final View view= inflater.inflate(R.layout.fragment_teachers_guide, container, false);

        //initialize views
        noReferenceBooksFound = view.findViewById(R.id.notReferenceBooksFound);

        adapter = new ReferenceBooksAdapter(getContext(),referenceBooks);
        _recyclerView = view.findViewById(R.id.referenceRecyclerView);
        _recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        _recyclerView.setItemAnimator(new DefaultItemAnimator());



        //populating registered book references
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        logedUserStream = prefs.getString(Constants.LOGGED_IN_USER_STREAM, null);

        dbReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_BOOKS);
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        Books uploadsModel = dataSnapshot1.getValue(Books.class);

                        if (uploadsModel.getStream().equals(logedUserStream)){
                            if (uploadsModel.getBookType().equalsIgnoreCase("Teachers Guide")){
                                referenceBooks.add(uploadsModel);
                            }
                        }
                    }
                    _recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    if (referenceBooks.size()<=0){
                        noReferenceBooksFound.setText("Teachers Guide is not found ):");
                        noReferenceBooksFound.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }


}
