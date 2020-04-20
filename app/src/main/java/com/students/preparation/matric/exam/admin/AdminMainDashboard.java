package com.students.preparation.matric.exam.admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.TokenService;
import com.students.preparation.matric.exam.modules.Home.LoginActivity;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AdminMainDashboard extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

private ProgressBar progressBar;
private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_dashboard);
        toolbar = findViewById(R.id.mainToolBar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Admin dashboard");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        progressBar = findViewById(R.id.adminProgressBar);

        addHomeFragment();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.admin_main_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

          switch (item.getItemId()){
              case R.id.admin_logout:
                  finish();
                  TokenService.adminLogout(getApplicationContext());
                  Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                  TokenService.adminLogout(getApplicationContext());
                  startActivity(intent);
          }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        Fragment fragment = null;
        if(id==R.id.nav_home){
            fragment = new AdminHomeFragment();
            toolbar.setTitle("Admin dashboard");
        } else if(id == R.id.nav_new_student){
            fragment = new ApproveRegisteredStudents();
            toolbar.setTitle("New students");
        }else if(id==R.id.nav_approved_student){
            fragment = new ViewApprovedStudents();
            toolbar.setTitle("Approved student");
        }else if(id==R.id.nav_add_reference){
            fragment = new AddReferences();
            toolbar.setTitle("Add reference");

        }else if(id==R.id.nav_exams){
            fragment = new AddEntModelExam();
            toolbar.setTitle("Add exams");
        }else if(id==R.id.nav_notes){
            fragment = new AddNoteTipsReferences();
            toolbar.setTitle("Add notes");
        }else if(id==R.id.nav_add_tutorial){
            fragment = new AddTutorialFragment();
            toolbar.setTitle("Add tutorial");
        }else if(id==R.id.nav_add_plasma){
            fragment = new AddPlasmaLesson();
            toolbar.setTitle("Add plasma lesson");
        }else if(id==R.id.nav_news){
            fragment = new AddNewsFragment();
            toolbar.setTitle("Add news");
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        progressBar.setVisibility(View.GONE);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addHomeFragment(){
        progressBar.setVisibility(View.GONE);
        Fragment newFragment = new AdminHomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void showNewStudentFragment(){
        toolbar.setTitle("New Students");
        Fragment newFragment = new ApproveRegisteredStudents();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, newFragment);
        ft.commit();
    }

    public void showApprovedStudentFragment(){
        toolbar.setTitle("Approved Students");
        Fragment newFragment = new ViewApprovedStudents();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, newFragment);
        ft.commit();
    }


}
