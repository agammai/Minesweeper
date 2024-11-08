package minesweeper;

public class Cell {

    private boolean hasMine;
    private boolean isOpened;

    private int adjacentMines;

    public String getValue()
    {
//        if(hasMine)
//        {
//            return "M";
//        }
        if(isOpened)
        {
            return String.valueOf(adjacentMines);
        }
        return "-";
    }

    public void setMine(boolean hasMine) {
        this.hasMine = hasMine;
    }

    public boolean hasMine() {
        return hasMine;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "hasMine=" + hasMine +
                ", isOpened=" + isOpened +
                ", adjacentMines=" + adjacentMines +
                '}';
    }
}
