package com.example.gogoooma.cooperativeproject;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TimeIntegrateActivity extends Fragment {
    CallData callData = new CallData("timetable");
    CallData callData2 = new CallData("place");
    View v;

    String weekday;
    int startHour;
    int startMin;
    int endHour;
    int endMin;

    TextView monday[] = new TextView[12];
    TextView tuesday[] = new TextView[12];
    TextView wednesday[] = new TextView[12];
    TextView thursday[] = new TextView[12];
    TextView friday[] = new TextView[12];
    TextView saturday[] = new TextView[12];
    TextView sunday[] = new TextView[12];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (callData.arr.size() == 0 || callData.arr.size() % 7 != 0) ;
                while (callData2.arr.size() == 0 || callData2.arr.size() % 10 != 0) ;
            }
        };

        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
        }
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.activity_time_integrate, container, false);
        init();
        init2();

        return v;
    }


    public void init() {
        createArray();

        for (int j = 0; j < callData.arr.size(); j += 7) {
            for (int k = 0; k < GlobalVariable.g_nowTeam.getMembers().size(); k++) {
                if (callData.arr.get(j).equals(GlobalVariable.g_nowTeam.getMembers().get(k).getPhoneNum())) {

                    // 각 줄의 요일, 시작시간, 종료시간을 받음
                    weekday = callData.arr.get(2 + j).toString();
                    startHour = Integer.parseInt(callData.arr.get(3 + j));
                    startMin = Integer.parseInt(callData.arr.get(4 + j));
                    endHour = Integer.parseInt(callData.arr.get(5 + j));
                    endMin = Integer.parseInt(callData.arr.get(6 + j));

                    if (startHour > 12) startHour -= 12;
                    if (endHour > 12) endHour -= 12;
                    int start = findIndex(startHour);
                    int end = findIndex(endHour);

                    switch (weekday) {
                        case "monday":
                            for (int i = start; i <= end; i++) {
                                monday[i].setBackgroundColor(Color.rgb(153, 102, 204));
                            }
                            break;
                        case "tuesday":
                            for (int i = start; i <= end; i++) {
                                tuesday[i].setBackgroundColor(Color.rgb(195, 205, 230));
                            }
                            break;
                        case "wednesday":
                            for (int i = start; i <= end; i++) {
                                wednesday[i].setBackgroundColor(Color.rgb(153, 102, 204));
                            }
                            break;
                        case "thursday":
                            for (int i = start; i <= end; i++) {
                                thursday[i].setBackgroundColor(Color.rgb(195, 205, 230));
                            }
                            break;
                        case "friday":
                            for (int i = start; i <= end; i++) {
                                friday[i].setBackgroundColor(Color.rgb(153, 102, 204));
                            }
                            break;
                        case "saturday":
                            for (int i = start; i <= end; i++) {
                                saturday[i].setBackgroundColor(Color.rgb(195, 205, 230));
                            }
                            break;
                        case "sunday":
                            for (int i = start; i <= end; i++) {
                                sunday[i].setBackgroundColor(Color.rgb(153, 102, 204));
                            }
                            break;
                    }
                }
            }
        }
    }

    public void init2() {
        // createArray();

        for (int j = 0; j < callData2.arr.size(); j += 10) {
            if (callData2.arr.get(j).equals(GlobalVariable.g_nowTeam.getTeamNum() + "")) {

                // 각 줄의 요일, 시작시간, 종료시간을 받음
                weekday = callData2.arr.get(2 + j).toString();
                startHour = Integer.parseInt(callData2.arr.get(3 + j));
                startMin = Integer.parseInt(callData2.arr.get(4 + j));
                endHour = Integer.parseInt(callData2.arr.get(5 + j));
                endMin = Integer.parseInt(callData2.arr.get(6 + j));

                if (startHour > 12) startHour -= 12;
                if (endHour > 12) endHour -= 12;
                int start = findIndex(startHour);
                int end = findIndex(endHour);
                String placeStr = callData2.arr.get(j + 1);


                switch (weekday) {
                    case "월요일":
                        for (int i = start; i <= end; i++) {
                            monday[i].setBackgroundColor(Color.rgb(0, 0, 255));
                            if (placeStr.length() > 2 * (i - start)) {
                                int last = 2;
                                if (placeStr.length() < 2 * (i - start) + 2)
                                    last = 1;
                                monday[i].setText(placeStr.substring((i - start) * 2, (i - start) * 2 + last));
                            }
                        }
                        break;
                    case "화요일":
                        for (int i = start; i <= end; i++) {
                            tuesday[i].setBackgroundColor(Color.rgb(0, 0, 255));
                            if (placeStr.length() > 2 * (i - start)) {
                                int last = 2;
                                if (placeStr.length() < 2 * (i - start) + 2)
                                    last = 1;
                                tuesday[i].setText(placeStr.substring((i - start) * 2, (i - start) * 2 + last));
                            }
                        }
                        break;
                    case "수요일":
                        for (int i = start; i <= end; i++) {
                            wednesday[i].setBackgroundColor(Color.rgb(0, 0, 255));
                            if (placeStr.length() > 2 * (i - start)) {
                                int last = 2;
                                if (placeStr.length() < 2 * (i - start) + 2)
                                    last = 1;
                                wednesday[i].setText(placeStr.substring((i - start) * 2, (i - start) * 2 + last));
                            }
                        }
                        break;
                    case "목요일":
                        for (int i = start; i <= end; i++) {
                            thursday[i].setBackgroundColor(Color.rgb(0, 0, 255));
                            if (placeStr.length() > 2 * (i - start)) {
                                int last = 2;
                                if (placeStr.length() < 2 * (i - start) + 2)
                                    last = 1;
                                thursday[i].setText(placeStr.substring((i - start) * 2, (i - start) * 2 + last));
                            }
                        }
                        break;
                    case "금요일":
                        for (int i = start; i <= end; i++) {
                            friday[i].setBackgroundColor(Color.rgb(0, 0, 255));
                            if (placeStr.length() > 2 * (i - start)) {
                                int last = 2;
                                if (placeStr.length() < 2 * (i - start) + 2)
                                    last = 1;
                                friday[i].setText(placeStr.substring((i - start) * 2, (i - start) * 2 + last));
                            }
                        }
                        break;
                    case "토요일":
                        for (int i = start; i <= end; i++) {
                            saturday[i].setBackgroundColor(Color.rgb(0, 0, 255));
                            if (placeStr.length() > 2 * (i - start)) {
                                int last = 2;
                                if (placeStr.length() < 2 * (i - start) + 2)
                                    last = 1;
                                saturday[i].setText(placeStr.substring((i - start) * 2, (i - start) * 2 + last));
                            }
                        }
                        break;
                    case "일요일":
                        for (int i = start; i <= end; i++) {
                            sunday[i].setBackgroundColor(Color.rgb(0, 0, 255));
                            if (placeStr.length() > 2 * (i - start)) {
                                int last = 2;
                                if (placeStr.length() < 2 * (i - start) + 2)
                                    last = 1;
                                sunday[i].setText(placeStr.substring((i - start) * 2, (i - start) * 2 + last));
                            }
                        }
                        break;
                }
            }
        }
    }

    public int findIndex(int time) {
        int ind = 0;

        switch (time) {
            case 9:
                ind = 0;
                break;
            case 10:
                ind = 1;
                break;
            case 11:
                ind = 2;
                break;
            case 12:
                ind = 3;
                break;
            case 1:
                ind = 4;
                break;
            case 2:
                ind = 5;
                break;
            case 3:
                ind = 6;
                break;
            case 4:
                ind = 7;
                break;
            case 5:
                ind = 8;
                break;
            case 6:
                ind = 9;
                break;
            case 7:
                ind = 10;
                break;
            case 8:
                ind = 11;
                break;
        }
        return ind;
    }

    public void createArray() {
        monday[0] = (TextView) v.findViewById(R.id.mmonday9);
        monday[1] = (TextView) v.findViewById(R.id.mmonday10);
        monday[2] = (TextView) v.findViewById(R.id.mmonday11);
        monday[3] = (TextView) v.findViewById(R.id.mmonday12);
        monday[4] = (TextView) v.findViewById(R.id.mmonday1);
        monday[5] = (TextView) v.findViewById(R.id.mmonday2);
        monday[6] = (TextView) v.findViewById(R.id.mmonday3);
        monday[7] = (TextView) v.findViewById(R.id.mmonday4);
        monday[8] = (TextView) v.findViewById(R.id.mmonday5);
        monday[9] = (TextView) v.findViewById(R.id.mmonday6);
        monday[10] = (TextView) v.findViewById(R.id.mmonday7);
        monday[11] = (TextView) v.findViewById(R.id.mmonday8);

        tuesday[0] = (TextView) v.findViewById(R.id.ttuesday9);
        tuesday[1] = (TextView) v.findViewById(R.id.ttuesday10);
        tuesday[2] = (TextView) v.findViewById(R.id.ttuesday11);
        tuesday[3] = (TextView) v.findViewById(R.id.ttuesday12);
        tuesday[4] = (TextView) v.findViewById(R.id.ttuesday1);
        tuesday[5] = (TextView) v.findViewById(R.id.ttuesday2);
        tuesday[6] = (TextView) v.findViewById(R.id.ttuesday3);
        tuesday[7] = (TextView) v.findViewById(R.id.ttuesday4);
        tuesday[8] = (TextView) v.findViewById(R.id.ttuesday5);
        tuesday[9] = (TextView) v.findViewById(R.id.ttuesday6);
        tuesday[10] = (TextView) v.findViewById(R.id.ttuesday7);
        tuesday[11] = (TextView) v.findViewById(R.id.ttuesday8);

        wednesday[0] = (TextView) v.findViewById(R.id.wwednesday9);
        wednesday[1] = (TextView) v.findViewById(R.id.wwednesday10);
        wednesday[2] = (TextView) v.findViewById(R.id.wwednesday11);
        wednesday[3] = (TextView) v.findViewById(R.id.wwednesday12);
        wednesday[4] = (TextView) v.findViewById(R.id.wwednesday1);
        wednesday[5] = (TextView) v.findViewById(R.id.wwednesday2);
        wednesday[6] = (TextView) v.findViewById(R.id.wwednesday3);
        wednesday[7] = (TextView) v.findViewById(R.id.wwednesday4);
        wednesday[8] = (TextView) v.findViewById(R.id.wwednesday5);
        wednesday[9] = (TextView) v.findViewById(R.id.wwednesday6);
        wednesday[10] = (TextView) v.findViewById(R.id.wwednesday7);
        wednesday[11] = (TextView) v.findViewById(R.id.wwednesday8);

        thursday[0] = (TextView) v.findViewById(R.id.tthursday9);
        thursday[1] = (TextView) v.findViewById(R.id.tthursday10);
        thursday[2] = (TextView) v.findViewById(R.id.tthursday11);
        thursday[3] = (TextView) v.findViewById(R.id.tthursday12);
        thursday[4] = (TextView) v.findViewById(R.id.tthursday1);
        thursday[5] = (TextView) v.findViewById(R.id.tthursday2);
        thursday[6] = (TextView) v.findViewById(R.id.tthursday3);
        thursday[7] = (TextView) v.findViewById(R.id.tthursday4);
        thursday[8] = (TextView) v.findViewById(R.id.tthursday5);
        thursday[9] = (TextView) v.findViewById(R.id.tthursday6);
        thursday[10] = (TextView) v.findViewById(R.id.tthursday7);
        thursday[11] = (TextView) v.findViewById(R.id.tthursday8);

        friday[0] = (TextView) v.findViewById(R.id.ffriday9);
        friday[1] = (TextView) v.findViewById(R.id.ffriday10);
        friday[2] = (TextView) v.findViewById(R.id.ffriday11);
        friday[3] = (TextView) v.findViewById(R.id.ffriday12);
        friday[4] = (TextView) v.findViewById(R.id.ffriday1);
        friday[5] = (TextView) v.findViewById(R.id.ffriday2);
        friday[6] = (TextView) v.findViewById(R.id.ffriday3);
        friday[7] = (TextView) v.findViewById(R.id.ffriday4);
        friday[8] = (TextView) v.findViewById(R.id.ffriday5);
        friday[9] = (TextView) v.findViewById(R.id.ffriday6);
        friday[10] = (TextView) v.findViewById(R.id.ffriday7);
        friday[11] = (TextView) v.findViewById(R.id.ffriday8);

        saturday[0] = (TextView) v.findViewById(R.id.ssaturday9);
        saturday[1] = (TextView) v.findViewById(R.id.ssaturday10);
        saturday[2] = (TextView) v.findViewById(R.id.ssaturday11);
        saturday[3] = (TextView) v.findViewById(R.id.ssaturday12);
        saturday[4] = (TextView) v.findViewById(R.id.ssaturday1);
        saturday[5] = (TextView) v.findViewById(R.id.ssaturday2);
        saturday[6] = (TextView) v.findViewById(R.id.ssaturday3);
        saturday[7] = (TextView) v.findViewById(R.id.ssaturday4);
        saturday[8] = (TextView) v.findViewById(R.id.ssaturday5);
        saturday[9] = (TextView) v.findViewById(R.id.ssaturday6);
        saturday[10] = (TextView) v.findViewById(R.id.ssaturday7);
        saturday[11] = (TextView) v.findViewById(R.id.ssaturday8);

        sunday[0] = (TextView) v.findViewById(R.id.ssunday9);
        sunday[1] = (TextView) v.findViewById(R.id.ssunday10);
        sunday[2] = (TextView) v.findViewById(R.id.ssunday11);
        sunday[3] = (TextView) v.findViewById(R.id.ssunday12);
        sunday[4] = (TextView) v.findViewById(R.id.ssunday1);
        sunday[5] = (TextView) v.findViewById(R.id.ssunday2);
        sunday[6] = (TextView) v.findViewById(R.id.ssunday3);
        sunday[7] = (TextView) v.findViewById(R.id.ssunday4);
        sunday[8] = (TextView) v.findViewById(R.id.ssunday5);
        sunday[9] = (TextView) v.findViewById(R.id.ssunday6);
        sunday[10] = (TextView) v.findViewById(R.id.ssunday7);
        sunday[11] = (TextView) v.findViewById(R.id.ssunday8);
    }


}
