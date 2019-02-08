package project2;

import javax.swing.*;
import java.awt.*;

public class MineSweeperGUI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mine Sweeper!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MineSweeperPanel panel = new MineSweeperPanel();
        frame.setResizable(false);
        frame.getContentPane().add(panel);
        frame.setExtendedState(frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
