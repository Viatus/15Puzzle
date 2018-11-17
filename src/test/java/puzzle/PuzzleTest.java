package puzzle;

import org.junit.Test;

import static org.junit.Assert.*;

public class PuzzleTest {
    @Test
    public void moveUp() throws Exception {
        Integer[][] input = new Integer[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                input[i][j] = i * 4 + j + 1;
            }
        }
        input[0][2] = 0;
        input[3][3] = 3;
        Puzzle puzzle = new Puzzle(input);
        puzzle.moveUp(1);
        assertTrue(puzzle.getField()[0][2] == 7 && puzzle.getField()[1][2] == 0);
        puzzle.moveUp(2);
        assertTrue(puzzle.getField()[0][2] == 7 && puzzle.getField()[1][2] == 11 && puzzle.getField()[2][2] == 15 && puzzle.getField()[3][2] == 0);
    }

    @Test
    public void moveDown() throws Exception {
        Integer[][] input = new Integer[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                input[i][j] = i * 4 + j + 1;
            }
        }
        input[3][3] = 0;
        Puzzle puzzle = new Puzzle(input);
        puzzle.moveDown(1);
        assertTrue(puzzle.getField()[3][3] == 12 && puzzle.getField()[2][3] == 0);
        puzzle.moveDown(2);
        assertTrue(puzzle.getField()[3][3] == 12 && puzzle.getField()[2][3] == 8 && puzzle.getField()[1][3] == 4 && puzzle.getField()[0][3] == 0);
    }

    @Test
    public void moveLeft() throws Exception {
        Integer[][] input = new Integer[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                input[i][j] = i * 4 + j + 1;
            }
        }
        input[3][3] = 5;
        input[1][0] = 0;
        Puzzle puzzle = new Puzzle(input);
        puzzle.moveLeft(1);
        assertTrue(puzzle.getField()[1][0] == 6 && puzzle.getField()[1][1] == 0);
        puzzle.moveLeft(2);
        assertTrue(puzzle.getField()[1][0] == 6 && puzzle.getField()[1][1] == 7 && puzzle.getField()[1][2] == 8 && puzzle.getField()[1][3] == 0);
    }

    @Test
    public void moveRight() throws Exception {
        Integer[][] input = new Integer[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                input[i][j] = i * 4 + j + 1;
            }
        }
        input[3][3] = 12;
        input[2][3] = 0;
        Puzzle puzzle = new Puzzle(input);
        puzzle.moveRight(1);
        assertTrue(puzzle.getField()[2][3] == 11 && puzzle.getField()[2][2] == 0);
        puzzle.moveRight(2);
        assertTrue(puzzle.getField()[2][3] == 11 && puzzle.getField()[2][2] == 10 && puzzle.getField()[2][1] == 9 && puzzle.getField()[2][0] == 0);
    }

    @Test
    public void isSolved() throws Exception {
        Integer[][] input = new Integer[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                input[i][j] = i * 4 + j + 1;
            }
        }
        input[3][3] = 0;
        Puzzle puzzle = new Puzzle(input);
        assertTrue(puzzle.isSolved());
    }

    @Test
    public void getEstimateMovesToEnd() {
        Integer[][] input = new Integer[4][4];
        input[0][0] = 1;
        input[0][1] = 4;
        input[0][2] = 3;
        input[0][3] = 2;
        input[1][0] = 5;
        input[1][1] = 6;
        input[1][2] = 12;
        input[1][3] = 8;
        input[2][0] = 9;
        input[2][1] = 10;
        input[2][2] = 11;
        input[2][3] = 7;
        input[3][0] = 13;
        input[3][1] = 14;
        input[3][2] = 15;
        input[3][3] = 0;
        Puzzle puzlle = new Puzzle(input);
        assertEquals(14, puzlle.getEstimateMovesToEnd());

    }
}