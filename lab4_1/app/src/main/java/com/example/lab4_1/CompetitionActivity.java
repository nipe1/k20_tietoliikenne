package com.example.lab4_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import org.w3c.dom.Text;

import java.util.ArrayList;

public class CompetitionActivity extends AppCompatActivity {

    private ArrayList<String> leagues = new ArrayList<>();
    ListView listView;
    TextView textView;
    String areaID;
    String url = "https://api.football-data.org/v2/competitions?areas=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        areaID = bundle.getString("message");
        listView = (ListView) findViewById(R.id.listView);
        url = url + areaID;
        getJSON();
    }

    private void arrayAdapt() {
        final ArrayAdapter<String> aa;
        aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, leagues);
        listView.setAdapter(aa);
    }

    private void getJSON() {
        RequestQueue queue = Volley.newRequestQueue(this);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray competitions = response.getJSONArray("competitions");

                            for (int i = 0; i < competitions.length(); i++) {
                                JSONObject val = competitions.getJSONObject(i);
                                leagues.add(val.get("name").toString());
                            }
                            arrayAdapt();
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