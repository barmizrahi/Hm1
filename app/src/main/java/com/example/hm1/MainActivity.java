package com.example.hm1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ImageView panel_IMG_bone1;
    private ImageView panel_IMG_bone2;
    private ImageView panel_IMG_bone3;
    private ImageView panel_IMG_cat1;
    private ImageView panel_IMG_cat2;
    private ImageView panel_IMG_cat3;
    private ImageView panel_IMG_dog;
    private ImageView panel_IMG_left;
    private ImageView panel_IMG_right;
    private TextView panel_LBL_score;
    private LinearLayout panel_main_layout;
    private LinearLayout panel_lanes;
    private ArrayList<ImageView> arrOfSpaceShip;
    private ArrayList<LinearLayout> arrOfLayout;
    private ImageView[][] matOfAlien;
    private ImageView[][] matOfBonus;
    private randomAlien[] arrOfRandomAlien;
    private randomAlien[] arrOfRandomBonus;
    private ImageView[] arrOfLife;
    private boolean needToGen = true;
    private Timer timer = new Timer();
    private int lives = 3 , score = 0 ,genBonus,bonus,curntPos=1,insertToArrOfRandomAlien=0,insertToArrOfRandomBonus = 0;
    private final int NUM_OF_LANES = 3;
    private final int MAX_TO_ARRIVE = 6;
    private randomAlien ra;
    Context context = null;
    LinearLayout.LayoutParams linearlayoutParam = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        context = this;
        RelativeLayout.LayoutParams rootlayoutParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < NUM_OF_LANES; i++) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            ImageView spaceShip = new ImageView(context);
            spaceShip.setId(i);//check this
            spaceShip.setLayoutParams(new LinearLayout.LayoutParams(130, 50));
            spaceShip.setImageResource(R.drawable.img_spaceship);
            if (i != 1) {
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
            for (int j = 0; j < 6; j++) {
                ImageView alien = new ImageView(context);
                alien.setId(i);
                alien.setLayoutParams(new LinearLayout.LayoutParams(100, 100)); // value is in pixels
                alien.setImageResource(R.drawable.img_alien);
                matOfAlien[j][i] = alien;
                linearLayout1.addView(alien);
                alien.setVisibility(View.INVISIBLE);

                ImageView bonus = new ImageView(context);
                bonus.setId(i);
                bonus.setLayoutParams(new LinearLayout.LayoutParams(100, 100)); // value is in pixels
                bonus.setImageResource(R.drawable.img_star);
                matOfBonus[j][i] = bonus;
                linearLayout1.addView(bonus);
                bonus.setVisibility(View.INVISIBLE);
            }
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            arrOfLayout.add(linearLayout1);
            arrOfLayout.get(i).addView(spaceShip, lp);
            panel_lanes.addView(linearLayout1);
        }

        setContentView(panel_main_layout);

        panel_IMG_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrOfSpaceShip.get(2).getVisibility() != View.VISIBLE) {
                    arrOfSpaceShip.get(curntPos + 1).setVisibility(View.VISIBLE);
                    arrOfSpaceShip.get(curntPos).setVisibility(View.INVISIBLE);
                    curntPos += 1;
                }
            }
        });
        panel_IMG_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrOfSpaceShip.get(0).getVisibility() != View.VISIBLE) {
                    arrOfSpaceShip.get(curntPos - 1).setVisibility(View.VISIBLE);
                    arrOfSpaceShip.get(curntPos).setVisibility(View.INVISIBLE);
                    curntPos -= 1;
                }
            }
        });
    }

    private void startTicker() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gen();
                    }
                });
            }
        }, 0, 1000);
    }

    private void gen() {
        if (needToGen) {
            needToGen = false;
            int max = 2, min = 0, lane = 0;//new Random().nextInt((max - min) + 1) + min;
            int max1 = 1 , min1 = 0;
            genBonus = new Random().nextInt((max1 - min1) + 1) + min1;
            if(genBonus==0) { //then gen an alien
                if (insertToArrOfRandomAlien == NUM_OF_LANES + 1) {
                    insertToArrOfRandomAlien = 0;
                  //  arrOfRandomAlien[insertToArrOfRandomAlien].setRow(lane);
                 //   arrOfRandomAlien[insertToArrOfRandomAlien].setLine(0);
                }
                arrOfRandomAlien[insertToArrOfRandomAlien] = new randomAlien(0, lane);
                ra = arrOfRandomAlien[insertToArrOfRandomAlien];
                matOfAlien[ra.getLine()][ra.getRow()].setVisibility(View.VISIBLE);
                insertToArrOfRandomAlien++;
            }
            else{ // need to gen a bonus
                if (insertToArrOfRandomBonus == NUM_OF_LANES + 1) {
                    insertToArrOfRandomBonus = 0;
                    //  arrOfRandomAlien[insertToArrOfRandomAlien].setRow(lane);
                    //   arrOfRandomAlien[insertToArrOfRandomAlien].setLine(0);
                }
                arrOfRandomBonus[insertToArrOfRandomBonus] = new randomAlien(0, lane);
                ra = arrOfRandomBonus[insertToArrOfRandomBonus];
                matOfBonus[ra.getLine()][ra.getRow()].setVisibility(View.VISIBLE);
                insertToArrOfRandomBonus++;
            }
        } else {
            needToGen = true;
        }
        //Log.i("myt",""+needToGen + " genbonus "+genBonus + " insertToArrOfRandomBonus " + insertToArrOfRandomBonus+" insertToArrOfRandomAlien " +insertToArrOfRandomAlien);
           for (int i = 0; i < NUM_OF_LANES + 1; i++) {
            int num = 0;
            if (arrOfRandomAlien[i] != null) {
                if (i != insertToArrOfRandomAlien - 1 || needToGen || genBonus==1) {
                    if (arrOfRandomAlien[i].getLine() >= MAX_TO_ARRIVE) {
                        num = 5;
                    } else {
                        num = arrOfRandomAlien[i].getLine();
                    }
                    //Log.i("mys" , "alien "+num+ " "+i);
                    matOfAlien[num][arrOfRandomAlien[i].getRow()].setVisibility(View.INVISIBLE);
                    arrOfRandomAlien[i].addLine();
                    if (arrOfRandomAlien[i].getRow() == curntPos && arrOfRandomAlien[i].getLine() == MAX_TO_ARRIVE) {
                        lives--;
                        updateLivesViews();
                    }
                }
                if (arrOfRandomAlien[i].getLine() < MAX_TO_ARRIVE) {
                    Log.i("my", "alien index: " + i + " ,line: " + arrOfRandomAlien[i].getLine() + " ,row " + arrOfRandomAlien[i].getRow());
                    matOfAlien[arrOfRandomAlien[i].getLine()][arrOfRandomAlien[i].getRow()].setVisibility(View.VISIBLE);
                }
                // if(arrOfRandomCat[i].getLine()==5 && i==0){
                //   Log.i("my","here");
                //   matOfCats[5][arrOfRandomCat[i].getRow()].setVisibility(View.VISIBLE);
                // }
            }
                if (arrOfRandomBonus[i] != null) {
                    if (i != insertToArrOfRandomBonus - 1 || needToGen || genBonus==0) {
                        if (arrOfRandomBonus[i].getLine() >= MAX_TO_ARRIVE) {
                            num = 5;
                        } else {
                            num = arrOfRandomBonus[i].getLine();
                        }
                        //Log.i("mys" , "bonus "+num+" "+i);
                        matOfBonus[num][arrOfRandomBonus[i].getRow()].setVisibility(View.INVISIBLE);
                        arrOfRandomBonus[i].addLine();
                        if (arrOfRandomBonus[i].getRow() == curntPos && arrOfRandomBonus[i].getLine() == MAX_TO_ARRIVE) {
                            score = score + 100;
                            panel_LBL_score.setText("" + score);
                        }
                    }
                    if (arrOfRandomBonus[i].getLine() < MAX_TO_ARRIVE) {
                        Log.i("my", "bonus index: " + i + " ,line: " + arrOfRandomBonus[i].getLine() + " ,row " + arrOfRandomBonus[i].getRow());
                        matOfBonus[arrOfRandomBonus[i].getLine()][arrOfRandomBonus[i].getRow()].setVisibility(View.VISIBLE);
                    }
                }

        }

    }


    private void findView() {
        arrOfSpaceShip = new ArrayList<>(NUM_OF_LANES);
        panel_LBL_score = findViewById(R.id.panel_LBL_score);
        panel_IMG_right = findViewById(R.id.panel_IMG_right);
        panel_IMG_left = findViewById(R.id.panel_IMG_left);
        arrOfLayout = new ArrayList(NUM_OF_LANES);
        arrOfRandomAlien = new randomAlien[NUM_OF_LANES+1];
        arrOfRandomBonus = new randomAlien[NUM_OF_LANES+1];
        matOfAlien = new ImageView[MAX_TO_ARRIVE][NUM_OF_LANES];
        matOfBonus = new ImageView[MAX_TO_ARRIVE][NUM_OF_LANES];
        panel_main_layout = findViewById(R.id.panel_main_layout);
        panel_lanes = findViewById(R.id.panel_lanes);
        arrOfLife = new ImageView[]{
                findViewById(R.id.panel_IMG_life1),
                findViewById(R.id.panel_IMG_life2),
                findViewById(R.id.panel_IMG_life3)
        };
    }

    private void updateLivesViews() {
        if (lives == 0) {
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
    private void stopTicker() {
        timer.cancel();
    }
    protected void onStart() {
        super.onStart();
        startTicker();
    }
    protected void onStop() {
        super.onStop();
        stopTicker();
    }
}