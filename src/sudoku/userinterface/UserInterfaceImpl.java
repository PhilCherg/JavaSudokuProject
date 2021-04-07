package sudoku.userinterface;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sudoku.constants.GameState;
import sudoku.problemdomain.Coordinates;
import sudoku.problemdomain.SudokuGame;

import java.util.HashMap;

/*
This class is an implementation of the View interface that is nested in the IUserInterfaceContract interface.
It implements the necessary methods.
 */
public class UserInterfaceImpl implements IUserInterfaceContract.View, EventHandler<KeyEvent> {
    //This is the top level JavaFX container
    private final Stage stage;

    //This is a JavaFX class used to display an individual window
    private final Group root;

    /*
    This field a hashmap that holds all the TextFields on the grid
    They key of the hashmap is the coordinate of the text field
    The mapped value is the actual text field
     */
    private HashMap<Coordinates, SudokuTextField> textFieldCoordinates;

    private IUserInterfaceContract.EventListener listener;

    //Those fields are parameters for the visual design of the screen
    private static final double WINDOW_Y = 732;
    private static final double WINDOW_X = 668;
    private static final double BOARD_PADDING = 50;
    private static final double BOARD_X_AND_Y = 576;
    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0, 150, 136);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224, 242, 241);
    private static final String SUDOKU = "Sudoku";

    public UserInterfaceImpl(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
    }

    /*
    This method calls all the necessary methods to create the entire UI
    The specific functionality is extracted into individual helper methods
    This is done to enhance readability of the code
     */
    private void initializeUserInterface() {
        drawBackground(root);
        drawTitle(root);
        drawSudokuBoard(root);
        drawTextFields(root);
        drawGridLines(root);
        stage.show();
    }

    //This helper method deals with creating the lines of the sudoku grid
    private void drawGridLines(Group root) {
        int xAndY = 114;

        for (int i = 0; i < 10; i++) {      //There are 10 lines vertically and horizontally, since the grid is 9x9
            int thickness;
            if (i == 0 || i == 3 || i == 6 || i == 9) {     //Set  every third line to be thicker (to highlight the sudoku squares)
                thickness = 3;
            } else {
                thickness = 2;
            }

            /*
            The "lines" are actually JavaFX rectangles with parameters
            They are generated with a helper method whose parameters are (x, y, height, width)
            The x and y coordinates change with the index to space out the lines 64 pixels apart
            The thickness is the height of horizontal lines and the length of vertical lines
            The BOARD_X_AND_Y constant is the length of horizontal lines and the height of vertical lines
             */
            Rectangle verticalLine = getLine(xAndY + 64 * (i-1), BOARD_PADDING, BOARD_X_AND_Y, thickness);
            Rectangle horizontalLine = getLine(BOARD_PADDING,xAndY + 64 * (i-1), thickness, BOARD_X_AND_Y);

            //This adds the new lines to the root field
            root.getChildren().addAll(verticalLine, horizontalLine);
        }
    }

    private Rectangle getLine(double x, double y, double height, double width) {
        Rectangle line = new Rectangle();       //create a default JavaFX rectangle

        //Set the x, y, height, and width parameters of the rectangle object
        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);

        //Set the color of the rectangle
        line.setFill(Color.BLACK);
        return line;
    }

    //This helper method deals with creating the text fields, which represent the tiles of the sudoku grid
    private void drawTextFields(Group root) {
        final int xOrigin = 50;     //This is where the grid begins, since there is a 50px padding between it and the window
        final int yOrigin = 50;

        final int xAndYDelta = 64;          //the distance between the lines

        // O(n^2) Runtime Complexity
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //Determine the coordinates of each new text field
                int x = xOrigin + i * xAndYDelta;
                int y = yOrigin + j * xAndYDelta;

                //Create the corresponding text field
                SudokuTextField tile = new SudokuTextField(i, j);
                styleSudokuTile(tile, x, y);
                tile.setOnKeyPressed(this);

                //Add the text field to the grid hashmap
                textFieldCoordinates.put(new Coordinates(i, j), tile);
                //Add the text field to the UI
                root.getChildren().add(tile);
            }
        }
    }

    //This is a helper method that deals with the visual design of the text fields
    private void styleSudokuTile(SudokuTextField tile, double x, double y) {
        Font numberFont = new Font(32);
        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);

        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefWidth(64);
        tile.setPrefHeight(64);

        tile.setBackground(Background.EMPTY);
    }

    //This is a helper method that creates the sudoku board (which is a JavaFX rectangle)
    private void drawSudokuBoard(Group root) {
        Rectangle boardBackground = new Rectangle();

        //Set the variables related to the sudoku board rectangle
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);
        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setHeight(BOARD_X_AND_Y);
        boardBackground.setFill(BOARD_BACKGROUND_COLOR);

        //Add the board to the UI
        root.getChildren().addAll(boardBackground);
    }

    //This is a helper method that creates the title at the bottom of the window
    private void drawTitle(Group root) {
        Text title = new Text(235, 690, SUDOKU);
        title.setFill(Color.WHITE);
        Font titleFont = new Font(43);
        title.setFont(titleFont);
        root.getChildren().add(title);
    }

    //This is a helper method that creates the background around the grid
    private void drawBackground(Group root) {
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }

    //Setter method for the listener
    @Override
    public void setListener(IUserInterfaceContract.EventListener listener) {
        this.listener = listener;
    }

    //This method updates the value in a tile
    @Override
    public void updateSquare(int x, int y, int input) {
        //Access the corresponding tile from the hashmap
        SudokuTextField tile = textFieldCoordinates.get(new Coordinates(x, y));

        //This makes sure that any 0 will result in an empty text
        String value = Integer.toString(input);
        if (value.equals("0")) value = "";

        //Assign the value to the tile
        tile.textProperty().setValue(value);
    }

    /*
    This method is the same as the updateSquare() but it goes through the entire grid
    It is used when creating a new game or for bigger grid updates
     */
    @Override
    public void updateBoard(SudokuGame game) {
        boolean[][] initialTiles = game.getInitialTiles();
        for (int i = 0; i < 9; i++) {        //use nested for loops to go through the 9x9 grid
            for (int j = 0; j < 9; j++) {
                //Access the corresponding tile
                TextField tile = textFieldCoordinates.get(new Coordinates(j, i));

                //Get the value of the current tile from the (copy of) the game state
                String value = Integer.toString(game.getCopyOfGridState()[i][j]);

                if (value.equals("0")) value = "";
                tile.setText(value);

                if (initialTiles[i][j]) {
                    tile.setStyle("-fx-text-fill: black;");
                    tile.setStyle("-fx-opacity: 1;");
                    tile.setDisable(true);
                } else {
                    tile.setStyle("-fx-text-fill: blue;");
                    tile.setDisable(false);
                }
            }
        }
    }

    @Override
    public void showDialog(String message) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if (dialog.getResult() == ButtonType.OK) listener.onDialogClick();
    }

    @Override
    public void showError(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait();
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (event.getText().matches("[0-9]")) {
                int value = Integer.parseInt(event.getText());
                handleInput(value, event.getSource());
            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                handleInput(0, event.getSource());
            } else {
                ((TextField) event.getSource()).clear();
                System.out.println("Current text: "+ ((TextField) event.getSource()).getText());
            }
        }
        event.consume();
    }

    private void handleInput(int value, Object source) {
        listener.onSudokuInput(
                ((SudokuTextField) source).getX(),
                ((SudokuTextField) source).getY(),
                value
        );
    }
}
