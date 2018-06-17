package com.example.gogoooma.cooperativeproject;

import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference db;
    TimerTask timerTask;
    String teamNum;
    String title, content;
    Integer check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance().getReference("FireMsg");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // snapshot이 현재 DB의 snapshot 데이터만큼 읽어옴
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> map = (HashMap<String, Object>) snapshot.getValue();
                    FireMsg msg = new FireMsg((String) map.get("teamNum"), (ArrayList<String>) map.get("title"),
                            (ArrayList<String>) map.get("content"), (ArrayList<String>) map.get("members"));
                    if(GlobalVariable.g_nowTeam != null)
                        if(!msg.getMembers().contains(GlobalVariable.g_user.getPhoneNum())) {
                            if (msg.getTeamNum().equals(GlobalVariable.g_nowTeam.getTeamNum() + "")) {
                                teamNum = msg.getTeamNum();
                                title = msg.getTitle().get(0);
                                content = msg.getContent().get(0);
                                notification();

                                msg.getMembers().add(GlobalVariable.g_user.getPhoneNum());
                                db.child(snapshot.getKey()).child("members")
                                        .setValue(msg.getMembers());
                            }
                        }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();

        if (GlobalVariable.g_nowTeam != null)
            ab.setTitle("\t" + GlobalVariable.g_nowTeam.getTeamName() + " 팀");
        else
            ab.setTitle("관리자 페이지");
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (GlobalVariable.g_user.getAdmin())
        {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.adminFirst).setVisible(true);
            nav_Menu.findItem(R.id.adminSecond).setVisible(true);
            nav_Menu.findItem(R.id.adminMsg).setVisible(true);
            nav_Menu.findItem(R.id.home).setVisible(false);
            nav_Menu.findItem(R.id.timeTable).setVisible(false);
            nav_Menu.findItem(R.id.thirdActivity).setVisible(false);
            nav_Menu.findItem(R.id.fourthActivity).setVisible(false);
            nav_Menu.findItem(R.id.pushActivity).setVisible(false);
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, new AdminActivity()).commit();
        } else

        {
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, new ProjectActivity()).commit();
        }
    }

    public void notification() {

        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (teamNum != null)
                    makeNotification();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 1000);
    }

    public void makeNotification() {
        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher));
        builder.setTicker(teamNum + "팀");
        builder.setContentTitle(teamNum+"팀의 "+title);
        builder.setContentText(content);
        builder.setVibrate(new long[]{0, 3000});
        Uri soundUri = RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(soundUri);
        builder.setAutoCancel(true);

        Intent intent = new Intent(MainActivity.this, PushActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pIntent);

        Notification notification = builder.build();
        manager.notify(1, notification);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(getApplicationContext(), TeamActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("member", GlobalVariable.g_user);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager manager = getFragmentManager();
        int id = item.getItemId();

        if (id == R.id.home) {
            manager.beginTransaction().replace(R.id.content_main, new ProjectActivity()).commit();
        } else if (id == R.id.timeTable) {
            manager.beginTransaction().replace(R.id.content_main, new TimeIntegrateActivity()).commit();
        } else if (id == R.id.thirdActivity) {
            manager.beginTransaction().replace(R.id.content_main, new ThirdActivity()).commit();
        } else if (id == R.id.fourthActivity) {
            manager.beginTransaction().replace(R.id.content_main, new PlaceActivity()).commit();
        } else if (id == R.id.pushActivity) {
            manager.beginTransaction().replace(R.id.content_main, new PushActivity()).commit();
        } else if (id == R.id.adminFirst) {

        } else if (id == R.id.adminSecond) {

        } else if (id == R.id.adminMsg) {
            manager.beginTransaction().replace(R.id.content_main, new MsgFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
