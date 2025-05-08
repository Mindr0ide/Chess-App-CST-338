package com.example.chessappcst338;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(String color) {
        super(color, "bishop");
    }

    @Override
    public ArrayList<Move> possibleMoves(int x, int y, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        while (true) {
            for (int i = 0; i < directions.length; i++) {
                int newX = x + directions[i][0];
                int newY = y + directions[i][1];

                if (newX < 0 || newX > 7 || newY < 0 || newY > 7) {
                    break;
                }

                Piece pieceAtNewPosition = board.getPieceAt(newX, newY);
                if (pieceAtNewPosition == null) {
                    moves.add(new Move(x, y, newX, newY));
                } else if (!pieceAtNewPosition.getColor().equals(this.getColor())) {
                    moves.add(new Move(x, y, newX, newY));
                    break;
                } else {
                    break;
                }
            }
        }

        // while (true) {
        //     for (int i = 0; i < directions.length; i++) {
        //         int newX = x + directions[i][0];
        //         int newY = y + directions[i][1];

        //         if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
        //             Piece piece = board.getPieceAt(newX, newY);
        //             if (piece == null) {
        //                 moves.add(new Move(newX, newY, this));
        //             } else if (!piece.getColor().equals(this.getColor())) {
        //                 moves.add(new Move(x, y, this));
        //                 break;
        //             } else {
        //                 break;
        //             }
        //         } else {
        //             break;
        //         }
        //     }
        // }

        return moves;
        
    }
}