package com.example.chessappcst338;

import java.util.ArrayList;

public class Rook extends Piece {
    public Rook(String color) {
        super(color, "rook");
    }

    @Override
    public ArrayList<Move> getPossibleMoves(Position position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();

        // Horizontal and vertical movements
        addLinearMoves(getX(), getY(), 1, 0, board, moves);  // Right
        addLinearMoves( getX(), getY(), -1, 0, board, moves); // Left
        addLinearMoves(getX(), getY(), 0, 1, board, moves);  // Down
        addLinearMoves(getX(), getY(), 0, -1, board, moves); // Up

        return moves;
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "♖" : "♜";
    }
}