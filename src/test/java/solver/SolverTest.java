package solver;

import org.junit.Test;
import puzzle.Puzzle;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SolverTest {
    @Test
    public void getPuzzleSolution() throws Exception {
        int[] input = new int[16];
        for (int i = 0; i < 15; i++) {
            input[i] = i + 1;
        }
        input[15] = 0;
        input[0] = 3;
        input[1] = 1;
        input[2] = 2;
        List<Puzzle> solution = Solver.getPuzzleSolution(new Puzzle(input));
        List<Puzzle> expectedSolution = new ArrayList<>();
        Puzzle currentStep = new Puzzle(input);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveDown(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveDown(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveRight(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveDown(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveRight(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveRight(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveUp(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveLeft(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveDown(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveLeft(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveUp(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveRight(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveRight(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveDown(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveLeft(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveLeft(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveUp(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveLeft(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveUp(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        currentStep.moveUp(1);
        expectedSolution.add(new Puzzle(currentStep.getField()));
        assertTrue(solution.get(solution.size()-1).isSolved());
        assertEquals(solution,expectedSolution);

        input[0]=0;
        input[1]=7;
        input[2]=10;
        input[3]=4;
        input[4]=5;
        input[5]=9;
        input[6]=8;
        input[7]=12;
        input[8]=2;
        input[9]=14;
        input[10]=13;
        input[11]=11;
        input[12]=6;
        input[13]=15;
        input[14]=1;
        input[15]=3;
        solution = Solver.getPuzzleSolution(new Puzzle(input));
        assertTrue(solution.get(solution.size()-1).isSolved());
        assertEquals(solution.size(),47);
    }

}