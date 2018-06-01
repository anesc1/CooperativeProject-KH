package com.example.gogoooma.cooperativeproject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdminActivity extends Fragment {
    View v;
    RegisterPlace registerPlace;
    EditText edit_admin,edit_place,edit_startDay,edit_startHour,edit_startMin,edit_endHour,edit_endMin,edit_posX,edit_posY;
    Button placebtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_admin, container, false);
        edit_admin = (EditText)v.findViewById(R.id.admin1);
        edit_place = (EditText)v.findViewById(R.id.admin2);
        edit_startDay = (EditText)v.findViewById(R.id.admin3);
        edit_startHour = (EditText)v.findViewById(R.id.admin4);
        edit_startMin = (EditText)v.findViewById(R.id.admin5);
        edit_endHour = (EditText)v.findViewById(R.id.admin6);
        edit_endMin = (EditText)v.findViewById(R.id.admin7);
        edit_posX = (EditText)v.findViewById(R.id.admin8);
        edit_posY = (EditText)v.findViewById(R.id.admin9);

        final String admin = edit_admin.getText().toString();
        final String place = edit_place.getText().toString();
        final String startDay = edit_startDay.getText().toString();
        final String startHour = edit_startHour.getText().toString();
        final String startMin = edit_startMin.getText().toString();
        final String endHour = edit_endHour.getText().toString();
        final String endMin = edit_endMin.getText().toString();
        final String posX = edit_posX.getText().toString();
        final String posY = edit_posY.getText().toString();

        placebtn = (Button)v.findViewById(R.id.button3);
        placebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPlace = new RegisterPlace();
                registerPlace.execute(admin,place,startDay,startHour,startMin,endHour,endMin,posX,posY);
            }
        });

        return v;
    }

    class RegisterPlace extends AsyncTask<String, Void, String> {
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
        }


        @Override
        protected String doInBackground(String... params) {

            String phpadmin = (String) params[0];
            String phpplace = (String) params[1];
            String phpstartDay = (String) params[2];
            String phpstartHour = (String) params[3];
            String phpstartMin = (String) params[4];
            String phpendHour = (String) params[5];
            String phpendMin = (String) params[6];
            String phpposX = (String) params[7];
            String phpposY = (String) params[7];

            String serverURL = "http://anesc1.cafe24.com/placeup.php";
            String postParameters = "&admin=" + phpadmin + "&place=" + phpplace + "&startDay=" + phpstartDay + "&startHour=" + phpstartHour + "&startMin=" + phpstartMin + "&endHour=" + phpendHour + "&endMin=" + phpendMin + "&posX=" + phpposX + "&posY=" + phpposY;


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
