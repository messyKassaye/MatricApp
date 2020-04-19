package com.students.preparation.matric.exam.admin;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.adapter.QuestionRecyclerViewAdapter;
import com.students.preparation.matric.exam.model.Exams;
import com.students.preparation.matric.exam.model.Questions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


public class RegisterQuestionsFragment extends Fragment {

    private TextView examCounter;
    private int nextQuestion = 1;
    private int totalQuestionNumbers = 0;
    private String examType,examSubject;
    private int examYear;
    private String examName;
    private String examTime;
    private RecyclerView recyclerView;
    private QuestionRecyclerViewAdapter adapter;
    private ArrayList<Questions> arrayList = new ArrayList();
    private LinearLayoutManager layoutManager;

    private JSONArray mainJSON;
    private LinearLayout completeAddingQuestionLayout;
    private Button uploadExam;
    private StorageReference storageReference;
    private TextView uploadProgress;
    private DatabaseReference databaseReference;

    public RegisterQuestionsFragment(String examType, String examSubject, int year, int totalQuestion,String examTime) {
        // Required empty public constructor
        this.totalQuestionNumbers = totalQuestion;
        this.examType = examType;
        this.examSubject = examSubject;
        this.examYear = year;
        this.examName = this.examSubject+"_"+this.examYear+".json";
        this.examTime = examTime;
        try{
            mainJSON = new JSONArray();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_register_questions, container, false);

        storageReference = FirebaseStorage.getInstance().getReference();


        completeAddingQuestionLayout = view.findViewById(R.id.questionAdditionDoneLayout);
        uploadExam = view.findViewById(R.id.uploadQuestion);
        uploadProgress = view.findViewById(R.id.uploadingProgress);

        examCounter = view.findViewById(R.id.examCounter);
        examCounter.setText(String.valueOf(nextQuestion));
        Questions questions = new Questions();
        questions.setQuestions("Enter question number 1");
        questions.setChoice1("Enter choice 1");
        questions.setQuestionNumber(nextQuestion);

        adapter = new QuestionRecyclerViewAdapter(this,getContext(),arrayList);
        SnapHelper helper = new PagerSnapHelper();
        recyclerView = view.findViewById(R.id.questionRecyclerView);
        layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(layoutManager);
        helper.attachToRecyclerView(recyclerView);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        arrayList.add(questions);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        uploadExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StorageReference storageRef = storageReference.child(Constants.EXAM_FILES_PATH+"/"+examName);
              String path = createFile(examName,mainJSON.toString());
              Uri uri = Uri.fromFile(new File(path));
              storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      uploadProgress.setText("Your exam is uploaded successfully. Now we are updating your exam database...");
                      storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                          @Override
                          public void onSuccess(Uri uri) {
                              Exams exams = new Exams();
                              exams.setExamType(examType);
                              exams.setExamSubject(examSubject);
                              exams.setExamYear(examYear);
                              exams.setJsonDownloadUrl(uri.toString());
                              exams.setFileName(examName);
                              exams.setTotalQuestionNumber(totalQuestionNumbers);
                              exams.setExamTime(examTime);
                              storeNewExam(exams);
                          }
                      });
                  }
              }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                  }
              }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                  @SuppressWarnings("VisibleForTests")
                  @Override
                  public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                      double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                      uploadProgress.setText((int) progress + "% Uploading...");
                  }
              });
            }
        });

        return view;
    }

    public void nextQuestion(){
        if (nextQuestion!=totalQuestionNumbers) {
            nextQuestion++;
            examCounter.setText(""+nextQuestion);

            Questions questions1 = new Questions();
            questions1.setQuestions("Enter question number " + nextQuestion);
            questions1.setQuestionNumber(nextQuestion);
            arrayList.add(questions1);

            int lastVisible = layoutManager.findLastVisibleItemPosition() + 1;
            if(lastVisible <= adapter.getItemCount()){
                layoutManager.scrollToPosition(lastVisible);
            }
        }else if (nextQuestion==totalQuestionNumbers){
            recyclerView.setVisibility(View.GONE);
            completeAddingQuestionLayout.setVisibility(View.VISIBLE);
        }
    }
    public boolean addQuestion(Questions questions){
        System.out.println("explanations: "+questions.getExplanations());
        try {
            // Here we convert Java Object to JSON
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("question_number", questions.getQuestionNumber()); // Set the first name/pair
            jsonObj.put("question", questions.getQuestions());

            JSONObject choices = new JSONObject(); // we need another object to store the address
            choices.put("choice_1", questions.getChoice1());
            choices.put("choice_2", questions.getChoice2());
            choices.put("choice_3", questions.getChoice3());
            choices.put("choice_4",questions.getChoice4());

            // We add the object to the main object
            jsonObj.put("choices", choices);

            jsonObj.put("answer",questions.getAnswer().replaceAll(" ","_").toLowerCase());
            jsonObj.put("explanation",questions.getExplanations());

            mainJSON.put(jsonObj);
            return true;
        }
        catch(JSONException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String createFile(String fileName,String data) {
       File file = new File(getActivity().getFilesDir(), "jsons");
            if (!file.exists()) {
                file.mkdir();
            }
            try {
                File gpxfile = new File(file, fileName);
                FileWriter writer = new FileWriter(gpxfile);
                writer.append(data);
                writer.flush();
                writer.close();
               return gpxfile.getAbsolutePath();
            } catch (Exception e) {return null; }

    }

    public void storeNewExam(Exams exams){
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.EXAM_FILES_PATH);
        String examId = databaseReference.push().getKey();
        databaseReference.child(examId).setValue(exams)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        uploadExam.setVisibility(View.GONE);
                      uploadProgress.setText("Database updated successfully");
                      examCounter.setText("Done");
                    }
                });
    }
}
