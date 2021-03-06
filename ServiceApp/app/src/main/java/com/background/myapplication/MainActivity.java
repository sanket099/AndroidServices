package com.background.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText editTextInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextInput = findViewById(R.id.edit_text_input);
    }
    public void startService(View v) {
        String input = editTextInput.getText().toString();
        Intent serviceIntent = new Intent(this, MyService.class); //to start the service
        serviceIntent.putExtra("inputExtra", input);

        //startService(serviceIntent);
        ContextCompat.startForegroundService(this, serviceIntent); //start intent service just like this

        //MyJobIntentService.enqueueWork(this, serviceIntent); // start job intent service
    }

    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, MyService.class);
        stopService(serviceIntent);
    }


}
