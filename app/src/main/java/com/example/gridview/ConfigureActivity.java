package com.example.gridview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class ConfigureActivity extends AppCompatActivity {
    public static final int OPEN_NEW_ACTIVITY = 123456;
    private Spinner spinner;
    private EditText ed;
    private static final String[] paths = {"item 1", "item 2", "item 3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);
        spinner = (Spinner)findViewById(R.id.spinner1);
        ed = (EditText) findViewById(R.id.newText);
        ArrayList<String> iconNames = new ArrayList<String>();
        iconNames = getStringArrayPref("iconsArray");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ConfigureActivity.this,
                android.R.layout.simple_spinner_item,iconNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        final Button save = (Button) findViewById(R.id.save);


        ArrayList<String> finalIconNames = iconNames;
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String newText = ed.getText().toString();
                String oldText = spinner.getSelectedItem().toString();
                int index = finalIconNames.indexOf(oldText);
                finalIconNames.set(index, newText);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                JSONArray a = new JSONArray();

                for(int i = 0; i < finalIconNames.size(); i++) {
                    a.put(finalIconNames.get(i));
                }
                if (!finalIconNames.isEmpty()) {
                    editor.putString("iconsArray", a.toString());
                } else {
                    editor.putString("iconsArray", null);
                }
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, OPEN_NEW_ACTIVITY);
            }
        });
    }
    public ArrayList<String> getStringArrayPref(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String json = prefs.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }
}