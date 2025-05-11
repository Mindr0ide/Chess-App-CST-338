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
            int nx = x + offset[0];
            int ny = y + offset[1];
            if (nx >= 0 && nx < 8 && ny >= 0 && ny < 8) {
                Piece target = board.getPieceAt(nx, ny);
                if (target == null || !target.color.equals(this.color)) {
                    moves.add(new Move(nx, ny, this));
                }
            }
        }

        return moves;
    }
}