package com.example.hm1.dataBase;

import java.util.ArrayList;

public class ScoreDataBase {
    private ArrayList<Score> scores = new ArrayList<>();

    public ScoreDataBase() {
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public ScoreDataBase setRecords(ArrayList<Score> scores) {
        this.scores = scores;
        return this;
    }

}
