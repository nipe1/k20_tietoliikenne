package com.example.lab3_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        getJSON();
        arrayAdapt();
    }

    private void arrayAdapt() {
        final ArrayAdapter<String> aa;
        aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, leagues);
        listView.setAdapter(aa);
    }

    private void getJSON(){
        String url = "https://api.football-data.org/v2/competitions?areas=2072";

        RequestQueue queue = Volley.newRequestQueue(this);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray competitions = response.getJSONArray("competitions");

                            for(int i=0;i<competitions.length();i++){
                                JSONObject val = competitions.getJSONObject(i);
                                leagues.add(val.get("name").toString());
                            }
                        } catch (Exception e){
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


    // Access the RequestQueue through your singleton class.
    //MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
}
