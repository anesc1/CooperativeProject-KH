package com.example.gogoooma.cooperativeproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TempActivity extends AppCompatActivity {
    Integer check=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        if(check.equals(GlobalVariable.g_project.size()) && GlobalVariable.g_nowTeam.leader.equals(GlobalVariable.g_user))
        {
            Intent intent1 = new Intent(TempActivity.this,AddProjectNum.class);
            startActivityForResult(intent1, 11);
            // 프로젝트 정보가 없을 때, 리더가 아닐 때
        }else if(check.equals(GlobalVariable.g_project.size()) && !(GlobalVariable.g_nowTeam.leader.equals(GlobalVariable.g_user)))
        {
            Intent intent1 = new Intent(TempActivity.this,NoticeActivity.class);
            startActivityForResult(intent1, 11);
        }
        else //프로젝트 정보가 이미 있을 때 차트
        {
            Intent intent1 = new Intent(TempActivity.this,ScheduleActivity.class);
            startActivityForResult(intent1, 11);
        }
    }

    // 모두 입력후 차트
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 11){
            Intent intent1 = new Intent(TempActivity.this,MainActivity.class);
            startActivity(intent1);
        }
    }
}

