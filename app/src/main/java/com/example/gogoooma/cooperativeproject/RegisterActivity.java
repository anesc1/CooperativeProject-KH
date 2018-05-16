package com.example.gogoooma.cooperativeproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {
    EditText edit_Name;
    EditText edit_phoneNum;
    EditText edit_pass;
    EditText edit_age;
    Button regbtn;
    RegisterData insert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edit_Name = (EditText)findViewById(R.id.editText);
        edit_phoneNum = (EditText)findViewById(R.id.editText6);
        edit_pass = (EditText)findViewById(R.id.editText7);
        edit_age = (EditText)findViewById(R.id.editText4);

    }

    public void onRegister(View view) {
        String Name = edit_Name.getText().toString();
        String phoneNum = edit_phoneNum.getText().toString();
        String pass = edit_pass.getText().toString();
        String age = edit_age.getText().toString();

        insert = new RegisterData();
        insert.execute(Name,phoneNum,pass,age);
        Intent intent = new Intent();
        setResult(1, intent);
        finish();
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

            String name= (String)params[0];
            String phoneNum = (String)params[1];
            String password = (String)params[2];
            String age = (String)params[3];

            String serverURL = "http://anesc1.cafe24.com/memberup.php";
            String postParameters = "&name=" + name + "&phoneNum=" + phoneNum + "&password=" + password + "&age=" + age;


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

                return new String("Error: " + e.getMessage());
            }

        }
    }

}

