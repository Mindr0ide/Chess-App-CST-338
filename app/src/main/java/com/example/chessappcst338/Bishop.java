package com.example.chessappcst338;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(String color) {
        super(color, "bishop");
    }

    @Override
    public ArrayList<Move> possibleMoves(int x, int y, Board board) {
        return null;
    }
}