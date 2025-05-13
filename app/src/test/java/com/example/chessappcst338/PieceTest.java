package com.example.chessappcst338;

import junit.framework.TestCase;

import java.util.ArrayList;

public class PieceTest extends TestCase {

    public void testGetColor() {
        Piece piece = new Pawn("white", 1,1);
        assertEquals("white", piece.getColor());

        piece = new Rook("black", 1,1);
        assertEquals("black", piece.getColor());

        piece = new Knight("white", 1,1);
        assertEquals("white", piece.getColor());

        piece = new Bishop("black", 1,1);
        assertEquals("black", piece.getColor());

        piece = new Queen("white", 1,1);
        assertEquals("white", piece.getColor());

        piece = new King("black", 1,1);
        assertEquals("black", piece.getColor());

    }

    public void testTestGetName() {
        Piece piece = new Pawn("white", 1,1);
        assertEquals("pawn", piece.getName());

        piece = new Rook("black", 1,1);
        assertEquals("rook", piece.getName());

        piece = new Knight("white", 1,1);
        assertEquals("knight", piece.getName());

        piece = new Bishop("black", 1,1);
        assertEquals("bishop", piece.getName());

        piece = new Queen("white", 1,1);
        assertEquals("queen", piece.getName());

        piece = new King("black", 1,1);
        assertEquals("king", piece.getName());
        
    }


    public void testQueenPossibleMoves() {
        Board board = new Board();
        board.reset();
        Piece queen = new Queen("white", 0, 0);
        board.setPieceAt(3, 3, queen);
        ArrayList<Move> moves = queen.possibleMoves(3, 3, board);
        assertEquals(19, moves.size());
    }

    public void testBishopPossibleMoves() {
        Board board = new Board();
        board.reset();
        Piece bishop = new Bishop("white", 0, 0);
        board.setPieceAt(3, 3, bishop);
        ArrayList<Move> moves = bishop.possibleMoves(3, 3, board);
        assertEquals(8, moves.size());
    }

    public void testKnightPossibleMoves() {
        Board board = new Board();
        board.reset();
        Piece knight = new Knight("white", 0, 0);
        board.setPieceAt(3, 3, knight);
        ArrayList<Move> moves = knight.possibleMoves(3, 3, board);
        assertEquals(8, moves.size());
    }

    public void testRookPossibleMoves() {
        Board board = new Board();
        board.reset();

        Piece rook = new Rook("white", 0, 0);
        board.setPieceAt(3, 3, rook);

        ArrayList<Move> moves = rook.possibleMoves(3, 3, board);
        assertEquals(11, moves.size());
    }

    public void testPawnPossibleMoves() {
        Board board = new Board();
        board.reset();
        Piece pawn = new Pawn("white", 0, 0);
        board.setPieceAt(4, 4, pawn);
        Piece piece = new Pawn("black", 0, 0);
        board.setPieceAt(3, 3, piece);
        ArrayList<Move> moves = piece.possibleMoves(3, 3, board);
        assertEquals(2, moves.size());
    }

    public void testKingPossibleMoves() {
        Board board = new Board();
        board.reset();
        Piece king = new King("white", 0, 0);
        board.setPieceAt(3, 3, king);
        ArrayList<Move> moves = king.possibleMoves(3, 3, board);
        assertEquals(8, moves.size());
    }
}