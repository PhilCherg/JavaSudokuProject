package sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sudoku.buildlogic.SudokuBuildLogic;
import sudoku.userinterface.IUserInterfaceContract;
import sudoku.userinterface.UserInterfaceImpl;

import java.io.IOException;

/*
This class is responsible for the entire application
Its main method is called at the start of the program by the Main class
 */
public class SudokuApplication extends Application {
    private IUserInterfaceContract.View uiImpl;

    //This method is called when the application starts
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Create the UI
        uiImpl = new UserInterfaceImpl(primaryStage);
        try {
            SudokuBuildLogic.build(uiImpl);     //Attempt to create the game
        } catch (IOException e) {
            e.printStackTrace();        //Report any potential errors
            throw e;
        }
    }

    //Default method of the Application class
    public static void main(String[] args) {
        launch(args);
    }
}
