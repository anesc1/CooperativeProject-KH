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

import java.util.ArrayList;
import java.util.StringTokenizer;

public class LoginActivity extends AppCompatActivity {
    String phoneNum, pass;
    EditText edit_phoneNum;
    EditText edit_pass;
    CallData callData = new CallData("member");
    CallData callData2 = new CallData("team");
    CallData callData3 = new CallData("project");
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
        num_mem = callData.arr.size() / 5;

        int saveInt = -1;
        for (int i = 0; i < num_mem; i++) {
            String temp = callData.arr.get(i * 5 + 1);

            String temp2 = callData.arr.get(i * 5 + 2);

            if (phoneNum != "" && pass != "") {
                if (phoneNum.equals(temp) && pass.equals(temp2)) {
                    saveInt = i * 5;
                }
            }
        }

        if (saveInt != -1) {
            String name = callData.arr.get(saveInt);
            String ageStr = callData.arr.get(saveInt + 3).replaceAll(" ", "");
            int age = Integer.parseInt(ageStr);
            if(callData.arr.get(saveInt + 4).equals("t"))
                GlobalVariable.g_user = new Member(name, age, phoneNum, pass, true, null);
            else
                GlobalVariable.g_user = new Member(name, age, phoneNum, pass, false, null);


            for(int i=0; i<callData2.arr.size(); i+=5){
                int indexOf = callData2.arr.get(i+4).indexOf(phoneNum);
                if(indexOf > -1){
                    String tempStr = callData2.arr.get(i+1).replaceAll(" ", "");

                    // 팀에 팀원 할당
                    ArrayList<Member> list = new ArrayList<>();
                    StringTokenizer st = new StringTokenizer(callData2.arr.get(i+4).toString(), "/");
                    while (st.hasMoreTokens()) {
                        String str = st.nextToken().trim();

                        if(str!=null && str!="" && !str.equals(phoneNum)) {
                            for (int j = 0; j < callData.arr.size(); j += 5) {
                                if (callData.arr.get(j+1).equals(str)) {
                                    name = callData.arr.get(j).trim();
                                    String phone = callData.arr.get(j + 1).trim();
                                    String password = callData.arr.get(j + 2).trim();
                                    ageStr = callData.arr.get(j + 3).trim();
                                    age = Integer.parseInt(ageStr);
                                    list.add(new Member(name, age, phone, password, false, null));
                                }
                            }
                        }
                    }
                    list.add(GlobalVariable.g_user);

                    GlobalVariable.g_team.add(new Team(list, null, callData2.arr.get(i),
                            Integer.parseInt(tempStr), null, null));
                }

            }

            //같은 teamNum에 대한 project할당
            for(int i=0; i<callData3.arr.size(); i+=4){
               String projectName = callData3.arr.get(i).trim();
               Integer projectNum = Integer.parseInt(callData3.arr.get(i+1).trim());
               Integer agenda = Integer.parseInt(callData3.arr.get(i+3).trim());
               Integer teamNum = Integer.parseInt(callData3.arr.get(i+2).trim());
               for(int j=0;j<GlobalVariable.g_team.size();j++)
               {
                   if(teamNum.equals(GlobalVariable.g_team.get(j).getTeamNum()))
                   {
                       GlobalVariable.g_project.add(new Project(projectName,projectNum,agenda,teamNum));
                   }
               }
            }



            Intent intent = new Intent(this, TeamActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            edit_phoneNum.setText("");
            edit_pass.setText("");
        }

    }
}
