package com.example.gogoooma.cooperativeproject;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class PlaceActivity extends Fragment {
    View v;
    ListView listView;
    PlaceAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_place, container, false);

        listView = (ListView) v.findViewById(R.id.plistView);
        adapter = new PlaceAdapter(v.getContext(), R.layout.row, GlobalVariable.g_nowTeam.getPlaces());
       // Toast.makeText(v.getContext(), GlobalVariable.g_nowTeam.getPlaces().get(0).getPlace(), Toast.LENGTH_SHORT).show();

        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.pfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPlace = new Intent(v.getContext(), FourthActivity.class);
                startActivityForResult(addPlace, 123);
            }
        });
    //
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123){
            adapter.notifyDataSetChanged();
        }
    }
}
