package com.example.chessappcst338;

import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(String color) {
        super(color, "knight");
    }

    @Override
    public ArrayList<Move> possibleMoves(int x, int y, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int[][] offsets = {
                {1, 2}, {2, 1}, {-1, 2}, {-2, 1},
                {1, -2}, {2, -1}, {-1, -2}, {-2, -1}
        };
        for (int[] offset : offsets) {
            int newX = x + offset[0];
            int newY = y + offset[1];
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                Piece target = board.getPieceAt(newX, newY);
                if (target == null || !target.color.equals(this.color)) {
                    moves.add(new Move(x, y, newX, newY, this, board.getPieceAt(newX, newY)));

                }
            }
        }

        return moves;
    }
}