package com.example.lab4;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

public class SomeService extends Service {
    static final String TAG = "SOME_SERVICE";
    static final int MESSAGE = 1;
    class IncomingHandler extends Handler{
        public void handleMessage(Message msg){
            switch(msg.what){
                case MESSAGE:
                    String message = msg.getData().getString("MESSAGE");
                    Messenger reply = msg.replyTo;
                    Message response = Message.obtain(null, MESSAGE, 0, 0);
                    Bundle responseBundle = new Bundle();
                    responseBundle.putString("RESPONSE", "RESPONSE TO: " + message);
                    response.setData(responseBundle);

                    try {
                        reply.send(response);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
    final Messenger messenger = new Messenger(new IncomingHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "Service is running.");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "Service is destroyed.");
    }
}