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
    private Position selectedPosition = null;
    private ArrayList<Move> currentPossibleMoves = new ArrayList<>();
    private static final int BOARD_SIZE = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();

        findViewById(R.id.undoButton).setOnClickListener(v -> {
            game.undo();
        });

        LinearLayout chessBoard = findViewById(R.id.chessBoard);
        createChessBoard(chessBoard);
    }

    private View.OnClickListener squareClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tag = (String) v.getTag();
            int col = tag.charAt(7) - 'a'; // Extract column from tag like "square_a1"
            int row = 8 - Character.getNumericValue(tag.charAt(8)); // Extract row
            Position clickedPosition = new Position(col, row);

            // If no piece is selected, try to select one
            if (selectedPosition == null) {
                Piece piece = game.getBoard().getPieceAt(clickedPosition);
                if (piece != null &&
                        ((piece.getColor().equals("white") && game.isWhiteTurn()) ||
                                (piece.getColor().equals("black") && !game.isWhiteTurn()))) {

                    selectedPosition = clickedPosition;
                    currentPossibleMoves = piece.getPossibleMoves(clickedPosition, game.getBoard());
                    highlightPossibleMoves();
                }
            }
            // If a piece is already selected
            else {
                // Check if this is a valid move
                for (Move move : currentPossibleMoves) {
                    if (move.getTo().equals(clickedPosition)) {
                        // Execute the move
                        game.play(move);
                        resetSelection();
                        updateBoardDisplay();
                        return;
                    }
                }

                // If clicked on another piece of same color, select that instead
                Piece clickedPiece = game.getBoard().getPieceAt(clickedPosition);
                if (clickedPiece != null &&
                        clickedPiece.getColor().equals(game.getBoard().getPieceAt(selectedPosition).getColor())) {

                    selectedPosition = clickedPosition;
                    currentPossibleMoves = clickedPiece.getPossibleMoves(clickedPosition, game.getBoard());
                    highlightPossibleMoves();
                } else {
                    // Invalid move, reset selection
                    resetSelection();
                }
            }
        }
    };

    private void highlightPossibleMoves() {
        resetSquareColors(); // Reset all squares first

        // Highlight selected piece
        String selectedId = "square_" + selectedPosition.toString();
        int selectedResId = getResources().getIdentifier(selectedId, "id", getPackageName());
        Button selectedButton = findViewById(selectedResId);
        selectedButton.setBackgroundColor(Color.parseColor("#6495ED")); // Cornflower blue

        // Highlight possible moves
        for (Move move : currentPossibleMoves) {
            String moveId = "square_" + move.getTo().toString();
            int moveResId = getResources().getIdentifier(moveId, "id", getPackageName());
            Button moveButton = findViewById(moveResId);

            // Different color for capture moves
            if (game.getBoard().getPieceAt(move.getTo()) != null) {
                moveButton.setBackgroundColor(Color.parseColor("#FF6347")); // Tomato (for captures)
            } else {
                moveButton.setBackgroundColor(Color.parseColor("#90EE90")); // Light green
            }
        }
    }

    private void resetSelection() {
        selectedPosition = null;
        currentPossibleMoves.clear();
        resetSquareColors();
    }

    private void createChessBoard(LinearLayout chessBoard) {
        // Define colors
        int lightColor = Color.parseColor("#F0D9B5");
        int darkColor = Color.parseColor("#B58863");

        // Get screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getCurrentWindowMetrics();

        // Calculate maximum possible square size that fits the screen
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // Account for margins (1px per side for each square) and padding (4dp)
        int totalMargins = BOARD_SIZE * 2 * 2; // 1px margin on both sides for each square
        int paddingPx = (int) (4 * getResources().getDisplayMetrics().density);

        // Calculate square size based on smaller dimension (width or height)
        int maxPossibleSize = Math.min(
                (screenWidth - totalMargins - (2 * paddingPx)) / BOARD_SIZE,
                (screenHeight - totalMargins - (2 * paddingPx)) / BOARD_SIZE
        );

        // Set a reasonable minimum size
        int squareSize = Math.max(maxPossibleSize, (int) (40 * getResources().getDisplayMetrics().density));

        for (int row = 0; row < BOARD_SIZE; row++) {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);

            for (int col = 0; col < BOARD_SIZE; col++) {
                Button square = new Button(this);

                // Set square size based on calculated dimensions
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        squareSize,
                        squareSize
                );
                params.setMargins(1, 1, 1, 1);
                square.setLayoutParams(params);

                // Alternate colors
                if ((row + col) % 2 == 0) {
                    square.setBackgroundColor(lightColor);
                } else {
                    square.setBackgroundColor(darkColor);
                }

                // Add chess piece text (optional)
                if (row == 0 || row == 7 || row == 1 || row == 6) {
                    setInitialPieces(square, row, col);
                }

                // Set tag to identify position
                square.setTag("square_" + (char)('a' + col) + (8 - row));
                square.setOnClickListener(squareClickListener);

                // Make text fit better
                square.setTextSize(TypedValue.COMPLEX_UNIT_SP, squareSize / 3);
                square.setPadding(0, 0, 0, 0);

                String coordinate = "square_" + (char)('a' + col) + (8 - row);
                square.setTag(coordinate);

                // Optional: Also set as ID if needed
                int id = View.generateViewId();
                square.setId(id);

                rowLayout.addView(square);
            }

            chessBoard.addView(rowLayout);
        }
    }

    private void setInitialPieces(Button square, int row, int col) {
        // Set initial chess pieces (simplified version)
        if (row == 0 || row == 7) { // Back row pieces
            switch (col) {
                case 0: case 7:
                    square.setText(row == 0 ? "♖" : "♜"); // Rook
                    break;
                case 1: case 6:
                    square.setText(row == 0 ? "♘" : "♞"); // Knight
                    break;
                case 2: case 5:
                    square.setText(row == 0 ? "♗" : "♝"); // Bishop
                    break;
                case 3:
                    square.setText(row == 0 ? "♕" : "♛"); // Queen
                    break;
                case 4:
                    square.setText(row == 0 ? "♔" : "♚"); // King
                    break;
            }
        } else if (row == 1 || row == 6) { // Pawns
            square.setText(row == 1 ? "♙" : "♟");
        }
    }
    private void updateBoardDisplay() {
        Board board = game.getBoard();

        // Update each square on the board
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Get the button for this position
                String buttonId = "square_" + (char)('a' + col) + (8 - row);
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                Button squareButton = findViewById(resId);

                // Get the piece at this position
                Piece piece = board.getPieceAt(new Position(col, row));

                // Update the button appearance
                if (piece != null) {
                    // Set piece symbol and color
                    squareButton.setText(piece.toString());
                    squareButton.setTextColor(piece.getColor().equals("white") ?
                            Color.WHITE : Color.BLACK);
                } else {
                    squareButton.setText(""); // Empty square
                }

                // Set square color (alternating colors)
                if ((row + col) % 2 == 0) {
                    squareButton.setBackgroundColor(Color.parseColor("#F0D9B5")); // Light
                } else {
                    squareButton.setBackgroundColor(Color.parseColor("#B58863")); // Dark
                }
            }
        }

        // Update status display
        TextView statusText = findViewById(R.id.statusText);
        statusText.setText("Current turn: " +
                (game.isWhiteTurn() ? "White" : "Black") +
                "\nStatus: " + game.getStatus());

        // Highlight last move if available
        if (!game.getHistory().isEmpty()) {
            Move lastMove = game.getHistory().peek();
            highlightMove(lastMove);
        }
    }

    private void highlightMove(Move move) {
        // Reset all square backgrounds first
        resetSquareColors();

        // Get resources for highlight colors
        int fromHighlight = Color.parseColor("#FFFF00"); // Yellow
        int toHighlight = Color.parseColor("#FFA500");   // Orange

        // Highlight from square
        String fromId = "square_" + move.getFrom().toString();
        int fromResId = getResources().getIdentifier(fromId, "id", getPackageName());
        Button fromButton = findViewById(fromResId);
        fromButton.setBackgroundColor(fromHighlight);

        // Highlight to square
        String toId = "square_" + move.getTo().toString();
        int toResId = getResources().getIdentifier(toId, "id", getPackageName());
        Button toButton = findViewById(toResId);
        toButton.setBackgroundColor(toHighlight);
    }

    private void resetSquareColors() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                String buttonId = "square_" + (char)('a' + col) + (8 - row);
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                Button squareButton = findViewById(resId);

                if ((row + col) % 2 == 0) {
                    squareButton.setBackgroundColor(Color.parseColor("#F0D9B5"));
                } else {
                    squareButton.setBackgroundColor(Color.parseColor("#B58863"));
                }
            }
        }
    }
}