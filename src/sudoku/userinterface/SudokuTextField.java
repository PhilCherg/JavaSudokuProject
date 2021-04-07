package sudoku.userinterface;

import javafx.scene.control.TextField;

/*
This class inherits the JavaFX TextField class
The purpose of this is to override some of the the default method to suit the needs of the project
 */
public class SudokuTextField extends TextField {
    //Since everything is on a grid, each text field has x and y coordinates
    private final int x;
    private final int y;

    //2 parameter constructor
    public SudokuTextField(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //Getter methods for the fields
    public int getX() { return x; }
    public int getY() { return y; }

    /*
    The purpose of this override is to check whether the input is a number between 0 and 9
    To do this, we use a regex expression
    The actual implementation of the parent method is not changed
     */
    @Override
    public void replaceText(int i, int i1, String s) {
        if (!s.matches("[0-9]")) {      //Check whether the input is a number between 0 and 9 using regex
            super.replaceText(i, i1, s);        //Call the parent method
        }
    }

    /*
    The purpose of this override is to check whether the input is a number between 0 and 9
    To do this, we use a regex expression
    The actual implementation of the parent method is not changed
     */
    @Override
    public void replaceSelection(String s) {
        if (!s.matches("[0-9]")) {      //Check whether the input is a number between 0 and 9 using regex
            super.replaceSelection(s);          //Call the parent method
        }
    }
}
