package solver;

import puzzle.Puzzle;

import java.util.*;

public class Solver {

    public static List<Puzzle> getPuzzleSolution(Puzzle puzzle) {
        if (!puzzle.isSolvable()) {
            return Collections.EMPTY_LIST;
        }
        Set<Puzzle> closed = new HashSet<>();
        Map<Puzzle, Integer> costSoFar = new HashMap<>();
        costSoFar.put(puzzle, 0);
        Map<Puzzle, Puzzle> cameFrom = new HashMap<>();
        cameFrom.put(puzzle, null);
        Queue<Puzzle> open = new PriorityQueue<Puzzle>(Comparator.comparingInt(puzzle1 -> costSoFar.get(puzzle1) + puzzle1.getEstimateMovesToEnd()));
        open.add(puzzle);
        System.out.println(puzzle.getEstimateMovesToEnd());
        while (!open.isEmpty()) {
            Puzzle current = open.poll();
            if (current.isSolved()) {
                List<Puzzle> solution = new ArrayList<>();
                while (current != null) {
                    solution.add(current);
                    current = cameFrom.get(current);
                }
                List<Puzzle> reversedSolution = new ArrayList<>();
                for (int i = solution.size() - 1; i >= 0; i--) {
                    reversedSolution.add(solution.get(i));
                }
                return reversedSolution;
            }
            closed.add(current);
            List<Puzzle> possibilities= new ArrayList<>(current.getNeighbours());
            Collections.shuffle(possibilities);
            for (Puzzle neighbour : possibilities) {
                if (!closed.contains(neighbour) && !open.contains(neighbour)) {
                    costSoFar.put(neighbour, costSoFar.get(current) + 1);
                    open.add(neighbour);
                    cameFrom.put(neighbour, current);
                } else {
                    if (closed.contains(neighbour) || open.contains(neighbour)) {
                        if (costSoFar.get(current) + 1 < costSoFar.get(neighbour)) {
                            costSoFar.put(neighbour, costSoFar.get(current) + 1);
                            cameFrom.put(neighbour, current);
                        }
                    }
                }
            }
        }
        return Collections.singletonList(puzzle);
    }

    /*public static void main(String[] args) {
        Integer[][] input = new Integer[4][4];
        input[0][0] = 3;
        input[0][1] = 1;
        input[0][2] = 2;
        input[0][3] = 4;
        input[1][0] = 5;
        input[1][1] = 6;
        input[1][2] = 7;
        input[1][3] = 8;
        input[2][0] = 9;
        input[2][1] = 10;
        input[2][2] = 11;
        input[2][3] = 12;
        input[3][0] = 13;
        input[3][1] = 14;
        input[3][2] = 15;
        input[3][3] = 0;
        Puzzle puzzle = new Puzzle(input);
        for (Integer[] array : puzzle.getField()) {
            for (int element : array) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
        System.out.println(getPuzzleSolution(puzzle));
    }*/
}
