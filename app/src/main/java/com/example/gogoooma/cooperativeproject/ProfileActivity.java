package com.example.gogoooma.cooperativeproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileActivity extends AppCompatActivity {
    Member member;
    boolean flag;
    RegisterTeam insert;
    DeleteTeam delete;
    CallData callData = new CallData("timetable");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        member = (Member)intent.getSerializableExtra("member");
        flag = intent.getBooleanExtra("flag", false);
        init();
    }

    public void init(){
        TextView profileName2 = (TextView) findViewById(R.id.profileName2);
        TextView profileAge2 = (TextView) findViewById(R.id.profileAge2);
        TextView profilePhone2 = (TextView) findViewById(R.id.profilePhone2);

        profileName2.setText(member.getName());
        profileAge2.setText(member.getAge() + "");
        profilePhone2.setText(member.getPhoneNum());
        if(flag) {
            Button button = (Button) findViewById(R.id.btnInvite);
            button.setVisibility(View.VISIBLE);
        }
    }


    public void onInvite(View view) {
        Team team = GlobalVariable.g_nowTeam;
        String newMember = member.getPhoneNum();
        insert = new RegisterTeam();
        String teamMember = "";
        for(int i=0; i<team.getMembers().size(); i++){
            teamMember = teamMember + team.getMembers().get(i).getPhoneNum()+"/";
        }
        teamMember = teamMember + newMember;
        delete = new DeleteTeam();
        String num = String.valueOf(team.getTeamNum());
        delete.execute(num);
        insert.execute(team.getTeamName(),
                num, team.getLeader().getPhoneNum(), "", teamMember);
        GlobalVariable.g_nowTeam.members.add(member);
        for(int i=0; i<GlobalVariable.g_team.size(); i++){
            if(team.getTeamNum() == GlobalVariable.g_team.get(i).getTeamNum())
                GlobalVariable.g_team.get(i).members.add(member);

        }
        finish();
    }

    public void onTime(View view) {
        Intent intent = new Intent(this, TimetableActivity.class);
        startActivity(intent);
    }

    class RegisterTeam extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ProfileActivity.this,
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
            String postParameters = "&teamName=" + phpteamName + "&teamNum=" + phpteamNum + "&leader=" + phpleader + "&admin=" + phpadmin + "&member=" + phpmember;


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
    class DeleteTeam extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ProfileActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
        }


        @Override
        protected String doInBackground(String... params) {

            String phpteamNum1 = (String) params[0];

            String serverURL = "http://anesc1.cafe24.com/teamup1.php";
            String postParameters = "&teamNum=" + phpteamNum1;


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
