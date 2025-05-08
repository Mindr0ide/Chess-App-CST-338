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
        int start = 2 * dir;
        int normalized = ((start % 10) + 10) % 10;

        if (y == normalized && board.getPieceAt(x, y + dir) == null
                && board.getPieceAt(x, y + dir * 2) == null) {
            moves.add(new Move(x, y + dir * 2, this));
        }
        if (y + dir >= 0 && y + dir < 8 && board.getPieceAt(x, y + 1) == null) {
            moves.add(new Move(x, y + dir, this));
        }

        if (x - 1 >= 0 && y + dir >= 0 && y + dir < 8) {
            Piece diagLeft = board.getPieceAt(x - 1, y + dir);
            if (diagLeft != null && !diagLeft.color.equals(this.color)) {
                moves.add(new Move(x - 1, y + 1, this));
            }
        }

        if (x + 1 >= 0 && y + dir >= 0 && y + dir < 8) {
            Piece diagRight = board.getPieceAt(x + 1, y + dir);
            if (diagRight != null && !diagRight.color.equals(this.color)) {
                moves.add(new Move(x + 1, y + 1, this));
            }
        }

        return moves;
    }
}
