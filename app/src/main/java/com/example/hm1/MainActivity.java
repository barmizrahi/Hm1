package com.example.hm1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hm1.dataBase.LocationSensor;
import com.example.hm1.dataBase.MSPV3;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout panel_Life_Score_layout;
    private FrameLayout frameStart;
    private ImageView panel_IMG_left;
    private ImageView panel_IMG_right;

    private SensorManager sensorManager;
    private Sensor accSensor;
    private TextView panel_LBL_score;
    private LinearLayout panel_main_layout;
    private LinearLayout panel_lanes;
    private LinearLayout panel_below_layout;
    private ArrayList<ImageView> arrOfSpaceShip;
    private ArrayList<LinearLayout> arrOfLayout;
    private ImageView[][] matOfAlien;
    private randomImageView[] arrOfImageView;
    private ImageView[] arrOfLife;
    private boolean needToGen = true, needToSpeedUp = true;
    private Timer timer;
    private int lives = 3, score = 0, insertToArrOfImageView = 0, time = 1000, genBonus, choose;
    private final int NUM_OF_LANES = 5;
    private final int MAX_TO_ARRIVE = 6;
    private randomImageView ra;
    private float curX;
    private float curT;
    private float cutZ;
    private Game game;
    private Context context = null;
    private boolean firstTimeEnter = true;
    private LinearLayout.LayoutParams linearlayoutParam = null;
    private Fragment_start fragment_start;
    private Bundle insertTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = new Timer();
        requestLocPermissions();
        MSPV3.initHelper(this);
        LocationSensor.locationSensorInit(this);
        if (getIntent().getBundleExtra("BUNDLE") != null) {
            this.insertTo = getIntent().getBundleExtra("BUNDLE");
        } else {
            this.insertTo = new Bundle();
        }

        context = this;
        game = new Game(context, this);
        fragment_start = new Fragment_start();
        fragment_start.setActivity(this);
        fragment_start.setCallBackStart(callBack_start);
        getSupportFragmentManager().beginTransaction().add(R.id.frameStart, fragment_start).commit();
    }

    private void requestLocPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
    }



    public void sensorOrPress() {
        game.getPanel_Life_Score_layout().setVisibility(View.VISIBLE);
        game.getPanel_lanes().setVisibility(View.VISIBLE);
        if (game.getChoose() == 0) {//press
            game.getPanel_below_layout().setVisibility(View.VISIBLE);
            game.getPanel_IMG_right().setVisibility(View.VISIBLE);
            game.getPanel_IMG_left().setVisibility(View.VISIBLE);
        }
        if (game.getChoose() == 1) {//sensor
            initSensor();
        }
    }

    private void startTicker() {
        //timer = new Timer();
        timer.schedule(
                new TimerTask(){
                    @Override
                    public void run(){
                       // Log.i("bb" , ""+game.getTime());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Log.i("bb" , ""+game.getTime());
                                game.gen();
                            }
                        });

                    }
                },0,game.getTime());
