package com.students.preparation.matric.students.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
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

import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.model.AfterFinishModel;
import com.students.preparation.matric.students.model.QuestionAndAnswers;
import com.students.preparation.matric.students.modules.Students.activities.StartTestActivity;

import java.util.ArrayList;

public class AfterFinishiRecyclerViewAdapter extends RecyclerView.Adapter<AfterFinishiRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<QuestionAndAnswers> tutorials;
    private boolean answered = false;
    private String answerTypes;
    private String fileName;
    private StartTestActivity startActivity;
    private boolean isAfterFinish = false;
    private ArrayList<AfterFinishModel> afterFinishArray;
    public AfterFinishiRecyclerViewAdapter(Context context ,ArrayList<QuestionAndAnswers> tutorialsArrayList,String answerType,String fileName,ArrayList<AfterFinishModel> afterFinishModels) {
        this.context = context;
        this.tutorials = tutorialsArrayList;
        this.answerTypes = answerType;
        this.fileName = fileName;
        this.afterFinishArray = afterFinishModels;
    }

    @NonNull
    @Override
    public AfterFinishiRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.question_and_answer_recyclerview_ayout, viewGroup, false);
        return new AfterFinishiRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AfterFinishiRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        final QuestionAndAnswers singleTutorial = tutorials.get(i);
        final AfterFinishModel finishModel = afterFinishArray.get(i);

        viewHolder.questionNumber.setText(""+singleTutorial.getQuestionNumber());
        viewHolder.question.setText(Html.fromHtml(singleTutorial.getQuestion()));
        viewHolder.choice1.setText(singleTutorial.getChoices().getChoice1());
        viewHolder.choice2.setText(singleTutorial.getChoices().getChoice2());
        viewHolder.choice3.setText(singleTutorial.getChoices().getChoice3());
        viewHolder.choice4.setText(singleTutorial.getChoices().getChoice4());
        viewHolder.descriptionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.answersTextView.setText(singleTutorial.getExplanations());
                viewHolder.answerLayout.setVisibility(View.VISIBLE);
            }
        });

        if (singleTutorial.getAnswer().equals(finishModel.getAsnwer())){
            String value = finishModel.getAsnwer()
                    .substring(finishModel.getAsnwer().lastIndexOf("_")+1,finishModel.getAsnwer().length());
            int index = Integer.valueOf(value);
            if (index==1){
                RadioButton radioButton = (RadioButton) viewHolder.radioGroup.getChildAt(0);
                radioButton.setBackgroundColor(Color.GREEN);
                radioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_white_24dp, 0);
            }else {
                RadioButton radioButton = (RadioButton) viewHolder.radioGroup.getChildAt(Integer.valueOf(value)-1);
                radioButton.setBackgroundColor(Color.GREEN);
                radioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_white_24dp, 0);

            }
        }else {

            String userAnservalue = finishModel.getAsnwer()
                    .substring(finishModel.getAsnwer().lastIndexOf("_")+1,finishModel.getAsnwer().length());

            int usersAnserIndex = Integer.valueOf(userAnservalue);
            if (usersAnserIndex==1){
                RadioButton radioButton= (RadioButton) viewHolder.radioGroup.getChildAt(0);
                radioButton.setBackgroundColor(Color.RED);
                radioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_white_24dp, 0);
            }else {
                RadioButton radioButton= (RadioButton) viewHolder.radioGroup.getChildAt(usersAnserIndex-1);
                radioButton.setBackgroundColor(Color.RED);
                radioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_white_24dp, 0);

            }



            String mainAnservalue = singleTutorial.getAnswer()
                    .substring(singleTutorial.getAnswer().lastIndexOf("_")+1,singleTutorial.getAnswer().length());

            int mainAnserIndex = Integer.valueOf(mainAnservalue);

            if (mainAnserIndex==1){
                RadioButton mainAadioButton= (RadioButton) viewHolder.radioGroup.getChildAt(0);
                mainAadioButton.setBackgroundColor(Color.GREEN);
                mainAadioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_white_24dp, 0);

            }else {
                RadioButton mainAadioButton = (RadioButton) viewHolder.radioGroup.getChildAt(mainAnserIndex-1);
                mainAadioButton.setBackgroundColor(Color.GREEN);
                mainAadioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_white_24dp, 0);
            }
        }
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

        }
    }
}
