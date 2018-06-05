package com.example.gogoooma.cooperativeproject;

import android.app.ProgressDialog;
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

public class AddMessageActivity extends AppCompatActivity {
    EditText r;
    EditText m;
    SendMessage sendmsg;
    CallData callData = new CallData("alarm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (callData.arr.size() == 0 || callData.arr.size()%4!=0) ;
            }
        };

        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
        }

        r = (EditText) findViewById(R.id.receiverName);
        m = (EditText) findViewById(R.id.Message);
    }

    public void Send(View view) {
        String sendername = GlobalVariable.g_user.getName();
        String receivername = r.getText().toString();
        String message = m.getText().toString();
        sendmsg = new SendMessage();
        sendmsg.execute(sendername, receivername, message, "여기에 msgnum");
    }

    class SendMessage extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(AddMessageActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
        }


        @Override
        protected String doInBackground(String... params) {

            String phpsender = (String) params[0];
            String phpreceiver = (String) params[1];
            String phpmessage = (String) params[2];
            String phpmsgnum = (String) params[3];

            String serverURL = "http://anesc1.cafe24.com/alarmup.php";
            String postParameters = "&sender=" + phpsender + "&receiver=" + phpreceiver + "&message=" + phpmessage + "&msgnum=" + phpmsgnum;


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