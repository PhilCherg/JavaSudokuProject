package sudoku.computationlogic;

import sudoku.problemdomain.SudokuGame;

/*
This is a simple class containing methods to copying arrays to avoid working with the original array
 */
public class SudokuUtilities {
    //This method copies one array to another
    public static void copySudokuArrayValues(int [][] oldArray, int [][] newArray) {
        for (int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++) {
                newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
            }
        }
    }

    //This method returns a copy of an array
    public static int [][] copyToNewArray(int [][] oldArray) {
        int [][] newArray = new int[SudokuGame.GRID_BOUNDARY][SudokuGame.GRID_BOUNDARY];

        for (int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++) {
                newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
            }
        }

        return  newArray;
    }
}
