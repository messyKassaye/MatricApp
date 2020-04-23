package com.students.preparation.matric.students.modules.Students.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.students.preparation.matric.students.R;

public class TelegramGroupFragment extends Fragment {

    private LinearLayout telegramLayout;
    private LinearLayout mainLayout;
    private Button natural,social;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_telegram_group, container, false);

        telegramLayout = view.findViewById(R.id.telegramLayout);
        mainLayout = view.findViewById(R.id.telegramGroupMainLayout);

        natural = view.findViewById(R.id.naturalTelegramGroup);
        social = view.findViewById(R.id.socialTelegramGroup);
        new Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        telegramLayout.setVisibility(View.GONE);
                        mainLayout.setVisibility(View.VISIBLE);
                    }
                },
                3000);

        natural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentMessageTelegram("ethiobraves");
            }
        });

        social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentMessageTelegram("ethiobravess");
            }
        });
        return view;
    }

   public void intentMessageTelegram(String group)
    {
        final String appName = "org.telegram.messenger";
        final String telegramPlaus = "org.telegram.plus";
        final boolean isAppInstalled = isAppAvailable(getActivity().getApplicationContext(), appName);
        if (isAppInstalled)
        {
            try {
                Intent telegramIntent = new Intent(Intent.ACTION_VIEW);
                telegramIntent.setPackage(appName+"/"+group);
                startActivity(telegramIntent);
            } catch (Exception e) {
                // show error message
            }
        }
        else
        {
            final boolean isPlus = isAppAvailable(getActivity().getApplicationContext(),telegramPlaus);

            if (isPlus){
                try {
                    Intent telegramIntent = new Intent(Intent.ACTION_VIEW);
                    telegramIntent.setPackage(telegramPlaus+"/"+group);
                    startActivity(telegramIntent);
                } catch (Exception e) {
                    // show error message
                }
            }else {
                Toast.makeText(getActivity(), "There is no any telegram applications installed on this device", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public static boolean isAppAvailable(Context context, String appName)
    {
        PackageManager pm = context.getPackageManager();
        try
        {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }

}
