package com.students.preparation.matric.exam.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;

import java.util.ArrayList;
import java.util.HashMap;


public class ExamAdapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflter = null;

    public static ArrayList<String> selectedAnswers;

    RadioButton choice1,choice2,choice3,choice4;

    public ExamAdapter(Context ctx, Activity a, ArrayList<HashMap<String, String>> d){
        this.activity = a;
        this.data = d;
        this.context = ctx;
        //this.list = list;

        inflter = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        //Count=Size of ArrayList.
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }
    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        if (view != null) return view;

        view = inflter.inflate(R.layout.exam_question_row, null);

        // get the reference

        TextView question = (TextView) view.findViewById(R.id.exam_question);
        choice1 = (RadioButton) view.findViewById(R.id.radio_choice_1);
        choice2 = (RadioButton) view.findViewById(R.id.radio_choice_2);
        choice3 = (RadioButton) view.findViewById(R.id.radio_choice_3);
        choice4 = (RadioButton) view.findViewById(R.id.radio_choice_4);

        TextView answer = view.findViewById(R.id.the_answer);
        final TextView explanation = view.findViewById(R.id.the_explanation);

        ImageView optionalImageView = view.findViewById(R.id.exam_question_image);

        HashMap<String, String> listdata = new HashMap<String, String>();
        listdata = data.get(position);

        // Setting all values in listview
        question.setText(listdata.get(Constants.EXAM_QUESTION));
        choice1.setText(listdata.get(Constants.EXAM_CHOICE_1));
        choice2.setText(listdata.get(Constants.EXAM_CHOICE_2));
        choice3.setText(listdata.get(Constants.EXAM_CHOICE_3));
        choice4.setText(listdata.get(Constants.EXAM_CHOICE_4));

        answer.setText(listdata.get(Constants.EXAM_ANSWER));
        explanation.setText(listdata.get(Constants.EXAM_EXPLANATION));

        //Toast.makeText(context, listdata.get(Constants.EXAM_QUESTION), Toast.LENGTH_LONG).show();
        //Toast.makeText(context, listdata.get(Constants.EXAM_OPTIONAL_IMAGE), Toast.LENGTH_LONG).show();
        if (listdata.get(Constants.EXAM_OPTIONAL_IMAGE) != null && listdata.get(Constants.EXAM_OPTIONAL_IMAGE).compareTo("null") != 0) {
           /* Glide
                    .with(activity)
                    .load("https://firebasestorage.googleapis.com/v0/b/matric-app.appspot.com/o/uploads%2F1583442125651.jpg")
                    .into(optionalImageView);*/

            optionalImageView.setVisibility(View.VISIBLE);
            Glide.with(view.getContext())
                    .load(listdata.get(Constants.EXAM_OPTIONAL_IMAGE))
                    .placeholder(R.drawable.loading)
                    //.dontAnimate()
                    .into(optionalImageView);


        } else {
            optionalImageView.setVisibility(View.GONE);

        }


        final RadioGroup answerGroup = (RadioGroup)view.findViewById(R.id.exam_question_choices_radio);

        final HashMap<String, String> finalListdata = listdata;
        answerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //Toast.makeText(context,""+i , Toast.LENGTH_LONG).show();
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);

                if(Integer.valueOf(finalListdata.get(Constants.EXAM_ANSWER)) == (index+1)){
                    radioButton.setBackgroundColor(Color.GREEN);

                    explanation.setVisibility(View.VISIBLE);

                }else{

                    radioButton.setBackgroundColor(Color.RED);

                }
            }
        });

// set the value in TextView
        return view;
    }
}