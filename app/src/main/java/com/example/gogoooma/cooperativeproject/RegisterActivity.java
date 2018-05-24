package com.example.gogoooma.cooperativeproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    ArrayList<String> phoneNumList;
    int num_mem;
    int check;
    String Name, phoneNum, pass, passconf, age, admin;
    EditText edit_Name;
    EditText edit_phoneNum;
    EditText edit_pass;
    EditText edit_age;
    EditText edit_passconf;
    CheckBox checkBox;
    RegisterData insert;
    CallData callData = new CallData("member");
    boolean checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edit_Name = (EditText) findViewById(R.id.editText);
        edit_phoneNum = (EditText) findViewById(R.id.editText6);
        edit_pass = (EditText) findViewById(R.id.editText7);
        edit_age = (EditText) findViewById(R.id.editText4);
        edit_passconf = (EditText) findViewById(R.id.editText8);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        phoneNumList = new ArrayList<>();
        check =0;
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checked)
                    checked = false;
                else
                    checked = true;
            }
        });


    }

    public void onRegister(View view) {
        Name = edit_Name.getText().toString();
        phoneNum = edit_phoneNum.getText().toString();
        pass = edit_pass.getText().toString();
        passconf = edit_passconf.getText().toString();
        age = edit_age.getText().toString();

        Name = Name.replaceAll(" ","");
        Name = Name.replaceAll("-","");
        phoneNum = phoneNum.replaceAll(" ","");
        phoneNum = phoneNum.replaceAll("-","");
        if(phoneNum.length()==11)
        {
            int indexOf = phoneNum.indexOf("010");
            phoneNum = phoneNum.substring(indexOf, 11);
        }
        else
        {
            Toast.makeText(RegisterActivity.this,"번호는 11자리로 해주세요.",Toast.LENGTH_SHORT).show();
            edit_phoneNum.setText("");
            phoneNum="";
        }
        pass = pass.replaceAll(" ","");
        passconf = passconf.replaceAll(" ","");
        age = age.replaceAll(" ","");

        num_mem = callData.arr.size() / 5;
        for (int i = 0; i < num_mem; i++) {
            String temp = callData.arr.get(i * 5 + 1);
            temp = temp.replaceAll(" ","");
            phoneNumList.add(temp);
        }

        if(Name!="" && phoneNum!="" && pass!="" && passconf!="" && age!="")
        {
            for (int i = 0; i < num_mem; i++) {
                if (phoneNum.equals(phoneNumList.get(i))) {
                    Toast.makeText(RegisterActivity.this, "이미 존재하는 번호입니다.", Toast.LENGTH_SHORT).show();
                    check =1;
                    edit_Name.setText(Name);
                    edit_phoneNum.setText("");
                    edit_pass.setText("");
                    edit_passconf.setText("");
                    edit_age.setText(age);
                    edit_phoneNum.requestFocus();

                }
            }
            if (pass.equals(passconf) && check==0) {
                admin = null;
                if(checked)
                    admin = "t";
                else
                    admin = "f";
                insert = new RegisterData();
                insert.execute(Name, phoneNum, pass, age, admin);
                Intent intent = new Intent();
                setResult(1, intent);
                finish();
            } else if(!(pass.equals(passconf)) && check ==0){
                edit_Name.setText(Name);
                edit_phoneNum.setText(phoneNum);
                edit_pass.setText(pass);
                edit_passconf.setText("");
                edit_age.setText(age);
                Toast.makeText(RegisterActivity.this, "비밀번호와 비밀번호확인이 다릅니다", Toast.LENGTH_SHORT).show();
                edit_passconf.requestFocus();
            }

        }
    }

    class RegisterData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(RegisterActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
        }


        @Override
        protected String doInBackground(String... params) {

            String phpname = (String) params[0];
            String phpphoneNum = (String) params[1];
            String phppassword = (String) params[2];
            String phpage = (String) params[3];
            String phpadmin = (String) params[4];

            String serverURL = "http://anesc1.cafe24.com/memberup.php";
            String postParameters = "&name=" + phpname + "&phoneNum=" + phpphoneNum + "&password=" + phppassword + "&age=" + phpage + "&admin=" + phpadmin;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setRequestProperty("content-type", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                return new String("Error: " + e.getMessage());
            }

        }
    }

}

