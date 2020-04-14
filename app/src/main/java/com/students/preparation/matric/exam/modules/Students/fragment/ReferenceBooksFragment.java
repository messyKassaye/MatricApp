package com.students.preparation.matric.exam.modules.Students.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.UploadsModel;


public class ReferenceBooksFragment extends Fragment {

    //firebase variable
    private DatabaseReference dbReference;

    //Views
    private ProgressBar _progressBar;
    private TextView noReferenceBooksFound;
    private RecyclerView _recyclerView;


    public ReferenceBooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_reference_books, container, false);

        //initialize views

        _progressBar = view.findViewById(R.id.referenceBooksLoader);

        noReferenceBooksFound = view.findViewById(R.id.notReferenceBooksFound);

        _recyclerView = view.findViewById(R.id.referenceRecyclerView);

        //populating registered book references
        populateReferenceBooks();
        return view;
    }


    public void populateReferenceBooks(){

        dbReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_REFERENCE_BOOKS);
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                _progressBar.setVisibility(View.GONE);

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    UploadsModel uploadsModel = dataSnapshot1.getValue(UploadsModel.class);
                    System.out.println("Reference: "+uploadsModel.getTitle());
                }

                if (dataSnapshot.exists()){
                    //_recyclerView.setVisibility(View.VISIBLE);
                }else {
                    noReferenceBooksFound.setVisibility(View.VISIBLE);
                    noReferenceBooksFound.setText("There is no registered reference books until now.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
