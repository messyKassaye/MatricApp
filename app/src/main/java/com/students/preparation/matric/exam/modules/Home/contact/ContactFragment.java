package com.students.preparation.matric.exam.modules.Home.contact;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.students.preparation.matric.exam.R;

public class ContactFragment extends Fragment {


    Button tg,email,phone,tgGroup,tgChannel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contact, container, false);

        tgChannel = root.findViewById(R.id.join_tg_channel);
        tgGroup = root.findViewById(R.id.join_tg_group);
        tg = root.findViewById(R.id.contact_tg);
        email = root.findViewById(R.id.contact_email);
        phone = root.findViewById(R.id.contact_phone);


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
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/ethiobravez"));
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
        return root;
    }
}