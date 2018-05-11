package com.example.gogoooma.cooperativeproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by anesc on 2018-04-06.
 */

// 등희 어깨 듬직보스
public class CallData extends AppCompatActivity {
    TextView getData;
    phpDown get;
    ArrayList<String> arr;


    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.calldata);
        arr = new ArrayList<>();
        getData = (TextView) findViewById(R.id.getdata);

        get = new phpDown();
        get.execute("http://anesc1.cafe24.com/signdown.php").toString();

    }


    private class phpDown extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            String regex1 = "\\<.*?\\>";
            String regex2 = "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>";

            try {
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 연결되었으면.
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    // 연결되었음 코드가 리턴되면.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                        // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
//                        String line = br.readLine().replaceAll("<br>", ",");
//                        StringTokenizer st = new StringTokenizer(line, ",");
//                        while (st.hasMoreTokens()) {
//                            String str = st.nextToken();
//                            jsonHtml.append(str+"\n");
//                            arr.add(str);
//                        }
//
////                            if(line == null) break;
////                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
////                            jsonHtml.append(line);
                        String line;
                         while((line=br.readLine().replaceAll("<br>", ","))!=null) {
                              jsonHtml.append(line);
                             StringTokenizer st = new StringTokenizer(jsonHtml.toString(), ",");
                             while (st.hasMoreTokens()) {
                                 String str = st.nextToken();
                                if(str!=null)
                                    arr.add(str);
                             }

                         }

                        br.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return jsonHtml.toString();
        }

        protected void onPostExecute(String str) {
            getData.setText(str);
            Toast.makeText(CallData.this, arr.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
