package com.example.chessappcst338;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(String color) {
        super(color, "knight");
    }

    @Override
    public ArrayList<Move> getPossibleMoves(Position position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int[][] knightMoves = {
                {2, 1}, {1, 2}, {-1, 2}, {-2, 1},
                {-2, -1}, {-1, -2}, {1, -2}, {2, -1}
        };

        for (int[] move : knightMoves) {
            int newX = getX() + move[0];
            int newY = getY() + move[1];
            if (isValidSquare(newX, newY, board)) {
                moves.add(new Move(new Position(getX(), getY()), new Position(newX, newY)));
            }
        }
        return moves;
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "♘" : "♞";
    }
}