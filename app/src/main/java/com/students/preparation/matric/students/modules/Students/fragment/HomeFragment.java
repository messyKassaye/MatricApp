package com.students.preparation.matric.students.modules.Students.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.model.CorrectInCorrect;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ArrayList<CorrectInCorrect> naturalsList = new ArrayList<>();
    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);




        return root;
    }
}