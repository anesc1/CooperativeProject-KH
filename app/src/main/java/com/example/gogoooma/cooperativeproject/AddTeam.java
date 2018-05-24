package com.example.gogoooma.cooperativeproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddTeam extends AppCompatActivity {
    String teamName, teamNum,initmember;
    Integer teamSize;
    EditText editText;
    RegisterTeam insert;
    Member user;
    CallData callData = new CallData("team");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("팀 생성");
        setContentView(R.layout.activity_add_team);
        user = GlobalVariable.g_user;
        int color = Color.parseColor("#0f357d");
        editText = (EditText)findViewById(R.id.edit_teamName);
        editText.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    public void Save(View view) {
        teamName = editText.getText().toString();
        teamSize = callData.arr.size()/5;
        teamNum = String.valueOf(teamSize);
        initmember = user.getPhoneNum();
        insert = new RegisterTeam();
        insert.execute(teamName, teamNum, user.getPhoneNum(), "",initmember);
        List<Member> list = new ArrayList<>();
        list.add(user);
        GlobalVariable.g_team.add(new Team(list, null, teamName, Integer.parseInt(teamNum), null, null));
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }


    class RegisterTeam extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(AddTeam.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
        }


        @Override
        protected String doInBackground(String... params) {

            String phpteamName = (String) params[0];
            String phpteamNum = (String) params[1];
            String phpleader = (String) params[2];
            String phpadmin = (String) params[3];
            String phpmember = (String) params[4];

            String serverURL = "http://anesc1.cafe24.com/teamup.php";
            String postParameters = "&teamName=" + phpteamName + "&teamNum=" + phpteamNum + "&leader=" + phpleader + "&admin" + phpadmin + "&member=" + phpmember;


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