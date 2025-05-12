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
    ArrayList<Move> currentPossibleMoves = new ArrayList<Move>();

    private static final int BOARD_SIZE = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();
        game.getBoard().reset();

        findViewById(R.id.undoButton).setOnClickListener(v -> {
            game.undo();
        });

        LinearLayout chessBoard = findViewById(R.id.chessBoard);
        createChessBoard(chessBoard);
    }

    private final View.OnClickListener squareClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tag = (String) v.getTag();
            int row = Character.getNumericValue(tag.charAt(7)); // Extract row from tag "square_00"
            int col = Character.getNumericValue(tag.charAt(8)); // Extract column

            Piece clickedPiece = game.getBoard().getPieceAt(row, col);
            System.out.println(row + col);

            if (clickedPiece == null) {
                return;
            }

            // If we have a selected piece, try to move it
            if (selectedX != -1 && selectedY != -1) {
                for (Move move : currentPossibleMoves) {
                    if (move.getToX() == row && move.getToY() == col) {
                        game.play(move);
                        resetSelection();
                        updateBoardDisplay();
                        return;
                    }
                }
            }

            // Select a new piece if it's the correct color's turn
            if (((clickedPiece.getColor().equals("white") && game.isWhiteTurn()) || (clickedPiece.getColor().equals("black") && !game.isWhiteTurn()))){
                selectedX = row;
                selectedY = col;
                currentPossibleMoves = clickedPiece.possibleMoves(row, col, game.getBoard());
                highlightPossibleMoves();
            } else {
                resetSelection();
            }
        }
    };

    private void highlightPossibleMoves() {
        resetSquareColors(); // Reset all squares first

        // Highlight selected piece
        String selectedId = "square_" + selectedX + selectedY;
        int selectedResId = getResources().getIdentifier(selectedId, "id", getPackageName());
        Button selectedButton = findViewById(selectedResId);
        selectedButton.setBackgroundColor(Color.parseColor("#6495ED")); // Cornflower blue

        // Highlight possible moves
        for (Move move : currentPossibleMoves) {
            String moveId = "square_" + move.getToX() + move.getToY();
            int moveResId = getResources().getIdentifier(moveId, "id", getPackageName());
            Button moveButton = findViewById(moveResId);

            // Different color for capture moves
            if (game.getBoard().getPieceAt(move.getToX(), move.getToY()) != null) {
                moveButton.setBackgroundColor(Color.parseColor("#FF6347")); // Tomato (for captures)
            } else {
                moveButton.setBackgroundColor(Color.parseColor("#90EE90")); // Light green
            }
        }
    }

    private void resetSelection() {
        selectedX = -1;
        selectedY =  -1;
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
                square.setTag("square_" + row + col);
                square.setOnClickListener(squareClickListener);

                // Make text fit better
                square.setTextSize(TypedValue.COMPLEX_UNIT_SP, squareSize / 3);
                square.setPadding(0, 0, 0, 0);

                String coordinate = "square_" + row + col;
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
    private void updateBoardDisplay() {
        Board board = game.getBoard();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                String buttonId = "square_" + row + col;
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                Button squareButton = findViewById(resId);

                Piece piece = board.getPieceAt(row, col);
                squareButton.setText(piece != null ? getPieceSymbol(piece) : "");
                squareButton.setTextColor(piece != null && piece.getColor().equals("white") ?
                        Color.WHITE : Color.BLACK);
            }
        }
    }

    private void highlightMove(Move move) {
        // Reset all square backgrounds first
        resetSquareColors();

        // Get resources for highlight colors
        int fromHighlight = Color.parseColor("#FFFF00"); // Yellow
        int toHighlight = Color.parseColor("#FFA500");   // Orange

        // Highlight from square
        String fromId = "square_" + move.getFromX() + move.getFromY();
        int fromResId = getResources().getIdentifier(fromId, "id", getPackageName());
        Button fromButton = findViewById(fromResId);
        fromButton.setBackgroundColor(fromHighlight);

        // Highlight to square
        String toId = "square_" + move.getFromX() + move.getFromY();
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