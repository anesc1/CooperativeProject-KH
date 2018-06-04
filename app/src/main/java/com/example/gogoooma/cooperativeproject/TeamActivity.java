package com.example.gogoooma.cooperativeproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TeamActivity extends AppCompatActivity {
    ListView listView;
    TeamAdapter adapter;
    CallData callData3 = new CallData("project");
    CallData call = new CallData("place");
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (call.arr.size() == 0);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        setPlace();
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

    public void setPlace(){
        for(int i=0; i<GlobalVariable.g_team.size(); i++){
            for(int j=0; j<call.arr.size(); j+=10){
                if(GlobalVariable.g_team.get(i).getTeamNum() == Integer.parseInt(call.arr.get(j))){
                    for(int k=0; k<7; k++) {
                        String item = call.arr.get(j+k);
                        list.add(item);
                    }
                    Toast.makeText(this, call.arr.get(j), Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter = new TeamAdapter(this, R.layout.row, GlobalVariable.g_team, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("종료")
                .setMessage("종료하시겠습니까?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void init() {
        listView = (ListView) findViewById(R.id.listView);
        adapter = new TeamAdapter(this, R.layout.row, GlobalVariable.g_team, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GlobalVariable.g_nowTeam = GlobalVariable.g_team.get(position);

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        while (callData3.flag) ;
                    }
                };
                thread.start();
                try {
                    thread.join();
                } catch (Exception e) {
                }

                //같은 teamNum에 대한 project할당
                GlobalVariable.g_project.clear();
                for (int i = 0; i < callData3.arr.size(); i += 4) {
                    String projectName = callData3.arr.get(i).trim();
                    Integer projectNum = Integer.parseInt(callData3.arr.get(i + 1).trim());
                    String agenda = callData3.arr.get(i + 3).trim();
                    Integer teamNum = Integer.parseInt(callData3.arr.get(i + 2).trim());

                    if (teamNum.equals(GlobalVariable.g_nowTeam.getTeamNum())) {
                        GlobalVariable.g_project.add(new Project(projectName, projectNum, agenda, teamNum));
                    }
                }
                for (int i = 0; i < GlobalVariable.g_project.size(); i++) {
                    Toast.makeText(view.getContext(), GlobalVariable.g_project.get(i).getProjectName(), Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
