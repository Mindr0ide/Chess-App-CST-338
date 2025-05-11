package com.example.chessappcst338;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getRow() {
        return x;
    }

    public int getCol() {
        return y;
    }

    public String toString() {
        return "" + (char)('a' + y) + (8 - x);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return y == position.y && x == position.x;
    }
}