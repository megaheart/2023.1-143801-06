package com.megaheart.calculator;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * CalculatorView class represents the view of the calculator application.
 * It extends JFrame and contains the GUI components of the calculator.
 */
public class CalculatorView extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton clearButton;
    private JPanel panel;

    public CalculatorView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setTitle("Calculator");

        this.add(panel);
    }

    /**
     * Clears all text fields of the calculator.
     */
    public void clearAll() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
    }

    /**
     * Returns the first number entered by the user in the calculator view.
     *
     * @return the first number entered by the user.
     */
    public int getFirstNumber() {
        return Integer.parseInt(textField1.getText());
    }

    /**
     * Returns the second number entered by the user in the calculator view.
     *
     * @return the second number entered by the user
     */
    public int getSecondNumber() {
        return Integer.parseInt(textField2.getText());
    }

    /**
     * Sets the solution of the calculator to the given integer value and displays it in the third text field.
     * @param solution the integer value representing the solution of the calculator
     */
    public void setCalcSolution(int solution) {
        textField3.setText(Integer.toString(solution));
    }

    /**
     * Adds an ActionListener to the clearButton.
     *
     * @param listenerForClear the ActionListener to be added to the clearButton
     */
    public void addClearListener(ActionListener listenerForClear) {
        clearButton.addActionListener(listenerForClear);
    }

    /**
     * Adds an ActionListener to the button1 component.
     * 
     * @param listenerForAdd the ActionListener to be added to the button1 component
     */
    public void addAddListener(ActionListener listenerForAdd) {
        button1.addActionListener(listenerForAdd);
    }

    /**
     * Adds an ActionListener to the subtract button.
     *
     * @param listenerForSubtract the ActionListener to be added to the subtract button
     */
    public void addSubtractListener(ActionListener listenerForSubtract) {
        button2.addActionListener(listenerForSubtract);
    }

    /**
     * Adds an ActionListener to the button3 component of the CalculatorView.
     * 
     * @param listenerForMultiply the ActionListener to be added to the button3 component
     */
    public void addMultiplyListener(ActionListener listenerForMultiply) {
        button3.addActionListener(listenerForMultiply);
    }

    /**
     * Adds an ActionListener to the divide button.
     *
     * @param listenerForDivide the ActionListener to be added to the divide button
     */
    public void addDivideListener(ActionListener listenerForDivide) {
        button4.addActionListener(listenerForDivide);
    }

    /**
     * Adds an ActionListener to the modulus button.
     *
     * @param listenerForModulus the ActionListener to be added to the modulus button
     */
    public void addModulusListener(ActionListener listenerForModulus) {
        button5.addActionListener(listenerForModulus);
    }

    /**
     * Displays error message
     * @param errorMessage
     */
    void displayErrorMessage(String errorMessage){

        JOptionPane.showMessageDialog(this, errorMessage);

    }

}
