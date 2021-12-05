package com.example.hm1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hm1.callBacks.CallBack_list;
import com.example.hm1.dataBase.LocationSensor;
import com.example.hm1.dataBase.Score;
import com.example.hm1.dataBase.ScoreDataBase;
import com.example.hm1.dataBase.ScoreHandle;

public class top_ten_and_map extends AppCompatActivity {

    private Bundle bundle;
    private ScoreDataBase scoreDataBase;
    private FragmentPlayers fragmentPlayers;
    private FragmentMap fragmentMap;
    private ScoreHandle scoreHandle;
    private com.google.android.material.button.MaterialButton panel_BTN_return_manu;

    CallBack_list cbList = (i -> {
        Score score = scoreDataBase.getScores().get(i);
        String playerName = score.getPlayerName();
        double lat = score.getLat();
        double lon = score.getLon();
        String score1 = "" + score.getScore();
        fragmentMap.setLocation(playerName, score1, lat, lon);

    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_top_ten_and_map);
        this.bundle = getIntent().getBundleExtra("BUNDLE");
        panel_BTN_return_manu = findViewById(R.id.panel_BTN_return_manu);
        Context context = this;
        panel_BTN_return_manu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, MainActivity.class);
                myIntent.putExtra("BUNDLE", bundle);
                startActivity(myIntent);
            }
        });
        fragmentPlayers = new FragmentPlayers();
        fragmentPlayers.setActivity(this);
        fragmentPlayers.setCallBackList(cbList);
        updateDB();

        fragmentMap = new FragmentMap();
        fragmentMap.setActivity(this);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_gps, fragmentMap).commit();

    }

    private void updateDB() {
        String playerName = bundle.getString("PLAYER NAME");
        int score = bundle.getInt("SCORE");

        scoreDataBase = scoreHandle.getRecordDBFromSP();

        LocationSensor.getMe().getLocation((lat, lon) -> {
            Log.i("hh",""+lat+lon);
            updateLastRecord(lat, lon, score, playerName);
        });

    }


    private void updateLastRecord(double lat, double lon, int score1, String playerName) {
        if (score1 > 0 || playerName != null) {
            Score score = new Score();
            score.setPlayerName(playerName);
            score.setScore(score1);
            score.setLat(lat);
            score.setLon(lon);
            scoreHandle.updateRecord(scoreDataBase, score);
            Log.i("score",""+lat +" "+lon +" "+ score1 +" "+playerName);


        }
        updateList();


    }

    private void updateList() {
        fragmentPlayers.setRecords(scoreDataBase.getScores());
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_players, fragmentPlayers).commit();

    }

    private void deleteTheList(){
        scoreHandle.deleteTheList(scoreDataBase);
        updateList();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    public void finish() {
        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.putExtra("BUNDLE", bundle);
        startActivity(myIntent);
        super.finish();
    }

}