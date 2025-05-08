package com.example.chessappcst338;

import java.util.ArrayList;

public abstract class Piece {
    public String color;
    public String name;
//    int x, y;

    public Piece(String color, String name/*, int x, int y*/) {
        this.color = color;
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

//    public (int, int) getPos() {
//        return (x, y);
//    }

//    public int getX() {
//        return x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public void setPos(int x, int y) {
//        this.x = x;
//        this.y = y;
//    }

    public abstract ArrayList<Move> possibleMoves(int x, int y, Board board);
}
