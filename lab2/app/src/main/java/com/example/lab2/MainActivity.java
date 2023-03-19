package com.example.lab2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText number1;
    private EditText number2;
    private Button nextActivityButton;
    private Button photoButton;
    private TextView result;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number1 = (EditText) findViewById(R.id.editText);
        number2 = (EditText) findViewById(R.id.editText2);
        nextActivityButton = (Button) findViewById(R.id.button);
        photoButton = (Button) findViewById(R.id.photoButton);
        result = (TextView) findViewById(R.id.textView5);
        image = (ImageView) findViewById(R.id.imageView);

        nextActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number1.getText().toString().equals("") || number2.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Insert numbers!", Toast.LENGTH_SHORT).show();
                }
                else {
                    next_activity();
                }
            }
        });
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(imageCaptureIntent, 616);
            }
        });
    }
    public void next_activity(){
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("number1", Integer.parseInt(number1.getText().toString()));
        intent.putExtra("number2", Integer.parseInt(number2.getText().toString()));
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                int res = data.getIntExtra("result", 0);
                result.setText("Result: " + res);
            }
            if(resultCode == RESULT_CANCELED){
                result.setText("Nothing selected.");
            }
        }
        if(requestCode == 616 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }
    }
}