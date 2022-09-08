package com.example.gridview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class SingleViewActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    TextToSpeech mTts;
    private static final int MY_DATA_CHECK_CODE = 0;
    String name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_single_view);

        // check whether TTS resources are available on the device
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
        super.onCreate(savedInstanceState);


        // Get intent data
        Intent i = getIntent();

        // Selected image id
        int position = i.getExtras().getInt("id");
        name = i.getExtras().getString("name");
        ImageAdapter imageAdapter = new ImageAdapter(this);

        ImageView imageView = (ImageView) findViewById(R.id.SingleView);
        imageView.setImageResource(imageAdapter.mThumbIds[position]);
        TextView txtView = (TextView) findViewById(R.id.name);
        txtView.setText(name);
        final Button button = (Button) findViewById(R.id.back);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });


    }


    @Override
    public void onInit(int i) {
        // called as soon as the TextToSpeech instance is intialized
        // set the locale
        mTts.setLanguage(Locale.US);
        mTts.speak(name, TextToSpeech.QUEUE_ADD, null);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==MY_DATA_CHECK_CODE) {
            if(resultCode==TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                // if TTS resources are available you instanciate your TextToSpeech object
                mTts = new TextToSpeech(this, this);
            } else {
                // else you ask the system to install it
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    }
