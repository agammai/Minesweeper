/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package minesweeper;

import minesweeper.exception.DetonatedMineException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Random;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Minesweeper!");
        boolean playAgain = false;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
        do {
            play(reader, new Random());
            System.out.println("Press any key to play again and Q to quit");
                String input = reader.readLine();
                if (!input.equalsIgnoreCase("Q")) {
                    playAgain = true;
                }
        } while (playAgain);

            } catch (IOException e) {
                System.err.println("Error receiving input " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number");
            }

    }

    public static void play(BufferedReader reader, Random random) throws IOException {
        System.out.print("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
        String sizeInput = reader.readLine();
        int boardSize = Integer.parseInt(sizeInput);

        System.out.print("Enter the number of mines to place on the grid (maximum is 35% of the total squares): ");
        String minesInput = reader.readLine();
        int numberOfMines = Integer.parseInt(minesInput);

        Game game = new Game(boardSize, numberOfMines, Optional.ofNullable(random));

        System.out.println("Here is your minefield:");
        game.getBoard().print();

        try {
            boolean canContinue = true;
            while (canContinue) {
                System.out.println("Select a square to reveal (e.g. A1):");
                String selectedSquare = reader.readLine();
                Result result = game.play(selectedSquare);

                int adjacentMines = result.adjacentMines;
                GameStatus status = result.status;

                String outputMsg = "This square contains" + adjacentMines + " adjacent mines.";

                switch (status) {
                    case GAMEOVER -> {

                        System.out.println(outputMsg);
                        System.out.println("Here is your updated minefield:");
                        game.getBoard().print();

                        canContinue = false;
                        System.out.println("Congratulations, you have won the game!");
                    }
                    case ONGOING -> {

                        System.out.println(outputMsg);
                        System.out.println("Here is your updated minefield:");
                        game.getBoard().print();

                    }
                    case ALREADY_OPENED -> {
                        System.out.println("This cell is already opened");
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + status);
                }
            }
        } catch (DetonatedMineException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}
