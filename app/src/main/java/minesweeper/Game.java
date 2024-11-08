package minesweeper;


import minesweeper.exception.DetonatedMineException;

import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {

    private int boardSize;
    private int noOfMines;

    private Board board;

    private Random random;

    Set<BoardSquare> mineSquares;

    public static double MAX_MINE_SIZE = 0.35;

    Game(int boardSize, Set<BoardSquare> mineSquares) {
        this.boardSize = boardSize;
        this.noOfMines = mineSquares.size();
        this.board = new Board(boardSize, noOfMines);
        this.mineSquares = mineSquares;
        random = new Random();
    }

    public Game(int sizeArg, int noOfMinesArg, Optional<Random> randomA) {
        boardSize = sizeArg;
        noOfMines = noOfMinesArg;
        random = randomA.orElse(new Random());
        isValidMineSize();
        initializeBoard();
    }

    private void initializeBoard()
    {
        board = new Board(boardSize, noOfMines);
        generateMines();
        board.setMines(mineSquares);
        board.assignAdjacentMines();
    }

    boolean isValidMineSize() {
        if(noOfMines > MAX_MINE_SIZE * (boardSize * boardSize))
        {
            throw new IllegalArgumentException("not a valid mine size - Number of mines should be maximum of 35 percent of  total squares");
        }
        return true;
    }

    public Result play(String selectedSquare) throws DetonatedMineException {
        BoardSquare boardSquare = convertToCellIndices(selectedSquare);
        return board.open(boardSquare.getRow(), boardSquare.getColumn());
    }

    BoardSquare convertToCellIndices(String square)
    {
        validateSelectionFormat(square);
        String rowLetter = square.replaceAll("[^A-Z]", "");
        String columnNumber = square.replaceAll("[^0-9]", "");
        int rowNum = 0;
        for (int i = 0; i < rowLetter.length(); i++) {
            rowNum = rowNum * 26 + (rowLetter.charAt(i) - 'A' + 1);
        }
        int columnNum = Integer.parseInt(columnNumber);
        validateSelectionRange(rowNum, columnNum);
        return new BoardSquare(rowNum - 1,  columnNum- 1);
    }

    void validateSelectionRange(int row, int column) throws IllegalArgumentException
    {
        if(row < 1 || row > boardSize || column < 1 || column > boardSize)
        {
            throw new IllegalArgumentException("Invalid Selection. Please select square size within grid size");
        }
    }

    void validateSelectionFormat(String selectedSquare) throws IllegalArgumentException
    {
        Pattern pattern = Pattern.compile("^[A-Z]+\\d+$");
        Matcher matcher = pattern.matcher(selectedSquare);
        if(!matcher.matches())
        {
            throw new IllegalArgumentException("Invalid Selection. Format must be row (letters) followed by column (number) Sample A1.");
        }
    }

    public void generateMines()
    {

        mineSquares = IntStream.generate(() -> random.nextInt(boardSize))
                .mapToObj(row -> new BoardSquare(row, random.nextInt(boardSize)))
                .distinct()
                .limit(noOfMines)
                .collect(Collectors.toSet());

    }

    public Board getBoard() {
        return board;
    }

    public Set<BoardSquare> getMineSquares() {
        return mineSquares;
    }
}
