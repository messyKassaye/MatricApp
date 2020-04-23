package com.students.preparation.matric.students.modules.Students.fragment.shortnotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.students.Constants;
import com.students.preparation.matric.students.adapter.NotesRecyclerViewAdapter;
import com.students.preparation.matric.students.roomDB.entity.Notes;
import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.modules.Students.fragment.AddShortNotesFragment;

import java.util.ArrayList;

public class MyShortnotesFragment extends Fragment {


    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private TextView noNotesFound;

    private NotesRecyclerViewAdapter adapter;
    private ArrayList<Notes> notesArrayList = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shortnotes, container, false);

        fab = root.findViewById(R.id.fab_add);

        noNotesFound = root.findViewById(R.id.noNotesFound);

        adapter = new NotesRecyclerViewAdapter(getContext(),notesArrayList);
        recyclerView = root.findViewById(R.id.notesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        //adding a click listener on list view


        //getting the database reference
        //database reference to get uploads data
        DatabaseReference mDatabaseReference = FirebaseDatabase
                .getInstance().getReference(Constants.DATABASE_PATH_MY_SHORTNOTES);

        //retrieving upload data from firebase database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Notes uploadsModel = postSnapshot.getValue(Notes.class);
                        if (uploadsModel.getPhone()!=null){
                            if (uploadsModel.getPhone().equals(Constants.getUniqueIdentifyer(getActivity()))){
                                notesArrayList.add(uploadsModel);
                            }
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }else {
                    noNotesFound.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddShortNotesFragment.class);
                startActivity(intent);
            }
        });
        return root;
    }

    private void showSuccessConfirmation(String title , String content) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(title);
        alert.setMessage(content);
        alert.setPositiveButton("OK", null);
        alert.show();
    }


}