package com.students.preparation.matric.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.StudentsModel;
import com.students.preparation.matric.exam.model.Tutorials;
import java.util.ArrayList;

public class NewAndApprovedStudentAdapter extends RecyclerView.Adapter<NewAndApprovedStudentAdapter.ViewHolder> {

    private Context context;
    private ArrayList<StudentsModel> student;

    public NewAndApprovedStudentAdapter(Context context, ArrayList<StudentsModel> tutorialsArrayList) {
        this.context = context;
        this.student = tutorialsArrayList;
    }

    @NonNull
    @Override
    public NewAndApprovedStudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_student_and_approved_student_layout,
                viewGroup, false);
        return new NewAndApprovedStudentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewAndApprovedStudentAdapter.ViewHolder viewHolder, int i) {
        //Tutorials singleTutorial = tutorials.get(i);


    }

    @Override
    public int getItemCount() {
        return student.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //private final TextView tutorialImage;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //tutorialImage = itemView.findViewById(R.id.tutorial_image);


        }
    }
}

