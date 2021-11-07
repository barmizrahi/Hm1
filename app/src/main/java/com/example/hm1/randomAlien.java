package com.example.hm1;

public class randomAlien {
    int line;
    int row;

    public randomAlien(int line , int row){
        this.line = line;
        this.row = row;
    }
     public void addLine(){
        line++;
     }

    public int getLine() {
        return line;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row){
        this.row = row;
    }

    public void setLine(int line){
        this.line = line;
    }
}
