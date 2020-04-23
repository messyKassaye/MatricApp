package com.students.preparation.matric.students.modules.Students.activities;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.TokenService;
import com.students.preparation.matric.students.adapter.AfterFinishiRecyclerViewAdapter;
import com.students.preparation.matric.students.adapter.QuestionAndAnswerAdapter;
import com.students.preparation.matric.students.model.AfterFinishModel;
import com.students.preparation.matric.students.model.Choices;
import com.students.preparation.matric.students.model.CorrectInCorrect;
import com.students.preparation.matric.students.model.QuestionAndAnswers;

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
    private String examSubject;
    private TextView timeShower;
    private RecyclerView recyclerView;
    private QuestionAndAnswerAdapter adapter;
    private ArrayList<QuestionAndAnswers> arrayList = new ArrayList<>();
    private Dialog dialog;

    //test result;
    private LinearLayout mainLayout;
    private RelativeLayout mainContentLayout;
    private LinearLayout testResultLayout;
    private Button close,restart;
    private TextView testResult;
    private ImageView exitTest;

    private ArrayList<AfterFinishModel> afterFinishData = new ArrayList<>();
    private AfterFinishiRecyclerViewAdapter afterFinishAdapter;
    private RecyclerView afterFinishRecylerView;
    private boolean examFinished = false;

    private ArrayList<CorrectInCorrect> examCollection = new ArrayList<>();

    private ExamTimeCounter timeCounter;
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
        examSubject = getIntent().getStringExtra("subject");

        //views
        timeShower = findViewById(R.id.examTime);

        afterFinishAdapter = new AfterFinishiRecyclerViewAdapter(getApplicationContext(),arrayList,showAnswerType,fileName,afterFinishData);
        afterFinishRecylerView = findViewById(R.id.afterFinishRecyclerView);
        afterFinishRecylerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false));
        afterFinishRecylerView.setItemAnimator(new DefaultItemAnimator());
        afterFinishRecylerView.setAdapter(afterFinishAdapter);
        afterFinishAdapter.notifyDataSetChanged();



        adapter = new QuestionAndAnswerAdapter(examSubject,getApplicationContext(),this,arrayList,showAnswerType,fileName);
        recyclerView = findViewById(R.id.questionsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);




        String[] units = examTime.split(":"); //will break the string up into an array
        int hour = Integer.parseInt(units[0]); //first element
        int minute = Integer.parseInt(units[1]); //second element
        long duration = hour*3600000+60000 * minute;

        timeCounter = new ExamTimeCounter(duration,1000);
        timeCounter.start();


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
                questionAndAnswers.setType(object.getString("type"));
                questionAndAnswers.setBase64Image(object.getString("image"));
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
        mainContentLayout = findViewById(R.id.mainContentLayout);
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

        exitTest = findViewById(R.id.exitTest);
        exitTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTotalExamResult();
            }
        });

    }

    @Override
    public void onBackPressed() {
        backPressHandler();
        return;

    }


    public void showTotalExamResult(){
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.exa_time_is_not_done_dialog);
        dialog.findViewById(R.id.showMeResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                examFinished = true;
                showResult();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showResult(){
        timeCounter.cancel();
        int result =  0;
        for (int i=0;i<examCollection.size();i++){
            if (examCollection.get(i).getAnswer()=="Correct"){
                result++;
            }
        }

        if (showAnswerType.equalsIgnoreCase("After finishing")){
            final Dialog dialog=new Dialog(this);
            dialog.setContentView(R.layout.after_finish_exit_dialog);
            ((TextView)dialog.findViewById(R.id.afterFinishFinalResult))
                    .setText("You have got: "+result+"/"+totalQuestion);
            dialog.findViewById(R.id.checkResult).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerView.setVisibility(View.GONE);

                    afterFinishRecylerView.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
            });
            dialog.show();
            timeShower.setText("Completed");
        }else {
            mainContentLayout.setBackgroundColor(Color.WHITE);
            timeShower.setText("Completed");
            mainLayout.setVisibility(View.GONE);
            testResultLayout.setVisibility(View.VISIBLE);
            testResult.setText("You have got: "+result+"/"+totalQuestion);
        }
    }

    public void saveAfterFinishData(AfterFinishModel data){
        if(afterFinishData.size()==0){
            afterFinishData.add(data);
        } else{
            for (int i = 0; i < afterFinishData.size(); i++) {

                if (afterFinishData.get(i).getQuestionNumber()==data.getQuestionNumber()) {
                    afterFinishData.get(i).setAsnwer(data.getAsnwer());
                    break;
                }else {
                    afterFinishData.add(data);
                }
            }
        }

    }

    public void examArami(CorrectInCorrect correctInCorrect){
        if (examCollection.size()==0){
            examCollection.add(correctInCorrect);
        }else {
            for (int i=0;i<examCollection.size();i++){
                if (examCollection.get(i).getQuestionNumber()==correctInCorrect.getQuestionNumber()){
                    examCollection.get(i).setAnswer(correctInCorrect.getAnswer());
                }else {
                    examCollection.add(correctInCorrect);
                }
            }
        }
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


    public void finishTest(){
        examFinished = true;
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


    public class ExamTimeCounter extends CountDownTimer{

        public ExamTimeCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

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
    }
}
