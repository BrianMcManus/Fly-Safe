package com.example.flyingsitevalidator3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by brian on 02/08/2017.
 */

public class ModelListAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Model> models = new ArrayList();

    public ModelListAdapter(Context context, ArrayList<Model> nModels) {
        mContext = context;
        this.models = nModels;
        inflater = LayoutInflater.from(mContext);
    }

    public class ViewHolder {
        TextView name;
    }

    public void add(Model mod)
    {
        models.add(mod);
        notifyDataSetChanged();
    }

    public void remove(String modName)
    {
        for(int i = 0; i<models.size(); i++)
        {
            if(modName.equals(models.get(i).getName()))
            {
                models.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Model getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, final ViewGroup parent) {


        final ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.model_list_item, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.model);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();

        }

        // Set the results into TextViews
        holder.name.setText(models.get(position).getName());


        view.setOnLongClickListener(new View.OnLongClickListener()
        {


            @Override
            public boolean onLongClick(View v) {
                parent.showContextMenuForChild(v);
                return false;
            }
        });

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String selectedFromList = models.get(position).getName();


                    //Check the users chosen club against the list of clubs and send the relevant information in the intent
                    for (int i = 0; i < models.size(); i++) {

                        if (models.get(i).getName().equals(selectedFromList)) {

                            Model m = new Model();
                            m.setName(models.get(i).getName());
                            m.setWidth(models.get(i).getWidth());
                            m.setLength(models.get(i).getLength());
                            m.setFuelType(models.get(i).getFuelType());
                            m.setModelType(models.get(i).getModelType());
                            m.setRegistrationId(models.get(i).getRegistrationId());


                            //Place the model in a bundle
                            Bundle args = new Bundle();
                            args.putSerializable("Model", m);

                            //Create intent and place bundle with model info into it, then start the activity
                            Intent intent = new Intent(mContext, ModelInfo.class);
                            intent.putExtras(args);
                            mContext.startActivity(intent);

                        }
                    }

            }
        });

        return view;
    }


}

