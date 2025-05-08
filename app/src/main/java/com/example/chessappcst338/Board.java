package com.example.chessappcst338;

import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<Piece>> board = new ArrayList<>();

    public void reset(){
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                this.setPieceAt(i,j,null);

        setPieceAt(0,0,new Rook("black"));
        setPieceAt(0,1,new Knight("black"));
        setPieceAt(0,2,new Bishop("black"));
        setPieceAt(0,3,new Queen("black"));
        setPieceAt(0,4,new King("black"));
        setPieceAt(0,5,new Bishop("black"));
        setPieceAt(0,6,new Knight("black"));
        setPieceAt(0,7,new Rook("black"));
        for (int i = 0; i < 8; i++)
            setPieceAt(1,i,new Pawn("black"));

        for (int i = 0; i < 8; i++)
            setPieceAt(6,i,new Pawn("white"));
        setPieceAt(7,0,new Rook("white"));
        setPieceAt(7,1,new Knight("white"));
        setPieceAt(7,2,new Bishop("white"));
        setPieceAt(7,3,new Queen("white"));
        setPieceAt(7,4,new King("white"));
        setPieceAt(7,5,new Bishop("white"));
        setPieceAt(7,6,new Knight("white"));
        setPieceAt(7,7,new Rook("white"));
    }
    public Piece getPieceAt (int x, int y){
        if (x <= 7 && x >= 0 && y <= 7 && y >= 0)
            return board.get(y).get(x);
        return null;
    }
    public void setPieceAt (int x, int y, Piece piece){
        if (x <= 7 && x >= 0 && y <= 7 && y >= 0)
            board.get(y).set(x, piece);
    }

}
