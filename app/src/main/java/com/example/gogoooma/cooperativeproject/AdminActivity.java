package com.example.gogoooma.cooperativeproject;
// 지도 아이디 kxLUU44mpaFrMA9m81Dr
// secret vSbrpFnJfY

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AdminActivity extends Fragment {
    View v;
    RegisterPlace registerPlace;
    EditText edit_place;
    Button placebtn, startBtn, endBtn;
    boolean isStart = true;
    Spinner spinner, daySpinner;
    String num, admin, place, startDay, startHour, startMin, endHour, endMin, posX, posY;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_admin, container, false);
        init_map();
        init();

        placebtn = (Button) v.findViewById(R.id.addPlace);
        placebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place = edit_place.getText().toString();
                posX = String.valueOf(GlobalVariable.g_long);
                posY = String.valueOf(GlobalVariable.g_lati);
                registerPlace = new RegisterPlace();
                registerPlace.execute(admin, place, startDay, startHour, startMin, endHour, endMin, posX, posY, num);
            }
        });

        startBtn = (Button) v.findViewById(R.id.placeStart);
        endBtn = (Button) v.findViewById(R.id.placeEnd);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = true;
                TimePickerDialog dialog = new TimePickerDialog(v.getContext(),
                        AlertDialog.THEME_HOLO_LIGHT, listener, 12, 0, false);
                dialog.show();
            }
        });
        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = false;
                TimePickerDialog dialog = new TimePickerDialog(v.getContext(),
                        AlertDialog.THEME_HOLO_LIGHT, listener, 12, 0, false);
                dialog.show();
            }
        });
        return v;
    }

    public void init_map() {
        Fragment1 fragment1 = new Fragment1();
        fragment1.setArguments(new Bundle());
        FragmentManager fm = getActivity().getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragmentHere, fragment1);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        
    }

    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (isStart) {
                startHour = hourOfDay + "";
                startMin = minute + "";
            } else {
                endHour = hourOfDay + "";
                endMin = minute + "";
            }
            String ampm = "am";
            if (hourOfDay > 12) {
                hourOfDay -= 12;
                ampm = "pm";
            }
            String min = String.format("%02d", minute);
            if (isStart)
                startBtn.setText(ampm + hourOfDay + "시 " + min + "분");
            else
                endBtn.setText(ampm + hourOfDay + "시 " + min + "분");
        }
    };

    public void init() {
        edit_place = (EditText) v.findViewById(R.id.placeName);
        spinner = (Spinner) v.findViewById(R.id.spinner2);
        daySpinner = (Spinner) v.findViewById(R.id.spinner3);
        ArrayList<String> data = new ArrayList<>();
        data.add("장소를 사용할 최대 팀 수를 정하세요");
        for (int i = 0; i < 10; i++)
            data.add((i + 1) + "팀");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, data);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0)
                    num = position + "/";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        final ArrayList<String> day = new ArrayList<>();
        day.add("요일을 설정하세요");
        day.add("월요일");
        day.add("화요일");
        day.add("수요일");
        day.add("목요일");
        day.add("금요일");
        day.add("토요일");
        day.add("일요일");
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, day);
        daySpinner.setAdapter(dayAdapter);
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0)
                    startDay = day.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        admin = GlobalVariable.g_user.getPhoneNum();
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
            String phpposY = (String) params[8];
            String phpteams = (String) params[9];

            String serverURL = "http://anesc1.cafe24.com/placeup.php";
            String postParameters = "&admin=" + phpadmin + "&place=" + phpplace + "&startDay=" + phpstartDay + "&startHour=" + phpstartHour + "&startMin=" + phpstartMin + "&endHour=" + phpendHour + "&endMin=" + phpendMin + "&posX=" + phpposX + "&posY=" + phpposY + "&teams=" + phpteams;


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
