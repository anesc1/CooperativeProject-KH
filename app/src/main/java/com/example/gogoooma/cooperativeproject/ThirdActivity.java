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
        //프로젝트 정보가 없을 때 생성
        if(check.equals(GlobalVariable.g_project.size()))
        {
            Intent intent = new Intent(getActivity(),AddProjectNum.class);
            startActivityForResult(intent,123);
        }
        else //프로젝트 정보가 이미 있을 때 차트
        {

        }
        return v;
    }

    // 모두 입력후 차트
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {

        }
    }
}
