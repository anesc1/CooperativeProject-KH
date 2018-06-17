package com.example.gogoooma.cooperativeproject;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends ArrayAdapter<Project> {
    View v;
    Context context;
    List<Project> list;
    Project now;
    ArrayList<String> checking;
    public ProjectAdapter(@NonNull Context context, int resource, @NonNull List<Project> objects, ArrayList<String> checking) {
        super(context, resource, objects);
        this.context = context;
        list = objects;
        this.checking = checking;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        v = inflater.inflate(R.layout.team_project, null);
        now = list.get(position);

        TextView name = (TextView) v.findViewById(R.id.projectName);
        TextView phone = (TextView) v.findViewById(R.id.projectAgenda);
        if(checking.get(position).equals("f")) {
            name.setTextColor(Color.RED);
            phone.setTextColor(Color.RED);
        }
        name.setText(now.getProjectName());
        phone.setText(now.getAgenda());

        return v;
    }
}
