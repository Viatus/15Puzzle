package gui;

import puzzle.Puzzle;
import solver.Solver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PuzzlePanel extends JPanel {

    private Puzzle puzzle;

    private JTextField field[];

    private JButton generateRandom;
    private JButton solvePuzzle;
    private JButton showSolution;

    private List<Puzzle> solution;

    private void onGenerateRandom() {
        puzzle = new Puzzle();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Integer number = puzzle.getField()[i][j];
                if (number != 0) {
                    field[i * 4 + j].setText(number.toString());
                } else {
                    field[i * 4 + j].setText("");
                }
            }
        }
    }

    private void onSolvePuzzle() {
        Integer[][] inputField = new Integer[4][4];
        Set<String> allowed = new HashSet<>();
        allowed.addAll(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", ""));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!allowed.contains(field[i * 4 + j].getText())) {
                    new JOptionPane().showMessageDialog(null, "Вы ввели запрщенный символ", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (field[i * 4 + j].getText().equals("")) {
                    inputField[i][j] = 0;
                } else {
                    inputField[i][j] = Integer.valueOf(field[i * 4 + j].getText());
                }
            }
        }
        try {
            puzzle = new Puzzle(inputField);
        } catch (IllegalArgumentException e) {
            new JOptionPane().showMessageDialog(null, "Введенная вами комбинация нерешаема", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        solution = Solver.getPuzzleSolution(puzzle);
        showSolution.setEnabled(true);
    }

    private void onShowSolution() {
        for (Puzzle state : solution) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    Integer number = state.getField()[i][j];
                    if (number != 0) {
                        field[i * 4 + j].setText(number.toString());
                    } else {
                        field[i * 4 + j].setText("");
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public PuzzlePanel() {
        super();
        setLayout(null);
        generateRandom = new JButton("Случайно");
        generateRandom.setBounds(241, 0, 160, 80);
        generateRandom.addActionListener(e -> onGenerateRandom());
        add(generateRandom);
        solvePuzzle = new JButton("Решить");
        solvePuzzle.setBounds(241, 81, 160, 80);
        solvePuzzle.addActionListener(e -> onSolvePuzzle());
        add(solvePuzzle);
        showSolution = new JButton("Показать решение");
        showSolution.setBounds(241, 161, 160, 80);
        showSolution.setEnabled(false);
        showSolution.addActionListener(e -> onShowSolution());
        add(showSolution);
        field = new JTextField[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                field[i * 4 + j] = new JTextField();
                field[i * 4 + j].setBounds(j * 60, i * 60, 60, 60);
                field[i * 4 + j].setHorizontalAlignment(JTextField.CENTER);
                add(field[i * 4 + j]);
            }
        }
    }

}
