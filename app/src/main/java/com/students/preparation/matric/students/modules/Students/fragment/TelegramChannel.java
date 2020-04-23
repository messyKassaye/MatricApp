package com.students.preparation.matric.students.modules.Students.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.students.preparation.matric.students.R;


public class TelegramChannel extends Fragment {


    private LinearLayout telegramLayout;
    private TextView opened;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_telegram_channel, container, false);
        telegramLayout = view.findViewById(R.id.telegramLayout);
        opened = view.findViewById(R.id.opened);
        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        intentMessageTelegram("Shared form Matric preparation");
                        telegramLayout.setVisibility(View.GONE);
                        opened.setVisibility(View.VISIBLE);
                    }
                },
                3000);
        return view;
    }

    public void intentMessageTelegram(String channel) {
        final String appName = "org.telegram.messenger";
        final String telegramPlus = "org.telegram.plus";
        final boolean isAppInstalled = isAppAvailable(getActivity().getApplicationContext(), appName);
        if (isAppInstalled) {
            try {
                Intent telegramIntent = new Intent(Intent.ACTION_VIEW);
                telegramIntent.setData(Uri.parse(appName+"/ethiobravez"));
                startActivity(telegramIntent);
            } catch (Exception e) {
                // show error message
            }
        } else {
            final boolean isPlus = isAppAvailable(getActivity().getApplicationContext(),telegramPlus);

            if (isPlus){
                try {
                    Intent telegramIntent = new Intent(Intent.ACTION_VIEW);
                    telegramIntent.setPackage(telegramPlus+"/ethiobravez");
                    startActivity(telegramIntent);
                } catch (Exception e) {
                    // show error message
                }
            }else {
                Toast.makeText(getActivity(), "There is no any telegram applications installed on this device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static boolean isAppAvailable(Context context, String appName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}