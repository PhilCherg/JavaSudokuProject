package sudoku.problemdomain;

import java.io.IOException;
/*
Simple interface outlining what functionality a storage class needs to have
 */
public interface IStorage {
    void updateGameData(SudokuGame game) throws IOException;
    SudokuGame getGameData() throws IOException;
}
