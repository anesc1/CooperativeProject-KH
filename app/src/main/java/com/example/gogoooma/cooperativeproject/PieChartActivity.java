package com.example.gogoooma.cooperativeproject;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;

import java.util.ArrayList;

public class PieChartActivity extends SimpleFragment {
    CallData callData = new CallData("project");
    public static Fragment newInstance() {
        return new PieChartActivity();
    }
    ArrayList<String> proname;
    private PieChart mChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_pie_chart, container, false);


        mChart = v.findViewById(R.id.pieChart1);
        mChart.getDescription().setEnabled(false);

        //Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

//        mChart.setCenterTextTypeface(tf);
        mChart.setCenterText(generateCenterText());
        mChart.setCenterTextSize(10f);
//        mChart.setCenterTextTypeface(tf);

        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(45f);
        mChart.setTransparentCircleRadius(50f);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);

        mChart.setData(generatePieData());

        return v;
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString(GlobalVariable.g_nowTeam.getTeamName()+
                "\nQuarters 2018");
        s.setSpan(new RelativeSizeSpan(2f), 0, GlobalVariable.g_nowTeam.getTeamName().length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), GlobalVariable.g_nowTeam.getTeamName().length(), s.length(), 0);
        return s;
    }
}