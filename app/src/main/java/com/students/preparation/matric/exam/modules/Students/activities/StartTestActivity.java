package com.students.preparation.matric.exam.modules.Students.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.TokenService;
import com.students.preparation.matric.exam.adapter.QuestionAndAnswerAdapter;
import com.students.preparation.matric.exam.model.Choices;
import com.students.preparation.matric.exam.model.QuestionAndAnswers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
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
    private Dialog dialog;

    //test result;
    private RelativeLayout mainLayout;
    private LinearLayout testResultLayout;
    private Button close,restart;
    private TextView testResult;
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

        adapter = new QuestionAndAnswerAdapter(getApplicationContext(),arrayList,showAnswerType,fileName);
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

        savePracticeTime(fileName);
        //loading json


        try {
            JSONArray jsonArray =new JSONArray(readFromFile(fileName));
            for (int i=0;i<=jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                QuestionAndAnswers questionAndAnswers = new QuestionAndAnswers();
                questionAndAnswers.setQuestionNumber(object.getInt("question_number"));
                questionAndAnswers.setQuestion(object.getString("question"));
                questionAndAnswers.setAnswer(object.getString("answer"));
                questionAndAnswers.setExplanations(object.getString("explanation"));
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


        //setting Incorrect and Correct at start up
        TokenService.clearExamResult(
                this,
                fileName.substring(0,fileName.lastIndexOf(".")),
                "Correct");
        TokenService.clearExamResult(
                this,
                fileName.substring(0,fileName.lastIndexOf(".")),
                "InCorrect");

        //after finish time dialog
        mainLayout = findViewById(R.id.testMainLayout);
        testResultLayout = findViewById(R.id.testResultLayout);
        close = findViewById(R.id.close);
        testResult = findViewById(R.id.testResults);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        backPressHandler();
        return;


    }

    public void backPressHandler(){
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.after_exam_started_back_press_layout);
        dialog.findViewById(R.id.closeTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialog.findViewById(R.id.continueTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                backPressHandler();
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
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
                finishTest();
            }
        }.start();
    }



    public void finishTest(){
        int result = TokenService.getExamResult(
                getApplicationContext(),
                fileName.substring(0,fileName.lastIndexOf(".")),
                "Correct");
        testResult.setText("You have got : "+result+"/"+totalQuestion);
        recyclerView.setVisibility(View.GONE);
        mainLayout.setBackgroundColor(Color.WHITE);
        testResultLayout.setVisibility(View.VISIBLE);
        timeShower.setText("Completed");

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

    public void savePracticeTime(String fileName){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        int thisMonth = calendar.get(Calendar.MONTH);
        String month = getMonth(thisMonth);
        int date = calendar.get(Calendar.DATE);

        String practiceDate = month+"/"+date;
        TokenService.setPracticeDate(this,fileName.substring(0,fileName.lastIndexOf(".")),practiceDate);
    }

    public String getMonth(int index){
        String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};

        return months[index];
    }
}
