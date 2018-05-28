package com.example.gogoooma.cooperativeproject;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

        TextView prj1 = (TextView)findViewById(R.id.prj1);
        Button prj1btn = (Button)findViewById(R.id.prj1btn);
        TextView prj1agd = (TextView)findViewById(R.id.prj1agd);
        TextView prj2 = (TextView)findViewById(R.id.prj2);
        Button prj2btn = (Button)findViewById(R.id.prj2btn);
        TextView prj2agd = (TextView)findViewById(R.id.prj2agd);
        TextView prj3 = (TextView)findViewById(R.id.prj3);
        Button prj3btn = (Button)findViewById(R.id.prj3btn);
        TextView prj3agd = (TextView)findViewById(R.id.prj3agd);
        TextView prj4 = (TextView)findViewById(R.id.prj4);
        Button prj4btn = (Button)findViewById(R.id.prj4btn);
        TextView prj4agd = (TextView)findViewById(R.id.prj4agd);
        TextView prj5 = (TextView)findViewById(R.id.prj5);
        Button prj5btn = (Button)findViewById(R.id.prj5btn);
        TextView prj5agd = (TextView)findViewById(R.id.prj5agd);
        TextView prj6 = (TextView)findViewById(R.id.prj6);
        Button prj6btn = (Button)findViewById(R.id.prj6btn);
        TextView prj6agd = (TextView)findViewById(R.id.prj6agd);
        TextView prj7 = (TextView)findViewById(R.id.prj7);
        Button prj7btn = (Button)findViewById(R.id.prj7btn);
        TextView prj7agd = (TextView)findViewById(R.id.prj7agd);

        prj1.setText(projectName1);
        prj2.setText(projectName2);
        prj3.setText(projectName3);
        prj4.setText(projectName4);
        prj5.setText(projectName5);
        prj6.setText(projectName6);
        prj7.setText(projectName7);

        if(projectNum==3)
        {
            prj4.setVisibility(View.INVISIBLE);
            prj4btn.setVisibility(View.INVISIBLE);
            prj4agd.setVisibility(View.INVISIBLE);
            prj5.setVisibility(View.INVISIBLE);
            prj5btn.setVisibility(View.INVISIBLE);
            prj5agd.setVisibility(View.INVISIBLE);
            prj6.setVisibility(View.INVISIBLE);
            prj6btn.setVisibility(View.INVISIBLE);
            prj6agd.setVisibility(View.INVISIBLE);
            prj7.setVisibility(View.INVISIBLE);
            prj7btn.setVisibility(View.INVISIBLE);
            prj7agd.setVisibility(View.INVISIBLE);
        }else if(projectNum==4)
        {
            prj5.setVisibility(View.INVISIBLE);
            prj5btn.setVisibility(View.INVISIBLE);
            prj5agd.setVisibility(View.INVISIBLE);
            prj6.setVisibility(View.INVISIBLE);
            prj6btn.setVisibility(View.INVISIBLE);
            prj6agd.setVisibility(View.INVISIBLE);
            prj7.setVisibility(View.INVISIBLE);
            prj7btn.setVisibility(View.INVISIBLE);
            prj7agd.setVisibility(View.INVISIBLE);
        }else if(projectNum==5)
        {
            prj6.setVisibility(View.INVISIBLE);
            prj6btn.setVisibility(View.INVISIBLE);
            prj6agd.setVisibility(View.INVISIBLE);
            prj7.setVisibility(View.INVISIBLE);
            prj7btn.setVisibility(View.INVISIBLE);
            prj7agd.setVisibility(View.INVISIBLE);
        }else if(projectNum==6)
        {
            prj7.setVisibility(View.INVISIBLE);
            prj7btn.setVisibility(View.INVISIBLE);
            prj7agd.setVisibility(View.INVISIBLE);
        }


init(R.id.prj1btn);


//
//        for(int i=0;i<projectNum;i++)
//        {
//            String temp = String.valueOf(i);
//            insert = new AddAgenda.RegisterProject();
//            insert.execute(projectName.get(i).toString(),temp,String.valueOf(GlobalVariable.g_nowTeam.getTeamNum()),"");
//            GlobalVariable.g_project.add(new Project(projectName.get(i).toString(),i,Integer.parseInt(agenda.get(i).toString()),GlobalVariable.g_nowTeam.getTeamNum()));
    }


    private void init(int btn) {

        //Calendar를 이용하여 년, 월, 일, 시간, 분을 PICKER에 넣어준다.
        final Calendar cal = Calendar.getInstance();

        //DATE PICKER DIALOG
        findViewById(btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(AddAgenda.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        String msg = String.format("%d 년 %d 월 %d 일", year, month + 1, date);
                        Toast.makeText(AddAgenda.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
                dialog.show();

            }
        });
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
