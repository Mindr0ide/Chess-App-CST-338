package com.example.chessappcst338;

import java.util.ArrayList;

public class King extends Piece {

    public King(String color) {
        super(color, "king");
    }

    @Override
    public ArrayList<Move> possibleMoves(int x, int y, Board board) {
        return null;
    }
}