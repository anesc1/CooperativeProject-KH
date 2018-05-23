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

    public TeamAdapter(@NonNull Context context, int resource, @NonNull List<Team> objects) {
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
        TextView teamMember = (TextView) v.findViewById(R.id.teamMember);
        teamName.setText(now.getTeamName());
        teamMember.setText(now.printMember());
        return v;
    }
}
