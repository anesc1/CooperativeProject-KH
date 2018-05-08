package com.example.gogoooma.cooperativeproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createTab();
        link();
    }
    public void createTab(){

    }

    public void link(){
        TextView linkText = (TextView) findViewById(R.id.linkText);
        String str = "가입하려면 여기를 누르세요";
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        ssb.setSpan(new ForegroundColorSpan(Color.YELLOW),
                6, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        linkText.setText(ssb);
    }

    public void clickRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == 1){
                Toast.makeText(this, "가입된 아이디로 로그인해주세요", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}
