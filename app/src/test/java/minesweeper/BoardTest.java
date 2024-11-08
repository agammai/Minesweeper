package minesweeper;

import minesweeper.exception.DetonatedMineException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {


    @Test
    void shouldPrintBoard()
    {
         new Board(4, 3).print();
    }

    @Test
    void shouldReturnAdjacentMinesForGridSize4()
    {
        Board board = new Board(4, new HashSet<>(Arrays.asList(new BoardSquare(1, 1), new BoardSquare(0, 3))),0);
        board.assignAdjacentMines();
        assertEquals(board.getCells()[0][1].getAdjacentMines() , 1);
        assertEquals(board.getCells()[0][2].getAdjacentMines() , 2);
        assertEquals(board.getCells()[1][0].getAdjacentMines() , 1);
        assertEquals(board.getCells()[1][2].getAdjacentMines() , 2);
        assertEquals(board.getCells()[1][3].getAdjacentMines() , 1);
        assertEquals(board.getCells()[2][0].getAdjacentMines() , 1);
        assertEquals(board.getCells()[2][1].getAdjacentMines() , 1);
        assertEquals(board.getCells()[2][2].getAdjacentMines() , 1);
        assertEquals(board.getCells()[0][0].getAdjacentMines() , 1);
    }

    @Test
    void shouldReturnAdjacentMinesForGridSize2()
    {
        Board board = new Board(2, new HashSet<>(Arrays.asList(new BoardSquare(0, 1))),0);
        board.assignAdjacentMines();
        assertEquals(board.getCells()[0][0].getAdjacentMines() , 1);
        assertEquals(board.getCells()[1][0].getAdjacentMines() , 1);
        assertEquals(board.getCells()[1][1].getAdjacentMines() , 1);
    }

    @Test
    void shouldThrowExceptionWhenOpenedMineCell()
    {
        Board board = new Board(2, new HashSet<>(Arrays.asList(new BoardSquare(0, 1))),0);
        board.assignAdjacentMines();
        Exception exception = assertThrows(DetonatedMineException.class, () -> board.open(0,1), "Excepted to throw error when opened mine cell");
        assertTrue(exception.getMessage().contains("Oh no, you detonated a mine! Game over."));
    }

    @Test
    void shouldReturnGameOverWhenAllNonMineCellsAreOpened() throws DetonatedMineException {
        Board board = new Board(2, new HashSet<>(Arrays.asList(new BoardSquare(0, 1))),2);
        board.assignAdjacentMines();
        GameStatus status = board.open(1, 1).status;
        assertEquals(status, GameStatus.GAMEOVER);
    }

    @Test
    void shouldReturnAlreadyOpenedIfItIsAlreadyUncovered() throws DetonatedMineException {
        Board board = new Board(2, new HashSet<>(Arrays.asList(new BoardSquare(0, 1))),1);
        board.assignAdjacentMines();
        board.getCells()[1][1].setOpened(true);
        assertEquals(board.open(1,1).status, GameStatus.ALREADY_OPENED);
    }

    @Test
    void shouldUncoverAllAdjacentCellsIfOpenedOneIsZero() throws DetonatedMineException {
        Board board = new Board(4, new HashSet<>(Arrays.asList(new BoardSquare(0, 1), new BoardSquare(1, 1), new BoardSquare(2, 0))),0);
        board.assignAdjacentMines();
        GameStatus status = board.open(3,3).status;
        assertEquals(board.getOpenedCells(), 10);
        assertEquals(status, GameStatus.ONGOING);
    }

    @Test
    void shouldUncoverIfOpenedCellHasAdjacentMines() throws DetonatedMineException {
        Board board = new Board(4, new HashSet<>(Arrays.asList(new BoardSquare(0, 1), new BoardSquare(1, 1), new BoardSquare(2, 0))),0);
        board.assignAdjacentMines();
        board.open(3,3);
        GameStatus status = board.open(1,0).status;
        assertEquals(board.getOpenedCells(), 11);
        assertEquals(status, GameStatus.ONGOING);
    }


}
