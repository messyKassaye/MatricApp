package com.students.preparation.matric.exam.modules.Students.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.adapter.ReferenceBooksAdapter;
import com.students.preparation.matric.exam.model.UploadsModel;

import java.util.ArrayList;


public class ReferenceBooksFragment extends Fragment {

    //firebase variable
    private DatabaseReference dbReference;

    //Views
    private TextView noReferenceBooksFound;
    private RecyclerView _recyclerView;
    private String logedUserStream;
    private Spinner bookType;
    private String selectedBooksType;



    private ArrayList<UploadsModel> textBooksReference = new ArrayList<>();
    private ArrayList<UploadsModel> teachersGuidReference = new ArrayList<>();
    private ArrayList<UploadsModel> adminInboxReference = new ArrayList<>();
    private ReferenceBooksAdapter adapter;


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
        noReferenceBooksFound = view.findViewById(R.id.notReferenceBooksFound);

        _recyclerView = view.findViewById(R.id.referenceRecyclerView);
        _recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        _recyclerView.setItemAnimator(new DefaultItemAnimator());

        bookType = view.findViewById(R.id.booksType);
        final String[] streamArray = getResources().getStringArray(R.array.booksType);

        ArrayAdapter<CharSequence> streamAdapter = new ArrayAdapter<CharSequence>(getActivity(),
                R.layout.spinner_text, streamArray );
        streamAdapter.setDropDownViewResource(R.layout.simple_spinner_drop_down);
        bookType.setAdapter(streamAdapter);
        bookType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = ((TextView)view).getText().toString();
                if(!text.equals(streamArray[0])){
                    if (text.equals("Textbook")){
                        System.out.println("type: "+text);
                        adapter = new ReferenceBooksAdapter(getContext(),textBooksReference);
                        _recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else if (text.equals("Teachers Guide")){
                        adapter = new ReferenceBooksAdapter(getContext(),teachersGuidReference);
                        _recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else if (text.equals("Admin Inbox")){
                        adapter = new ReferenceBooksAdapter(getContext(),adminInboxReference);
                        _recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //populating registered book references
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        logedUserStream = prefs.getString(Constants.LOGGED_IN_USER_STREAM, null);

        dbReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_REFERENCE_BOOKS);
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        UploadsModel uploadsModel = dataSnapshot1.getValue(UploadsModel.class);

                        if (uploadsModel.getStream().equals(logedUserStream)){

                            if (uploadsModel.getType().equals("Textbook")){
                                textBooksReference.add(uploadsModel);
                            }else if(uploadsModel.getType().equals("Teachers Guide")){
                                teachersGuidReference.add(uploadsModel);
                            }else if (uploadsModel.getType().equals("Admin Inbox")){
                                adminInboxReference.add(uploadsModel);
                            }

                        }
                    }
                    System.out.println("Text Size: "+textBooksReference.size());
                }else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }


}