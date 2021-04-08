package sudoku.userinterface;

import sudoku.computationlogic.GameLogic;
import sudoku.constants.GameState;
import sudoku.constants.Messages;
import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;
import sudoku.userinterface.IUserInterfaceContract;

import java.io.IOException;

/*
This class is an implementation of the EvenListener interface that is nested in the IUserInterfaceContract interface.
It implements the necessary methods.
 */
public class ControlLogic implements IUserInterfaceContract.EventListener {
    private IStorage storage;
    private IUserInterfaceContract.View view;

    //2 Parameter constructor
    public ControlLogic(IStorage storage, IUserInterfaceContract.View view) {
        this.storage = storage;
        this.view = view;
    }

    /*
    This method deals with user input in the text fields in the grid.
    It uses the IStorage field to access and modify the game data indirectly.
    Additionally, it uses the SudokuGame helper methods to work with copies of the game state (immutability)
     */
    @Override
    public void onSudokuInput(int x, int y, int input) {
        try {
            SudokuGame gameData = storage.getGameData();            //get the current data in the grid
            int [][] newGridState = gameData.getCopyOfGridState();  //create a copy of the grid that will be modified
            newGridState[y][x] = input;         //insert the user input in the corresponding tile on the grid

            boolean[][] initialTiles = gameData.getInitialTiles();
            /*
            This changes the gameData object to a new SudokuGame object
            It uses the updates copy of the gameData with the user input
            This is done to determine the new game state after the user input
             */
            gameData = new SudokuGame(GameLogic.checkForCompletion(newGridState), newGridState, initialTiles);

            storage.updateGameData(gameData);       //Update the game state using the IStorage field
            view.updateSquare(x, y, input);         //Update the visual representation of the grid using the IUserInterfaceContract.View field

            if (gameData.getGameState() == GameState.COMPLETE) {        //check whether the sudoku is solved
                view.showDialog(Messages.GAME_COMPLETE);        //show a popup to the user to notify them they won
            }
        } catch (IOException e) {       //Since we are dealing with input, we are anticipating a potential IOException
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }
    }

    /*
    This method deals with button clicks by the user in the popup screen.
    The button that is clicked is the prompt to start a new game
    It uses the IStorage field to access and modify the game data indirectly.
     */
    @Override
    public void onDialogClick() {
        try {
            storage.updateGameData(GameLogic.getNewGame());     //start a new game by using the GameLogic method for a new board generation
            view.updateBoard(storage.getGameData());        //visualize the new game on the UI
        } catch (IOException e) {       //Since we are dealing with input, we are anticipating a potential IOException
            view.showError(Messages.ERROR);
        }
    }
}
