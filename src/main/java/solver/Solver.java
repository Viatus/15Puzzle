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
        Queue<Puzzle> open = new PriorityQueue<>(Comparator.comparingInt(puzzle1 -> puzzle1.getEstimateMovesToEnd() + costSoFar.get(puzzle1)));
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
            List<Puzzle> possibilities = new ArrayList<>(current.getNeighbours());
            for (Puzzle neighbour : possibilities) {
                if (closed.contains(neighbour)) {
                    continue;
                }
                if (!open.contains(neighbour)) {
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

}
