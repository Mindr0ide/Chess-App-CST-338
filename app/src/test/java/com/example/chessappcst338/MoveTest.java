package com.example.chessappcst338;

import junit.framework.TestCase;

public class MoveTest extends TestCase {

    public void testGetToX() {
        Piece piece = new Pawn("white");
        Piece captured = new Bishop("black");
        Move move = new Move(1, 2, 7, 5, piece, captured);

        assertEquals(7, move.getToX());
    }

    public void testGetToY() {
        Piece piece = new Pawn("white");
        Piece captured = new Bishop("black");
        Move move = new Move(1, 2, 7, 5, piece, captured);

        assertEquals(5, move.getToY());
    }

    public void testGetFromX() {
        Piece piece = new Pawn("white");
        Piece captured = new Bishop("black");
        Move move = new Move(1, 2, 7, 5, piece, captured);

        assertEquals(1, move.getFromX());
    }

    public void testGetFromY() {
        Piece piece = new Pawn("white");
        Piece captured = new Bishop("black");
        Move move = new Move(1, 2, 7, 5, piece, captured);

        assertEquals(2, move.getFromY());
    }

    public void testGetPiece() {
        Piece piece = new Queen("white");
        Move move = new Move(3, 3, 4, 4, piece, null);

        assertEquals(piece, move.getPiece());
    }

    public void testGetCapturedPiece() {
        Piece piece = new King("black");
        Piece captured = new Rook("white");
        Move move = new Move(4, 4, 5, 6, piece, captured);

        assertEquals(captured, move.getCapturedPiece());
    }
}