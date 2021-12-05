package com.example.hm1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class Game {
    private Context context;
    private ImageView panel_IMG_left, panel_IMG_right;
    private SensorManager sensorManager;
    private Sensor accSensor;
    private TextView panel_LBL_score;
    private LinearLayout.LayoutParams linearlayoutParam = null;
    private RelativeLayout panel_Life_Score_layout;
    private LinearLayout panel_main_layout, panel_lanes, panel_below_layout;
    private FrameLayout frameStart;
    private ArrayList<ImageView> arrOfSpaceShip;
    private ArrayList<LinearLayout> arrOfLayout;
    private ImageView[][] matOfAlien;
    private randomImageView[] arrOfImageView;
    private randomImageView ra;
    private ImageView[] arrOfLife;
    private Timer timer = new Timer();
    private boolean needToGen = true, needToSpeedUp = true, firstTimeEnter = true, endGame;
    private int lives = 3, score = 0, curPos = 2, insertToArrOfImageView = 0, time = 500,curTime = 500, genBonus, choose;
    private final int NUM_OF_LANES = 5;
    private final int MAX_TO_ARRIVE = 6;
    private float curX, curY, curZ;
    private MainActivity mainActivity;


    public Game(Context context, MainActivity mainActivity) {
        this.context = context;
        arrOfLayout = new ArrayList(NUM_OF_LANES);
        arrOfSpaceShip = new ArrayList<>(NUM_OF_LANES);
        arrOfImageView = new randomImageView[NUM_OF_LANES + 1];
        matOfAlien = new ImageView[MAX_TO_ARRIVE][NUM_OF_LANES];
        endGame = true;
        this.mainActivity = mainActivity;
    }

    public Context getContext() {
        return context;
    }

    public FrameLayout getFrameStart() {
        return frameStart;
    }

    public Game setFrameStart(FrameLayout frameStart) {
        this.frameStart = frameStart;
        return this;
    }

    public RelativeLayout getPanel_Life_Score_layout() {
        return panel_Life_Score_layout;
    }

    public Game setPanel_Life_Score_layout(RelativeLayout panel_Life_Score_layout) {
        this.panel_Life_Score_layout = panel_Life_Score_layout;
        return this;
    }

    public Game setContext(Context context) {
        this.context = context;
        return this;
    }

    public ImageView getPanel_IMG_left() {
        return panel_IMG_left;
    }

    public Game setPanel_IMG_left(ImageView panel_IMG_left) {
        this.panel_IMG_left = panel_IMG_left;
        return this;
    }

    public ImageView getPanel_IMG_right() {
        return panel_IMG_right;
    }

    public Game setPanel_IMG_right(ImageView panel_IMG_right) {
        this.panel_IMG_right = panel_IMG_right;
        return this;
    }

    public SensorManager getSensorManager() {
        return sensorManager;
    }

    public Game setSensorManager(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
        return this;
    }

    public Sensor getAccSensor() {
        return accSensor;
    }

    public Game setAccSensor(Sensor accSensor) {
        this.accSensor = accSensor;
        return this;
    }

    public TextView getPanel_LBL_score() {
        return panel_LBL_score;
    }

    public Game setPanel_LBL_score(TextView panel_LBL_score) {
        this.panel_LBL_score = panel_LBL_score;
        return this;
    }

    public LinearLayout getPanel_main_layout() {
        return panel_main_layout;
    }

    public Game setPanel_main_layout(LinearLayout panel_main_layout) {
        this.panel_main_layout = panel_main_layout;
        return this;
    }

    public LinearLayout getPanel_lanes() {
        return panel_lanes;
    }

    public Game setPanel_lanes(LinearLayout panel_lanes) {
        this.panel_lanes = panel_lanes;
        return this;
    }

    public LinearLayout getPanel_below_layout() {
        return panel_below_layout;
    }

    public Game setPanel_below_layout(LinearLayout panel_below_layout) {
        this.panel_below_layout = panel_below_layout;
        return this;
    }

    public ArrayList<ImageView> getArrOfSpaceShip() {
        return arrOfSpaceShip;
    }

    public Game setArrOfSpaceShip(ArrayList<ImageView> arrOfSpaceShip) {
        this.arrOfSpaceShip = arrOfSpaceShip;
        return this;
    }

    public ArrayList<LinearLayout> getArrOfLayout() {
        return arrOfLayout;
    }

    public Game setArrOfLayout(ArrayList<LinearLayout> arrOfLayout) {
        this.arrOfLayout = arrOfLayout;
        return this;
    }

    public ImageView[][] getMatOfAlien() {
        return matOfAlien;
    }

    public Game setMatOfAlien(ImageView[][] matOfAlien) {
        this.matOfAlien = matOfAlien;
        return this;
    }

    public randomImageView[] getArrOfImageView() {
        return arrOfImageView;
    }

    public Game setArrOfImageView(randomImageView[] arrOfImageView) {
        this.arrOfImageView = arrOfImageView;
        return this;
    }

    public ImageView[] getArrOfLife() {
        return arrOfLife;
    }

    public Game setArrOfLife(ImageView[] arrOfLife) {
        this.arrOfLife = arrOfLife;
        return this;
    }

    public boolean isNeedToGen() {
        return needToGen;
    }

    public Game setNeedToGen(boolean needToGen) {
        this.needToGen = needToGen;
        return this;
    }

    public boolean isNeedToSpeedUp() {
        return needToSpeedUp;
    }

    public Game setNeedToSpeedUp(boolean needToSpeedUp) {
        this.needToSpeedUp = needToSpeedUp;
        return this;
    }

    public Timer getTimer() {
        return timer;
    }

    public Game setTimer(Timer timer) {
        this.timer = timer;
        return this;
    }

    public int getLives() {
        return lives;
    }

    public Game setLives(int lives) {
        this.lives = lives;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Game setScore(int score) {
        this.score = score;
        return this;
    }

    public int getCurPos() {
        return curPos;
    }

    public Game setCurPos(int curPos) {
        this.curPos = curPos;
        return this;
    }

    public int getInsertToArrOfImageView() {
        return insertToArrOfImageView;
    }

    public Game setInsertToArrOfImageView(int insertToArrOfImageView) {
        this.insertToArrOfImageView = insertToArrOfImageView;
        return this;
    }

    public int getTime() {
        return time;
    }

    public Game setTime(int time) {
        this.time = time;
        return this;
    }

    public int getGenBonus() {
        return genBonus;
    }

    public Game setGenBonus(int genBonus) {
        this.genBonus = genBonus;
        return this;
    }

    public int getChoose() {
        return choose;
    }

    public Game setChoose(int choose) {
        this.choose = choose;
        return this;
    }

    public int getNUM_OF_LANES() {
        return NUM_OF_LANES;
    }

    public int getMAX_TO_ARRIVE() {
        return MAX_TO_ARRIVE;
    }

    public randomImageView getRa() {
        return ra;
    }

    public Game setRa(randomImageView ra) {
        this.ra = ra;
        return this;
    }

    public float getCurX() {
        return curX;
    }

    public Game setCurX(float curX) {
        this.curX = curX;
        return this;
    }

    public float getCurY() {
        return curY;
    }

    public Game setCurY(float curY) {
        this.curY = curY;
        return this;
    }

    public float getCurZ() {
        return curZ;
    }

    public Game setCurZ(float curZ) {
        this.curZ = curZ;
        return this;
    }

    public boolean getFirstTimeEnter() {
        return firstTimeEnter;
    }

    public Game setFirstTimeEnter(boolean firstTimeEnter) {
        this.firstTimeEnter = firstTimeEnter;
        return this;
    }

    public LinearLayout.LayoutParams getLinearlayoutParam() {
        return linearlayoutParam;
    }

    public Game setLinearlayoutParam(LinearLayout.LayoutParams linearlayoutParam) {
        this.linearlayoutParam = linearlayoutParam;
        return this;
    }

    public void insertImageView() {
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
            insertAlienAndBonus(i, linearLayout1);
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

    public void gen() {
        Log.i("here" , ""+time);
        if (endGame) {
            if (needToGen) {
                needToGen = false;
                int max = 4, min = 0, lane = new Random().nextInt((max - min) + 1) + min;
                max = 1;
                genBonus = new Random().nextInt((max - min) + 1) + min;
                if (genBonus == 0) { //then gen an alien
                    if (insertToArrOfImageView == NUM_OF_LANES + 1) {
                        insertToArrOfImageView = 0;
                    }
                    arrOfImageView[insertToArrOfImageView] = new randomImageView(0, lane, "alien");
                    ra = arrOfImageView[insertToArrOfImageView];
                    matOfAlien[ra.getLine()][ra.getRow()].setImageResource(R.drawable.img_alien);
                    matOfAlien[ra.getLine()][ra.getRow()].setVisibility(View.VISIBLE);
                    insertToArrOfImageView++;
                } else { // need to gen a bonus
                    if (insertToArrOfImageView == NUM_OF_LANES + 1) {
                        insertToArrOfImageView = 0;
                    }
                    arrOfImageView[insertToArrOfImageView] = new randomImageView(0, lane, "star");
                    ra = arrOfImageView[insertToArrOfImageView];
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
    }

    private void moveObj() {
        for (int i = 0; i < MAX_TO_ARRIVE; i++) {
            int num = 0;
            moveAlienAndStarDown(i, num);
        }
        score += 10;
        panel_LBL_score.setText("" + score);

    }

    private void moveAlienAndStarDown(int i, int num) {
        if (arrOfImageView[i] != null) {
            String type = arrOfImageView[i].getSource();//alien or star
            if (i != insertToArrOfImageView - 1 || needToGen) {
                if (arrOfImageView[i].getLine() >= MAX_TO_ARRIVE) {
                    num = 5;
                } else {
                    num = arrOfImageView[i].getLine();

                }
                arrOfImageView[i].addLine();
                matOfAlien[num][arrOfImageView[i].getRow()].setVisibility(View.INVISIBLE);
                Log.d("line", "" + arrOfImageView[i].getLine() + " " + curPos);
                if (arrOfImageView[i].getRow() == curPos && arrOfImageView[i].getLine() == MAX_TO_ARRIVE) {
                    if (type.equals("alien")) {
                        lives--;
                        updateLivesViews();
                    } else if (type.equals("star")) {
                        score = score + 100;
                        panel_LBL_score.setText("" + score);
                        if ((score + 1000) % 1000 == 0 && needToSpeedUp) {
                            Log.i("time" , ""+time);
                            if(time>100) {
                                time = time - 100;
                                curTime = time;
                                needToSpeedUp = false;
                            }
                        }
                    }
                }
            }
            if (arrOfImageView[i].getLine() < MAX_TO_ARRIVE) {
                if (type.equals("alien")) {
                    matOfAlien[arrOfImageView[i].getLine()][arrOfImageView[i].getRow()].setImageResource(R.drawable.img_alien);
                } else if (type.equals("star")) {
                    matOfAlien[arrOfImageView[i].getLine()][arrOfImageView[i].getRow()].setImageResource(R.drawable.img_star);
                }
                matOfAlien[arrOfImageView[i].getLine()][arrOfImageView[i].getRow()].setVisibility(View.VISIBLE);
            }
        }
        Log.i("here1" , ""+time);
    }

    private void updateLivesViews() {
        if(lives>0) {
            MediaPlayer ring = MediaPlayer.create(context, R.raw.crash_sound);
            ring.start();
        }
        arrOfLife[lives].setVisibility(View.INVISIBLE);
        if (lives == 0) {
            MediaPlayer ring = MediaPlayer.create(context, R.raw.end_game_sound);
            ring.start();
            stopTheGame();
            vibrate();
        }
    }

    public void moveTheSpaceShip(int pos) {
        for (int i = 0; i < NUM_OF_LANES; i++) {
            if (i != pos) {
                getArrOfSpaceShip().get(i).setVisibility(View.INVISIBLE);
            } else
                getArrOfSpaceShip().get(i).setVisibility(View.VISIBLE);
        }
        setCurPos(pos);
    }

    private void stopTheGame() {

        // game.getPanel_Life_Score_layout().setVisibility(View.GONE);
        getPanel_lanes().setVisibility(View.GONE);
        endGame = false;
        if (getChoose() == 0) {//press
            getPanel_below_layout().setVisibility(View.GONE);
            getPanel_IMG_right().setVisibility(View.GONE);
            getPanel_IMG_left().setVisibility(View.GONE);
        }
        if (getChoose() == 1) {//sensor

        }
        //get user name
        mainActivity.endGame();
        stopTicker();
    }

    private void vibrate() {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }

    private void cancelTheTimer() {
        timer.cancel();
    }

    public void stopTicker() {
        cancelTheTimer();
    }

    public boolean getEndGame() {
        return endGame;
    }

    public void speedUp(float y) {
        if(y<curY){
           // Log.i("bb","time "+time);
            if(time>0) {
                time = time - 10;
               // Log.i("here" , ""+time);
            }

        }
        else {
            time = curTime;
        }
    }
}
