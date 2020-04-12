package com.students.preparation.matric.exam.adapter;

/**
 * Created by Alphabits Technology PLC
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.students.preparation.matric.exam.R;

public class CustomNaturalGridAdapter extends BaseAdapter {
    private Context mContext;


    public CustomNaturalGridAdapter(Context c) {//,String[] web,int[] Imageid
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

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid = null;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // if (convertView == null) {

             grid = new View(mContext);
             grid = inflater.inflate(R.layout.grid_single, null);

             TextView subjectName = (TextView) grid.findViewById(R.id.grid_subject);
             ImageView subjectIcon = (ImageView) grid.findViewById(R.id.grid_subject_icon);


             if (position == 0) {
                 subjectIcon.setImageResource(R.drawable.math);
                 subjectName.setText("Math");
             } else if (position == 1) {

                 subjectIcon.setImageResource(R.drawable.english);
                 subjectName.setText("English");

             } else if (position == 2) {

                 subjectIcon.setImageResource(R.drawable.physics);
                 subjectName.setText("Physics");

             } else if (position == 3) {

                 subjectIcon.setImageResource(R.drawable.biology);
                 subjectName.setText("Biology");

             } else if (position == 4) {

                 subjectIcon.setImageResource(R.drawable.chemistry);
                 subjectName.setText("Chemistry");

             } else if (position == 5) {

                 subjectIcon.setImageResource(R.drawable.apptitude);
                 subjectName.setText("Aptitude");

             } else if (position == 6) {

                 subjectIcon.setImageResource(R.drawable.civics);
                 subjectName.setText("Civic");

             }
             return grid;
         //}
        //return convertView;


    }
}
