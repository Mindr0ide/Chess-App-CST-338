package com.example.chessappcst338;

import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(String color, int x, int y) {
        super(color, "rook", x, y);
    }

    @Override
    public ArrayList<Move> possibleMoves(int x, int y, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int i = 0; i < directions.length; i++) 
        {
            int j = 1;
            while(true)
            {
                int newX = x + directions[i][0] * j;
                int newY = y + directions[i][1] * j;

                if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) 
                {
                    Piece piece = board.getPieceAt(newX, newY);
                    if (piece == null) 
                    {
                        moves.add(new Move(x, y, newX, newY, this));
                        j++;
                    } 
                    else if (!piece.getColor().equals(this.getColor())) 
                    {
                        // capture move
                        moves.add(new Move(x, y, newX, newY, this, piece));
                        break;
                    } 
                    else 
                        break;
                } 
                else 
                    break;
            }
        }

        return moves;
    }
}