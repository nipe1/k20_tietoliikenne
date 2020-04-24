package com.example.lab2_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    URL myURL = null;
    EditText editAddress;
    TextView textFetch;
    String fetchText;
    InputStream in;
    StringBuilder sb;
    BufferedReader r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editAddress = (EditText) findViewById(R.id.editAddress);
        textFetch = (TextView) findViewById(R.id.textFetch);
    }


    public void buttonGo(View v) throws IOException {
        DownloadFilesTask task=new DownloadFilesTask();
        task.execute(editAddress.getText().toString());
    }

    private class DownloadFilesTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings){
            try{
                myURL = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) myURL.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.connect();
                in = urlConnection.getInputStream();
                //in = new BufferedInputStream(urlConnection.getInputStream());
                sb = new StringBuilder();
                r = new BufferedReader(new InputStreamReader(in), 1000);
                for (String line = r.readLine(); line != null; line = r.readLine()) {
                    sb.append(line);
                }
                fetchText = sb.toString();
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();

            }
            return fetchText;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected void onPostExecute(String text) {
            textFetch.setText(text);
        }
    }
}


