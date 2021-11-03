package com.example.hm1;

public class randomCat {
    int line;
    int col;

    public randomCat(int line ,int col){
        this.line = line;
        this.col = col;
    }
     public void addCol(){
        col++;
     }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col){
        this.col = col;
    }

    public void setLine(int line){
        this.line = line;
    }
}
