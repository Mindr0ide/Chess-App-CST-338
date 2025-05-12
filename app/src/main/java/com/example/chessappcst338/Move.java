package com.example.chessappcst338;

public class Move {
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;
    private Piece piece;

    private Piece capturedPiece;

    public Move(int fromX, int fromY, int toX, int toY, Piece piece/*, Piece capturedPiece*/) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.piece = piece;
        /*this.capturedPiece = capturedPiece;*/
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }

    public int getFromX() {
        return fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public Piece getPiece() {
        return piece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }
}
