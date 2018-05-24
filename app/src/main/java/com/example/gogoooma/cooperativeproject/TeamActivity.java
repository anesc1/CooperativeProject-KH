package com.example.gogoooma.cooperativeproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TeamActivity extends AppCompatActivity {
    ListView listView;
    TeamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        init();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTeam = new Intent(getApplicationContext(), AddTeam.class);
                startActivityForResult(addTeam, 123);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            adapter = new TeamAdapter(this, R.layout.row, GlobalVariable.g_team);
            listView.setAdapter(adapter);
        }
    }

    public void init() {
        listView = (ListView) findViewById(R.id.listView);
        adapter = new TeamAdapter(this, R.layout.row, GlobalVariable.g_team);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("team", GlobalVariable.g_team.get(position));
                startActivity(intent);
            }
        });

    }
}
