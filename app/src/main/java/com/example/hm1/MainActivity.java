package com.example.hm1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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
    private ArrayList<ImageView> arrOfDogs;
    private ArrayList<LinearLayout> arrOfLayout;
    private ImageView[][] matOfCats;
    private randomCat[] arrOfRandomCat;
    private ImageView[] arrOfBones;
    private boolean needToGen = true;
    private Timer timer = new Timer();
    private int lives = 3;
    private final int NUM_OF_LANES = 3;
    private int curntPos = 1;
    private int insertToArrOfRandomCat = 0;
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
            ImageView dog = new ImageView(context);
            dog.setId(i);//check this
            dog.setImageResource(R.drawable.img_dog);
            if (i != 1) {
                dog.setVisibility(View.INVISIBLE);
            }
            arrOfDogs.add(dog);
            LinearLayout linearLayout1 = new LinearLayout(context);
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            linearLayout1.setId(i);
            linearlayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
           // linearLayout1.setBackgroundColor(Color.argb(255, i, 50 * i + 100, 100 + 2 * i));
            linearlayoutParam.weight = 1;
            linearLayout1.setGravity(Gravity.CENTER);
            linearLayout1.setLayoutParams(linearlayoutParam);
            for (int j = 0; j < 6; j++) {
                ImageView cat = new ImageView(context);
                cat.setId(i);
                cat.setLayoutParams(new LinearLayout.LayoutParams(200, 200)); // value is in pixels
                cat.setImageResource(R.drawable.img_cat1);
                matOfCats[j][i] = cat;
                linearLayout1.addView(cat);
                cat.setVisibility(View.INVISIBLE);
            }
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            arrOfLayout.add(linearLayout1);
            arrOfLayout.get(i).addView(dog, lp);
            panel_lanes.addView(linearLayout1);
        }

        setContentView(panel_main_layout);

        panel_IMG_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrOfDogs.get(2).getVisibility() != View.VISIBLE) {
                    arrOfDogs.get(curntPos + 1).setVisibility(View.VISIBLE);
                    arrOfDogs.get(curntPos).setVisibility(View.INVISIBLE);
                    curntPos += 1;
                }
            }
        });
        panel_IMG_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrOfDogs.get(0).getVisibility() != View.VISIBLE) {
                    arrOfDogs.get(curntPos - 1).setVisibility(View.VISIBLE);
                    arrOfDogs.get(curntPos).setVisibility(View.INVISIBLE);
                    curntPos -= 1;
                }
            }
        });
        startTicker();
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
        randomCat rc;
        if (needToGen) {
            int max = 2, min = 0, lane = new Random().nextInt((max - min) + 1) + min;
            if (insertToArrOfRandomCat == NUM_OF_LANES + 1) {
                insertToArrOfRandomCat = 0;
                arrOfRandomCat[insertToArrOfRandomCat].setCol(0);
                arrOfRandomCat[insertToArrOfRandomCat].setLine(lane);
                rc = arrOfRandomCat[insertToArrOfRandomCat];

            } else {
                arrOfRandomCat[insertToArrOfRandomCat].setCol(0);
                arrOfRandomCat[insertToArrOfRandomCat].setLine(lane);
                rc = arrOfRandomCat[insertToArrOfRandomCat];
            }
            matOfCats[rc.getCol()][rc.getLine()].setVisibility(View.VISIBLE);
            insertToArrOfRandomCat++;
            needToGen = false;
        } else {
            needToGen = true;
        }
        for (int i = 0; i < NUM_OF_LANES + 1; i++) {
            int num = 0;
            if (arrOfRandomCat[i] != null) {
                if (i != insertToArrOfRandomCat - 1 || needToGen) {
                    if (arrOfRandomCat[i].getCol() == 6) {
                        num = arrOfRandomCat[i].getCol() - 1;
                    } else {
                        num = arrOfRandomCat[i].getCol();
                    }

                    matOfCats[num][arrOfRandomCat[i].getLine()].setVisibility(View.INVISIBLE);
                    arrOfRandomCat[i].addCol();
                    if (arrOfRandomCat[i].getLine() == curntPos && arrOfRandomCat[i].getCol() == 6) {
                        Log.d("my",""+curntPos);
                        lives--;
                        updateLivesViews();
                    }
                }
                if (arrOfRandomCat[i].getCol() < 6) {
                    matOfCats[arrOfRandomCat[i].getCol()][arrOfRandomCat[i].getLine()].setVisibility(View.VISIBLE);
                }
            }
        }

    }


    private void findView() {
        arrOfDogs = new ArrayList<>(NUM_OF_LANES);
        panel_IMG_right = findViewById(R.id.panel_IMG_right);
        panel_IMG_left = findViewById(R.id.panel_IMG_left);
        arrOfLayout = new ArrayList(NUM_OF_LANES);
        arrOfRandomCat = new randomCat[]{
                new randomCat(0, 0),
                new randomCat(0, 0),
                new randomCat(0, 0),
                new randomCat(0, 0)
        };
        matOfCats = new ImageView[7][NUM_OF_LANES];
        panel_main_layout = findViewById(R.id.panel_main_layout);
        panel_lanes = findViewById(R.id.panel_lanes);
        arrOfBones = new ImageView[]{
                findViewById(R.id.panel_IMG_bone1),
                findViewById(R.id.panel_IMG_bone2),
                findViewById(R.id.panel_IMG_bone3)
        };
    }

    private void updateLivesViews() {
        if (lives == 0) {
            arrOfBones[0].setVisibility(View.VISIBLE);
            arrOfBones[1].setVisibility(View.VISIBLE);
            arrOfBones[2].setVisibility(View.VISIBLE);
            lives = 3;
            Toast.makeText(getApplicationContext(), "Fail, Try Again", Toast.LENGTH_SHORT).show();
        } else
            arrOfBones[lives].setVisibility(View.INVISIBLE);

    }

}