package com.example.hm1.dataBase;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ScoreHandle {
    private final static int MAX_PLAYERS = 10;
    public final static String WINNER_LIST = "WINNER LIST";

    public static void updateRecord(ScoreDataBase scoreDataBase, Score score) {
        boolean needToAdd = true;
        ArrayList<Score> scores = scoreDataBase.getScores();
        for(int i=0;i<scoreDataBase.getScores().size();i++){
            Log.i("score","in update: "+scores.get(i).getLat() +" "+scores.get(i).getLon() +" "+ scores.get(i).getScore() +" "+scores.get(i).getPlayerName());
            if(scoreDataBase.getScores().get(i).getPlayerName().equals(score.getPlayerName())){
                if(score.getScore()>=scoreDataBase.getScores().get(i).getScore()){
                    scoreDataBase.getScores().get(i).setScore(score.getScore());
                    needToAdd = false;
                    break;
                }
            }
        }
        Log.i("hh",""+needToAdd);
        if(needToAdd)
            scoreDataBase.getScores().add(score);
        scoreDataBase.getScores().sort((a, b) -> (int) (b.getScore() - a.getScore()));
        if (scoreDataBase.getScores().size() > MAX_PLAYERS) {
            scoreDataBase.getScores().remove(MAX_PLAYERS);
        }
        saveRecord(scoreDataBase);
    }

    private static void saveRecord(ScoreDataBase scoreDataBase) {
        MSPV3.getMe().putString(WINNER_LIST, new Gson().toJson(scoreDataBase));
    }

    public static ScoreDataBase getRecordDBFromSP() {
        ScoreDataBase scoreDataBase = new Gson().fromJson(MSPV3.getMe().getString(WINNER_LIST, null), ScoreDataBase.class);
        if (scoreDataBase == null) {
            scoreDataBase = new ScoreDataBase();
        }
        return scoreDataBase;
    }


    public static void deleteTheList(ScoreDataBase scoreDataBase) {
        MSPV3.getMe().Delete(WINNER_LIST);

    }
}
