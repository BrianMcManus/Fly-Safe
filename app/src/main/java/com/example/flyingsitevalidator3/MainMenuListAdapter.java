package com.example.flyingsitevalidator3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian on 12/12/2016.
 */

public class MainMenuListAdapter extends BaseAdapter {

    private Context mContext;
    private static LayoutInflater inflater = null;
    private final String[] menuItems;
    private final int[] menuImages;

    //Constructor to initilize the icon titles and images and the context which the adapter will be used
    public MainMenuListAdapter(MainMenuActivity mainMenuActivity, String[] menuItems, int[] menuImages)
    {
        this.menuItems = menuItems;
        this.menuImages = menuImages;
        mContext = mainMenuActivity;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Get the number of titles in the list
    @Override
    public int getCount()
    {
        return menuItems.length;
    }

    //Default method returns position number
    @Override
    public Object getItem(int position)
    {
        return position;
    }

    //Default method returns position number
    @Override
    public long getItemId(int position)
    {
        return position;
    }


    //Get View method to initilize the adapter view, it cycles through the list items setting them in the list adapter
    @Override
    public View getView (final int position, View convertView, final ViewGroup parent)
    {
        final View rowView;

        //Set the view for the list, text and image views
        rowView = inflater.inflate(R.layout.activity_main_menu,null);
        TextView tv = (TextView) rowView.findViewById(R.id.textView1);
        final ImageView iv = (ImageView) rowView.findViewById(R.id.imageView1);

        //Set the alignment to center to keep images inline with each other
        tv.setTextAlignment(GridLayout.TEXT_ALIGNMENT_CENTER);

        //Set the text of the text view relevant to the position in the list
        tv.setText(menuItems[position]);

        //Set the image of the image view relevant to the position in the image list, is also set in a thread so as to speed up the loading process
        new Thread(new Runnable() {
            public void run() {

                rowView.post(new Runnable() {
                    public void run() {
                        iv.setImageResource(menuImages[position]);
                    }
                });
            }
        }).start();

        return rowView;
    }

}
