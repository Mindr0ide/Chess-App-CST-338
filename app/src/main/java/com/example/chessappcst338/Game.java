package com.example.chessappcst338;

import java.util.ArrayList;
import java.util.Stack;

public class Game {
    private Board board;
    private Stack<Move> history;
    private boolean isWhiteTurn;
    private GameStatus status;

    public Game() {
        this.board = new Board();
        this.history = new Stack<>();
        this.isWhiteTurn = true;
        this.status = GameStatus.IN_PROGRESS;
        initializeBoard();
    }

    private void initializeBoard() {
        // Set up the initial chess board configuration
        board.resetBoard();
    }

    public void play(Move move) {
        if (status != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("Game is already over");
        }

        if (!isValidMove(move)) {
            throw new IllegalArgumentException("Invalid move");
        }

        // Execute the move
        Piece capturedPiece = board.movePiece(move);
        history.push(move);

        // Store captured piece in the move object
        move.setCapturedPiece(capturedPiece);

        // Check for game-ending conditions
        checkGameStatus();

        // Switch turns
        isWhiteTurn = !isWhiteTurn;
    }

    public void undo() {
        if (history.isEmpty()) {
            throw new IllegalStateException("No moves to undo");
        }

        Move lastMove = history.pop();
        board.undoMove(lastMove);
        isWhiteTurn = !isWhiteTurn;
        status = GameStatus.IN_PROGRESS; // Reset status if game was over
    }

    private boolean isValidMove(Move move) {
        // Basic validation
        if (move == null || move.getFrom() == null || move.getTo() == null) {
            return false;
        }

        Piece piece = board.getPieceAt(move.getFrom());
        boolean isWhite;
        if (piece.getColor().equals("white")){
            isWhite = true;
        }
        else {
            isWhite = false;
        }

        // Check if piece exists and belongs to current player
        if (piece == null || isWhite != isWhiteTurn) {
            return false;
        }

        // Add more specific validation logic here
        // For example: check if the move follows chess rules for this piece

        return true;
    }

    private void checkGameStatus() {
        // Check for checkmate, stalemate, etc.
        String color;
        if (!isWhiteTurn) {
            color = "black";
        }
        else {
            color = "white";
        }
        if (board.isCheckmate(color)) {
            status = isWhiteTurn ? GameStatus.WHITE_WON : GameStatus.BLACK_WON;
        } else if (board.isStalemate(color)) {
            status = GameStatus.STALEMATE;
        } else if (board.isInsufficientMaterial()) {
            status = GameStatus.DRAW;
        }
        // Add other game-ending conditions
    }

    public void display() {
        board.display();
        System.out.println("Current turn: " + (isWhiteTurn ? "White" : "Black"));
        System.out.println("Game status: " + status);
    }

    public Board getBoard() {
        return board;
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Stack<Move> getHistory() {
        return history;
    }

    public enum GameStatus {
        IN_PROGRESS,
        WHITE_WON,
        BLACK_WON,
        STALEMATE,
        DRAW
    }
}
