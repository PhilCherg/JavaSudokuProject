package sudoku.buildlogic;

import sudoku.computationlogic.GameLogic;
import sudoku.persisitance.LocalStorageImpl;
import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;
import sudoku.userinterface.IUserInterfaceContract;
import sudoku.userinterface.ControlLogic;

import java.io.IOException;

/*
This class is responsible for calling all individual sections of the game building process
 */
public class SudokuBuildLogic {
    public static void build(IUserInterfaceContract.View userInterface) throws IOException {
        SudokuGame initialState;
        IStorage storage = new LocalStorageImpl();

        try {
            initialState = storage.getGameData();       //Try to read any existing saved data
        } catch (IOException e) {
            initialState = GameLogic.getNewGame();      //If no data is found, start a new game
            storage.updateGameData(initialState);       //Write the new game data to the file
        }

        IUserInterfaceContract.EventListener uiLogic = new ControlLogic(storage, userInterface);
        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialState);
    }
}
