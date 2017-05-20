package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText result;

        float[] n2 = { 1.5f, 3.9f, 4.1f, 3.3f };
        float[] n1 = { 2.1f, 2.45f, 3.673f, 4.32f, 2.05f, 1.93f, 5.67f, 6.01f };
        DTW dtw = new DTW(n1, n2);

        result =  (EditText)findViewById(R.id.result);
        result.setText(dtw.toString());

    }
}
