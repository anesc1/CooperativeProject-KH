package com.example.gogoooma.cooperativeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    Member user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        init();
    }

    public void init(){
        user = GlobalVariable.g_user;
        TextView profileName2 = (TextView) findViewById(R.id.profileName2);
        TextView profileAge2 = (TextView) findViewById(R.id.profileAge2);
        TextView profilePhone2 = (TextView) findViewById(R.id.profilePhone2);

        profileName2.setText(user.getName());
        profileAge2.setText(user.getAge() + "");
        profilePhone2.setText(user.getPhoneNum());
    }


}
