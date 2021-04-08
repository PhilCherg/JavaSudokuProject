package sudoku.computationlogic;

import sudoku.constants.GameState;
import sudoku.constants.Rows;
import sudoku.problemdomain.SudokuGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

/*
This class is responsible for managing the generation of the game
 */
public class GameLogic {
    //This method uses the GameGenerator class to generate a new grid
    public static SudokuGame getNewGame() {
        int[][] grid = GameGenerator.getNewGameGrid();
        boolean[][] isInitial = new boolean[GRID_BOUNDARY][GRID_BOUNDARY];

        //Check which values are full during the initial generation to determine the initial values
        for (int i = 0; i < GRID_BOUNDARY; i++) {
            for (int j = 0; j < GRID_BOUNDARY; j++) {
                isInitial[i][j] = (grid[i][j] != 0);
            }
        }

        return new SudokuGame(GameState.NEW, grid, isInitial);
    }

    //This method checks is the current game is completed
    public static GameState checkForCompletion(int [][] grid) {
        /*
        First, check if the grid is currently invalid (for example two 3s in the same square)
        Second, check if there are any unfilled tiles
         */
        if (sudokuIsInvalid(grid) || tilesAreNotFilled(grid)) {
            return GameState.ACTIVE;            //If yes, the game is still active (currently being played)
        } else {
            return GameState.COMPLETE;          //If not, the game is complete (fully filled out correctly)
        }
    }

    //This method checks whether the grid is valid by using the main sudoku rules
    public static boolean sudokuIsInvalid(int[][] grid) {
        /*
        First, check if all rows contain 9 unique numbers
        Second, check if all columns contain 9 unique numbers
        Third, check if all 3x3 squares contain 9 unique numbers
         */
        if (rowsAreInvalid(grid) || columnsAreInvalid(grid) || squaresAreInvalid(grid)) {
            return true;
        } else {
            return false;
        }

    }

    //This method goes through all the rows and checks if they have repeated numbers
    private static boolean rowsAreInvalid(int[][] grid) {
        //Go through the grid row by row by incrementing the y index
        for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
            List<Integer> row = new ArrayList<>();      //Create a list to store the current row
            //Go through each row elements by element by incrementing the x index
            for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
                row.add(grid[yIndex][xIndex]);
            }

            //Use the helper collectionHadRepeats method to check if all numbers in the row are unique
            if (collectionHasRepeats(row)) {
                return true;        //The first time a non unique number is detected, return true (the row is invalid)
            }
        }

        return false;   //If the entire grid has not detected an invalid row, return false (the rows are no invalid)
    }

    //This method is the same as the row method but it goes column by column
    private static boolean columnsAreInvalid(int[][] grid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            List<Integer> row = new ArrayList<>();
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                row.add(grid[yIndex][xIndex]);
            }

            if (collectionHasRepeats(row)) {
                return true;
            }
        }

        return false;
    }

    //This method checks if the squares are valid by checking 3 rows of 3 squares
    private static boolean squaresAreInvalid(int[][] grid) {
        //Extract the check for individual rows of squares to a helper method
        if (rowOfSquaresIsInvalid(Rows.TOP, grid)) {
            return true;
        }
        if (rowOfSquaresIsInvalid(Rows.MIDDLE, grid)) {
            return true;
        }
        if (rowOfSquaresIsInvalid(Rows.BOTTOM, grid)) {
            return true;
        }

        return false;
    }

    //This method checks if a row of 3 squares is invalid
    private static boolean rowOfSquaresIsInvalid(Rows value, int[][] grid) {
        //Based on the value, the row can be TOP, MIDDLE, BOTTOM (using the enum for row name)
        switch (value) {
            case TOP:
                //Extract the functionality to check each square in another function
                if (squareIsInvalid(0, 0, grid)) return true;
                if (squareIsInvalid(3, 0, grid)) return true;
                if (squareIsInvalid(6, 0, grid)) return true;
                return false;
            case MIDDLE:
                if (squareIsInvalid(0, 3, grid)) return true;
                if (squareIsInvalid(3, 3, grid)) return true;
                if (squareIsInvalid(6, 3, grid)) return true;
                return false;
            case BOTTOM:
                if (squareIsInvalid(0, 6, grid)) return true;
                if (squareIsInvalid(3, 6, grid)) return true;
                if (squareIsInvalid(6, 6, grid)) return true;
                return false;
            default:
                return false;
        }
    }

    //This method checks if the values in a given 3x3 square are unique
    private static boolean squareIsInvalid(int xIndex, int yIndex, int[][] grid) {
        //Determine the bottom right end of the square based on the top left input from the parameters
        int xIndexEnd = xIndex + 3;
        int yIndexEnd = yIndex + 3;

        //Create a list to store the values in the square
        List<Integer> square = new ArrayList<>();

        //Go through the square as a 3x3 array
        for (int i = yIndex; i < yIndexEnd; i++) {
            for (int j = xIndex; j < xIndexEnd; j++) {
                square.add(grid[i][j]);
            }
        }

        //Check if the square list contains repeated values
        if (collectionHasRepeats(square)) return  true;
        else return false;
    }

    //This methods checks is a list contains only unique elements by checking the frequency of each element
    public static boolean collectionHasRepeats(List<Integer> collection) {
        //Go through the numbners from 1 to 9
        for (int index = 1; index <= GRID_BOUNDARY; index++) {
            if (Collections.frequency(collection, index) > 1) {     //Check how many times the current number is in the list
                return true;
            }
        }
        return false;
    }

    //This method checks if there are empty tiles
    public static boolean tilesAreNotFilled(int[][] grid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                if (grid[xIndex][yIndex] == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
