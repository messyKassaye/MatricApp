package com.students.preparation.matric.exam.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.students.preparation.matric.exam.R;

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        ListView adminFunctions = findViewById(R.id.list_admin_dashoard);
        adminFunctions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    startActivity(new Intent(AdminDashboard.this , ApproveRegisteredStudents.class));
                }
                else if(position == 1){
                    startActivity(new Intent(AdminDashboard.this , ViewApprovedStudents.class));
                }
                else if(position == 2){
                    startActivity(new Intent(AdminDashboard.this , AddReferences.class));
                }
                else if(position == 3){
                    startActivity(new Intent(AdminDashboard.this , AddEntModelExam.class));
                }
                else if(position == 4){
                    startActivity(new Intent(AdminDashboard.this , AddNoteTipsReferences.class));
                }else if(position==5){
                    Intent intent = new Intent(getApplicationContext(),AddTutorialFragment.class);
                    startActivity(intent);
                }else if (position==6){
                    Intent intent = new Intent(getApplicationContext(),AddPlasmaLesson.class);
                    startActivity(intent);

                }
            }
        });
    }
}
