package com.example.hm1;

public class randomImageView {
    private int line;
    private int row;
    private String source;

    public randomImageView(int line , int row, String source){
        this.line = line;
        this.row = row;
        this.source = source;
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

    public String getSource(){
        return this.source;
    }

    public void setRow(int row){
        this.row = row;
    }

    public void setLine(int line){
        this.line = line;
    }
}
