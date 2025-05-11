package com.example.chessappcst338;

import java.util.ArrayList;

public abstract class Piece {
    private int x;
    private int y;
    protected String color;
    protected String name;
    protected boolean hasMoved = false;

    public Piece(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public abstract ArrayList<Move> getPossibleMoves(Position position, Board board);

    private void addCaptureMove(Position from, int toCol, int toRow, Board board, ArrayList<Move> moves) {
        if (toCol >= 0 && toCol < 8 && toRow >= 0 && toRow < 8) {
            Position to = new Position(toCol, toRow);
            Piece target = board.getPieceAt(to);
            if (target != null && !target.getColor().equals(color)) {
                moves.add(new Move(from, to));
            }
            // TODO: Add en passant logic here
        }
    }

    protected boolean isValidSquare(int x, int y, Board board) {
        // Check if square is within bounds
        if (x < 0 || x >= 8 || y < 0 || y >= 8) {
            return false;
        }

        // Square is valid if empty or contains opponent's piece
        Piece target = board.getPieceAt(new Position(x, y));
        return target == null || !target.getColor().equals(this.color);
    }

    protected void addLinearMoves(int x, int y, int dx, int dy, Board board, ArrayList<Move> moves) {
        int newX = x + dx;
        int newY = y + dy;

        while (isValidSquare(newX, newY, board)) {
            moves.add(new Move(new Position(x, y), new Position(newX, newY)));

            // Stop if we capture a piece
            if (board.getPieceAt(new Position(newX, newY)) != null) {
                break;
            }

            newX += dx;
            newY += dy;
        }
    }

    public Piece getClone() {
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract String getSymbol();
}
