package com.example.chessappcst338;

import junit.framework.TestCase;

public class BoardTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testReset() {
        Board board = new Board();
        board.reset();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 0 || i == 1 || i == 6 || i == 7) {
                    assertNotNull(board.getPieceAt(i, j));
                } else {
                    assertNull(board.getPieceAt(i, j));
                }
            }
        }

        assertEquals("white", board.getPieceAt(7, 0).getColor());
        assertEquals("rook", board.getPieceAt(7, 0).getName());

        assertEquals("white", board.getPieceAt(7, 1).getColor());
        assertEquals("knight", board.getPieceAt(7, 1).getName());

        assertEquals("white", board.getPieceAt(7, 2).getColor());
        assertEquals("bishop", board.getPieceAt(7, 2).getName());

        assertEquals("white", board.getPieceAt(7, 3).getColor());
        assertEquals("queen", board.getPieceAt(7, 3).getName());

        assertEquals("white", board.getPieceAt(7, 4).getColor());
        assertEquals("king", board.getPieceAt(7, 4).getName());

        assertEquals("white", board.getPieceAt(7, 5).getColor());
        assertEquals("bishop", board.getPieceAt(7, 5).getName());

        assertEquals("white", board.getPieceAt(7, 6).getColor());
        assertEquals("knight", board.getPieceAt(7, 6).getName());

        assertEquals("white", board.getPieceAt(7, 7).getColor());
        assertEquals("rook", board.getPieceAt(7, 7).getName());

        for (int i = 0; i < 8; i++) {
            assertEquals("white", board.getPieceAt(6, i).getColor());
            assertEquals("pawn", board.getPieceAt(6, i).getName());
        }

        assertEquals("black", board.getPieceAt(0, 0).getColor());
        assertEquals("rook", board.getPieceAt(0, 0).getName());

        assertEquals("black", board.getPieceAt(0, 1).getColor());
        assertEquals("knight", board.getPieceAt(0, 1).getName());

        assertEquals("black", board.getPieceAt(0, 2).getColor());
        assertEquals("bishop", board.getPieceAt(0, 2).getName());

        assertEquals("black", board.getPieceAt(0, 3).getColor());
        assertEquals("queen", board.getPieceAt(0, 3).getName());

        assertEquals("black", board.getPieceAt(0, 4).getColor());
        assertEquals("king", board.getPieceAt(0, 4).getName());

        assertEquals("black", board.getPieceAt(0, 5).getColor());
        assertEquals("bishop", board.getPieceAt(0, 5).getName());

        assertEquals("black", board.getPieceAt(0, 6).getColor());
        assertEquals("knight", board.getPieceAt(0, 6).getName());

        assertEquals("black", board.getPieceAt(0, 7).getColor());
        assertEquals("rook", board.getPieceAt(0, 7).getName());

        for (int i = 0; i < 8; i++) {
            assertEquals("black", board.getPieceAt(1, i).getColor());
            assertEquals("pawn", board.getPieceAt(1, i).getName());
        }
    }

    public void testGetPieceAt() {
        Board board = new Board();
        board.reset();

        assertEquals("black", board.getPieceAt(0, 0).getColor());
        assertEquals("rook", board.getPieceAt(0, 0).getName());

        assertNull(board.getPieceAt(2, 2));
    }

    public void testSetPieceAt() {
        Board board = new Board();
        board.reset();

        Piece piece = new Queen("white");
        board.setPieceAt(4, 4, piece);

        assertEquals(piece, board.getPieceAt(4, 4));
    }
}