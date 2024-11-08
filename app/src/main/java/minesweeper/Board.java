package minesweeper;


import minesweeper.exception.DetonatedMineException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Board {
    private Cell[][] cells;
    public static char STARTING_ALPHABET = 'A';

    private int openedCells = 0;

    private int mines;

    public Board(int size, int noOfMines) {
        cells = new Cell[size][size];
        mines = noOfMines;
        Arrays.stream(cells).forEach(a -> Arrays.setAll(a, j -> new Cell()));
    }

    Board(int size, Set<BoardSquare> minesA,int openedCellsA) {
        cells = new Cell[size][size];
        mines = minesA.size();
        openedCells = openedCellsA;
        Arrays.stream(cells).forEach(a -> Arrays.setAll(a, j -> new Cell()));
        minesA.forEach(square ->
                cells[square.getRow()][square.getColumn()].setMine(true));
    }

    public void setMines(Set<BoardSquare> mines)
    {
        mines.forEach(square -> cells[square.getRow()][square.getColumn()].setMine(true));
    }

    void assignAdjacentMines()
    {
        for (int row = 0; row < cells.length; row++)
        {
            for (int column = 0; column < cells[row].length; column++)
            {
                if(!cells[row][column].hasMine())
                {
                    cells[row][column].setAdjacentMines(calculateAdjacentMines(row, column));
                }
            }
        }
    }

    private int calculateAdjacentMines(int row, int column) {
        int count = 0;
        for(int rowIndex = -1 ; rowIndex <= 1; rowIndex++)
        {
            for(int columnIndex = -1; columnIndex <= 1; columnIndex++)
            {
                if (rowIndex == 0 && columnIndex == 0) continue;
                int currColumn = column + columnIndex;
                int currRow = row + rowIndex;
                if (currRow >= 0 && currRow < cells.length && currColumn >= 0 && currColumn < cells.length) {
                    if (cells[currRow][currColumn].hasMine()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    Result open(int x, int y) throws DetonatedMineException{

        Cell cell = cells[x][y];
        int adjacentMines = cell.getAdjacentMines();
        if (cell.isOpened()) {
            return new Result(GameStatus.ALREADY_OPENED, adjacentMines);
        }

        if (cell.getAdjacentMines() == 0) {
            reveal(new BoardSquare(x, y));
        }

        if(!cell.isOpened())
        {
            cell.setOpened(true);
            openedCells++;
        }

        if (cell.hasMine()) {
            throw new DetonatedMineException("Oh no, you detonated a mine! Game over.");
        }

        if (openedCells == (cells.length * cells.length - mines)) {
            return new Result(GameStatus.GAMEOVER, adjacentMines);
        }
        return new Result(GameStatus.ONGOING, adjacentMines);
    }

    void reveal(BoardSquare boardSquare) {
        Queue<BoardSquare> queue = new LinkedList<>();
        queue.add(boardSquare);

        while (!queue.isEmpty()) {
            BoardSquare square = queue.poll();
            int currentRow = square.getRow();
            int currentColumn = square.getColumn();

            if (currentRow < 0 || currentRow >= cells.length || currentColumn < 0 || currentColumn >= cells.length) {
                continue;
            }

            Cell cell = cells[currentColumn][currentRow];

            if (cell.isOpened() || cell.hasMine()) {
                continue;
            }

            cell.setOpened(true);
            openedCells++;

            if (cell.getAdjacentMines() == 0) {
                for(int rowIndex = -1 ; rowIndex <= 1; rowIndex++) {
                    for (int columnIndex = -1; columnIndex <= 1; columnIndex++) {
                        if (rowIndex == 0 && columnIndex == 0) continue;
                        queue.add(new BoardSquare(currentRow + rowIndex, currentColumn + columnIndex));
                    }
                }
            }
        }
    }

    public void print()
     {
         System.out.print("  "); // Column header
         for (int i = 1; i <= cells.length; i++) {
             System.out.printf("%s ", i);
         }
         System.out.println();

         for (int i = 0; i < cells.length; i++) {
             System.out.printf("%s ", getRowLabel(i)); // Row header
             for (Cell cell : cells[i]) {
                 System.out.printf("%s ", cell.getValue());
             }
             System.out.println();
         }
     }

    private String getRowLabel(int index) {
        StringBuilder label = new StringBuilder();

        while (index >= 0) {
            label.insert(0, (char) (STARTING_ALPHABET + (index % 26)));
            index = index / 26 - 1;
        }

        return label.toString();
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getOpenedCells() {
        return openedCells;
    }
}
