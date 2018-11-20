package gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SolverFrame extends JFrame {

    private PuzzlePanel puzzlePanel;

    private void initMainPanel() {
        puzzlePanel = new PuzzlePanel();
        puzzlePanel.setPreferredSize(new Dimension(680,240));
        puzzlePanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
    }

    private void onQuit() {
        String[] vars = {"Да", "Нет"};
        int result = JOptionPane.showOptionDialog(this, "Действительно выйти?",
                "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, vars, "Да");
        if (result == JOptionPane.YES_OPTION)
            System.exit(0);
    }

    private void initListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                onQuit();
            }
        });
    }

    public SolverFrame(String s) {
        super(s);
        setSize(650, 270);
        this.setLayout(null);
        initMainPanel();
        setVisible(true);
        initListeners();
        puzzlePanel.setBounds(0,0,650,240);
        add(puzzlePanel);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setMaximumSize(getSize());
        setMinimumSize(getSize());
        setResizable(false);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SolverFrame("Решатель пятнашек"));
    }


}
