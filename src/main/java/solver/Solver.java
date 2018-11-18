package solver;

import puzzle.Puzzle;

import javax.jws.soap.SOAPBinding;
import java.util.*;

public class Solver {

    public static Puzzle solvePuzzle(Puzzle puzzle) {
        if (!puzzle.isSolvable()) {
            return puzzle;
        }
        Set<Puzzle> closed = new HashSet<>();

        Map<Puzzle, Integer> costSoFar = new HashMap<>();
        costSoFar.put(puzzle, 0);
        Queue<Puzzle> open = new PriorityQueue<Puzzle>(Comparator.comparingInt(puzzle1 -> costSoFar.get(puzzle1) + puzzle1.getEstimateMovesToEnd()));
        open.add(puzzle);
        while (!open.isEmpty()) {
            Puzzle current = open.poll();
            if (current.isSolved()) {
                return current;
            }
            closed.add(current);
            System.out.println(costSoFar.get(current));
            Puzzle support;
            for (int i = 0; i < 4; i++) {
                support = new Puzzle(current.getField().clone());
                support.moveRight(i);
                if (!closed.contains(support) && !open.contains(support)) {
                    costSoFar.put(support, costSoFar.get(current) + 1);
                    open.add(support);
                } else {
                    if (closed.contains(support)) {
                        if (costSoFar.get(current) + 1 < costSoFar.get(support)) {
                            costSoFar.put(support, costSoFar.get(current) + 1);
                        }
                    }
                }
            }
            for (int i = 1; i < 4; i++) {
                support = new Puzzle(current.getField());
                support.moveUp(i);
                if (!closed.contains(support) && !open.contains(support)) {
                    costSoFar.put(support, costSoFar.get(current) + 1);
                    open.add(support);
                } else {
                    if (closed.contains(support)) {
                        if (costSoFar.get(current) + 1 < costSoFar.get(support)) {
                            costSoFar.put(support, costSoFar.get(current) + 1);
                        }
                    }
                }
            }
            for (int i = 0; i < 4; i++) {
                support = new Puzzle(current.getField());
                support.moveLeft(i);
                if (!closed.contains(support) && !open.contains(support)) {
                    costSoFar.put(support, costSoFar.get(current) + 1);
                    open.add(support);
                } else {
                    if (closed.contains(support)) {
                        if (costSoFar.get(current) + 1 < costSoFar.get(support)) {
                            costSoFar.put(support, costSoFar.get(current) + 1);
                        }
                    }
                }
            }
            for (int i = 0; i < 4; i++) {
                support = new Puzzle(current.getField());
                support.moveDown(i);
                if (!closed.contains(support) && !open.contains(support)) {
                    costSoFar.put(support, costSoFar.get(current) + 1);
                    open.add(support);
                } else {
                    if (closed.contains(support)) {
                        if (costSoFar.get(current) + 1 < costSoFar.get(support)) {
                            costSoFar.put(support, costSoFar.get(current) + 1);
                        }
                    }
                }
            }

        }
        return puzzle;
    }

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle();
        for (Integer[] array : puzzle.getField()) {
            for (int element : array) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
        puzzle = Solver.solvePuzzle(puzzle);
        for (Integer[] array : puzzle.getField()) {
            for (int element : array) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
}
