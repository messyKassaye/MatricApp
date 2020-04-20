package com.students.preparation.matric.exam.modules.Home.registration;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.StudentsModel;
import com.students.preparation.matric.exam.modules.Home.contact.ContactActivity;

public class Registration extends AppCompatActivity {

    TextView loginLink, registrationInfo, contactus_info;
    Button registerBtn;
    String deviceId = "";

    EditText fullName, mobileNumber, password, confirmPassword, txRefNum;
    Spinner stream, bank;//school, bank;
    String _fullName, _mobileNumber, _password, _confirmPassword, _txRefNum, _stream, _school, _bank;
    String sid;
    private DatabaseReference mDatabase;
    //ProgressDialog progressDialog;
    ProgressBar progressDialog;
    private RadioGroup teacherAndStudentRadioGroup;
    private String role = "";
    private AppCompatAutoCompleteTextView school;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        school = (AppCompatAutoCompleteTextView) findViewById(R.id.school);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, Constants.schoolList);
        school.setThreshold(1); //will start working from first character
        school.setAdapter(adapter);


//Create a database connection
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_REG_STUDENTS);

        init();
        registerBtn = findViewById(R.id.btn_signup);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStudent();
            }
        });

        contactus_info = findViewById(R.id.contactus_info);
        contactus_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, ContactActivity.class));
            }
        });
        registrationInfo = findViewById(R.id.registration_info);
        loginLink = findViewById(R.id.link_login);

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });

        registrationInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog("How to Register", getResources().getString(R.string.registration_information), false);
            }
        });

        teacherAndStudentRadioGroup = findViewById(R.id.teacherAndStudentRadioGroup);
        teacherAndStudentRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.student:
                        txRefNum.setVisibility(View.VISIBLE);
                        role = "Student";
                        break;
                    case R.id.teacher:
                        txRefNum.setVisibility(View.GONE);
                        role = "Teacher";
                        _txRefNum = "T";
                        break;
                }
            }
        });


        deviceId =getUniqueIdentifyer();

    }

    private String getUniqueIdentifyer() {
        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) getSystemService(Context.
                TELEPHONY_SERVICE);

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


        return telephonyManager.getDeviceId();


    }

    private void init() {
        fullName = findViewById(R.id.input_name);
        mobileNumber = findViewById(R.id.input_mobile);
        password = findViewById(R.id.input_password);
        confirmPassword = findViewById(R.id.input_confirm_password);
        stream = findViewById(R.id.stream);
        school = findViewById(R.id.school);
        bank = findViewById(R.id.bank);
        txRefNum = findViewById(R.id.input_bank_reference);

        progressDialog = findViewById(R.id.registering_progress);

    }

    public void registerStudent() {

        if (!validate()) {
            onRegistrationFailed();
            return;
        }

        registerBtn.setEnabled(false);

        progressDialog.setVisibility(View.VISIBLE);

        if(deviceId != null) {

            registerStudentToDatabase();
        }else{
            Toast.makeText(getApplicationContext() , "Unable to Get your device ID, Please use another device" , Toast.LENGTH_LONG).show();
        }

    }

    private void registerStudentToDatabase() {

        //create a new id for new student
        sid = mDatabase.push().getKey();

        StudentsModel registrationModel = new StudentsModel(sid, _fullName, _mobileNumber, _password, _txRefNum, _stream, _school, _bank, deviceId, "true",role);

        if (sid != null) {
            mDatabase.child(sid).setValue(registrationModel)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            onRegistrationFailed();
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            onRegistrationSuccess();
                        }
                    });


        } else {
            Toast.makeText(getBaseContext(), "- Registration Failed!! Try Again!!", Toast.LENGTH_LONG).show();
        }

    }


    public void onRegistrationSuccess() {
        progressDialog.setVisibility(View.GONE);

        registerBtn.setEnabled(true);
        setResult(RESULT_OK, null);


        openDialog("Submitted Successfully ", "Your request to register have been submitted successfully. Our admins will review whether your transaction id is valid as soon as possible and replay with a text message. \n" +
                "This will take 1-30 minutes \n" +
                "\n" +
                "የምዝገባ ጥያቄዎት በተሳካ ሁኔታ ተላልፏል። የመተግበሪያው አስተዳዳሪዎች የሚስጥር ቁጥሮ(Txn. Id) ትክክለኛ መሆኑን አረጋግጠው የማሳወቂያ መልእክት ያደርሶታል። \n" +
                "ይህ ሂደት ከ 1-30 ደቂቃ የሚፈጅ ይሆናል።\n", true);

    }

    public void openDialog(String title, String msg, final boolean status) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        if (status) {
                            finish();
                        }

                    }
                }).create().show();
    }

    public void onRegistrationFailed() {
        progressDialog.setVisibility(View.GONE);

        openDialog("Registration Failed!!", "Please Try Again!!", false);
        registerBtn.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;


        _fullName = fullName.getText().toString();
        _mobileNumber = mobileNumber.getText().toString();
        _password = password.getText().toString();
        _confirmPassword = confirmPassword.getText().toString();
        _txRefNum = txRefNum.getText().toString();
        _stream = stream.getSelectedItem().toString();
        //_school = school.getSelectedItem().toString();
        _school = school.getText().toString();
        _bank = bank.getSelectedItem().toString();

        if (_school.isEmpty() || _school.length() < 3) {
            school.setError("at least 3 characters");
            valid = false;
        } else {
            school.setError(null);
        }


        if (_fullName.isEmpty() || _fullName.length() < 3) {
            fullName.setError("at least 3 characters");
            valid = false;
        } else {
            fullName.setError(null);
        }

        if (role.equals("")){
            int lastChildPos=teacherAndStudentRadioGroup.getChildCount()-1;
            ((RadioButton)teacherAndStudentRadioGroup.getChildAt(lastChildPos-1)).setError("Select your role type");
            ((RadioButton)teacherAndStudentRadioGroup.getChildAt(lastChildPos)).setError("Select your role type");
            valid = false;
        }

        if (_mobileNumber.isEmpty() || _mobileNumber.length() < 9) {
            mobileNumber.setError("at least 9 characters");
            valid = false;
        } else {
            mobileNumber.setError(null);
        }


        if (_password.isEmpty() || _password.length() < 4) {
            password.setError("password must be at least 4 characters");
            valid = false;
        } else {
            password.setError(null);
        }

        if (_password.compareTo(_confirmPassword) != 0) {
            confirmPassword.setError("Password don't much");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }


}
