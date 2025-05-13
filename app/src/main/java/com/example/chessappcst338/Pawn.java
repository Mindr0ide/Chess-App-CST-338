package com.example.chessappcst338;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(String color, int x, int y) {
        super(color, "pawn", x, y);
    }

    @Override
    public ArrayList<Move> possibleMoves(int x, int y, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int direction = color.equals("white") ? -1 : 1;
        int startRow = color.equals("white") ? 6 : 1;


        moves.add(new Move(x, y, x + direction, y, this));

        return moves;
    }
}
