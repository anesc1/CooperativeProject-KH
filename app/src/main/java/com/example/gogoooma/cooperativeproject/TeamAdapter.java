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

public class TeamAdapter extends ArrayAdapter<Team> {
    View v;
    Context context;
    List<Team> list;
    Team now;
    List<String> place;

    public TeamAdapter(@NonNull Context context, int resource, @NonNull List<Team> objects
    , List<String> place) {
        super(context, resource, objects);
        this.context = context;
        list = objects;
        this.place = place;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        v = inflater.inflate(R.layout.row, null);
        now = list.get(position);

        TextView teamName = (TextView) v.findViewById(R.id.teamName);
        TextView teamMember = (TextView) v.findViewById(R.id.teamMember);
        teamName.setText(now.getTeamName());
        teamMember.setText(now.printMember());
        TextView teamPlace = (TextView) v.findViewById(R.id.textView8);
        for(int i=0; i<place.size(); i+=7){
            if(place.get(i).equals(now.getTeamNum()+"")){
                teamPlace.setVisibility(View.VISIBLE);
                String str = teamPlace.getText().toString() + "\n" + place.get(i+1) +" - "
                        + place.get(i+2) +" " +place.get(i+3) + ":" +
                        String.format("%02d", Integer.parseInt(place.get(i+4)))+" ~ "
                        + place.get(i+5) + ":" + String.format("%02d", Integer.parseInt(place.get(i+6)));
                teamPlace.setText(str);
            }
        }

        return v;
    }
}
