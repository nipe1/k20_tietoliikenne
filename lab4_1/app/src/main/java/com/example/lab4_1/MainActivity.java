package com.example.lab4_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> leagues = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();
    ListView listView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.test);
        getJSON();
        arrayAdapt();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                String val = listView.getItemAtPosition(position).toString();
                String areaID = ids.get(leagues.indexOf(val));
                textView.setText(areaID);
                Intent intent = new Intent(MainActivity.this, CompetitionActivity.class);

                intent.putExtra("message", areaID);
                //based on item add info to intent
                //startActivity(intent);
            }
        });
    }

    private void arrayAdapt() {
        final ArrayAdapter<String> aa;
        aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, leagues);
        listView.setAdapter(aa);
    }

    private void getJSON() {
        String url = "https://api.football-data.org/v2/areas";

        RequestQueue queue = Volley.newRequestQueue(this);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray competitions = response.getJSONArray("areas");

                            for (int i = 0; i < competitions.length(); i++) {
                                JSONObject val = competitions.getJSONObject(i);
                                leagues.add(val.get("name").toString());
                                ids.add(val.get("id").toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        queue.add(jsonObjectRequest);
    }
}
