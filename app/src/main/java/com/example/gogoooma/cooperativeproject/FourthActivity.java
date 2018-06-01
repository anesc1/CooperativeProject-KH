package com.example.gogoooma.cooperativeproject;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FourthActivity extends Fragment {
    View v;
    int arr[][] = new int[7][22];
    CallData tt = new CallData("timetable");
    CallData callData = new CallData("place");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (callData.flag) ;
                while (tt.flag);
            }
        };

        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
        }

        v = inflater.inflate(R.layout.activity_fourth, container, false);
        getTimetable();

        return v;
    }

    public void getTimetable() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 22; j++) {
                arr[i][j] = 0;
            }
        }

        for (int j = 0; j < tt.arr.size(); j += 7) {
            for (int k = 0; k < GlobalVariable.g_nowTeam.getMembers().size(); k++) {
                if (tt.arr.get(j).equals(GlobalVariable.g_nowTeam.getMembers().get(k).getPhoneNum())) {

                    // 각 줄의 요일, 시작시간, 종료시간을 받음
                    String weekday = tt.arr.get(2 + j).toString();
                    int startHour = Integer.parseInt(tt.arr.get(3 + j));
                    int startMin = Integer.parseInt(tt.arr.get(4 + j));
                    int endHour = Integer.parseInt(tt.arr.get(5 + j));
                    int endMin = Integer.parseInt(tt.arr.get(6 + j));
                    int day = 0, num1, num2;
                    switch (weekday) {
                        case "monday":
                            day = 0;
                            break;
                        case "tuesday":
                            day = 1;
                            break;
                        case "wednesday":
                            day = 2;
                            break;
                        case "thursday":
                            day = 3;
                            break;
                        case "friday":
                            day = 4;
                            break;
                        case "saturday":
                            day = 5;
                            break;
                        case "sunday":
                            day = 6;
                            break;
                    }
                    num1 = 2 * (startHour - 9);
                    if (startMin == 30)
                        num1++;
                    num2 = 2 * (endHour - 9);
                    if (startMin == 30)
                        num2++;
                    for (int i = num1; i < num2; i++)
                        arr[day][i] = 1;
                }
            }
        }
    }
}
