package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText userInput;
    private Button insertButton;
    private Button viewButton;
    private ListView list;
    private DatabaseHandler databaseHandler;
    private TextView modificationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = (EditText) findViewById(R.id.editText);
        insertButton = (Button) findViewById(R.id.insertButton);
        viewButton = (Button) findViewById(R.id.viewButton);
        list = (ListView) findViewById(R.id.list);
        modificationData = (TextView) findViewById(R.id.textView);

        // SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("lab3_preferences", Context.MODE_PRIVATE);
        String date = sharedPreferences.getString("date", "23023-2343");
        modificationData.setText(date);

        databaseHandler = new DatabaseHandler(MainActivity.this);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userInput.getText().toString();
                if (name.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter name.", Toast.LENGTH_SHORT).show();
                    return;
                }

                databaseHandler.insertIntoDatabase(name);
                Toast.makeText(MainActivity.this, "Name has been saved.", Toast.LENGTH_SHORT).show();
                userInput.setText("");
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> result = databaseHandler.readFromDatabase();
                if (result.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No data in database.", Toast.LENGTH_SHORT).show();
                }
                else {
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, result);
                    list.setAdapter(arrayAdapter);
                }

            }
        });
    }
}