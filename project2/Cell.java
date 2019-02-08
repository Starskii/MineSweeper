package project2;

public class Cell {

    /*******************************************************
     * The number of mines adjacent to this tile.
     ******************************************************/
    private int mineCount;

    private boolean isFlagged;

    private boolean isExposed;

    private boolean isMine;

    public void Cell() {

        setMineCount(0);
        setFlagged(false);
        setExposed(false);
        setMine(false);
    }

    public void Cell(int mineCount, boolean isFlagged,
        boolean isExposed, boolean isMine) {

        setMineCount(mineCount);
        setFlagged(isFlagged);
        setExposed(isExposed);
        setMine(isMine);
    }

    public int getMineCount() {
        return mineCount;
    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isExposed() {
        return isExposed;
    }

    public void setExposed(boolean exposed) {
        isExposed = exposed;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }
}
