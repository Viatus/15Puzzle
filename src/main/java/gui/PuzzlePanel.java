package gui;

import puzzle.Puzzle;
import solver.Solver;

import javax.swing.*;
import java.util.*;
import java.util.List;

public class PuzzlePanel extends JPanel {

    private Puzzle puzzle;

    private JTextField field[];

    private JButton generateRandom;
    private JButton solvePuzzle;
    private JButton showTurn;

    private List<Puzzle> solution;
    private int currentTurn;

    private void onGenerateRandom() {
        puzzle = new Puzzle();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Integer number = puzzle.getField()[i * 4 + j];
                if (number != 0) {
                    field[i * 4 + j].setText(number.toString());
                } else {
                    field[i * 4 + j].setText("");
                }
            }
        }
    }

    private void onSolvePuzzle() {
        int[] inputField = new int[16];
        Set<String> allowed = new HashSet<>();
        allowed.addAll(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", ""));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!allowed.contains(field[i * 4 + j].getText())) {
                    new JOptionPane().showMessageDialog(null, "Вы ввели запрщенный символ", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (field[i * 4 + j].getText().equals("")) {
                    inputField[i * 4 + j] = 0;
                } else {
                    inputField[i * 4 + j] = Integer.valueOf(field[i * 4 + j].getText());
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
        showTurn.setEnabled(true);
        currentTurn = 0;
    }

    private void onShowTurn() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Integer number = solution.get(currentTurn).getField()[i * 4 + j];
                if (number != 0) {
                    field[i * 4 + j].setText(number.toString());
                } else {
                    field[i * 4 + j].setText("");
                }
            }
        }
        if (currentTurn < solution.size() - 1) {
            currentTurn++;
        } else {
            currentTurn = 0;
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
        showTurn = new JButton("Ход");
        showTurn.setBounds(241, 161, 160, 80);
        showTurn.setEnabled(false);
        showTurn.addActionListener(e -> onShowTurn());
        add(showTurn);
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
