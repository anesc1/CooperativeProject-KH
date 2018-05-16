package com.example.gogoooma.cooperativeproject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SecondActivity extends Fragment {
    View v;
    private static String TAG = "phptest_WriteData";
    CallData callData = new CallData("http://anesc1.cafe24.com/projectdown.php");

    EditText edit_projectName;
    EditText edit_projectNum;
    EditText edit_teamNum;
    EditText edit_agenda;
    EditText edit_agendaNum;
    Button insertbtn;
    Button getbtn;
    TextView insert_result;
    PlaceInsertData insert;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_second, container, false);

        edit_projectName = (EditText)v.findViewById(R.id.edit_projectName);
        edit_projectNum = (EditText)v.findViewById(R.id.edit_projectNum);
        edit_teamNum = (EditText)v.findViewById(R.id.edit_teamNum);
        edit_agenda = (EditText)v.findViewById(R.id.edit_agenda);
        edit_agendaNum = (EditText)v.findViewById(R.id.edit_agendaNum);
        insert_result = (TextView)v.findViewById(R.id.insert_result);
        insertbtn = (Button)v.findViewById(R.id.insertdata_btn);
        insertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String projectName = edit_projectName.getText().toString();
                String projectNum = edit_projectNum.getText().toString();
                String teamNum = edit_teamNum.getText().toString();
                String agenda = edit_agenda.getText().toString();
                String agendaNum = edit_agendaNum.getText().toString();

        insert = new PlaceInsertData();
        insert.execute(projectName,projectNum,teamNum,agenda,agendaNum);
                edit_projectName.setText("");
                edit_projectNum.setText("");
                edit_teamNum.setText("");
                edit_agenda.setText("");
                edit_agendaNum.setText("");
            }
        });
        getbtn = (Button)v.findViewById(R.id.getdata_btn);
        getbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), GlobalVariable.g_projectArr.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }



    class PlaceInsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            insert_result.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String  projectName= (String)params[0];
            String projectNum = (String)params[1];
            String teamNum = (String)params[2];
            String agenda = (String)params[3];
            String agendaNum = (String)params[4];


            String serverURL = "http://anesc1.cafe24.com/projectup.php";
            String postParameters = "&projectName=" + projectName + "&projectNum=" + projectNum + "&teamNum=" + teamNum + "&agenda=" + agenda + "&agendaNum=" + agendaNum;


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
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

}
