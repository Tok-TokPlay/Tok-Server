package com.example.a4p.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class Client extends AppCompatActivity {
    String s= null;
    ArrayList<Integer> userBeat = new ArrayList<>();
    EditText dt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        dt= (EditText)(findViewById(R.id.editText));

    }
    public void onClick(View v){
        s=dt.getText().toString();
        Log.v("S",s+"");
        String[] str = new String[s.length()];
        str=s.split(",");
        Log.v("String",str.toString()+"");
        for(int i=0;i<str.length;i++){
            userBeat.add(Integer.parseInt(str[i]));
        }
        Log.v("userbeat",userBeat+" oiko");

       ClientConnect cc = new ClientConnect("165.194.17.109",userBeat);
    }
}
