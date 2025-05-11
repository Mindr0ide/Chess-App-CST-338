package com.example.chessappcst338;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Game game;
    private static final int BOARD_SIZE = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();

        // Set up button listeners
        findViewById(R.id.playButton).setOnClickListener(v -> {
            game.play();
        });

        findViewById(R.id.undoButton).setOnClickListener(v -> {
            game.undo();
        });

        LinearLayout chessBoard = findViewById(R.id.chessBoard);
        createChessBoard(chessBoard);
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
                square.setTag("" + (char)('a' + col) + (8 - row));

                // Make text fit better
                square.setTextSize(TypedValue.COMPLEX_UNIT_SP, squareSize / 3);
                square.setPadding(0, 0, 0, 0);

                String coordinate = "" + (char)('a' + col) + (8 - row);
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
}