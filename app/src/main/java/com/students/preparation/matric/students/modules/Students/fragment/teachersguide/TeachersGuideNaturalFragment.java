package com.students.preparation.matric.students.modules.Students.fragment.teachersguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.students.preparation.matric.students.Constants;
import com.students.preparation.matric.students.modules.Students.StudentDashboard;
import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.adapter.TextbookListAdapter;
import com.students.preparation.matric.students.model.UploadsModel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeachersGuideNaturalFragment extends Fragment {

    //GridView grid;


    //the listview
    private ListView listView;

    //list to store uploads data
    private List<UploadsModel> uploadsModelList;

    private int selectedGrade = 11, selectedStream = 1;
    TextbookListAdapter adapter;


    public TeachersGuideNaturalFragment(int grade, int stream) {
        // Required empty public constructor

        selectedGrade = grade;
        selectedStream = stream;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_teachers_guide_natural, container, false);

        //getting the database reference
        //database reference to get uploads data
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_TEACHERS_GUIDE + "/" + Constants.DATABASE_PATH_NATURAL + "_" + Constants.DATABASE_PATH_GRADE_11);
        ;
        if (selectedGrade == 11 && selectedStream == 1) {
            mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_TEACHERS_GUIDE + "/" + Constants.DATABASE_PATH_NATURAL + "_" + Constants.DATABASE_PATH_GRADE_11);
        } else if (selectedGrade == 11 && selectedStream == 2) {

            mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_TEACHERS_GUIDE + "/" + Constants.DATABASE_PATH_SOCIAL + "_" + Constants.DATABASE_PATH_GRADE_11);

        } else if (selectedGrade == 12 && selectedStream == 1) {
            mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_TEACHERS_GUIDE + "/" + Constants.DATABASE_PATH_NATURAL + "_" + Constants.DATABASE_PATH_GRADE_12);

        } else if (selectedGrade == 12 && selectedStream == 2) {
            mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_TEACHERS_GUIDE + "/" + Constants.DATABASE_PATH_SOCIAL + "_" + Constants.DATABASE_PATH_GRADE_12);

        }


        uploadsModelList = new ArrayList<>();
        listView = root.findViewById(R.id.list_natural_guide);


        //adding a click listener on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the uploadsModel

                UploadsModel uploadsModel = uploadsModelList.get(i);


                final File rootPath = new File(Environment.getExternalStorageDirectory(), Constants.LOCALFILE_DOWNLOADS_PATH);


                final File localFile = new File(rootPath, uploadsModel.getTitle().trim()+"_"+uploadsModel.getTimestamp() + ".PDF");


                //File file = new File(path);
                if (localFile.isFile()) {
                    //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/example.pdf");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(localFile), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                } else {
                    downloadPDFFile(uploadsModel.getTitle().trim()+"_"+uploadsModel.getTimestamp(), uploadsModel.getUrl());

                }
            }
        });


        //getting the database reference
        //database reference to get uploads data
        //DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_TEACHERS_GUIDE);


        //retrieving upload data from firebase database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UploadsModel uploadsModel = postSnapshot.getValue(UploadsModel.class);
                    uploadsModelList.add(uploadsModel);
                }

                //String[] titlesSubject = new String[uploadsModelList.size()];
                //String[] streamGrade = new String[uploadsModelList.size()];

                ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();

                for (int i = 0; i < uploadsModelList.size(); i++) {

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(Constants.TB_KEY_TITLE, uploadsModelList.get(i).getSubject() + " : " + uploadsModelList.get(i).getTitle());
                    map.put(Constants.TB_KEY_SUBTITLE, uploadsModelList.get(i).getStream() + " Science, Grade " + uploadsModelList.get(i).getGrade());

                    final File rootPath = new File(Environment.getExternalStorageDirectory(), Constants.LOCALFILE_DOWNLOADS_PATH);


                    final File localFile = new File(rootPath, uploadsModelList.get(i).getTitle().trim()+"_"+uploadsModelList.get(i).getTimestamp() + ".PDF");

                    if(localFile.isFile()) {

                        map.put(Constants.TB_KEY_THUMB_ICON, "open");
                    }else{
                        map.put(Constants.TB_KEY_THUMB_ICON, "download");
                    }

                    // adding HashList to ArrayList
                    arrayList.add(map);
                }

                //adapter = new TextbookListAdapter(getActivity(), arrayList);
                if(getActivity() != null) {
                    adapter = new TextbookListAdapter(getActivity(), arrayList);
                }else{
                    adapter = new TextbookListAdapter(StudentDashboard.context, arrayList);
                }

                //displaying it to list
                //ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, uploads);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return root;
    }

    private void downloadPDFFile(final String fileName, String fileUrl) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(fileUrl);

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Downloading");
        pd.setMessage("Downloading Please Wait!");
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();


        final File rootPath = new File(Environment.getExternalStorageDirectory(), Constants.LOCALFILE_DOWNLOADS_PATH);

        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }


        final File localFile = new File(rootPath, fileName + ".PDF");

        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("firebase ", "local file created " + localFile.toString());

                if (!isVisible()) {
                    return;
                }

                if (localFile.canRead()) {

                    pd.dismiss();
                }

                Toast.makeText(getContext(), "Download Completed", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Internal storage/" + Constants.LOCALFILE_DOWNLOADS_PATH + fileName, Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("firebase ", ";local tem file not created  created " + exception.toString());
                Toast.makeText(getContext(), "Download Incomplete", Toast.LENGTH_LONG).show();
            }
        });
    }

}