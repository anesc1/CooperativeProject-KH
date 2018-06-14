package com.example.gogoooma.cooperativeproject;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity {
    RegisterPlace registerPlace;
    AddVer addVer;
    SendMessage sendMessage;
    DeleteTeam deleteTeam;
    boolean isProfile;
    Spinner spinner;
    EditText edit_place;
    String team;
    String startDay, startHour, startMin, endHour, endMin;
    boolean isStart, isTime = false;
    Button startBtn, endBtn;
    TimePickerDialog dialog;
    int num = 0;
    CallData callData2 = new CallData("team");
    int tempver;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        Intent intent = getIntent();
        db = FirebaseDatabase.getInstance().getReference("FireMsg");

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                while (callData2.arr.size() == 0 || callData2.arr.size()%6!=0) ;
            }
        };

        thread2.start();
        try {
            thread2.join();
        } catch (Exception e) {
        }

        isProfile = intent.getBooleanExtra("isProfile", true);
        isTime = intent.getBooleanExtra("isTime", false);
        if(isTime)
            isProfile = false;
        if(!isProfile){
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.cafeLinear);
            linearLayout.setVisibility(View.VISIBLE);
            Button button = (Button) findViewById(R.id.addAddress3);
            button.setText("등록");
        }
        dialog = new TimePickerDialog(this,
                AlertDialog.THEME_HOLO_LIGHT, listener, 12, 0, false);
        startBtn = (Button) findViewById(R.id.cafeStart);
        endBtn = (Button) findViewById(R.id.cafeEnd);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = true;
                dialog.show();
            }
        });
        endBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                isStart = false;
                dialog.show();
            }
        });
        if(isTime){
            int nowD = intent.getIntExtra("nowDay", 0);
            int nowH = intent.getIntExtra("nowH", 0);
            startBtn.setText(String.format("%02d", nowH) + ":00");
            endBtn.setText(String.format("%02d", nowH+1) + ":00");
            num = nowD;
            team = GlobalVariable.g_nowTeam.getTeamNum()+"";
            switch(nowD){
                case 1:
                    startDay = "월요일";
                    break;
                case 2:
                    startDay = "화요일";
                    break;
                case 3:
                    startDay = "수요일";
                    break;
                case 4:
                    startDay = "목요일";
                    break;
                case 5:
                    startDay = "금요일";
                    break;
                case 6:
                    startDay = "토요일";
                    break;
                case 7:
                    startDay = "일요일";
                    break;
            }
            startHour = nowH+"";
            startMin = 0+"";
            endHour = (nowH+1) + "";
            endMin = 0+"";
        }
        init();

        init_map();
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
        edit_place = (EditText) findViewById(R.id.cafeName);
        spinner = (Spinner) findViewById(R.id.cafeSpinner);

        final ArrayList<String> day = new ArrayList<>();
        day.add("요일을 설정하세요");
        day.add("월요일");
        day.add("화요일");
        day.add("수요일");
        day.add("목요일");
        day.add("금요일");
        day.add("토요일");
        day.add("일요일");
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, day);
        spinner.setAdapter(dayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0)
                    startDay = day.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        if(isTime)
            spinner.setSelection(num);
        team = GlobalVariable.g_nowTeam.getTeamNum()+"";
    }

    public void addAddress(View view) {
        if(isProfile) {
            String userAddr = GlobalVariable.g_user.getPhoneNum();
            String posX = String.valueOf(GlobalVariable.g_long);
            String posY = String.valueOf(GlobalVariable.g_lati);
            registerPlace = new RegisterPlace();
            registerPlace.execute(userAddr, "집", "-1", "-1", "-1", "-1", "-1", posX, posY, "-1");
            Toast.makeText(this, "주소가 등록되었습니다", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            registerPlace = new RegisterPlace();
            registerPlace.execute(team, edit_place.getText().toString(),
                    startDay, startHour, startMin, endHour, endMin,
                    String.valueOf(GlobalVariable.g_long), String.valueOf(GlobalVariable.g_lati), team);
            addVer = new AddVer();
            deleteTeam = new DeleteTeam();

            for(int i=0;i<callData2.arr.size();i+=6)
            {
                String tempteam = callData2.arr.get(i+1).toString();
                if(GlobalVariable.g_nowTeam.getTeamNum()==Integer.parseInt(tempteam))
                {
                    tempver = Integer.parseInt(callData2.arr.get(i+5).toString());
                    tempver = tempver+1;
                    deleteTeam.execute(tempteam);
                    addVer.execute(callData2.arr.get(i).toString(),callData2.arr.get(i+1).toString(),callData2.arr.get(i+2).toString(),callData2.arr.get(i+3).toString(),callData2.arr.get(i+4).toString(),String.valueOf(tempver));
                }
            }

            for(int i=0;i<GlobalVariable.g_nowTeam.getMembers().size();i++)
            {
                String teammembers = GlobalVariable.g_nowTeam.getMembers().get(i).getPhoneNum();
                sendMessage = new SendMessage();
                sendMessage.execute(GlobalVariable.g_user.getName(),teammembers,"버전이 "+ String.valueOf(tempver)+"로 바뀌었습니다.","msgnum");
            }
            GlobalVariable.g_nowTeam.places.add(new Place(GlobalVariable.g_nowTeam.getAdmin(),
                    edit_place.getText().toString(), startDay, Integer.parseInt(startHour),
                    Integer.parseInt(startMin), Integer.parseInt(endHour), Integer.parseInt(endMin),
                    GlobalVariable.g_long, GlobalVariable.g_lati, null));
            ArrayList<String> str1 = new ArrayList<>();
            ArrayList<String> str2 = new ArrayList<>();
            ArrayList<String> str3 = new ArrayList<>();
            str1.add("새로운 일정");
            str2.add(edit_place.getText().toString() +", "+startDay + " " + startHour+ ":" + startMin + " - " + endHour + ":" + endMin);
            str3.add(GlobalVariable.g_user.getPhoneNum());
            FireMsg fireMsg = new FireMsg(GlobalVariable.g_nowTeam.getTeamNum()+"", str1, str2, str3);
            db.push().setValue(fireMsg);
            finish();
        }
    }

    public void init_map() {
        Fragment1 fragment1 = new Fragment1();
        fragment1.setArguments(new Bundle());
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragmentHere2, fragment1);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    class RegisterPlace extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(AddressActivity.this,
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

    class AddVer extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(AddressActivity.this,
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
            String phpver = (String)params[5];

            String serverURL = "http://anesc1.cafe24.com/teamup.php";
            String postParameters = "&teamName=" + phpteamName + "&teamNum=" + phpteamNum + "&leader=" + phpleader + "&admin=" + phpadmin + "&member=" + phpmember + "&ver=" + phpver;


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

            progressDialog = ProgressDialog.show(AddressActivity.this,
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

    class SendMessage extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(AddressActivity.this,
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