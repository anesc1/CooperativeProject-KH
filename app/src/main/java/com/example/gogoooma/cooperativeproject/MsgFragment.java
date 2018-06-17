package com.example.gogoooma.cooperativeproject;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MsgFragment extends Fragment {
    View v;
    DatabaseReference db;
    AdminMsg msg;
    ListView adminMsgList;
    ProjectAdapter adapter;
    String nowStr;

    public MsgFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_msg, container, false);
        db = FirebaseDatabase.getInstance().getReference("adminMsg");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // snapshot이 현재 DB의 snapshot 데이터만큼 읽어옴
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> map = (HashMap<String, Object>) snapshot.getValue();
                    if (((String) map.get("admin")).equals(GlobalVariable.g_user.getPhoneNum())){
                        nowStr = snapshot.getKey();
                        msg = new AdminMsg((String) map.get("admin"),
                                (String) map.get("check"), (String) map.get("teamNum"),
                                (ArrayList<String>) map.get("pNames"), (ArrayList<String>) map.get("pAgendas"),
                                (ArrayList<String>) map.get("checking"));
                    }
                }
                ArrayList<Project> list = new ArrayList<>();
                for(int i=0; i<msg.pNames.size(); i++){
                    list.add(new Project(msg.pNames.get(i), 0, msg.pAgendas.get(i), 0));
                }
                adapter = new ProjectAdapter(v.getContext(), R.layout.team_project, list, msg.checking);
                adminMsgList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adminMsgList = (ListView) v.findViewById(R.id.adminMsgList);
        adminMsgList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                msg.checking.set(position, "f");
                db.child(nowStr).child("checking").setValue(msg.checking);
                adapter.notifyDataSetChanged();
            }
        });

        return v;
    }
}