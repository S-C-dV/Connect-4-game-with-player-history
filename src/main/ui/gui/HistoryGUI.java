package ui.gui;

import model.Coin;
import model.CompletedPlayingGrid;
import model.PlayingGrid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// class that runs any game history
public class HistoryGUI extends JComponent {

    private final ArrayList<CompletedPlayingGrid> history;
    private JFrame frame;
    private int index;
    private CompletedPlayingGrid viewingGrid;
    private JButton nextButton;
    private JButton backButton;
    private JButton menuButton;
    private JLabel winner;

    // EFFECT: creates the class with setting frame look and buttons, and specified list of games as history
    public HistoryGUI(ArrayList<CompletedPlayingGrid> history, JFrame frame) {
        this.frame = frame;
        this.history = history;
        this.index = 0;

        nextButton = makeNextButton();
        frame.add(nextButton);
        menuButton = makeMenuButton();
        frame.add(menuButton);
        backButton = makeBackButton();
        frame.add(backButton);

        this.winner = addGameDescribe();

        if (this.history.isEmpty()) {
            viewingGrid = null;
        } else {
            viewingGrid = this.history.get(index);
        }
        winner.setText(changeText());
    }

    // EFFECT: returns label with specific location on frame.
    private JLabel addGameDescribe() {
        JLabel winner = new JLabel("text", SwingConstants.CENTER);
        winner.setBounds(225, 670,250, 30);
        winner.setOpaque(true);
        frame.add(winner);
        return winner;
    }

    // MODIFIES: this
    // EFFECT: returns button to return to menu with specific location in frame
    // has action listener to close HistoryGUI
    private JButton makeMenuButton() {
        JButton menuButton = new JButton("Menu");
        menuButton.setBounds(50, 670, 150, 30);

        menuButton.addActionListener(new ActionListener() {
            @Override
            // MODIFIES: this
            // EFFECT: removes content of frame and closes visibility
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.setVisible(false);
            }
        });
        return menuButton;
    }

    private JButton makeBackButton() {
        JButton backButton = new JButton("Back");
        backButton.setBounds(490, 670, 100, 30);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index > 0) {
                    index--;
                    viewingGrid = history.get(index);
                } else {
                    viewingGrid = null;
                    index = -1;
                }
                winner.setText(changeText());
                repaint();
            }
        });
        return backButton;
    }

    // MODIFIES: this
    // EFFECT: returns setup button for "next", changes grid displayed to next in list.
    private JButton makeNextButton() {
        JButton nextButton = new JButton("Next");
        nextButton.setBounds(590, 670, 100, 30);

        nextButton.addActionListener(new ActionListener() {
            @Override
            // MODIFIES: this
            // EFFECT: changes grid to display
            public void actionPerformed(ActionEvent e) {
                if (history.size() > index) {
                    index++;
                }
                if (history.size() <= index) {
                    viewingGrid = null;
                } else {
                    viewingGrid = history.get(index);
                }
                winner.setText(changeText());
                repaint();
            }
        });
        return nextButton;
    }

    // EFFECT: creates panel visuals.
    public void paintComponent(Graphics g) {
        if (viewingGrid != null) {
            for (int col = 0; col <= 6; col++) {
                ArrayList<Coin> coins = viewingGrid.getCol(col);
                for (int row = 0; row <= 5; row++) {
                    Graphics2D g2 = (Graphics2D) g;
                    int x = (col * 100) + 5;
                    int y = 515 - (100 * row);
                    if (row < coins.size()) {
                        Coin coin = coins.get(row);
                        if (coin.getPlayer()) {
                            g2.setColor(Color.red);
                        } else {
                            g2.setColor(Color.yellow);
                        }
                    } else {
                        g2.setColor(Color.white);
                    }
                    g2.drawOval(x, y, 90, 90);
                    g2.fillOval(x, y, 90, 90);
                }
            }
        }
    }

    // EFFECT: returns text indicating who won, or that you don't have any boards (left) to show
    private String changeText() {
        if (viewingGrid != null) {
            if (viewingGrid.getWinner().equals(PlayingGrid.getDrawRepresentation())) {
                return "Game was tied";
            } else {
                return (viewingGrid.getWinner() + " won");
            }
        } else {
            return "No board to show";
        }
    }
}
