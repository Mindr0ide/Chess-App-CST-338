package com.example.chessappcst338;

import java.util.Stack;

public class Game {
    private Board board;
    private Stack<Move> history;
    private boolean isWhiteTurn;

    public Game(){
        board = new Board();
        //board.reset();
        history = new Stack<>();
        isWhiteTurn = true;
    }

    public void play(){
        board.reset();
        while(true){
            this.display();
            Move move;   //TODO  = display.getMove(); ici c'est l'entroid ou on get le move de l'utilisateur
            if( isWhiteTurn && move.getPiece().getColor().equals("white") || // if the move is from the wright color
                !isWhiteTurn && move.getPiece().getColor().equals("black") ){

                if(move.getPiece().getPossibleMoves(move.getX(), move.getY(), board).contains(move)){ // if the move is valid

                    history.push(move);
                    board.setPieceAt(move.getX(), move.getY(), move.getPiece());
                    isWhiteTurn = !isWhiteTurn;

                }
                else{
                    System.out.println("Invalid move!");
                    continue;
                }

            } else {
                System.out.println("It's not your turn!");
            }
            
            if(isCheckmate(board)){ // c'est une fct qui existe pas encore mais qui va check si le roi est en echec et mat
                System.out.println("Checkmate! " + (isWhiteTurn ? "Black" : "White") + " wins!");
                break;
            }
        }
    }

    public void undo(){
        if(!history.isEmpty()){
            Move lastMove = history.pop();
            // TODO: undo the move on the board





            isWhiteTurn = !isWhiteTurn;
        } else {
            System.out.println("No moves to undo!");
        }
    }

    public void display(){
        
    }

    public Move getLast(){
        return history.pop();
    }
}
