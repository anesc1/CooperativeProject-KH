package com.example.gogoooma.cooperativeproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class AddProject extends AppCompatActivity {
    EditText projectName1;
    EditText agenda1;
    EditText projectName2;
    EditText agenda2;
    EditText projectName3;
    EditText agenda3;
    EditText projectName4;
    EditText agenda4;
    EditText projectName5;
    EditText agenda5;
    EditText projectName6;
    EditText agenda6;
    EditText projectName7;
    EditText agenda7;
    Integer projectNum;
    String  increaseNum="0";
    ArrayList<String> projectName;
    ArrayList<String> agenda;
    RegisterProject insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        Intent i = getIntent();
        projectNum = i.getIntExtra("projectNum",-1);

        projectName1 = (EditText)findViewById(R.id.projectName1);
        agenda1 = (EditText)findViewById(R.id.agenda1);
        projectName2 = (EditText)findViewById(R.id.projectName2);
        agenda2 = (EditText)findViewById(R.id.agenda2);
        projectName3 = (EditText)findViewById(R.id.projectName3);
        agenda3 = (EditText)findViewById(R.id.agenda3);
        projectName4 = (EditText)findViewById(R.id.projectName4);
        agenda4 = (EditText)findViewById(R.id.agenda4);
        projectName5 = (EditText)findViewById(R.id.projectName5);
        agenda5 = (EditText)findViewById(R.id.agenda5);
        projectName6 = (EditText)findViewById(R.id.projectName6);
        agenda6 = (EditText)findViewById(R.id.agenda6);
        projectName7 = (EditText)findViewById(R.id.projectName7);
        agenda7 = (EditText)findViewById(R.id.agenda7);

        if(projectNum==3)
        {
            projectName4.setVisibility(View.INVISIBLE);
            agenda4.setVisibility(View.INVISIBLE);
            projectName5.setVisibility(View.INVISIBLE);
            agenda5.setVisibility(View.INVISIBLE);
            projectName6.setVisibility(View.INVISIBLE);
            agenda6.setVisibility(View.INVISIBLE);
            projectName7.setVisibility(View.INVISIBLE);
            agenda7.setVisibility(View.INVISIBLE);
        }else if(projectNum==4)
        {
            projectName5.setVisibility(View.INVISIBLE);
            agenda5.setVisibility(View.INVISIBLE);
            projectName6.setVisibility(View.INVISIBLE);
            agenda6.setVisibility(View.INVISIBLE);
            projectName7.setVisibility(View.INVISIBLE);
            agenda7.setVisibility(View.INVISIBLE);
        }else if(projectNum==5)
        {
            projectName6.setVisibility(View.INVISIBLE);
            agenda6.setVisibility(View.INVISIBLE);
            projectName7.setVisibility(View.INVISIBLE);
            agenda7.setVisibility(View.INVISIBLE);
        }else if(projectNum==6)
        {
            projectName7.setVisibility(View.INVISIBLE);
            agenda7.setVisibility(View.INVISIBLE);
        }
    }



    public void Save(View view) {
        projectName = new ArrayList<>();
        agenda = new ArrayList<>();
        String prjName1 = projectName1.getText().toString();
        String agd1 = agenda1.getText().toString();
        projectName.add(prjName1);
        agenda.add(agd1);
        String prjName2 = projectName2.getText().toString();
        String agd2 = agenda2.getText().toString();
        projectName.add(prjName2);
        agenda.add(agd2);
        String prjName3 = projectName3.getText().toString();
        String agd3 = agenda3.getText().toString();
        projectName.add(prjName3);
        agenda.add(agd3);
        String prjName4 = projectName4.getText().toString();
        String agd4 = agenda4.getText().toString();
        projectName.add(prjName4);
        agenda.add(agd4);
        String prjName5 = projectName5.getText().toString();
        String agd5 = agenda5.getText().toString();
        projectName.add(prjName5);
        agenda.add(agd5);
        String prjName6 = projectName6.getText().toString();
        String agd6 = agenda6.getText().toString();
        projectName.add(prjName6);
        agenda.add(agd6);
        String prjName7 = projectName7.getText().toString();
        String agd7 = agenda7.getText().toString();
        projectName.add(prjName7);
        agenda.add(agd7);


        for(int i=0;i<projectNum;i++)
        {
            String temp = String.valueOf(i);
            insert = new AddProject.RegisterProject();
            insert.execute(projectName.get(i).toString(),temp,String.valueOf(GlobalVariable.g_nowTeam.getTeamNum()),agenda.get(i).toString());
            GlobalVariable.g_project.add(new Project(projectName.get(i).toString(),i,Integer.parseInt(agenda.get(i).toString()),GlobalVariable.g_nowTeam.getTeamNum()));
        }
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    class RegisterProject extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(AddProject.this,
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

