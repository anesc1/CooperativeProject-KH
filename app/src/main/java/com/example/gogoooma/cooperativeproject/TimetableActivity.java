package com.example.gogoooma.cooperativeproject;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimetableActivity extends AppCompatActivity {
    Spinner Weekday;
    EditText edittext;

    Boolean isStart = true;
    String weekday;
    String st;
    String et;

    int startHour;
    int startMin;
    int endHour;
    int endMin;

    TextView monday[] = new TextView[12];
    TextView tuesday[] = new TextView[12];
    TextView wednesday[] = new TextView[12];
    TextView thursday[] = new TextView[12];
    TextView friday[] = new TextView[12];
    TextView saturday[] = new TextView[12];
    TextView sunday[] = new TextView[12];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        init();
        createArray();

    }


    public void init() {
        Weekday = (Spinner) findViewById(R.id.selectDay);
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
    }

    public void btnClick(View view) {
        if(startHour > 12) startHour -= 12;
        if(endHour > 12) endHour -= 12;
        int start = findIndex(startHour);
        int end = findIndex(endHour);

        String todo = edittext.getText().toString();

        switch (weekday) {
            case "monday":
                for (int i = start; i <= end; i++) {
                    monday[i].setText(todo);
                    monday[i].setBackgroundColor(Color.rgb(153, 102, 204));
                }
                break;
            case "tuesday":
                for (int i = start; i <= end; i++) {
                    tuesday[i].setText(todo);
                    tuesday[i].setBackgroundColor(Color.rgb(195, 205, 230));
                }
                break;
            case "wednesday":
                for (int i = start; i <= end; i++) {
                    wednesday[i].setText(todo);
                    wednesday[i].setBackgroundColor(Color.rgb(153, 102, 204));
                }
                break;
            case "thursday":
                for (int i = start; i <= end; i++) {
                    thursday[i].setText(todo);
                    thursday[i].setBackgroundColor(Color.rgb(195, 205, 230));
                }
                break;
            case "friday":
                for (int i = start; i <= end; i++) {
                    friday[i].setText(todo);
                    friday[i].setBackgroundColor(Color.rgb(153, 102, 204));
                }
                break;
            case "saturday":
                for (int i = start; i <= end; i++) {
                    saturday[i].setText(todo);
                    saturday[i].setBackgroundColor(Color.rgb(195, 205, 230));
                }
                break;
            case "sunday":
                for (int i = start; i <= end; i++) {
                    sunday[i].setText(todo);
                    sunday[i].setBackgroundColor(Color.rgb(153, 102, 204));
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

        saturday[0] = (TextView) findViewById(R.id.saturday9);
        saturday[1] = (TextView) findViewById(R.id.saturday10);
        saturday[2] = (TextView) findViewById(R.id.saturday11);
        saturday[3] = (TextView) findViewById(R.id.saturday12);
        saturday[4] = (TextView) findViewById(R.id.saturday1);
        saturday[5] = (TextView) findViewById(R.id.saturday2);
        saturday[6] = (TextView) findViewById(R.id.saturday3);
        saturday[7] = (TextView) findViewById(R.id.saturday4);
        saturday[8] = (TextView) findViewById(R.id.saturday5);
        saturday[9] = (TextView) findViewById(R.id.saturday6);
        saturday[10] = (TextView) findViewById(R.id.saturday7);
        saturday[11] = (TextView) findViewById(R.id.saturday8);

        sunday[0] = (TextView) findViewById(R.id.sunday9);
        sunday[1] = (TextView) findViewById(R.id.sunday10);
        sunday[2] = (TextView) findViewById(R.id.sunday11);
        sunday[3] = (TextView) findViewById(R.id.sunday12);
        sunday[4] = (TextView) findViewById(R.id.sunday1);
        sunday[5] = (TextView) findViewById(R.id.sunday2);
        sunday[6] = (TextView) findViewById(R.id.sunday3);
        sunday[7] = (TextView) findViewById(R.id.sunday4);
        sunday[8] = (TextView) findViewById(R.id.sunday5);
        sunday[9] = (TextView) findViewById(R.id.sunday6);
        sunday[10] = (TextView) findViewById(R.id.sunday7);
        sunday[11] = (TextView) findViewById(R.id.sunday8);
    }

    public void onClickStart(View view) {
        isStart = true;
        TimePickerDialog dialog = new TimePickerDialog(this, listener, 15, 24, false);
        dialog.show();
    }

    public void onClickEnd(View view) {
        isStart = false;
        TimePickerDialog dialog = new TimePickerDialog(this, listener, 15, 24, false);
        dialog.show();
    }

    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Button button;
            if (isStart) {
                button = (Button) findViewById(R.id.button4);
                startHour = hourOfDay;
                startMin = minute;
            } else {
                button = (Button) findViewById(R.id.button5);
                endHour = hourOfDay;
                endMin = minute;
            }
            String noon = "am";
            if (hourOfDay > 12) {
                noon = "pm";
                hourOfDay -= 12;
            }
            String min = "";
            if (minute == 0)
                min = "00";
            button.setText(noon + hourOfDay + ":" + min);
        }
    };
}
