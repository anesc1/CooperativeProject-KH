package com.example.gogoooma.cooperativeproject;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Availability;
import com.anychart.anychart.AvailabilityPeriod;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Resource;
import com.anychart.anychart.TimeTrackingMode;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        AnyChartView anyChartView = findViewById(R.id.resource);

        Resource resource = AnyChart.resource();

        resource.setZoomLevel(1d)
                .setTimeTrackingMode(TimeTrackingMode.ACTIVITY_PER_CHART)
                .setCurrentStartDate("2016-09-30");

        resource.getCalendar().setAvailabilities(new Availability[] {
                new Availability(AvailabilityPeriod.DAY, (Double) null, 10d, (Double) null, (Double) null, 18d, true),
                new Availability(AvailabilityPeriod.DAY, (Double) null, 14d, (Double) null, (Double) null, 15d, false),
                new Availability(AvailabilityPeriod.WEEK, (Double) null, (Double) null, 5d, (Double) null, 18d, false),
                new Availability(AvailabilityPeriod.WEEK, (Double) null, (Double) null, 6d, (Double) null, 18d, false)
        });

        List<DataEntry> data = new ArrayList<>();

        data.add(new ResourceDataEntry(
                "Romario",
                new Activity[]{
                        new Activity(
                                "Gantt timeline",
                                new Interval[]{
                                        new Interval("2016-10-01", "2016-10-11")
                                },
                                "#62BEC1"),
                        new Activity(
                                "Gantt Connectors events/removal + UI customization",
                                new Interval[]{
                                        new Interval("2016-10-01", "2016-10-04")
                                },
                                "#62BEC1"),
                        new Activity(
                                "Chart Facebook sharing",
                                new Interval[]{
                                        new Interval("2016-10-01", "2016-10-04")
                                },
                                "#62BEC1"),
                        new Activity(
                                "Chart animation problems",
                                new Interval[]{
                                        new Interval("2016-10-05", "2016-10-09")
                                },
                                "#62BEC1"),
                        new Activity(
                                "iPad touch problems",
                                new Interval[]{
                                        new Interval("2016-10-12", "2016-10-16"),
                                        new Interval("2016-10-17", "2016-10-21")
                                },
                                "#62BEC1"),
                        new Activity(
                                "Some improvements for chart labels",
                                new Interval[]{
                                        new Interval("2016-10-17", "2016-10-22"),
                                        new Interval("2016-10-22", "2016-10-26")
                                },
                                "#62BEC1")
                }));
        data.add(new ResourceDataEntry(
                "Antonio",
                new Activity[]{
                        new Activity(
                                "Gantt resource list",
                                new Interval[]{
                                        new Interval("2016-09-25", "2016-10-01")
                                },
                                "#EA526F"),
                        new Activity(
                                "Pareto Chart",
                                new Interval[]{
                                        new Interval("2016-09-25", "2016-10-05")
                                },
                                "#EA526F"),
                        new Activity(
                                "Chart bug fixes",
                                new Interval[]{
                                        new Interval("2016-10-08", "2016-10-25")
                                },
                                "#EA526F"),
                        new Activity(
                                "Chart legend",
                                new Interval[]{
                                        new Interval("2016-10-06", "2016-10-12")
                                },
                                "#EA526F")
                }));
        data.add(new ResourceDataEntry(
                "Alejandro",
                new Activity[]{
                        new Activity(
                                "Pie chart improvement",
                                new Interval[]{
                                        new Interval("2016-09-25", "2016-10-02")
                                },
                                "#8789C0"),
                        new Activity(
                                "Pie chart labels problems",
                                new Interval[]{
                                        new Interval("2016-10-05", "2016-11-01")
                                },
                                "#8789C0"),
                        new Activity(
                                "Stock chart minor bugs",
                                new Interval[]{
                                        new Interval("2016-10-01", "2016-10-10")
                                },
                                "#8789C0"),
                        new Activity(
                                "Chart minor bug fixes",
                                new Interval[]{
                                        new Interval("2016-10-20", "2016-11-05")
                                },
                                "#8789C0")
                }));
        data.add(new ResourceDataEntry(
                "Sergio",
                new Activity[]{
                        new Activity(
                                "Gantt logo",
                                new Interval[]{
                                        new Interval("2016-09-30", "2016-10-03")
                                },
                                "#E06D06"),
                        new Activity(
                                "Tooltip bug fix",
                                new Interval[]{
                                        new Interval("2016-10-04", "2016-10-10")
                                },
                                "#E06D06"),
                        new Activity(
                                "Chart label",
                                new Interval[]{
                                        new Interval("2016-10-11", "2016-10-15")
                                },
                                "#E06D06"),
                        new Activity(
                                "Map series labels improvement",
                                new Interval[]{
                                        new Interval("2016-10-16", "2016-11-03")
                                },
                                "#E06D06")
                }));

        resource.setData(data);

        anyChartView.setChart(resource);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

}