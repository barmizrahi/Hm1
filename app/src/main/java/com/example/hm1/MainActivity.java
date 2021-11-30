package com.example.hm1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
    private Timer timer = new Timer();
    private int lives = 3, score = 0, curPos = 2, insertToArrOfImageView = 0, time = 1000, genBonus,choose;
    private final int NUM_OF_LANES = 5;
    private final int MAX_TO_ARRIVE = 6;
    private randomImageView ra;
    private float curX;
    private float curT;
    private float cutZ;
    Game game;
    private Context context = null;
    private boolean firstTimeEnter = true;
    private LinearLayout.LayoutParams linearlayoutParam = null;
    private Fragment_start fragment_start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        game = new Game(context);
        fragment_start = new Fragment_start();
        fragment_start.setActivity(this);
        fragment_start.setCallBackStart(callBack_start);
        getSupportFragmentManager().beginTransaction().add(R.id.frameStart, fragment_start).commit();

    }

  /*  private void insertImageView() {
        for (int i = 0; i < NUM_OF_LANES; i++) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            ImageView spaceShip = new ImageView(context);
            spaceShip.setId(i);//check this
            spaceShip.setLayoutParams(new LinearLayout.LayoutParams(500, 500));
            spaceShip.setImageResource(R.drawable.img_spaceship);
            if (i != 2) {
                spaceShip.setVisibility(View.INVISIBLE);
            }
            arrOfSpaceShip.add(spaceShip);
            LinearLayout linearLayout1 = new LinearLayout(context);
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            linearLayout1.setId(i);
            linearlayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            linearlayoutParam.weight = 1;
            linearLayout1.setGravity(Gravity.CENTER);
            linearLayout1.setLayoutParams(linearlayoutParam);
            insertAlienAndBonus(i,linearLayout1);
           // insertAlienAndBonus(i,linearLayout1);
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            arrOfLayout.add(linearLayout1);
            arrOfLayout.get(i).addView(spaceShip, lp);
            panel_lanes.addView(linearLayout1);

        }
    }
    private void insertAlienAndBonus(int i, LinearLayout linearLayout1) {
        for (int j = 0; j < MAX_TO_ARRIVE; j++) {
            ImageView imageView = new ImageView(context);
            imageView.setId(i);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(180, 180)); // value is in pixels
            matOfAlien[j][i] = imageView;
            linearLayout1.addView(imageView);
        }
    }
      public void sensorOrPress(int choose){
        this.choose = choose;
        if(choose==0) {//press
            game.getPanel_below_layout().setVisibility(View.VISIBLE);
            game.getPanel_IMG_right().setVisibility(View.VISIBLE);
            game.getPanel_IMG_left().setVisibility(View.VISIBLE);
        }
        if(choose==1){//sensor
            initSensor();
        }
    }
*/
  public void sensorOrPress(){
      if(game.getChoose()==0) {//press
          game.getPanel_below_layout().setVisibility(View.VISIBLE);
          game.getPanel_IMG_right().setVisibility(View.VISIBLE);
          game.getPanel_IMG_left().setVisibility(View.VISIBLE);
      }
      if(game.getChoose()==1){//sensor
          initSensor();
      }
  }

    private void startTicker() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //gen();
                        game.gen();
                    }
                });
            }
        }, 0, time);
    }
