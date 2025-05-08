package com.example.chessappcst338;

public class Move {
    private int x;
    private int y;
    private Piece piece;

    public Move(int x, int y, Piece piece){
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Piece getPiece() {
        return piece;
    }
}
