package com.students.preparation.matric.exam.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.NoteTipModel;

import java.util.ArrayList;

public class AdminShortNotesAdapter extends RecyclerView.Adapter<AdminShortNotesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<NoteTipModel> newsArrayList;

    public AdminShortNotesAdapter(Context context, ArrayList<NoteTipModel> tutorialsArrayList) {
        this.context = context;
        this.newsArrayList = tutorialsArrayList;
    }

    @NonNull
    @Override
    public AdminShortNotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.short_note_and_tips_car_layout,
                viewGroup, false);
        return new AdminShortNotesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdminShortNotesAdapter.ViewHolder viewHolder, int i) {
        final NoteTipModel news = newsArrayList.get(i);
        viewHolder.image.setText(String.valueOf(news.getTitle().charAt(0)));
        viewHolder.title.setText(news.getTitle());
        viewHolder.subject.setText(news.getSubject()+":");
        viewHolder.grade.setText("Grade "+news.getGrade()+":");
        viewHolder.stream.setText(news.getStream());
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
        private final TextView subject,grade,stream;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.adminNotesImage);
            title = itemView.findViewById(R.id.adminNotesTitle);
            cardView = itemView.findViewById(R.id.adminNoteCard);
            subject = itemView.findViewById(R.id.notesSubject);
            grade = itemView.findViewById(R.id.notesGrade);
            stream = itemView.findViewById(R.id.notesStream);

        }
    }
}