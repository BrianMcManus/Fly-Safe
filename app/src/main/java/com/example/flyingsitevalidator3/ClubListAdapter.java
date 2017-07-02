package com.example.flyingsitevalidator3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by brian on 30/06/2017.
 */

public class ClubListAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Club> clubs = new ArrayList();
    private ArrayList<Club> filteredClubs = new ArrayList();

    public ClubListAdapter(Context context, ArrayList<Club> nClubs) {
        mContext = context;
        this.clubs = nClubs;
        Club c = new Club();
        c.setShortName("All Sites");
        c.setName("All Sites");
        clubs.add(c);
        Collections.sort(clubs, new Club.NameComparator());

        inflater = LayoutInflater.from(mContext);
        this.filteredClubs = new ArrayList<Club>();
        this.filteredClubs.addAll(clubs);


    }

    public class ViewHolder {
        TextView name;
    }


    @Override
    public int getCount() {
        return clubs.size();
    }

    @Override
    public Club getItem(int position) {
        return clubs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {


        final ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.club_list_item, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.club);
            view.setTag(holder);
            holder.name.setText("All Sites");
        } else {
            holder = (ViewHolder) view.getTag();

        }

        // Set the results into TextViews
        holder.name.setText(clubs.get(position).getShortName());

       /* if(position<1)
        {
            holder.name.setText("All Sites");
        }
        else
        {
            // Set the results into TextViews
            holder.name.setText(clubs.get(position).getShortName());
        }*/



        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int temp = position + 1;
                String selectedFromList = clubs.get(position).getName();

                //check if the user chose the "all sites" option and if so send all the clubs info in the intent
                if (selectedFromList.equalsIgnoreCase("All Sites")) {

                    //Start the map activity and send the all club information in the intent
                    Intent intent = new Intent(mContext, ClubsAndSitesMap.class);
                    intent.putExtra("AllSites_dataProvider", clubs);
                    mContext.startActivity(intent);
                } else {
                    //Check the users chosen club against the list of clubs and send the relevant information in the intent
                    for (int i = 0; i < clubs.size(); i++) {

                        if (clubs.get(i).getName().equals(selectedFromList)) {

                            LatLng fromPosition = new LatLng(clubs.get(i).getLat(), clubs.get(i).getLon());
                            String name = clubs.get(i).getName();
                            String county = clubs.get(i).getCounty();
                            String url = clubs.get(i).getUrl();
                            Bundle args = new Bundle();
                            args.putParcelable("longLat_dataProvider", fromPosition);
                            Intent intent = new Intent(mContext, ClubsAndSitesMap.class);
                            intent.putExtra("name_dataProvider", name);
                            intent.putExtra(("address_dataProvider"), county);
                            intent.putExtra("url_dataProvider", url);
                            intent.putExtras(args);
                            intent.putExtra("ClubDataProvider", clubs);
                            mContext.startActivity(intent);

                        }
                    }
                }
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        clubs.clear();

        if (charText.length() == 0) {
            clubs.addAll(filteredClubs);
        }
        else
        {
            for (Club c : filteredClubs)
            {
                if(c.getCounty() != null)
                {
                    if (c.getCounty().toLowerCase(Locale.getDefault()).startsWith(charText))// Can also use "Contains"
                    {
                        clubs.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}
