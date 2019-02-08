package project2;

import javax.swing.*;
import java.util.Random;

public class MineSweeperGame {

    /*******************************************************
     * Multi - Dim array for the minesweeper game board.
     ******************************************************/
    private Cell[][] board;

    /*******************************************************
     * The current status of the game.
     ******************************************************/
    private GameStatus status;

    /*******************************************************
     * The total amount of mines on the board.
     ******************************************************/
    private int totalMineCount;

    private int totalUnexposedCount;

    private int numRows;

    private int numCol;

    public MineSweeperGame() {

        this.status = GameStatus.NotOverYet;

        // Setting board size
        setBoardSize();
        this.totalUnexposedCount = this.numRows * this.numCol + 10;

        // Setting number of mines
        setNumMines();

        // Creating an empty board
        this.board = new Cell[this.numRows][this.numCol];
        createBoard();

        // Randomly place mines and assign numbers
        setMines();
        assignNumbers();
    }

    private void setBoardSize() {

        int boardsize = 0;

        do {

            String input = JOptionPane.showInputDialog(null,
                "Enter size of the board " + "between 3 - 30");

            // Checking if the cancel button is pressed
            if (input == null) {

                System.exit(0);
            }

            if (input.isEmpty()) {

                JOptionPane.showMessageDialog(null,
                    "Nothing was entered");
            }

            else {
                // Checking if the boardsize is valid
                boardsize = Integer.parseInt(input);

                if (boardsize < 3 || boardsize > 30) {

                    JOptionPane.showMessageDialog(null,
                        "You  have entered an"
                            + " invalid board size.");
                }
            }

        } while (boardsize < 3 || boardsize > 30);

        this.numRows = boardsize;
        this.numCol = boardsize;
    }

    private void setNumMines() {

        int mines = 0;

        do {

            // Ask user for number of mines
            String input = JOptionPane.showInputDialog(null,
                "Enter size of the board " + "between 1 - "
                    + (this.totalUnexposedCount - 1));

            // If the cancel button is pressed or nothing is
            // entered
            if (input == null) {

                System.exit(0);
            }

            if (input.isEmpty()) {

                JOptionPane.showMessageDialog(null,
                    "Nothing was entered");
            }

            else {
                mines = Integer.parseInt(input);

                // Checking to see if the amount of mines is
                // valid
                if (mines < 1
                    || mines > (this.totalUnexposedCount - 1)) {

                    JOptionPane.showMessageDialog(null,
                        "You  have entered an" + " invalid number of "
                            + "mines.");
                }
            }
        } while (mines < 1 || mines > (this.totalUnexposedCount - 1));

        // Set total mine count
        this.totalMineCount = mines;
    }

    private void createBoard() {

        for (int row = 0; row < this.numRows; row++) {

            for (int col = 0; col < this.numCol; col++) {

                // Setting each space to a blink spot
                this.board[row][col] = new Cell();
            }
        }
    }

    /*******************************************************
     * This methods sets the mines on the board randomly
     ******************************************************/
    private void setMines() {

        Random index = new Random();
        int mineCount = 0;

        while (mineCount < totalMineCount) {

            // Choosing a random spot on board
            int col = index.nextInt(this.numCol);
            int row = index.nextInt(this.numRows);

            if (!this.board[row][col].isMine()) {

                this.board[row][col].setMine(true);
                mineCount++;
            }
        }
    }

    private void assignNumbers() {

        for (int row = 0; row < this.numRows; row++) {

            for (int col = 0; col < this.numCol; col++) {

                // Checking if space has a mine
                if (board[row][col].isMine()) {

                    // Adding a mine count to all spaces
                    // next to the mine
                    addValue(row, col);
                }
            }
        }
    }

    /*******************************************************
     * This method adds a mine count value to all spaces around the
     * mine.
     *
     * @param row The row number that contains the mine
     * @param col The column number that contains the mine.
     ******************************************************/
    private void addValue(int row, int col) {

        int checkRow;
        int checkCol;

        // Spaces that should be checked
        int xCheck[] = { -1, 0, 1, -1, 1, -1, 0, 1 };
        int yCheck[] = { 1, 1, 1, 0, 0, -1, -1, -1 };

        for (int i = 0; i < 8; i++) {

            checkRow = row + xCheck[i];
            checkCol = col + yCheck[i];

            if (validSpace(checkRow, checkCol)) {

                if (!this.board[checkRow][checkCol].isMine()) {

                    // Getting the current mine count on space
                    int currentMine = this.board[checkRow][checkCol]
                        .getMineCount();

                    // adding 1 to total mine count
                    this.board[checkRow][checkCol]
                        .setMineCount(currentMine + 1);
                }
            }
        }
    }

    /*******************************************************
     * This method determines if the current tile/space exist on the
     * minesweeper game board.
     *
     * @param row The row number that is being checked.
     * @param col The column number that is being checked.
     * @return True if the tile/space coordinate exist.
     ******************************************************/
    private boolean validSpace(int row, int col) {

        if (row >= 0 && row < this.numRows) {

            if (col >= 0 && col < this.numCol) {

                return true;
            }
        }

        return false;
    }

    public int getNumRows() {
        return this.numRows;
    }

    public int getNumCol() {
        return this.numCol;
    }

    public void select(int row, int col) {

        // Total spaces that have not been exposed
        int totalNotExposed = 0;

        // Do nothing if the space is already flagged
        if (this.board[row][col].isFlagged()) {

            return;
        }

        // Exposing the location
        this.board[row][col].setExposed(true);
        this.totalUnexposedCount--;

        if (this.board[row][col].getMineCount() == 0) {

            revealEmptySpace(row, col);
        }

        if (this.board[row][col].isMine()) {

            this.status = GameStatus.Lost;
        }

        // Checking if user Won
        else if (this.totalUnexposedCount == this.totalMineCount) {

            this.status = GameStatus.Won;
        }

        // reveal more spaces if empty space is revealed
    }

    // Reset the game board
    public void reset() {

        // Reset Game status
        this.totalUnexposedCount = this.numRows * this.numCol;
        this.status = GameStatus.NotOverYet;

        // Clear each space
        createBoard();

        // Placing new Mines and new numbers
        setMines();
        assignNumbers();
    }

    /*******************************************************
     * This method returns the current GameStatus of the game.
     *
     * @return The current status of the game.
     ******************************************************/
    public GameStatus getGameStatus() {

        return this.status;
    }

    public Cell getCell(int row, int col) {

        return this.board[row][col];
    }

    private void revealEmptySpace(int row, int col) {

    }
}
