package com.example.chessappcst338;

import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(String color) {
        super(color, "queen");
    }


    @Override
    public ArrayList<Move> getPossibleMoves(Position position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();

        // Combine rook and bishop movements
        // Horizontal and vertical
        addLinearMoves(getX(), getY(), 1, 0, board, moves);
        addLinearMoves(getX(), getY(), -1, 0, board, moves);
        addLinearMoves(getX(), getY(), 0, 1, board, moves);
        addLinearMoves(getX(), getY(), 0, -1, board, moves);

        // Diagonal
        addLinearMoves(getX(), getY(), 1, 1, board, moves);
        addLinearMoves(getX(), getY(), -1, 1, board, moves);
        addLinearMoves(getX(), getY(), 1, -1, board, moves);
        addLinearMoves(getX(), getY(), -1, -1, board, moves);

        return moves;
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "♕" : "♛";
    }
}