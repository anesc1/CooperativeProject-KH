package com.example.gogoooma.cooperativeproject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ThirdActivity extends Fragment {
    View v;
    Integer check=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_third, container, false);
//        프로젝트 정보가 없을 때, 리더일 때 생성
        if(check.equals(GlobalVariable.g_project.size()) && GlobalVariable.g_nowTeam.leader.equals(GlobalVariable.g_user))
        {
          Intent intent = new Intent(getActivity(),NoticeActivity.class);
          startActivity(intent);
            // 프로젝트 정보가 없을 때, 리더가 아닐 때
        }else if(check.equals(GlobalVariable.g_project.size()) && !(GlobalVariable.g_nowTeam.leader.equals(GlobalVariable.g_user)))
        {
            Intent intent = new Intent(getActivity(),NoticeActivity.class);
            startActivity(intent);
        }
        else //프로젝트 정보가 이미 있을 때 차트
        {
            Intent intent1 = new Intent(getActivity(),ScheduleActivity.class);
            startActivity(intent1);
        }
        return v;
    }


}
