package com.example.gridview;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id){
                // Send intent to SingleViewActivity
                Intent i = new Intent(getApplicationContext(), SingleViewActivity.class);
                // Pass image index
                i.putExtra("id", position);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                ArrayList<String> iconNames = new ArrayList<String>();
                String json = prefs.getString("iconsArray", null);

                if (json != null) {
                    try {
                        JSONArray a = new JSONArray(json);
                        for (int j = 0; j < a.length(); j++) {
                            String url = a.optString(j);
                            iconNames.add(url);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{

                    iconNames = new ArrayList<>(Arrays.asList("Contact Card Icon", "Paint Brush Icon",
                            "Carpenter Saw Icon", "Video Camera Icon",
                            "Bus on Time Icon", "Credit Card Icon",
                            "Paint Palette Icon", "Corona Virus Icon",
                            "Car Seat Icon", "Speaker Icon"));

                    SharedPreferences.Editor editor = prefs.edit();
                    JSONArray a = new JSONArray();

                    for(int k = 0; k < iconNames.size(); k++) {
                        a.put(iconNames.get(k));
                    }
                    if (!iconNames.isEmpty()) {
                        editor.putString("iconsArray", a.toString());
                    } else {
                        editor.putString("iconsArray", null);
                    }
                    editor.commit();
                }

                i.putExtra("name", iconNames.get(position));
                startActivity(i);
            }
        });

        final Button map = (Button) findViewById(R.id.map);


        map.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ConfigureActivity.class);
                startActivity(i);
            }
        });
    }
}