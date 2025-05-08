package com.example.chessappcst338;

import junit.framework.TestCase;

public class BoardTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testReset() {
        Board board = new Board();
        board.reset();

        // Check if the pieces are set correctly
        assertEquals("black", board.getPieceAt(0, 0).getColor());
        assertEquals("rook", board.getPieceAt(0, 0).getName());

        assertEquals("black", board.getPieceAt(0, 0).getColor());
        assertEquals("knight", board.getPieceAt(0, 0).getName());
        

    }

    public void testGetPieceAt() {
        Board board = new Board();
        board.reset();

        assertEquals("black", board.getPieceAt(0, 0).getColor());
        assertEquals("rook", board.getPieceAt(0, 0).getName());

        assertNull(board.getPieceAt(2, 2));
    }

    public void testSetPieceAt() {
    }
}