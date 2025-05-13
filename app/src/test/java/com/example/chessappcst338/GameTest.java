package com.example.chessappcst338;

import junit.framework.TestCase;

public class GameTest extends TestCase {

    public void testPlay() {
        Game game = new Game();
        Piece pawn = new Pawn("white", 1, 0);
        game.getBoard().setPieceAt(1, 0, pawn);
        Move move = new Move(1, 0, 2, 0, pawn);
        game.play(move);

        assertNull(game.getBoard().getPieceAt(1, 0));
        assertEquals(pawn, game.getBoard().getPieceAt(2, 0));

        Piece blackPawn = new Pawn("black", 6, 0);
        game.getBoard().setPieceAt(6, 0, blackPawn);
        Piece whitePawn2 = new Pawn("white", 5, 1);
        game.getBoard().setPieceAt(5, 1, whitePawn2);
        Move captureMove = new Move(5, 1, 6, 0, whitePawn2);
        game.play(captureMove);

        assertEquals(whitePawn2, game.getBoard().getPieceAt(6, 0));
    }

    public void testUndo() {
        Game game = new Game();
        Piece pawn = new Pawn("white", 4, 4);
        game.getBoard().setPieceAt(4, 4, pawn);
        Move move = new Move(4, 4, 5, 4, pawn);
        game.play(move);

        game.undo();

        assertEquals(pawn, game.getBoard().getPieceAt(4, 4));
        assertNull(game.getBoard().getPieceAt(5, 4));
        assertEquals(true, game.isWhiteTurn());
        assertEquals(true, game.getHistory().isEmpty());
    }

    public void testIsGameOver() {
        Game game = new Game();
        Piece king = new King("black", 3, 3);
        game.getBoard().setPieceAt(3, 3, king);
        Piece rook = new Rook("white", 2, 3);
        game.getBoard().setPieceAt(2, 3, rook);

        Move capture = new Move(2, 3, 3, 3, rook);
        game.play(capture);

        assertEquals(true, game.isGameOver());
        assertEquals("white", game.getWinner());
    }
}