package com.example.chessappcst338;

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(String color) {
        super(color, "bishop");
    }


    @Override
    public ArrayList<Move> getPossibleMoves(Position position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();

        // Diagonal movements
        addLinearMoves(getX(), getY(), 1, 1, board, moves);   // Down-right
        addLinearMoves(getX(), getY(), -1, 1, board, moves);  // Down-left
        addLinearMoves(getX(), getY(), 1, -1, board, moves);  // Up-right
        addLinearMoves(getX(),getY(), -1, -1, board, moves); // Up-left

        return moves;
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "♗" : "♝";
    }
}