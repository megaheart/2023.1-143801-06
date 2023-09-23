package org.example;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CalculatorView extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton clearButton;
    private JPanel panel;
    private JLabel resultField;

    public CalculatorView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 250);
        this.setTitle("Calculator");

        this.add(panel);
    }

    public void clearAll() {
        textField1.setText("");
        textField2.setText("");
        resultField.setText("0");
    }

    public int getFirstNumber() {
        return Integer.parseInt(textField1.getText());
    }

    public int getSecondNumber() {
        return Integer.parseInt(textField2.getText());
    }

    public void setCalcSolution(int solution) {
        resultField.setText(Integer.toString(solution));
    }

    public void addClearListener(ActionListener listenerForClear) {
        clearButton.addActionListener(listenerForClear);
    }

    public void addAddListener(ActionListener listenerForAdd) {
        button1.addActionListener(listenerForAdd);
    }

    public void addSubtractListener(ActionListener listenerForSubtract) {
        button2.addActionListener(listenerForSubtract);
    }

    public void addMultiplyListener(ActionListener listenerForMultiply) {
        button3.addActionListener(listenerForMultiply);
    }

    public void addDivideListener(ActionListener listenerForDivide) {
        button4.addActionListener(listenerForDivide);
    }

    public void addModulusListener(ActionListener listenerForModulus) {
        button5.addActionListener(listenerForModulus);
    }

    void displayErrorMessage(String errorMessage){

        JOptionPane.showMessageDialog(this, errorMessage);

    }

}
