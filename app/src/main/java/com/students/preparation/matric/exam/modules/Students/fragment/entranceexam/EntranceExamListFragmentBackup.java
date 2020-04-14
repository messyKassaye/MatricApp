package com.students.preparation.matric.exam.modules.Students.fragment.entranceexam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.EntranceExamModel;

import java.util.List;

public class EntranceExamListFragmentBackup extends Fragment {


    //the listview
    ListView listView;

    //list to store uploads data
    List<EntranceExamModel> entranceExamModelList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_entrance_exam, container, false);

        /*
        entranceExamModelList = new ArrayList<>();
        listView = root.findViewById(R.id.list_entrance_exam);




        //adding a click listener on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the entranceExamModel

                Toast.makeText(getContext() , listView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();
                //Create new fragment
                //pass exam name to the new fragment
                //copy this code to the new fragment ...

                //EntranceExamModel entranceExamModel = entranceExamModelList.get(i);

                //Opening the entranceExamModel file in browser using the entranceExamModel url
                //Intent intent = new Intent(Intent.ACTION_VIEW);
                //intent.setData();
                //startActivity(intent);

            }
        });

        //getting the database reference
        //database reference to get uploads data
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_ENTRANCE_EXAM);


        //retrieving upload data from firebase database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String examTitleList = "";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Toast.makeText(getContext() , postSnapshot.getKey().toString() , Toast.LENGTH_LONG).show();

                    examTitleList += postSnapshot.getKey() +",";
                    //EntranceExamModel entranceExamModel = postSnapshot.getValue(EntranceExamModel.class);
                    //entranceExamModelList.add(entranceExamModel);
                    //HashMap<String, Object> result = new HashMap<>();

                    //result = (HashMap<String, Object>) dataSnapshot.getValue();
                    //Toast.makeText(getContext() , result.get("Physics_Exam2011").toString() , Toast.LENGTH_LONG).show();

                    //EntranceExamModel entranceExamModel = (EntranceExamModel) result.get("Physics_Exam2011");//postSnapshot.getValue(EntranceExamModel.class);
                    //entranceExamModelList.add(entranceExamModel);
                }

                examTitleList =  examTitleList.substring(0, examTitleList.length() - 1);

                String [] questionsList = new String[0];
                if (examTitleList != null) {
                    questionsList = examTitleList.toString().split("\\s*,\\s*");
                }
                //String[] questionsList = new String[entranceExamModelList.size()];


                //String[] questionsList = new String[entranceExamModelList.size()];

                //for (int i = 0; i < questionsList.length; i++) {
                //    questionsList[i] = entranceExamModelList.get(i).getExamQuestion();
                //}


                //CustomSocialGridAdapter customAdapter = new CustomSocialGridAdapter(getContext(), questionsList);
                //listView.setAdapter(customAdapter);

                //displaying it to list
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, questionsList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

*/
        return root;
    }
}