package com.students.preparation.matric.exam.modules.Students.fragment.studytips;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.adapter.AdminShortNotesAdapter;
import com.students.preparation.matric.exam.modules.Students.StudentDashboard;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.NoteTipModel;

import java.util.ArrayList;
import java.util.List;

public class StudytipsFragment extends Fragment {


    private RecyclerView recyclerView;
    private TextView noNotesFound;

    private AdminShortNotesAdapter adapter;
    private ArrayList<NoteTipModel> notesArrayList = new ArrayList<>();
    DatabaseReference mDatabaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_studytips, container, false);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_SHORTNOTES);

        noNotesFound = view.findViewById(R.id.adminNoStudyTipsFound);
        adapter = new AdminShortNotesAdapter(getContext(),notesArrayList);
        recyclerView = view.findViewById(R.id.adminStudyTipsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        NoteTipModel uploadsModel = postSnapshot.getValue(NoteTipModel.class);
                        if (uploadsModel.getType().equals("Study Tip")){
                            notesArrayList.add(uploadsModel);
                        }
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    noNotesFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void showSuccessConfirmation(String title , String content) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(title);
        alert.setMessage(content);
        alert.setPositiveButton("OK", null);
        alert.show();
    }
}