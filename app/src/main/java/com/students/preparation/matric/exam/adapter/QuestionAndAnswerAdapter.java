package com.students.preparation.matric.exam.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.TokenService;
import com.students.preparation.matric.exam.model.QuestionAndAnswers;
import java.util.ArrayList;

public class QuestionAndAnswerAdapter  extends RecyclerView.Adapter<QuestionAndAnswerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<QuestionAndAnswers> tutorials;
    private boolean answered = false;
    private String answerTypes;
    private String fileName;

    public QuestionAndAnswerAdapter(Context context, ArrayList<QuestionAndAnswers> tutorialsArrayList,String answerType,String fileName) {
        this.context = context;
        this.tutorials = tutorialsArrayList;
        this.answerTypes = answerType;
        this.fileName = fileName;
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
        viewHolder.descriptionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Explanations: "+singleTutorial.getExplanations());
                if (answered){
                    viewHolder.answersTextView.setText(singleTutorial.getExplanations());
                    viewHolder.answerLayout.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.answerIsNotProvided.setVisibility(View.VISIBLE);
                }
            }
        });
        viewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checked = (RadioButton)group.findViewById(checkedId);
                answered = true;
                viewHolder.answerIsNotProvided.setVisibility(View.GONE);
                if (checked.isChecked()){
                    viewHolder.choice4.setEnabled(false);
                    viewHolder.choice3.setEnabled(false);
                    viewHolder.choice2.setEnabled(false);
                    viewHolder.choice1.setEnabled(false);
                    if (answerTypes.equalsIgnoreCase("Right way")) {
                        if (checked.getTag().equals(singleTutorial.getAnswer())) {
                            TokenService.writeExamTest(
                                    context,
                                    fileName.substring(0,fileName.lastIndexOf(".")),
                                    "Correct",
                                    1);
                            viewHolder.radioGroup.setEnabled(false);
                            checked.setBackgroundColor(Color.GREEN);
                            checked.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_white_24dp, 0);
                        } else {
                            viewHolder.radioGroup.setEnabled(false);
                            TokenService.writeExamTest(
                                    context,
                                    fileName.substring(0,fileName.lastIndexOf(".")),
                                    "Incorrect",1);
                            checked.setBackgroundColor(Color.RED);
                            checked.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_white_24dp, 0);
                        }
                    }else {
                        if (checked.getTag().equals(singleTutorial.getAnswer())){
                            TokenService.writeExamTest(
                                    context,
                                    fileName.substring(0,fileName.lastIndexOf(".")),
                                    "Correct",
                                    1);
                        }else {
                            TokenService.writeExamTest(
                                    context,
                                    fileName.substring(0,fileName.lastIndexOf(".")),
                                    "Incorrect",
                                    1);
                        }
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
        private LinearLayout answerLayout,descriptionLayout;
        private ImageView showAnswer;
        private TextView answersTextView;
        private TextView answerIsNotProvided;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            questionNumber = itemView.findViewById(R.id.questionNumber);
            question = itemView.findViewById(R.id.question);
            choice1 = itemView.findViewById(R.id.choice1);
            choice2 = itemView.findViewById(R.id.choice2);
            choice3 = itemView.findViewById(R.id.choice3);
            choice4 = itemView.findViewById(R.id.choice4);
            radioGroup = itemView.findViewById(R.id.testRadioGroup);
            answerLayout = itemView.findViewById(R.id.answersLayout);
            showAnswer = itemView.findViewById(R.id.showDescription);
            answersTextView = itemView.findViewById(R.id.answerTextView);
            descriptionLayout = itemView.findViewById(R.id.descriptionLayout);
            answerIsNotProvided = itemView.findViewById(R.id.answerIsNotProvided);

        }
    }
}
