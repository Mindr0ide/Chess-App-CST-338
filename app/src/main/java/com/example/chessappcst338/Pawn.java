package com.example.chessappcst338;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(String color) {
        super(color, "pawn");
    }

    @Override
    public ArrayList<Move> possibleMoves(int x, int y, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int dir = color.equals("white") ? -1 : 1;
        int startRow = color.equals("white") ? 6 : 1;

        // Forward move (1 square)
        if (y + dir >= 0 && y + dir < 8 && board.getPieceAt(x, y + dir) == null) {
            moves.add(new Move(x, y, x, y + dir, this, null));
        }

        // Initial double move
        if (y == startRow && board.getPieceAt(x, y + dir) == null
                && board.getPieceAt(x, y + 2 * dir) == null) {
            moves.add(new Move(x, y, x, y + 2 * dir, this, null));
        }

        // Capture moves
        if (x - 1 >= 0 && y + dir >= 0 && y + dir < 8) {
            Piece diagLeft = board.getPieceAt(x - 1, y + dir);
            if (diagLeft != null && !diagLeft.color.equals(this.color)) {
                moves.add(new Move(x, y, x - 1, y + dir, this, diagLeft));
            }
        }

        if (x + 1 < 8 && y + dir >= 0 && y + dir < 8) {
            Piece diagRight = board.getPieceAt(x + 1, y + dir);
            if (diagRight != null && !diagRight.color.equals(this.color)) {
                moves.add(new Move(x, y, x + 1, y + dir, this, diagRight));
            }
        }

        return moves;
    }
}
