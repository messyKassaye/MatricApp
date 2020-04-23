package com.students.preparation.matric.students.modules.Students.share;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.students.preparation.matric.students.R;

public class ShareFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_share, container, false);

        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String sAux = "\nEntrance Preparation\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, getString(R.string.menu_share)));
        } catch (Exception e) {
            //e.toString();
        }

        return root;
    }
}