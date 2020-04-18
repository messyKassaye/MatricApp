package com.students.preparation.matric.exam.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.QuestionAndAnswers;
import com.students.preparation.matric.exam.model.Tutorials;

import java.util.ArrayList;

public class QuestionAndAnswerAdapter  extends RecyclerView.Adapter<QuestionAndAnswerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<QuestionAndAnswers> tutorials;

    public QuestionAndAnswerAdapter(Context context, ArrayList<QuestionAndAnswers> tutorialsArrayList) {
        this.context = context;
        this.tutorials = tutorialsArrayList;
    }

    @NonNull
    @Override
    public QuestionAndAnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.question_and_answer_recyclerview_ayout, viewGroup, false);
        return new QuestionAndAnswerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionAndAnswerAdapter.ViewHolder viewHolder, int i) {
        final QuestionAndAnswers singleTutorial = tutorials.get(i);
        viewHolder.questionNumber.setText(""+singleTutorial.getQuestionNumber());
        viewHolder.question.setText(singleTutorial.getQuestion());
        viewHolder.choice1.setText(singleTutorial.getChoices().getChoice1());
        viewHolder.choice2.setText(singleTutorial.getChoices().getChoice2());
        viewHolder.choice3.setText(singleTutorial.getChoices().getChoice3());
        viewHolder.choice4.setText(singleTutorial.getChoices().getChoice4());
        viewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checked = (RadioButton)group.findViewById(checkedId);
                if (checked.isChecked()){
                    if (checked.getTag().equals(singleTutorial.getAnswer())){
                        viewHolder.radioGroup.setEnabled(false);
                        checked.setBackgroundColor(Color.GREEN);
                        checked.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check_white_24dp,0);
                    }else {
                        viewHolder.radioGroup.setEnabled(false);
                        checked.setBackgroundColor(Color.RED);
                        checked.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_clear_white_24dp,0);

                    }

                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return tutorials.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView questionNumber,question;
        private RadioGroup radioGroup;
        private RadioButton choice1,choice2,choice3,choice4;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            questionNumber = itemView.findViewById(R.id.questionNumber);
            question = itemView.findViewById(R.id.question);
            choice1 = itemView.findViewById(R.id.choice1);
            choice2 = itemView.findViewById(R.id.choice2);
            choice3 = itemView.findViewById(R.id.choice3);
            choice4 = itemView.findViewById(R.id.choice4);
            radioGroup = itemView.findViewById(R.id.testRadioGroup);

        }
    }
}
