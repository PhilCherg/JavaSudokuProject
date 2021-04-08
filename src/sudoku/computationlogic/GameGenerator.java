package sudoku.computationlogic;

import sudoku.problemdomain.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

/*
This class is responsible for generating the initial numbers in the grid
 */
public class GameGenerator {
    //This method returns a grid with some initial values
    public static int [][] getNewGameGrid() {
        int [][] test = unsolveGame(getSolvedGame());
        return test;
    }

    /*
    This method takes a fully solved valid board and removes 40 numbers
    Additionally, it makes sure that the resulting grid can actually be solved
    If it is insolvable, it tried to remove 40 new elements, until it gets a solvable result
     */
    private static int[][] unsolveGame(int[][] solvedGame) {
        Random random = new Random(System.currentTimeMillis());

        boolean solvable = false;
        int [][] solvableArray = new int [GRID_BOUNDARY][GRID_BOUNDARY];

        while (solvable == false) {
            SudokuUtilities.copySudokuArrayValues(solvedGame, solvableArray);

            for (int i = 0; i < 40; i++) {          //Go through 40 elements
                int xCoordinate = random.nextInt(GRID_BOUNDARY);        //Generate random coordinates for an element to be deleted
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if (solvableArray[xCoordinate][yCoordinate] != 0) {     //Check is the random position is filled
                    solvableArray[xCoordinate][yCoordinate] = 0;        //If yes, remove the value
                } else {
                    i--;        //If no, go back one iteration to make sure exactly 40 elements are deleted
                }
            }
            int [][] toBeSolved = new int [GRID_BOUNDARY][GRID_BOUNDARY];
            SudokuUtilities.copySudokuArrayValues(solvableArray, toBeSolved);       //Create a copy of the generated grid

            solvable = SudokuSolver.puzzleIsSolvable(toBeSolved);       //Check if the generated grid is solvable
        }       //repeat until we have a solvable grid with 40 removed elements

        return solvableArray;
    }

    //This method generated a fully solved grid with valid values
    private static int [][] getSolvedGame() {
        Random random = new Random(System.currentTimeMillis());
        int [][] newGrid = new int [GRID_BOUNDARY][GRID_BOUNDARY];

        //go through the numbers from 1 to 9 and assign them
        for (int value = 1; value <= GRID_BOUNDARY; value++) {
            int allocations = 0;
            int interrupt = 0;          //keeps track of how many times an invalid number was generated

            List<Coordinates> allocTracker = new ArrayList<>();

            int attempts = 0;

            while (allocations < GRID_BOUNDARY) {
                if (interrupt > 200) {              //This restarts the process if we get stuck with more than 200 interrupts
                    allocTracker.forEach(coord -> {
                        newGrid[coord.getX()][coord.getY()] = 0;
                    });

                    interrupt = 0;          //reset the count
                    allocations = 0;
                    allocTracker.clear();
                    attempts++;         //increase the number of attempts

                    if (attempts > 500) {           //This is a second failsafe in case we get stuck on generating a grid
                        clearArray(newGrid);
                        attempts = 0;
                        value = 1;
                    }
                }

                int xCoordinate = random.nextInt(GRID_BOUNDARY);        //Generate random coordinates
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if (newGrid[xCoordinate][yCoordinate] == 0) {       //Check if the chosen tile is empty
                    newGrid[xCoordinate][yCoordinate] = value;      //Set the value

                    if (GameLogic.sudokuIsInvalid(newGrid)) {       //Check if the grid is invalid with the new value
                        newGrid[xCoordinate][yCoordinate] = 0;
                        interrupt++;        //If yes, increase the counter for interrupts
                    } else {
                        allocTracker.add(new Coordinates(xCoordinate, yCoordinate));
                        allocations++;      //If not, increase the counter for allocations
                    }
                }
            }//repeat this until the current number has been assigned 9 times
        }//repeat this until all 9 numbers have been assigned 9 times

        return  newGrid;
    }

    //This is a helper method that goes through an array and resets its values
    private static void clearArray(int[][] newGrid) {
        for (int i = 0; i < GRID_BOUNDARY; i++) {
            for (int j = 0; j < GRID_BOUNDARY; j++) {
                newGrid[i][j] = 0;
            }
        }
    }
}
