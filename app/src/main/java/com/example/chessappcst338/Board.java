package com.example.chessappcst338;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Piece[][] squares;
    private Position enPassantTarget;
    private boolean whiteCanCastleKingside = true;
    private boolean whiteCanCastleQueenside = true;
    private boolean blackCanCastleKingside = true;
    private boolean blackCanCastleQueenside = true;

    public Board() {
        squares = new Piece[8][8];
        resetBoard();
    }

    public void resetBoard() {
        // Clear the board
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col] = null;
            }
        }

        // Set up pawns
        for (int col = 0; col < 8; col++) {
            squares[1][col] = new Pawn("black");
            squares[6][col] = new Pawn("white");
        }

        // Set up other pieces
        // Black pieces
        squares[0][0] = new Rook("black");
        squares[0][1] = new Knight("black");
        squares[0][2] = new Bishop("black");
        squares[0][3] = new Queen("black");
        squares[0][4] = new King("black");
        squares[0][5] = new Bishop("black");
        squares[0][6] = new Knight("black");
        squares[0][7] = new Rook("black");

        // White pieces
        squares[7][0] = new Rook("white");
        squares[7][1] = new Knight("white");
        squares[7][2] = new Bishop("white");
        squares[7][3] = new Queen("white");
        squares[7][4] = new King("white");
        squares[7][5] = new Bishop("white");
        squares[7][6] = new Knight("white");
        squares[7][7] = new Rook("white");

        // Reset special move flags
        enPassantTarget = null;
        whiteCanCastleKingside = true;
        whiteCanCastleQueenside = true;
        blackCanCastleKingside = true;
        blackCanCastleQueenside = true;
    }

    public Piece getPieceAt(Position position) {
        if (!isValidPosition(position)) return null;
        return squares[position.getRow()][position.getCol()];
    }

    public void setPieceAt(Position position, Piece piece) {
        if (isValidPosition(position)) {
            squares[position.getRow()][position.getCol()] = piece;
        }
    }

    public Piece movePiece(Move move) {
        Piece movingPiece = getPieceAt(move.getFrom());
        if (movingPiece == null) return null;

        Piece capturedPiece = getPieceAt(move.getTo());

        // Handle special moves
        if (movingPiece instanceof King && Math.abs(move.getFrom().getCol() - move.getTo().getCol()) == 2) {
            // Castling
            return handleCastling(move);
        } else if (movingPiece instanceof Pawn) {
            // Handle en passant and pawn promotion
            return handlePawnMove(move, movingPiece);
        }

        // Regular move
        setPieceAt(move.getTo(), movingPiece);
        setPieceAt(move.getFrom(), null);
        movingPiece.setMoved(true);

        // Update castling rights if rook or king moved
        updateCastlingRights(move, movingPiece);

        return capturedPiece;
    }

    public void undoMove(Move move) {
        // Put the moving piece back
        setPieceAt(move.getFrom(), getPieceAt(move.getTo()));
        setPieceAt(move.getTo(), move.getCapturedPiece());

//        // Handle special cases
//        if (move.getMovingPiece() instanceof King && Math.abs(move.getFrom().getCol() - move.getTo().getCol()) == 2) {
//            // Undo castling
//            undoCastling(move);
//        } else if (move.getMovingPiece() instanceof Pawn && move.isEnPassant()) {
//            // Undo en passant
//            setPieceAt(move.getEnPassantCapturePosition(), move.getCapturedPiece());
//        }
//
//        // Restore moved status
//        if (!move.wasPieceMovedBefore()) {
//            move.getMovingPiece().setMoved(false);
//        }
    }

    private Piece handlePawnMove(Move move, Piece pawn) {
        Position from = move.getFrom();
        Position to = move.getTo();
        Piece capturedPiece = null;

//        // Check for en passant
//        if (from.getCol() != to.getCol() && getPieceAt(to) == null) {
//            // En passant capture
//            capturedPiece = getPieceAt(new Position(from.getRow(), to.getCol()));
//            setPieceAt(new Position(from.getRow(), to.getCol()), null);
//            move.setEnPassant(true);
//            move.setEnPassantCapturePosition(new Position(from.getRow(), to.getCol()));
//        } else {
//            capturedPiece = getPieceAt(to);
//        }

        // Move the pawn
        setPieceAt(to, pawn);
        setPieceAt(from, null);
        pawn.setMoved(true);

        // Check for pawn promotion
        if (to.getRow() == 0 || to.getRow() == 7) {
            setPieceAt(to, new Queen(pawn.getColor()));
            move.setPromotedTo(getPieceAt(to));
        }

        // Set en passant target if pawn moved two squares
        if (Math.abs(from.getRow() - to.getRow()) == 2) {
            enPassantTarget = new Position((from.getRow() + to.getRow()) / 2, from.getCol());
        } else {
            enPassantTarget = null;
        }

        return capturedPiece;
    }

    private Piece handleCastling(Move move) {
        Piece king = getPieceAt(move.getFrom());
        int row = move.getFrom().getRow();
        int direction = move.getTo().getCol() > move.getFrom().getCol() ? 1 : -1;

        // Move the king
        setPieceAt(move.getTo(), king);
        setPieceAt(move.getFrom(), null);
        king.setMoved(true);

        // Move the rook
        int rookCol = direction > 0 ? 7 : 0;
        Position rookPos = new Position(row, rookCol);
        Piece rook = getPieceAt(rookPos);
        setPieceAt(new Position(row, move.getFrom().getCol() + direction), rook);
        setPieceAt(rookPos, null);
        rook.setMoved(true);

        move.setCastling(true);
        return null;
    }

    private void undoCastling(Move move) {
        Piece king = getPieceAt(move.getTo());
        int row = move.getFrom().getRow();
        int direction = move.getTo().getCol() > move.getFrom().getCol() ? 1 : -1;

        // Find and move back the rook
        Position rookNewPos = new Position(row, move.getFrom().getCol() + direction);
        Piece rook = getPieceAt(rookNewPos);
        int rookOriginalCol = direction > 0 ? 7 : 0;
        setPieceAt(new Position(row, rookOriginalCol), rook);
        setPieceAt(rookNewPos, null);

//        if (!move.wasRookMovedBefore()) {
//            rook.setMoved(false);
//        }
    }

    private void updateCastlingRights(Move move, Piece movingPiece) {
        if (movingPiece instanceof King) {
            if (movingPiece.getColor().equals("white")) {
                whiteCanCastleKingside = false;
                whiteCanCastleQueenside = false;
            } else {
                blackCanCastleKingside = false;
                blackCanCastleQueenside = false;
            }
        } else if (movingPiece instanceof Rook) {
            if (movingPiece.getColor().equals("white")) {
                if (move.getFrom().getCol() == 0) whiteCanCastleQueenside = false;
                if (move.getFrom().getCol() == 7) whiteCanCastleKingside = false;
            } else {
                if (move.getFrom().getCol() == 0) blackCanCastleQueenside = false;
                if (move.getFrom().getCol() == 7) blackCanCastleKingside = false;
            }
        }
    }

    public boolean isInCheck(String color) {
        Position kingPos = findKing(color);
        if (kingPos == null) return false;

        // Check if any opponent's piece can attack the king
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position pos = new Position(row, col);
                Piece piece = getPieceAt(pos);
                if (piece != null && !piece.getColor().equals(color)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(String color) {
        if (!isInCheck(color)) return false;

        // Check if any move can get out of check
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position pos = new Position(row, col);
                Piece piece = getPieceAt(pos);
                if (piece != null && piece.getColor().equals(color)) {
                    List<Move> moves = piece.getPossibleMoves(pos, this);
                    for (Move move : moves) {
                        Board tempBoard = getClone();
                        tempBoard.movePiece(move);
                        if (!tempBoard.isInCheck(color)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean isStalemate(String color) {
        if (isInCheck(color)) return false;

        // Check if player has any legal moves
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position pos = new Position(row, col);
                Piece piece = getPieceAt(pos);
                if (piece != null && piece.getColor().equals(color)) {
                    List<Move> moves = piece.getPossibleMoves(pos, this);
                    for (Move move : moves) {
                        Board tempBoard = getClone();
                        tempBoard.movePiece(move);
                        if (!tempBoard.isInCheck(color)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean isInsufficientMaterial() {
        // Count pieces
        int whitePieces = 0;
        int blackPieces = 0;
        boolean whiteHasNonKing = false;
        boolean blackHasNonKing = false;
        boolean hasBishopsOrKnights = false;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece != null) {
                    if (piece.getColor().equals("white")) {
                        whitePieces++;
                        if (!(piece instanceof King)) {
                            whiteHasNonKing = true;
                            if (piece instanceof Bishop || piece instanceof Knight) {
                                hasBishopsOrKnights = true;
                            }
                        }
                    } else {
                        blackPieces++;
                        if (!(piece instanceof King)) {
                            blackHasNonKing = true;
                            if (piece instanceof Bishop || piece instanceof Knight) {
                                hasBishopsOrKnights = true;
                            }
                        }
                    }
                }
            }
        }

        // King vs King
        if (whitePieces == 1 && blackPieces == 1) return true;

        // King + bishop/knight vs King
        if ((whitePieces == 1 && blackPieces <= 2 && !blackHasNonKing) ||
                (blackPieces == 1 && whitePieces <= 2 && !whiteHasNonKing)) {
            return true;
        }

        // King + bishop vs King + bishop with bishops on same color
        if (whitePieces == 2 && blackPieces == 2 && hasBishopsOrKnights) {
            return checkBishopsOnSameColor();
        }

        return false;
    }

    private boolean checkBishopsOnSameColor() {
        Position whiteBishopPos = null;
        Position blackBishopPos = null;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece instanceof Bishop) {
                    if (piece.getColor().equals("white")) {
                        whiteBishopPos = new Position(row, col);
                    } else {
                        blackBishopPos = new Position(row, col);
                    }
                }
            }
        }

        if (whiteBishopPos != null && blackBishopPos != null) {
            boolean whiteOnWhite = (whiteBishopPos.getRow() + whiteBishopPos.getCol()) % 2 == 0;
            boolean blackOnWhite = (blackBishopPos.getRow() + blackBishopPos.getCol()) % 2 == 0;
            return whiteOnWhite == blackOnWhite;
        }

        return false;
    }

    private Position findKing(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece instanceof King && piece.getColor().equals(color)) {
                    return new Position(row, col);
                }
            }
        }
        return null;
    }

    @NonNull
    public Board getClone() {
        Board newBoard = new Board();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece != null) {
                    newBoard.squares[row][col] = piece.getClone();
                }
            }
        }
        newBoard.enPassantTarget = this.enPassantTarget;
        newBoard.whiteCanCastleKingside = this.whiteCanCastleKingside;
        newBoard.whiteCanCastleQueenside = this.whiteCanCastleQueenside;
        newBoard.blackCanCastleKingside = this.blackCanCastleKingside;
        newBoard.blackCanCastleQueenside = this.blackCanCastleQueenside;
        return newBoard;
    }

    public void display() {
        System.out.println("  a b c d e f g h");
        for (int row = 0; row < 8; row++) {
            System.out.print((8 - row) + " ");
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                System.out.print(piece != null ? piece.getSymbol() : ".");
                System.out.print(" ");
            }
            System.out.println((8 - row));
        }
        System.out.println("  a b c d e f g h");
    }

    private boolean isValidPosition(Position position) {
        return position != null &&
                position.getRow() >= 0 && position.getRow() < 8 &&
                position.getCol() >= 0 && position.getCol() < 8;
    }

    // Getters for special move information
    public Position getEnPassantTarget() { return enPassantTarget; }
    public boolean canWhiteCastleKingside() { return whiteCanCastleKingside; }
    public boolean canWhiteCastleQueenside() { return whiteCanCastleQueenside; }
    public boolean canBlackCastleKingside() { return blackCanCastleKingside; }
    public boolean canBlackCastleQueenside() { return blackCanCastleQueenside; }
}