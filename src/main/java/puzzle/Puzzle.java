package puzzle;

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
        } while (isSolvable(field));
    }

    //Конструктор, который заполняет поле на основе заданного массива
    public Puzzle(Integer[][] field) {
        int count = 0;
        Set<Integer> numbers = new HashSet<>();
        for (int i =0;i<16;i++) {
            numbers.add(i);
        }
        for (Integer[] array : field) {
            count++;
            if (array.length !=size) {
                throw new IllegalArgumentException();
            }
            for (int element: array) {
                if (numbers.contains(element)) {
                    numbers.remove(element);
                } else {
                    throw new IllegalArgumentException();
                }
            }
        }
        if (count !=size) {
            throw new IllegalArgumentException();
        }
        this.field = field;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[i][j] == 0) {
                    emptyX = i;
                    emptyY = j;
                    return;
                }
            }
        }
    }

    //Проверка сгенерированного поля на решимость
    private boolean isSolvable(Integer[][] field) {
        int sum = size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = i; k < size; k++) {
                    for (int h = j; h < size; h++) {
                        if (field[i][j] > field[k][h]) {
                            sum++;
                        }
                    }
                }
            }
        }
        return sum % 2 == 0;
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

}