/*
    private void gen(){
        if (needToGen) {
            needToGen = false;
            int max = 4, min = 0, lane = new Random().nextInt((max - min) + 1) + min;
            max = 1;
            genBonus = new Random().nextInt((max - min) + 1) + min;
            if (genBonus == 0) { //then gen an alien
                if (insertToArrOfImageView == NUM_OF_LANES + 1) {
                    insertToArrOfImageView = 0;
                }
                arrOfImageView[insertToArrOfImageView] = new randomImageView(0, lane,"alien");
                ra = arrOfImageView[insertToArrOfImageView];
                matOfAlien[ra.getLine()][ra.getRow()].setImageResource(R.drawable.img_alien);
                matOfAlien[ra.getLine()][ra.getRow()].setVisibility(View.VISIBLE);
                insertToArrOfImageView++;
            } else { // need to gen a bonus
                if (insertToArrOfImageView == NUM_OF_LANES + 1) {
                    insertToArrOfImageView = 0;
                }
                arrOfImageView[insertToArrOfImageView] = new randomImageView(0, lane,"star");
                ra =  arrOfImageView[insertToArrOfImageView];
                matOfAlien[ra.getLine()][ra.getRow()].setImageResource(R.drawable.img_star);
                matOfAlien[ra.getLine()][ra.getRow()].setVisibility(View.VISIBLE);
               // matOfBonus[ra.getLine()][ra.getRow()].setVisibility(View.VISIBLE);
                insertToArrOfImageView++;
            }
        } else {
            needToGen = true;
        }
        moveObj();
    }

    private void moveObj() {
        for (int i = 0; i < NUM_OF_LANES + 1; i++) {
            int num = 0;
            moveAlienAndStarDown(i, num);
        }
        score += 10;
        panel_LBL_score.setText("" + score);

    }


    private void moveAlienAndStarDown(int i, int num) {
        if (arrOfImageView[i] != null) {
            String type = arrOfImageView[i].getSource();//alien or star
            if (i != insertToArrOfImageView - 1 || needToGen ) {
                if (arrOfImageView[i].getLine() >= MAX_TO_ARRIVE) {
                    num = 5;
                } else {
                    num = arrOfImageView[i].getLine();
                }

                matOfAlien[num][arrOfImageView[i].getRow()].setVisibility(View.INVISIBLE);
                arrOfImageView[i].addLine();
                if (arrOfImageView[i].getRow() == curPos && arrOfImageView[i].getLine() == MAX_TO_ARRIVE) {
                   if(type.equals("alien")) {
                       lives--;
                       updateLivesViews();
                   }
                   else if(type.equals("star")){
                       score = score + 100;
                       panel_LBL_score.setText("" + score);
                       if ((score + 1000) % 1000 == 0 && needToSpeedUp) {
                           time = time - 100;
                           needToSpeedUp = false;
                       }
                   }
                }
            }
            if (arrOfImageView[i].getLine() < MAX_TO_ARRIVE) {
                if(type.equals("alien")){
                    matOfAlien[arrOfImageView[i].getLine()][arrOfImageView[i].getRow()].setImageResource(R.drawable.img_alien);
                }
                else if(type.equals("star")){
                    matOfAlien[arrOfImageView[i].getLine()][arrOfImageView[i].getRow()].setImageResource(R.drawable.img_star);
                }
                matOfAlien[arrOfImageView[i].getLine()][arrOfImageView[i].getRow()].setVisibility(View.VISIBLE);
            }
        }
    }
*/

    private void findView() {
        frameStart = findViewById(R.id.frameStart);
        panel_Life_Score_layout =findViewById(R.id.panel_Life_Score_layout);
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
/*
    private void updateLivesViews() {
        if (lives == 0) {
            //then exit and save the score not recaycle
            //open the view of top ten and map
            arrOfLife[0].setVisibility(View.VISIBLE);
            arrOfLife[1].setVisibility(View.VISIBLE);
            arrOfLife[2].setVisibility(View.VISIBLE);
            lives = 3;
            Toast.makeText(getApplicationContext(), "Fail, Try Again", Toast.LENGTH_SHORT).show();
            vibrate();
        } else
            arrOfLife[lives].setVisibility(View.INVISIBLE);

    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }
   */
    protected void onStop() {
        super.onStop();
        game.stopTicker();
    }

    protected void onStart() {
        super.onStart();
        /*
        sensorOrPress();
        game.setFirstTimeEnter(true);
        startTicker();
        */


    }



    @Override
    protected void onResume() {
        super.onResume();
        if(game.getChoose()==1) {
            sensorManager.registerListener(accSensorEventListener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(game.getChoose()==1) {
            sensorManager.unregisterListener(accSensorEventListener);
        }
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
            if(game.getFirstTimeEnter()){//need to set the curr values for speed
                game.setCurX(event.values[0]);
                game.setCurY(event.values[1]);
                game.setCurZ(event.values[2]);
                game.setFirstTimeEnter(false);
            }
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            Log.i("myTag" ,""+ x);
            if(x>7&&x<=9){//the 0 posion is on
                moveTheSpaceShip(0);
                /*
                game.getArrOfSpaceShip().get(0).setVisibility(View.VISIBLE);
                game.getArrOfSpaceShip().get(3).setVisibility(View.INVISIBLE);
                game.getArrOfSpaceShip().get(4).setVisibility(View.INVISIBLE);
                game.getArrOfSpaceShip().get(1).setVisibility(View.INVISIBLE);
                game.getArrOfSpaceShip().get(2).setVisibility(View.INVISIBLE);
                game.setCurPos(0);
                */
            }
            if(x>=1&&x<=4){//the 1 posion is on
                moveTheSpaceShip(1);
                /*
                game.getArrOfSpaceShip().get(1).setVisibility(View.VISIBLE);
                game.getArrOfSpaceShip().get(2).setVisibility(View.INVISIBLE);
                game.getArrOfSpaceShip().get(4).setVisibility(View.INVISIBLE);
                game.getArrOfSpaceShip().get(3).setVisibility(View.INVISIBLE);
                game.getArrOfSpaceShip().get(0).setVisibility(View.INVISIBLE);
                game.setCurPos(1);

                 */
            }
            if(x>=-1&&x<=1){//the middle 2 posion is on
                moveTheSpaceShip(2);
                /*
                game.getArrOfSpaceShip().get(2).setVisibility(View.VISIBLE);
                game.getArrOfSpaceShip().get(3).setVisibility(View.INVISIBLE);
                game.getArrOfSpaceShip().get(4).setVisibility(View.INVISIBLE);
                game.getArrOfSpaceShip().get(1).setVisibility(View.INVISIBLE);
                game.getArrOfSpaceShip().get(0).setVisibility(View.INVISIBLE);
                game.setCurPos(2);

                 */
            }
            if(x<=-1&&x>=-4){//the  3 posion is on
                moveTheSpaceShip(3);
                /*
                game.getArrOfSpaceShip().get(3).setVisibility(View.VISIBLE);
                game.getArrOfSpaceShip().get(0).setVisibility(View.INVISIBLE);
                game.getArrOfSpaceShip().get(4).setVisibility(View.INVISIBLE);
                game.getArrOfSpaceShip().get(1).setVisibility(View.INVISIBLE);
                game.getArrOfSpaceShip().get(2).setVisibility(View.INVISIBLE);
                game.setCurPos(3);
                */

            }
            if(x<-7&&x>=-9){//the left 4 posion is on
                moveTheSpaceShip(4);
                /*
                game.getArrOfSpaceShip().get(4).setVisibility(View.VISIBLE);
                game.getArrOfSpaceShip().get(3).setVisibility(View.INVISIBLE);
                game.getArrOfSpaceShip().get(1).setVisibility(View.INVISIBLE);
                game.getArrOfSpaceShip().get(0).setVisibility(View.INVISIBLE);
                game.getArrOfSpaceShip().get(2).setVisibility(View.INVISIBLE);
                game.setCurPos(4);

                 */
            }
        }
    };

    private void moveTheSpaceShip(int pos) {
        for(int i=0;i<NUM_OF_LANES;i++){
            if(i!=pos){
                game.getArrOfSpaceShip().get(i).setVisibility(View.INVISIBLE);
            }
            else
                game.getArrOfSpaceShip().get(i).setVisibility(View.VISIBLE);
        }
        game.setCurPos(pos);
    }

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

        }
    };

    private void startTheGame() {


        findView();
        game.insertImageView();
        setContentView(panel_main_layout);
        game.getPanel_IMG_right().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.getArrOfSpaceShip().get(4).getVisibility() != View.VISIBLE) {
                    game.getArrOfSpaceShip().get(curPos + 1).setVisibility(View.VISIBLE);
                    game.getArrOfSpaceShip().get(curPos).setVisibility(View.INVISIBLE);
                    curPos += 1;
                }
            }
        });
        game.getPanel_IMG_left().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.getArrOfSpaceShip().get(0).getVisibility() != View.VISIBLE) {
                    game.getArrOfSpaceShip().get(curPos - 1).setVisibility(View.VISIBLE);
                    game.getArrOfSpaceShip().get(curPos).setVisibility(View.INVISIBLE);
                    curPos -= 1;
                }
            }
        });
        frameStart.setVisibility(View.GONE);//gone the start manu and bring the game
        panel_Life_Score_layout.setVisibility(View.VISIBLE);
        panel_lanes.setVisibility(View.VISIBLE);
        sensorOrPress();
        game.setFirstTimeEnter(true);//for speed
        startTicker();
    }
}