package com.students.preparation.matric.exam.modules.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.InternetConnection;
import com.students.preparation.matric.exam.TokenService;
import com.students.preparation.matric.exam.admin.AdminMainDashboard;
import com.students.preparation.matric.exam.modules.Students.StudentDashboard;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.admin.AdminDashboard;
import com.students.preparation.matric.exam.model.StudentsModel;
import com.students.preparation.matric.exam.admin_teachers.TeachersActivity;
import com.students.preparation.matric.exam.modules.Home.registration.Registration;
import com.students.preparation.matric.exam.modules.Home.contact.ContactActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LoginActivity extends AppCompatActivity {
    final int PERMISSION_ALL = 1111;

    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 787549;
    //private LoginViewModel loginViewModel;
    public static List<StudentsModel> studentsModelList;
    StudentsModel loggedInStudent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
          //      .get(LoginViewModel.class);


        //Login the user automatically
        if(TokenService.getAdminToken(getApplicationContext())==null){
            checkLoggedInStatus();
        }else {
            Intent intent = new Intent(getApplicationContext(),AdminMainDashboard.class);
            startActivity(intent);
        }

        studentsModelList = new ArrayList<>();

        populateApprovedStudents();
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        TextView registerNow = findViewById(R.id.link_signup);

        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Register activity
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
                //finish();
            }
        });
        TextView contactUs = findViewById(R.id.link_contact_us_);

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Register activity
                Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Register activity
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
                //finish();
            }
        });



