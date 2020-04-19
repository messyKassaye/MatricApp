package com.students.preparation.matric.exam.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.TokenService;
import com.students.preparation.matric.exam.model.ExamSubjects;
import com.students.preparation.matric.exam.model.Exams;
import com.students.preparation.matric.exam.modules.Students.activities.StartTestActivity;
import com.students.preparation.matric.exam.modules.Students.fragment.EntranceExamFragment;
import com.students.preparation.matric.exam.roomDB.MatricAppDatabase;
import com.students.preparation.matric.exam.roomDB.entity.ExamPractice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SubjectsExamRecyclerViewAdapter extends RecyclerView.Adapter<SubjectsExamRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Exams> tutorials;
    private int downloadPercantage =0;
    private String lastPracticedDate = "Not started";
    public SubjectsExamRecyclerViewAdapter(Context context, ArrayList<Exams> tutorialsArrayList) {
        this.context = context;
        this.tutorials = tutorialsArrayList;

    }

    public SubjectsExamRecyclerViewAdapter() {
    }

    @NonNull
    @Override
    public SubjectsExamRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subject_exam_layout, viewGroup, false);
        return new SubjectsExamRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubjectsExamRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        final Exams singleTutorial = tutorials.get(i);
        SpannableStringBuilder cs = new SpannableStringBuilder("X3 + X2");
        cs.setSpan(new SuperscriptSpan(), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(0.75f), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new SuperscriptSpan(), 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(0.75f), 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.percentage.setText(Html.fromHtml(""+downloadPercantage+"<sup><small>%</small></sup>"));

        viewHolder.examYear.setText(""+singleTutorial.getExamYear());
        viewHolder.givenTime.setText("Given time: "+singleTutorial.getExamTime());
        String lastPractice = TokenService.getPracticeDate(context,
                singleTutorial.getFileName().substring(0,singleTutorial.getFileName().lastIndexOf(".")));
        if (lastPractice==null){
            viewHolder.lastPracitice.setText(Html.fromHtml("<p>Last practiced: <span style='color:red;'>Not started</span></p>"));
        }else {
            viewHolder.lastPracitice.setText("Last practiced: "+lastPractice);
        }

        viewHolder.downloadExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.downloadProgress.setVisibility(View.VISIBLE);
                viewHolder.downloadProgress.setText("Please wait saving exam local...");
                viewHolder.downloadExam.setText("Saving data....");
                viewHolder.downloadExam.setEnabled(false);
                if (checkFileExistence(singleTutorial.getFileName())){

                    showDialog(viewHolder,singleTutorial.getFileName(),singleTutorial.getExamTime(),singleTutorial.getTotalQuestionNumber());
                }else {
                    new DownloadTask(
                            viewHolder,
                            context,
                            singleTutorial.getJsonDownloadUrl(),
                            viewHolder.percentage,
                            singleTutorial.getFileName(),
                            singleTutorial.getExamTime(),
                            singleTutorial.getTotalQuestionNumber()).execute();
                }

            }
        });




    }

    @Override
    public int getItemCount() {
        return tutorials.size();
    }

    public  void startWorking(String fileName){
        File examFile = new File(
                context.getExternalFilesDir(Context.DOWNLOAD_SERVICE)+"/examFiles",
                fileName);
        if (examFile.exists()){
            System.out.println("File is found");
        }
        System.out.println("File: "+fileName);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView examYear;
        private final TextView givenTime,lastPracitice,percentage;
        private final TextView downloadProgress;
        private final Button downloadExam;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            examYear = itemView.findViewById(R.id.examYear);
            givenTime = itemView.findViewById(R.id.givenTime);
            lastPracitice = itemView.findViewById(R.id.lastPracticed);
            percentage = itemView.findViewById(R.id.percentage);
            downloadProgress = itemView.findViewById(R.id.jsonDownloadProgress);
            downloadExam = itemView.findViewById(R.id.startDownloadingExam);
        }
    }

    private class DownloadTask extends AsyncTask<String,Integer,String>{

        private Context context;
        private PowerManager.WakeLock mWakeLock;
        private String url;
        private TextView progress;
        private String fileName;
        private String examTime;
        private int totalQuestion;
        private SubjectsExamRecyclerViewAdapter.ViewHolder viewHolder;
        public DownloadTask(SubjectsExamRecyclerViewAdapter.ViewHolder viewHolder,Context context,String url,TextView textView,String file,String examTime,int totalQuestion) {
            this.context = context;
            this.url = url;
            this.progress =textView;
            this.fileName = file;
            this.examTime = examTime;
            this.totalQuestion = totalQuestion;
            this.viewHolder = viewHolder;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(this.url);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();


                File examFile = new File(context.getExternalFilesDir(null),fileName);
                if (!examFile.exists()){
                    examFile.createNewFile();
                }
                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(examFile);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values[0]==100){
                File examFile = new File(
                        context.getExternalFilesDir(null),
                        fileName);
                viewHolder.downloadExam.setText("Start");
                viewHolder.downloadExam.setTextColor(Color.BLACK);
                viewHolder.downloadProgress.setVisibility(View.GONE);
                viewHolder.percentage.setText(""+0);
                if (examFile.exists()){
                    showDialog(viewHolder,fileName,examTime,totalQuestion);
                 }
            progress.setText(Html.fromHtml(""+values[0]+"<sup><small>%</small></sup>"));
        }

    }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
        }
    }

    public boolean checkFileExistence(String fileName){
        File examFile = new File(
                context.getExternalFilesDir(null),
                fileName);
        if (examFile.exists()){
            return true;
        }else {
            return false;
        }
    }

    public void showDialog(SubjectsExamRecyclerViewAdapter.ViewHolder viewHolder,String fileName,String examTime,int totalQuestion){
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.when_to_show_answer_dialog);
        ((RadioGroup)dialog.findViewById(R.id.whenToShowGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                viewHolder.downloadExam.setText("Start");
                viewHolder.downloadExam.setTextColor(Color.BLACK);
                viewHolder.downloadProgress.setVisibility(View.GONE);
                viewHolder.percentage.setText(""+0);
                switch (checkedId){
                    case R.id.radioAfterFinish:
                        Intent intent = new Intent(context, StartTestActivity.class);
                        intent.putExtra("fileName",fileName);
                        intent.putExtra("showAnswer","After finishing");
                        intent.putExtra("examTimes",examTime);
                        intent.putExtra("totalQuestion",totalQuestion);
                        context.startActivity(intent);
                        dialog.dismiss();
                    case R.id.radioRightWay:
                        Intent intents = new Intent(context, StartTestActivity.class);
                        intents.putExtra("fileName",fileName);
                        intents.putExtra("showAnswer","Right way");
                        intents.putExtra("examTimes",examTime);
                        intents.putExtra("totalQuestion",totalQuestion);
                        context.startActivity(intents);
                        dialog.dismiss();

                }
            }
        });

        dialog.show();
    }

}