package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.CompletedPlayingGrid;
import model.GameHistory;
import model.PlayingGrid;
import persistence.JsonReader;
import persistence.JsonWriter;

// JSON-related methods inspired by provided phase 2 example project :
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// menu to start new game or view game history.
public class RunMainMenu {

    private Scanner input;
    private GameHistory history;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/history.json";

    // MODIFIES: this
    // EFFECT: starts up the main menu, creates new game history
    public RunMainMenu() {
        this.history = new GameHistory();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runMenu();
    }

    // EFFECT: displays command options
    private void displayMenu() {
        System.out.println("\nSelect choice:");
        System.out.println("\tn -> new game");
        System.out.println("\th -> view game history");
        System.out.println("\tl -> load game history from file");
        System.out.println("\tu -> upload/save game history to file");
    }

    // EFFECT: displays input options, awaits and runs the input
    private void runMenu() {

        String command;
        input = new Scanner(System.in);

        displayMenu();

        command = input.next();
        command = command.toLowerCase();

        processCommand(command);
    }

    // MODIFIES: this
    // EFFECT: processes command for redirect to game or game history.
    //         if player chooses to save their game, history gets updated.
    private void processCommand(String command) {
        if (command.equals("n")) {
            RunPlayingGrid game = new RunPlayingGrid();
            if (game.getSave()) {
                CompletedPlayingGrid completed = game.makeCompletedGame();
                history.addGame(completed);
            }
        } else if (command.equals("h")) {
            RunGameHistory runHistory = new RunGameHistory(history);
        } else if (command.equals("l")) {
            loadGameHistoryFile();
        } else if (command.equals("u")) {
            saveGameHistoryToFile();
        } else {
            System.out.println("Invalid input. try again.");
        }
        runMenu();
    }

    // JSON-related methods inspired by provided phase 2 example project :
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // MODIFIES: this
    // EFFECTS: loads game history from file
    private void loadGameHistoryFile() {
        try {
            this.history = jsonReader.read();
            System.out.println("Loaded game history from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // JSON-related methods inspired by provided phase 2 example project :
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: saves the workroom to file
    private void saveGameHistoryToFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(history);
            jsonWriter.close();
            System.out.println("Saved game history to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}