/*
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });*/
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUsingIMEI(usernameEditText);
                //verifyAndLoginUser(usernameEditText);

            }
        });


        //Login the Admin User
        TextView adminLogin = findViewById(R.id.link_admin_login);
        adminLogin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final AlertDialog dialogBuilder = new AlertDialog.Builder(LoginActivity.this).create();
                LayoutInflater inflater = LoginActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.admin_login_popup, null);

                final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
                Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
                Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);

                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                });
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        /*
                        LocalTime time = LocalTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        System.out.println(time.format(formatter));
                        */

                        Date date = new Date();
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HHmm");
                        //Toast.makeText(getApplicationContext() , ""+formatter.format(date) , Toast.LENGTH_LONG).show();

                        if (editText.getText().toString().compareTo("messy") == 0) {
                            startActivity(new Intent(LoginActivity.this,
                                    AdminMainDashboard.class));
                            TokenService.setAdminToken(getApplicationContext(),"adminIsLogin");
                            dialogBuilder.dismiss();
                        }
                    }
                });

                dialogBuilder.setView(dialogView);
                dialogBuilder.show();


                return true;
            }
        });

        //Login the Teachers User
        TextView teachersLogin = findViewById(R.id.link_teachers_login);
        teachersLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, TeachersActivity.class));
            }
        });


        checkForPermission();
    }

    private void checkForPermission() {
        String[] PERMISSIONS = {
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case PERMISSION_ALL: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            showDialogOK("SMS and Location Services Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkForPermission();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    finish();
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private void loginUsingIMEI(EditText usernameEditText) {
        String deviceId = getUniqueIdentifyer();

        boolean studentFound = false;
        boolean teacherFound = false;
        boolean differentDev = false;

        for (StudentsModel student : studentsModelList) {

            if (student.get_mobileNumber().compareToIgnoreCase(usernameEditText.getText().toString()) == 0) {

                System.out.println("DeviceIdOnline"+student.get_deviceId());

                if(student.get_txRefNum().compareToIgnoreCase("T") == 0){
                    teacherFound = true;
                }
                if (student.get_deviceId().compareToIgnoreCase(deviceId) == 0) {
                    loggedInStudent = student;
                    studentFound = true;


                    break;
                } else {

                    differentDev = true;
                    Toast.makeText(getApplicationContext(), "Please use the device that you have registered with.", Toast.LENGTH_LONG).show();
                }
            }

            //i++;
        }

        if(teacherFound){
            startActivity(new Intent(LoginActivity.this, TeachersActivity.class));
        }
        if(studentFound){
            loginStudent(loggedInStudent);
        }else if (!differentDev){
            Toast.makeText(getApplicationContext(), "Unapproved or Unregistered Phone, Please register and wait for approval.", Toast.LENGTH_LONG).show();
        }
    }

    private String getUniqueIdentifyer() {
        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) getSystemService(Context.
                TELEPHONY_SERVICE);
        /*
         * getDeviceId() returns the unique device ID.
         * For example,the IMEI for GSM and the MEID or ESN for CDMA phones.
         */
        //String deviceId = telephonyManager.getDeviceId();
        /*
         * getSubscriberId() returns the unique subscriber ID,
         * For example, the IMSI for a GSM phone.
         */
        if(isStoragePermissionGranted()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.

                    //subscriberId = telephonyManager.getSubscriberId();


                }
            }

            System.out.println("DEVICEID1 :" + telephonyManager.getDeviceId());
            return telephonyManager.getDeviceId();

        }
        //deviceId = telephonyManager.getSubscriberId();

        return "NotFound";
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);

                //Toast.makeText(this , "The Application will not work unless you accept the permission" , Toast.LENGTH_LONG).show();


                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    private void verifyAndLoginUser(EditText usernameEditText) {
        //if (StudentDashboard.isInternetAvailable(getApplicationContext())) {
        if(InternetConnection.checkConnection(getApplicationContext())) {

            //populateApprovedStudents();

            loggedInStudent = new StudentsModel();
            boolean studentFound = false;
            int i = 0;
            if(studentsModelList.size() != 0) {
                //Toast.makeText(getApplicationContext(),"StuSize: " + studentsModelList.size(),Toast.LENGTH_LONG).show();

                for (StudentsModel student : studentsModelList) {
                    //Toast.makeText(getApplicationContext(),student.get_mobileNumber()+" : " +student.get_activeStatus(),Toast.LENGTH_LONG).show();

                    if (student.get_mobileNumber().compareToIgnoreCase(usernameEditText.getText().toString()) == 0) {


                        if (student.get_activeStatus().compareToIgnoreCase("true") == 0) {
                            loggedInStudent = student;
                            studentFound = true;


                            break;
                        } else {
                            //Toast.makeText(getApplicationContext(), "You have to LOGOUT from the other device, while being online.", Toast.LENGTH_LONG).show();
                        }
                    }

                    i++;
                }
            }else{
                Toast.makeText(getApplicationContext() , "Unable to connect with the online database" ,Toast.LENGTH_LONG).show();
            }

            if (i != 0 && i == studentsModelList.size() && !studentFound) {
                Toast.makeText(getApplicationContext(), "If you have logged in using other device please Logout first. " +
                        "If you have already Registered, wait for Approval", Toast.LENGTH_LONG).show();
            }

            if(studentFound){

                try {
                    loginStudent(loggedInStudent);
                }catch (Exception ignore){}
                //Toast.makeText(getApplicationContext(), "Found the user", Toast.LENGTH_LONG).show();

                studentFound = false;
            }
        /*
        if(!studentFound){
            Toast.makeText(getApplicationContext(), "You have to LOGOUT from the other device, while being online.", Toast.LENGTH_LONG).show();
        }
        */



            //startActivity(new Intent(LoginActivity.this , StudentDashboard.class));


        /*loadingProgressBar.setVisibility(View.VISIBLE);
        loginViewModel.login(usernameEditText.getText().toString(),
                passwordEditText.getText().toString());

        */
        } else {
            Toast.makeText(getApplicationContext(), "Please connect to an Internet", Toast.LENGTH_LONG).show();
        }
    }

    private void checkLoggedInStatus() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String phoneNumber = prefs.getString(Constants.LOGGED_IN_USER_PHONE_NUMBER, null);
        String name = prefs.getString(Constants.LOGGED_IN_USER_FULL_NAME, null);
        String school = prefs.getString(Constants.LOGGED_IN_USER_SCHOOL, null);
        String stream = prefs.getString(Constants.LOGGED_IN_USER_STREAM, null);

        if (phoneNumber != null && stream != null) {
            startActivity(new Intent(LoginActivity.this, StudentDashboard.class));

            finish();
        }
    }


    private void loginStudent(StudentsModel loggedInStudent) {
        //Toast.makeText(getApplicationContext() , loggedInStudent.get_mobileNumber() , Toast.LENGTH_LONG).show();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        if (loggedInStudent.get_txRefNum().compareToIgnoreCase("T") == 0) {
            startActivity(new Intent(LoginActivity.this, TeachersActivity.class));
            //prefs.edit().putString(Constants.LOGGED_IN_USER_PHONE_NUMBER, loggedInStudent.get_mobileNumber()).apply();
            //prefs.edit().putString(Constants.LOGGED_IN_USER_IS_TEACHER, "YES").apply();



            finish();
        } else {
            //Save to session
            prefs.edit().putString(Constants.LOGGED_IN_USER_PHONE_NUMBER, loggedInStudent.get_mobileNumber()).apply();
            prefs.edit().putString(Constants.LOGGED_IN_USER_FULL_NAME, loggedInStudent.get_fullName()).apply();
            prefs.edit().putString(Constants.LOGGED_IN_USER_SCHOOL, loggedInStudent.get_school()).apply();
            prefs.edit().putString(Constants.LOGGED_IN_USER_STREAM, loggedInStudent.get_stream()).apply();
            prefs.edit().putString(Constants.LOGGED_IN_USER_STUDENT_ID, loggedInStudent.get_studentId()).apply();
            //prefs.edit().putString(Constants.LOGGED_IN_USER_PHONE_NUMBER, loggedInStudent.get_password());
            //prefs.apply();
            //String data = prefs.getString(variable, defaultValue);

            startActivity(new Intent(LoginActivity.this, StudentDashboard.class));

            //updateActiveStatusDB("false");
            //StudentDashboard.changeActiveStatus(loggedInStudent._activeStatus , "false");

            //StudentDashboard.changeLoginLogoutStatus(loggedInStudent._mobileNumber , "false");
            //StudentDashboard.changeActiveStatus(loggedInStudent._mobileNumber , "false");
            finish();
        }

        //populateApprovedStudents();
    }

    public void updateActiveStatusDB(String status) {
        loggedInStudent.set_activeStatus(status);
        updateStudentToDatabase(loggedInStudent);

    }

    public void updateActiveStatusDBLogout(String pNumber, String status) {


        //stM.set_activeStatus(status);
        //updateStudentToDatabase(stM);

    }

    private void updateStudentToDatabase(StudentsModel studentData) {

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_APPROVED_STUDENTS);

        mDatabaseReference.child(studentData.get_studentId()).setValue(studentData)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to update Main DB", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_REG_STUDENTS).child(studentData.get_studentId());

                    }
                });

    }

    /*
    private void updateUiWithUser(LoggedInUserView model) {
        //String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        //Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

        startActivity(new Intent(LoginActivity.this, StudentDashboard.class));
    }*/

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


    private void populateApprovedStudents() {
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_APPROVED_STUDENTS);
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    try {
                        StudentsModel studentsModel = postSnapshot.getValue(StudentsModel.class);
                        studentsModelList.add(studentsModel);
                    }catch (Exception ignore){}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
