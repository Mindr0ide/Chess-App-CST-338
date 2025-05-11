package com.example.chessappcst338;

public class Move {
    private Position from;
    private Position to;
    private Piece capturedPiece;
    private Piece promotedTo;
    private boolean isCastling;
    private boolean isEnPassant;

    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    // Getters and setters
    public Position getFrom() { return from; }
    public Position getTo() { return to; }
    public Piece getCapturedPiece() { return capturedPiece; }
    public void setCapturedPiece(Piece capturedPiece) { this.capturedPiece = capturedPiece; }
    public Piece getPromotedTo() { return promotedTo; }
    public void setPromotedTo(Piece promotedTo) { this.promotedTo = promotedTo; }
    public boolean isCastling() { return isCastling; }
    public void setCastling(boolean castling) { isCastling = castling; }
    public boolean isEnPassant() { return isEnPassant; }
    public void setEnPassant(boolean enPassant) { isEnPassant = enPassant; }
}
