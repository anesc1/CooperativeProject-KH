package com.example.gogoooma.cooperativeproject;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AddAgenda extends AppCompatActivity {
    ArrayList<String> projectName;
    Integer projectNum,check;
    String projectName1, projectName2, projectName3, projectName4, projectName5, projectName6, projectName7;
    String prj1start,prj1end,prj2start,prj2end,prj3start,prj3end,prj4start,prj4end,prj5start,prj5end,prj6start,prj6end,prj7start,prj7end;
    ArrayList<String> agenda;
    RegisterProject insert;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agenda);
        Intent i = getIntent();
        projectNum = i.getIntExtra("projectNum", -1);
        projectName1 = i.getStringExtra("projectName1");
        projectName2 = i.getStringExtra("projectName2");
        projectName3 = i.getStringExtra("projectName3");
        projectName4 = i.getStringExtra("projectName4");
        projectName5 = i.getStringExtra("projectName5");
        projectName6 = i.getStringExtra("projectName6");
        projectName7 = i.getStringExtra("projectName7");
        projectName = new ArrayList<>();
        projectName.add(projectName1);
        projectName.add(projectName2);
        projectName.add(projectName3);
        projectName.add(projectName4);
        projectName.add(projectName5);
        projectName.add(projectName6);
        projectName.add(projectName7);


        TextView prj1 = (TextView) findViewById(R.id.prj1);
        Button prj1btn = (Button) findViewById(R.id.prj1btn);
        Button prj1btn_ = (Button) findViewById(R.id.prj1btn_);
        TextView prj1agd = (TextView) findViewById(R.id.prj1agd);
        TextView prj2 = (TextView) findViewById(R.id.prj2);
        Button prj2btn = (Button) findViewById(R.id.prj2btn);
        Button prj2btn_ = (Button) findViewById(R.id.prj2btn_);
        TextView prj2agd = (TextView) findViewById(R.id.prj2agd);
        TextView prj3 = (TextView) findViewById(R.id.prj3);
        Button prj3btn = (Button) findViewById(R.id.prj3btn);
        Button prj3btn_ = (Button) findViewById(R.id.prj3btn_);
        TextView prj3agd = (TextView) findViewById(R.id.prj3agd);
        TextView prj4 = (TextView) findViewById(R.id.prj4);
        Button prj4btn = (Button) findViewById(R.id.prj4btn);
        Button prj4btn_ = (Button) findViewById(R.id.prj4btn_);
        TextView prj4agd = (TextView) findViewById(R.id.prj4agd);
        TextView prj5 = (TextView) findViewById(R.id.prj5);
        Button prj5btn = (Button) findViewById(R.id.prj5btn);
        Button prj5btn_ = (Button) findViewById(R.id.prj5btn_);
        TextView prj5agd = (TextView) findViewById(R.id.prj5agd);
        TextView prj6 = (TextView) findViewById(R.id.prj6);
        Button prj6btn = (Button) findViewById(R.id.prj6btn);
        Button prj6btn_ = (Button) findViewById(R.id.prj6btn_);
        TextView prj6agd = (TextView) findViewById(R.id.prj6agd);
        TextView prj7 = (TextView) findViewById(R.id.prj7);
        Button prj7btn = (Button) findViewById(R.id.prj7btn);
        Button prj7btn_ = (Button) findViewById(R.id.prj7btn_);
        TextView prj7agd = (TextView) findViewById(R.id.prj7agd);

        prj1.setText(projectName1);
        prj2.setText(projectName2);
        prj3.setText(projectName3);
        prj4.setText(projectName4);
        prj5.setText(projectName5);
        prj6.setText(projectName6);
        prj7.setText(projectName7);

        if (projectNum == 3) {
            prj4.setVisibility(View.INVISIBLE);
            prj4btn.setVisibility(View.INVISIBLE);
            prj4btn_.setVisibility(View.INVISIBLE);
            prj4agd.setVisibility(View.INVISIBLE);
            prj5.setVisibility(View.INVISIBLE);
            prj5btn.setVisibility(View.INVISIBLE);
            prj5btn_.setVisibility(View.INVISIBLE);
            prj5agd.setVisibility(View.INVISIBLE);
            prj6.setVisibility(View.INVISIBLE);
            prj6btn.setVisibility(View.INVISIBLE);
            prj6btn_.setVisibility(View.INVISIBLE);
            prj6agd.setVisibility(View.INVISIBLE);
            prj7.setVisibility(View.INVISIBLE);
            prj7btn.setVisibility(View.INVISIBLE);
            prj7btn_.setVisibility(View.INVISIBLE);
            prj7agd.setVisibility(View.INVISIBLE);
        } else if (projectNum == 4) {
            prj5.setVisibility(View.INVISIBLE);
            prj5btn.setVisibility(View.INVISIBLE);
            prj5btn_.setVisibility(View.INVISIBLE);
            prj5agd.setVisibility(View.INVISIBLE);
            prj6.setVisibility(View.INVISIBLE);
            prj6btn.setVisibility(View.INVISIBLE);
            prj6btn_.setVisibility(View.INVISIBLE);
            prj6agd.setVisibility(View.INVISIBLE);
            prj7.setVisibility(View.INVISIBLE);
            prj7btn.setVisibility(View.INVISIBLE);
            prj7btn_.setVisibility(View.INVISIBLE);
            prj7agd.setVisibility(View.INVISIBLE);
        } else if (projectNum == 5) {
            prj6.setVisibility(View.INVISIBLE);
            prj6btn.setVisibility(View.INVISIBLE);
            prj6btn_.setVisibility(View.INVISIBLE);
            prj6agd.setVisibility(View.INVISIBLE);
            prj7.setVisibility(View.INVISIBLE);
            prj7btn.setVisibility(View.INVISIBLE);
            prj7btn_.setVisibility(View.INVISIBLE);
            prj7agd.setVisibility(View.INVISIBLE);
        } else if (projectNum == 6) {
            prj7.setVisibility(View.INVISIBLE);
            prj7btn.setVisibility(View.INVISIBLE);
            prj7btn_.setVisibility(View.INVISIBLE);
            prj7agd.setVisibility(View.INVISIBLE);
        }





    }


    public void prj1start(View view) {
        check =1;
        DatePickerDialog dialog = new DatePickerDialog(AddAgenda.this, listener, 2018, 05, 30);
        dialog.show();
    }

    public void prj1end(View view) {
    check = 2;
        DatePickerDialog dialog = new DatePickerDialog(AddAgenda.this, listener, 2018, 05, 30);
        dialog.show();
    }

    public void prj2start(View view) {
        check =3;
        DatePickerDialog dialog = new DatePickerDialog(AddAgenda.this, listener, 2018, 05, 30);
        dialog.show();
    }

    public void prj2end(View view) {
        check = 4;
        DatePickerDialog dialog = new DatePickerDialog(AddAgenda.this, listener, 2018, 05, 30);
        dialog.show();
    }

    public void prj3start(View view) {
        check =5;
        DatePickerDialog dialog = new DatePickerDialog(AddAgenda.this, listener, 2018, 05, 30);
        dialog.show();
    }

    public void prj3end(View view) {
        check = 6;
        DatePickerDialog dialog = new DatePickerDialog(AddAgenda.this, listener, 2018, 05, 30);
        dialog.show();
    }

    public void prj4start(View view) {
        check =7;
        DatePickerDialog dialog = new DatePickerDialog(AddAgenda.this, listener, 2018, 05, 30);
        dialog.show();
    }

    public void prj4end(View view) {
        check = 8;
        DatePickerDialog dialog = new DatePickerDialog(AddAgenda.this, listener, 2018, 05, 30);
        dialog.show();
    }

    public void prj5start(View view) {
        check =9;
        DatePickerDialog dialog = new DatePickerDialog(AddAgenda.this, listener, 2018, 05, 30);
        dialog.show();
    }

    public void prj5end(View view) {
        check = 10;
        DatePickerDialog dialog = new DatePickerDialog(AddAgenda.this, listener, 2018, 05, 30);
        dialog.show();
    }

    public void prj6start(View view) {
        check = 11;
        DatePickerDialog dialog = new DatePickerDialog(AddAgenda.this, listener, 2018, 05, 30);
        dialog.show();
    }

    public void prj6end(View view) {
        check = 12;
        DatePickerDialog dialog = new DatePickerDialog(AddAgenda.this, listener, 2018, 05, 30);
        dialog.show();
    }

    public void prj7start(View view) {
        check =13;
        DatePickerDialog dialog = new DatePickerDialog(AddAgenda.this, listener, 2018, 05, 30);
        dialog.show();
    }

    public void prj7end(View view) {
        check = 14;
        DatePickerDialog dialog = new DatePickerDialog(AddAgenda.this, listener, 2018, 05, 30);
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            if(check.equals(1))
            {
                if((month/10)==0 && (dayOfMonth/10)==0)
                {
                    prj1start = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)==0 && (dayOfMonth/10)!=0)
                {
                    prj1start = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)==0)
                {
                    prj1start = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)!=0)
                {
                    prj1start = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }
            }else if(check.equals(2))
            {
                if((month/10)==0 && (dayOfMonth/10)==0)
                {
                    prj1end = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)==0 && (dayOfMonth/10)!=0)
                {
                    prj1end = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)==0)
                {
                    prj1end = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)!=0)
                {
                    prj1end = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }
            }else if(check.equals(3))
            {
                if((month/10)==0 && (dayOfMonth/10)==0)
                {
                    prj2start = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)==0 && (dayOfMonth/10)!=0)
                {
                    prj2start = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)==0)
                {
                    prj2start = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)!=0)
                {
                    prj2start = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }
            }else if(check.equals(4))
            {
                if((month/10)==0 && (dayOfMonth/10)==0)
                {
                    prj2end = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)==0 && (dayOfMonth/10)!=0)
                {
                    prj2end = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)==0)
                {
                    prj2end = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)!=0)
                {
                    prj2end = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }
            }else if(check.equals(5))
            {
                if((month/10)==0 && (dayOfMonth/10)==0)
                {
                    prj3start = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)==0 && (dayOfMonth/10)!=0)
                {
                    prj3start = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)==0)
                {
                    prj3start = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)!=0)
                {
                    prj3start = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }
            }else if(check.equals(6))
            {
                if((month/10)==0 && (dayOfMonth/10)==0)
                {
                    prj3end = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)==0 && (dayOfMonth/10)!=0)
                {
                    prj3end = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)==0)
                {
                    prj3end = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)!=0)
                {
                    prj3end = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }
            }else if(check.equals(7))
            {
                if((month/10)==0 && (dayOfMonth/10)==0)
                {
                    prj4start = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)==0 && (dayOfMonth/10)!=0)
                {
                    prj4start = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)==0)
                {
                    prj4start = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)!=0)
                {
                    prj4start = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }
            }else if(check.equals(8))
            {
                if((month/10)==0 && (dayOfMonth/10)==0)
                {
                    prj4end = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)==0 && (dayOfMonth/10)!=0)
                {
                    prj4end = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)==0)
                {
                    prj4end = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)!=0)
                {
                    prj4end = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }
            }else if(check.equals(9))
            {
                if((month/10)==0 && (dayOfMonth/10)==0)
                {
                    prj5start = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)==0 && (dayOfMonth/10)!=0)
                {
                    prj5start = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)==0)
                {
                    prj5start = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)!=0)
                {
                    prj5start = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }
            }else if(check.equals(10))
            {
                if((month/10)==0 && (dayOfMonth/10)==0)
                {
                    prj5end = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)==0 && (dayOfMonth/10)!=0)
                {
                    prj5end = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)==0)
                {
                    prj5end = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)!=0)
                {
                    prj5end = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }
            }else if(check.equals(11))
            {
                if((month/10)==0 && (dayOfMonth/10)==0)
                {
                    prj6start = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)==0 && (dayOfMonth/10)!=0)
                {
                    prj6start = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)==0)
                {
                    prj6start = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)!=0)
                {
                    prj6start = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }
            }else if(check.equals(12))
            {
                if((month/10)==0 && (dayOfMonth/10)==0)
                {
                    prj6end = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)==0 && (dayOfMonth/10)!=0)
                {
                    prj6end = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)==0)
                {
                    prj6end = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)!=0)
                {
                    prj6end = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }
            }else if(check.equals(13))
            {
                if((month/10)==0 && (dayOfMonth/10)==0)
                {
                    prj7start = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)==0 && (dayOfMonth/10)!=0)
                {
                    prj7start = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)==0)
                {
                    prj7start = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)!=0)
                {
                    prj7start = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }
            }else if(check.equals(14))
            {
                if((month/10)==0 && (dayOfMonth/10)==0)
                {
                    prj7end = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)==0 && (dayOfMonth/10)!=0)
                {
                    prj7end = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)==0)
                {
                    prj7end = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth);
                }else if((month/10)!=0 && (dayOfMonth/10)!=0)
                {
                    prj7end = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                }
            }

        }
    };

    public void agendaSave(View view) {
        agenda = new ArrayList<>();
        String agenda1 = prj1start+"/"+prj1end;
        String agenda2 = prj2start+"/"+prj2end;
        String agenda3 = prj3start+"/"+prj3end;
        String agenda4 = prj4start+"/"+prj4end;
        String agenda5 = prj5start+"/"+prj5end;
        String agenda6 = prj6start+"/"+prj6end;
        String agenda7 = prj7start+"/"+prj7end;
        agenda.add(agenda1);
        agenda.add(agenda2);
        agenda.add(agenda3);
        agenda.add(agenda4);
        agenda.add(agenda5);
        agenda.add(agenda6);
        agenda.add(agenda7);

        for (int j = 0; j < projectNum; j++) {
            String temp = String.valueOf(j);
            insert = new AddAgenda.RegisterProject();
            insert.execute(projectName.get(j).toString(), temp, String.valueOf(GlobalVariable.g_nowTeam.getTeamNum()), agenda.get(j).toString());
            GlobalVariable.g_project.add(new Project(projectName.get(j).toString(), j, agenda.get(j).toString(), GlobalVariable.g_nowTeam.getTeamNum()));
          }
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }


    class RegisterProject extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(AddAgenda.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
        }


        @Override
        protected String doInBackground(String... params) {

            String phpprojectName = (String) params[0];
            String phpprojectNum = (String) params[1];
            String phpteamNum = (String) params[2];
            String phpagenda = (String) params[3];

            String serverURL = "http://anesc1.cafe24.com/projectup.php";
            String postParameters = "&projectName=" + phpprojectName + "&projectNum=" + phpprojectNum + "&teamNum=" + phpteamNum + "&agenda=" + phpagenda;


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
