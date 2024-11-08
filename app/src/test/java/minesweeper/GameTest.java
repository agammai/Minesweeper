package minesweeper;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    void shouldThrowErrMsgWhenNoOfMinesGreaterThan35Percent()
    {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Game(4, 10, Optional.empty()), "Excepted to throw error when number of mines greater than 35 percent");
        assertTrue(exception.getMessage().contains("Number of mines should be maximum of 35 percent of  total squares"));
    }

    @Test
    void shouldInitializeBoardWhenNoOfMinesIsValid()
    {
        assertDoesNotThrow(() -> new Game(4, 5,Optional.empty()));
    }

    @Test
    void shouldGenerateMinesEqualToNumberOfMines()
    {
        int noOfMines = 5;
        Game game = new Game(4, noOfMines, Optional.empty());
        assertFalse(game.mineSquares.isEmpty());
        assertEquals(game.mineSquares.size(), noOfMines);

    }

    @Test
    void shouldReturn00ForA1()
    {
        Game game = new Game(4, 5, Optional.empty());
        BoardSquare boardSquare = game.convertToCellIndices("A1");
        assertEquals(boardSquare.getRow(), 0);
        assertEquals(boardSquare.getColumn(), 0);
    }

    @Test
    void shouldReturn260ForAA1()
    {
        Game game = new Game(27, 5, Optional.empty());
        BoardSquare boardSquare = game.convertToCellIndices("AA27");
        assertEquals(boardSquare.getRow(), 26);
        assertEquals(boardSquare.getColumn(), 26);
    }

    @Test
    void shouldReturn10ForB1()
    {
        Game game = new Game(4, 5, Optional.empty());
        BoardSquare boardSquare = game.convertToCellIndices("B1");
        assertEquals(boardSquare.getRow(), 1);
        assertEquals(boardSquare.getColumn(), 0);
    }

    @Test
    void shouldThrowErrorForIndexGreaterThanGridSize()
    {
        Game game = new Game(4, 5, Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> game.convertToCellIndices("E1"), "Excepted to throw error when selected square is not within grid size");
        assertTrue(exception.getMessage().contains("Invalid Selection. Please select square size within grid size"));
    }

    @Test
    void shouldThrowErrorForInvalidSquareSelectionFormat()
    {
        Game game = new Game(4, 5, Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> game.convertToCellIndices("@1"), "Excepted to throw error when selected square is not within grid size");
        assertTrue(exception.getMessage().contains("Invalid Selection. Format must be row (letters) followed by column (number) Sample A1."));
    }


}
