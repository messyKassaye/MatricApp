package com.students.preparation.matric.exam.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.Tutorials;
import java.util.ArrayList;

public class TutorialRecyclerViewAdapter extends RecyclerView.Adapter<TutorialRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Tutorials> tutorials;

    public TutorialRecyclerViewAdapter(Context context, ArrayList<Tutorials> tutorialsArrayList) {
        this.context = context;
        this.tutorials = tutorialsArrayList;
    }

    @NonNull
    @Override
    public TutorialRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tutorial_recyclerview_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TutorialRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        final Tutorials singleTutorial = tutorials.get(i);
        viewHolder.tutorialImage.setText(String.valueOf(singleTutorial.getSubject().charAt(0)));
        viewHolder.tutorialTitle.setText(singleTutorial.getTitle());
        viewHolder.subjectName.setText(singleTutorial.getSubject()+" > ");
        viewHolder.stream.setText(singleTutorial.getStream());
        viewHolder.grade.setText("Grade "+singleTutorial.getGrade()+" >");

        String embedLink = singleTutorial.getYoutubeLink().replace("watch","embed")
                .replace("?","/").replace("v=","");

        String frameVideo = "<html><body>" +
                "<iframe width=\"300\" height=\"250\" " +
                "src=\""+embedLink+"\" " +
                "frameborder=\"0\"></iframe>" +
                "</body>" +
                "</html>";
        viewHolder.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = viewHolder.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        viewHolder.webView.loadData(frameVideo, "text/html", "utf-8");
        viewHolder.webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if(newProgress==10){
                    viewHolder.webView.setVisibility(View.VISIBLE);
                }
            }
        });
        viewHolder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(singleTutorial.getYoutubeLink()));
                viewHolder.download.setText("Downloading.....");
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tutorials.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tutorialImage;
        private final TextView tutorialTitle;
        private final TextView subjectName;
        private final TextView stream;
        private final TextView grade;
        private WebView webView;
        private Button download;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tutorialImage = itemView.findViewById(R.id.tutorial_image);
            tutorialTitle = itemView.findViewById(R.id.tutorialTitle);
            subjectName = itemView.findViewById(R.id.subjectName);
            stream = itemView.findViewById(R.id.tutorialStream);
            webView = itemView.findViewById(R.id.tutorialWebView);
            download = itemView.findViewById(R.id.downloadTutorial);
            grade = itemView.findViewById(R.id.tutorialGrade);

        }
    }

}
