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
import android.widget.ListView;

public class TeamActivity extends Fragment {
    View v;
    ListView listView;
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
                startActivityForResult(addTeam, 123);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123){
            adapter = new TeamAdapter(v.getContext(), R.layout.row, GlobalVariable.g_team);
            listView.setAdapter(adapter);
        }
    }

    public void init(){
        listView = (ListView) v.findViewById(R.id.listView);
        adapter = new TeamAdapter(v.getContext(), R.layout.row, GlobalVariable.g_team);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(v.getContext(), ProjectActivity.class);
                intent.putExtra("team", GlobalVariable.g_team.get(position));
                startActivity(intent);
            }
        });
    }
}