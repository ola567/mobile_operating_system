package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.lab5.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'lab5' library on application startup.
    static {
        System.loadLibrary("lab5");
    }

    private ActivityMainBinding binding;
    private int[] myArray = {1, 2, 3, 4, 5, 4, 2, 1, 76, 2, 434};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.nativeString;
        TextView beforeTV = binding.beforeRemoving;
        TextView afterTV = binding.afterRemoving;

        tv.setText(stringFromJNI());
        beforeTV.setText(Arrays.toString(myArray));
        afterTV.setText(Arrays.toString(arrayFromJNI(myArray)));
    }

    /**
     * A native method that is implemented by the 'lab5' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native int[] arrayFromJNI(int[] myArray);
}