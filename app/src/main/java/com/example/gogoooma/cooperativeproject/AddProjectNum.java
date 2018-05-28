package com.example.gogoooma.cooperativeproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        if(projectNum>7 || projectNum<3)
        {
            editText.setText("3 에서 7사이의 값을 입력하세요");
        }else
        {
            Intent intent = new Intent(AddProjectNum.this, AddProject.class);
            intent.putExtra("projectNum",projectNum);
            startActivityForResult(intent,123);
        }

    }


    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 123) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
    }
}
