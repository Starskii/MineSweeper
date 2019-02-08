package project2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MineSweeperPanel extends JPanel {

    private JButton[][] board;
    private Cell iCell;
    private JButton quitButton;
    private MineSweeperGame game;

    public MineSweeperPanel() {

        JPanel gameBoard = new JPanel();
        ButtonListener listener = new ButtonListener();

        this.game = new MineSweeperGame();
        this.board = new JButton[this.game.getNumRows()][this.game
            .getNumCol()];

        // Set Layouts
        setLayout(new BorderLayout());
        gameBoard.setLayout(new GridLayout(this.game.getNumRows(),
            this.game.getNumCol()));

        gameBoard.setMaximumSize(new Dimension(100, 100));

        // Adding game tiles
        for (int row = 0; row < this.game.getNumRows(); row++) {

            for (int col = 0; col < this.game.getNumRows(); col++) {

                this.board[row][col] = new JButton("");

                //

                this.board[row][col].addActionListener(listener);
                this.board[row][col]
                    .setMaximumSize(new Dimension(100, 100));
                this.board[row][col]
                    .setBorder(BorderFactory.createRaisedBevelBorder());

                gameBoard.add(this.board[row][col]);
            }
        }

        // Adding menu panel items
        this.quitButton = new JButton("Quit");
        this.quitButton.addActionListener(listener);

        add(gameBoard, BorderLayout.CENTER);
        add(this.quitButton, BorderLayout.NORTH);

        displayBoard();
    }

    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {

            for (int row = 0; row < game.getNumRows(); row++) {

                for (int col = 0; col < game.getNumRows(); col++) {

                    if (board[row][col] == event.getSource()) {

                        game.select(row, col);
                    }
                }
            }

            if (quitButton == event.getSource()) {

                System.exit(0);
            }

            displayBoard();
            gameStatus();
        }
    }

    private void displayBoard() {

        for (int row = 0; row < game.getNumRows(); row++) {

            for (int col = 0; col < game.getNumRows(); col++) {

                iCell = game.getCell(row, col);

                if (iCell.isMine()) {

                    board[row][col].setText("!");
                }

                if (iCell.isExposed()) {

                    board[row][col].setEnabled(false);

                    if (iCell.getMineCount() != 0) {

                        board[row][col]
                            .setText("" + iCell.getMineCount());
                    }

                    // If mine is revealed
                    else if (iCell.isMine()) {

                        board[row][col].setText("!");
                    }

                    // If an empty space is selected
                    else {

                        board[row][col].setText("");
                    }
                }

                else {

                    board[row][col].setEnabled(true);
                }
            }
        }
    }

    private void gameStatus() {

        if (this.game.getGameStatus() == GameStatus.Lost) {

            JOptionPane.showMessageDialog(null, "You Lose");

            displayBoard();
            this.game.reset();
            resetGUIBoard();
        }

        else if (this.game.getGameStatus() == GameStatus.Won) {

            JOptionPane.showMessageDialog(null, "Congrats, You Won");

            displayBoard();
            this.game.reset();
            resetGUIBoard();
        }
    }

    private void resetGUIBoard() {

        for (int row = 0; row < this.game.getNumRows(); row++) {

            for (int col = 0; col < this.game.getNumRows(); col++) {

                this.board[row][col].setEnabled(true);
                this.board[row][col].setText("");
            }
        }
    }
}
