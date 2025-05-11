package com.example.chessappcst338;

import junit.framework.TestCase;

public class BoardTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testReset() {
        Board board = new Board();
        board.resetBoard();

        // Check if the pieces are set correctly
        assertEquals("black", board.getPieceAt(new Position(0, 0)).getColor());
        assertEquals("rook", board.getPieceAt(new Position(0, 0)).getName());

        assertEquals("black", board.getPieceAt(new Position(0, 0)).getColor());
        assertEquals("knight", board.getPieceAt(new Position(0, 0)).getName());
        

    }

    public void testGetPieceAt() {
        Board board = new Board();
        board.resetBoard();

        assertEquals("black", board.getPieceAt(new Position(0, 0)).getColor());
        assertEquals("rook", board.getPieceAt(new Position(0, 0)).getName());

        assertNull(board.getPieceAt(new Position(2, 2)));
    }

    public void testSetPieceAt() {
    }
}