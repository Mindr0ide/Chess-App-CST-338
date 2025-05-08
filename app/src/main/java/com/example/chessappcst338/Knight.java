package com.example.chessappcst338;

import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(String color) {
        super(color, "knight");
    }

    @Override
    public ArrayList<Move> possibleMoves(int x, int y, Board board) {
        return null;
    }
}