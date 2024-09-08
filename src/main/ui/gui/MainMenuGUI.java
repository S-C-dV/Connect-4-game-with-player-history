package ui.gui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// main menu for the GUI
public class MainMenuGUI extends JFrame {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 750;

    private GameHistory history;
    private final JsonReader jsonReader;
    private final JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/history.json";

    // EFFECT: constructs the frame and buttons, and new json classes
    public MainMenuGUI() {
        this.history = new GameHistory();
        this.jsonReader = new JsonReader(JSON_STORE);
        this.jsonWriter = new JsonWriter(JSON_STORE);

        JFrame menuFrame = new JFrame("Menu");
        setupMenuFrame(menuFrame);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.blue);

        JButton newGameButton = makeNewGameButton();
        JComboBox viewHistoryBox = makeViewHistoryBox();
        JButton loadFromFileButton = makeLoadFromFileButton();
        JButton saveToFileButton = makeSaveToFileButton();

        panel.add(newGameButton);
        panel.add(viewHistoryBox);
        panel.add(loadFromFileButton);
        panel.add(saveToFileButton);
        menuFrame.add(panel);

        menuFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: returns new "Save to file"  button, saves history to file
    private JButton makeSaveToFileButton() {
        JButton saveToFileButton = new JButton("Save History to File");
        saveToFileButton.setBounds(50, 250, 200, 20);

        saveToFileButton.addActionListener(new ActionListener() {
            @Override
            // MODIFIES: history.json
            // EFFECTS: when button clicked, saves current history to file
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });
        return saveToFileButton;
    }

    // MODIFIES: history.json
    // EFFECTS: saves history to file, or creates error message
    private void saveToFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(history);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            JFrame exception = new JFrame();
            JOptionPane.showMessageDialog(exception, "Unable to load to file",
                    "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: returns new "load from file"  button, loads file to history
    private JButton makeLoadFromFileButton() {
        JButton loadFromFileButton = new JButton("Load History from File");
        loadFromFileButton.setBounds(50, 200, 200, 20);

        loadFromFileButton.addActionListener(new ActionListener() {
            @Override
            // MODIFIES: this
            // EFFECTS: when button clicked, loads file to current history
            public void actionPerformed(ActionEvent e) {
                loadFromFile();
            }
        });
        return loadFromFileButton;
    }

    // MODIFIES: this
    // EFFECTS: loads history from file, or creates error message
    private void loadFromFile() {
        try {
            this.history = jsonReader.read();
        } catch (IOException e) {
            JFrame exception = new JFrame();
            JOptionPane.showMessageDialog(exception, "Unable to load from file",
                    "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECT: creates button for "new game", possibility of saving it to history
    private JButton makeNewGameButton() {
        JButton newGameButton = new JButton("New Game");
        newGameButton.setBounds(100, 100, 100, 20);

        newGameButton.addActionListener(new ActionListener() {
            @Override
            // MODIFIES: this
            // EFFECT: creates new game, possibility of saving it to history
            public void actionPerformed(ActionEvent ae) {
                initializeGame();
            }
        });

        return newGameButton;
    }


    // EFFECTS: creates ComboBox for history button, creates new frame with filtered history
    private JComboBox makeViewHistoryBox() {
        String[] options = {"Choose one", "View all history", "View games won by Player 1",
                "View games won by Player 2", "View all games ended in a draw"};

        JComboBox viewHistoryBox = new JComboBox(options);
        viewHistoryBox.setBounds(45, 150, 210, 20);

        viewHistoryBox.addActionListener(new ActionListener() {
            @Override
            // EFFECT: creates new frame to view history
            public void actionPerformed(ActionEvent e) {
                if (viewHistoryBox.getSelectedItem() == "View all history") {
                    initializeGameHistory(history.getHistory());
                } else if (viewHistoryBox.getSelectedItem() == "View games won by Player 1") {
                    initializeGameHistory(history.getWinnerGames(PlayingGrid.getPlayerRepresentation(true)));
                } else if (viewHistoryBox.getSelectedItem() == "View games won by Player 2") {
                    initializeGameHistory(history.getWinnerGames(PlayingGrid.getPlayerRepresentation(false)));
                } else if (viewHistoryBox.getSelectedItem() == "View all games ended in a draw") {
                    initializeGameHistory(history.getWinnerGames(PlayingGrid.getDrawRepresentation()));
                }
            }
        });
        return viewHistoryBox;
    }

    // EFFECT: creates new frame to call HistoryGUI
    private void initializeGameHistory(ArrayList<CompletedPlayingGrid> history) {
        JFrame historyFrame = new JFrame("History");
        historyFrame.setSize(WIDTH, HEIGHT);
        historyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        historyFrame.setLocationRelativeTo(null);
        historyFrame.getContentPane().setBackground(Color.blue);
        HistoryGUI historyGui = new HistoryGUI(history, historyFrame);
        historyFrame.getContentPane().add(historyGui);
        historyFrame.setVisible(true);
    }

    // EFFECT: sets up dimensions/looks of frame menu
    private void setupMenuFrame(JFrame menuFrame) {
        menuFrame.setSize(300, 400);
        menuFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);

        menuFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Game was exited"); // TODO
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        });
    }

    // EFFECT: prints out events that have been logged
    private void printLog(EventLog el) {
        for (Event event: el) {
            System.out.println(event.toString() + "\n");
        }
    }

    // MODIFIES: this
    // EFFECT: sets up frame of, and calls new game. possibility of updating history
    private void initializeGame() {
        JFrame gameFrame = new JFrame("Game");
        gameFrame.setSize(WIDTH, HEIGHT);
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.getContentPane().setBackground(Color.blue);
        GameGUI game = new GameGUI(gameFrame, this.history);
        gameFrame.getContentPane().add(game);
        gameFrame.setVisible(true);
    }

}
