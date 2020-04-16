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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.News;
import com.students.preparation.matric.exam.model.Tutorials;

import java.util.ArrayList;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<News> newsArrayList;

    public NewsRecyclerViewAdapter(Context context, ArrayList<News> tutorialsArrayList) {
        this.context = context;
        this.newsArrayList = tutorialsArrayList;
    }

    @NonNull
    @Override
    public NewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_card_layout, viewGroup, false);
        return new NewsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        final News news = newsArrayList.get(i);
        viewHolder.image.setText(String.valueOf(news.getTitle().charAt(0)));
        viewHolder.title.setText(news.getTitle());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(news.getLink()));
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView image;
        private final TextView title;
        private final CardView cardView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.newsImage);
            title = itemView.findViewById(R.id.newTitle);
            cardView = itemView.findViewById(R.id.newCardView);


        }
    }
}
