package com.example.hm1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SaveScoreActivity extends AppCompatActivity {
    private com.google.android.material.textview.MaterialTextView panel_LBL_show_score;
    private EditText panel_LBL_insert_name;
    private com.google.android.material.button.MaterialButton panel_BTN_ok_name;
    private int score;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_score);
        this.bundle = getIntent().getBundleExtra("BUNDLE");
        score = bundle.getInt("SCORE");
        findView();
        panel_LBL_show_score.setText(panel_LBL_show_score.getText().toString()+ score);
        initOkButton();


    }

    private void initOkButton() {
        panel_BTN_ok_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                name = panel_LBL_insert_name.getText().toString();
                insertDataAndStartActivity(name);
                finish();

            }
        });
    }

    private void insertDataAndStartActivity(String playerName) {
        bundle.putString("PLAYER NAME", playerName);
        bundle.putInt("SCORE", score);
        Intent myIntent = new Intent(this, top_ten_and_map.class);
        myIntent.putExtra("BUNDLE", bundle);
        startActivity(myIntent);

        finish();
    }


    public void findView() {
        panel_BTN_ok_name = findViewById(R.id.panel_BTN_ok_name);
        panel_LBL_insert_name = findViewById(R.id.panel_LBL_insert_name);
        panel_LBL_show_score = findViewById(R.id.panel_LBL_show_score);

    }

    @Override
    public void finish() {
        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.putExtra("BUNDLE", bundle);
        startActivity(myIntent);
        super.finish();
    }
}