package com.example.lab1;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class Task extends AsyncTask<Void, Integer, Void> {
    ProgressBar progressBar;
    TextView textView;

    Task(ProgressBar progressBar, TextView textView){
        this.progressBar = progressBar;
        this.textView = textView;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (int i=0; i<50; i++){
            if (isCancelled()){
                break;
            }
            try {
                Thread.sleep(100);
                publishProgress(2*i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        this.progressBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Void results){
        super.onPostExecute(results);
        this.textView.setText("Task finished.");
    }
}
