package com.example.chessappcst338;

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

    public void play(){}

    public void undo(){}

    public void display(){}

    public Move getLast(){
        return history.peek();
    }
}
