package sudoku.problemdomain;

import sudoku.computationlogic.SudokuUtilities;
import sudoku.constants.GameState;
import java.io.Serializable;

/*
This class holds all the information for the current game
 */
public class SudokuGame implements Serializable {
    //This field is for whether the game is new, active, or completed
    private final GameState gameState;
    //This array holds all the values in the tiles
    private final int[][] gridState;
    //This array is to check which tiles were initially generated and which are inputted by the used
    private final boolean[][] initialTiles;

    //This is a constant for the size of the grid
    public static final int GRID_BOUNDARY = 9;

    public SudokuGame(GameState gameState, int[][] gridState, boolean[][] initialTiles) {
        this.gameState = gameState;
        this.gridState = gridState;
        this.initialTiles = initialTiles;
    }

    public GameState getGameState() {
        return gameState;
    }
    public boolean[][] getInitialTiles() {
        return initialTiles;
    }

    //Helper method used in order to work with copies of the grid state
    public int[][] getCopyOfGridState() {
        return SudokuUtilities.copyToNewArray(gridState);
    }
}
