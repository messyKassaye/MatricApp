package com.students.preparation.matric.exam.admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.UploadsModel;

import java.util.ArrayList;
import java.util.List;

public class ViewUploadsActivity extends AppCompatActivity {

    //the listview
    ListView listView;

    //database reference to get uploads data
    DatabaseReference mDatabaseReference;

    //list to store uploads data
    List<UploadsModel> uploadsModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_uploads);

        uploadsModelList = new ArrayList<>();
        listView = findViewById(R.id.listView);


        //adding a click listener on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the uploadsModel
                UploadsModel uploadsModel = uploadsModelList.get(i);

                //Opening the uploadsModel file in browser using the uploadsModel url
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uploadsModel.getUrl()));
                startActivity(intent);
            }
        });


        //getting the database reference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_TEXTBOOKS);

        //retrieving upload data from firebase database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UploadsModel uploadsModel = postSnapshot.getValue(UploadsModel.class);
                    uploadsModelList.add(uploadsModel);
                }

                String[] uploads = new String[uploadsModelList.size()];

                for (int i = 0; i < uploads.length; i++) {
                    uploads[i] = uploadsModelList.get(i).getTitle() +"\n" + uploadsModelList.get(i).getStream()+"\n" + uploadsModelList.get(i).getSubject()+"\n" + uploadsModelList.get(i).getType();
                }

                //displaying it to list
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}