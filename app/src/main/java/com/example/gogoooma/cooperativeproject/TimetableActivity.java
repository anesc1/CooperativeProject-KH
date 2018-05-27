package com.example.gogoooma.cooperativeproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TimetableActivity extends AppCompatActivity {
    Spinner Weekday;
    Spinner Starttime;
    Spinner Endtime;
    EditText edittext;

    String weekday;
    String st;
    String et;

    int starttime;
    int endtime;

    TextView monday[] = new TextView[12];
    TextView tuesday[] = new TextView[12];
    TextView wednesday[] = new TextView[12];
    TextView thursday[] = new TextView[12];
    TextView friday[] = new TextView[12];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        init();
        createArray();
    }

    public void init() {
        Weekday = (Spinner) findViewById(R.id.selectDay);
        Starttime = (Spinner) findViewById(R.id.selectStartTime);
        Endtime = (Spinner) findViewById(R.id.selectEndTime);
        edittext = (EditText) findViewById(R.id.editText);

        Weekday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                weekday = Weekday.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Starttime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                st = Starttime.getSelectedItem().toString();
                starttime = st.charAt(0) - 48;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Endtime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et = Endtime.getSelectedItem().toString();
                endtime = et.charAt(0) - 48;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void btnClick(View view) {
        int start = findIndex(starttime);
        int end = findIndex(endtime);
        String todo = edittext.getText().toString();

        switch (weekday) {
            case "monday":
                for (int i = start; i <= end; i++) {
                    monday[i].setText(todo);
                    monday[i].setBackgroundColor(view.getResources().getColor(R.color.colorPrimary));
                }
                break;
            case "tuesday":
                for (int i = start; i <= end; i++) {
                    tuesday[i].setText(todo);
                    tuesday[i].setBackgroundColor(view.getResources().getColor(R.color.colorPrimary));
                }
                break;
            case "wednesday":
                for (int i = start; i <= end; i++) {
                    wednesday[i].setText(todo);
                    wednesday[i].setBackgroundColor(view.getResources().getColor(R.color.colorPrimary));
                }
                break;
            case "thursday":
                for (int i = start; i <= end; i++) {
                    thursday[i].setText(todo);
                    thursday[i].setBackgroundColor(view.getResources().getColor(R.color.colorPrimary));
                }
                break;
            case "friday":
                for (int i = start; i <= end; i++) {
                    friday[i].setText(todo);
                    friday[i].setBackgroundColor(view.getResources().getColor(R.color.colorPrimary));
                }
                break;
        }
    }

    public int findIndex(int time) {
        int ind = 0;

        switch (time) {
            case 9:
                ind = 0;
                break;
            case 10:
                ind = 1;
                break;
            case 11:
                ind = 2;
                break;
            case 12:
                ind = 3;
                break;
            case 1:
                ind = 4;
                break;
            case 2:
                ind = 5;
                break;
            case 3:
                ind = 6;
                break;
            case 4:
                ind = 7;
                break;
            case 5:
                ind = 8;
                break;
            case 6:
                ind = 9;
                break;
            case 7:
                ind = 10;
                break;
            case 8:
                ind = 11;
                break;
        }
        return ind;
    }

    public void createArray() {
        monday[0] = (TextView) findViewById(R.id.monday9);
        monday[1] = (TextView) findViewById(R.id.monday10);
        monday[2] = (TextView) findViewById(R.id.monday11);
        monday[3] = (TextView) findViewById(R.id.monday12);
        monday[4] = (TextView) findViewById(R.id.monday1);
        monday[5] = (TextView) findViewById(R.id.monday2);
        monday[6] = (TextView) findViewById(R.id.monday3);
        monday[7] = (TextView) findViewById(R.id.monday4);
        monday[8] = (TextView) findViewById(R.id.monday5);
        monday[9] = (TextView) findViewById(R.id.monday6);
        monday[10] = (TextView) findViewById(R.id.monday7);
        monday[11] = (TextView) findViewById(R.id.monday8);

        tuesday[0] = (TextView) findViewById(R.id.tuesday9);
        tuesday[1] = (TextView) findViewById(R.id.tuesday10);
        tuesday[2] = (TextView) findViewById(R.id.tuesday11);
        tuesday[3] = (TextView) findViewById(R.id.tuesday12);
        tuesday[4] = (TextView) findViewById(R.id.tuesday1);
        tuesday[5] = (TextView) findViewById(R.id.tuesday2);
        tuesday[6] = (TextView) findViewById(R.id.tuesday3);
        tuesday[7] = (TextView) findViewById(R.id.tuesday4);
        tuesday[8] = (TextView) findViewById(R.id.tuesday5);
        tuesday[9] = (TextView) findViewById(R.id.tuesday6);
        tuesday[10] = (TextView) findViewById(R.id.tuesday7);
        tuesday[11] = (TextView) findViewById(R.id.tuesday8);

        wednesday[0] = (TextView) findViewById(R.id.wednesday9);
        wednesday[1] = (TextView) findViewById(R.id.wednesday10);
        wednesday[2] = (TextView) findViewById(R.id.wednesday11);
        wednesday[3] = (TextView) findViewById(R.id.wednesday12);
        wednesday[4] = (TextView) findViewById(R.id.wednesday1);
        wednesday[5] = (TextView) findViewById(R.id.wednesday2);
        wednesday[6] = (TextView) findViewById(R.id.wednesday3);
        wednesday[7] = (TextView) findViewById(R.id.wednesday4);
        wednesday[8] = (TextView) findViewById(R.id.wednesday5);
        wednesday[9] = (TextView) findViewById(R.id.wednesday6);
        wednesday[10] = (TextView) findViewById(R.id.wednesday7);
        wednesday[11] = (TextView) findViewById(R.id.wednesday8);

        thursday[0] = (TextView) findViewById(R.id.thursday9);
        thursday[1] = (TextView) findViewById(R.id.thursday10);
        thursday[2] = (TextView) findViewById(R.id.thursday11);
        thursday[3] = (TextView) findViewById(R.id.thursday12);
        thursday[4] = (TextView) findViewById(R.id.thursday1);
        thursday[5] = (TextView) findViewById(R.id.thursday2);
        thursday[6] = (TextView) findViewById(R.id.thursday3);
        thursday[7] = (TextView) findViewById(R.id.thursday4);
        thursday[8] = (TextView) findViewById(R.id.thursday5);
        thursday[9] = (TextView) findViewById(R.id.thursday6);
        thursday[10] = (TextView) findViewById(R.id.thursday7);
        thursday[11] = (TextView) findViewById(R.id.thursday8);

        friday[0] = (TextView) findViewById(R.id.friday9);
        friday[1] = (TextView) findViewById(R.id.friday10);
        friday[2] = (TextView) findViewById(R.id.friday11);
        friday[3] = (TextView) findViewById(R.id.friday12);
        friday[4] = (TextView) findViewById(R.id.friday1);
        friday[5] = (TextView) findViewById(R.id.friday2);
        friday[6] = (TextView) findViewById(R.id.friday3);
        friday[7] = (TextView) findViewById(R.id.friday4);
        friday[8] = (TextView) findViewById(R.id.friday5);
        friday[9] = (TextView) findViewById(R.id.friday6);
        friday[10] = (TextView) findViewById(R.id.friday7);
        friday[11] = (TextView) findViewById(R.id.friday8);

    }

    public void setting(TextView[] monday, TextView[] tuesday, TextView[] wednesday, TextView[] thursday, TextView[] friday, Context context) {
        for (int i = 0; i < 12; i++) {
            if (!this.monday[i].equals("")) {
                monday[i].setText(this.monday[i].toString());
                monday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }

            if (!this.tuesday[i].equals("")) {
                monday[i].setText(this.tuesday[i].toString());
                monday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }

            if (!this.wednesday[i].equals("")) {
                wednesday[i].setText(this.wednesday[i].toString());
                wednesday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }

            if (!this.thursday[i].equals("")) {
                thursday[i].setText(this.thursday[i].toString());
                thursday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }

            if (!this.friday[i].equals("")) {
                friday[i].setText(this.friday[i].toString());
                friday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }
        }
    }
}
