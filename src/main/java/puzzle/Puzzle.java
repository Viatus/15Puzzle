package puzzle;

import java.lang.reflect.Array;
import java.util.*;

//Класс поля 
public class Puzzle {
    //Размер поля
    private final int size = 4;
    //Индексы пустой клетки
    private int emptyX;
    private int emptyY;

    //Массив элементов
    private int[] field = new int[size * size];

    public int[] getField() {
        return field;
    }

    //Конструктор, который заполняет поле случайным образом
    public Puzzle() {
        do {
            Set<Integer> support = new HashSet<>();
            for (int i = 0; i < size * size; i++) {
                boolean isNumberFilled = false;
                while (!isNumberFilled) {
                    final Random random = new Random();
                    int number = random.nextInt(size * size);
                    if (!support.contains(number)) {
                        if (number == 0) {
                            emptyY = i % size;
                            emptyX = i / size;
                        }
                        support.add(number);
                        field[i] = number;
                        isNumberFilled = true;
                    }
                }
            }

        } while (!isSolvable());
    }

    //Конструктор, который заполняет поле на основе заданного массива
    public Puzzle(int[] field) {
        int count = 0;
        Set<Integer> numbers = new HashSet<>();
        for (int i = 0; i < size * size; i++) {
            numbers.add(i);
        }
        for (int number : field) {
            count++;
            if (numbers.contains(number)) {
                numbers.remove(number);
            } else {
                throw new IllegalArgumentException();
            }
        }
        if (count != size * size) {
            throw new IllegalArgumentException();
        }
        System.arraycopy(field, 0, this.field, 0, size * size);
        for (int i = 0; i < size * size; i++) {
            if (field[i] == 0) {
                emptyX = i / size;
                emptyY = i % size;
            }
        }
        if (!isSolvable()) {
            throw new IllegalArgumentException();
        }
    }

    //Конструктор копирования
    private Puzzle(Puzzle puzzle) {
        System.arraycopy(puzzle.getField(), 0, this.field, 0, size * size);
        for (int i = 0; i < size * size; i++) {
            if (field[i] == 0) {
                emptyX = i / size;
                emptyY = i % size;
            }
        }
    }

    //Проверка сгенерированного поля на решимость
    public boolean isSolvable() {
        int sum = 0;
        for (int i = 0; i < size * size; i++) {
            for (int k = i / size; k < size; k++) {
                for (int h = k == i / size ? i % size + 1 : 0; h < size; h++) {
                    if (field[i] > field[k * size + h] && field[k * size + h] != 0) {
                        sum++;
                    }
                }
            }
        }
        return (sum + emptyX + 1) % 2 == 0;
    }

    //Перемещение на заданной количество клеток, например moveUp - передвинуть ненулевые клетки вверх
    public boolean moveUp(int number) {
        if (emptyX + number < size) {
            for (int i = emptyX; i < number + emptyX; i++) {
                field[i * 4 + emptyY] = field[(i + 1) * 4 + emptyY];
                field[(i + 1) * 4 + emptyY] = 0;
            }
        } else {
            return false;
        }
        emptyX = number + emptyX;
        return true;
    }

    public boolean moveDown(int number) {
        if (emptyX - number >= 0) {
            for (int i = emptyX; i > emptyX - number; i--) {
                field[i * 4 + emptyY] = field[(i - 1) * 4 + emptyY];
                field[(i - 1) * 4 + emptyY] = 0;
            }
        } else {
            return false;
        }
        emptyX = emptyX - number;
        return true;
    }

    public boolean moveLeft(int number) {
        if (emptyY + number < size) {
            for (int i = emptyY; i < emptyY + number; i++) {
                field[emptyX * 4 + i] = field[emptyX * 4 + i + 1];
                field[emptyX * 4 + i + 1] = 0;
            }
        } else {
            return false;
        }
        emptyY = emptyY + number;
        return true;
    }

