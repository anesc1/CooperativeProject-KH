package com.example.gogoooma.cooperativeproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PlaceAdapter extends ArrayAdapter<Place> {
    View v;
    Context context;
    List<Place> list;
    Place now;

    public PlaceAdapter(@NonNull Context context, int resource, @NonNull List<Place> objects) {
        super(context, resource, objects);
        this.context = context;
        list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        v = inflater.inflate(R.layout.row, null);
        now = list.get(position);
        TextView teamName = (TextView) v.findViewById(R.id.teamName);
        TextView teamPlace = (TextView) v.findViewById(R.id.teamPlace);
        teamName.setText(now.getPlace());
        String str = now.getPlace() +" - "
                + now.getDay() +" " +now.getStartHour() + ":" +
                String.format("%02d", now.getStartMin())+" ~ "
                + now.getEndHour() + ":" + String.format("%02d", now.getEndMin());
        teamPlace.setText(str);

        return v;
    }
}
