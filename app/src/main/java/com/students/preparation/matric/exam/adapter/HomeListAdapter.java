package com.students.preparation.matric.exam.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeListAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    public HomeListAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        //if (convertView != null) return convertView;

        View vi = inflater.inflate(R.layout.home_list_row, null);

        TextView title = vi.findViewById(R.id.title); // title
        TextView artist = vi.findViewById(R.id.artist); // artist name
        ImageView thumb_image = vi.findViewById(R.id.list_image); // thumb image

        HashMap<String, String> listdata = new HashMap<String, String>();
        listdata = data.get(position);

        // Setting all values in listview
        title.setText(listdata.get(Constants.HOME_KEY_TITLE));
        artist.setText(listdata.get(Constants.HOME_KEY_SUBTITLE));

        //Setting an image
        String uri = "drawable/" + listdata.get(Constants.HOME_KEY_THUMB_ICON);
        int imageResource = vi.getContext().getApplicationContext().getResources().getIdentifier(uri, null, vi.getContext().getApplicationContext().getPackageName());
        Drawable image = vi.getContext().getResources().getDrawable(imageResource);
        thumb_image.setImageDrawable(image);


        return vi;
    }
}