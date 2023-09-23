package com.megaheart.calculator;

import javax.swing.*;

public class CalculatorViewPanel extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JPanel panel;

    public CalculatorViewPanel() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setTitle("Calculator");

        this.add(panel);
    }
}
