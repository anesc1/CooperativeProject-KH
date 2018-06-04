package com.example.gogoooma.cooperativeproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AddProject extends AppCompatActivity {
    EditText projectName1;
    EditText projectName2;
    EditText projectName3;
    EditText projectName4;
    EditText projectName5;
    EditText projectName6;
    EditText projectName7;
    Integer projectNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        Intent i = getIntent();
        projectNum = i.getIntExtra("projectNum",-1);

        projectName1 = (EditText)findViewById(R.id.projectName1);
        projectName2 = (EditText)findViewById(R.id.projectName2);
        projectName3 = (EditText)findViewById(R.id.projectName3);
        projectName4 = (EditText)findViewById(R.id.projectName4);
        projectName5 = (EditText)findViewById(R.id.projectName5);
        projectName6 = (EditText)findViewById(R.id.projectName6);
        projectName7 = (EditText)findViewById(R.id.projectName7);

        if(projectNum==3)
        {
            projectName4.setVisibility(View.INVISIBLE);
            projectName5.setVisibility(View.INVISIBLE);
            projectName6.setVisibility(View.INVISIBLE);
            projectName7.setVisibility(View.INVISIBLE);
        }else if(projectNum==4)
        {
            projectName5.setVisibility(View.INVISIBLE);
            projectName6.setVisibility(View.INVISIBLE);
            projectName7.setVisibility(View.INVISIBLE);
        }else if(projectNum==5)
        {
            projectName6.setVisibility(View.INVISIBLE);
            projectName7.setVisibility(View.INVISIBLE);
        }else if(projectNum==6)
        {
            projectName7.setVisibility(View.INVISIBLE);
        }
    }



    public void Save(View view) {
        String prjName1 = projectName1.getText().toString();
        String prjName2 = projectName2.getText().toString();
        String prjName3 = projectName3.getText().toString();
        String prjName4 = projectName4.getText().toString();
        String prjName5 = projectName5.getText().toString();
        String prjName6 = projectName6.getText().toString();
        String prjName7 = projectName7.getText().toString();


        Intent intent = new Intent(AddProject.this, AddAgenda.class);
        intent.putExtra("projectNum",projectNum);
        intent.putExtra("projectName1",prjName1);
        intent.putExtra("projectName2",prjName2);
        intent.putExtra("projectName3",prjName3);
        intent.putExtra("projectName4",prjName4);
        intent.putExtra("projectName5",prjName5);
        intent.putExtra("projectName6",prjName6);
        intent.putExtra("projectName7",prjName6);
        startActivityForResult(intent,123);
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

