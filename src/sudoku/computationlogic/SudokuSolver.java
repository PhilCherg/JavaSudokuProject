package sudoku.computationlogic;

import org.ietf.jgss.GSSManager;
import sudoku.problemdomain.Coordinates;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

/*
The purpose of this class is to check whether the generated
grid by the GameGenerator is solvable in a unique way.
 */
public class SudokuSolver {
    public static boolean puzzleIsSolvable(int [][] puzzle) {
        //Create an array holding the coordinates of all empty tiles
        Coordinates[] emptyCells = getEmptyCells(puzzle);

        //This loop goes through all 40 empty cells
        for (int i = 0; i < 40; i++) {
            //Get the coordinates corresponding to the current cell
            Coordinates current = emptyCells[i];
            //Go through all numbers from 1 to 9 to test them
            for (int number = 1; number < 10; number++) {
                //Set the current cell to the current test number
                puzzle[current.getY()][current.getX()] = number;
                if (GameLogic.sudokuIsInvalid(puzzle)) {    //Check if the grid is valid with this number
                    if (number == 9) return false;      //If it is invalid and we are at 9, there are no more options, so the puzzle is not solvable
                    else continue;      //If we are not at 9 just continue to the next test number
                } else break;       //If we found a valid input for the current cell, break the loop and go to the next cell
            }
        }
        return true;            //If the entire test was true and nothing returned false, then return true
    }

    //This method detects all the empty cells in the puzzle and arranges their coordinates in an array
    private static Coordinates[] getEmptyCells(int[][] puzzle) {
        Coordinates [] emptyCells = new Coordinates[40];
        int iterator = 0;

        //Go through the entire grid with 2 for loops
        for (int i = 0; i < GRID_BOUNDARY; i++) {
            for (int j = 0; j < GRID_BOUNDARY; j++) {
                if (puzzle[i][j] == 0) {            //Check if the current cell is empty
                    emptyCells[iterator] = new Coordinates(j, i);       //Add its coordinates to the array
                    if (iterator == 39) {           //When iterator is 39 the array of 40 empty cells is full
                        return emptyCells;
                    }
                    iterator++;
                }
            }
        }

        return emptyCells;
    }
}
