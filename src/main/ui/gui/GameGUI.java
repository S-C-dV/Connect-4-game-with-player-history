package ui.gui;

import model.Coin;
import model.CompletedPlayingGrid;
import model.GameHistory;
import model.PlayingGrid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// class that runs a game
public class GameGUI extends JComponent {

    private PlayingGrid playingGrid;
    private Boolean isPlayer1Turn;
    private JFrame frame;
    private JButton wdButton;
    private GameHistory history;
    private JLabel turn;




    // EFFECTS: sets up frame and contents for the game
    public GameGUI(JFrame frame, GameHistory history) {
        setDataForConstructor(frame, history);
        addMouseListener(new ColumnMouseHandler());

        wdButton = makeWDButton();
        turn = new JLabel("Player 1's turn", SwingConstants.CENTER);
        turn.setBounds(5, 680, 110, 35);
        turn.setOpaque(true);

        frame.add(wdButton);
        frame.add(turn);
    }


    private JButton makeWDButton() {
        JButton wdButton = new JButton("Withdraw");
        wdButton.setBounds(300, 650, 100, 50);

        wdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapPlayer();
                endGameWithWinner();
            }
        });

        return wdButton;
    }

    private void setDataForConstructor(JFrame frame, GameHistory history) {
        this.frame = frame;
        this.playingGrid = new PlayingGrid();
        this.isPlayer1Turn = true;
        this.history = history;
    }

    // MODIFIES: this, MainMenuGUI
    // ends game, closes the frame, optionally adds game to history
    private void endGameWithWinner() {
        JFrame anounceWinner = new JFrame();
        int saveQuestion = JOptionPane.showConfirmDialog(anounceWinner, getWinnerMessage(),
                "game won", JOptionPane.YES_NO_OPTION);
        this.playingGrid.setWinner(getWinnerFromTurn());
        if (saveQuestion == JOptionPane.YES_OPTION) {
            CompletedPlayingGrid completed = playingGrid.makeGridCompleted();
            history.addGame(completed);
        }
        frame.getContentPane().removeAll();
        frame.remove(wdButton);
        frame.setVisible(false);
    }

    private String getWinnerFromTurn() {
        return playingGrid.whoWon(isPlayer1Turn);
    }

    // EFFECTS: returns the message to display when game is won
    private String getWinnerMessage() {
        if (playingGrid.isGameDraw()) {
            return "Game ended in a draw! Would you like to save this game to history?";
        } else {
            return (playingGrid.getPlayerRepresentation(isPlayer1Turn)
                    + " wins! Would you like to save this game to history?");
        }
    }

    // EFFECT: paints the frame.
    public void paintComponent(Graphics g) {
        for (int col = 0; col <= 6; col++) {
            ArrayList<Coin> coins = playingGrid.getCol(col);
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

    // class that listens for mouse click and updates GameGUI's board accordingly
    class ColumnMouseHandler extends MouseAdapter {
        @Override
        // MODIFIES: GameGUI
        // EFFECTS: adds coin to column according to where player clicked.
        public void mouseClicked(MouseEvent me) {
            int xcoord = me.getX();
            int ycoord = me.getY();
            if (ycoord <= 605) {
                for (int i = 1; i <= 7; i++) {
                    if ((i * 100) >= xcoord) {
                        playingGrid.addCoinToCol(i - 1, isPlayer1Turn);
                        break;
                    }
                }
            }
            repaint();
            if (playingGrid.getGameOver()) {
                endGameWithWinner();
            }
            swapPlayer();
        }
    }

    // MODIFIES: this
    // EFFECT: swaps player and swaps text telling which player turn it is
    public void swapPlayer() {
        this.isPlayer1Turn = !this.isPlayer1Turn;
        String turnText;
        if (this.isPlayer1Turn) {
            turnText = "Player 1's turn";
        } else {
            turnText = "Player 2's turn";
        }
        turn.setText(turnText);
    }
}
