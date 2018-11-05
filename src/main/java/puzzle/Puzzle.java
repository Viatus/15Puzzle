package puzzle;

import java.util.*;

//Класс поля 
public class Puzzle {
    //Размер поля
    private final int size = 4;
    //Индексы пустой клетки
    private int emptyX = size - 1;
    private int emptyY = size - 1;

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
                    if (!(i == size - 1 & j == size - 1)) {
                        boolean isNumberFilled = false;
                        while (!isNumberFilled) {
                            final Random random = new Random();
                            int number = random.nextInt(size * size - 1) + 1;
                            if (!support.contains(number)) {
                                support.add(number);
                                field[i][j] = number;
                                isNumberFilled = true;
                            }
                        }
                    }
                }
            }
            field[size - 1][size - 1] = 0;
        } while (isSolvable(field));
    }

    //Проверка сгенерированного поля на решимость
    public boolean isSolvable(Integer[][] field) {
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

    public static void main(String[] args) {
        Puzzle fi = new Puzzle();
        Integer[][] f = fi.getField();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(f[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        fi.moveDown(2);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(f[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        fi.moveUp(2);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(f[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        fi.moveRight(2);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(f[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        fi.moveLeft(2);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(f[i][j] + " ");
            }
            System.out.println();
        }
    }
}