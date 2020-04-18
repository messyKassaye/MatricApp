package com.students.preparation.matric.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.ExamSubjects;
import com.students.preparation.matric.exam.modules.Students.fragment.EntranceExamFragment;

import java.util.ArrayList;

public class StudentExamSubjectRecyclerViewAdapter extends RecyclerView.Adapter<StudentExamSubjectRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ExamSubjects> tutorials;
    private EntranceExamFragment fragment;
    public StudentExamSubjectRecyclerViewAdapter(EntranceExamFragment fragment,Context context, ArrayList<ExamSubjects> tutorialsArrayList) {
        this.context = context;
        this.tutorials = tutorialsArrayList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public StudentExamSubjectRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.entrance_exam_card_layout, viewGroup, false);
        return new StudentExamSubjectRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentExamSubjectRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        final ExamSubjects singleTutorial = tutorials.get(i);
        viewHolder.subjectName.setText(singleTutorial.getSubject());
        viewHolder.imageView.setImageResource(singleTutorial.getSubjectImage());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.showSubjectsExam(singleTutorial.getSubject());
            }
        });


    }

    @Override
    public int getItemCount() {
        return tutorials.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView subjectName;
        private final ImageView imageView;
        private final CardView cardView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.examSubjectName);
            imageView = itemView.findViewById(R.id.subjectImage);
            cardView = itemView.findViewById(R.id.subjectCard);
        }
    }
}
