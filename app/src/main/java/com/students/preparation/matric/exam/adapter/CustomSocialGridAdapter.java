package com.students.preparation.matric.exam.adapter;

/**
 * Created by Alphabits Technology PLC
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.students.preparation.matric.exam.R;


public class CustomSocialGridAdapter extends BaseAdapter {
    private Context mContext;


    public CustomSocialGridAdapter(Context c) {//,String[] web,int[] Imageid
        mContext = c;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 7;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // if (convertView == null) {

        grid = new View(mContext);
        grid = inflater.inflate(R.layout.grid_single, null);

        TextView subjectName = (TextView) grid.findViewById(R.id.grid_subject);
        // = R.drawable.ic_launcher_round;
        ImageView subjectIcon = (ImageView) grid.findViewById(R.id.grid_subject_icon);


        if (position == 0) {
            subjectIcon.setImageResource(R.drawable.math);
            subjectName.setText("Math");
        } else if (position == 1) {

            subjectIcon.setImageResource(R.drawable.english);
            subjectName.setText("English");

        } else if (position == 2) {

            subjectIcon.setImageResource(R.drawable.economics);
            subjectName.setText("Economics");

        } else if (position == 3) {

            subjectIcon.setImageResource(R.drawable.history);
            subjectName.setText("History");

        } else if (position == 4) {

            subjectIcon.setImageResource(R.drawable.apptitude);
            subjectName.setText("Aptitude");

        } else if (position == 5) {

            subjectIcon.setImageResource(R.drawable.civics);
            subjectName.setText("Civic");

        } else if (position == 6) {

            subjectIcon.setImageResource(R.drawable.geography);
            subjectName.setText("Geography");

        }

        return grid;
    }
}
