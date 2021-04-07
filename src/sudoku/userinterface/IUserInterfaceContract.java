package sudoku.userinterface;

import sudoku.problemdomain.SudokuGame;

/*
This interface contains two nested interfaces.
The first one, EventListener, outlines functionality related to the user input from text fields or buttons.
The second one, View, outlines functionality related to the user interface.
 */

public interface IUserInterfaceContract {
    /*
    This interface deals with user input
     */
    interface EventListener {
        //This method deals with user input in a text field with a specified x and y position in the grid
        void onSudokuInput(int x, int y, int input);

        //This method deals with button clicks from the user in dialog popups
        void onDialogClick();
    }

    /*
    This interface deals with the user interface
     */
    interface View {
        //Setter method for the listener field
        void setListener(IUserInterfaceContract.EventListener listener);

        //This method deals with changing the number displayed in a tile on the grid
        void updateSquare(int x, int y, int input);

        //This method deals with updating the entire grid
        void updateBoard(SudokuGame game);

        //This method deals with creating a popup message for the user
        void showDialog(String message);

        //This method deals with showing any errors that occur to the user
        void showError(String message);
    }
}
