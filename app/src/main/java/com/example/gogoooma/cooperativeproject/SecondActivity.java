package com.example.gogoooma.cooperativeproject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SecondActivity extends Fragment {
    View v;
    private static String TAG = "phptest_WriteData";

    EditText edit_ID;
    EditText edit_agenda1;
    EditText edit_agenda2;
    EditText edit_agenda3;
    EditText edit_agenda4;
    EditText edit_agenda5;
    Button insertbtn;
    Button getbtn;
    TextView insert_result;
    InsertData insert;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_second, container, false);
        edit_ID = (EditText)v.findViewById(R.id.edit_ID);
        edit_agenda1 = (EditText)v.findViewById(R.id.edit_agenda1);
        edit_agenda2 = (EditText)v.findViewById(R.id.edit_agenda2);
        edit_agenda3 = (EditText)v.findViewById(R.id.edit_agenda3);
        edit_agenda4 = (EditText)v.findViewById(R.id.edit_agenda4);
        edit_agenda5 = (EditText)v.findViewById(R.id.edit_agenda5);
        insert_result = (TextView)v.findViewById(R.id.insert_result);
        insertbtn = (Button)v.findViewById(R.id.insertdata_btn);
        insertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = edit_ID.getText().toString();
                String agenda1 = edit_agenda1.getText().toString();
                String agenda2 = edit_agenda2.getText().toString();
                String agenda3 = edit_agenda3.getText().toString();
                String agenda4 = edit_agenda4.getText().toString();
                String agenda5 = edit_agenda5.getText().toString();

        insert = new InsertData();
        insert.execute(ID,agenda1,agenda2,agenda3,agenda4,agenda5);


                edit_ID.setText("");
                edit_agenda1.setText("");
                edit_agenda2.setText("");
                edit_agenda3.setText("");
                edit_agenda4.setText("");
                edit_agenda5.setText("");
            }
        });
        getbtn = (Button)v.findViewById(R.id.getdata_btn);
        getbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CallData.class);
                getActivity().startActivity(intent);
            }
        });
        return v;
    }



    class InsertData extends AsyncTask<String, Void, String> {
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

            String ID = (String)params[0];
            String agenda1 = (String)params[1];
            String agenda2 = (String)params[2];
            String agenda3 = (String)params[3];
            String agenda4 = (String)params[4];
            String agenda5 = (String)params[5];



            String serverURL = "http://anesc1.cafe24.com/signup.php";
            String postParameters = "ID=" + ID + "&agenda1=" + agenda1 + "&agenda2=" + agenda2 + "&agenda3=" + agenda3 + "&agenda4=" + agenda4 + "&agenda5=" + agenda5;


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
