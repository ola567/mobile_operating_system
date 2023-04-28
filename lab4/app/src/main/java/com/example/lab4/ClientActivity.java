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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ClientActivity extends AppCompatActivity {

    static final int MESSAGE = 1;
    private EditText clientData;
    private Button sendButton;
    private Button backButton;
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
            if (msg.what == MESSAGE){
                String message = msg.getData().getString("RESPONSE");
                serviceResponse.setText(message.toString());
            }
            else {
                super.handleMessage(msg);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Intent intent = new Intent(this, SomeService.class);
        try {
            bindService(intent, serviceConnection, Context.BIND_EXTERNAL_SERVICE);
        }
        catch (SecurityException e) {
            isBound = false;
            mMessenger = null;
            Toast.makeText(ClientActivity.this, "Service not started", Toast.LENGTH_SHORT).show();
        }


        serviceResponse = (TextView) findViewById(R.id.serviceResponse);
        sendButton = (Button) findViewById(R.id.sendButton);
        backButton = (Button) findViewById(R.id.backButton);
        clientData = (EditText) findViewById(R.id.clientData);

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
                else {
                    Log.d("SOME_SERVICE", "Not bound");
                    Toast.makeText(ClientActivity.this, "Not bound", Toast.LENGTH_SHORT).show();
                }
            }
        });
         backButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 finish();
             }
         });
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }
}