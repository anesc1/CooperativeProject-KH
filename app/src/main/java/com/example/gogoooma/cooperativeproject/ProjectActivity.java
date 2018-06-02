package com.example.gogoooma.cooperativeproject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProjectActivity extends Fragment implements View.OnClickListener {
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    Team team;
    ListView listView,listView1;
    TextView projAdminName, projAdminPhone;
    MemberAdapter adapter;
    ArrayAdapter adapter1;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_project, container, false);
        listView = (ListView) v.findViewById(R.id.teamMemberListView);
        listView1 = (ListView)v.findViewById(R.id.teamProjListView);
        projAdminName = (TextView) v.findViewById(R.id.projAdminName);
        projAdminPhone = (TextView) v.findViewById(R.id.projAdminPhone);
        team = GlobalVariable.g_nowTeam;
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        fab = (FloatingActionButton)v.findViewById(R.id.fab);
        fab1 = (FloatingActionButton)v.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)v.findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(v.getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(v.getContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(v.getContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(v.getContext(),R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        init();

        return v;
    }

    public void init(){
        projAdminName.setText(team.getAdmin().getName());
        projAdminPhone.setText(team.getAdmin().getPhoneNum());
        int num = 0;
        for(int i=0; i<team.getMembers().size(); i++){
            if(team.getMembers().get(i).getPhoneNum().equals(team.getLeader().getPhoneNum())){
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

        ArrayList<Project> temp = new ArrayList<>();
        for(int i=0; i<GlobalVariable.g_project.size() ;i++)
        {
            temp.add(GlobalVariable.g_project.get(i));
        }
        adapter1 = new ProjectAdapter(v.getContext(),R.layout.team_project,temp);
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
        switch (id){
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:
                // 프로젝트 생성 버튼 클릭
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
        if(requestCode == 11){
            if(resultCode == 1){
                Member newMember = (Member)data.getSerializableExtra("member");
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                intent.putExtra("member", newMember);
                intent.putExtra("flag", true);
                startActivityForResult(intent, 22);
            }
        } else if(requestCode == 22){
            listView.setAdapter(adapter);
            setListViewHeightBasedOnChildren(listView);
            adapter.notifyDataSetChanged();
        }
    }

    public void animateFAB(){

        if(isFabOpen){

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
            Log.d("Raj","open");

        }
    }
}
