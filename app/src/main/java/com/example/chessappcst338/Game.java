package com.example.chessappcst338;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Game {
    private Board board;
    private Stack<Move> history;
    private boolean isWhiteTurn;

    public Game(){
//        board = new Board()
        history = new Stack<>();
        isWhiteTurn = true;
    }

    public void play(){
//        display();
//        System.out.print("What piece do you want to play?");
//        int input = in.nextInt();
//        Piece currentPiece = board.getPieceAt(input);
//        System.out.print("Your possible moves are:");
//        ArrayList<Move> possibleMoves = currentPiece.possibleMoves(input);
//        for (int i = 0; i < possibleMoves.size(); i++) {
//            System.out.println(possibleMoves.get(i));
//        }
//        int move = in.nextInt();
//        board.setPieceAt(move);

    }

    public void undo(){
        Move oldMove = getLast();
    }

    public void display(){}

    public Move getLast(){
        return history.pop();
    }
}
