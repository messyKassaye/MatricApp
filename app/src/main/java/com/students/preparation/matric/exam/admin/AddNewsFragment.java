package com.students.preparation.matric.exam.admin;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.News;


public class AddNewsFragment extends Fragment {

    private EditText newsTitle,newsLink;
    private Button addNews;
    private LinearLayout loadingLayout;
    private TextView errorShower;
    private DatabaseReference databaseReference;
    public AddNewsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_add_news, container, false);

        databaseReference = FirebaseDatabase
                .getInstance().getReference(Constants.DATABASE_PATH_NEWS);
        errorShower = view.findViewById(R.id.newsErrorShower);

        newsTitle = view.findViewById(R.id.newsTitle);
        newsLink = view.findViewById(R.id.newsLink);

        addNews = view.findViewById(R.id.addNews);

        loadingLayout = view.findViewById(R.id.loadingLayout);

        addNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = newsTitle.getText().toString();
                String link = newsLink.getText().toString();
                if (title.equals("")){
                    errorShower.setText("Please enter news title");
                    errorShower.setTextColor(Color.RED);
                }else if (link.equals("")){
                    errorShower.setText("Please enter news link");
                    errorShower.setTextColor(Color.RED);
                }else {
                    addNews.setVisibility(View.GONE);
                    loadingLayout.setVisibility(View.VISIBLE);
                    String nid = databaseReference.push().getKey();
                    News news = new News(title,link);
                    databaseReference.child(nid).setValue(news).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            errorShower.setText("News added successfully");
                            errorShower.setTextColor(Color.GREEN);

                          addNews.setVisibility(View.VISIBLE);
                          loadingLayout.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });

     return view;
    }


}
