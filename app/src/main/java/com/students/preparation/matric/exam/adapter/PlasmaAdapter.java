package com.students.preparation.matric.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.Tutorials;

import java.util.ArrayList;

public class PlasmaAdapter extends RecyclerView.Adapter<PlasmaAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Tutorials> tutorials;

    public PlasmaAdapter(Context context, ArrayList<Tutorials> tutorialsArrayList) {
        this.context = context;
        this.tutorials = tutorialsArrayList;
    }

    @NonNull
    @Override
    public PlasmaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tutorial_recyclerview_layout, viewGroup, false);
        return new PlasmaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlasmaAdapter.ViewHolder viewHolder, int i) {
        Tutorials singleTutorial = tutorials.get(i);
        viewHolder.tutorialImage.setText(String.valueOf(singleTutorial.getSubject().charAt(0)));
        viewHolder.subjectName.setText(singleTutorial.getSubject());
        viewHolder.stream.setText(singleTutorial.getStream()+" > grade "+singleTutorial.getGrade());

        String frameVideo = "<html><body>" +
                "<iframe width=\"300\" height=\"305\" " +
                "src=\""+singleTutorial.getYoutubeLink()+"\" " +
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
                    viewHolder.webViewLoader.setVisibility(View.GONE);
                    viewHolder.webView.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return tutorials.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tutorialImage;
        private final TextView subjectName;
        private final TextView stream;
        private WebView webView;
        private LinearLayout webViewLoader;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tutorialImage = itemView.findViewById(R.id.tutorial_image);
            subjectName = itemView.findViewById(R.id.subject_name);
            stream = itemView.findViewById(R.id.stream_grade);

            webView = itemView.findViewById(R.id.tutorialWebView);
            webViewLoader = itemView.findViewById(R.id.webviewLoadingLayout);

        }
    }

}
