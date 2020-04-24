package com.example.lab2_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    String appleURL = "https://financialmodelingprep.com/api/company/price/AAPL";
    String googleURL = "https://financialmodelingprep.com/api/company/price/GOOGL";
    String faceBookURL = "https://financialmodelingprep.com/api/company/price/FB";
    String nokiaURL = "https://financialmodelingprep.com/api/company/price/NOK";

    String htmlText;

    private ArrayList<String> prices = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> stocks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        names.add("Apple");
        names.add("Google");
        names.add("Facebook");
        names.add("Nokia");

        apiCall(appleURL);
        apiCall(googleURL);
        apiCall(faceBookURL);
        apiCall(nokiaURL);

        for (int i = 0; i < 4; i++) {
            stocks.add(names.get(i) + ": " + prices.get(i));
        }

        arrayAdapt();
    }

    private void apiCall(String url) {
        loadFromWeb(url);

        int priceIndex = htmlText.indexOf("price") + 8;

        Log.d("mytag", "es: " + htmlText.substring(priceIndex, htmlText.indexOf(" ", priceIndex)));

        prices.add(htmlText.substring(priceIndex, htmlText.indexOf(" ", priceIndex)));
    }

    private void arrayAdapt() {
        final ArrayAdapter<String> aa;
        aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stocks);
        listView.setAdapter(aa);
    }

    protected void loadFromWeb(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            htmlText = fromStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String fromStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append(newLine);
        }
        return out.toString();
    }
}