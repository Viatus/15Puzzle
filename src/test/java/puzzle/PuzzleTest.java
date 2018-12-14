package puzzle;

import org.junit.Test;

import static org.junit.Assert.*;

public class PuzzleTest {
    @Test
    public void moveUp() throws Exception {
        int[] input = new int[16];
        for (int i = 0; i < 15; i++) {
            input[i] = i + 1;
        }
        input[15] = 0;
        input[13] = 13;
        input[12] = 14;
        input[2] = 0;
        input[15] = 3;
        Puzzle puzzle = new Puzzle(input);
        puzzle.moveUp(1);
        assertTrue(puzzle.getField()[2] == 7 && puzzle.getField()[6] == 0);
        puzzle.moveUp(2);
        assertTrue(puzzle.getField()[2] == 7 && puzzle.getField()[6] == 11 && puzzle.getField()[10] == 15 && puzzle.getField()[14] == 0);
    }

    @Test
    public void moveDown() throws Exception {
        int[] input = new int[16];
        for (int i = 0; i < 15; i++) {
            input[i] = i + 1;
        }
        input[15] = 0;
        Puzzle puzzle = new Puzzle(input);
        puzzle.moveDown(1);
        assertTrue(puzzle.getField()[15] == 12 && puzzle.getField()[11] == 0);
        puzzle.moveDown(2);
        assertTrue(puzzle.getField()[15] == 12 && puzzle.getField()[11] == 8 && puzzle.getField()[7] == 4 && puzzle.getField()[3] == 0);
    }

    @Test
    public void moveLeft() throws Exception {
        int[] input = new int[16];
        for (int i = 0; i < 15; i++) {
            input[i] = i + 1;
        }
        input[15] = 0;
        input[15] = 5;
        input[4] = 0;
        Puzzle puzzle = new Puzzle(input);
        puzzle.moveLeft(1);
        assertTrue(puzzle.getField()[4] == 6 && puzzle.getField()[5] == 0);
        puzzle.moveLeft(2);
        assertTrue(puzzle.getField()[4] == 6 && puzzle.getField()[5] == 7 && puzzle.getField()[6] == 8 && puzzle.getField()[7] == 0);
    }

    @Test
    public void moveRight() throws Exception {
        int[] input = new int[16];
        for (int i = 0; i < 15; i++) {
            input[i] = i + 1;
        }
        input[15] = 0;
        input[15] = 12;
        input[11] = 0;
        Puzzle puzzle = new Puzzle(input);
        puzzle.moveRight(1);
        assertTrue(puzzle.getField()[11] == 11 && puzzle.getField()[10] == 0);
        puzzle.moveRight(2);
        assertTrue(puzzle.getField()[11] == 11 && puzzle.getField()[10] == 10 && puzzle.getField()[9] == 9 && puzzle.getField()[8] == 0);
    }

    @Test
    public void isSolved() throws Exception {
        int[] input = new int[16];
        for (int i = 0; i < 15; i++) {
            input[i] = i + 1;
        }
        input[15] = 0;
        Puzzle puzzle = new Puzzle(input);
        assertTrue(puzzle.isSolved());
    }

    @Test
    public void getEstimateMovesToEnd() {
        int[] input = new int[16];
        input[0] = 1;
        input[1] = 4;
        input[2] = 3;
        input[3] = 2;
        input[4] = 5;
        input[5] = 6;
        input[6] = 12;
        input[7] = 8;
        input[8] = 9;
        input[9] = 10;
        input[10] = 11;
        input[11] = 7;
        input[12] = 13;
        input[13] = 14;
        input[14] = 15;
        input[15] = 0;
        Puzzle puzzle = new Puzzle(input);
        assertEquals(14, puzzle.getEstimateMovesToEnd());

    }
}