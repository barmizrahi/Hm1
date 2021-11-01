package com.example.hm1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

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
    private final int NUM_OF_LANES =3;
    private  int curntPos = 1;

    //intent
    Context context=null;

    LinearLayout.LayoutParams linearlayoutParam=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<ImageView> arrOfDogs = new ArrayList<>(3);
        ArrayList<LinearLayout> arrOfLayout = new ArrayList(3);
        panel_main_layout = findViewById(R.id.panel_main_layout);
        panel_lanes = findViewById(R.id.panel_lanes);

        context=this;
        LinearLayout.LayoutParams rootlayoutParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        int top=0;
        for(int i=0;i<NUM_OF_LANES;i++)
        {
            ImageView cat = new ImageView(context);
            cat.setId(i);
            cat.setLayoutParams(new LinearLayout.LayoutParams(200, 200)); // value is in pixels

            cat.setImageResource(R.drawable.img_cat1);
            //android:layout_marginStart="65dp"
            ImageView dog = new ImageView(context);
            dog.setId(i);//check this
            dog.setImageResource(R.drawable.img_dog);
            dog.setLayoutParams(new LinearLayout.LayoutParams(100, 100)); // value is in pixels
            //rootlayoutParam = new LinearLayout.LayoutParams(100, 100);
            rootlayoutParam.gravity = Gravity.CENTER_HORIZONTAL;;
            //dog.setGravity(Gravity.CENTER | Gravity.TOP);
            dog.setLayoutParams(rootlayoutParam);
            if(i!=1){
                dog.setVisibility(View.INVISIBLE);
            }
            arrOfDogs.add(dog);

            LinearLayout linearLayout1=new LinearLayout(context);
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            linearLayout1.setId(i);
            linearlayoutParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            linearLayout1.setBackgroundColor(Color.argb(255,i,50*i+100,100+2*i));
            linearlayoutParam.weight= 1;
            linearLayout1.setLayoutParams(linearlayoutParam);
            linearLayout1.addView(cat);

            arrOfLayout.add(linearLayout1);
            arrOfLayout.get(i).addView(dog);
            panel_lanes.addView(linearLayout1);


        }
/*
        ImageView dog = new ImageView(context);
        dog.setId(0);//check this
        dog.setImageResource(R.drawable.img_dog);
        dog.setLayoutParams(new LinearLayout.LayoutParams(100, 100)); // value is in pixels
        arrOfLayout.get(1).addView(dog);

 */
        setContentView(panel_main_layout);
        panel_IMG_right = findViewById(R.id.panel_IMG_right);
        panel_IMG_left = findViewById(R.id.panel_IMG_left);
        panel_IMG_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrOfDogs.get(2).getVisibility() != View.VISIBLE){
                    arrOfDogs.get(curntPos+1).setVisibility(View.VISIBLE);
                    arrOfDogs.get(curntPos).setVisibility(View.INVISIBLE);
                    curntPos += 1;
                }
            }
        });
        panel_IMG_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrOfDogs.get(0).getVisibility() != View.VISIBLE){
                    arrOfDogs.get(curntPos-1).setVisibility(View.VISIBLE);
                    arrOfDogs.get(curntPos).setVisibility(View.INVISIBLE);
                    curntPos -= 1;
                }
            }
        });
    }
}