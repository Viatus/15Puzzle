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
    private Integer[][] field = new Integer[size][size];

    public Integer[][] getField() {
        return field;
    }

    //Конструктор, который заполняет поле случайным образом
    public Puzzle() {
        do {
            Set<Integer> support = new HashSet<>();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    boolean isNumberFilled = false;
                    while (!isNumberFilled) {
                        final Random random = new Random();
                        int number = random.nextInt(size * size);
                        if (!support.contains(number)) {
                            if (number == 0) {
                                emptyY = j;
                                emptyX = i;
                            }
                            support.add(number);
                            field[i][j] = number;
                            isNumberFilled = true;
                        }
                    }

                }
            }
        } while (!isSolvable());
    }

    //Конструктор, который заполняет поле на основе заданного массива
    public Puzzle(Integer[][] field) {
        int count = 0;
        Set<Integer> numbers = new HashSet<>();
        for (int i = 0; i < 16; i++) {
            numbers.add(i);
        }
        for (Integer[] array : field) {
            count++;
            if (array.length != size) {
                throw new IllegalArgumentException();
            }
            for (int element : array) {
                if (numbers.contains(element)) {
                    numbers.remove(element);
                } else {
                    throw new IllegalArgumentException();
                }
            }
        }
        if (count != size) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.field[i][j] = field[i][j];
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[i][j] == 0) {
                    emptyX = i;
                    emptyY = j;
                }
            }
        }
        if (!isSolvable()) {
            throw new IllegalArgumentException();
        }
    }

    //Проверка сгенерированного поля на решимость
    public boolean isSolvable() {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = i; k < size; k++) {
                    for (int h = k == i ? j + 1 : 0; h < size; h++) {
                        if (field[i][j] > field[k][h] && field[k][h] != 0) {
                            sum++;
                        }
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
                field[i][emptyY] = field[i + 1][emptyY];
                field[i + 1][emptyY] = 0;
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
                field[i][emptyY] = field[i - 1][emptyY];
                field[i - 1][emptyY] = 0;
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
                field[emptyX][i] = field[emptyX][i + 1];
                field[emptyX][i + 1] = 0;
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
                field[emptyX][i] = field[emptyX][i - 1];
                field[emptyX][i - 1] = 0;
            }
        } else {
            return false;
        }
        emptyY = emptyY - number;
        return true;
    }

    //Получение множества всех возможных ходов
    public Set<Puzzle> getNeighbours() {
        Set<Puzzle> neighbours = new HashSet<>();
        Puzzle support = new Puzzle(field);
        if (support.moveRight(1)) {
            neighbours.add(support);
        }
        support = new Puzzle(field);
        if (support.moveLeft(1)) {
            neighbours.add(support);
        }
        support = new Puzzle(field);
        if (support.moveUp(1)) {
            neighbours.add(support);
        }
        support = new Puzzle(field);
        if (support.moveDown(1)) {
            neighbours.add(support);
        }
        return neighbours;
    }

    //Проверка решонности головомки
    public boolean isSolved() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[i][j] != i * 4 + j + 1 && (i != size - 1 || j != size - 1)) {
                    return false;
                } else {
                    if (i == size - 1 && j == size - 1 && field[i][j] != 0) {
                        return false;
                    }
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
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[i][j] != 0) {
                    distance += Math.abs(i - (field[i][j] - 1) / 4) + Math.abs(j - (field[i][j] - 1) % 4);
                } else {
                    distance += Math.abs(i - size + 1) + Math.abs(j - size + 1);
                }
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
        if (field[0][1] == 2 && field[1][0] == size + 1 && field[0][0] != 1) {
            if (getLinearConflictForCell(0, 1) == 0 && getLinearConflictForCell(1, 0) == 0) {
                cornerConflict += 2;
            }
        }
        if (field[0][size - 2] == size - 1 && field[1][size - 1] == size + size && field[0][size - 1] != size) {
            if (getLinearConflictForCell(0, size - 2) == 0 && getLinearConflictForCell(1, size - 1) == 0) {
                cornerConflict += 2;
            }
        }
        if (field[size - 2][0] == (size - 2) * size + 1 && field[size - 1][1] == (size - 1) * size + 2 && field[size - 1][0] != (size - 1) * size + 1) {
            if (getLinearConflictForCell(size - 2, 0) == 0 && getLinearConflictForCell(size - 1, 1) == 0) {
                cornerConflict += 2;
            }
        }
        return cornerConflict;
    }

    private int getLinearConflictForCell(int currentI, int currentJ) {
        int linearConflict = 0;
        for (int k = currentI + 1; k < size; k++) {
            if (field[currentI][currentJ] > field[k][currentJ] && (field[k][currentJ] - 1) % 4 == currentJ && (field[currentI][currentJ] - 1) % 4 == currentJ) {
                linearConflict += 2;
            }
        }
        for (int k = currentJ + 1; k < size; k++) {
            if (field[currentI][currentJ] > field[currentI][k] && (field[currentI][k] - 1) / 4 == currentI && (field[currentI][currentJ] - 1) / 4 == currentI) {
                linearConflict += 2;
            }
        }
        return linearConflict;
    }

    public String toString() {
        String string = "";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                string += field[i][j] + " ";
            }
            string += "\n";
        }
        return string;
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
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!field[i][j].equals(other.getField()[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result += (i + j) * field[i][j];
            }
        }
        return result;
    }
}