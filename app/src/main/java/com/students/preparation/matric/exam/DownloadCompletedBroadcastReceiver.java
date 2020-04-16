package com.students.preparation.matric.exam;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.students.preparation.matric.exam.roomDB.DAO.ExamImageDAO;
import com.students.preparation.matric.exam.roomDB.MatricAppDatabase;
import com.students.preparation.matric.exam.roomDB.entity.ExamQuestionImage;

import java.io.File;

public class DownloadCompletedBroadcastReceiver extends BroadcastReceiver {
    private Context context;
    private String fileName;
    private File file;
    private Long downloadID;
    private boolean downloading = false;
    private String questionName;
    public DownloadCompletedBroadcastReceiver(Context context,String questionName,Long downloadId) {
        this.context = context;
        this.fileName = fileName;
        this.questionName = questionName;
        file = new File(context.getExternalFilesDir(null)+"/examImages/"+questionName);
        this.downloadID = downloadId;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        //Checking if the received broadcast is for our enqueued download by matching download id
        if (downloadID == id) {
            File downloadedFile = new File(context.getExternalFilesDir(null)+"/examImages/"+fileName);
            if(downloadedFile.exists()){
                ExamQuestionImage download = new ExamQuestionImage();
                download.setQuestionName(questionName);
                download.setFilePath(file.getPath());
                saveDownloadStatus(download);
            }

        }
        //
    }

    public void saveDownloadStatus(final ExamQuestionImage downloadImage){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ExamImageDAO downloadDAO = MatricAppDatabase.getDatabase(context).getExamDAO();
                downloadDAO.store(downloadImage);
            }
        });
    }

    public boolean isDownloading() {
        return downloading;
    }

    public void setDownloading(boolean downloading) {
        this.downloading = downloading;
    }
}