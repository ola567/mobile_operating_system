package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView number1;
    private TextView number2;
    private Button addButton;
    private Button substractButton;
    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        number1 = (TextView) findViewById(R.id.textView3);
        number2 = (TextView) findViewById(R.id.textView4);
        addButton = (Button) findViewById(R.id.button2);
        substractButton = (Button) findViewById(R.id.button3);

        Intent intent = getIntent();
        int n2 = intent.getIntExtra("number1", 0);
        int n1 = intent.getIntExtra("number2", 0);

        number1.setText("Number 1: " + n1);
        number2.setText("Number 2: " + n2);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = n1 + n2;
                result_action();
            }
        });

        substractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = n1 - n2;
                result_action();
            }
        });
    }
    public void result_action(){
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra("result", result);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}