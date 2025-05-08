package com.example.chessappcst338;

import java.util.ArrayList;

public abstract class Piece {
    public String color;
    public String name;

    public Piece(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public abstract ArrayList<Move> possibleMoves(int x, int y, Board board);
}
