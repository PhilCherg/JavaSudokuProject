package sudoku.persisitance;

import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;
import java.io.*;

/*
This class is an implementation of the IStorage interface
It accesses a file in the system where it stores and retrieves the necessary data.
 */
public class LocalStorageImpl implements IStorage {
    private static File GAME_DATA = new File(
            System.getProperty("../.."),        //This is the path to the file; the ../.. indicates its 2 folders up form the current folder in the tree
            "gamedata.txt"          //This is the name of the file
    );

    //This method writes any new data to the file
    @Override
    public void updateGameData(SudokuGame game) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(GAME_DATA);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(game);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new IOException("Unable to access Game Data");
        }
    }

    //This method reads the data from the file
    @Override
    public SudokuGame getGameData() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(GAME_DATA);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            SudokuGame gameState = (SudokuGame) objectInputStream.readObject();
            objectInputStream.close();
            return gameState;
        } catch (ClassNotFoundException e) {
            throw  new IOException("File Not Found");
        }
    }
}
