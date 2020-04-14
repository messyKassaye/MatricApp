package com.students.preparation.matric.exam.admin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.students.preparation.matric.exam.R;


public class AdminHomeFragment extends Fragment {
    private TextView totalNewStudent,totalApprovedStudent,totalTutorial;
    private Button showAllNewStudent,showAllApprovedStudent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        //view initialization
        totalNewStudent = view.findViewById(R.id.newStudentsNumber);
        totalApprovedStudent = view.findViewById(R.id.approvedStudentsNumber);
        totalTutorial =view.findViewById(R.id.totalTutorialNumbers);

        showAllNewStudent = view.findViewById(R.id.showAllNewStudentsButton);
        showAllApprovedStudent = view.findViewById(R.id.showAllApprovedStudentButton);
        showAllNewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AdminMainDashboard dashboard= (AdminMainDashboard) (getActivity());
               dashboard.showNewStudentFragment();
            }
        });

        showAllApprovedStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminMainDashboard dashboard= (AdminMainDashboard) (getActivity());
                dashboard.showApprovedStudentFragment();
            }
        });

        return view;
    }

}
