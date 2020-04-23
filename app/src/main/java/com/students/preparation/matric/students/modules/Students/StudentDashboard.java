package com.students.preparation.matric.students.modules.Students;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.students.Constants;
import com.students.preparation.matric.students.R;
import com.students.preparation.matric.students.modules.Home.LoginActivity;
import com.students.preparation.matric.students.model.StudentsModel;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class StudentDashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Intent intent;
    DrawerLayout drawer;
    public static List<StudentsModel> studentsModelList;
    String phoneNumber;
    public static StudentDashboard context;

    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2444;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeLoginLogoutStatus();
        context =this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_textbook,
                R.id.nav_reference,
                R.id.nav_tutorials,
                R.id.nav_plasma,
                R.id.nav_teachers_guide,
                R.id.nav_shortnotes,
                R.id.nav_entrance_exam,
                R.id.nav_model_exam,
                R.id.nav_studytips,
                R.id.nav_adminInbox,
                R.id.nav_teachersInbox,
                R.id.nav_grades,
                R.id.nav_quiz,
                R.id.nav_shortnotes,
                R.id.nav_worksheet,
                R.id.nav_my_shortnotes,
                R.id.nav_web,
                R.id.nav_worksheet,
                R.id.nav_about,
                R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        if(isStoragePermissionGranted()) {

            checkStatus();
        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                //Toast.makeText(this , "The Application will not work unless you accept the permission" , Toast.LENGTH_LONG).show();


                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Toast.makeText(this , "The Application will not work unless you accept the permission" , Toast.LENGTH_LONG).show();

                    //Close the App
                    //finish();
                    showInfo("Permission Needed" , "The Application will not work unless you accept the permission! \n Re-run the Application and Accept the Permission.");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void showInfo(String title , String content) {
        AlertDialog.Builder alert = new AlertDialog.Builder(StudentDashboard.this);
        alert.setTitle(title);
        alert.setMessage(content);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.show();
    }

    private void checkStatus() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String phoneNumber = prefs.getString(Constants.LOGGED_IN_USER_PHONE_NUMBER, null);
        String name = prefs.getString(Constants.LOGGED_IN_USER_FULL_NAME, null);
        String school = prefs.getString(Constants.LOGGED_IN_USER_SCHOOL, null);
        String stream = prefs.getString(Constants.LOGGED_IN_USER_STREAM, null);

        if (phoneNumber == null) {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {


            case R.id.action_rate:
                // Rate 5 stars code
                Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                intent = new Intent(StudentDashboard.this, StudentDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                }

                return true;

            case R.id.action_share:
                //Share App
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    String sAux = "\nEntrance Preparation\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, getString(R.string.menu_share)));
                } catch (Exception e) {
                    //e.toString();
                }
                return true;

            case R.id.action_logout:

                AlertDialog.Builder builder = new AlertDialog.Builder(StudentDashboard.this);
                builder.setCancelable(true);
                builder.setTitle("Are you sure you want to change active status?");
                //builder.setMessage("Do you want to Approve " + uploadsModel.get_fullName() + "?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //if(isInternetAvailable(StudentDashboard.this)) {
                                //if(InternetConnection.checkConnection(getApplicationContext())) {
                                    logoutUserNow();
                                //}else{
                                  //  Toast.makeText(getApplicationContext() , "Please connect to an Internet" , Toast.LENGTH_LONG).show();
                                //}
                            }
                        });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void logoutUserNow() {
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            phoneNumber = prefs.getString(Constants.LOGGED_IN_USER_PHONE_NUMBER, null);

            prefs.edit().putString(Constants.LOGGED_IN_USER_PHONE_NUMBER, null).apply();
            prefs.edit().putString(Constants.LOGGED_IN_USER_FULL_NAME, null).apply();
            prefs.edit().putString(Constants.LOGGED_IN_USER_SCHOOL, null).apply();
            prefs.edit().putString(Constants.LOGGED_IN_USER_STREAM, null).apply();
            prefs.edit().putString(Constants.LOGGED_IN_USER_STUDENT_ID, null).apply();

            startActivity(new Intent(StudentDashboard.this, LoginActivity.class));

            //updateActiveStatusDBLogout(phoneNumber,"true");
            //changeLoginLogoutStatus(phoneNumber,"true");
            //uncomment the below code to change status
            // changeActiveStatus(phoneNumber , "true");
            //

            //finish();
        } catch (Exception e) {
            //e.toString();
        }
    }

    public static void changeLoginLogoutStatus() {

        studentsModelList = new ArrayList<>();

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_APPROVED_STUDENTS);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    StudentsModel stModel = postSnapshot.getValue(StudentsModel.class);
                    studentsModelList.add(stModel);
                }

                //changeActiveStatus(phoneNumber , status);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void changeActiveStatus(String pn , String status) {

        StudentsModel stu = new StudentsModel();
        boolean stuFound = false;

        for (StudentsModel student : studentsModelList) {
          try {
              if (student.get_mobileNumber().compareToIgnoreCase(pn) == 0 ){//&& student.get_activeStatus().compareToIgnoreCase(status) != 0) {

                  stu = student;
                  stuFound = true;
                  //stu.set_activeStatus(status);
                  //student.get_activeStatus() = status;

                  break;
              }
          }catch (NullPointerException ignore){
              //finish();
          }

          //i++;
        }


        if(stuFound){//i >= studentsModelList.size()) {
            stu.set_activeStatus(status);
            updateStudentToDatabase(stu);

            stuFound = false;
        }

    }

    private static void updateStudentToDatabase(StudentsModel studentData) {

        Toast.makeText(context, studentData.get_mobileNumber()+" "+studentData.get_activeStatus(), Toast.LENGTH_LONG).show();

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_APPROVED_STUDENTS);

        mDatabaseReference.child(studentData.get_studentId()).setValue(studentData)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to update Main DB", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_REG_STUDENTS).child(studentData.get_studentId());
                        Toast.makeText(context, "Updated", Toast.LENGTH_LONG).show();

                    }
                });

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        //Open drawer on back pressed
        if (!drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.openDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    public static boolean isInternetAvailable(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager)ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            try {
                URL url = new URL("http://www.google.com/");
                HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
                urlc.setRequestProperty("User-Agent", "test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1000); // mTimeout is in seconds
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                Log.i("warning", "Error checking internet connection", e);
                return false;
            }
        }

        return false;

    }


}
