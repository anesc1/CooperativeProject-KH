package com.example.gogoooma.cooperativeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddProjectNum extends AppCompatActivity {
EditText editText;
Integer projectNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("팀 생성");
        setContentView(R.layout.activity_add_project_num);
        editText = (EditText)findViewById(R.id.edit_projectNum);
    }

    public void Save(View view) {
        projectNum = Integer.parseInt(editText.getText().toString());
        Intent intent = new Intent(AddProjectNum.this, AddProject.class);
        intent.putExtra("projectNum",projectNum);
        startActivity(intent);
    }
}
