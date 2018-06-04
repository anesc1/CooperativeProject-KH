package com.example.gogoooma.cooperativeproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
import java.util.List;

public class FourthActivity extends AppCompatActivity {
    int arr[][] = new int[7][23];
    CallData tt = new CallData("timetable");
    CallData callData = new CallData("place");
    List<Place> list;
    Spinner spinner;
    Button startBtn, endBtn;
    ImageButton searchBtn;
    String findDay = "전체";
    int findSH, findSM, findEH, findEM;
    boolean isStart;
    RegisterPlace registerPlace = new RegisterPlace();

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (callData.arr.size() == 0) ;
                while (tt.arr.size() == 0) ;
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
        }
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        

        list = new ArrayList<>();


        Button otherPlace = (Button) findViewById(R.id.otherPlace);
        otherPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FourthActivity.this, AddressActivity.class);
                intent.putExtra("isProfile", false);
                startActivity(intent);
            }
        });
        spinner = (Spinner) findViewById(R.id.spinnerPlc);
        final ArrayList<String> day = new ArrayList<>();
        day.add("전체");
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
                findDay = day.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        startBtn = (Button) findViewById(R.id.startPlc);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = true;
                TimePickerDialog dialog = new TimePickerDialog(FourthActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT, listener, 12, 0, false);
                dialog.show();
            }
        });
        endBtn = (Button) findViewById(R.id.endPlc);
        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = false;
                TimePickerDialog dialog = new TimePickerDialog(FourthActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT, listener, 12, 0, false);
                dialog.show();
            }
        });
        searchBtn = (ImageButton) findViewById(R.id.btnSearch);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!findDay.equals("전체")) {
                    List<Place> tempList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getDay().equals(findDay))
                            tempList.add(list.get(i));
                    }
                    adapter = new MyAdapter(FourthActivity.this, tempList);
                    recyclerView.setAdapter(adapter);
                }else{
                    adapter = new MyAdapter(FourthActivity.this, list);
                    recyclerView.setAdapter(adapter);
                }

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getTimetable();
        getPlace();

        adapter = new MyAdapter(this, list);
        recyclerView.setAdapter(adapter);
        // 카드뷰를 선택했을 때
        adapter.setItemClick(new MyAdapter.ItemClick() {
            public void onClick(View view, int position) {
                final int pos = position;
                new android.support.v7.app.AlertDialog.Builder(FourthActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("등록")
                        .setMessage("등록하시겠습니까?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Place nowP = list.get(pos);
                                registerPlace.execute(GlobalVariable.g_nowTeam.getTeamNum()+""
                                        , nowP.getPlace(), nowP.getDay(), nowP.getStartHour()+"", nowP.getStartMin()+""
                                        , nowP.getEndHour()+"", nowP.getEndMin()+"",
                                        nowP.getPosX()+"", nowP.getPosY()+"", GlobalVariable.g_nowTeam.getTeamNum()+"");

                                ProjectActivity proj = new ProjectActivity();
                                proj.setArguments(new Bundle());
                                FragmentManager fm = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                fragmentTransaction.replace(R.id.content_main, proj);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

    }

    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (isStart) {
                findSH = hourOfDay;
                findSM = minute;
            } else {
                findEH = hourOfDay;
                findEM = minute;
            }
            String ampm = "am ";
            if (hourOfDay > 12) {
                hourOfDay -= 12;
                ampm = "pm ";
            }
            String min = String.format("%02d", minute);
            if (isStart)
                startBtn.setText(ampm + hourOfDay + ":" + min);
            else
                endBtn.setText(ampm + hourOfDay + ":" + min);
        }
    };

    public void getPlace() {
        for (int i = 0; i < callData.arr.size(); i += 10) {
            if (callData.arr.get(i).equals(GlobalVariable.g_nowTeam.getAdmin().getPhoneNum())) {
                // 각 줄의 요일, 시작시간, 종료시간을 받음
                String weekday = callData.arr.get(2 + i).toString();
                int startHour = Integer.parseInt(callData.arr.get(3 + i));
                int startMin = Integer.parseInt(callData.arr.get(4 + i));
                int endHour = Integer.parseInt(callData.arr.get(5 + i));
                int endMin = Integer.parseInt(callData.arr.get(6 + i));
                double posX = Double.parseDouble(callData.arr.get(i + 7));
                double posY = Double.parseDouble(callData.arr.get(i + 8));
                int day = 0;
                switch (weekday) {
                    case "월요일":
                        day = 0;
                        break;
                    case "화요일":
                        day = 1;
                        break;
                    case "수요일":
                        day = 2;
                        break;
                    case "목요일":
                        day = 3;
                        break;
                    case "금요일":
                        day = 4;
                        break;
                    case "토요일":
                        day = 5;
                        break;
                    case "일요일":
                        day = 6;
                        break;
                }
                int num1 = 2 * (startHour - 9);
                if (startMin == 30)
                    num1++;
                int num2 = 2 * (endHour - 9);
                if (endMin == 30)
                    num2++;
                boolean flag = false;
                int start = -1;
                int end = -1;
                for (int j = num1; j < num2; j++) {
                    if (arr[day][j] == 0) {
                        if (flag) {
                            end = j + 1;
                            if (arr[day][j + 1] == 1 || j == num2 - 1) {
                                if (start != -1 && end != -1)
                                    list.add(new Place(GlobalVariable.g_nowTeam.getAdmin(), callData.arr.get(i + 1),
                                            weekday, (start / 2) + 9, (start % 2) * 30,
                                            (end / 2) + 9, (end % 2) * 30, posX, posY, null));
                                flag = false;
                                continue;
                            }
                        } else {
                            start = j;
                            flag = true;
                            end = j + 1;
                            if (arr[day][j + 1] == 1 || j == num2 - 1) {
                                if (start != -1 && end != -1)
                                    list.add(new Place(GlobalVariable.g_nowTeam.getAdmin(), callData.arr.get(i + 1),
                                            weekday, (start / 2) + 9, (start % 2) * 30,
                                            (end / 2) + 9, (end % 2) * 30, posX, posY, null));
                                flag = false;
                            }
                        }
                    }
                }

            }
        }
    }

    public void getTimetable() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 23; j++) {
                arr[i][j] = 0;
            }
        }

        for (int j = 0; j < tt.arr.size(); j += 7) {
            for (int k = 0; k < GlobalVariable.g_nowTeam.getMembers().size(); k++) {
                if (tt.arr.get(j).equals(GlobalVariable.g_nowTeam.getMembers().get(k).getPhoneNum())) {

                    // 각 줄의 요일, 시작시간, 종료시간을 받음
                    String weekday = tt.arr.get(2 + j).toString();
                    int startHour = Integer.parseInt(tt.arr.get(3 + j));
                    int startMin = Integer.parseInt(tt.arr.get(4 + j));
                    int endHour = Integer.parseInt(tt.arr.get(5 + j));
                    int endMin = Integer.parseInt(tt.arr.get(6 + j));
                    int day = 0, num1, num2;
                    switch (weekday) {
                        case "monday":
                            day = 0;
                            break;
                        case "tuesday":
                            day = 1;
                            break;
                        case "wednesday":
                            day = 2;
                            break;
                        case "thursday":
                            day = 3;
                            break;
                        case "friday":
                            day = 4;
                            break;
                        case "saturday":
                            day = 5;
                            break;
                        case "sunday":
                            day = 6;
                            break;
                    }
                    num1 = 2 * (startHour - 9);
                    if (startMin == 30)
                        num1++;
                    num2 = 2 * (endHour - 9);
                    if (endMin == 30)
                        num2++;
                    for (int i = num1; i < num2; i++)
                        arr[day][i] = 1;
                }
            }
        }
    }

    class RegisterPlace extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(FourthActivity.this,
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
