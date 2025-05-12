package com.example.chessappcst338;

import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<Piece>> board = new ArrayList<>();

    public void reset(){
        for (int y = 0; y < 8; y++) {
            ArrayList<Piece> row = new ArrayList<>();
            for (int x = 0; x < 8; x++)
                row.add(null);
            board.add(row);
        }

        setPieceAt(0,0,new Rook("black", 0, 0));
        setPieceAt(0,1,new Knight("black", 0, 1));
        setPieceAt(0,2,new Bishop("black", 0, 2));
        setPieceAt(0,3,new Queen("black", 0, 3));
        setPieceAt(0,4,new King("black", 0, 4));
        setPieceAt(0,5,new Bishop("black", 0, 5));
        setPieceAt(0,6,new Knight("black", 0, 6));
        setPieceAt(0,7,new Rook("black", 0, 7));
        for (int i = 0; i < 8; i++)
            setPieceAt(1,i,new Pawn("black", 1, i));

        for (int i = 0; i < 8; i++)
            setPieceAt(6,i,new Pawn("white", 6, i));
        setPieceAt(7,0,new Rook("white",7,0));
        setPieceAt(7,1,new Knight("white",7,1));
        setPieceAt(7,2,new Bishop("white",7,2));
        setPieceAt(7,3,new Queen("white",7,3));
        setPieceAt(7,4,new King("white",7,4));
        setPieceAt(7,5,new Bishop("white",7,5));
        setPieceAt(7,6,new Knight("white",7,6));
        setPieceAt(7,7,new Rook("white",7,7));
    }
    public Piece getPieceAt (int x, int y){
        if (x <= 7 && x >= 0 && y <= 7 && y >= 0){
            return board.get(y).get(x);
        }
        return null;
    }
    public void setPieceAt (int x, int y, Piece piece){
        if (x <= 7 && x >= 0 && y <= 7 && y >= 0){
            board.get(y).set(x, piece);
            //piece.setPos(x, y);
        }
    }

}