    public boolean moveRight(int number) {
        if (emptyY - number >= 0) {
            for (int i = emptyY; i > emptyY - number; i--) {
                field[emptyX * 4 + i] = field[emptyX * 4 + i - 1];
                field[emptyX * 4 + i - 1] = 0;
            }
        } else {
            return false;
        }
        emptyY = emptyY - number;
        return true;
    }

    //Получение списка всех возможных ходов
    public List<Puzzle> getNeighbours() {
        List<Puzzle> neighbours = new ArrayList<>();
        Puzzle support = new Puzzle(this);
        if (support.moveRight(1)) {
            neighbours.add(support);
        }
        support = new Puzzle(this);
        if (support.moveLeft(1)) {
            neighbours.add(support);
        }
        support = new Puzzle(this);
        if (support.moveUp(1)) {
            neighbours.add(support);
        }
        support = new Puzzle(this);
        if (support.moveDown(1)) {
            neighbours.add(support);
        }
        return neighbours;
    }

    //Проверка решонности головомки
    public boolean isSolved() {
        for (int i = 0; i < size * size; i++) {
            if (field[i] != i + 1 && (i != size * size - 1)) {
                return false;
            } else {
                if (i == size * size - 1 && field[i] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    //Приблизительная оценка количество ходов до решения головоломки. Состоит из манхеттенского расстояния, линейного конфликта и углового конфликта
    public int getEstimateMovesToEnd() {
        return getManhattanDistance() + getLinearConflict() + getCornerConflict();
    }


    private int getManhattanDistance() {
        int distance = 0;
        for (int i = 0; i < size * size; i++) {
            if (field[i] != 0) {
                distance += Math.abs(i / size - (field[i] - 1) / size) + Math.abs(i % size - (field[i] - 1) % size);
            }
        }
        return distance;
    }

    private int getLinearConflict() {
        int linearConflict = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                linearConflict += getLinearConflictForCell(i, j);
            }
        }
        return linearConflict;
    }

    private int getCornerConflict() {
        int cornerConflict = 0;
        if (field[1] == 2 && field[size] == size + 1 && field[0] != 1) {
            if (getLinearConflictForCell(0, 1) == 0 && getLinearConflictForCell(1, 0) == 0) {
                cornerConflict += 2;
            }
        }
        if (field[size - 2] == size - 1 && field[2 * size - 1] == 2 * size && field[size - 1] != size) {
            if (getLinearConflictForCell(0, size - 2) == 0 && getLinearConflictForCell(1, size - 1) == 0) {
                cornerConflict += 2;
            }
        }
        if (field[(size - 2) * size] == (size - 2) * size + 1 && field[(size - 1) * size + 1] == (size - 1) * size + 2 && field[(size - 1) * size] != (size - 1) * size + 1) {
            if (getLinearConflictForCell(size - 2, 0) == 0 && getLinearConflictForCell(size - 1, 1) == 0) {
                cornerConflict += 2;
            }
        }
        return cornerConflict;
    }

    private int getLinearConflictForCell(int currentI, int currentJ) {
        int linearConflict = 0;
        for (int k = currentI + 1; k < size; k++) {
            if (field[currentI * 4 + currentJ] > field[k * 4 + currentJ] && (field[k * 4 + currentJ] - 1) % 4 == currentJ && (field[currentI * 4 + currentJ] - 1) % 4 == currentJ) {
                linearConflict += 2;
            }
        }
        for (int k = currentJ + 1; k < size; k++) {
            if (field[currentI * 4 + currentJ] > field[currentI * 4 + k] && (field[currentI * 4 + k] - 1) / 4 == currentI && (field[currentI * 4 + currentJ] - 1) / 4 == currentI) {
                linearConflict += 2;
            }
        }
        return linearConflict;
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                string.append(field[i * size + j]).append(" ");
            }
            string.append("\n");
        }
        return string.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Puzzle other = (Puzzle) obj;
        for (int i = 0; i < size * size; i++) {
            if (this.getField()[i] != other.getField()[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(field);
    }
}