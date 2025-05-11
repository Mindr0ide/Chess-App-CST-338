package com.example.chessappcst338;

import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(String color) {
        super(color, "pawn");
    }

    @Override
    public ArrayList<Move> getPossibleMoves(Position position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int direction = color.equals("white") ? -1 : 1;
        int startRow = color.equals("white") ? 6 : 1;

        // Forward move
        if (board.getPieceAt(new Position(getX(), getY() + direction)) == null) {
            moves.add(new Move(new Position(getX(), getY()), new Position(getX(), getY() + direction)));

            // Double move from starting position
            if (getX() == startRow && board.getPieceAt(new Position(getX(), getY() + 2 * direction)) == null) {
                moves.add(new Move(new Position(getX(), getY()), new Position(getX(), getY() + 2 * direction)));
            }
        }

        // Capture moves
        addCaptureMove(getX(), getY(), getX() - 1, getY() + direction, board, moves);
        addCaptureMove(getX(), getY(), getX() + 1, getY() + direction, board, moves);

        // TODO: Implement en passant

        return moves;
    }

    private void addCaptureMove(int fromX, int fromY, int toX, int toY, Board board, ArrayList<Move> moves) {
        if (toX >= 0 && toX < 8 && toY >= 0 && toY < 8) {
            Piece target = board.getPieceAt(new Position(toX, toY));
            if (target != null && !target.getColor().equals(color)) {
                moves.add(new Move(new Position(fromX, fromY), new Position(toX, toY)));
            }
        }
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "♙" : "♟";
    }
}