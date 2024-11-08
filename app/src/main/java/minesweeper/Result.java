package minesweeper;

public class Result {

    GameStatus status;

    int adjacentMines;

    public Result(GameStatus status, int adjacentMines) {
        this.status = status;
        this.adjacentMines = adjacentMines;
    }
}
