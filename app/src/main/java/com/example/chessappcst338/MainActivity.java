package com.example.chessappcst338;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Game game;
    private int selectedX = -1;
    private int selectedY = -1;
    private boolean isPieceSelected = false;
    private Button[][] chessSquares = new Button[8][8];
    ArrayList<Move> currentPossibleMoves = new ArrayList<>();

    private static final int BOARD_SIZE = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();
        game.getBoard().reset();

        // Setup undo and reset button
        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(v -> {
            updateStatusText("");
            game.undo();
            updateBoardDisplay();
            resetSelection();
            if (game.isGameOver()) {
                enableBoard();
            }
        });

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(v -> {
            updateStatusText("");
            game = new Game();
            game.getBoard().reset();
            updateBoardDisplay();
            resetSelection();
            enableBoard();
        });

        // initialize chess board
        LinearLayout chessBoard = findViewById(R.id.chessBoard);
        createChessBoard(chessBoard);
        updateBoardDisplay();
    }

    private void updateStatusText(String message) {
        TextView statusText = findViewById(R.id.statusText);
        if (statusText != null) {
            statusText.setText(message);
        }
    }

    private final View.OnClickListener squareClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tag = (String) v.getTag();
            int row = Character.getNumericValue(tag.charAt(7));
            int col = Character.getNumericValue(tag.charAt(8));
            Piece clickedPiece = game.getBoard().getPieceAt(row, col);


            if (game.isGameOver()) {
                return;
            }

            if (!isPieceSelected) {
                if (clickedPiece != null &&
                        ((game.isWhiteTurn() && clickedPiece.getColor().equals("white")) ||
                                (!game.isWhiteTurn() && clickedPiece.getColor().equals("black")))) {

                    selectedX = row;
                    selectedY = col;
                    isPieceSelected = true;
                    currentPossibleMoves = clickedPiece.possibleMoves(row, col, game.getBoard());

                    // Update status text
                    updateStatusText("Selected " + clickedPiece.getName() + " at " +
                            getChessNotation(selectedX, selectedY) +
                            "\nPossible moves: " + formatPossibleMoves());
                }
            } else {
                for (Move move : currentPossibleMoves) {
                    if (move.getToX() == row && move.getToY() == col) {
                        game.play(move);
                        if (game.isGameOver()) {
                            displayGameOver();
                        } else {
                            updateStatusText("Moved " + move.getPiece().getName() +
                                    " from " + getChessNotation(move.getFromX(), move.getFromY()) +
                                    " to " + getChessNotation(move.getToX(), move.getToY()));
                        }
                        updateBoardDisplay();
                        resetSelection();
                        return;
                    }
                }
                resetSelection();
            }
        }
    };

    private void displayGameOver() {
        String winner = game.getWinner();
        String message = "Game Over! " +
                (winner.equals("white") ? "White" : "Black") + " wins by capturing the king!";
        updateStatusText(message);

        // disable further moves
        disableBoard();
    }

    private void disableBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button square = chessSquares[row][col];
                if (square != null) {
                    square.setEnabled(false);
                }
            }
        }
    }

    private void enableBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button square = chessSquares[row][col];
                if (square != null) {
                    square.setEnabled(true);
                }
            }
        }
    }

    private String getChessNotation(int row, int col) {
        char file = (char) ('a' + col);
        int rank = 8 - row;
        return "" + file + rank;
    }

    private String formatPossibleMoves() {
        StringBuilder sb = new StringBuilder();
        for (Move move : currentPossibleMoves) {
            sb.append(getChessNotation(move.getToX(), move.getToY())).append(" ");
        }
        return sb.toString().trim();
    }

    private void showPossibleMovesAsText() {
        // Highlight selected piece
        if (selectedX >= 0 && selectedY >= 0) {
            Button selectedButton = chessSquares[selectedX][selectedY];
            if (selectedButton != null) {
                selectedButton.setBackgroundColor(Color.parseColor("#6495ED")); // Blue
            }
        }

        // Highlight possible moves
        for (Move move : currentPossibleMoves) {
            if (move.getToX() >= 0 && move.getToX() < 8 &&
                    move.getToY() >= 0 && move.getToY() < 8) {

                Button moveButton = chessSquares[move.getToX()][move.getToY()];
                if (moveButton != null) {
                    if (game.getBoard().getPieceAt(move.getToX(), move.getToY()) != null) {
                        moveButton.setBackgroundColor(Color.parseColor("#FF6347")); // Red (capture)
                    } else {
                        moveButton.setBackgroundColor(Color.parseColor("#90EE90")); // Green (move)
                    }
                }
            }
        }
    }

    private void resetSelection() {
        selectedX = -1;
        selectedY = -1;
        isPieceSelected = false;
        currentPossibleMoves.clear();
        updateBoardDisplay();
    }

    private void createChessBoard(LinearLayout chessBoard) {
        chessBoard.removeAllViews();
        chessSquares = new Button[8][8];

        // calculation of the square size based on screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int squareSize = Math.min(
                (displayMetrics.widthPixels - chessBoard.getPaddingLeft() - chessBoard.getPaddingRight()) / BOARD_SIZE,
                (displayMetrics.heightPixels / 2) / BOARD_SIZE // Only use half screen height for board
        );

        for (int row = 0; row < BOARD_SIZE; row++) {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);

            for (int col = 0; col < BOARD_SIZE; col++) {
                Button square = new Button(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        squareSize,
                        squareSize
                );
                params.setMargins(1, 1, 1, 1);
                square.setLayoutParams(params);

                // Set alternating colors
                if ((row + col) % 2 == 0) {
                    square.setBackgroundColor(Color.parseColor("#F0D9B5")); // Light
                } else {
                    square.setBackgroundColor(Color.parseColor("#B58863")); // Dark
                }

                // Set initial pieces
                setInitialPieces(square, row, col);

                String coordinateId = "square_" + row + col;
                square.setTag(coordinateId);
                square.setOnClickListener(squareClickListener);
                square.setId(View.generateViewId());

                // Store the mapping between coordinate and ID
                getResources().getIdentifier(coordinateId, "id", getPackageName());

                chessSquares[row][col] = square;
                // Text styling
                square.setTextSize(TypedValue.COMPLEX_UNIT_SP, squareSize / 3);
                square.setPadding(0, 0, 0, 0);

                rowLayout.addView(square);
            }
            chessBoard.addView(rowLayout);
        }
    }

    private void setInitialPieces(Button square, int row, int col) {
        // Clear any existing text first
        square.setText("");

        // Black pieces (top rows)
        if (row == 0) { // Black back row
            switch (col) {
                case 0: case 7:
                    square.setText("♜"); // Rook
                    break;
                case 1: case 6:
                    square.setText("♞"); // Knight
                    break;
                case 2: case 5:
                    square.setText("♝"); // Bishop
                    break;
                case 3:
                    square.setText("♛"); // Queen
                    break;
                case 4:
                    square.setText("♚"); // King
                    break;
            }
        } else if (row == 1) { // Black pawns
            square.setText("♟");
        }
        // White pieces (bottom rows)
        else if (row == 6) { // White pawns
            square.setText("♙");
        } else if (row == 7) { // White back row
            switch (col) {
                case 0: case 7:
                    square.setText("♖"); // Rook
                    break;
                case 1: case 6:
                    square.setText("♘"); // Knight
                    break;
                case 2: case 5:
                    square.setText("♗"); // Bishop
                    break;
                case 3:
                    square.setText("♕"); // Queen
                    break;
                case 4:
                    square.setText("♔"); // King
                    break;
            }
        }
    }

    private void updateBoardDisplay() {
        Board board = game.getBoard();
        TextView statusText = findViewById(R.id.statusText);

        if (game.isGameOver()) {
            String winner = game.getWinner();
            statusText.setText("Game Over! " +
                    (winner.equals("white") ? "White" : "Black") + " wins!");
            return;
        }

        // Update status text
        if (statusText != null) {
            statusText.setText(game.isWhiteTurn() ? "White's turn" : "Black's turn");
        }

        // Update all squares on the board
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button squareButton = chessSquares[row][col];
                if (squareButton != null) {
                    Piece piece = board.getPieceAt(row, col);

                    // Update piece display
                    squareButton.setText(piece != null ? getPieceSymbol(piece) : "");
                    squareButton.setTextColor(piece != null && piece.getColor().equals("white")
                            ? Color.WHITE : Color.BLACK);

                    // Reset background color
                    if ((row + col) % 2 == 0) {
                        squareButton.setBackgroundColor(Color.parseColor("#F0D9B5")); // Light
                    } else {
                        squareButton.setBackgroundColor(Color.parseColor("#B58863")); // Dark
                    }
                }
            }
        }

        // Highlight selected piece and possible moves if one is selected
        if (isPieceSelected) {
            showPossibleMovesAsText();
        }
    }


    private String getPieceSymbol(Piece piece) {
        switch (piece.getName()) {
            case "rook": return piece.getColor().equals("white") ? "♖" : "♜";
            case "knight": return piece.getColor().equals("white") ? "♘" : "♞";
            case "bishop": return piece.getColor().equals("white") ? "♗" : "♝";
            case "queen": return piece.getColor().equals("white") ? "♕" : "♛";
            case "king": return piece.getColor().equals("white") ? "♔" : "♚";
            case "pawn": return piece.getColor().equals("white") ? "♙" : "♟";
            default: return "";
        }
    }
}