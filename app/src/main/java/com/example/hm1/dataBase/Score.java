package com.example.hm1.dataBase;

public class Score {
    private String playerName;
    private int score;
    private double lat;
    private double lon;

    public Score() {
        playerName = "";
        score = 0;
        lat = 0.0;
        lon = 0.0;
    }


    public Score setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }


    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public Score setScore(int score) {
        this.score = score;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Score setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Score setLon(double lon) {
        this.lon = lon;
        return this;
    }
}
