package com.students.preparation.matric.exam.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.News;
import com.students.preparation.matric.exam.model.Notes;

import java.util.ArrayList;

public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Notes> newsArrayList;

    public NotesRecyclerViewAdapter(Context context, ArrayList<Notes> tutorialsArrayList) {
        this.context = context;
        this.newsArrayList = tutorialsArrayList;
    }

    @NonNull
    @Override
    public NotesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.short_notes_layout,
                viewGroup, false);
        return new NotesRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotesRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        final Notes news = newsArrayList.get(i);
        viewHolder.image.setText(String.valueOf(news.getTitle().charAt(0)));
        viewHolder.title.setText(news.getTitle());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dialog.setContentView(R.layout.dialog_layout);
                TextView content = dialog.findViewById(R.id.notesContents);
                content.setText(news.getContent());
                dialog.findViewById(R.id.closeDialog).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
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

            image = itemView.findViewById(R.id.notesImage);
            title = itemView.findViewById(R.id.notesTitle);
            cardView = itemView.findViewById(R.id.notesCard);

        }
    }
}