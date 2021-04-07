package sudoku.problemdomain;

import sudoku.computationlogic.SudokuUtilities;
import sudoku.constants.GameState;
import java.io.Serializable;

public class SudokuGame implements Serializable {
    private final GameState gameState;
    private final int[][] gridState;
    private final boolean[][] initialTiles;

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

    public int[][] getCopyOfGridState() {
        return SudokuUtilities.copyToNewArray(gridState);
    }
}
