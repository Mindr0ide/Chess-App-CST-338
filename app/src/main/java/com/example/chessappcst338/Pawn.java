package com.example.chessappcst338;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(String color, int x, int y) {
        super(color, "pawn", x, y);
    }

    @Override
    public ArrayList<Move> possibleMoves(int x, int y, Board board) {
        ArrayList<Move> moves = new ArrayList<>();

        int dir = color.equals("white") ? -1 : 1;
        int startRow = color.equals("white") ? 6  : 1;

        // forward move
        Piece forward = board.getPieceAt(x + dir, y);
        if (forward == null) {
            moves.add(new Move(x, y, x + dir, y, this));
            if (x == startRow) {
                Piece forward2 = board.getPieceAt(x + dir * 2, y);
                if (forward2 == null)
                    moves.add(new Move(x, y, x + dir * 2, y, this));
            }
        }

        // pawn captures
        Piece captureRight = board.getPieceAt(x + dir, y + 1);
        if (captureRight != null && !captureRight.getColor().equals(this.color))
            moves.add(new Move(x, y, x + dir, y + 1, this, captureRight));

        Piece captureLeft = board.getPieceAt(x + dir, y - 1);
        if (captureLeft != null && !captureLeft.getColor().equals(this.color))
            moves.add(new Move(x, y, x + dir, y - 1, this, captureLeft));

        return moves;
    }
}
