package com.example.accelerometergame;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView square;
    private double vertical;
    private double horizontal;
    private double resultant;
    private Button shotButton;
    private static ImageView hole;
    private static ImageView ball;
    private TextView swingText;
    private TextView powerText;
    private static ImageView sand1;
    private static ImageView sand2;
    private static ImageView pond1;
    private static ImageView pond2;
    private int numSwings;
    public static int level=1;
    private static int startBallX;
    private static int startBallY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        numSwings=0;
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        swingText=findViewById(R.id.swingText);
        powerText=findViewById(R.id.powerText);
        square=findViewById(R.id.tv_square);
        hole=findViewById(R.id.golf_hole);
        ball=findViewById(R.id.golf_ball);
        sand1=findViewById(R.id.sand_1);
        sand2=findViewById(R.id.sand_2);
        pond1=findViewById(R.id.water_1);
        pond2=findViewById(R.id.water_2);
        Display display = getWindowManager().getDefaultDisplay();
        int w = ((Display) display).getWidth();
        int h = display.getHeight();

        setUpSensorStuff();
        shotButton=findViewById(R.id.shotBtn);
        shotButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                float newX=ball.getX();
                float newY=ball.getY();
                //check if on sand
                Rect oldballRect = new Rect((int)ball.getX(), (int) ball.getY(), (int)ball.getX()+ball.getWidth(), (int) ball.getY() + ball.getHeight());
                Rect sand1Rect = new Rect((int)sand1.getX(), (int) sand1.getY(), (int)sand1.getX()+sand1.getWidth(), (int) sand1.getY() + sand1.getHeight());
                if(oldballRect.intersect(sand1Rect)){
                    newX=newX+(float) (horizontal * resultant*2);
                    newY=newY+(float)(vertical * resultant*2);
                }else{
                    newX=newX+(float)(horizontal * resultant * 10);
                    newY=newY+(float)(vertical * resultant * 10);
                }

                if(!(horizontal==0) && !(vertical==0)) {
                    if(newX>0&&newY>0&&newX<w&&newY<h){
                        ball.setX(newX);
                        ball.setY(newY);
                        numSwings++;
                        resultant = getResultant(vertical, horizontal);
                         Rect holeRect = new Rect((int)hole.getX(), (int) hole.getY(), (int)hole.getX()+hole.getWidth(), (int) hole.getY() + hole.getHeight());
                         Rect ballRect = new Rect((int)ball.getX(), (int) ball.getY(), (int)ball.getX()+ball.getWidth(), (int) ball.getY() + ball.getHeight());
                         Rect pond1Rect = new Rect((int)pond1.getX(), (int) pond1.getY(), (int)pond1.getX()+pond1.getWidth(), (int) pond1.getY() + pond1.getHeight());
                        Rect pond2Rect = new Rect((int)pond2.getX(), (int) pond2.getY(), (int)pond2.getX()+pond2.getWidth(), (int) pond2.getY() + pond2.getHeight());

                        if (holeRect.intersect(ballRect)) {
                            ball.setVisibility(View.INVISIBLE);
                            saveSwings(level);
                            if(level==5){
                                level=1;
                            }else{
                                level++;
                            }
                            Intent i = new Intent(getApplicationContext(), PopActivity.class);
                            startActivity(i);
                        }
                        if(pond1Rect.intersect(ballRect)||pond2Rect.intersect(ballRect)){
                            ball.setX(startBallX);
                            ball.setY(startBallY);
                        }
                        swingText.setText("Swings: " + numSwings);
                    }
                }else{
                    System.out.println("NO SHOT ANGLE");
                }
            }
        });
        setTheCourse();
    }

    private void setUpSensorStuff() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
       sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            double h=event.values[0];
            double v=event.values[1];
            vertical=v;
            horizontal=h;
            double power=((Math.abs(horizontal)+Math.abs(vertical))/18)*100;
            powerText.setText("Power: "+(int)power+"%");
            horizontal=horizontal*(-1);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onDestroy() {
        sensorManager.unregisterListener(this);
        super.onDestroy();
    }

    private double getResultant(double x,double y) {
        double r=Math.pow(x,2)+Math.pow(y,2);
        r=Math.sqrt(r);
        return r;
    }
    private double getAngle(double x,double y){
        double a=Math.atan((y/x));
        a=Math.toDegrees(a);
        return a;
    }

    public void saveSwings(int level){
//        try{
//
//                int beforeSwings=getSwings(level);
//                if(beforeSwings<level) {
//
//
//                }




//            }
        try{
            

            FileOutputStream fOut = openFileOutput(("swing" + level), Context.MODE_PRIVATE);
            String str = String.valueOf(numSwings);
            fOut.write(str.getBytes());
            fOut.close();

        }catch(Exception e) {
            System.out.println(e);
        }
    }

    public int getSwings(int level){
        try{
            FileInputStream fin = openFileInput("swing"+level);
            int c;
            String temp="";
            while( (c = fin.read()) != -1) {
                temp = temp+Character.toString((char) c);
            }
            int x=Integer.parseInt(temp);
            fin.close();
            return x;
        }catch(Exception e){
            System.out.println(e);
        }
        return 0;
    }

//    public  void updateStats(){
//        File sdCard = Environment.getExternalStorageDirectory();
//        File dir = new File (sdcard.getAbsolutePath() + "/dir1/dir2");
//        dir.mkdirs();
//        File file = new File(dir, "filename");
//
//        FileOutputStream f = new FileOutputStream(file);
//    }

    public static void setTheCourse(){
        //TODO place of hole
        //TODO place of ball
        //TODO place of water
        //TODO place of sand
        if(level==1){
            startBallX=50;
            startBallY=1100;
            ball.setX(75);
            ball.setY(135);
            hole.setX(475);
            hole.setY(400);
            pond1.setX(230);
            pond1.setY(535);
            sand1.setVisibility(View.INVISIBLE);
            pond2.setVisibility(View.INVISIBLE);
            sand2.setVisibility(View.INVISIBLE);
        }else if(level==2){
            startBallX=750;
            startBallY=950;
            ball.setX(750);
            ball.setY(950);
            hole.setX(30);
            hole.setY(5);
            pond1.setX(175);
            pond1.setY(600);
            sand1.setX(550);
            sand1.setY(550);
            pond2.setVisibility(View.INVISIBLE);
            sand2.setVisibility(View.INVISIBLE);
        }else if(level==3){
            startBallX=50;
            startBallY=150;
            ball.setX(50);
            ball.setY(150);
            hole.setX(725);
            hole.setY(500);
            pond1.setX(300);
            pond1.setY(740);
            sand1.setX(250);
            sand1.setY(350);
            sand2.setX(550);
            sand2.setY(700);
            pond2.setVisibility(View.INVISIBLE);
        }else if(level==4){
            startBallX=250;
            startBallY=350;
            ball.setX(250);
            ball.setY(350);
            hole.setX(625);
            hole.setY(700);
            pond1.setX(400);
            pond1.setY(725);
            sand1.setX(600);
            sand1.setY(500);
            sand2.setX(60);
            sand2.setY(700);
            pond2.setX(550);
            pond2.setY(250);
        }else if(level==5){
            startBallX=750;
            startBallY=100;
            ball.setX(750);
            ball.setY(100);
            hole.setX(425);
            hole.setY(200);
            pond1.setX(375);
            pond1.setY(715);
            sand1.setX(375);
            sand1.setY(450);
            sand2.setX(150);
            sand2.setY(600);
            pond2.setX(550);
            pond2.setY(600);
        }
    }




}