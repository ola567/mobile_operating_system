package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button startButton;
    private Button nextActivity;
    private Button stopButton;
    private TextView serviceStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        nextActivity = (Button) findViewById(R.id.nextActivity);
        serviceStatus = (TextView) findViewById(R.id.serviceStatus);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startButtonPressed();
            }
        });
        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivityPressed();
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(MainActivity.this, SomeService.class));
                serviceStatus.setText("Service stopped");
            }
        });
    }
    public void startButtonPressed(){
        Intent intent = new Intent(this, SomeService.class);
        startService(intent);
        serviceStatus.setText("Service running");
    }
    public void nextActivityPressed() {
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }
}