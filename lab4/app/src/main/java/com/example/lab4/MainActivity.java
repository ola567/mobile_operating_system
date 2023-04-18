package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

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

    static final int MESSAGE = 1;
    private Button startButton;
    private Button stopButton;
    private Button sendButton;
    private EditText clientData;
    private TextView serviceResponse;
    boolean isBound = false;
    Messenger mService = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBound = true;
            mService = new Messenger(service);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            isBound = false;
        }
    };
    private Messenger mMessenger = new Messenger(new IncomingHandler());
    class IncomingHandler extends Handler {
        public void handleMessage(Message msg){
            switch(msg.what){
                case MESSAGE:
                    String message = msg.getData().getString("RESPONSE");
                    serviceResponse.setText(message.toString());
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        sendButton = (Button) findViewById(R.id.sendButton);
        clientData = (EditText) findViewById(R.id.clientData);
        serviceResponse = (TextView) findViewById(R.id.serviceResponse);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startButtonPressed();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBound) {
                    Message msg = Message.obtain(null, MESSAGE, 0, 0);
                    String data = clientData.getText().toString();
                    Bundle dataBundle = new Bundle();
                    dataBundle.putString("MESSAGE", data);
                    msg.setData(dataBundle);
                    msg.replyTo = mMessenger;
                    try {
                        mService.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopButtonPressed();
            }
        });
    }
    public void startButtonPressed(){
        Intent intent = new Intent(this, SomeService.class);
        if (!isBound) {
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
        else {
            Toast.makeText(this, "Already bound", Toast.LENGTH_SHORT).show();
        }
    }
    public void stopButtonPressed(){
        if (isBound) {
            unbindService(serviceConnection);
        }
        else {
            Toast.makeText(this, "No bound", Toast.LENGTH_SHORT).show();
        }
    }
}