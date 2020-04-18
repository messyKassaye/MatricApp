package com.students.preparation.matric.exam.modules.Students.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.adapter.QuestionAndAnswerAdapter;
import com.students.preparation.matric.exam.adapter.QuestionRecyclerViewAdapter;
import com.students.preparation.matric.exam.model.Choices;
import com.students.preparation.matric.exam.model.Exams;
import com.students.preparation.matric.exam.model.QuestionAndAnswers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class StartTestActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String examTime;
    private String showAnswerType;
    private String fileName;
    private int totalQuestion;
    private TextView timeShower;
    private RecyclerView recyclerView;
    private QuestionAndAnswerAdapter adapter;
    private ArrayList<QuestionAndAnswers> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);
        toolbar = findViewById(R.id.testAppbar);
        setSupportActionBar(toolbar);

        examTime = getIntent().getStringExtra("examTimes");
        showAnswerType = getIntent().getStringExtra("showAnswer");
        fileName = getIntent().getStringExtra("fileName");
        totalQuestion = getIntent().getIntExtra("totalQuestion",0);

        //views
        timeShower = findViewById(R.id.examTime);

        adapter = new QuestionAndAnswerAdapter(getApplicationContext(),arrayList);
        recyclerView = findViewById(R.id.questionsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        String[] units = examTime.split(":"); //will break the string up into an array
        int hour = Integer.parseInt(units[0]); //first element
        int minute = Integer.parseInt(units[1]); //second element
        long duration = hour*3600000+60000 * minute;
        startCountingTime(duration);

        //loading json

        try {
            JSONArray jsonArray =new JSONArray(readFromFile(fileName));
            for (int i=0;i<=jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                System.out.println("NUmber: "+object.getInt("question_number"));
                QuestionAndAnswers questionAndAnswers = new QuestionAndAnswers();
                questionAndAnswers.setQuestionNumber(object.getInt("question_number"));
                questionAndAnswers.setQuestion(object.getString("question"));
                questionAndAnswers.setAnswer(object.getString("answer"));
                questionAndAnswers.setExplanations("explanation");
                JSONObject choiceObject = object.getJSONObject("choices");
                Choices choices = new Choices();
                choices.setChoice1(choiceObject.getString("choice_1"));
                choices.setChoice2(choiceObject.getString("choice_2"));
                choices.setChoice3(choiceObject.getString("choice_3"));
                choices.setChoice4(choiceObject.getString("choice_4"));
                questionAndAnswers.setChoices(choices);
                arrayList.add(questionAndAnswers);
            }
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        System.out.println("Size: "+arrayList.size());
        System.out.println("JSON: "+readFromFile(fileName));
    }

    public void startCountingTime(long time){
        new CountDownTimer(time,1000){

            @Override
            public void onTick(long millisUntilFinished) {

                long elapsedhour = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished));

                long elapsedMinute = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));

                long elapsedSecond = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));

                String elapsedTime = ""+elapsedhour+":"+elapsedMinute+":"+elapsedSecond;
                timeShower.setText(elapsedTime);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

  private String readFromFile (String fileName) {
        String text = "";
       try {
          //Make your FilePath and File
          String yourFilePath = getApplicationContext().getExternalFilesDir(null) + "/" + fileName;
          File yourFile = new File(yourFilePath);
          //Make an InputStream with your File in the constructor
          InputStream inputStream = new FileInputStream(yourFile);
          StringBuilder stringBuilder = new StringBuilder();
          //Check to see if your inputStream is null
          //If it isn't use the inputStream to make a InputStreamReader
          //Use that to make a BufferedReader
          //Also create an empty String
          if (inputStream != null) {
              InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
              BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
              String receiveString = "";
              //Use a while loop to append the lines from the Buffered reader
              while ((receiveString = bufferedReader.readLine()) != null){
                  stringBuilder.append(receiveString);
              }
              //Close your InputStream and save stringBuilder as a String
              inputStream.close();
              text = stringBuilder.toString();
          }
          return text;
      } catch (Exception e) {
          //Log your error with Log.e
           e.printStackTrace();
           return null;
      }
    }

}
