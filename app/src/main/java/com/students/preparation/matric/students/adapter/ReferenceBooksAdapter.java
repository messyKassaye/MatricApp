package com.students.preparation.matric.students.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.model.Books;
import com.students.preparation.matric.students.model.UploadsModel;

import java.io.File;
import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;

public class ReferenceBooksAdapter extends RecyclerView.Adapter<ReferenceBooksAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Books> referenceBooks;

    public ReferenceBooksAdapter(Context context, ArrayList<Books> tutorialsArrayList) {
        this.context = context;
        this.referenceBooks = tutorialsArrayList;
    }

    @NonNull
    @Override
    public ReferenceBooksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reference_books_layout, viewGroup, false);
        return new ReferenceBooksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReferenceBooksAdapter.ViewHolder viewHolder, int i) {
        final Books singleTutorial = referenceBooks.get(i);
        viewHolder.title.setText(singleTutorial.getTitle());
        viewHolder.subject.setText(singleTutorial.getSubject()+":");
        viewHolder.grade.setText(singleTutorial.getGrades()+":");
        viewHolder.stream.setText(singleTutorial.getStream());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownloading(singleTutorial.getTitle(),singleTutorial.getDownloadUrl());
            }
        });

    }

    @Override
    public int getItemCount() {
        return referenceBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView subject,grade,stream;
        private final ImageView download;
        private final CardView cardView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.referenceTitle);
            subject = itemView.findViewById(R.id.referenceSubject);
            grade = itemView.findViewById(R.id.referenceGrade);
            stream = itemView.findViewById(R.id.referenceStream);
            download = itemView.findViewById(R.id.downloadIcon);
            cardView = itemView.findViewById(R.id.referenceCard);

        }
    }

    public void startDownloading(String title,String url){
        File file = new File(context.getExternalFilesDir(null) + "/examImages");

        //now if download complete file not visible now lets show it
        DownloadManager.Request request = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            request = new DownloadManager.Request(Uri.parse(url))
                    .setTitle(title)
                    .setDescription("Downloading...")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationUri(Uri.fromFile(file))
                    .setRequiresCharging(false)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true);
        } else {
            request = new DownloadManager.Request(Uri.parse(url))
                    .setTitle(title)
                    .setDescription("Downloading...")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationUri(Uri.fromFile(file))
                    .setAllowedOverRoaming(true);
        }

        DownloadManager downloadManager = (DownloadManager) context
                .getSystemService(DOWNLOAD_SERVICE);
        long downloadId = downloadManager.enqueue(request);
    }

}