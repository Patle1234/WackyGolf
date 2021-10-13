package com.example.accelerometergame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.FileInputStream;
import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {
    private static Button returnBtn;
    private static ListView parList;
    public static boolean ifAlreadyDisplayed=false;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEM
    public static ArrayList<String> achivementList=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    public static ArrayAdapter<String> adapter;
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        returnBtn=findViewById(R.id.rBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StatisticsActivity.this, StartActivity.class);
                startActivity(i);
            }
        });

        parList=findViewById(R.id.par_list);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, achivementList);
        parList.setAdapter(adapter);

        for(int i=1;i<6;i++) {
            try {
                FileInputStream fin = openFileInput("swing" +i);
                int c;
                String temp = "";
                while ((c = fin.read()) != -1) {
                    temp = temp + Character.toString((char) c);
                }
                int x = Integer.parseInt(temp);
                for(int j=0;j< achivementList.size();j++){
                    if(achivementList.get(j).equals("Level "+i+": " + x)){
                        ifAlreadyDisplayed=true;
                    }
                }
                fin.close();
                if(!ifAlreadyDisplayed) {
                    achivementList.add("Level " + i + ": " + x);
                }
                ifAlreadyDisplayed=false;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }




}
