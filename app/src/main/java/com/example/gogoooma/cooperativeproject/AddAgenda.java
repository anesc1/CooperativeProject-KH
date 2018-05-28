package com.example.gogoooma.cooperativeproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AddAgenda extends AppCompatActivity {
    ArrayList<String> projectName;
    Integer projectNum;
    String projectName1, projectName2, projectName3, projectName4, projectName5, projectName6, projectName7;
    RegisterProject insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agenda);
        Intent i = getIntent();
        projectNum = i.getIntExtra("projectNum", -1);
        projectName1 = i.getStringExtra("projectName1");
        projectName2 = i.getStringExtra("projectName2");
        projectName3 = i.getStringExtra("projectName3");
        projectName4 = i.getStringExtra("projectName4");
        projectName5 = i.getStringExtra("projectName5");
        projectName6 = i.getStringExtra("projectName6");
        projectName7 = i.getStringExtra("projectName7");

//
//        for(int i=0;i<projectNum;i++)
//        {
//            String temp = String.valueOf(i);
//            insert = new AddAgenda.RegisterProject();
//            insert.execute(projectName.get(i).toString(),temp,String.valueOf(GlobalVariable.g_nowTeam.getTeamNum()),"");
//            GlobalVariable.g_project.add(new Project(projectName.get(i).toString(),i,Integer.parseInt(agenda.get(i).toString()),GlobalVariable.g_nowTeam.getTeamNum()));
    }

    class RegisterProject extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(AddAgenda.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
        }


        @Override
        protected String doInBackground(String... params) {

            String phpprojectName = (String) params[0];
            String phpprojectNum = (String) params[1];
            String phpteamNum = (String) params[2];
            String phpagenda = (String) params[3];

            String serverURL = "http://anesc1.cafe24.com/projectup.php";
            String postParameters = "&projectName=" + phpprojectName + "&projectNum=" + phpprojectNum + "&teamNum=" + phpteamNum + "&agenda=" + phpagenda;


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
