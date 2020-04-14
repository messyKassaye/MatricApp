package com.students.preparation.matric.exam.modules.Students.fragment.shortnotes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.modules.Students.StudentDashboard;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.NoteTipModel;

import java.util.ArrayList;
import java.util.List;

public class ShortnotesFragment extends Fragment {


    //the listview
    ListView listView;

    //list to store uploads data
    List<NoteTipModel> uploadsModelList;

    String studentStream = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shortnotes, container, false);

        uploadsModelList = new ArrayList<>();
        listView = root.findViewById(R.id.list_shortnotes);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        studentStream = prefs.getString(Constants.LOGGED_IN_USER_STREAM, null);

        //adding a click listener on list view


        //getting the database reference
        //database reference to get uploads data
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_SHORTNOTES);


        //retrieving upload data from firebase database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    NoteTipModel uploadsModel = postSnapshot.getValue(NoteTipModel.class);

                    if(uploadsModel.getStream().compareToIgnoreCase(studentStream) == 0) {
                        uploadsModelList.add(uploadsModel);
                    }
                }

                String[] uploads = new String[uploadsModelList.size()];

                for (int i = 0; i < uploads.length; i++) {
                    //if(uploadsModelList.get(i).getStream().compareToIgnoreCase(studentStream) == 0) {
                        uploads[i] = "Title\n\n\t\t" + uploadsModelList.get(i).getTitle() + "\n\nSubject\n\n\t\t" + uploadsModelList.get(i).getSubject() + "\n\nContent\n\n\t\t" + "Click for Content";//uploadsModelList.get(i).getContent();
                    //}
                }

                //displaying it to list
                if(getContext() != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, uploads);
                    listView.setAdapter(adapter);
                }else {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(StudentDashboard.context, android.R.layout.simple_list_item_1, uploads);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showSuccessConfirmation(uploadsModelList.get(position).getTitle() , uploadsModelList.get(position).getContent());
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