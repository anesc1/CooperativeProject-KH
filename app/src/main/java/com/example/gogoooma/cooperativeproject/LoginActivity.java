package com.example.gogoooma.cooperativeproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    String  phoneNum, pass;
    EditText edit_phoneNum;
    EditText edit_pass;
    CallData callData = new CallData("member");
    int num_mem;
    ArrayList<String> phoneNumList;
    ArrayList<String> passList;
    HashMap<String,String> Numpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_phoneNum = (EditText)findViewById(R.id.editText2);
        edit_pass = (EditText)findViewById(R.id.editText3);
        phoneNumList = new ArrayList<>();
        passList = new ArrayList<>();
        Numpass = new HashMap<>();

        link();
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
                callData = new CallData("member");
            }
        }
    }

    public void onLogin(View view) {



        phoneNum = edit_phoneNum.getText().toString();
        pass = edit_pass.getText().toString();
        num_mem = callData.arr.size() / 4;

//        for(int i=0; i<num_mem; i++){
//            String name = callData.arr.get(i*4);
//            String phoneNum = callData.arr.get(i*4+1);
//            String password = callData.arr.get(i*4+2);
//            int age = Integer.parseInt(callData.arr.get(i*4+3));
//            Member tempMember = new Member(name, age, phoneNum, password, false, null);
//            GlobalVariable.g_member.add(tempMember);
//        }

        for (int i = 0; i < num_mem; i++) {
            String temp = callData.arr.get(i * 4 + 1);
            temp = temp.replaceAll(" ","");
            phoneNumList.add(temp);
        }

        for (int i = 0; i < num_mem; i++) {
            String temp = callData.arr.get(i * 4 + 2);
            temp = temp.replaceAll(" ","");
            passList.add(temp);
        }

        for(int i=0; i< num_mem; i++)
        {
            Numpass.put(phoneNumList.get(i),passList.get(i));
        }
        if(phoneNum !=""&& pass !="")
        {
            if(pass.equals(Numpass.get(phoneNum)))
            {
                Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
            }else
            {
                Toast.makeText(LoginActivity.this,"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                edit_phoneNum.setText("");
                edit_pass.setText("");
            }
        }
    }
}
