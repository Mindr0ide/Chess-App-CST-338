package com.example.chessappcst338;

import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(String color) {
        super(color, "queen");
    }

    @Override
    public ArrayList<Move> possibleMoves(int x, int y, Board board) {
        return null;
    }
}
