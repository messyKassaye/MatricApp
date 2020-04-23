package com.students.preparation.matric.students.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.model.AfterFinishModel;
import com.students.preparation.matric.students.model.CorrectInCorrect;
import com.students.preparation.matric.students.model.QuestionAndAnswers;
import com.students.preparation.matric.students.modules.Students.activities.StartTestActivity;

import java.util.ArrayList;

public class QuestionAndAnswerAdapter extends RecyclerView.Adapter<QuestionAndAnswerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<QuestionAndAnswers> tutorials;
    private boolean answered = false;
    private String answerTypes;
    private String fileName;
    private StartTestActivity startActivity;
    private String subject;

    public QuestionAndAnswerAdapter(String subject, Context context, StartTestActivity activity, ArrayList<QuestionAndAnswers> tutorialsArrayList, String answerType, String fileName) {
        this.context = context;
        this.tutorials = tutorialsArrayList;
        this.answerTypes = answerType;
        this.fileName = fileName;
        this.startActivity = activity;
        this.subject = subject;
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

        if (singleTutorial.getType().equalsIgnoreCase("formula")) {
            viewHolder.nonFormulaSubject.setVisibility(View.GONE);
            viewHolder.formulaBasedSubjects.setVisibility(View.VISIBLE);
            displayFormulas(viewHolder, singleTutorial);
        } else if (singleTutorial.getType().equalsIgnoreCase("image")) {
            viewHolder.imageQuestion.setVisibility(View.VISIBLE);
            viewHolder.imageQuestion.setImageBitmap(convertBase64(singleTutorial.getBase64Image()));

        } else {
            viewHolder.descriptionLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!answered) {
                        viewHolder.answerIsNotProvided.setVisibility(View.VISIBLE);
                    }
                }
            });
            viewHolder.questionNumber.setText("" + singleTutorial.getQuestionNumber());
            viewHolder.question.setText(Html.fromHtml(singleTutorial.getQuestion()));
            viewHolder.choice1.setText(singleTutorial.getChoices().getChoice1());
            viewHolder.choice2.setText(singleTutorial.getChoices().getChoice2());
            viewHolder.choice3.setText(singleTutorial.getChoices().getChoice3());
            viewHolder.choice4.setText(singleTutorial.getChoices().getChoice4());
            viewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton checked = (RadioButton) group.findViewById(checkedId);
                    answered = true;
                    viewHolder.answerIsNotProvided.setVisibility(View.GONE);
                    if (checked.isChecked()) {
                        if (answerTypes.equalsIgnoreCase("Right away")) {
                            viewHolder.descriptionLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    System.out.println("Explanations: " + singleTutorial.getExplanations());
                                    if (answered) {
                                        viewHolder.answersTextView.setText(singleTutorial.getExplanations());
                                        viewHolder.answerLayout.setVisibility(View.VISIBLE);
                                    } else {
                                        viewHolder.answerIsNotProvided.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                            viewHolder.choice4.setEnabled(false);
                            viewHolder.choice3.setEnabled(false);
                            viewHolder.choice2.setEnabled(false);
                            viewHolder.choice1.setEnabled(false);
                            int radioButtonLength = viewHolder.radioGroup.getChildCount();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < radioButtonLength; i++) {
                                        if (viewHolder.radioGroup.getChildAt(i).getTag().equals(singleTutorial.getAnswer())) {
                                            ((RadioButton) viewHolder.radioGroup.getChildAt(i)).setBackgroundColor(Color.GREEN);
                                            ((RadioButton) viewHolder.radioGroup.getChildAt(i)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_white_24dp, 0);
                                        }
                                    }
                                }
                            }, 500);
                            if (checked.getTag().equals(singleTutorial.getAnswer())) {
                                CorrectInCorrect correct = new CorrectInCorrect(
                                        singleTutorial.getQuestionNumber(), "Correct");
                                startActivity.examArami(correct);

                                viewHolder.radioGroup.setEnabled(false);
                                checked.setBackgroundColor(Color.GREEN);
                                checked.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_white_24dp, 0);

                            } else {
                                viewHolder.radioGroup.setEnabled(false);

                                CorrectInCorrect correct = new CorrectInCorrect(
                                        singleTutorial.getQuestionNumber(), "InCorrect");
                                startActivity.examArami(correct);

                                checked.setBackgroundColor(Color.RED);
                                checked.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_white_24dp, 0);
                            }
                        } else if (answerTypes.equalsIgnoreCase("After finishing")) {
                            //save after finish data
                            AfterFinishModel finishModel = new AfterFinishModel(
                                    singleTutorial.getQuestionNumber(),
                                    checked.getTag().toString());
                            startActivity.saveAfterFinishData(finishModel);

                            if (checked.getTag().equals(singleTutorial.getAnswer())) {
                                CorrectInCorrect correct = new CorrectInCorrect(
                                        singleTutorial.getQuestionNumber(), "Correct");
                                startActivity.examArami(correct);
                            } else {
                                CorrectInCorrect correct = new CorrectInCorrect(
                                        singleTutorial.getQuestionNumber(), "Incorrect");
                                startActivity.examArami(correct);
                            }
                        }
                    }
                }
            });

        }

    }

    public Bitmap convertBase64(String data) {
        String pureBase64Encoded = "";
        if (data.contains(",")) {
            pureBase64Encoded = data.substring(data.indexOf(",") + 1);
        } else {
            pureBase64Encoded = data;
        }
        byte[] decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public void displayFormulas(QuestionAndAnswerAdapter.ViewHolder viewHolder, QuestionAndAnswers single) {
        viewHolder.formulaQuestionNumber.setText("" + single.getQuestionNumber());
        WebSettings webSettings = viewHolder.questionWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        viewHolder.questionWebView.loadDataWithBaseURL(
                "file:///android_asset/mathscribe/",
                setJavascript(single.getQuestion()),
                "text/html",
                "UTF-8", null);


        WebSettings choiceSettings = viewHolder.formulaChoice1.getSettings();
        choiceSettings.setJavaScriptEnabled(true);
        viewHolder.formulaChoice1.loadDataWithBaseURL(
                "file:///android_asset/mathscribe/",
                setJavascript(single.getChoices().getChoice1()),
                "text/html",
                "UTF-8", null);

        WebSettings choice2Settings = viewHolder.formulaChoice2.getSettings();
        choice2Settings.setJavaScriptEnabled(true);
        viewHolder.formulaChoice2.loadDataWithBaseURL(
                "file:///android_asset/mathscribe/",
                setJavascript(single.getChoices().getChoice2()),
                "text/html",
                "UTF-8", null);

        WebSettings choice3Settings = viewHolder.formulaChoice3.getSettings();
        choice3Settings.setJavaScriptEnabled(true);
        viewHolder.formulaChoice3.loadDataWithBaseURL(
                "file:///android_asset/mathscribe/",
                setJavascript(single.getChoices().getChoice3()),
                "text/html",
                "UTF-8", null);

        WebSettings choice4Settings = viewHolder.formulaChoic4.getSettings();
        choice4Settings.setJavaScriptEnabled(true);
        viewHolder.formulaChoic4.loadDataWithBaseURL(
                "file:///android_asset/mathscribe/",
                setJavascript(single.getChoices().getChoice4()),
                "text/html",
                "UTF-8", null);

    }



    public String setJavascript(String javascript) {
        String path = "file:///android_asset/mathscribe/";
        String js = "<html><head>"
                + "<link rel='stylesheet' href='" + path + "jqmath-0.4.3.css'>"
                + "<script src='" + path + "jquery-1.4.3.min.js'></script>"
                + "<script src='" + path + "jqmath-etc-0.4.6.min.js'></script>"
                + "</head><body>"
                + "<script>var s = '" + javascript + "';M.parseMath(s);document.write(s);</script></body>";

        return js;
    }

    @Override
    public int getItemCount() {
        return tutorials.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView questionNumber, question;
        private RadioGroup radioGroup;
        private RadioButton choice1, choice2, choice3, choice4;
        private LinearLayout answerLayout, descriptionLayout;
        private TextView answersTextView;
        private TextView answerIsNotProvided;
        private ImageView imageQuestion;
        private final LinearLayout nonFormulaSubject, formulaBasedSubjects;

        private final WebView questionWebView;
        private final WebView formulaChoice1, formulaChoice2, formulaChoice3, formulaChoic4;
        private final TextView formulaQuestionNumber;


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
            answersTextView = itemView.findViewById(R.id.answerTextView);
            descriptionLayout = itemView.findViewById(R.id.descriptionLayout);
            answerIsNotProvided = itemView.findViewById(R.id.answerIsNotProvided);
            imageQuestion = itemView.findViewById(R.id.questionImage);
            nonFormulaSubject = itemView.findViewById(R.id.nonFormulasSubject);
            formulaBasedSubjects = itemView.findViewById(R.id.formulaBasedSubject);
            questionWebView = itemView.findViewById(R.id.formulaQuestion);
            formulaQuestionNumber = itemView.findViewById(R.id.formulasQuestionNumber);
            formulaChoice1 = itemView.findViewById(R.id.formChoice1);
            formulaChoice2 = itemView.findViewById(R.id.formChoice3);
            formulaChoice3 = itemView.findViewById(R.id.formChoice3);
            formulaChoic4 = itemView.findViewById(R.id.formChoice4);

        }
    }
}
