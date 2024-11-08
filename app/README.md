# Minesweeper Game

## Description
A console-based implementation of the classic Minesweeper game where the objective is to uncover all non-mine cells on a grid without triggering a mine. The game ends when a mine is uncovered, and the player wins by revealing all non-mine cells.

## Implementation
- Grid size is int max. Row header will be A..,Z, AA....AZ, AAA
- Current implementation of placing mines is using _Random_ class. **Dependency injection** is used so that any implementation can be pluggable without changing Game and Board
- **Board** only does the operation specific to grid
- **Game** object has been introduced so that it can be customizable for multiple player
- **Unit** test has been written for all classes and **E2E** written for app to simulate game play for variable inputs.

## Requirements
- Java 20
- Command-line interface

## Run the game

you can run the game with

**Build the JAR**: Run this command to build your JAR file:

    ./gradlew clean
    ./gradlew build

**Run the JAR**: 
1. To run the application after building, use the following command:

    java -jar build/libs/app-1.0.0.jar
2. 
2. We can run the application by directly executing the App class in any of the availabel IDE

## Example Gameplay
Welcome to Minesweeper!

Enter the size of the grid (e.g. 4 for a 4x4 grid): 3

Enter the number of mines to place on the grid (maximum is 35% of the total squares):3

Here is your minefield:

        1  2  3
    A   _  _  _
    B   _  _  _
    C   _  _  _


Select a square to reveal (e.g. A1): C3

Oh no, you detonated a mine! Game over.

Press any key to play again...

