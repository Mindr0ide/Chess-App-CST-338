package com.example.chessappcst338;

import java.util.ArrayList;

public class King extends Piece {
    public King(String color) {
        super(color, "king");
    }

    @Override
    public ArrayList<Move> getPossibleMoves(Position position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();

        // All 8 possible king moves
        int[][] kingMoves = {
                {1, 0}, {1, 1}, {0, 1}, {-1, 1},
                {-1, 0}, {-1, -1}, {0, -1}, {1, -1}
        };

        for (int[] move : kingMoves) {
            int newX = getX() + move[0];
            int newY = getY() + move[1];
            if (isValidSquare(newX, newY, board)) {
                moves.add(new Move(new Position(getX(), getY()), new Position(newX, newY)));
            }
        }

        // TODO: Implement castling

        return moves;
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "♔" : "♚";
    }
}