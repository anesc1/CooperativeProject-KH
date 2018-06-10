package com.example.gogoooma.cooperativeproject;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends Fragment {

    View view;
    public ChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewPager viewPager=(ViewPager) getActivity().findViewById(R.id.viewPager);
        viewPager.setAdapter(new ChartSlider(getActivity().getFragmentManager()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_chart, container, false);
        return view;
    }


    private class ChartSlider extends FragmentPagerAdapter {

        public ChartSlider(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new BarChartFrag();
                case 1:
                    return new PieChartActivity();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
