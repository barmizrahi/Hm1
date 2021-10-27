package com.example.hm1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}