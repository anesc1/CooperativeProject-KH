package com.example.gogoooma.cooperativeproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ProjectActivity extends Fragment implements View.OnClickListener {
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    Team team;
    ListView listView, listView1;
    TextView projAdminName, projAdminPhone;
    MemberAdapter adapter;
    ArrayAdapter adapter1;
    View v;
    LinearLayout adminInfo;
    DatabaseReference db;
    ArrayList<Project> temp;
    ArrayList<String> strings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_project, container, false);
        listView = (ListView) v.findViewById(R.id.teamMemberListView);
        listView1 = (ListView) v.findViewById(R.id.teamProjListView);
        projAdminName = (TextView) v.findViewById(R.id.projAdminName);
        projAdminPhone = (TextView) v.findViewById(R.id.projAdminPhone);
        team = GlobalVariable.g_nowTeam;
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) v.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) v.findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(v.getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(v.getContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(v.getContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(v.getContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        adminInfo = (LinearLayout) v.findViewById(R.id.adminInfo);
        adminInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(v.getContext())
                        .setIcon(android.R.drawable.ic_menu_info_details)
                        .setTitle("메시지 전송")
                        .setMessage("프로젝트 정보를 전송하시겠습니까?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ArrayList<String> pnames = new ArrayList<>();
                                ArrayList<String> pagenda = new ArrayList<>();
                                for(int i=0; i<temp.size(); i++){
                                    pnames.add(temp.get(i).getProjectName());
                                    pagenda.add(temp.get(i).getAgenda());
                                }
                                AdminMsg msg = new AdminMsg(GlobalVariable.g_nowTeam.getAdmin().getPhoneNum(),
                                        "f",GlobalVariable.g_nowTeam.getTeamNum()+"",
                                        pnames,pagenda, strings);
                                db.push().setValue(msg);
                                Toast.makeText(v.getContext(), "전송완료", Toast.LENGTH_SHORT).show();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        init();

        return v;
    }

    public void init() {
        projAdminName.setText(team.getAdmin().getName());
        projAdminPhone.setText(team.getAdmin().getPhoneNum());
        int num = 0;
        for (int i = 0; i < team.getMembers().size(); i++) {
            if (team.getMembers().get(i).getPhoneNum().equals(team.getLeader().getPhoneNum())) {
                num = i;
            }
        }
        adapter = new MemberAdapter(v.getContext(), R.layout.team_member, team.getMembers(), num);
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                intent.putExtra("member", team.getMembers().get(position));
                startActivity(intent);
            }
        });

        temp = new ArrayList<>();
        for (int i = 0; i < GlobalVariable.g_project.size(); i++) {
            temp.add(GlobalVariable.g_project.get(i));
        }
        strings = new ArrayList<>();
        for( int i=0; i<temp.size(); i++)
            strings.add("t");
        db = FirebaseDatabase.getInstance().getReference("adminMsg");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // snapshot이 현재 DB의 snapshot 데이터만큼 읽어옴
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> map = (HashMap<String, Object>) snapshot.getValue();
                    if (((String) map.get("teamNum")).equals(GlobalVariable.g_nowTeam.getTeamNum()+"")){
                        Toast.makeText(v.getContext(), "ok", Toast.LENGTH_SHORT).show();
                        ArrayList<String> tempL = new ArrayList<>((ArrayList<String>) map.get("checking"));
                        adapter1 = new ProjectAdapter(v.getContext(), R.layout.team_project, temp, tempL);
                        listView1.setAdapter(adapter1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter1 = new ProjectAdapter(v.getContext(), R.layout.team_project, temp, strings);
        listView1.setAdapter(adapter1);
        setListViewHeightBasedOnChildren(listView1);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        return true;
    }

    // listView 를 scrollView에서 쓰기 위해서 선언한 함수
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:
                // 프로젝트 생성 버튼 클릭
                Intent intent = new Intent(getActivity(), AddProjectNum.class);
                startActivityForResult(intent, 123);
                break;
            case R.id.fab2:
                // 팀원 추가 버튼 클릭
                Intent addTeam = new Intent(v.getContext(), AddTeam.class);
                addTeam.putExtra("flag", true);
                startActivityForResult(addTeam, 11);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            if (resultCode == 1) {
                Member newMember = (Member) data.getSerializableExtra("member");
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                intent.putExtra("member", newMember);
                intent.putExtra("flag", true);
                startActivityForResult(intent, 22);
            }
        } else if (requestCode == 22) {
            listView.setAdapter(adapter);
            setListViewHeightBasedOnChildren(listView);
            adapter.notifyDataSetChanged();
        } else if (requestCode == 123) {
            if (resultCode == 11) {
                Intent intent1 = new Intent(getActivity(), ScheduleActivity.class);
                startActivityForResult(intent1, 11);
            } else {
                ProjectActivity proj = new ProjectActivity();
                proj.setArguments(new Bundle());
                FragmentManager fm = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, proj);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }
    }

    public void animateFAB() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");

        }
    }
}
