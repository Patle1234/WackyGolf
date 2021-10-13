package com.example.accelerometergame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class LevelActivity  extends AppCompatActivity {
    private Button level1Btn;
    private Button level2Btn;
    private Button level3Btn;
    private Button level4Btn;
    private Button level5Btn;
    private Button rBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        level1Btn=findViewById(R.id.level_1_btn);
        level1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.level=1;
                MainActivity.setTheCourse();
                Intent i = new Intent(LevelActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


        level2Btn=findViewById(R.id.level_2_btn);
        level2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.level=2;
                MainActivity.setTheCourse();
                Intent i = new Intent(LevelActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        level3Btn=findViewById(R.id.level_3_btn);
        level3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.level=3;
                MainActivity.setTheCourse();
                Intent i = new Intent(LevelActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        level4Btn=findViewById(R.id.level_4_btn);
        level4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.level=4;
                MainActivity.setTheCourse();

                Intent i = new Intent(LevelActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        level5Btn=findViewById(R.id.level_5_btn);
        level5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.level=5;
                MainActivity.setTheCourse();

                Intent i = new Intent(LevelActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        rBtn=findViewById((R.id.rBtn));
        rBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LevelActivity.this, StartActivity.class);
                startActivity(i);
            }
        });
    }
}
