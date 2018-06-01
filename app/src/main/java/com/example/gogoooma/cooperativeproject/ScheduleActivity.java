package com.example.gogoooma.cooperativeproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Resource;
import com.anychart.anychart.TimeTrackingMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {
    CallData callData = new CallData("project");
    List<Activity> activityList;
    List<String >dateList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        //ㄴㅇㄴ
        AnyChartView anyChartView = findViewById(R.id.resource);
        activityList=new ArrayList<>();
        dateList=new ArrayList<>();
        Thread thread = new Thread(){
            @Override
            public void run() {
                while(callData.flag);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch(Exception e) {
        }

        Toast.makeText(getApplicationContext(),callData.arr.get(1),Toast.LENGTH_SHORT).show();
        for (int i =0;i<callData.arr.size();i+=4){

            if (Integer.parseInt(callData.arr.get(i+2))==GlobalVariable.g_nowTeam.getTeamNum()){

                String date[]=callData.arr.get(i+3).split("/");
                dateList.add(date[0]);
                dateList.add(date[1]);
                activityList.add(new Activity(callData.arr.get(i),
                        new Interval[]{
                                new Interval(date[0],date[1])
                        },"#62BEC1"));
            }

        }
        Collections.sort(dateList);
        Activity[] activities= new Activity[activityList.size()];
        for (int i =0;i<activityList.size();i++){
            activities[i]=activityList.get(i);
        }
        Resource resource = AnyChart.resource();


        resource.setZoomLevel(1d)
                .setTimeTrackingMode(TimeTrackingMode.ACTIVITY_PER_CHART)
                .setCurrentStartDate(setCurrentState(dateList.get(0)));

        List<DataEntry> data = new ArrayList<>();

        data.add(new ResourceDataEntry(
                GlobalVariable.g_nowTeam.getTeamName(),
               activities)
                   );
        resource.setData(data);

        anyChartView.setChart(resource);
    }



    private class ResourceDataEntry extends DataEntry {
        ResourceDataEntry(String name, Activity[] activities) {
            setValue("name", name);
            setValue("activities", activities);
        }
    }

    private class Activity extends DataEntry {
        Activity(String name, Interval[] intervals, String fill) {
            setValue("name", name);
            setValue("intervals", intervals);
            setValue("fill", fill);
        }
    }

    private class Interval extends DataEntry {
        Interval(String start, String end) {
            setValue("start", start);
            setValue("end", end);
        }
    }
    public String setCurrentState(String cur){
        Date date = new Date();

        System.out.println(date);

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        cur = sdformat.format(cal.getTime());
    return cur;
    }
}