/*
        game.setTimer(timer);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.i("here" , ""+game.getTime());
                        //gen();
                        game.gen();
                    }
                });
            }
        },game.getTime()+1000);

 */
    }

    private void findView() {

        frameStart = findViewById(R.id.frameStart);
        game.setFrameStart(frameStart);
        panel_Life_Score_layout = findViewById(R.id.panel_Life_Score_layout);
        game.setPanel_Life_Score_layout(panel_Life_Score_layout);
        panel_below_layout = findViewById(R.id.panel_below_layout);
        game.setPanel_below_layout(panel_below_layout);
        panel_LBL_score = findViewById(R.id.panel_LBL_score);
        game.setPanel_LBL_score(panel_LBL_score);
        panel_IMG_right = findViewById(R.id.panel_IMG_right);
        game.setPanel_IMG_right(panel_IMG_right);
        panel_IMG_left = findViewById(R.id.panel_IMG_left);
        game.setPanel_IMG_left(panel_IMG_left);
        panel_main_layout = findViewById(R.id.panel_main_layout);
        game.setPanel_main_layout(panel_main_layout);
        panel_lanes = findViewById(R.id.panel_lanes);
        game.setPanel_lanes(panel_lanes);
        arrOfSpaceShip = new ArrayList<>(NUM_OF_LANES);
        arrOfLayout = new ArrayList(NUM_OF_LANES);
        arrOfImageView = new randomImageView[NUM_OF_LANES + 1];
        matOfAlien = new ImageView[MAX_TO_ARRIVE][NUM_OF_LANES];
        arrOfLife = new ImageView[]{
                findViewById(R.id.panel_IMG_life1),
                findViewById(R.id.panel_IMG_life2),
                findViewById(R.id.panel_IMG_life3)
        };
        game.setArrOfLife(arrOfLife);

    }

    protected void onStop() {
        super.onStop();
        game.stopTicker();
    }

    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (game.getChoose() == 1) {
            sensorManager.registerListener(accSensorEventListener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        if (game.getChoose() == 1) {
            sensorManager.unregisterListener(accSensorEventListener);
        }
        super.onPause();
    }

    public boolean isSensorExist(int sensorType) {
        return (sensorManager.getDefaultSensor(sensorType) != null);
    }

    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        onResume();
    }

    private SensorEventListener accSensorEventListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (game.getFirstTimeEnter()) {
                game.setCurX(event.values[0]);
                game.setCurY(event.values[1]);
                game.setCurZ(event.values[2]);
                game.setFirstTimeEnter(false);
            }
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            if (x > 7 && x <= 9) {//the 0 posion is on
                game.moveTheSpaceShip(0);
            }
            if (x >= 2 && x <= 4) {//the 1 posion is on
                game.moveTheSpaceShip(1);
            }
            if (x >= -1 && x <= 1) {//the middle 2 posion is on
                game.moveTheSpaceShip(2);
            }
            if (x <= -2 && x >= -4) {//the  3 posion is on
                game.moveTheSpaceShip(3);
            }
            if (x < -7 && x >= -9) {//the left 4 posion is on
                game.moveTheSpaceShip(4);
            }
        }
    };


    CallBack_Start callBack_start = new CallBack_Start() {
        @Override
        public void gameWithButton() {
            game.setChoose(0);
            startTheGame();
        }

        @Override
        public void gameWithSensor() {
            game.setChoose(1);
            startTheGame();
        }

        @Override
        public void showTopTen() {
            Intent myIntent = new Intent(context, top_ten_and_map.class);
            myIntent.putExtra("BUNDLE", insertTo);
            startActivity(myIntent);
        }
    };



    private void startTheGame() {
        findView();
        game.insertImageView();
        setContentView(panel_main_layout);
        game.getPanel_IMG_right().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curPos = game.getCurPos();
                if (game.getArrOfSpaceShip().get(4).getVisibility() != View.VISIBLE) {
                    game.getArrOfSpaceShip().get(curPos + 1).setVisibility(View.VISIBLE);
                    game.getArrOfSpaceShip().get(curPos).setVisibility(View.INVISIBLE);

                    game.setCurPos(curPos + 1);
                }
            }
        });
        game.getPanel_IMG_left().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curPos = game.getCurPos();
                if (game.getArrOfSpaceShip().get(0).getVisibility() != View.VISIBLE) {
                    game.getArrOfSpaceShip().get(curPos - 1).setVisibility(View.VISIBLE);
                    game.getArrOfSpaceShip().get(curPos).setVisibility(View.INVISIBLE);
                    game.setCurPos(curPos - 1);
                }
            }
        });
        game.getFrameStart().setVisibility(View.GONE);//gone the start manu and bring the game
        sensorOrPress();
        game.getPanel_Life_Score_layout().setVisibility(View.VISIBLE);
        game.getPanel_lanes().setVisibility(View.VISIBLE);
        game.setFirstTimeEnter(true);//for speed
        startTicker();
    }

    public void endGame() {
        timer.cancel();
        finish();
        openSaveScore();
        overridePendingTransition(0, 0);


    }

    private void openSaveScore() {
        Intent myIntent = new Intent(this, SaveScoreActivity.class);
        insertTo.putInt("SCORE", game.getScore());
        myIntent.putExtra("BUNDLE", insertTo);
        startActivity(myIntent);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timer.cancel();
        Intent intent=new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        overridePendingTransition(0, 0);
        this.finish();

    }
}