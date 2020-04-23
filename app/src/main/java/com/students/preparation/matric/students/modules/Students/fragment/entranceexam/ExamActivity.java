package com.students.preparation.matric.students.modules.Students.fragment.entranceexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.students.Constants;
import com.students.preparation.matric.students.DownloadCompletedBroadcastReceiver;
import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.adapter.ExamAdapter;
import com.students.preparation.matric.students.model.ExamQuestionsModel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ExamActivity extends AppCompatActivity {

    String subject, year;
    List<ExamQuestionsModel> examQuestionsList;
    ListView listview_exam_questions;
    String[] examQuestions;
    private DownloadCompletedBroadcastReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        examQuestionsList = new ArrayList<>();
        listview_exam_questions = findViewById(R.id.listview_exam_questions);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            subject = extras.getString(Constants.EXAM_KEY_SUBJECT);
            year = extras.getString(Constants.EXAM_KEY_YEAR);


            DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_ENTRANCE_EXAM + "/" + subject + "/" + year);

            //retrieving upload data from firebase database
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        ExamQuestionsModel examQuestions = postSnapshot.getValue(ExamQuestionsModel.class);
                        examQuestionsList.add(examQuestions);
                    }

                    examQuestions = new String[examQuestionsList.size()];

                    for (int i = 0; i < examQuestions.length; i++) {
                        examQuestions[i] = examQuestionsList.get(i).getExamQuestion() + "\n" + examQuestionsList.get(i).getExamChoice1() + "\n" + examQuestionsList.get(i).getExamChoice2() + "\n" + examQuestionsList.get(i).getExamChoice3() + "\n" + examQuestionsList.get(i).getExamChoice4() + "\n" + examQuestionsList.get(i).examAnswer;
                    }

                    ArrayList<HashMap<String, String>> questionsArrayList = new ArrayList<HashMap<String, String>>();

                    for (int i = 0; i < examQuestionsList.size(); i++) {

                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put(Constants.EXAM_QUESTION, examQuestionsList.get(i).getExamQuestion());
                        map.put(Constants.EXAM_CHOICE_1, examQuestionsList.get(i).getExamChoice1());
                        map.put(Constants.EXAM_CHOICE_2, examQuestionsList.get(i).getExamChoice2());
                        map.put(Constants.EXAM_CHOICE_3, examQuestionsList.get(i).getExamChoice3());
                        map.put(Constants.EXAM_CHOICE_4, examQuestionsList.get(i).getExamChoice4());
                        map.put(Constants.EXAM_ANSWER, examQuestionsList.get(i).getExamAnswer());
                        map.put(Constants.EXAM_EXPLANATION, examQuestionsList.get(i).getExamAnswerExplanation());
                        String questionName =examQuestionsList.get(i).getExamQuestion();
                        String imageUrl =examQuestionsList.get(i).getExamOptionalImageUrl();



                        downloadFile(questionName,imageUrl);

                        map.put(Constants.EXAM_OPTIONAL_IMAGE, examQuestionsList.get(i).getExamOptionalImageUrl());

                        questionsArrayList.add(map);
                        //Toast.makeText(getApplicationContext() , "URL: "+examQuestionsList.get(i).getExamOptionalImageUrl() , Toast.LENGTH_LONG).show();
                    }

                    ExamAdapter adapter = new ExamAdapter(getApplicationContext(),
                            ExamActivity.this, questionsArrayList);//, list);

                    //displaying it to list
                    //ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, examQuestions);
                    listview_exam_questions.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }


    private void downloadFile(String questionName,String bucketUrl) {
        File file = new File(getApplicationContext().getExternalFilesDir(null) + "/examImages");

        //now if download complete file not visible now lets show it
        DownloadManager.Request request = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            request = new DownloadManager.Request(Uri.parse(bucketUrl))
                    .setTitle(questionName)
                    .setDescription("Downloading...")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationUri(Uri.fromFile(file))
                    .setRequiresCharging(false)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true);
        } else {
            request = new DownloadManager.Request(Uri.parse(bucketUrl))
                    .setTitle(questionName)
                    .setDescription("Downloading...")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationUri(Uri.fromFile(file))
                    .setAllowedOverRoaming(true);
        }

        DownloadManager downloadManager = (DownloadManager) getApplicationContext()
                .getSystemService(DOWNLOAD_SERVICE);
        long downloadId = downloadManager.enqueue(request);

        receiver = new DownloadCompletedBroadcastReceiver(getApplicationContext(),questionName,downloadId);
        getApplicationContext().registerReceiver(receiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        /*StorageReference  islandRef = storageRef.child(questionName+".jpg");

        File rootPath = new File(getApplicationContext().getExternalFilesDir(null), "questionsImage");
        if(!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath,questionName+".jpg");

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("firebase ",";local tem file created  created " +localFile.toString());
                //  updateDb(timestamp,localFile.toString(),position);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("firebase ",";local tem file not created  created " +exception.toString());
            }
        });*/
    }

}
