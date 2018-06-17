package com.example.gogoooma.cooperativeproject;

        import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.FileUtils;

import java.util.ArrayList;
import java.util.Calendar;

public abstract class SimpleFragment extends Fragment {
    CallData callData = new CallData("project");
    private Typeface tf;
    ArrayList<String> proname = new ArrayList<>();
    ArrayList<String> proagenda = new ArrayList<>();
    ArrayList<Integer> subday = new ArrayList<>();
    public SimpleFragment() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (callData.arr.size() == 0 || callData.arr.size()%4!=0);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
        }
}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected BarData generateBarData() {

        proname = new ArrayList<>();
         int count=0;
        for (int i =0;i<callData.arr.size();i+=4){

            if (Integer.parseInt(callData.arr.get(i+2))==GlobalVariable.g_nowTeam.getTeamNum()){
                proname.add(callData.arr.get(i));
                count++;
            }
        }

        for (int i =0;i<callData.arr.size();i+=4){

            if (Integer.parseInt(callData.arr.get(i+2))==GlobalVariable.g_nowTeam.getTeamNum()){
                proagenda.add(callData.arr.get(i+3));
            }
        }
        for(int i = 0;i<proagenda.size();i++)
        {
            String start = proagenda.get(i).split("/")[0];
            String end = proagenda.get(i).split("/")[1];
            int startyear = Integer.parseInt(start.split("-")[0]);
            int startmonth = Integer.parseInt(start.split("-")[1]);
            int startday = Integer.parseInt(start.split("-")[2]);
            int endyear = Integer.parseInt(end.split("-")[0]);
            int endmonth = Integer.parseInt(end.split("-")[1]);
            int endday = Integer.parseInt(end.split("-")[2]);
            subday.add(GetDifferenceOfDate(endyear,endmonth,endday,startyear,startmonth,startday));
        }


        int all=0;
        for(int i=0;i<subday.size();i++)
        {
            all = all+subday.get(i);
        }
        for(int i=0;i<subday.size();i++)
        {
            subday.set(i,subday.get(i)*100/all);
        }

        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();

        for(int i = 0; i < count; i++) {
            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
            entries.add(new BarEntry(i,(float) subday.get(i)));
            BarDataSet ds = new BarDataSet(entries, proname.get(i));
            switch(i%6)
            {
                case 0:ds.setColor(Color.rgb(200,20,20));break;
                case 1:ds.setColor(Color.rgb(20 ,200,20));break;
                case 2:ds.setColor(Color.rgb(20,20,200));break;
                case 3:ds.setColor(Color.rgb(200,200,20));break;
                case 4:ds.setColor(Color.rgb(20 ,200,200));break;
                case 5:ds.setColor(Color.rgb(200,20,200));break;
            }
            sets.add(ds);
        }

        BarData d = new BarData(sets);
        d.setValueTypeface(tf);
        return d;
    }



    /**
     * generates less data (1 DataSet, 4 values)
     * @return
     */
    protected PieData generatePieData() {

        int count = 4;

        ArrayList<PieEntry> entries1 = new ArrayList<PieEntry>();


        for(int i = 0; i < GlobalVariable.g_nowTeam.getMembers().size(); i++) {
            entries1.add(new PieEntry((float)(Math.random()*60+40), GlobalVariable.g_nowTeam.getMembers().get(i)));
        }

        PieDataSet ds1 = new PieDataSet(entries1, GlobalVariable.g_nowTeam.getTeamName()+" 2018");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);
        d.setValueTypeface(tf);

        return d;
    }




    public int GetDifferenceOfDate ( int nYear1, int nMonth1, int nDate1, int nYear2, int nMonth2, int nDate2 ) {
        Calendar cal = Calendar.getInstance ( );
        int nTotalDate1 = 0, nTotalDate2 = 0, nDiffOfYear = 0, nDiffOfDay = 0;

        if ( nYear1 > nYear2 ) {
            for ( int i = nYear2; i < nYear1; i++ ) {
                cal.set ( i, 12, 0 ); nDiffOfYear += cal.get ( Calendar.DAY_OF_YEAR );
            }
            nTotalDate1 += nDiffOfYear;
        } else if ( nYear1 < nYear2 ) {
            for ( int i = nYear1; i < nYear2; i++ ) {
                cal.set ( i, 12, 0 );
                nDiffOfYear += cal.get ( Calendar.DAY_OF_YEAR );
            }
            nTotalDate2 += nDiffOfYear;
        }
        cal.set ( nYear1, nMonth1-1, nDate1 );
        nDiffOfDay = cal.get ( Calendar.DAY_OF_YEAR );
        nTotalDate1 += nDiffOfDay;
        cal.set ( nYear2, nMonth2-1, nDate2 );
        nDiffOfDay = cal.get ( Calendar.DAY_OF_YEAR );
        nTotalDate2 += nDiffOfDay;
        return nTotalDate1-nTotalDate2;
    }
}



