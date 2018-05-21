package com.example.gogoooma.cooperativeproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    String phoneNum, pass;
    EditText edit_phoneNum;
    EditText edit_pass;
    CallData callData = new CallData("member");
    CallData callData2 = new CallData("team");
    int num_mem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        int color = Color.WHITE;
        edit_phoneNum = (EditText) findViewById(R.id.editText2);
        edit_pass = (EditText) findViewById(R.id.editText3);
        edit_phoneNum.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        edit_pass.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        link();
    }

    public void link() {
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
        if (requestCode == 1) {
            if (resultCode == 1) {
                Toast.makeText(this, "가입된 아이디로 로그인해주세요", Toast.LENGTH_SHORT).show();
                callData = new CallData("member");
            }
        }
    }

    public void onLogin(View view) {
        phoneNum = edit_phoneNum.getText().toString();
        phoneNum = phoneNum.replaceAll(" ","");
        pass = edit_pass.getText().toString();
        pass = pass.replaceAll(" ","");
        num_mem = callData.arr.size() / 4;

        int saveInt = -1;
        for (int i = 0; i < num_mem; i++) {
            String temp = callData.arr.get(i * 4 + 1);
            temp = temp.replaceAll(" ", "");
            String temp2 = callData.arr.get(i * 4 + 2);
            temp2 = temp2.replaceAll(" ", "");
            if (phoneNum != "" && pass != "") {
                if (phoneNum.equals(temp) && pass.equals(temp2)) {
                    saveInt = i * 4;
                }
            }
        }

        if (saveInt != -1) {
            String name = callData.arr.get(saveInt);
            String ageStr = callData.arr.get(saveInt + 3).replaceAll(" ", "");
            int age = Integer.parseInt(ageStr);
            GlobalVariable.g_user = new Member(name, age, phoneNum, pass, false, null);

            for(int i=0; i<callData2.arr.size(); i+=5){
                int indexOf = callData2.arr.get(i+4).indexOf(phoneNum);
                if(indexOf > -1){
                    String tempStr = callData2.arr.get(i+1).replaceAll(" ", "");
                    GlobalVariable.g_team.add(new Team(null, null, callData2.arr.get(i),
                            Integer.parseInt(tempStr), null, null));
                }
            }

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            edit_phoneNum.setText("");
            edit_pass.setText("");
        }

    }
}
