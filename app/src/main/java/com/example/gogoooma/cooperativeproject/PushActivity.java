package com.example.gogoooma.cooperativeproject;

import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PushActivity extends Fragment {
    List<MessageData> m_data;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MessageAdapter adapter;
    TimerTask timerTask;
    CallData callData = new CallData("alarm");
    CallData callData2 = new CallData("member");
    View v;

    String send = "";
    String receive = "";
    String mess = "";

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (callData.arr.size() == 0) ;
                while (callData2.arr.size() == 0) ;
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
        }
        v = inflater.inflate(R.layout.activity_push, container, false);
        receive();
        notification();

        // 메시지 추가
        FloatingActionButton fab = v.findViewById(R.id.sendMessage);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addMessage = new Intent(v.getContext(), AddMessageActivity.class);
                startActivity(addMessage);
            }
        });

        return v;
    }

    //if(receive.equals(callData.arr.get(j + 1))){

    // 수신된 메시지 확인에 관련된 것들
    public void receive() {
        m_data = new ArrayList<MessageData>();
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(v.getContext(),
                LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManager);

        for (int i = 0; i < callData.arr.size(); i += 4) {
            // 각 줄의 발신자, 수신자, 메시지를 받음
            send = callData.arr.get(i).toString();

            receive = callData.arr.get(1 + i).toString();

            mess = callData.arr.get(2 + i).toString();

            // 수신자가 현재 사용자일 경우만 데이터 리스트에 추가
            if (receive.equals(GlobalVariable.g_user.getPhoneNum())) {
                m_data.add(new MessageData(send, receive, mess));
            }
        }

        adapter = new MessageAdapter(m_data);
        recyclerView.setAdapter(adapter);
    }

    public void notification() {

        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (receive != "")
                    makeNotification();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 1000);
    }

    public void makeNotification() {
        NotificationManager manager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(v.getContext());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher));
        builder.setTicker("메시지");
        builder.setContentTitle("From" + receive);
        builder.setContentText(mess);
        builder.setVibrate(new long[]{0, 3000});
        Uri soundUri = RingtoneManager.getActualDefaultRingtoneUri(v.getContext(),
                RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(soundUri);
        builder.setAutoCancel(true);

        Intent intent = new Intent(v.getContext(), PushActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(v.getContext(), 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pIntent);

        Notification notification = builder.build();
        manager.notify(1, notification);
    }
}