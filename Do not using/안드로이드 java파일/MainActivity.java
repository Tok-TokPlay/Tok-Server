package com.example.myapplication;

import android.content.res.Resources;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText result;

        int num;
        ArrayList<Character> arrayList = new ArrayList<Character>();
        String filename = "저장데이터.txt";
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator;
        try {
            FileInputStream fis = new FileInputStream(path + "저장데이터.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            System.out.println("저장데이터");
            while ((num = reader.read()) != -1) {
                arrayList.add((char)num);
            }
            for(int i=0;i<10;i++) {
                System.out.println((char)arrayList.get(i));
            }
            reader.close();
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayList <Character>arrayList2 = new ArrayList<>();
        try {
            FileReader reader = new FileReader(path+"체크.txt");
            System.out.println("체크");
            while ((num = reader.read()) != -1) {
                arrayList2.add((char)num);
            }
            for(int i=0;i<10;i++) {
                System.out.println(arrayList2.get(i));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        float n1[] = new float[arrayList.size()];
        float n2[] = new float[arrayList2.size()];
        for(int i=0;i<arrayList.size();i++) {
            n1[i] = arrayList.get(i)-'0';
        }
        for(int i=0;i<arrayList2.size();i++) {
            n2[i] = arrayList2.get(i)-'0';
        }
        DTW dtw = new DTW(n1,n2);

        result =  (EditText)findViewById(R.id.result);
        result.setText(dtw.toString());

    }
}
