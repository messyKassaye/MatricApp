package com.students.preparation.matric.students.modules.teachers;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;
import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.TokenService;
import com.students.preparation.matric.students.modules.Home.LoginActivity;
import com.students.preparation.matric.students.modules.Home.contact.ContactFragment;
import com.students.preparation.matric.students.modules.Students.fragment.AboutFragment;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

public class TeachersDashboard extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_dashboard);
        toolbar = findViewById(R.id.teachersBar);
        toolbar.setTitle("Teachers dashboard");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new AddBooks();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, newFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        addHomeFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teachers_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.teacher_logout:
                logout();
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout(){
        finish();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        TokenService.teachersLogout(getApplicationContext());
        startActivity(intent);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        if(id==R.id.nav_teachers_home){
            fragment = new TeachersHome();
            toolbar.setTitle("Teachers dashboard");
        } else if(id == R.id.nav_teacher_reference){
            fragment = new AddBooks();
            toolbar.setTitle("Add new reference");
        }else if(id==R.id.nav_teachers_logout){
            logout();
        }else if (id==R.id.nav_teacher_about){
            fragment = new AboutFragment();
            toolbar.setTitle("About us");
        }else if (id==R.id.nav_teachers_contact){
            fragment = new ContactFragment();
            toolbar.setTitle("Contact us");
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addHomeFragment(){
        Fragment newFragment = new TeachersHome();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
