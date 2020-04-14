package com.students.preparation.matric.exam.modules.Home.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.students.preparation.matric.exam.R;

public class ContactActivity extends AppCompatActivity {
    Button tg,email,phone,tgGroup,tgChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_contact);

        tgChannel = findViewById(R.id.join_tg_channel);
        tgGroup = findViewById(R.id.join_tg_group);
        tg = findViewById(R.id.contact_tg);
        email = findViewById(R.id.contact_email);
        phone = findViewById(R.id.contact_phone);


        tgGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=ethiobraves"));
                startActivity(intent);
            }
        });

        tgChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=ethiobravez"));
                startActivity(intent);
            }
        });

        tg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=entpreadmin"));
                startActivity(intent);
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_VIEW);
                callIntent.setData(Uri.parse("tel:0968001105"));
                startActivity(callIntent);
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "yeabsramtekabe@gmail.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                    //intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                    startActivity(intent);
                }catch(ActivityNotFoundException e){
                    //TODO smth
                }
            }
        });

    }
}
