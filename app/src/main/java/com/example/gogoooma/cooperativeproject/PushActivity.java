package com.example.gogoooma.cooperativeproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PushActivity extends AppCompatActivity {
    List<MessageData> m_data;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MessageAdapter adapter;
    private FloatingActionButton sendm;
    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        receive();
        notification();

        // 메시지 추가
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addMessage = new Intent(getApplicationContext(), AddMessageActivity.class);
                startActivity(addMessage);
            }
        });
    }


    // 수신된 메시지 확인에 관련된 것들
    public void receive() {
        m_data = new ArrayList<MessageData>();

        // to등희  여기서 MessageData에 sender, receiver, message를 담아서 list에 저장해서 recyclerview로 보낼거임

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessageAdapter(m_data);

        recyclerView.setAdapter(adapter);
    }

    public void notification() {

        timerTask = new TimerTask() {
            @Override
            public void run() {
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
        builder.setTicker("메시지");
        builder.setContentTitle("From" + "보낸 사람"); //보낸 사람을 알려줌 (DB 사용)
        builder.setContentText("보낼 메시지"); //메시지를 띄워줌 (DB 사용)
        builder.setVibrate(new long[]{0, 3000});
        Uri soundUri = RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(soundUri);
        builder.setAutoCancel(true);

        Intent intent = new Intent(this, PushActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pIntent);

        Notification notification = builder.build();
        manager.notify(1, notification);
    }

    public void pushClick(View view) {

    }
}
