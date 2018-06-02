package com.example.gogoooma.cooperativeproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AddMessageActivity extends AppCompatActivity {
    EditText s;
    EditText r;
    EditText m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);

        s = (EditText) findViewById(R.id.senderName);
        r = (EditText) findViewById(R.id.receiverName);
        m = (EditText) findViewById(R.id.Message);
    }

    public void Send(View view) {
        String sendername = s.getText().toString();
        String receivername = s.getText().toString();
        String message = m.getText().toString();

        // 이 세개의 string을 DB에 넣어야됨!!
    }
}
