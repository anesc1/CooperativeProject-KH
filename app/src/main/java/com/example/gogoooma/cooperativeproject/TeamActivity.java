package com.example.gogoooma.cooperativeproject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TeamActivity extends Fragment {
    View v;
    ListView listView;
    List<String> teamNameList;
    TeamAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_team, container, false);
        init();

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTeam = new Intent(v.getContext(), AddTeam.class);
                startActivity(addTeam);
            }
        });
        return v;
    }

    public void init(){
        listView = (ListView) v.findViewById(R.id.listView);
        adapter = new TeamAdapter(v.getContext(), R.layout.row, GlobalVariable.g_team);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(v.getContext(), ProjectActivity.class);
                intent.putExtra("team", position);
                startActivity(intent);
            }
        });
    }
}