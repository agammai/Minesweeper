package minesweeper;

import java.util.Objects;

public class BoardSquare {

    private int row;

    private int column;

    public BoardSquare(int rowA, int colA) {

        row = rowA;
        column = colA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardSquare that = (BoardSquare) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "BoardSquare{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }


    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
