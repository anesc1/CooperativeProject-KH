package com.example.gogoooma.cooperativeproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class AddProject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        Intent i = getIntent();
        int projectNum = i.getIntExtra("projectNum",-1);
        Toast.makeText(getApplicationContext(), String.valueOf(projectNum),Toast.LENGTH_SHORT).show();
    }
}
