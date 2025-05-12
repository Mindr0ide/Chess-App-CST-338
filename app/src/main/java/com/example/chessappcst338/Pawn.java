package com.example.chessappcst338;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(String color, int x, int y) {
        super(color, "pawn", x, y);
    }

    @Override
    public ArrayList<Move> possibleMoves(int x, int y, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int direction = color.equals("white") ? -1 : 1;  // White moves up (-1), black moves down (+1)
        int startRow = color.equals("white") ? 6 : 1;    // White starts at row 6, black at row 1

        moves.add(new Move(x, y, x + direction, y, this));

        return moves;
    }

    private boolean isValidSquare(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    private void checkCapture(int targetX, int targetY, Board board, ArrayList<Move> moves) {
        if (isValidSquare(targetX, targetY)) {
            Piece target = board.getPieceAt(targetX, targetY);
            if (target != null && !target.color.equals(this.color)) {
                moves.add(new Move(getX(), getY(), targetX, targetY, this));
            }
        }
    }
}
