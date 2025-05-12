package com.example.chessappcst338;

import java.util.Stack;

public class Game {
    private Board board;
    private Stack<Move> history;
    private boolean isWhiteTurn;

    public Game() {
        board = new Board();
        board.reset(); // Active l'initialisation complète du plateau
        history = new Stack<>();
        isWhiteTurn = true;
    }

    public void play(Move move) {
        history.push(move);
        board.setPieceAt(move.getToX(), move.getToY(), move.getPiece());
        board.setPieceAt(move.getFromX(), move.getFromY(), null);
        isWhiteTurn = !isWhiteTurn;
    }

    public void undo() {
        if (!history.isEmpty()) {
            Move lastMove = history.pop();

            board.setPieceAt(lastMove.getFromX(), lastMove.getFromY(), lastMove.getPiece());

            board.setPieceAt(lastMove.getToX(), lastMove.getToY(), lastMove.getCapturedPiece());

            isWhiteTurn = !isWhiteTurn;
        } else {
            System.out.println("No moves to undo!");
        }
    }

    public void display() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece p = board.getPieceAt(x, y);
                if (p == null) System.out.print("- ");
                else System.out.print(p.getName().charAt(0) + " ");
            }
            System.out.println();
        }
    }
}
